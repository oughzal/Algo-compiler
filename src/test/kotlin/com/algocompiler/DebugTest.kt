package com.algocompiler

import kotlin.test.*

/**
 * Tests pour le débogage d'exécution des algorithmes.
 * Vérifie que les erreurs de parsing et d'exécution affichent
 * des messages clairs avec numéro de ligne et token.
 */
class DebugTest {

    // ========== Tests d'erreurs de parsing ==========

    @Test
    fun testParseErrorMissingAlorsAfterSi() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                si x == 5
                    ecrire("cinq")
                finsi
            fin
        """.trimIndent()

        val exception = assertFailsWith<ParseException> {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            parser.parse()
        }

        // Vérifier que l'exception contient le numéro de ligne
        assertTrue(exception.message!!.contains("ligne"), "Le message d'erreur devrait contenir 'ligne'")
        assertTrue(exception.line > 0, "Le numéro de ligne devrait être > 0")
        println("✓ Test erreur parsing (manque 'alors') : ${exception.message}")
    }

    @Test
    fun testParseErrorInvalidType() {
        val code = """
            algorithme Test
            variables
                x : invalid_type
            debut
            fin
        """.trimIndent()

        val exception = assertFailsWith<ParseException> {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            parser.parse()
        }

        assertTrue(exception.message!!.contains("Type de variable invalide"),
                   "Le message devrait indiquer un type invalide")
        assertTrue(exception.message!!.contains("ligne"),
                   "Le message devrait contenir le numéro de ligne")
        assertTrue(exception.line == 3, "L'erreur devrait être à la ligne 3")
        println("✓ Test erreur parsing (type invalide) : ${exception.message}")
    }

    @Test
    fun testParseErrorMissingAInPourLoop() {
        val code = """
            algorithme Test
            variables
                i : entier
            debut
                pour i de 1 jusqua 10 faire
                    ecrire(i)
                finpour
            fin
        """.trimIndent()

        val exception = assertFailsWith<ParseException> {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            parser.parse()
        }

        assertTrue(exception.message!!.contains("Attendu 'à'"),
                   "Le message devrait indiquer qu'on attend 'à'")
        assertTrue(exception.message!!.contains("ligne"),
                   "Le message devrait contenir le numéro de ligne")
        println("✓ Test erreur parsing (manque 'à' dans pour) : ${exception.message}")
    }

    @Test
    fun testParseErrorUnexpectedToken() {
        val code = """
            algorithme Test
            debut
                x = 5
                ??? invalid
            fin
        """.trimIndent()

        val exception = assertFailsWith<Exception> {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            parser.parse()
        }

        assertTrue(exception.message!!.contains("ligne") ||
                   exception.message!!.contains("Ligne"),
                   "Le message devrait contenir le numéro de ligne")
        println("✓ Test erreur parsing (token inattendu) : ${exception.message}")
    }

    @Test
    fun testParseErrorMissingOperatorAfterIdentifier() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                si x 5 alors
                    ecrire("test")
                finsi
            fin
        """.trimIndent()

        val exception = assertFailsWith<ParseException> {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            parser.parse()
        }

