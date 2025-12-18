package com.algocompiler

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ShortCircuitTest {

    @Test
    fun testShortCircuitET() {
        // Test que l'opérateur ET ne évalue pas le second opérande si le premier est faux
        val code = """
            algorithme TestET
            variables
                x : entier
                resultat : booleen
            debut
                x = 0
                resultat = (x == 1) et (10 / x > 0)
                ecrireln("Résultat: ", resultat)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        val interpreter = Interpreter()

        // Capturer la sortie
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        // Exécuter sans erreur de division par zéro
        assertDoesNotThrow {
            interpreter.execute(program)
        }

        System.setOut(originalOut)
        val output = outputStream.toString().trim()

        // Vérifier que le résultat est false et qu'il n'y a pas eu d'erreur
        assertTrue(output.contains("false"))
    }

    @Test
    fun testShortCircuitOU() {
        // Test que l'opérateur OU ne évalue pas le second opérande si le premier est vrai
        val code = """
            algorithme TestOU
            variables
                x : entier
                resultat : booleen
            debut
                x = 0
                resultat = (x == 0) ou (10 / x > 0)
                ecrireln("Résultat: ", resultat)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        val interpreter = Interpreter()

        // Capturer la sortie
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        // Exécuter sans erreur de division par zéro
        assertDoesNotThrow {
            interpreter.execute(program)
        }

        System.setOut(originalOut)
        val output = outputStream.toString().trim()

        // Vérifier que le résultat est true et qu'il n'y a pas eu d'erreur
        assertTrue(output.contains("true"))
    }

    @Test
    fun testETWithBothTrue() {
        // Test ET avec les deux opérandes vrais
        val code = """
            algorithme TestETBothTrue
            variables
                resultat : booleen
            debut
                resultat = (5 > 3) et (10 < 20)
                ecrireln(resultat)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        val interpreter = Interpreter()

        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        interpreter.execute(program)

        System.setOut(originalOut)
        val output = outputStream.toString().trim()

        assertTrue(output.contains("true"))
    }

    @Test
    fun testOUWithBothFalse() {
        // Test OU avec les deux opérandes faux
        val code = """
            algorithme TestOUBothFalse
            variables
                resultat : booleen
            debut
                resultat = (5 < 3) ou (10 > 20)
                ecrireln(resultat)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        val interpreter = Interpreter()

        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        interpreter.execute(program)

        System.setOut(originalOut)
        val output = outputStream.toString().trim()

        assertTrue(output.contains("false"))
    }

    @Test
    fun testComplexShortCircuit() {
        // Test avec des expressions plus complexes
        val code = """
            algorithme TestComplexe
            variables
                a, b, c : entier
                resultat : booleen
            debut
                a = 5
                b = 10
                c = 0
                resultat = (a < b) et (b > 3) et (c == 0)
                ecrireln("Test 1: ", resultat)
                
                resultat = (a > b) ou (b < 5) ou (c == 0)
                ecrireln("Test 2: ", resultat)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        val interpreter = Interpreter()

        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(PrintStream(outputStream))

        interpreter.execute(program)

        System.setOut(originalOut)
        val output = outputStream.toString().trim()

        assertTrue(output.contains("Test 1: true"))
        assertTrue(output.contains("Test 2: true"))
    }
}

