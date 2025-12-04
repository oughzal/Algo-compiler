package com.algocompiler

import kotlin.test.*

class LexerTest {

    @Test
    fun testSimpleTokens() {
        val code = "algorithme Test debut fin"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.ALGORITHME, tokens[0].type)
        assertEquals(TokenType.IDENTIFICATEUR, tokens[1].type)
        assertEquals(TokenType.DEBUT, tokens[2].type)
        assertEquals(TokenType.FIN, tokens[3].type)
        assertEquals(TokenType.EOF, tokens[4].type)
    }

    @Test
    fun testNumberLiterals() {
        val code = "42 3.14 0.5"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.NOMBRE, tokens[0].type)
        assertEquals("42", tokens[0].value)
        assertEquals(TokenType.NOMBRE, tokens[1].type)
        assertEquals("3.14", tokens[1].value)
        assertEquals(TokenType.NOMBRE, tokens[2].type)
        assertEquals("0.5", tokens[2].value)
    }

    @Test
    fun testStringLiterals() {
        val code = "\"Hello\" \"World\\n\""
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.TEXTE, tokens[0].type)
        assertEquals("Hello", tokens[0].value)
        assertEquals(TokenType.TEXTE, tokens[1].type)
        assertEquals("World\n", tokens[1].value)
    }

    @Test
    fun testCharacterLiterals() {
        val code = "'A' 'z' '0'"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.CARACTERE_LITERAL, tokens[0].type)
        assertEquals("A", tokens[0].value)
        assertEquals(TokenType.CARACTERE_LITERAL, tokens[1].type)
        assertEquals("z", tokens[1].value)
        assertEquals(TokenType.CARACTERE_LITERAL, tokens[2].type)
        assertEquals("0", tokens[2].value)
    }

    @Test
    fun testCharacterEscapeSequences() {
        val code = "'\\n' '\\t' '\\'' '\\\\'"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.CARACTERE_LITERAL, tokens[0].type)
        assertEquals("\n", tokens[0].value)
        assertEquals(TokenType.CARACTERE_LITERAL, tokens[1].type)
        assertEquals("\t", tokens[1].value)
        assertEquals(TokenType.CARACTERE_LITERAL, tokens[2].type)
        assertEquals("'", tokens[2].value)
        assertEquals(TokenType.CARACTERE_LITERAL, tokens[3].type)
        assertEquals("\\", tokens[3].value)
    }

    @Test
    fun testTypographicQuotes() {
        val code = "'A' 'B'"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.CARACTERE_LITERAL, tokens[0].type)
        assertEquals("A", tokens[0].value)
        assertEquals(TokenType.CARACTERE_LITERAL, tokens[1].type)
        assertEquals("B", tokens[1].value)
    }

    @Test
    fun testOperators() {
        val code = "+ - * / = == != < > <= >= :"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.PLUS, tokens[0].type)
        assertEquals(TokenType.MOINS, tokens[1].type)
        assertEquals(TokenType.MULT, tokens[2].type)
        assertEquals(TokenType.DIV, tokens[3].type)
        assertEquals(TokenType.AFFECTATION, tokens[4].type)
        assertEquals(TokenType.EGAL, tokens[5].type)
        assertEquals(TokenType.DIFFERENT, tokens[6].type)
        assertEquals(TokenType.INFERIEUR, tokens[7].type)
        assertEquals(TokenType.SUPERIEUR, tokens[8].type)
        assertEquals(TokenType.INFERIEUR_EGAL, tokens[9].type)
        assertEquals(TokenType.SUPERIEUR_EGAL, tokens[10].type)
        assertEquals(TokenType.DEUX_POINTS, tokens[11].type)
    }

    @Test
    fun testPowerOperator() {
        val code = "2 ** 3"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.NOMBRE, tokens[0].type)
        assertEquals(TokenType.PUISSANCE, tokens[1].type)
        assertEquals("**", tokens[1].value)
        assertEquals(TokenType.NOMBRE, tokens[2].type)
    }

    @Test
    fun testMultVsPower() {
        val code = "2 * 3 ** 4 * 5"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.NOMBRE, tokens[0].type)
        assertEquals(TokenType.MULT, tokens[1].type)
        assertEquals(TokenType.NOMBRE, tokens[2].type)
        assertEquals(TokenType.PUISSANCE, tokens[3].type)
        assertEquals(TokenType.NOMBRE, tokens[4].type)
        assertEquals(TokenType.MULT, tokens[5].type)
        assertEquals(TokenType.NOMBRE, tokens[6].type)
    }

    @Test
    fun testKeywords() {
        val code = "si alors sinon finsi pour tantque selon cas"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.SI, tokens[0].type)
        assertEquals(TokenType.ALORS, tokens[1].type)
        assertEquals(TokenType.SINON, tokens[2].type)
        assertEquals(TokenType.FINSI, tokens[3].type)
        assertEquals(TokenType.POUR, tokens[4].type)
        assertEquals(TokenType.TANTQUE, tokens[5].type)
        assertEquals(TokenType.SELON, tokens[6].type)
        assertEquals(TokenType.CAS, tokens[7].type)
    }

    @Test
    fun testCaseInsensitivity() {
        val code = "ALGORITHME Algorithme DEBUT debut"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.ALGORITHME, tokens[0].type)
        assertEquals(TokenType.ALGORITHME, tokens[1].type)
        assertEquals(TokenType.DEBUT, tokens[2].type)
        assertEquals(TokenType.DEBUT, tokens[3].type)
    }

    @Test
    fun testAccentOptional() {
        val code = "ecrire écrire debut début"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.ECRIRE, tokens[0].type)
        assertEquals(TokenType.ECRIRE, tokens[1].type)
        assertEquals(TokenType.DEBUT, tokens[2].type)
        assertEquals(TokenType.DEBUT, tokens[3].type)
    }

    @Test
    fun testCommentsSingleLine() {
        val code = """
            // Commentaire ligne
            x := 5
            # Autre commentaire
            y := 10
        """.trimIndent()
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        // Les commentaires doivent être ignorés
        val identifierTokens = tokens.filter { it.type == TokenType.IDENTIFICATEUR }
        assertEquals(2, identifierTokens.size)
        assertEquals("x", identifierTokens[0].value)
        assertEquals("y", identifierTokens[1].value)
    }

    @Test
    fun testCommentsMultiLine() {
        val code = """
            /* Commentaire
               multi-lignes */
            x := 5
        """.trimIndent()
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        val identifierTokens = tokens.filter { it.type == TokenType.IDENTIFICATEUR }
        assertEquals(1, identifierTokens.size)
        assertEquals("x", identifierTokens[0].value)
    }

    @Test
    fun testTypes() {
        val code = "entier reel chaine caractere booleen"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.ENTIER, tokens[0].type)
        assertEquals(TokenType.REEL, tokens[1].type)
        assertEquals(TokenType.CHAINE, tokens[2].type)
        assertEquals(TokenType.CARACTERE, tokens[3].type)
        assertEquals(TokenType.BOOLEEN, tokens[4].type)
    }

    @Test
    fun testBooleanLiterals() {
        val code = "vrai faux"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.VRAI, tokens[0].type)
        assertEquals(TokenType.FAUX, tokens[1].type)
    }

    @Test
    fun testLogicalOperators() {
        val code = "et ou non"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.ET, tokens[0].type)
        assertEquals(TokenType.OU, tokens[1].type)
        assertEquals(TokenType.NON, tokens[2].type)
    }

    @Test
    fun testDelimiters() {
        val code = "( ) [ ] , ;"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.PAREN_GAUCHE, tokens[0].type)
        assertEquals(TokenType.PAREN_DROITE, tokens[1].type)
        assertEquals(TokenType.CROCHET_GAUCHE, tokens[2].type)
        assertEquals(TokenType.CROCHET_DROIT, tokens[3].type)
        assertEquals(TokenType.VIRGULE, tokens[4].type)
        assertEquals(TokenType.POINT_VIRGULE, tokens[5].type)
    }

    @Test
    fun testDivAndMod() {
        val code = "div mod %"
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        assertEquals(TokenType.DIV_ENTIERE, tokens[0].type)
        assertEquals(TokenType.MOD, tokens[1].type)
        assertEquals(TokenType.MOD, tokens[2].type)
    }
}