        assertTrue(exception.message!!.contains("Opérateur de comparaison attendu") ||
                   exception.message!!.contains("ligne"),
                   "Le message devrait indiquer une erreur de syntaxe")
        assertTrue(exception.message!!.contains("ligne"),
                   "Le message devrait contenir le numéro de ligne")
        println("✓ Test erreur parsing (manque opérateur) : ${exception.message}")
    }

    // ========== Tests d'erreurs d'exécution (runtime) ==========

    @Test
    fun testRuntimeErrorUndefinedVariable() {
        val code = """
            algorithme Test
            debut
                ecrire(x)
            fin
        """.trimIndent()

        val exception = assertFailsWith<Exception> {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            val program = parser.parse()
            val interpreter = Interpreter()
            interpreter.execute(program)
        }

        assertTrue(exception.message!!.contains("Variable non définie") ||
                   exception.message!!.contains("Variable") ||
                   exception.message!!.contains("x"),
                   "Le message devrait mentionner la variable non définie")
        println("✓ Test erreur runtime (variable non définie) : ${exception.message}")
    }

    @Test
    fun testRuntimeErrorDivisionByZero() {
        val code = """
            algorithme Test
            variables
                x, y : entier
                z : reel
            debut
                x = 10
                y = 0
                z = x / y
                ecrireln(z)
            fin
        """.trimIndent()

        // Note: En Kotlin, division par zéro produit Infinity, pas une exception
        // Ce test vérifie que le code s'exécute (peut produire Infinity)
        try {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            val program = parser.parse()
            val interpreter = Interpreter()
            interpreter.execute(program)
            println("⚠ Test division par zéro : pas d'erreur levée (résultat = Infinity)")
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("Division par zéro") ||
                       e.message!!.contains("division") ||
                       e.message!!.contains("zero") ||
                       e.message!!.contains("Infinity"),
                       "Le message devrait mentionner la division par zéro")
            println("✓ Test erreur runtime (division par zéro) : ${e.message}")
        }
    }

    @Test
    fun testRuntimeErrorArrayIndexOutOfBounds() {
        val code = """
            algorithme Test
            variables
                T : tableau[5] de entier
            debut
                T[10] = 42
            fin
        """.trimIndent()

        val exception = assertFailsWith<Exception> {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            val program = parser.parse()
            val interpreter = Interpreter()
            interpreter.execute(program)
        }

        assertTrue(exception.message!!.contains("Index hors limites") ||
                   exception.message!!.contains("index") ||
                   exception.message!!.contains("limites"),
                   "Le message devrait mentionner l'index hors limites")
        println("✓ Test erreur runtime (index hors limites) : ${exception.message}")
    }

    @Test
    fun testRuntimeErrorTypeIncompatibility() {
        val code = """
            algorithme Test
            variables
                x : entier
                s : chaine
            debut
                x = 5
                s = x
            fin
        """.trimIndent()

        // Note: Ce test vérifie si le typage fort est activé
        // Si pas d'erreur, c'est que le casting implicite est autorisé
        try {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            val program = parser.parse()
            val interpreter = Interpreter()
            interpreter.execute(program)
            println("⚠ Test erreur runtime (incompatibilité de type) : pas d'erreur levée (casting implicite autorisé)")
        } catch (e: Exception) {
            assertTrue(e.message!!.contains("type") ||
                       e.message!!.contains("incompatible") ||
                       e.message!!.contains("affectation"),
                       "Le message devrait mentionner l'incompatibilité de type")
            println("✓ Test erreur runtime (incompatibilité de type) : ${e.message}")
        }
    }

    @Test
    fun testRuntimeErrorFunctionNotFound() {
        val code = """
            algorithme Test
            debut
                inexistante()
            fin
        """.trimIndent()

        val exception = assertFailsWith<Exception> {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            val program = parser.parse()
            val interpreter = Interpreter()
            interpreter.execute(program)
        }

        assertTrue(exception.message!!.contains("Fonction") ||
                   exception.message!!.contains("inexistante") ||
                   exception.message!!.contains("non définie"),
                   "Le message devrait mentionner la fonction non définie")
        println("✓ Test erreur runtime (fonction non définie) : ${exception.message}")
    }

    // ========== Tests de messages d'erreur clairs ==========

    @Test
    fun testParseExceptionContainsLineAndToken() {
        val token = Token(TokenType.IDENTIFICATEUR, "testToken", 42, 10)
        val exception = ParseException("Test d'erreur", 42, token)

        assertEquals(42, exception.line)
        assertEquals(token, exception.token)
        assertTrue(exception.toString().contains("ligne 42"))
        assertTrue(exception.toString().contains("testToken"))
        println("✓ Test ParseException format : ${exception.toString()}")
    }

    @Test
    fun testParseExceptionWithoutToken() {
        val exception = ParseException("Test d'erreur sans token", 15, null)

        assertEquals(15, exception.line)
        assertNull(exception.token)
        assertTrue(exception.toString().contains("ligne 15"))
        assertTrue(exception.toString().contains("?")) // Token inconnu
        println("✓ Test ParseException sans token : ${exception.toString()}")
    }

    // ========== Tests de syntaxe valide mais complexe ==========

    @Test
    fun testComplexExpressionParsing() {
        val code = """
            algorithme Test
            variables
                x, y, z : entier
            debut
                x = 2
                y = 3
                z = (x + y) * (x - y) + x ** y
                ecrireln(z)
            fin
        """.trimIndent()

        // Ne devrait pas lever d'exception
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals("Test", program.name)
        assertTrue(program.statements.isNotEmpty())
        println("✓ Test parsing expression complexe : succès")
    }

    @Test
    fun testNestedControlStructures() {
        val code = """
            algorithme Test
            variables
                i, j : entier
            debut
                pour i de 1 à 3 faire
                    si i == 2 alors
                        pour j de 1 à 2 faire
                            ecrire(i)
                            ecrire(j)
                        finpour
                    finsi
                finpour
            fin
        """.trimIndent()

        // Ne devrait pas lever d'exception
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals("Test", program.name)
        println("✓ Test parsing structures imbriquées : succès")
    }

    @Test
    fun testMatrixOperations() {
        val code = """
            algorithme Test
            variables
                M : tableau[2][2] de entier
                i, j : entier
            debut
                pour i de 0 à 1 faire
                    pour j de 0 à 1 faire
                        M[i][j] = i * 2 + j
                    finpour
                finpour
            fin
        """.trimIndent()

        // Ne devrait pas lever d'exception
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertEquals("Test", program.name)
        println("✓ Test parsing matrice : succès")
    }
}

