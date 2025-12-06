package com.algocompiler.intellij

import com.intellij.psi.tree.IElementType

object AlgoTokenTypes {
    val KEYWORD = IElementType("KEYWORD", AlgoLanguage)
    val TYPE = IElementType("TYPE", AlgoLanguage)
    val IDENTIFIER = IElementType("IDENTIFIER", AlgoLanguage)
    val NUMBER = IElementType("NUMBER", AlgoLanguage)
    val STRING = IElementType("STRING", AlgoLanguage)
    val CHARACTER = IElementType("CHARACTER", AlgoLanguage)
    val BOOLEAN = IElementType("BOOLEAN", AlgoLanguage)
    val OPERATOR = IElementType("OPERATOR", AlgoLanguage)
    val COMMENT = IElementType("COMMENT", AlgoLanguage)
    val WHITESPACE = IElementType("WHITESPACE", AlgoLanguage)
}

