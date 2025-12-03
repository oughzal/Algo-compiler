package com.algocompiler

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: java -jar algoc.jar <fichier.algo>")
        exitProcess(1)
    }

    val fileName = args[0]
    val file = File(fileName)

    if (!file.exists()) {
        println("Erreur: Le fichier '$fileName' n'existe pas")
        exitProcess(1)
    }

    if (!file.name.endsWith(".algo")) {
        println("Erreur: Le fichier doit avoir l'extension .algo")
        exitProcess(1)
    }

    try {
        // Lire le code et normaliser les quotes typographiques
        var code = file.readText()

        // Remplacer les guillemets typographiques par des guillemets ASCII
        code = code.replace('\u2018', '\'')  // ' → '
                   .replace('\u2019', '\'')  // ' → '
                   .replace('\u201C', '"')   // " → "
                   .replace('\u201D', '"')   // " → "

        val lexer = Lexer(code)
        val tokens = lexer.tokenize()

        // Mode debug : afficher les tokens si demandé
        if (System.getProperty("debug.tokens") != null) {
            println("--- TOKENS ---")
            for (t in tokens) {
                println("${t.line}:${t.column} ${t.type} -> '${t.value}'")
            }
            return
        }

        val parser = Parser(tokens)
        val ast = parser.parse()
        val interpreter = Interpreter()
        interpreter.execute(ast)
    } catch (e: Exception) {
        println("Erreur: ${e.message}")
        exitProcess(1)
    }
}
