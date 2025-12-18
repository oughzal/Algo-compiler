package com.algocompiler

import kotlin.test.*

class PassageReferenceTest {

    @Test
    fun testPassageReferenceSimple() {
        val code = """
            algorithme Test
            variables
                x : entier
            
            procedure doubler(ref n : entier)
            debut
                n = n * 2
            fin
            
            debut
                x = 5
                doubler(x)
                ecrireln("x = ", x)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        
        assertNotNull(program)
        assertEquals(1, program.functions.size)
        assertTrue(program.functions[0].parameters[0].isByReference)
        println("✓ Test passage référence simple réussi")
    }

    @Test
    fun testEchangeValeurs() {
        val code = """
            algorithme Test
            variables
                a, b : entier
            
            procedure echanger(ref x : entier, ref y : entier)
            variables
                temp : entier
            debut
                temp = x
                x = y
                y = temp
            fin
            
            debut
                a = 10
                b = 20
                echanger(a, b)
                ecrireln("a = ", a, ", b = ", b)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        
        assertNotNull(program)
        val fonction = program.functions[0]
        assertEquals(2, fonction.parameters.size)
        assertTrue(fonction.parameters[0].isByReference)
        assertTrue(fonction.parameters[1].isByReference)
        println("✓ Test échange valeurs réussi")
    }

    @Test
    fun testPassageValeurEtReference() {
        val code = """
            algorithme Test
            variables
                x, y : entier
            
            procedure ajouterA(ref resultat : entier, valeur : entier)
            debut
                resultat = resultat + valeur
            fin
            
            debut
                x = 10
                y = 5
                ajouterA(x, y)
                ecrireln("x = ", x)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        
        assertNotNull(program)
        val procedure = program.functions[0]
        assertTrue(procedure.parameters[0].isByReference, "Premier paramètre devrait être par référence")
        assertFalse(procedure.parameters[1].isByReference, "Deuxième paramètre devrait être par valeur")
        println("✓ Test mélange valeur/référence réussi")
    }

    @Test
    fun testFonctionAvecReference() {
        val code = """
            algorithme Test
            variables
                n : entier
            
            fonction incrementer(ref x : entier) : entier
            variables
                ancien : entier
            debut
                ancien = x
                x = x + 1
                retourner ancien
            fin
            
            debut
                n = 5
                ecrireln("Ancien: ", incrementer(n))
                ecrireln("Nouveau: ", n)
            fin
        """.trimIndent()

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser(tokens)
        val program = parser.parse()
        
        assertNotNull(program)
        assertTrue(program.functions[0].parameters[0].isByReference)
        println("✓ Test fonction avec référence réussi")
    }
}

