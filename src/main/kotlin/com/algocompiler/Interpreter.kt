package com.algocompiler

import kotlin.math.*

class ReturnException(val value: Any?) : Exception()

class Interpreter {
    private val variables = mutableMapOf<String, Any>()
    private val constants = mutableSetOf<String>()
    private val functions = mutableMapOf<String, FunctionDeclaration>()
    private val variableTypes = mutableMapOf<String, String>() // Stocker les types déclarés
    private var currentLine = 0 // Numéro de ligne en cours d'exécution

    // Helper pour lancer une erreur d'exécution avec le numéro de ligne
    private fun runtimeError(message: String): Nothing {
        throw Exception("Erreur ligne $currentLine: $message")
    }

    // Normalisation des noms (case insensitive)
    private fun normalize(name: String): String {
        return name.lowercase()
                .replace("é", "e")
                .replace("è", "e")
                .replace("ê", "e")
                .replace("à", "a")
                .replace("â", "a")
                .replace("î", "i")
                .replace("ô", "o")
                .replace("ù", "u")
                .replace("û", "u")
                .replace("ç", "c")
    }

    fun execute(program: Program) {
        // Store functions
        for (func in program.functions) {
            functions[normalize(func.name)] = func
        }

        // Initialize variables
        for (varDecl in program.variables) {
            initializeVariable(varDecl)
        }

        // Initialize constants
        for (constDecl in program.constants) {
            initializeConstant(constDecl)
        }

        // Execute statements
        for (statement in program.statements) {
            executeStatement(statement)
        }
    }

    private fun initializeVariable(varDecl: VariableDeclaration) {
        val normalizedName = normalize(varDecl.name)
        val normalizedType = normalize(varDecl.type)

        // Stocker le type de la variable
        variableTypes[normalizedName] = normalizedType

        if (varDecl.arraySize != null && varDecl.arraySize2 != null) {
            // Initialize matrix (2D array)
            if (varDecl.initialValue != null) {
                // With initial value
                val value = evaluateExpression(varDecl.initialValue)
                if (value is List<*>) {
                    @Suppress("UNCHECKED_CAST")
                    variables[normalizedName] = (value as List<List<Any>>).map { row ->
                        row.map { castToType(it, normalizedType) }.toMutableList()
                    }.toMutableList()
                } else {
                    runtimeError("La valeur d'initialisation pour '${varDecl.name}' doit être une matrice")
                }
            } else {
                // With default values
                val defaultValue =
                        when (normalizedType) {
                            "entier" -> 0
                            "reel" -> 0.0
                            "chaine" -> ""
                            "caractere" -> '\u0000'
                            "booleen" -> false
                            else -> 0
                        }
                variables[normalizedName] = MutableList(varDecl.arraySize) {
                    MutableList(varDecl.arraySize2) { defaultValue }
                }
            }
        } else if (varDecl.arraySize != null) {
            // Initialize array (1D)
            if (varDecl.initialValue != null) {
                // With initial value
                val value = evaluateExpression(varDecl.initialValue)
                if (value is List<*>) {
                    @Suppress("UNCHECKED_CAST")
                    variables[normalizedName] = (value as List<Any>).map {
                        castToType(it, normalizedType)
                    }.toMutableList()
                } else {
                    runtimeError("La valeur d'initialisation pour '${varDecl.name}' doit être un tableau")
                }
            } else {
                // With default values
                val defaultValue =
                        when (normalizedType) {
                            "entier" -> 0
                            "reel" -> 0.0
                            "chaine" -> ""
                            "caractere" -> '\u0000'
                            "booleen" -> false
                            else -> 0
                        }
                variables[normalizedName] = MutableList(varDecl.arraySize) { defaultValue }
            }
        } else {
            // Initialize simple variable
            if (varDecl.initialValue != null) {
                val value = evaluateExpression(varDecl.initialValue)
                // Appliquer le casting selon le type déclaré
                variables[normalizedName] = castToType(value, normalizedType)
            } else {
                // With default value
                variables[normalizedName] =
                        when (normalizedType) {
                            "entier" -> 0
                            "reel" -> 0.0
                            "chaine" -> ""
                            "caractere" -> '\u0000'
                            "booleen" -> false
                            else -> 0
                        }
            }
        }
    }

    private fun initializeConstant(constDecl: ConstantDeclaration) {
        val normalizedName = normalize(constDecl.name)
        val value = evaluateExpression(constDecl.value)
        variables[normalizedName] = value
        constants.add(normalizedName)
    }

