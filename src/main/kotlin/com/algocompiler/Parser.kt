package com.algocompiler

import com.algocompiler.ParseException

class Parser(private val tokens: List<Token>) {
    private var pos = 0

    private fun current(): Token = tokens.getOrNull(pos) ?: Token(TokenType.EOF, "", -1, -1)

    private fun advance() {
        if (pos < tokens.size) pos++
    }

    // Helper pour lancer une erreur de parsing avec infos (ligne, token)
    private fun error(message: String, token: Token? = null): Nothing {
        val t = token ?: current()
        val tokenInfo = if (t.value.isNotEmpty()) t.value else "EOF"
        throw com.algocompiler.ParseException("Erreur de parsing : $message (ligne ${t.line}, token='$tokenInfo')", t.line, t)
    }

    private fun expect(type: TokenType): Token {
        val token = current()
        if (token.type != type) {
            error("Attendu $type, mais trouvé ${token.type}")
        }
        advance()
        return token
    }

    private fun skipNewlines() {
        while (current().type == TokenType.NEWLINE) {
            advance()
        }
    }

    fun parse(): Program {
        skipNewlines()
        expect(TokenType.ALGORITHME)
        val name = expect(TokenType.IDENTIFICATEUR).value
        skipNewlines()

        val variables = mutableListOf<VariableDeclaration>()
        if (current().type == TokenType.VARIABLES) {
            advance()
            skipNewlines()
            variables.addAll(parseVariableDeclarations())
        }

        skipNewlines()

        val constants = mutableListOf<ConstantDeclaration>()
        if (current().type == TokenType.CONSTANTES) {
            advance()
            skipNewlines()
            constants.addAll(parseConstantDeclarations())
        }

        skipNewlines()

        val functions = mutableListOf<FunctionDeclaration>()
        while (current().type == TokenType.FONCTION || current().type == TokenType.PROCEDURE) {
            functions.add(parseFunctionDeclaration())
            skipNewlines()
        }

        // Check if there are more variables after functions
        if (current().type == TokenType.VARIABLES) {
            advance()
            skipNewlines()
            variables.addAll(parseVariableDeclarations())
        }

        skipNewlines()
        expect(TokenType.DEBUT)
        skipNewlines()

        val statements = mutableListOf<Statement>()
        while (current().type != TokenType.FIN && current().type != TokenType.EOF) {
            skipNewlines()
            if (current().type == TokenType.FIN) break
            val stmt = parseStatement()
            statements.add(stmt)
            skipNewlines()
        }

        expect(TokenType.FIN)

        return Program(name, variables, constants, functions, statements)
    }

    private fun parseVariableDeclarations(): List<VariableDeclaration> {
        val declarations = mutableListOf<VariableDeclaration>()

        while (current().type == TokenType.IDENTIFICATEUR) {
            val names = mutableListOf<String>()
            names.add(current().value)
            advance()

            while (current().type == TokenType.VIRGULE) {
                advance()
                names.add(expect(TokenType.IDENTIFICATEUR).value)
            }

            expect(TokenType.DEUX_POINTS)

            var isArray = false
            var arraySize: Int? = null
            var arraySize2: Int? = null  // Pour les matrices

            if (current().type == TokenType.TABLEAU) {
                isArray = true
                advance()
                expect(TokenType.CROCHET_GAUCHE)
                arraySize = expect(TokenType.NOMBRE).value.toInt()
                expect(TokenType.CROCHET_DROIT)

                // Vérifier s'il y a une deuxième dimension (matrice)
                if (current().type == TokenType.CROCHET_GAUCHE) {
                    advance()
                    arraySize2 = expect(TokenType.NOMBRE).value.toInt()
                    expect(TokenType.CROCHET_DROIT)
                }

                expect(TokenType.DE)
            }

            val type =
                    when (current().type) {
                        TokenType.ENTIER -> "entier"
                        TokenType.REEL -> "reel"
                        TokenType.CHAINE -> "chaine"
                        TokenType.CARACTERE -> "caractere"
                        TokenType.BOOLEEN -> "booleen"
                        else ->
                                error("Type de variable invalide")
                    }
            advance()

            // Check for initialization (= value)
            var initialValue: Expression? = null
            if (current().type == TokenType.AFFECTATION) {
                advance()
                initialValue = parseExpression()
            }

            for (name in names) {
                declarations.add(VariableDeclaration(name, type, if (isArray) arraySize else null, arraySize2, initialValue))
            }

            skipNewlines()
        }

        return declarations
    }

