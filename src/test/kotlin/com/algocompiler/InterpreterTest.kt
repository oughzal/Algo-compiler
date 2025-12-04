package com.algocompiler

import kotlin.test.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class InterpreterTest {

    private fun captureOutput(block: () -> Unit): String {
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))
        try {
            block()
        } finally {
            System.setOut(originalOut)
        }
        // Normaliser les retours à la ligne pour Windows (\r\n -> \n)
        return outputStream.toString().replace("\r\n", "\n")
    }

    private fun executeCode(code: String): String {
        return captureOutput {
            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            val program = parser.parse()
            val interpreter = Interpreter()
            interpreter.execute(program)
        }
    }

    @Test
    fun testSimpleAssignment() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 5
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("5\n", output)
    }

    @Test
    fun testCharacterAssignment() {
        val code = """
            algorithme Test
            variables
                c : caractere
            debut
                c = 'A'
                ecrireln(c)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("A\n", output)
    }

    @Test
    fun testCharacterEscapeSequences() {
        val code = """
            algorithme Test
            variables
                c : caractere
            debut
                c = '\n'
                ecrire("avant")
                ecrire(c)
                ecrire("apres")
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("avant\napres", output)
    }

    @Test
    fun testOrdFunction() {
        val code = """
            algorithme Test
            variables
                c : caractere
                code : entier
            debut
                c = 'A'
                code = ord(c)
                ecrireln(code)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("65\n", output)
    }

    @Test
    fun testChrFunction() {
        val code = """
            algorithme Test
            variables
                c : caractere
            debut
                c = chr(65)
                ecrireln(c)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("A\n", output)
    }

    @Test
    fun testOrdChrRoundTrip() {
        val code = """
            algorithme Test
            variables
                c1, c2 : caractere
            debut
                c1 = 'X'
                c2 = chr(ord(c1))
                ecrireln(c2)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("X\n", output)
    }

    @Test
    fun testArithmeticOperations() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 2 + 3 * 4
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("14\n", output)
    }

    @Test
    fun testDivAndMod() {
        val code = """
            algorithme Test
            variables
                q, r : entier
            debut
                q = 17 div 5
                r = 17 mod 5
                ecrire(q)
                ecrire(" ")
                ecrireln(r)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("3 2\n", output)
    }

    @Test
    fun testIfStatement() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 10
                si x > 5 alors
                    ecrireln("grand")
                sinon
                    ecrireln("petit")
                finsi
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("grand\n", output)
    }

    @Test
    fun testForLoop() {
        val code = """
            algorithme Test
            variables
                i, somme : entier
            debut
                somme = 0
                pour i de 1 à 5 faire
                    somme = somme + i
                finpour
                ecrireln(somme)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("15\n", output)
    }

    @Test
    fun testWhileLoop() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 0
                tantque x < 3 faire
                    x = x + 1
                fintantque
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("3\n", output)
    }

    @Test
    fun testRepeatUntilLoop() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 0
                repeter
                    x = x + 1
                jusqua x == 3
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("3\n", output)
    }

    @Test
    fun testWhenStatement() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 2
                selon(x)
                    cas 1 : ecrireln("un")
                    cas 2 : ecrireln("deux")
                    cas 3 : ecrireln("trois")
                    defaut : ecrireln("autre")
                finselon
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("deux\n", output)
    }

    @Test
    fun testWhenStatementDefault() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 99
                selon(x)
                    cas 1 : ecrireln("un")
                    cas 2 : ecrireln("deux")
                    defaut : ecrireln("autre")
                finselon
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("autre\n", output)
    }

    @Test
    fun testArray() {
        val code = """
            algorithme Test
            variables
                tab : tableau[3] de entier
            debut
                tab[0] = 10
                tab[1] = 20
                tab[2] = 30
                ecrireln(tab[1])
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("20\n", output)
    }

    @Test
    fun testConstant() {
        val code = """
            algorithme Test
            constantes
                PI : reel = 3.14
            debut
                ecrireln(PI)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("3.14\n", output)
    }

    @Test
    fun testConstantProtection() {
        val code = """
            algorithme Test
            constantes
                PI : reel = 3.14
            debut
                PI = 3.0
            fin
        """.trimIndent()

        assertFailsWith<Exception> {
            executeCode(code)
        }
    }

    @Test
    fun testStringConcatenation() {
        val code = """
            algorithme Test
            variables
                s1, s2 : chaine
            debut
                s1 = "Hello"
                s2 = " World"
                ecrireln(s1 + s2)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("Hello World\n", output)
    }

    @Test
    fun testEcrireWithoutNewline() {
        val code = """
            algorithme Test
            debut
                ecrire("Hello")
                ecrire(" ")
                ecrire("World")
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("Hello World", output)
    }

    @Test
    fun testEcrirelnWithNewline() {
        val code = """
            algorithme Test
            debut
                ecrireln("Line1")
                ecrireln("Line2")
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("Line1\nLine2\n", output)
    }

    @Test
    fun testEcrirelnEmpty() {
        val code = """
            algorithme Test
            debut
                ecrire("Before")
                ecrireln()
                ecrire("After")
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("Before\nAfter", output)
    }

    @Test
    fun testBuiltInMathFunctions() {
        val code = """
            algorithme Test
            variables
                x : reel
            debut
                x = abs(-5)
                ecrireln(x)
                x = racine(16)
                ecrireln(x)
                x = puissance(2, 3)
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("5\n4\n8\n", output)
    }

    @Test
    fun testBuiltInStringFunctions() {
        val code = """
            algorithme Test
            variables
                s : chaine
                len : entier
            debut
                s = "Hello"
                len = longueur(s)
                ecrireln(len)
                s = majuscule(s)
                ecrireln(s)
                s = minuscule(s)
                ecrireln(s)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("5\nHELLO\nhello\n", output)
    }

    @Test
    fun testBuiltInCharacterFunctions() {
        val code = """
            algorithme Test
            variables
                c : caractere
                result : booleen
            debut
                c = 'A'
                result = estLettre(c)
                ecrireln(result)
                result = estMajuscule(c)
                ecrireln(result)
                c = versMinuscule(c)
                ecrireln(c)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("true\ntrue\na\n", output)
    }

    @Test
    fun testConversionFunctions() {
        val code = """
            algorithme Test
            variables
                x : entier
                y : reel
                s : chaine
            debut
                x = versEntier(3.7)
                ecrireln(x)
                y = versReel(5)
                ecrireln(y)
                s = versChaine(42)
                ecrireln(s)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("3\n5\n42\n", output)
    }

    @Test
    fun testLogicalOperators() {
        val code = """
            algorithme Test
            variables
                a, b : booleen
            debut
                a = vrai et faux
                ecrireln(a)
                b = vrai ou faux
                ecrireln(b)
                a = non vrai
                ecrireln(a)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("false\ntrue\nfalse\n", output)
    }

    @Test
    fun testComparisonOperators() {
        val code = """
            algorithme Test
            variables
                result : booleen
            debut
                result = 5 == 5
                ecrireln(result)
                result = 5 != 3
                ecrireln(result)
                result = 5 > 3
                ecrireln(result)
                result = 3 < 5
                ecrireln(result)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("true\ntrue\ntrue\ntrue\n", output)
    }

    @Test
    fun testUserFunction() {
        val code = """
            algorithme Test
            fonction carre(x : entier) : entier
            debut
                retourner x * x
            fin
            variables
                result : entier
            debut
                result = carre(5)
                ecrireln(result)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("25\n", output)
    }

    @Test
    fun testUserProcedure() {
        val code = """
            algorithme Test
            procedure afficher(msg : chaine)
            debut
                ecrireln(msg)
            fin
            debut
                afficher("Hello")
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("Hello\n", output)
    }

    @Test
    fun testCaseInsensitivity() {
        val code = """
            algorithme Test
            VARIABLES
                X : ENTIER
            DEBUT
                X = 5
                ECRIRELN(X)
            FIN
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("5\n", output)
    }

    @Test
    fun testAccentOptional() {
        val code = """
            algorithme Test
            variables
                x : entier
            debut
                x = 5
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("5\n", output)
    }

    @Test
    fun testNoZeroAppendedToChar() {
        val code = """
            algorithme Test
            variables
                c : caractere
            debut
                ecrire(c)
                ecrireln("fin")
            fin
        """.trimIndent()

        val output = executeCode(code)
        // Le caractère nul initialisé ne doit pas afficher "0"
        assertEquals("fin\n", output)
    }

    @Test
    fun testPowerOperator() {
        val code = """
            algorithme Test
            variables
                x : reel
            debut
                x = 2 ** 3
                ecrireln(x)
                x = 5 ** 2
                ecrireln(x)
                x = 10 ** 0
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("8\n25\n1\n", output)
    }

    @Test
    fun testPowerOperatorPrecedence() {
        val code = """
            algorithme Test
            variables
                x : reel
            debut
                x = 2 + 3 ** 2
                ecrireln(x)
                x = 2 * 3 ** 2
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("11\n18\n", output)
    }

    @Test
    fun testPowerOperatorRightAssociative() {
        val code = """
            algorithme Test
            variables
                x : reel
            debut
                x = 2 ** 3 ** 2
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        // 2 ** (3 ** 2) = 2 ** 9 = 512
        assertEquals("512\n", output)
    }

    @Test
    fun testLogicalOperatorsDetailed() {
        val code = """
            algorithme Test
            variables
                a, b, c : booleen
            debut
                a = vrai et vrai
                ecrireln(a)
                b = vrai et faux
                ecrireln(b)
                c = faux et faux
                ecrireln(c)
                
                a = vrai ou vrai
                ecrireln(a)
                b = vrai ou faux
                ecrireln(b)
                c = faux ou faux
                ecrireln(c)
                
                a = non vrai
                ecrireln(a)
                b = non faux
                ecrireln(b)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("true\nfalse\nfalse\ntrue\ntrue\nfalse\nfalse\ntrue\n", output)
    }

    @Test
    fun testLogicalOperatorsWithComparison() {
        val code = """
            algorithme Test
            variables
                x, y : entier
                result : booleen
            debut
                x = 5
                y = 10
                result = x > 0 et y < 20
                ecrireln(result)
                result = x > 10 ou y == 10
                ecrireln(result)
                result = non (x == y)
                ecrireln(result)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("true\ntrue\ntrue\n", output)
    }

    @Test
    fun testComplexLogicalExpression() {
        val code = """
            algorithme Test
            variables
                a, b, c : booleen
                result : booleen
            debut
                a = vrai
                b = faux
                c = vrai
                result = (a ou b) et c
                ecrireln(result)
                result = a et b ou c
                ecrireln(result)
                result = non a ou b et c
                ecrireln(result)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("true\ntrue\nfalse\n", output)
    }

    @Test
    fun testPowerWithNegativeBase() {
        val code = """
            algorithme Test
            variables
                x : reel
            debut
                x = (-2) ** 3
                ecrireln(x)
                x = (-2) ** 2
                ecrireln(x)
            fin
        """.trimIndent()

        val output = executeCode(code)
        assertEquals("-8\n4\n", output)
    }
}
