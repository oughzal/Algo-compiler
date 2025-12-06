package com.algocompiler.intellij

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType

class AlgoLexerAdapter : LexerBase() {
    private var buffer: CharSequence = ""
    private var startOffset: Int = 0
    private var endOffset: Int = 0
    private var currentOffset: Int = 0
    private var currentTokenType: IElementType? = null

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        this.buffer = buffer
        this.startOffset = startOffset
        this.endOffset = endOffset
        this.currentOffset = startOffset
        advance()
    }

    override fun getState(): Int = 0

    override fun getTokenType(): IElementType? = currentTokenType

    override fun getTokenStart(): Int = startOffset

    override fun getTokenEnd(): Int = currentOffset

    override fun advance() {
        if (currentOffset >= endOffset) {
            currentTokenType = null
            return
        }

        startOffset = currentOffset

        val char = buffer[currentOffset]
        when {
            char.isWhitespace() -> {
                while (currentOffset < endOffset && buffer[currentOffset].isWhitespace()) {
                    currentOffset++
                }
                currentTokenType = AlgoTokenTypes.WHITESPACE
            }
            char.isLetter() -> {
                while (currentOffset < endOffset &&
                       (buffer[currentOffset].isLetterOrDigit() || buffer[currentOffset] == '_')) {
                    currentOffset++
                }
                val text = buffer.substring(startOffset, currentOffset).lowercase()
                currentTokenType = when (text) {
                    "algorithme", "debut", "fin", "variables", "constantes",
                    "si", "alors", "sinon", "sinonsi", "finsi",
                    "pour", "de", "faire", "finpour",
                    "tantque", "fintantque", "repeter", "jusqua",
                    "selon", "cas", "finselon",
                    "ecrire", "ecrireln", "lire",
                    "fonction", "procedure", "retourner",
                    "et", "ou", "non" -> AlgoTokenTypes.KEYWORD
                    "entier", "reel", "chaine", "caractere", "booleen", "tableau" -> AlgoTokenTypes.TYPE
                    "vrai", "faux" -> AlgoTokenTypes.BOOLEAN
                    else -> AlgoTokenTypes.IDENTIFIER
                }
            }
            char.isDigit() -> {
                while (currentOffset < endOffset &&
                       (buffer[currentOffset].isDigit() || buffer[currentOffset] == '.')) {
                    currentOffset++
                }
                currentTokenType = AlgoTokenTypes.NUMBER
            }
            char == '"' -> {
                currentOffset++
                while (currentOffset < endOffset && buffer[currentOffset] != '"') {
                    if (buffer[currentOffset] == '\\') currentOffset++
                    currentOffset++
                }
                if (currentOffset < endOffset) currentOffset++
                currentTokenType = AlgoTokenTypes.STRING
            }
            char == '\'' -> {
                currentOffset++
                while (currentOffset < endOffset && buffer[currentOffset] != '\'') {
                    if (buffer[currentOffset] == '\\') currentOffset++
                    currentOffset++
                }
                if (currentOffset < endOffset) currentOffset++
                currentTokenType = AlgoTokenTypes.CHARACTER
            }
            char == '/' && currentOffset + 1 < endOffset && buffer[currentOffset + 1] == '/' -> {
                while (currentOffset < endOffset && buffer[currentOffset] != '\n') {
                    currentOffset++
                }
                currentTokenType = AlgoTokenTypes.COMMENT
            }
            char == '#' -> {
                while (currentOffset < endOffset && buffer[currentOffset] != '\n') {
                    currentOffset++
                }
                currentTokenType = AlgoTokenTypes.COMMENT
            }
            else -> {
                currentOffset++
                currentTokenType = AlgoTokenTypes.OPERATOR
            }
        }
    }

    override fun getBufferSequence(): CharSequence = buffer

    override fun getBufferEnd(): Int = endOffset
}