    private fun parseConstantDeclarations(): List<ConstantDeclaration> {
        val declarations = mutableListOf<ConstantDeclaration>()

        while (current().type == TokenType.IDENTIFICATEUR) {
            val name = current().value
            advance()

            expect(TokenType.DEUX_POINTS)

            val type =
                    when (current().type) {
                        TokenType.ENTIER -> "entier"
                        TokenType.REEL -> "reel"
                        TokenType.CHAINE -> "chaine"
                        TokenType.CARACTERE -> "caractere"
                        TokenType.BOOLEEN -> "booleen"
                        else ->
                                error("Type de constante invalide")
                    }
            advance()

            expect(TokenType.AFFECTATION)

            val value = parseExpression()

            declarations.add(ConstantDeclaration(name, type, value))

            skipNewlines()
        }

        return declarations
    }

    private fun parseFunctionDeclaration(): FunctionDeclaration {
        val isFonction = current().type == TokenType.FONCTION
        advance()

        val name = expect(TokenType.IDENTIFICATEUR).value
        expect(TokenType.PAREN_GAUCHE)

        val parameters = mutableListOf<VariableDeclaration>()
        if (current().type == TokenType.IDENTIFICATEUR || current().type == TokenType.VAR) {
            parameters.add(parseParameter())
            while (current().type == TokenType.VIRGULE) {
                advance()
                parameters.add(parseParameter())
            }
        }

        expect(TokenType.PAREN_DROITE)

        var returnType: String? = null
        if (isFonction) {
            expect(TokenType.DEUX_POINTS)
            returnType =
                    when (current().type) {
                        TokenType.ENTIER -> "entier"
                        TokenType.REEL -> "reel"
                        TokenType.CHAINE -> "chaine"
                        TokenType.CARACTERE -> "caractere"
                        TokenType.BOOLEEN -> "booleen"
                        else ->
                                error("Type de retour invalide")
                    }
            advance()
        }

        skipNewlines()

        val variables = mutableListOf<VariableDeclaration>()
        if (current().type == TokenType.VARIABLES) {
            advance()
            skipNewlines()
            variables.addAll(parseVariableDeclarations())
        }

        skipNewlines()
        expect(TokenType.DEBUT)
        skipNewlines()

        val body = mutableListOf<Statement>()
        while (current().type != TokenType.FIN && current().type != TokenType.EOF) {
            skipNewlines()
            if (current().type == TokenType.FIN) break
            body.add(parseStatement())
            skipNewlines()
        }

        expect(TokenType.FIN)

        return FunctionDeclaration(name, parameters, returnType, variables, body)
    }

    private fun parseParameter(): VariableDeclaration {
        // Vérifier si c'est un passage par référence (var, ref, reference)
        var isByReference = false
        if (current().type == TokenType.VAR) {
            isByReference = true
            advance()
        }

        val name = expect(TokenType.IDENTIFICATEUR).value
        expect(TokenType.DEUX_POINTS)

        var isArray = false
        var arraySize: Int? = null

        if (current().type == TokenType.TABLEAU) {
            isArray = true
            advance()
            if (current().type == TokenType.CROCHET_GAUCHE) {
                advance()
                if (current().type == TokenType.NOMBRE) {
                    arraySize = current().value.toInt()
                    advance()
                }
                expect(TokenType.CROCHET_DROIT)
            }
            expect(TokenType.DE)
        }

        val type =
                when (current().type) {
                    TokenType.ENTIER -> "entier"
                    TokenType.REEL -> "reel"
                    TokenType.CHAINE -> "chaine"
                    TokenType.CARACTERE -> "caractere"
                    TokenType.BOOLEEN -> "booleen"
                    else ->
                            error("Type de paramètre invalide")
                }
        advance()

        return VariableDeclaration(name, type, if (isArray) arraySize else null, null, null, isByReference)
    }

