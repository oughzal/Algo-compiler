package com.algocompiler

class Lexer(private val input: String) {
    private var pos = 0
    private var line = 1
    private var column = 1
    private val tokens = mutableListOf<Token>()

    private val keywords =
            mapOf(
                    "algorithme" to TokenType.ALGORITHME,
                    "debut" to TokenType.DEBUT,
                    "fin" to TokenType.FIN,
                    "variables" to TokenType.VARIABLES,
                    "var" to TokenType.VARIABLES,
                    "constantes" to TokenType.CONSTANTES,
                    "const" to TokenType.CONSTANTES,
                    "si" to TokenType.SI,
                    "alors" to TokenType.ALORS,
                    "sinon" to TokenType.SINON,
                    "finsi" to TokenType.FINSI,
                    "pour" to TokenType.POUR,
                    "de" to TokenType.DE,
                    "faire" to TokenType.FAIRE,
                    "finpour" to TokenType.FINPOUR,
                    "tantque" to TokenType.TANTQUE,
                    "fintantque" to TokenType.FINTANTQUE,
                    "repeter" to TokenType.REPETER,
                    "jusqua" to TokenType.JUSQUA,
                    "selon" to TokenType.SELON,
                    "cas" to TokenType.CAS,
                    "defaut" to TokenType.DEFAUT,
                    "finselon" to TokenType.FINSELON,
                    "ecrire" to TokenType.ECRIRE,
                    "ecrireln" to TokenType.ECRIRELN,
                    "lire" to TokenType.LIRE,
                    "entier" to TokenType.ENTIER,
                    "reel" to TokenType.REEL,
                    "chaine" to TokenType.CHAINE,
                    "caractere" to TokenType.CARACTERE,
                    "booleen" to TokenType.BOOLEEN,
                    "vrai" to TokenType.VRAI,
                    "faux" to TokenType.FAUX,
                    "et" to TokenType.ET,
                    "ou" to TokenType.OU,
                    "non" to TokenType.NON,
                    "mod" to TokenType.MOD,
                    "div" to TokenType.DIV_ENTIERE,
                    "fonction" to TokenType.FONCTION,
                    "procedure" to TokenType.PROCEDURE,
                    "retourner" to TokenType.RETOURNER,
                    "tableau" to TokenType.TABLEAU
            )

    // Fonction pour normaliser les chaînes (enlever les accents et convertir en minuscules)
    private fun normalize(str: String): String {
        return str.lowercase()
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
                .replace("'", "")
                .replace("’", "")
                .replace("‘", "")
    }

    private fun current(): Char? = if (pos < input.length) input[pos] else null


    private fun isSingleQuote(ch: Char?): Boolean {
        return ch == '\'' || ch == '’' || ch == '‘'
    }

    private fun advance() {
        if (current() == '\n') {
            line++
            column = 1
        } else {
            column++
        }
        pos++
    }

    private fun skipWhitespace() {
        while (current()?.isWhitespace() == true && current() != '\n') {
            advance()
        }
    }

    private fun skipComment() {
        // Commentaire ligne simple //
        if (current() == '/' && pos + 1 < input.length && input[pos + 1] == '/') {
            while (current() != '\n' && current() != null) {
                advance()
            }
        }
        // Commentaire ligne simple #
        else if (current() == '#') {
            while (current() != '\n' && current() != null) {
                advance()
            }
        }
        // Commentaire multi-lignes /* */
        else if (current() == '/' && pos + 1 < input.length && input[pos + 1] == '*') {
            advance() // Skip /
            advance() // Skip *
            while (current() != null) {
                if (current() == '*' && pos + 1 < input.length && input[pos + 1] == '/') {
                    advance() // Skip *
                    advance() // Skip /
                    break
                }
                advance()
            }
        }
    }

    private fun readNumber(): Token {
        val startCol = column
        val sb = StringBuilder()
        var hasDecimal = false

        while (current()?.isDigit() == true || current() == '.') {
            if (current() == '.') {
                if (hasDecimal) break
                hasDecimal = true
            }
            sb.append(current())
            advance()
        }

        return Token(TokenType.NOMBRE, sb.toString(), line, startCol)
    }

    private fun readString(): Token {
        val startCol = column
        advance() // Skip opening quote
        val sb = StringBuilder()

        while (current() != '"' && current() != null) {
            if (current() == '\\' && pos + 1 < input.length) {
                advance()
                when (current()) {
                    'n' -> sb.append('\n')
                    't' -> sb.append('\t')
                    'r' -> sb.append('\r')
                    '\\' -> sb.append('\\')
                    '"' -> sb.append('"')
                    else -> {
                        sb.append('\\')
                        sb.append(current())
                    }
                }
                advance()
            } else {
                sb.append(current())
                advance()
            }
        }

        if (current() == '"') {
            advance() // Skip closing quote
        }

        return Token(TokenType.TEXTE, sb.toString(), line, startCol)
    }

