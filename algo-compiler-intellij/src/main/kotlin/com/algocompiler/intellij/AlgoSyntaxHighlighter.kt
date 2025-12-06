package com.algocompiler.intellij

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class AlgoSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val KEYWORD = TextAttributesKey.createTextAttributesKey(
            "ALGO_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD
        )
        val TYPE = TextAttributesKey.createTextAttributesKey(
            "ALGO_TYPE", DefaultLanguageHighlighterColors.CLASS_NAME
        )
        val STRING = TextAttributesKey.createTextAttributesKey(
            "ALGO_STRING", DefaultLanguageHighlighterColors.STRING
        )
        val NUMBER = TextAttributesKey.createTextAttributesKey(
            "ALGO_NUMBER", DefaultLanguageHighlighterColors.NUMBER
        )
        val COMMENT = TextAttributesKey.createTextAttributesKey(
            "ALGO_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT
        )
        val IDENTIFIER = TextAttributesKey.createTextAttributesKey(
            "ALGO_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER
        )
        val OPERATOR = TextAttributesKey.createTextAttributesKey(
            "ALGO_OPERATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN
        )
        val CHARACTER = TextAttributesKey.createTextAttributesKey(
            "ALGO_CHARACTER", DefaultLanguageHighlighterColors.STRING
        )
        val BOOLEAN = TextAttributesKey.createTextAttributesKey(
            "ALGO_BOOLEAN", DefaultLanguageHighlighterColors.KEYWORD
        )
    }

    override fun getHighlightingLexer(): Lexer = AlgoLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
        return when (tokenType) {
            AlgoTokenTypes.KEYWORD -> arrayOf(KEYWORD)
            AlgoTokenTypes.TYPE -> arrayOf(TYPE)
            AlgoTokenTypes.STRING -> arrayOf(STRING)
            AlgoTokenTypes.CHARACTER -> arrayOf(CHARACTER)
            AlgoTokenTypes.NUMBER -> arrayOf(NUMBER)
            AlgoTokenTypes.COMMENT -> arrayOf(COMMENT)
            AlgoTokenTypes.IDENTIFIER -> arrayOf(IDENTIFIER)
            AlgoTokenTypes.OPERATOR -> arrayOf(OPERATOR)
            AlgoTokenTypes.BOOLEAN -> arrayOf(BOOLEAN)
            else -> emptyArray()
        }
    }
}