    private fun parseStatement(): Statement {
        skipNewlines()

        return when (current().type) {
            TokenType.IDENTIFICATEUR -> parseAssignmentOrFunctionCall()
            TokenType.SI -> parseIfStatement()
            TokenType.POUR -> parseForLoop()
            TokenType.TANTQUE -> parseWhileLoop()
            TokenType.REPETER -> parseRepeatUntilLoop()
            TokenType.SELON -> parseWhenStatement()
            TokenType.ECRIRE -> parseWriteStatement()
            TokenType.ECRIRELN -> parseWriteLnStatement()
            TokenType.LIRE -> parseReadStatement()
            TokenType.RETOURNER -> parseReturnStatement()
            // Support pour les expressions seules (nombres, parenthèses, etc.)
            TokenType.NOMBRE, TokenType.PAREN_GAUCHE, TokenType.MOINS, TokenType.PLUS -> {
                val startToken = current()
                val expr = parseExpression()
                ExpressionStatement(expr, startToken.line)
            }
            else ->
                    error("Instruction invalide '${current().value}'")
        }
    }

    private fun parseAssignmentOrFunctionCall(): Statement {
        val startToken = current()
        val name = expect(TokenType.IDENTIFICATEUR).value

        return when (current().type) {
            TokenType.AFFECTATION -> {
                advance()
                val expression = parseExpression()
                Assignment(name, expression, startToken.line)
            }
            TokenType.CROCHET_GAUCHE -> {
                advance()
                val index1 = parseExpression()
                expect(TokenType.CROCHET_DROIT)

                // Vérifier s'il y a une deuxième dimension (matrice)
                if (current().type == TokenType.CROCHET_GAUCHE) {
                    advance()
                    val index2 = parseExpression()
                    expect(TokenType.CROCHET_DROIT)
                    expect(TokenType.AFFECTATION)
                    val expression = parseExpression()
                    MatrixAssignment(name, index1, index2, expression, startToken.line)
                } else {
                    expect(TokenType.AFFECTATION)
                    val expression = parseExpression()
                    ArrayAssignment(name, index1, expression, startToken.line)
                }
            }
            TokenType.PAREN_GAUCHE -> {
                // Sauvegarder la position avant de parser l'appel de fonction
                val savedPos = pos
                advance()
                val arguments = mutableListOf<Expression>()
                if (current().type != TokenType.PAREN_DROITE) {
                    arguments.add(parseExpression())
                    while (current().type == TokenType.VIRGULE) {
                        advance()
                        arguments.add(parseExpression())
                    }
                }
                expect(TokenType.PAREN_DROITE)

                // Vérifier si c'est un appel de fonction seul ou une expression plus complexe
                // Si le token suivant est un opérateur, c'est une expression standalone
                if (current().type in listOf(TokenType.PLUS, TokenType.MOINS, TokenType.MULT,
                                              TokenType.DIV, TokenType.DIV_ENTIERE, TokenType.MOD,
                                              TokenType.PUISSANCE, TokenType.EGAL, TokenType.DIFFERENT,
                                              TokenType.INFERIEUR, TokenType.SUPERIEUR,
                                              TokenType.INFERIEUR_EGAL, TokenType.SUPERIEUR_EGAL,
                                              TokenType.ET, TokenType.OU)) {
                    // C'est une expression complexe, reculer et re-parser complètement
                    // Revenir à l'identificateur
                    pos = savedPos - 1
                    val expr = parseExpression()
                    ExpressionStatement(expr, startToken.line)
                } else {
                    // C'est juste un appel de fonction (statement)
                    FunctionCall(name, arguments, startToken.line)
                }
            }
            // Si ce n'est ni une affectation, ni un accès tableau, ni un appel de fonction
            // C'est une expression seule qui commence par un identificateur
            else -> {
                // Reculer d'un token pour re-parser comme expression complète
                pos--
                val expr = parseExpression()
                ExpressionStatement(expr, startToken.line)
            }
        }
    }



