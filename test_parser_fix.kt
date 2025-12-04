import com.algocompiler.*

fun main() {
    println("Test du parser corrigé...")

    val code = """
        algorithme Test
        variables
            x : entier
        debut
            x = 5
            ecrireln(x)
        fin
    """.trimIndent()

    try {
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        println("Tokens: ${tokens.size}")

        val parser = Parser(tokens)
        val program = parser.parse()
        println("Programme parsé avec succès: ${program.name}")
        println("Variables: ${program.variables.size}")
        println("Statements: ${program.statements.size}")
        println("✓ Test réussi!")
    } catch (e: Exception) {
        println("✗ Erreur: ${e.message}")
        e.printStackTrace()
    }
}

