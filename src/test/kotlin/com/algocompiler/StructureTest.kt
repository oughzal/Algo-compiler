
package com.algocompiler

import kotlin.test.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class StructureTest {

    private fun executeCode(code: String): String {
        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out

        try {
            System.setOut(PrintStream(outputStream))

            val lexer = Lexer(code)
            val tokens = lexer.tokenize()
            val parser = Parser(tokens)
            val program = parser.parse()

            Interpreter().execute(program)

            return outputStream.toString().replace("\r\n", "\n")
        } finally {
            System.setOut(originalOut)
        }
    }

    @Test
    fun testBasicStructureFieldAssignmentAndRead() {
        val code = """
            algorithme Test
            type
                Etudiant = enregistrement
                    age : entier
                    nom : chaine
                finenregistrement
            variables
                e : Etudiant
            debut
                e.age <- 20
                e.nom <- "Ali"
                ecrireln(e.age)
                ecrireln(e.nom)
            fin
        """.trimIndent()


        assertEquals("20\nAli\n", executeCode(code))
    }

    @Test
    fun testNestedStructureFieldAccess() {
        val code = """
            algorithme Test
            type
                Adresse = enregistrement
                    ville : chaine
                finenregistrement

                Etudiant = enregistrement
                    adresse : Adresse
                finenregistrement
            variables
                e : Etudiant
            debut
                e.adresse.ville <- "Skikda"
                ecrireln(e.adresse.ville)
            fin
        """.trimIndent()

        assertEquals("Skikda\n", executeCode(code))
    }

    @Test
    fun testStructureDefaultValues() {
        val code = """
            algorithme Test
            type
                Etudiant = enregistrement
                    age : entier
                    nom : chaine
                    actif : booleen
                finenregistrement
            variables
                e : Etudiant
            debut
                ecrireln(e.age)
                ecrireln(e.nom)
                ecrireln(e.actif)
            fin
        """.trimIndent()

        assertEquals("0\n\nfalse\n", executeCode(code))
    }

    @Test
    fun testUnknownStructureFieldFails() {
        val code = """
            algorithme Test
            type
                Etudiant = enregistrement
                    age : entier
                finenregistrement
            variables
                e : Etudiant
            debut
                e.inconnu <- 10
            fin
        """.trimIndent()

        val exception = assertFailsWith<Exception> {
            executeCode(code)
        }

        assertTrue(exception.message!!.contains("non déclaré"))
    }
}
