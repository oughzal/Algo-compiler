package com.algocompiler.intellij

import com.intellij.lang.Language

object AlgoLanguage : Language("Algo") {
    override fun getDisplayName(): String = "Algo"
    override fun isCaseSensitive(): Boolean = false
}

