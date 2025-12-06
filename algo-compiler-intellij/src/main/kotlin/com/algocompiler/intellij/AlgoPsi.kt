package com.algocompiler.intellij

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.ASTNode
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class AlgoFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, AlgoLanguage) {
    override fun getFileType(): FileType = AlgoFileType
    override fun toString(): String = "Algo File"
}

class AlgoPsiElement(node: ASTNode) : ASTWrapperPsiElement(node)