    private fun readChar(): Token {
        val startCol = column
        advance() // Skip opening single quote (could be typographic)

        if (current() == null) {
            throw Exception("Caractère non terminé à la ligne $line")
        }

        val charValue = if (current() == '\\' && pos + 1 < input.length) {
            // Gestion des caractères d'échappement
            advance()
            when (current()) {
                'n' -> '\n'
                't' -> '\t'
                'r' -> '\r'
                '\\' -> '\\'
                '\'' -> '\''
                else -> current()!!
            }.also { advance() }
        } else {
            current()!!.also { advance() }
        }

        if (!isSingleQuote(current())) {
            val found = current()
            val code = found?.code ?: -1
            throw Exception("Caractère non terminé ou trop long à la ligne $line (attendu '). Caractère trouvé: '$found' (U+${code.toString(16).uppercase()})")
        }

        advance() // Skip closing single quote

        return Token(TokenType.CARACTERE_LITERAL, charValue.toString(), line, startCol)
    }

    private fun readIdentifier(): Token {
        val startCol = column
        val sb = StringBuilder()

        while (current()?.let {
            it.isLetterOrDigit() ||
                    it == '_' ||
                    it == 'é' ||
                    it == 'è' ||
                    it == 'à' ||
                    it == 'ù' ||
                    it == '\''
        } == true) {
            sb.append(current())
            advance()
        }

        val originalValue = sb.toString()
        val normalizedValue = normalize(originalValue)
        val type = keywords[normalizedValue] ?: TokenType.IDENTIFICATEUR

        return Token(type, originalValue, line, startCol)
    }

    fun tokenize(): List<Token> {
        while (current() != null) {
            skipWhitespace()

            if (current() == null) break

            // Commentaires (// ou # ou /* */)
            if ((current() == '/' && pos + 1 < input.length && input[pos + 1] == '/') ||
                            (current() == '/' && pos + 1 < input.length && input[pos + 1] == '*') ||
                            current() == '#'
            ) {
                skipComment()
                continue
            }

            // Newline
            if (current() == '\n') {
                tokens.add(Token(TokenType.NEWLINE, "\n", line, column))
                advance()
                continue
            }

            // Numbers
            if (current()?.isDigit() == true) {
                tokens.add(readNumber())
                continue
            }

            // Strings
            if (current() == '"') {
                tokens.add(readString())
                continue
            }

            // Characters
            if (isSingleQuote(current())) {
                tokens.add(readChar())
                continue
            }

            // Identifiers and keywords
            if (current()?.isLetter() == true || current()?.let { it in "éèàùâêîôûç" } == true) {
                tokens.add(readIdentifier())
                continue
            }

            // Operators and delimiters
            val startCol = column
            val token =
                    when (current()) {
                        '+' -> {
                            advance()
                            Token(TokenType.PLUS, "+", line, startCol)
                        }
                        '-' -> {
                            advance()
                            Token(TokenType.MOINS, "-", line, startCol)
                        }
                        '*' -> {
                            advance()
                            if (current() == '*') {
                                advance()
                                Token(TokenType.PUISSANCE, "**", line, startCol)
                            } else {
                                Token(TokenType.MULT, "*", line, startCol)
                            }
                        }
                        '/' -> {
                            advance()
                            Token(TokenType.DIV, "/", line, startCol)
                        }
                        '%' -> {
                            advance()
                            Token(TokenType.MOD, "%", line, startCol)
                        }
                        ',' -> {
                            advance()
                            Token(TokenType.VIRGULE, ",", line, startCol)
                        }
                        ':' -> {
                            advance()
                            Token(TokenType.DEUX_POINTS, ":", line, startCol)
                        }
                        ';' -> {
                            advance()
                            Token(TokenType.POINT_VIRGULE, ";", line, startCol)
                        }
                        '(' -> {
                            advance()
                            Token(TokenType.PAREN_GAUCHE, "(", line, startCol)
                        }
                        ')' -> {
                            advance()
                            Token(TokenType.PAREN_DROITE, ")", line, startCol)
                        }
                        '[' -> {
                            advance()
                            Token(TokenType.CROCHET_GAUCHE, "[", line, startCol)
                        }
                        ']' -> {
                            advance()
                            Token(TokenType.CROCHET_DROIT, "]", line, startCol)
                        }
                        '=' -> {
                            advance()
                            if (current() == '=') {
                                advance()
                                Token(TokenType.EGAL, "==", line, startCol)
                            } else {
                                Token(TokenType.AFFECTATION, "=", line, startCol)
                            }
                        }
                        '<' -> {
                            advance()
                            if (current() == '=') {
                                advance()
                                Token(TokenType.INFERIEUR_EGAL, "<=", line, startCol)
                            } else if (current() == '>') {
                                advance()
                                Token(TokenType.DIFFERENT, "<>", line, startCol)
                            } else {
                                Token(TokenType.INFERIEUR, "<", line, startCol)
                            }
                        }
                        '>' -> {
                            advance()
                            if (current() == '=') {
                                advance()
                                Token(TokenType.SUPERIEUR_EGAL, ">=", line, startCol)
                            } else {
                                Token(TokenType.SUPERIEUR, ">", line, startCol)
                            }
                        }
                        '!' -> {
                            advance()
                            if (current() == '=') {
                                advance()
                                Token(TokenType.DIFFERENT, "!=", line, startCol)
                            } else {
                                throw Exception(
                                        "Caractère inattendu '!' à la ligne $line, colonne $startCol"
                                )
                            }
                        }
                        else -> {
                            throw Exception(
                                    "Caractère inattendu '${current()}' à la ligne $line, colonne $column"
                            )
                        }
                    }

            tokens.add(token)
        }

        tokens.add(Token(TokenType.EOF, "", line, column))
        return tokens
    }
}
