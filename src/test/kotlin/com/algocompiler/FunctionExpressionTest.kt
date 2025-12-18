package com.algocompiler

import kotlin.test.*

class FunctionExpressionTest {

    @Test
    fun testFunctionCallInExpression() {
        val code = """
            algorithme Test
            variables
                a, b, resultat : réel
            debut
                a = 4
                b = 5
                resultat = racine(a) + b
                ecrireln("Résultat: ", resultat)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertNotNull(program)
        println("✓ Test parsing racine(a) + b dans affectation réussi")
    }

    @Test
    fun testFunctionCallStandaloneExpression() {
        val code = """
            algorithme Test
            variables
                a, b : réel
            debut
                a = 4
                b = 5
                racine(a) + b
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertNotNull(program)
        println("✓ Test parsing racine(a) + b comme expression standalone réussi")
    }

    @Test
    fun testMultipleFunctionCallsInExpression() {
        val code = """
            algorithme Test
            variables
                x, y : réel
            debut
                x = abs(-3) * puissance(2, 3)
                y = racine(16) + abs(-5)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertNotNull(program)
        println("✓ Test parsing expressions avec multiples appels de fonctions réussi")
    }

    @Test
    fun testComplexExpressionStartingWithFunction() {
        val code = """
            algorithme Test
            variables
                resultat : réel
            debut
                resultat = puissance(2, 3) + racine(16) * abs(-2)
                puissance(2, 3) + racine(16)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()

        assertNotNull(program)
        println("✓ Test parsing expression complexe commençant par fonction réussi")
    }
}

