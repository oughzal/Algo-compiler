package com.algocompiler

class Parser(private val tokens: List<Token>) {
    private var pos = 0

    private fun current(): Token = tokens.getOrNull(pos) ?: tokens.last()

    private fun advance() {
        if (pos < tokens.size - 1) pos++
    }

    private fun expect(type: TokenType): Token {
        val token = current()
        if (token.type != type) {
            throw Exception("Attendu $type, mais trouvé ${token.type} à la ligne ${token.line}")
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
            statements.add(parseStatement())
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

            if (current().type == TokenType.TABLEAU) {
                isArray = true
                advance()
                expect(TokenType.CROCHET_GAUCHE)
                arraySize = expect(TokenType.NOMBRE).value.toInt()
                expect(TokenType.CROCHET_DROIT)
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
                                throw Exception(
                                        "Type de variable invalide à la ligne ${current().line}"
                                )
                    }
            advance()

            for (name in names) {
                declarations.add(VariableDeclaration(name, type, if (isArray) arraySize else null))
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
                                throw Exception(
                                        "Type de constante invalide à la ligne ${current().line}"
                                )
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
        if (current().type == TokenType.IDENTIFICATEUR) {
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
                                throw Exception(
                                        "Type de retour invalide à la ligne ${current().line}"
                                )
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
                            throw Exception(
                                    "Type de paramètre invalide à la ligne ${current().line}"
                            )
                }
        advance()

        return VariableDeclaration(name, type, if (isArray) arraySize else null)
    }

    private fun parseStatement(): Statement {
        skipNewlines()

        return when (current().type) {
            TokenType.IDENTIFICATEUR -> parseAssignmentOrFunctionCall()
            TokenType.SI -> parseIfStatement()
            TokenType.POUR -> parseForLoop()
            TokenType.TANTQUE -> parseWhileLoop()
            TokenType.REPETER -> parseRepeatUntilLoop()
            TokenType.ECRIRE -> parseWriteStatement()
            TokenType.ECRIRELN -> parseWriteLnStatement()
            TokenType.LIRE -> parseReadStatement()
            TokenType.RETOURNER -> parseReturnStatement()
            else ->
                    throw Exception(
                            "Instruction invalide '${current().value}' à la ligne ${current().line}"
                    )
        }
    }

    private fun parseAssignmentOrFunctionCall(): Statement {
        val name = expect(TokenType.IDENTIFICATEUR).value

        return when (current().type) {
            TokenType.AFFECTATION -> {
                advance()
                val expression = parseExpression()
                Assignment(name, expression)
            }
            TokenType.CROCHET_GAUCHE -> {
                advance()
                val index = parseExpression()
                expect(TokenType.CROCHET_DROIT)
                expect(TokenType.AFFECTATION)
                val expression = parseExpression()
                ArrayAssignment(name, index, expression)
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
                FunctionCall(name, arguments)
            }
            else ->
                    throw Exception(
                            "Attendu '=' ou '[' ou '(' après l'identificateur à la ligne ${current().line}"
                    )
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
        expect(TokenType.SI)
        val condition = parseExpression()
        expect(TokenType.ALORS)
        skipNewlines()

        val thenBranch = mutableListOf<Statement>()
        while (current().type != TokenType.SINON &&
                current().type != TokenType.FINSI &&
                current().type != TokenType.EOF) {
            skipNewlines()
            if (current().type == TokenType.SINON || current().type == TokenType.FINSI) break
            thenBranch.add(parseStatement())
            skipNewlines()
        }

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

        return IfStatement(condition, thenBranch, elseBranch)
    }

    private fun parseForLoop(): ForLoop {
        expect(TokenType.POUR)
        val variable = expect(TokenType.IDENTIFICATEUR).value
        expect(TokenType.DE)
        val start = parseExpression()

        // Accepter "à" ou "a" (normalisé)
        if (current().type != TokenType.IDENTIFICATEUR ||
                        (current().value.lowercase() != "à" && current().value.lowercase() != "a")
        ) {
            throw Exception(
                    "Attendu 'à' après 'de' dans la boucle 'pour', trouvé ${current().value} à la ligne ${current().line}"
            )
        }
        advance()

        val end = parseExpression()
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

        return ForLoop(variable, start, end, body)
    }

    private fun parseWhileLoop(): WhileLoop {
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

        return WhileLoop(condition, body)
    }

    private fun parseRepeatUntilLoop(): RepeatUntilLoop {
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

        return RepeatUntilLoop(body, condition)
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
        return parseLogicalOr()
    }

    private fun parseLogicalOr(): Expression {
        var left = parseLogicalAnd()

        while (current().type == TokenType.OU) {
            val op = current().value
            advance()
            val right = parseLogicalAnd()
            left = BinaryOp(left, op, right)
        }

        return left
    }

    private fun parseLogicalAnd(): Expression {
        var left = parseComparison()

        while (current().type == TokenType.ET) {
            val op = current().value
            advance()
            val right = parseComparison()
            left = BinaryOp(left, op, right)
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
            val op = current().value
            advance()
            val right = parseAdditive()
            left = BinaryOp(left, op, right)
        }

        return left
    }

    private fun parseAdditive(): Expression {
        var left = parseMultiplicative()

        while (current().type in listOf(TokenType.PLUS, TokenType.MOINS)) {
            val op = current().value
            advance()
            val right = parseMultiplicative()
            left = BinaryOp(left, op, right)
        }

        return left
    }

    private fun parseMultiplicative(): Expression {
        var left = parseUnary()

        while (current().type in
                listOf(TokenType.MULT, TokenType.DIV, TokenType.DIV_ENTIERE, TokenType.MOD)) {
            val op =
                    when (current().type) {
                        TokenType.DIV_ENTIERE -> "div"
                        TokenType.MOD -> "mod"
                        else -> current().value
                    }
            advance()
            val right = parseUnary()
            left = BinaryOp(left, op, right)
        }

        return left
    }

    private fun parseUnary(): Expression {
        if (current().type == TokenType.MOINS) {
            advance()
            return UnaryOp("-", parseUnary())
        }

        if (current().type == TokenType.NON) {
            advance()
            return UnaryOp("non", parseUnary())
        }

        return parsePrimary()
    }

    private fun parsePrimary(): Expression {
        return when (current().type) {
            TokenType.NOMBRE -> {
                val value = current().value.toDouble()
                advance()
                NumberLiteral(value)
            }
            TokenType.TEXTE -> {
                val value = current().value
                advance()
                StringLiteral(value)
            }
            TokenType.CARACTERE_LITERAL -> {
                val value = current().value[0]
                advance()
                CharLiteral(value)
            }
            TokenType.VRAI -> {
                advance()
                BooleanLiteral(true)
            }
            TokenType.FAUX -> {
                advance()
                BooleanLiteral(false)
            }
            TokenType.IDENTIFICATEUR -> {
                val name = current().value
                advance()

                when (current().type) {
                    TokenType.CROCHET_GAUCHE -> {
                        advance()
                        val index = parseExpression()
                        expect(TokenType.CROCHET_DROIT)
                        ArrayAccess(name, index)
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
                        FunctionCallExpression(name, arguments)
                    }
                    else -> Variable(name)
                }
            }
            TokenType.PAREN_GAUCHE -> {
                advance()
                val expr = parseExpression()
                expect(TokenType.PAREN_DROITE)
                expr
            }
            else ->
                    throw Exception(
                            "Expression invalide à la ligne ${current().line}: ${current().value}"
                    )
        }
    }
}