    private fun executeStatement(statement: Statement) {
        // Mettre à jour le numéro de ligne courant
        currentLine = statement.line

        when (statement) {
            is Assignment -> executeAssignment(statement)
            is ArrayAssignment -> executeArrayAssignment(statement)
            is MatrixAssignment -> executeMatrixAssignment(statement)
            is IfStatement -> executeIfStatement(statement)
            is ForLoop -> executeForLoop(statement)
            is WhileLoop -> executeWhileLoop(statement)
            is RepeatUntilLoop -> executeRepeatUntilLoop(statement)
            is WhenStatement -> executeWhenStatement(statement)
            is WriteStatement -> executeWriteStatement(statement)
            is ReadStatement -> executeReadStatement(statement)
            is ReturnStatement ->
                    throw ReturnException(
                            if (statement.expression != null)
                                    evaluateExpression(statement.expression)
                            else null
                    )
            is FunctionCall -> executeFunctionCall(statement.name, statement.arguments)
        }
    }

    private fun executeAssignment(assignment: Assignment) {
        val normalizedName = normalize(assignment.variable)
        if (constants.contains(normalizedName)) {
            runtimeError("Impossible de modifier la constante '${assignment.variable}'")
        }
        val value = evaluateExpression(assignment.expression)

        // Vérifier si la variable existante est un tableau ou une matrice
        val existingValue = variables[normalizedName]

        if (existingValue is MutableList<*>) {
            // C'est un tableau ou une matrice
            if (existingValue.firstOrNull() is MutableList<*>) {
                // C'est une matrice (2D)
                if (value is List<*>) {
                    val targetType = variableTypes[normalizedName]
                    @Suppress("UNCHECKED_CAST")
                    variables[normalizedName] = (value as List<List<Any>>).map { row ->
                        row.map { if (targetType != null) castToType(it, targetType) else it }.toMutableList()
                    }.toMutableList()
                } else {
                    runtimeError("Impossible d'affecter une valeur non-matrice à la matrice '${assignment.variable}'")
                }
            } else {
                // C'est un tableau (1D)
                if (value is List<*>) {
                    val targetType = variableTypes[normalizedName]
                    @Suppress("UNCHECKED_CAST")
                    variables[normalizedName] = (value as List<Any>).map {
                        if (targetType != null) castToType(it, targetType) else it
                    }.toMutableList()
                } else {
                    runtimeError("Impossible d'affecter une valeur non-tableau au tableau '${assignment.variable}'")
                }
            }
        } else {
            // C'est une variable simple
            val targetType = variableTypes[normalizedName]
            val castedValue = if (targetType != null) {
                castToType(value, targetType)
            } else {
                value
            }
            variables[normalizedName] = castedValue
        }
    }

    private fun executeArrayAssignment(assignment: ArrayAssignment) {
        val value = evaluateExpression(assignment.expression)
        val index = toInt(evaluateExpression(assignment.index))
        val normalizedName = normalize(assignment.arrayName)

        @Suppress("UNCHECKED_CAST")
        val array =
                variables[normalizedName] as? MutableList<Any>
                        ?: runtimeError(
                                "Variable '${assignment.arrayName}' n'est pas un tableau"
                        )

        if (index < 0 || index >= array.size) {
            runtimeError("Index $index hors limites pour le tableau '${assignment.arrayName}'")
        }

        // Déduire le type d'élément du tableau depuis le premier élément
        val elementType = when (array.firstOrNull()) {
            is Int -> "entier"
            is Double -> "reel"
            is String -> "chaine"
            is Char -> "caractere"
            is Boolean -> "booleen"
            else -> null
        }

        val castedValue = if (elementType != null) {
            castToType(value, elementType)
        } else {
            value
        }

        array[index] = castedValue
    }

    private fun executeMatrixAssignment(assignment: MatrixAssignment) {
        val value = evaluateExpression(assignment.expression)
        val index1 = toInt(evaluateExpression(assignment.index1))
        val index2 = toInt(evaluateExpression(assignment.index2))
        val normalizedName = normalize(assignment.matrixName)

        @Suppress("UNCHECKED_CAST")
        val matrix =
                variables[normalizedName] as? MutableList<MutableList<Any>>
                        ?: runtimeError(
                                "Variable '${assignment.matrixName}' n'est pas une matrice"
                        )

        if (index1 < 0 || index1 >= matrix.size) {
            runtimeError("Index ligne $index1 hors limites pour la matrice '${assignment.matrixName}'")
        }

        if (index2 < 0 || index2 >= matrix[index1].size) {
            runtimeError("Index colonne $index2 hors limites pour la matrice '${assignment.matrixName}'")
        }

        // Déduire le type d'élément de la matrice depuis le premier élément
        val elementType = when (matrix.firstOrNull()?.firstOrNull()) {
            is Int -> "entier"
            is Double -> "reel"
            is String -> "chaine"
            is Char -> "caractere"
            is Boolean -> "booleen"
            else -> null
        }

        val castedValue = if (elementType != null) {
            castToType(value, elementType)
        } else {
            value
        }

        matrix[index1][index2] = castedValue
    }