    private fun parseReturnStatement(): ReturnStatement {
        expect(TokenType.RETOURNER)
        val expression =
                if (current().type != TokenType.NEWLINE &&
                                current().type != TokenType.FIN &&
                                current().type != TokenType.EOF
                ) {
                    parseExpression()
                } else null
        return ReturnStatement(expression)
    }

    private fun parseIfStatement(): IfStatement {
        val siToken = current()
        expect(TokenType.SI)
        val condition = parseExpression()
        expect(TokenType.ALORS)
        skipNewlines()

        val thenBranch = mutableListOf<Statement>()
        while (current().type != TokenType.SINONSI &&
                current().type != TokenType.SINON &&
                current().type != TokenType.FINSI &&
                current().type != TokenType.EOF) {
            skipNewlines()
            if (current().type == TokenType.SINONSI ||
                current().type == TokenType.SINON ||
                current().type == TokenType.FINSI) break
            thenBranch.add(parseStatement())
            skipNewlines()
        }

        // Parse elseIf clauses (sinonSi)
        val elseIfClauses = mutableListOf<ElseIfClause>()
        while (current().type == TokenType.SINONSI) {
            val sinonSiToken = current()
            advance() // consume SINONSI
            val elseIfCondition = parseExpression()
            expect(TokenType.ALORS)
            skipNewlines()

            val elseIfBranch = mutableListOf<Statement>()
            while (current().type != TokenType.SINONSI &&
                    current().type != TokenType.SINON &&
                    current().type != TokenType.FINSI &&
                    current().type != TokenType.EOF) {
                skipNewlines()
                if (current().type == TokenType.SINONSI ||
                    current().type == TokenType.SINON ||
                    current().type == TokenType.FINSI) break
                elseIfBranch.add(parseStatement())
                skipNewlines()
            }

            elseIfClauses.add(ElseIfClause(elseIfCondition, elseIfBranch, sinonSiToken.line))
        }

        // Parse else clause (sinon)
        val elseBranch =
                if (current().type == TokenType.SINON) {
                    advance()
                    skipNewlines()
                    val branch = mutableListOf<Statement>()
                    while (current().type != TokenType.FINSI && current().type != TokenType.EOF) {
                        skipNewlines()
                        if (current().type == TokenType.FINSI) break
                        branch.add(parseStatement())
                        skipNewlines()
                    }
                    branch
                } else null

        expect(TokenType.FINSI)

        return IfStatement(condition, thenBranch, elseIfClauses, elseBranch, siToken.line)
    }

    private fun parseForLoop(): ForLoop {
        val pourToken = current()
        expect(TokenType.POUR)
        val variable = expect(TokenType.IDENTIFICATEUR).value
        expect(TokenType.DE)
        val start = parseExpression()

        // Accepter uniquement "à" (pas "a" pour éviter confusion avec les variables)
        if (current().type != TokenType.IDENTIFICATEUR ||
                        current().value.lowercase() != "à"
        ) {
            error("Attendu 'à' après 'de' dans la boucle 'pour', trouvé ${current().value}")
        }
        advance()

        val end = parseExpression()

        // Vérifier si on a un "pas"
        var step: Expression? = null
        if (current().type == TokenType.IDENTIFICATEUR &&
            current().value.lowercase() == "pas") {
            advance() // consommer "pas"
            step = parseExpression()
        }

        expect(TokenType.FAIRE)
        skipNewlines()

        val body = mutableListOf<Statement>()
        while (current().type != TokenType.FINPOUR && current().type != TokenType.EOF) {
            skipNewlines()
            if (current().type == TokenType.FINPOUR) break
            body.add(parseStatement())
            skipNewlines()
        }

        expect(TokenType.FINPOUR)

        return ForLoop(variable, start, end, step, body, pourToken.line)
    }

