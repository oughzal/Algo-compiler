
package com.algocompiler

enum class TokenType {
    // Mots-clés
    ALGORITHME,
    DEBUT,
    FIN,
    VARIABLES,
    CONSTANTES,
    TYPE,
    ENREGISTREMENT,
    FINENREGISTREMENT,
    SI,
    ALORS,
    SINON,
    SINONSI,
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
    SELON,
    CAS,
    DEFAUT,
    FINSELON,
    ECRIRE,
    ECRIRELN,
    LIRE,
    FONCTION,
    PROCEDURE,
    RETOURNER,
    TABLEAU,
    VAR,
    PAS,

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
    ADRESSE,
    DIV,
    DIV_ENTIERE,
    MOD,
    PUISSANCE,
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
    POINT,
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

data class Token(
    val type: TokenType,
    val value: String,
    val line: Int,
    val column: Int
)