    private fun executeIfStatement(ifStatement: IfStatement) {
        val condition = evaluateExpression(ifStatement.condition)

        if (toBoolean(condition)) {
            // Execute the main "then" branch
            for (statement in ifStatement.thenBranch) {
                executeStatement(statement)
            }
        } else {
            // Check elseIf clauses (sinonSi)
            var executed = false
            for (elseIfClause in ifStatement.elseIfClauses) {
                val elseIfCondition = evaluateExpression(elseIfClause.condition)
                if (toBoolean(elseIfCondition)) {
                    for (statement in elseIfClause.thenBranch) {
                        executeStatement(statement)
                    }
                    executed = true
                    break
                }
            }

            // Execute else branch if no elseIf was executed
            if (!executed && ifStatement.elseBranch != null) {
                for (statement in ifStatement.elseBranch) {
                    executeStatement(statement)
                }
            }
        }
    }

    private fun executeForLoop(forLoop: ForLoop) {
        val start = evaluateExpression(forLoop.start)
        val end = evaluateExpression(forLoop.end)

        val startInt = toInt(start)
        val endInt = toInt(end)

        // Calculer le pas : expression optionnelle dans l'AST
        val stepValue = if (forLoop.step != null) {
            val sv = toInt(evaluateExpression(forLoop.step))
            if (sv == 0) runtimeError("Le pas de la boucle ne peut pas être 0")
            sv
        } else {
            // Par défaut : +1 si on monte, -1 si on descend
            if (startInt <= endInt) 1 else -1
        }

        var i = startInt
        if (stepValue > 0) {
            // Boucle ascendante
            while (i <= endInt) {
                variables[normalize(forLoop.variable)] = i
                for (statement in forLoop.body) {
                    executeStatement(statement)
                }
                i += stepValue
            }
        } else {
            // Boucle descendante
            while (i >= endInt) {
                variables[normalize(forLoop.variable)] = i
                for (statement in forLoop.body) {
                    executeStatement(statement)
                }
                i += stepValue // stepValue est négatif
            }
        }
    }

    private fun executeWhileLoop(whileLoop: WhileLoop) {
        while (toBoolean(evaluateExpression(whileLoop.condition))) {
            for (statement in whileLoop.body) {
                executeStatement(statement)
            }
        }
    }

    private fun executeRepeatUntilLoop(repeatLoop: RepeatUntilLoop) {
        do {
            for (statement in repeatLoop.body) {
                executeStatement(statement)
            }
        } while (!toBoolean(evaluateExpression(repeatLoop.condition)))
    }

    private fun executeWhenStatement(whenStatement: WhenStatement) {
        val expressionValue = evaluateExpression(whenStatement.expression)

        // Parcourir tous les cas
        for (case in whenStatement.cases) {
            val caseValue = evaluateExpression(case.value)

            // Comparer les valeurs
            if (valuesEqual(expressionValue, caseValue)) {
                // Exécuter les instructions de ce cas
                for (statement in case.statements) {
                    executeStatement(statement)
                }
                return // Sortir après le premier cas correspondant (comme un break automatique)
            }
        }

        // Si aucun cas ne correspond, exécuter le cas par défaut s'il existe
        if (whenStatement.defaultCase != null) {
            for (statement in whenStatement.defaultCase) {
                executeStatement(statement)
            }
        }
    }

    private fun valuesEqual(a: Any?, b: Any?): Boolean {
        return when {
            a == null && b == null -> true
            a == null || b == null -> false
            a is Number && b is Number -> a.toDouble() == b.toDouble()
            a is String && b is String -> a == b
            a is Char && b is Char -> a == b
            a is Boolean && b is Boolean -> a == b
            else -> a == b
        }
    }

    private fun executeWriteStatement(writeStatement: WriteStatement): Unit {
        val values = writeStatement.expressions.map { evaluateExpression(it) }
        val output = values.joinToString("") { formatValue(it) }
        if (writeStatement.newline) {
            println(output)
            System.out.flush()
        } else {
            print(output)
            System.out.flush()
        }
    }