    private fun parseWhileLoop(): WhileLoop {
        val tantQueToken = current()
        expect(TokenType.TANTQUE)
        val condition = parseExpression()
        expect(TokenType.FAIRE)
        skipNewlines()

        val body = mutableListOf<Statement>()
        while (current().type != TokenType.FINTANTQUE && current().type != TokenType.EOF) {
            skipNewlines()
            if (current().type == TokenType.FINTANTQUE) break
            body.add(parseStatement())
            skipNewlines()
        }

        expect(TokenType.FINTANTQUE)

        return WhileLoop(condition, body, tantQueToken.line)
    }

    private fun parseRepeatUntilLoop(): RepeatUntilLoop {
        val repeterToken = current()
        expect(TokenType.REPETER)
        skipNewlines()

        val body = mutableListOf<Statement>()
        while (current().type != TokenType.JUSQUA && current().type != TokenType.EOF) {
            skipNewlines()
            if (current().type == TokenType.JUSQUA) break
            body.add(parseStatement())
            skipNewlines()
        }

        expect(TokenType.JUSQUA)
        val condition = parseExpression()

        return RepeatUntilLoop(body, condition, repeterToken.line)
    }

    private fun parseWhenStatement(): WhenStatement {
        val selonToken = current()
        expect(TokenType.SELON)
        expect(TokenType.PAREN_GAUCHE)
        val expression = parseExpression()
        expect(TokenType.PAREN_DROITE)
        skipNewlines()

        val cases = mutableListOf<WhenCase>()
        var defaultCase: List<Statement>? = null

        while (current().type != TokenType.FINSELON && current().type != TokenType.EOF) {
            skipNewlines()

            when (current().type) {
                TokenType.CAS -> {
                    advance() // consommer 'cas'
                    val value = parseExpression()
                    expect(TokenType.DEUX_POINTS) // :
                    skipNewlines()

                    val statements = mutableListOf<Statement>()
                    // Lire une seule instruction ou plusieurs jusqu'au prochain cas/defaut/finselon
                    while (current().type != TokenType.CAS &&
                           current().type != TokenType.DEFAUT &&
                           current().type != TokenType.FINSELON &&
                           current().type != TokenType.EOF) {
                        skipNewlines()
                        if (current().type == TokenType.CAS ||
                            current().type == TokenType.DEFAUT ||
                            current().type == TokenType.FINSELON) break
                        statements.add(parseStatement())
                        skipNewlines()
                    }

                    cases.add(WhenCase(value, statements))
                }
                TokenType.DEFAUT -> {
                    advance() // consommer 'defaut'
                    expect(TokenType.DEUX_POINTS) // :
                    skipNewlines()

                    val statements = mutableListOf<Statement>()
                    while (current().type != TokenType.FINSELON && current().type != TokenType.EOF) {
                        skipNewlines()
                        if (current().type == TokenType.FINSELON) break
                        statements.add(parseStatement())
                        skipNewlines()
                    }

                    defaultCase = statements
                }
                TokenType.FINSELON -> break
                else -> {
                    skipNewlines()
                    if (current().type != TokenType.FINSELON) {
                        advance() // ignorer les tokens non reconnus
                    }
                }
            }
        }

        expect(TokenType.FINSELON)

        return WhenStatement(expression, cases, defaultCase, selonToken.line)
    }

    private fun parseWriteStatement(): WriteStatement {
        expect(TokenType.ECRIRE)
        if (current().type == TokenType.PAREN_GAUCHE) {
            advance()
        }

        val expressions = mutableListOf<Expression>()
        expressions.add(parseExpression())

        while (current().type == TokenType.VIRGULE) {
            advance()
            expressions.add(parseExpression())
        }

        if (current().type == TokenType.PAREN_DROITE) {
            advance()
        }

        return WriteStatement(expressions, newline = false)
    }

