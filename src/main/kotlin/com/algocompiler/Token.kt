package com.algocompiler

enum class TokenType {
    // Mots-clés
    ALGORITHME,
    DEBUT,
    FIN,
    VARIABLES,
    CONSTANTES,
    SI,
    ALORS,
    SINON,
    FINSI,
    POUR,
    DE,
    A,
    FAIRE,
    FINPOUR,
    TANTQUE,
    FINTANTQUE,
    REPETER,
    JUSQUA,
    ECRIRE,
    ECRIRELN,
    LIRE,
    FONCTION,
    PROCEDURE,
    RETOURNER,
    TABLEAU,

    // Types
    ENTIER,
    REEL,
    CHAINE,
    CARACTERE,
    BOOLEEN,

    // Opérateurs
    PLUS,
    MOINS,
    MULT,
    DIV,
    DIV_ENTIERE,
    MOD,
    AFFECTATION,
    EGAL,
    DIFFERENT,
    INFERIEUR,
    SUPERIEUR,
    INFERIEUR_EGAL,
    SUPERIEUR_EGAL,
    ET,
    OU,
    NON,

    // Délimiteurs
    VIRGULE,
    DEUX_POINTS,
    POINT_VIRGULE,
    PAREN_GAUCHE,
    PAREN_DROITE,
    CROCHET_GAUCHE,
    CROCHET_DROIT,

    // Littéraux
    NOMBRE,
    TEXTE,
    CARACTERE_LITERAL,
    VRAI,
    FAUX,
    IDENTIFICATEUR,

    // Fin
    EOF,
    NEWLINE
}

data class Token(val type: TokenType, val value: String, val line: Int, val column: Int)