    private fun executeReadStatement(readStatement: ReadStatement) {
        for (target in readStatement.targets) {
            when (target) {
                is SimpleReadTarget -> {
                    val normalizedName = normalize(target.name)
                    // Read input without automatic prompt
                    val input = readLine() ?: ""

                    // If variable exists and is a Char -> store first char
                    val existing = variables[normalizedName]
                    val value: Any = when {
                        existing is Char -> (if (input.isNotEmpty()) input[0] else '\u0000')
                        existing is MutableList<*> && (existing.getOrNull(0) is Char) ->
                            (if (input.isNotEmpty()) input[0] else '\u0000')
                        else -> input.toDoubleOrNull() ?: input
                    }

                    variables[normalizedName] = value
                }
                is ArrayReadTarget -> {
                    val index = toInt(evaluateExpression(target.index))
                    val normalizedName = normalize(target.arrayName)
                    // Read input without automatic prompt
                    val input = readLine() ?: ""

                    @Suppress("UNCHECKED_CAST")
                    val array =
                            variables[normalizedName] as? MutableList<Any>
                                    ?: runtimeError(
                                            "Variable '${target.arrayName}' n'est pas un tableau"
                                    )

                    if (index < 0 || index >= array.size) {
                        runtimeError(
                                "Index $index hors limites pour le tableau '${target.arrayName}'"
                        )
                    }

                    val value: Any = when {
                        (array.getOrNull(0) is Char) -> (if (input.isNotEmpty()) input[0] else '\u0000')
                        else -> input.toDoubleOrNull() ?: input
                    }

                    array[index] = value
                }
            }
        }
    }

    private fun executeFunctionCall(name: String, arguments: List<Expression>): Any? {
        // Check if it's a built-in function
        val normalizedName = normalize(name)
        val builtInResult = tryExecuteBuiltInFunction(normalizedName, arguments)
        if (builtInResult != null || isBuiltInFunction(normalizedName)) {
            return builtInResult
        }

        val function = functions[normalizedName] ?: runtimeError("Fonction '$name' non définie")

        // Save current variable context
        val savedVariables = variables.toMap()

        // Initialize function parameters
        for ((i, param) in function.parameters.withIndex()) {
            if (i < arguments.size) {
                val argValue = evaluateExpression(arguments[i])
                if (param.arraySize != null) {
                    variables[normalize(param.name)] = argValue
                } else {
                    variables[normalize(param.name)] = argValue
                }
            } else {
                initializeVariable(param)
            }
        }

        // Initialize function local variables
        for (varDecl in function.variables) {
            initializeVariable(varDecl)
        }

        // Execute function body
        var returnValue: Any? = null
        try {
            for (statement in function.body) {
                executeStatement(statement)
            }
        } catch (e: ReturnException) {
            returnValue = e.value
        }

        // Restore variable context
        variables.clear()
        variables.putAll(savedVariables)

        return returnValue
    }

    private fun isBuiltInFunction(name: String): Boolean {
        return name.lowercase() in
                listOf(
                        // Mathématiques de base
                        "abs",
                        "racine",
                        "puissance",
                        "arrondi",
                        "plancher",
                        "plafond",
                        "min",
                        "max",
                        "reste",
                        "quotient",
                        // Trigonométrie
                        "sin",
                        "cos",
                        "tan",
                        "log",
                        "exp",
                        // Chaînes
                        "longueur",
                        "majuscule",
                        "minuscule",
                        "sousChaine",
                        "souschaine",
                        "estVide",
                        "estvide",
                        "contient",
                        "commence",
                        "termine",
                        "position",
                        // Caractères
                        "ord",
                        "chr",
                        "estLettre",
                        "estlettre",
                        "estChiffre",
                        "estchiffre",
                        "estMajuscule",
                        "estmajuscule",
                        "estMinuscule",
                        "estminuscule",
                        "versMajuscule",
                        "versmajuscule",
                        "versMinuscule",
                        "versminuscule",
                        // Conversions
                        "versEntier",
                        "versentier",
                        "versReel",
                        "versreel",
                        "versChaine",
                        "verschaine",
                        // Aléatoire
                        "aleatoire"
                )
    }