    private fun parseWriteLnStatement(): WriteStatement {
        expect(TokenType.ECRIRELN)
        if (current().type == TokenType.PAREN_GAUCHE) {
            advance()
        }

        val expressions = mutableListOf<Expression>()

        // Vérifier s'il y a des expressions
        if (current().type != TokenType.PAREN_DROITE &&
            current().type != TokenType.NEWLINE &&
            current().type != TokenType.EOF) {
            expressions.add(parseExpression())

            while (current().type == TokenType.VIRGULE) {
                advance()
                expressions.add(parseExpression())
            }
        }

        if (current().type == TokenType.PAREN_DROITE) {
            advance()
        }

        return WriteStatement(expressions, newline = true)
    }

    private fun parseReadStatement(): ReadStatement {
        expect(TokenType.LIRE)
        if (current().type == TokenType.PAREN_GAUCHE) {
            advance()
        }

        val targets = mutableListOf<ReadTarget>()

        // Parse first target
        val name = expect(TokenType.IDENTIFICATEUR).value
        if (current().type == TokenType.CROCHET_GAUCHE) {
            advance()
            val index = parseExpression()
            expect(TokenType.CROCHET_DROIT)
            targets.add(ArrayReadTarget(name, index))
        } else {
            targets.add(SimpleReadTarget(name))
        }

        while (current().type == TokenType.VIRGULE) {
            advance()
            val varName = expect(TokenType.IDENTIFICATEUR).value
            if (current().type == TokenType.CROCHET_GAUCHE) {
                advance()
                val index = parseExpression()
                expect(TokenType.CROCHET_DROIT)
                targets.add(ArrayReadTarget(varName, index))
            } else {
                targets.add(SimpleReadTarget(varName))
            }
        }

        if (current().type == TokenType.PAREN_DROITE) {
            advance()
        }

        return ReadStatement(targets)
    }

    private fun parseExpression(): Expression {
        return parseConditional()
    }

    private fun parseConditional(): Expression {
        // Check if it starts with 'si' (conditional expression)
        if (current().type == TokenType.SI) {
            val siToken = current()
            advance() // consume 'si'
            val condition = parseLogicalOr()
            expect(TokenType.ALORS)
            val thenValue = parseLogicalOr()
            expect(TokenType.SINON)
            val elseValue = parseLogicalOr()
            return ConditionalExpression(condition, thenValue, elseValue, siToken.line)
        }

        return parseLogicalOr()
    }

    private fun parseLogicalOr(): Expression {
        var left = parseLogicalAnd()

        while (current().type == TokenType.OU) {
            val opToken = current()
            val op = opToken.value
            advance()
            val right = parseLogicalAnd()
            left = BinaryOp(left, op, right, opToken.line)
        }

        return left
    }

    private fun parseLogicalAnd(): Expression {
        var left = parseComparison()

        while (current().type == TokenType.ET) {
            val opToken = current()
            val op = opToken.value
            advance()
            val right = parseComparison()
            left = BinaryOp(left, op, right, opToken.line)
        }

        return left
    }

    private fun parseComparison(): Expression {
        var left = parseAdditive()

        while (current().type in
                listOf(
                        TokenType.EGAL,
                        TokenType.DIFFERENT,
                        TokenType.INFERIEUR,
                        TokenType.SUPERIEUR,
                        TokenType.INFERIEUR_EGAL,
                        TokenType.SUPERIEUR_EGAL
                )) {
            val opToken = current()
            val op = opToken.value
            advance()
            val right = parseAdditive()
            left = BinaryOp(left, op, right, opToken.line)
        }

        return left
    }

    private fun parseAdditive(): Expression {
        var left = parseMultiplicative()

        while (current().type in listOf(TokenType.PLUS, TokenType.MOINS)) {
            val opToken = current()
            val op = opToken.value
            advance()
            val right = parseMultiplicative()
            left = BinaryOp(left, op, right, opToken.line)
        }

        return left
    }

