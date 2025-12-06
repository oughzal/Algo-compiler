package com.algocompiler.intellij

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object AlgoFileType : LanguageFileType(AlgoLanguage) {
    override fun getName(): String = "Algo File"
    override fun getDescription(): String = "Fichier de pseudo-code Algo"
    override fun getDefaultExtension(): String = "algo"
    override fun getIcon(): Icon? = AlgoIcons.FILE
}

