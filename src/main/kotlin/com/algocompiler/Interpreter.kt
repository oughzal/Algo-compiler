package com.algocompiler

import kotlin.math.*

class ReturnException(val value: Any?) : Exception()

class Interpreter {
    private val variables = mutableMapOf<String, Any>()
    private val constants = mutableSetOf<String>()
    private val functions = mutableMapOf<String, FunctionDeclaration>()

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
        if (varDecl.arraySize != null) {
            // Initialize array
            val defaultValue =
                    when (normalize(varDecl.type)) {
                        "entier" -> 0
                        "reel" -> 0.0
                        "chaine" -> ""
                        "caractere" -> '\u0000'
                        "booleen" -> false
                        else -> 0
                    }
            variables[normalizedName] = MutableList(varDecl.arraySize) { defaultValue }
        } else {
            // Initialize simple variable
            variables[normalizedName] =
                    when (normalize(varDecl.type)) {
                        "entier" -> 0
                        "reel" -> 0.0
                        "chaine" -> ""
                        "caractere" -> '\u0000'
                        "booleen" -> false
                        else -> 0
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
        when (statement) {
            is Assignment -> executeAssignment(statement)
            is ArrayAssignment -> executeArrayAssignment(statement)
            is IfStatement -> executeIfStatement(statement)
            is ForLoop -> executeForLoop(statement)
            is WhileLoop -> executeWhileLoop(statement)
            is RepeatUntilLoop -> executeRepeatUntilLoop(statement)
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
            throw Exception("Impossible de modifier la constante '${assignment.variable}'")
        }
        val value = evaluateExpression(assignment.expression)
        variables[normalizedName] = value
    }

    private fun executeArrayAssignment(assignment: ArrayAssignment) {
        val value = evaluateExpression(assignment.expression)
        val index = toInt(evaluateExpression(assignment.index))
        val normalizedName = normalize(assignment.arrayName)

        @Suppress("UNCHECKED_CAST")
        val array =
                variables[normalizedName] as? MutableList<Any>
                        ?: throw Exception(
                                "Variable '${assignment.arrayName}' n'est pas un tableau"
                        )

        if (index < 0 || index >= array.size) {
            throw Exception("Index $index hors limites pour le tableau '${assignment.arrayName}'")
        }

        array[index] = value
    }

    private fun executeIfStatement(ifStatement: IfStatement) {
        val condition = evaluateExpression(ifStatement.condition)

        if (toBoolean(condition)) {
            for (statement in ifStatement.thenBranch) {
                executeStatement(statement)
            }
        } else if (ifStatement.elseBranch != null) {
            for (statement in ifStatement.elseBranch) {
                executeStatement(statement)
            }
        }
    }

    private fun executeForLoop(forLoop: ForLoop) {
        val start = evaluateExpression(forLoop.start)
        val end = evaluateExpression(forLoop.end)

        val startInt = toInt(start)
        val endInt = toInt(end)

        for (i in startInt..endInt) {
            variables[normalize(forLoop.variable)] = i
            for (statement in forLoop.body) {
                executeStatement(statement)
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

    private fun executeWriteStatement(writeStatement: WriteStatement): Unit {
        val values = writeStatement.expressions.map { evaluateExpression(it) }
        val output = values.joinToString("") { formatValue(it) }
        if (writeStatement.newline) {
            println(output)
        } else {
            print(output)
        }
        // Ne rien retourner explicitement
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
                                    ?: throw Exception(
                                            "Variable '${target.arrayName}' n'est pas un tableau"
                                    )

                    if (index < 0 || index >= array.size) {
                        throw Exception(
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

        val function = functions[normalizedName] ?: throw Exception("Fonction '$name' non définie")

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
                if (args.isEmpty()) throw Exception("abs() nécessite 1 argument")
                abs(toDouble(args[0]))
            }
            "racine" -> {
                if (args.isEmpty()) throw Exception("racine() nécessite 1 argument")
                sqrt(toDouble(args[0]))
            }
            "puissance" -> {
                if (args.size < 2) throw Exception("puissance() nécessite 2 arguments")
                toDouble(args[0]).pow(toDouble(args[1]))
            }
            "arrondi" -> {
                if (args.isEmpty()) throw Exception("arrondi() nécessite 1 argument")
                round(toDouble(args[0])).toInt()
            }
            "plancher" -> {
                if (args.isEmpty()) throw Exception("plancher() nécessite 1 argument")
                floor(toDouble(args[0])).toInt()
            }
            "plafond" -> {
                if (args.isEmpty()) throw Exception("plafond() nécessite 1 argument")
                ceil(toDouble(args[0])).toInt()
            }
            "min" -> {
                if (args.size < 2) throw Exception("min() nécessite 2 arguments")
                min(toDouble(args[0]), toDouble(args[1]))
            }
            "max" -> {
                if (args.size < 2) throw Exception("max() nécessite 2 arguments")
                max(toDouble(args[0]), toDouble(args[1]))
            }
            "reste" -> {
                if (args.size < 2) throw Exception("reste() nécessite 2 arguments")
                toInt(args[0]) % toInt(args[1])
            }
            "quotient" -> {
                if (args.size < 2) throw Exception("quotient() nécessite 2 arguments")
                toInt(args[0]) / toInt(args[1])
            }
            "sin" -> {
                if (args.isEmpty()) throw Exception("sin() nécessite 1 argument")
                sin(toDouble(args[0]))
            }
            "cos" -> {
                if (args.isEmpty()) throw Exception("cos() nécessite 1 argument")
                cos(toDouble(args[0]))
            }
            "tan" -> {
                if (args.isEmpty()) throw Exception("tan() nécessite 1 argument")
                tan(toDouble(args[0]))
            }
            "log" -> {
                if (args.isEmpty()) throw Exception("log() nécessite 1 argument")
                ln(toDouble(args[0]))
            }
            "exp" -> {
                if (args.isEmpty()) throw Exception("exp() nécessite 1 argument")
                exp(toDouble(args[0]))
            }

            // Fonctions de chaînes
            "longueur" -> {
                if (args.isEmpty()) throw Exception("longueur() nécessite 1 argument")
                args[0].toString().length
            }
            "majuscule" -> {
                if (args.isEmpty()) throw Exception("majuscule() nécessite 1 argument")
                args[0].toString().uppercase()
            }
            "minuscule" -> {
                if (args.isEmpty()) throw Exception("minuscule() nécessite 1 argument")
                args[0].toString().lowercase()
            }
            "souschaine", "sousChaine" -> {
                if (args.size < 3)
                        throw Exception("sousChaine() nécessite 3 arguments (chaine, debut, fin)")
                val str = args[0].toString()
                val debut = toInt(args[1])
                val fin = toInt(args[2])
                if (debut < 0 || fin > str.length || debut > fin) {
                    throw Exception("Indices invalides pour sousChaine")
                }
                str.substring(debut, fin)
            }
            "estvide", "estVide" -> {
                if (args.isEmpty()) throw Exception("estVide() nécessite 1 argument")
                args[0].toString().isEmpty()
            }
            "contient" -> {
                if (args.size < 2) throw Exception("contient() nécessite 2 arguments (chaine, sous_chaine)")
                args[0].toString().contains(args[1].toString())
            }
            "commence" -> {
                if (args.size < 2) throw Exception("commence() nécessite 2 arguments (chaine, prefixe)")
                args[0].toString().startsWith(args[1].toString())
            }
            "termine" -> {
                if (args.size < 2) throw Exception("termine() nécessite 2 arguments (chaine, suffixe)")
                args[0].toString().endsWith(args[1].toString())
            }
            "position" -> {
                if (args.size < 2) throw Exception("position() nécessite 2 arguments (chaine, sous_chaine)")
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
                if (args.isEmpty()) throw Exception("ord() nécessite 1 argument (un caractère)")
                val value = args[0]
                when (value) {
                    is Char -> value.code
                    is String -> if (value.isNotEmpty()) value[0].code else 0
                    else -> throw Exception("ord() nécessite un caractère en argument")
                }
            }
            "chr" -> {
                if (args.isEmpty()) throw Exception("chr() nécessite 1 argument (un code ASCII)")
                val code = toInt(args[0])
                if (code < 0 || code > 127) {
                    throw Exception("chr() : le code ASCII doit être entre 0 et 127")
                }
                code.toChar()
            }
            "estlettre", "estLettre" -> {
                if (args.isEmpty()) throw Exception("estLettre() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.isLetter()
                    is String -> if (value.isNotEmpty()) value[0].isLetter() else false
                    else -> false
                }
            }
            "estchiffre", "estChiffre" -> {
                if (args.isEmpty()) throw Exception("estChiffre() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.isDigit()
                    is String -> if (value.isNotEmpty()) value[0].isDigit() else false
                    else -> false
                }
            }
            "estmajuscule", "estMajuscule" -> {
                if (args.isEmpty()) throw Exception("estMajuscule() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.isUpperCase()
                    is String -> if (value.isNotEmpty()) value[0].isUpperCase() else false
                    else -> false
                }
            }
            "estminuscule", "estMinuscule" -> {
                if (args.isEmpty()) throw Exception("estMinuscule() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.isLowerCase()
                    is String -> if (value.isNotEmpty()) value[0].isLowerCase() else false
                    else -> false
                }
            }
            "versmajuscule", "versMajuscule" -> {
                if (args.isEmpty()) throw Exception("versMajuscule() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.uppercaseChar()
                    is String -> if (value.isNotEmpty()) value[0].uppercaseChar() else '\u0000'
                    else -> throw Exception("versMajuscule() nécessite un caractère")
                }
            }
            "versminuscule", "versMinuscule" -> {
                if (args.isEmpty()) throw Exception("versMinuscule() nécessite 1 argument")
                val value = args[0]
                when (value) {
                    is Char -> value.lowercaseChar()
                    is String -> if (value.isNotEmpty()) value[0].lowercaseChar() else '\u0000'
                    else -> throw Exception("versMinuscule() nécessite un caractère")
                }
            }

            // Fonctions de conversion
            "versentier", "versEntier" -> {
                if (args.isEmpty()) throw Exception("versEntier() nécessite 1 argument")
                toInt(args[0])
            }
            "versreel", "versReel" -> {
                if (args.isEmpty()) throw Exception("versReel() nécessite 1 argument")
                toDouble(args[0])
            }
            "verschaine", "versChaine" -> {
                if (args.isEmpty()) throw Exception("versChaine() nécessite 1 argument")
                args[0].toString()
            }
            else -> null
        }
    }

    private fun evaluateExpression(expression: Expression): Any {
        return when (expression) {
            is NumberLiteral -> expression.value
            is StringLiteral -> expression.value
            is CharLiteral -> expression.value
            is BooleanLiteral -> expression.value
            is Variable -> variables[normalize(expression.name)]
                            ?: throw Exception("Variable '${expression.name}' non définie")
            is ArrayAccess -> {
                val normalizedName = normalize(expression.name)
                @Suppress("UNCHECKED_CAST")
                val array =
                        variables[normalizedName] as? List<Any>
                                ?: throw Exception(
                                        "Variable '${expression.name}' n'est pas un tableau"
                                )
                val index = toInt(evaluateExpression(expression.index))
                if (index < 0 || index >= array.size) {
                    throw Exception(
                            "Index $index hors limites pour le tableau '${expression.name}'"
                    )
                }
                array[index]
            }
            is FunctionCallExpression -> {
                executeFunctionCall(expression.name, expression.arguments)
                        ?: throw Exception(
                                "La fonction '${expression.name}' ne retourne pas de valeur"
                        )
            }
            is BinaryOp -> evaluateBinaryOp(expression)
            is UnaryOp -> evaluateUnaryOp(expression)
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
            "div" -> toInt(left) / toInt(right)
            "mod", "%" -> toInt(left) % toInt(right)
            "==" -> left == right || toDouble(left) == toDouble(right)
            "<>" -> left != right && toDouble(left) != toDouble(right)
            "!=" -> left != right && toDouble(left) != toDouble(right)
            "<" -> toDouble(left) < toDouble(right)
            ">" -> toDouble(left) > toDouble(right)
            "<=" -> toDouble(left) <= toDouble(right)
            ">=" -> toDouble(left) >= toDouble(right)
            "et" -> toBoolean(left) && toBoolean(right)
            "ou" -> toBoolean(left) || toBoolean(right)
            else -> throw Exception("Opérateur invalide: ${binaryOp.operator}")
        }
    }

    private fun evaluateUnaryOp(unaryOp: UnaryOp): Any {
        val operand = evaluateExpression(unaryOp.operand)

        return when (unaryOp.operator) {
            "-" -> -toDouble(operand)
            "non" -> !toBoolean(operand)
            else -> throw Exception("Opérateur unaire invalide: ${unaryOp.operator}")
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
            is Char -> value.toString()
            else -> value.toString()
        }
    }
}