    private fun tryExecuteBuiltInFunction(name: String, arguments: List<Expression>): Any? {
        val nameLower = name.lowercase()
        val args = arguments.map { evaluateExpression(it) }

        return when (nameLower) {
            // Fonctions mathématiques
            "abs" -> {
                if (args.isEmpty()) runtimeError("abs() nécessite 1 argument")
                abs(toDouble(args[0]))
            }
            "racine" -> {
                if (args.isEmpty()) runtimeError("racine() nécessite 1 argument")
                sqrt(toDouble(args[0]))
            }
            "puissance" -> {
                if (args.size < 2) runtimeError("puissance() nécessite 2 arguments")
                toDouble(args[0]).pow(toDouble(args[1]))
            }
            "arrondi" -> {
                if (args.isEmpty()) runtimeError("arrondi() nécessite 1 argument")
                round(toDouble(args[0])).toInt()
            }
            "plancher" -> {
                if (args.isEmpty()) runtimeError("plancher() nécessite 1 argument")
                floor(toDouble(args[0])).toInt()
            }
            "plafond" -> {
                if (args.isEmpty()) runtimeError("plafond() nécessite 1 argument")
                ceil(toDouble(args[0])).toInt()
            }
            "min" -> {
                if (args.size < 2) runtimeError("min() nécessite 2 arguments")
                min(toDouble(args[0]), toDouble(args[1]))
            }
            "max" -> {
                if (args.size < 2) runtimeError("max() nécessite 2 arguments")
                max(toDouble(args[0]), toDouble(args[1]))
            }
            "reste" -> {
                if (args.size < 2) runtimeError("reste() nécessite 2 arguments")
                toInt(args[0]) % toInt(args[1])
            }
            "quotient" -> {
                if (args.size < 2) runtimeError("quotient() nécessite 2 arguments")
                toInt(args[0]) / toInt(args[1])
            }
            "sin" -> {
                if (args.isEmpty()) runtimeError("sin() nécessite 1 argument")
                sin(toDouble(args[0]))
            }
            "cos" -> {
                if (args.isEmpty()) runtimeError("cos() nécessite 1 argument")
                cos(toDouble(args[0]))
            }
            "tan" -> {
                if (args.isEmpty()) runtimeError("tan() nécessite 1 argument")
                tan(toDouble(args[0]))
            }
            "log" -> {
                if (args.isEmpty()) runtimeError("log() nécessite 1 argument")
                ln(toDouble(args[0]))
            }
            "exp" -> {
                if (args.isEmpty()) runtimeError("exp() nécessite 1 argument")
                exp(toDouble(args[0]))
            }

            // Fonctions de chaînes
            "longueur" -> {
                if (args.isEmpty()) runtimeError("longueur() nécessite 1 argument")
                args[0].toString().length
            }
            "majuscule" -> {
                if (args.isEmpty()) runtimeError("majuscule() nécessite 1 argument")
                args[0].toString().uppercase()
            }
            "minuscule" -> {
                if (args.isEmpty()) runtimeError("minuscule() nécessite 1 argument")
                args[0].toString().lowercase()
            }
            "souschaine", "sousChaine" -> {
                if (args.size < 3)
                        runtimeError("sousChaine() nécessite 3 arguments (chaine, debut, fin)")
                val str = args[0].toString()
                val debut = toInt(args[1])
                val fin = toInt(args[2])
                if (debut < 0 || fin > str.length || debut > fin) {
                    runtimeError("Indices invalides pour sousChaine")
                }
                str.substring(debut, fin)
            }
            "estvide", "estVide" -> {
                if (args.isEmpty()) runtimeError("estVide() nécessite 1 argument")
                args[0].toString().isEmpty()
            }
            "contient" -> {
                if (args.size < 2) runtimeError("contient() nécessite 2 arguments (chaine, sous_chaine)")
                args[0].toString().contains(args[1].toString())
            }
            "commence" -> {
                if (args.size < 2) runtimeError("commence() nécessite 2 arguments (chaine, prefixe)")
                args[0].toString().startsWith(args[1].toString())
            }
            "termine" -> {
                if (args.size < 2) runtimeError("termine() nécessite 2 arguments (chaine, suffixe)")
                args[0].toString().endsWith(args[1].toString())
            }
            "position" -> {
                if (args.size < 2) runtimeError("position() nécessite 2 arguments (chaine, sous_chaine)")
                args[0].toString().indexOf(args[1].toString())
            }

            // Fonction aléatoire
            "aleatoire" -> {
                if (args.isEmpty()) {
                    // Retourne un nombre entre 0.0 et 1.0
                    kotlin.random.Random.nextDouble()
                } else if (args.size == 1) {
                    // Retourne un entier entre 0 et max-1
                    kotlin.random.Random.nextInt(toInt(args[0]))
                } else {
                    // Retourne un entier entre min et max-1
                    val min = toInt(args[0])
                    val max = toInt(args[1])
                    kotlin.random.Random.nextInt(min, max)
                }
            }

            // Fonctions de caractères
            "ord" -> {
                if (args.isEmpty()) runtimeError("ord() nécessite 1 argument (un caractère)")
                val value = args[0]
                when (value) {
                    is Char -> value.code
                    is String -> if (value.isNotEmpty()) value[0].code else 0
                    else -> runtimeError("ord() nécessite un caractère en argument")
                }
            }
            "chr" -> {
                if (args.isEmpty()) runtimeError("chr() nécessite 1 argument (un code ASCII)")
                val code = toInt(args[0])
                // Accepter la plage complète des code points représentables par un Char Kotlin
                if (code < 0 || code > Char.MAX_VALUE.code) {
                    runtimeError("chr() : le code doit être entre 0 et ${Char.MAX_VALUE.code}")
                }
                code.toChar()
            }
            "estlettre", "estLettre" -> {
                if (args.isEmpty()) runtimeError("estLettre() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.isLetter()
                    is String -> if (value.isNotEmpty()) value[0].isLetter() else false
                    else -> false
                }
            }
            "estchiffre", "estChiffre" -> {
                if (args.isEmpty()) runtimeError("estChiffre() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.isDigit()
                    is String -> if (value.isNotEmpty()) value[0].isDigit() else false
                    else -> false
                }
            }
            "estmajuscule", "estMajuscule" -> {
                if (args.isEmpty()) runtimeError("estMajuscule() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.isUpperCase()
                    is String -> if (value.isNotEmpty()) value[0].isUpperCase() else false
                    else -> false
                }
            }
            "estminuscule", "estMinuscule" -> {
                if (args.isEmpty()) runtimeError("estMinuscule() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.isLowerCase()
                    is String -> if (value.isNotEmpty()) value[0].isLowerCase() else false
                    else -> false
                }
            }
            "versmajuscule", "versMajuscule" -> {
                if (args.isEmpty()) runtimeError("versMajuscule() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.uppercaseChar()
                    is String -> if (value.isNotEmpty()) value[0].uppercaseChar() else '\u0000'
                    else -> runtimeError("versMajuscule() nécessite un caractère")
                }
            }
            "versminuscule", "versMinuscule" -> {
                if (args.isEmpty()) runtimeError("versMinuscule() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.lowercaseChar()
                    is String -> if (value.isNotEmpty()) value[0].lowercaseChar() else '\u0000'
                    else -> runtimeError("versMinuscule() nécessite un caractère")
                }
            }

            // Fonctions de conversion
            "versentier", "versEntier" -> {
                if (args.isEmpty()) runtimeError("versEntier() nécessite 1 argument")
                toInt(args[0])
            }
            "versreel", "versReel" -> {
                if (args.isEmpty()) runtimeError("versReel() nécessite 1 argument")
                toDouble(args[0])
            }
            "verschaine", "versChaine" -> {
                if (args.isEmpty()) runtimeError("versChaine() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Double -> {
                        if (value % 1.0 == 0.0) {
                            value.toInt().toString()
                        } else {
                            value.toString()
                        }
                    }
                    else -> value.toString()
                }
            }
            else -> null
        }
    }

    private fun evaluateExpression(expression: Expression): Any {
        // Mettre à jour le numéro de ligne si l'expression en a un
        if (expression.line > 0) {
            currentLine = expression.line
        }

        return when (expression) {
            is NumberLiteral -> expression.value
            is StringLiteral -> expression.value
            is CharLiteral -> expression.value
            is BooleanLiteral -> expression.value
            is Variable -> variables[normalize(expression.name)]
                            ?: runtimeError("Variable '${expression.name}' non définie")
            is ArrayAccess -> {
                val normalizedName = normalize(expression.name)
                @Suppress("UNCHECKED_CAST")
                val array =
                        variables[normalizedName] as? List<Any>
                                ?: runtimeError(
                                        "Variable '${expression.name}' n'est pas un tableau"
                                )
                val index = toInt(evaluateExpression(expression.index))
                if (index < 0 || index >= array.size) {
                    runtimeError(
                            "Index $index hors limites pour le tableau '${expression.name}'"
                    )
                }
                array[index]
            }
            is MatrixAccess -> {
                val normalizedName = normalize(expression.name)
                @Suppress("UNCHECKED_CAST")
                val matrix =
                        variables[normalizedName] as? List<List<Any>>
                                ?: runtimeError(
                                        "Variable '${expression.name}' n'est pas une matrice"
                                )
                val index1 = toInt(evaluateExpression(expression.index1))
                val index2 = toInt(evaluateExpression(expression.index2))

                if (index1 < 0 || index1 >= matrix.size) {
                    runtimeError(
                            "Index ligne $index1 hors limites pour la matrice '${expression.name}'"
                    )
                }
                if (index2 < 0 || index2 >= matrix[index1].size) {
                    runtimeError(
                            "Index colonne $index2 hors limites pour la matrice '${expression.name}'"
                    )
                }
                matrix[index1][index2]
            }
            is FunctionCallExpression -> {
                executeFunctionCall(expression.name, expression.arguments)
                        ?: runtimeError(
                                "La fonction '${expression.name}' ne retourne pas de valeur"
                        )
            }
            is BinaryOp -> evaluateBinaryOp(expression)
            is UnaryOp -> evaluateUnaryOp(expression)
            is ConditionalExpression -> {
                val condition = evaluateExpression(expression.condition)
                if (toBoolean(condition)) {
                    evaluateExpression(expression.thenValue)
                } else {
                    evaluateExpression(expression.elseValue)
                }
            }
            is ArrayLiteral -> {
                // Evaluate all elements and return as MutableList
                expression.elements.map { evaluateExpression(it) }.toMutableList()
            }
        }
    }

    private fun evaluateBinaryOp(binaryOp: BinaryOp): Any {
        val left = evaluateExpression(binaryOp.left)
        val right = evaluateExpression(binaryOp.right)

        return when (binaryOp.operator) {
            "+" -> {
                if (left is String || right is String) {
                    left.toString() + right.toString()
                } else {
                    toDouble(left) + toDouble(right)
                }
            }
            "-" -> toDouble(left) - toDouble(right)
            "*" -> toDouble(left) * toDouble(right)
            "/" -> toDouble(left) / toDouble(right)
            "**", "^" -> toDouble(left).pow(toDouble(right))
            "div" -> toInt(left) / toInt(right)
            "mod", "%" -> toInt(left) % toInt(right)
            "==" -> compareEquals(left, right)
            "<>" -> !compareEquals(left, right)
            "!=" -> !compareEquals(left, right)
            "<" -> compareLess(left, right)
            ">" -> compareLess(right, left)
            "<=" -> !compareLess(right, left)
            ">=" -> !compareLess(left, right)
            "et" -> toBoolean(left) && toBoolean(right)
            "ou" -> toBoolean(left) || toBoolean(right)
            else -> runtimeError("Opérateur invalide: ${binaryOp.operator}")
        }
    }

    private fun compareEquals(left: Any, right: Any): Boolean {
        return when {
            // Même type : comparaison directe
            left::class == right::class -> left == right

            // Nombres : comparer en tant que nombres
            isNumber(left) && isNumber(right) -> {
                toDouble(left) == toDouble(right)
            }

            // Nombre vs chaîne numérique : essayer comparaison numérique
            isNumber(left) && right is String -> {
                val numRight = right.toDoubleOrNull()
                if (numRight != null) {
                    toDouble(left) == numRight
                } else {
                    false
                }
            }
            left is String && isNumber(right) -> {
                val numLeft = left.toDoubleOrNull()
                if (numLeft != null) {
                    numLeft == toDouble(right)
                } else {
                    false
                }
            }

            // Caractères et chaînes
            left is Char && right is String -> {
                if (right.length == 1) left == right[0]
                else left.toString() == right
            }
            left is String && right is Char -> {
                if (left.length == 1) left[0] == right
                else left == right.toString()
            }

            // Booléens : comparaison stricte
            left is Boolean && right is Boolean -> left == right

            // Autres cas : pas égaux (typage strict)
            else -> false
        }
    }

    private fun compareLess(left: Any, right: Any): Boolean {
        return when {
            // Nombres : comparaison numérique
            isNumber(left) && isNumber(right) -> {
                toDouble(left) < toDouble(right)
            }

            // Chaînes : comparaison lexicographique
            left is String && right is String -> left < right

            // Caractères : comparaison par code
            left is Char && right is Char -> left < right

            // Caractère et chaîne
            left is Char && right is String -> left.toString() < right
            left is String && right is Char -> left < right.toString()

            // Booléens : false < true
            left is Boolean && right is Boolean -> !left && right

            // Autres cas : convertir en nombre
            else -> toDouble(left) < toDouble(right)
        }
    }

    private fun isNumber(value: Any): Boolean {
        return value is Int || value is Double || value is Float || value is Long
    }

    /**
     * Convertit une valeur vers le type cible avec casting implicite
     * Règles de conversion strictes :
     * - entier -> reel : conversion automatique ✅
     * - caractere -> chaine : conversion automatique ✅
     * - caractere -> entier : ord(c) - code ASCII ✅
     * - entier -> caractere : chr(n) - si dans les limites ✅
     * - reel -> entier : troncature ✅
     *
     * Conversions INTERDITES (génèrent une erreur) :
     * - chaine -> caractere ❌
     * - entier -> chaine ❌
     * - reel -> chaine ❌
     * - booleen -> chaine ❌
     * - chaine -> entier ❌
     * - chaine -> reel ❌
     */
    private fun castToType(value: Any, targetType: String): Any {
        val normalizedTargetType = normalize(targetType)

        // Fonction helper pour obtenir le nom du type d'une valeur
        fun getTypeName(v: Any): String {
            return when (v) {
                is Int -> "entier"
                is Double -> "reel"
                is String -> "chaine"
                is Char -> "caractere"
                is Boolean -> "booleen"
                else -> "inconnu"
            }
        }

        return when (normalizedTargetType) {
            "entier" -> {
                when (value) {
                    is Int -> value
                    is Double -> value.toInt() // Troncature autorisée
                    is Char -> value.code // ord(c) autorisé
                    is String -> {
                        // ❌ INTERDIT : chaine -> entier
                        runtimeError("Erreur de type : Impossible de convertir une chaine en entier. Utilisez versEntier() pour une conversion explicite.")
                    }
                    is Boolean -> if (value) 1 else 0 // Autorisé pour les tests
                    else -> toInt(value)
                }
            }
            "reel" -> {
                when (value) {
                    is Double -> value
                    is Int -> value.toDouble() // ✅ Casting implicite entier -> reel
                    is Char -> value.code.toDouble() // ord(c) autorisé
                    is String -> {
                        // ❌ INTERDIT : chaine -> reel
                        runtimeError("Erreur de type : Impossible de convertir une chaine en reel. Utilisez versReel() pour une conversion explicite.")
                    }
                    is Boolean -> if (value) 1.0 else 0.0 // Autorisé pour les tests
                    else -> toDouble(value)
                }
            }
            "chaine" -> {
                when (value) {
                    is String -> value
                    is Char -> value.toString() // ✅ Casting implicite caractere -> chaine
                    is Int -> {
                        // ❌ INTERDIT : entier -> chaine
                        runtimeError("Erreur de type : Impossible de convertir un entier en chaine. Utilisez versChaine() pour une conversion explicite.")
                    }
                    is Double -> {
                        // ❌ INTERDIT : reel -> chaine
                        runtimeError("Erreur de type : Impossible de convertir un reel en chaine. Utilisez versChaine() pour une conversion explicite.")
                    }
                    is Boolean -> {
                        // ❌ INTERDIT : booleen -> chaine
                        runtimeError("Erreur de type : Impossible de convertir un booleen en chaine. Utilisez versChaine() pour une conversion explicite.")
                    }
                    else -> runtimeError("Erreur de type : Conversion vers chaine non supportée pour le type ${getTypeName(value)}")
                }
            }
            "caractere" -> {
                when (value) {
                    is Char -> value
                    is String -> {
                        // ❌ INTERDIT : chaine -> caractere
                        runtimeError("Erreur de type : Impossible de convertir une chaine en caractere. La chaine peut contenir plusieurs caractères.")
                    }
                    is Int -> {
                        // ✅ chr(n) autorisé - vérifier les limites
                        if (value in 0..Char.MAX_VALUE.code) {
                            value.toChar()
                        } else {
                            runtimeError("Valeur $value hors limites pour un caractère (0-${Char.MAX_VALUE.code})")
                        }
                    }
                    is Double -> {
                        val intValue = value.toInt()
                        if (intValue in 0..Char.MAX_VALUE.code) {
                            intValue.toChar()
                        } else {
                            runtimeError("Valeur $intValue hors limites pour un caractère")
                        }
                    }
                    else -> runtimeError("Erreur de type : Conversion vers caractere non supportée pour le type ${getTypeName(value)}")
                }
            }
            "booleen" -> {
                when (value) {
                    is Boolean -> value
                    is Int -> value != 0
                    is Double -> value != 0.0
                    is String -> {
                        // Pour les booléens, on peut être plus permissif pour les valeurs littérales
                        when (value.lowercase()) {
                            "vrai", "true" -> true
                            "faux", "false" -> false
                            else -> runtimeError("Erreur de type : Impossible de convertir la chaine '$value' en booleen. Valeurs autorisées: 'vrai', 'faux'")
                        }
                    }
                    is Char -> value != '\u0000'
                    else -> toBoolean(value)
                }
            }
            else -> value // Type inconnu, garder la valeur telle quelle
        }
    }

    private fun evaluateUnaryOp(unaryOp: UnaryOp): Any {
        val operand = evaluateExpression(unaryOp.operand)

        return when (unaryOp.operator) {
            "-" -> -toDouble(operand)
            "non" -> !toBoolean(operand)
            else -> runtimeError("Opérateur unaire invalide: ${unaryOp.operator}")
        }
    }

    private fun toDouble(value: Any): Double {
        return when (value) {
            is Double -> value
            is Int -> value.toDouble()
            is Char -> value.code.toDouble()
            is String -> value.toDoubleOrNull() ?: 0.0
            is Boolean -> if (value) 1.0 else 0.0
            else -> 0.0
        }
    }

    private fun toInt(value: Any): Int {
        return when (value) {
            is Int -> value
            is Double -> value.toInt()
            is Char -> value.code
            is String -> value.toIntOrNull() ?: 0
            is Boolean -> if (value) 1 else 0
            else -> 0
        }
    }

    private fun toBoolean(value: Any): Boolean {
        return when (value) {
            is Boolean -> value
            is Double -> value != 0.0
            is Int -> value != 0
            is Char -> value != '\u0000'
            is String -> value.isNotEmpty()
            else -> false
        }
    }

    private fun formatValue(value: Any): String {
        return when (value) {
            is Double -> {
                if (value % 1.0 == 0.0) {
                    value.toInt().toString()
                } else {
                    value.toString()
                }
            }
            is Char -> {
                // Ne pas afficher le caractère nul par défaut (initialisation)
                if (value == '\u0000') "" else value.toString()
            }
            is MutableList<*> -> {
                // Affichage des tableaux au format [elem1, elem2, ...]
                "[" + value.joinToString(", ") { elem ->
                    when (elem) {
                        is MutableList<*> -> formatValue(elem) // Tableau 2D (matrice)
                        else -> formatValue(elem ?: "null")
                    }
                } + "]"
            }
            else -> value.toString()
        }
    }
}
