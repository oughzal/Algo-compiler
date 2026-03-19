package com.algocompiler.mobile.data

import android.os.Environment
import com.algocompiler.mobile.compiler.Interpreter
import com.algocompiler.mobile.compiler.Lexer
import com.algocompiler.mobile.compiler.Parser
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class FileManager {
    private val baseDir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
        "AlgoCompiler"
    )

    init {
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
    }

    fun getAllFiles(path: String = ""): List<AlgoFile> {
        val currentDir = if (path.isEmpty()) baseDir else File(baseDir, path)
        if (!currentDir.exists()) return emptyList()

        return currentDir.listFiles()?.mapNotNull { file ->
            try {
                AlgoFile(
                    name = file.name,
                    path = path,
                    isDirectory = file.isDirectory,
                    file = file
                )
            } catch (e: Exception) {
                null
            }
        }?.sortedWith(compareBy({ !it.isDirectory }, { it.name.lowercase() })) ?: emptyList()
    }

    fun createFile(name: String, path: String = ""): AlgoFile? {
        return try {
            val fileName = if (name.endsWith(".algo")) name else "$name.algo"
            val dir = if (path.isEmpty()) baseDir else File(baseDir, path)
            val file = File(dir, fileName)

            if (file.exists()) {
                null
            } else {
                file.createNewFile()
                file.writeText("""algorithme ${name.removeSuffix(".algo")}
variables
    
debut
    ecrireln("Hello World!")
fin
""")
                AlgoFile(fileName, path, false, file)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun createFolder(name: String, path: String = ""): Boolean {
        return try {
            val dir = if (path.isEmpty()) baseDir else File(baseDir, path)
            val folder = File(dir, name)
            folder.mkdirs()
        } catch (e: Exception) {
            false
        }
    }

    fun readFile(file: AlgoFile): String? {
        return try {
            file.file.readText()
        } catch (e: Exception) {
            null
        }
    }

    fun writeFile(file: AlgoFile, content: String): Boolean {
        return try {
            file.file.writeText(content)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun deleteFile(file: AlgoFile): Boolean {
        return try {
            if (file.isDirectory) {
                file.file.deleteRecursively()
            } else {
                file.file.delete()
            }
        } catch (e: Exception) {
            false
        }
    }

    fun renameFile(file: AlgoFile, newName: String): Boolean {
        return try {
            val newFile = File(file.file.parentFile, newName)
            file.file.renameTo(newFile)
        } catch (e: Exception) {
            false
        }
    }

    fun executeAlgorithm(file: AlgoFile): String {
        return try {
            val code = readFile(file) ?: return "Erreur: Impossible de lire le fichier"

            // Capture output
            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream, true, "UTF-8")
            val originalOut = System.out
            System.setOut(printStream)

            try {
                // Normalize typographic quotes
                val normalizedCode = code
                    .replace('\u2018', '\'')
                    .replace('\u2019', '\'')
                    .replace('\u201C', '"')
                    .replace('\u201D', '"')

                val lexer = Lexer(normalizedCode)
                val tokens = lexer.tokenize()
                val parser = Parser(tokens)
                val ast = parser.parse()
                val interpreter = Interpreter()
                interpreter.execute(ast)

                outputStream.toString("UTF-8")
            } finally {
                System.setOut(originalOut)
                printStream.close()
            }
        } catch (e: Exception) {
            "Erreur: ${e.message}"
        }
    }
}