    private fun parseMultiplicative(): Expression {
        var left = parsePower()

        while (current().type in
                listOf(TokenType.MULT, TokenType.DIV, TokenType.DIV_ENTIERE, TokenType.MOD)) {
            val opToken = current()
            val op =
                    when (opToken.type) {
                        TokenType.DIV_ENTIERE -> "div"
                        TokenType.MOD -> "mod"
                        else -> opToken.value
                    }
            advance()
            val right = parsePower()
            left = BinaryOp(left, op, right, opToken.line)
        }

        return left
    }

    private fun parsePower(): Expression {
        var left = parseUnary()

        while (current().type == TokenType.PUISSANCE) {
            val opToken = current()
            val operator = opToken.value  // Stocker l'opérateur tel qu'il est (** ou ^)
            advance()
            // L'opérateur ** ou ^ est associatif à droite, donc on appelle récursivement parsePower
            val right = parsePower()
            left = BinaryOp(left, operator, right, opToken.line)
        }

        return left
    }

    private fun parseUnary(): Expression {
        if (current().type == TokenType.MOINS) {
            val opToken = current()
            advance()
            return UnaryOp("-", parseUnary(), opToken.line)
        }

        if (current().type == TokenType.NON) {
            val opToken = current()
            advance()
            return UnaryOp("non", parseUnary(), opToken.line)
        }

        return parsePrimary()
    }

    private fun parsePrimary(): Expression {
        return when (current().type) {
            TokenType.NOMBRE -> {
                val token = current()
                val value = token.value.toDouble()
                advance()
                NumberLiteral(value, token.line)
            }
            TokenType.TEXTE -> {
                val token = current()
                val value = token.value
                advance()
                StringLiteral(value, token.line)
            }
            TokenType.CARACTERE_LITERAL -> {
                val token = current()
                val value = token.value[0]
                advance()
                CharLiteral(value, token.line)
            }
            TokenType.VRAI -> {
                val token = current()
                advance()
                BooleanLiteral(true, token.line)
            }
            TokenType.FAUX -> {
                val token = current()
                advance()
                BooleanLiteral(false, token.line)
            }
            TokenType.IDENTIFICATEUR -> {
                val token = current()
                val name = token.value
                advance()

                when (current().type) {
                    TokenType.CROCHET_GAUCHE -> {
                        advance()
                        val index1 = parseExpression()
                        expect(TokenType.CROCHET_DROIT)

                        // Vérifier s'il y a une deuxième dimension (matrice)
                        if (current().type == TokenType.CROCHET_GAUCHE) {
                            advance()
                            val index2 = parseExpression()
                            expect(TokenType.CROCHET_DROIT)
                            MatrixAccess(name, index1, index2, token.line)
                        } else {
                            ArrayAccess(name, index1, token.line)
                        }
                    }
                    TokenType.PAREN_GAUCHE -> {
                        advance()
                        val arguments = mutableListOf<Expression>()
                        if (current().type != TokenType.PAREN_DROITE) {
                            arguments.add(parseExpression())
                            while (current().type == TokenType.VIRGULE) {
                                advance()
                                arguments.add(parseExpression())
                            }
                        }
                        expect(TokenType.PAREN_DROITE)
                        FunctionCallExpression(name, arguments, token.line)
                    }
                    else -> Variable(name, token.line)
                }
            }
            TokenType.PAREN_GAUCHE -> {
                advance()
                val expr = parseExpression()
                expect(TokenType.PAREN_DROITE)
                expr
            }
            TokenType.CROCHET_GAUCHE -> {
                // Array literal: [1, 2, 3, 4]
                val token = current()
                advance()
                val elements = mutableListOf<Expression>()

                if (current().type != TokenType.CROCHET_DROIT) {
                    elements.add(parseExpression())
                    while (current().type == TokenType.VIRGULE) {
                        advance()
                        elements.add(parseExpression())
                    }
                }

                expect(TokenType.CROCHET_DROIT)
                ArrayLiteral(elements, token.line)
            }
            else ->
                    error("Expression invalide: ${current().value}")
        }
    }
}
