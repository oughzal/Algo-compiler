package com.algocompiler.intellij

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

class AlgoCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(),
            AlgoCompletionProvider()
        )
    }
}

class AlgoCompletionProvider : CompletionProvider<CompletionParameters>() {
    private val keywords = listOf(
        "algorithme", "debut", "fin", "variables", "var", "constantes", "const",
        "si", "alors", "sinon", "sinonsi", "finsi",
        "pour", "de", "à", "faire", "finpour",
        "tantque", "fintantque", "repeter", "jusqua",
        "selon", "cas", "defaut", "finselon",
        "ecrire", "ecrireln", "lire",
        "fonction", "procedure", "retourner",
        "et", "ou", "non", "vrai", "faux"
    )

    private val types = listOf("entier", "reel", "chaine", "caractere", "booleen", "tableau")

    private val functions = listOf(
        // Mathématiques
        "abs", "racine", "puissance", "arrondi", "plancher", "plafond",
        "min", "max", "reste", "quotient",
        "sin", "cos", "tan", "log", "exp",
        // Chaînes
        "longueur", "majuscule", "minuscule", "sousChaine",
        "estVide", "contient", "commence", "termine", "position",
        // Caractères
        "ord", "chr", "estLettre", "estChiffre",
        "estMajuscule", "estMinuscule", "versMajuscule", "versMinuscule",
        // Conversions
        "versEntier", "versReel", "versChaine",
        // Aléatoire
        "aleatoire"
    )

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        keywords.forEach { keyword ->
            result.addElement(
                LookupElementBuilder.create(keyword)
                    .withTypeText("mot-clé")
                    .bold()
            )
        }

        types.forEach { type ->
            result.addElement(
                LookupElementBuilder.create(type)
                    .withTypeText("type")
            )
        }

        functions.forEach { function ->
            result.addElement(
                LookupElementBuilder.create(function)
                    .withTypeText("fonction")
                    .withInsertHandler { context, _ ->
                        context.document.insertString(context.tailOffset, "()")
                        context.editor.caretModel.moveToOffset(context.tailOffset - 1)
                    }
            )
        }
    }
}

