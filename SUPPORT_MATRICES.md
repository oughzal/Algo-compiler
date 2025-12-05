# 📊 Support des Matrices (Tableaux 2D)

## 🎉 Nouvelle Fonctionnalité v1.6.0

Le compilateur Algo-Compiler supporte maintenant les **matrices** (tableaux à deux dimensions) !

---

## 📝 Syntaxe

### Déclaration

```algo
variables
    nomMatrice : tableau[lignes][colonnes] de type
```

#### Exemples

```algo
variables
    matrice : tableau[3][3] de entier      // Matrice 3x3 d'entiers
    notes : tableau[5][10] de reel         // Matrice 5x10 de réels
    grille : tableau[8][8] de caractere    // Matrice 8x8 de caractères
```

### Accès aux Éléments

```algo
valeur = matrice[ligne][colonne]
```

#### Exemples

```algo
x = matrice[0][0]         // Premier élément (coin supérieur gauche)
y = matrice[2][1]         // Ligne 2, Colonne 1
z = notes[i][j]           // Avec variables
```

### Affectation

```algo
matrice[ligne][colonne] = valeur
```

#### Exemples

```algo
matrice[0][0] = 10
matrice[i][j] = 25
notes[1][3] = 15.5
```

---

## 💡 Exemples Complets

### Exemple 1 : Matrice Simple

```algo
algorithme MatriceSimple

variables
    M : tableau[2][2] de entier
    i, j : entier

debut
    // Initialisation
    M[0][0] = 1
    M[0][1] = 2
    M[1][0] = 3
    M[1][1] = 4
    
    // Affichage
    pour i de 0 à 1 faire
        pour j de 0 à 1 faire
            ecrire(M[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

**Sortie** :
```
1 2
3 4
```

### Exemple 2 : Initialisation avec Boucles

```algo
algorithme InitialisationMatrice

variables
    M : tableau[3][3] de entier
    i, j : entier

debut
    // Remplir avec i * j
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            M[i][j] = i * j
        finpour
    finpour
    
    // Afficher
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            ecrire(M[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

**Sortie** :
```
0 0 0
0 1 2
0 2 4
```

### Exemple 3 : Addition de Matrices

```algo
algorithme AdditionMatrices

variables
    A, B, C : tableau[2][2] de entier
    i, j : entier

debut
    // Initialiser A
    A[0][0] = 1
    A[0][1] = 2
    A[1][0] = 3
    A[1][1] = 4
    
    // Initialiser B
    B[0][0] = 5
    B[0][1] = 6
    B[1][0] = 7
    B[1][1] = 8
    
    // Calculer C = A + B
    pour i de 0 à 1 faire
        pour j de 0 à 1 faire
            C[i][j] = A[i][j] + B[i][j]
        finpour
    finpour
    
    // Afficher résultat
    ecrireln("C = A + B :")
    pour i de 0 à 1 faire
        pour j de 0 à 1 faire
            ecrire(C[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

**Sortie** :
```
C = A + B :
6 8
10 12
```

### Exemple 4 : Recherche dans une Matrice

```algo
algorithme RechercheMatrice

variables
    M : tableau[3][3] de entier
    i, j, valeur : entier
    trouve : booleen

debut
    // Initialiser
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            M[i][j] = (i + 1) * 10 + (j + 1)
        finpour
    finpour
    
    // Rechercher une valeur
    valeur = 22
    trouve = faux
    
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            si M[i][j] = valeur alors
                trouve = vrai
                ecrireln("Valeur ", valeur, " trouvée en [", i, "][", j, "]")
            finsi
        finpour
    finpour
    
    si non trouve alors
        ecrireln("Valeur non trouvée")
    finsi
fin
```

### Exemple 5 : Transposition

```algo
algorithme TranspositionMatrice

variables
    A, T : tableau[3][2] de entier
    i, j : entier

debut
    // Initialiser A (3 lignes, 2 colonnes)
    A[0][0] = 1
    A[0][1] = 2
    A[1][0] = 3
    A[1][1] = 4
    A[2][0] = 5
    A[2][1] = 6
    
    ecrireln("Matrice A (3x2) :")
    pour i de 0 à 2 faire
        pour j de 0 à 1 faire
            ecrire(A[i][j], " ")
        finpour
        ecrireln()
    finpour
    
    // Transposer : T[j][i] = A[i][j]
    // Note : T devrait être tableau[2][3]
    // Pour cet exemple, on affiche juste la transposée
    ecrireln()
    ecrireln("Transposée T (2x3) :")
    pour j de 0 à 1 faire
        pour i de 0 à 2 faire
            ecrire(A[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

### Exemple 6 : Calcul de Sommes

```algo
algorithme SommesMatrice

variables
    M : tableau[3][3] de entier
    i, j, sommeTotal, sommeLigne, sommeColonne : entier

debut
    // Initialiser
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            M[i][j] = (i + 1) * (j + 1)
        finpour
    finpour
    
    // Afficher
    ecrireln("Matrice :")
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            ecrire(M[i][j], " ")
        finpour
        ecrireln()
    finpour
    ecrireln()
    
    // Somme totale
    sommeTotal = 0
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            sommeTotal = sommeTotal + M[i][j]
        finpour
    finpour
    ecrireln("Somme totale : ", sommeTotal)
    
    // Somme par ligne
    pour i de 0 à 2 faire
        sommeLigne = 0
        pour j de 0 à 2 faire
            sommeLigne = sommeLigne + M[i][j]
        finpour
        ecrireln("Somme ligne ", i, " : ", sommeLigne)
    finpour
    
    // Somme par colonne
    pour j de 0 à 2 faire
        sommeColonne = 0
        pour i de 0 à 2 faire
            sommeColonne = sommeColonne + M[i][j]
        finpour
        ecrireln("Somme colonne ", j, " : ", sommeColonne)
    finpour
fin
```

### Exemple 7 : Matrice Identité

```algo
algorithme MatriceIdentite

variables
    I : tableau[4][4] de entier
    i, j : entier

debut
    // Initialiser matrice identité
    pour i de 0 à 3 faire
        pour j de 0 à 3 faire
            si i = j alors
                I[i][j] = 1
            sinon
                I[i][j] = 0
            finsi
        finpour
    finpour
    
    // Afficher
    ecrireln("Matrice Identité 4x4 :")
    pour i de 0 à 3 faire
        pour j de 0 à 3 faire
            ecrire(I[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

**Sortie** :
```
1 0 0 0
0 1 0 0
0 0 1 0
0 0 0 1
```

### Exemple 8 : Table de Multiplication

```algo
algorithme TableMultiplication

variables
    table : tableau[10][10] de entier
    i, j : entier

debut
    // Remplir la table
    pour i de 0 à 9 faire
        pour j de 0 à 9 faire
            table[i][j] = (i + 1) * (j + 1)
        finpour
    finpour
    
    // Afficher
    ecrireln("Table de multiplication :")
    pour i de 0 à 9 faire
        pour j de 0 à 9 faire
            ecrire(table[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

---

## 🎓 Applications Pratiques

### 1. Jeux de Plateau

```algo
// Plateau de jeu (échecs, dames, etc.)
variables
    plateau : tableau[8][8] de caractere
```

### 2. Traitement d'Images

```algo
// Image en niveaux de gris
variables
    image : tableau[100][100] de entier
```

### 3. Tableaux de Notes

```algo
// Notes : étudiants × matières
variables
    notes : tableau[30][5] de reel
```

### 4. Grilles de Calcul

```algo
// Feuille de calcul simple
variables
    grille : tableau[20][10] de reel
```

---

## ⚙️ Détails Techniques

### Initialisation

Les matrices sont automatiquement initialisées avec des valeurs par défaut :
- **entier** : `0`
- **reel** : `0.0`
- **chaine** : `""`
- **caractere** : `'\u0000'`
- **booleen** : `faux`

### Indices

- Les indices commencent à **0**
- Premier élément : `M[0][0]`
- Dernier élément d'une matrice NxM : `M[N-1][M-1]`

### Limites

- Index ligne doit être : `0 <= ligne < nombre_lignes`
- Index colonne doit être : `0 <= colonne < nombre_colonnes`
- Un dépassement génère une erreur

---

## 🎯 Bonnes Pratiques

### 1. Nommage Clair

```algo
// ✅ Bon
variables
    grille : tableau[10][10] de entier
    notes : tableau[20][5] de reel

// ❌ Moins clair
variables
    g : tableau[10][10] de entier
    x : tableau[20][5] de reel
```

### 2. Boucles Imbriquées

```algo
// ✅ Bon : parcours ligne par ligne
pour i de 0 à lignes - 1 faire
    pour j de 0 à colonnes - 1 faire
        // Traitement
    finpour
finpour
```

### 3. Vérification des Indices

```algo
// ✅ Bon : vérifier avant accès
si i >= 0 et i < 3 et j >= 0 et j < 3 alors
    valeur = M[i][j]
sinon
    ecrireln("Indices invalides")
finsi
```

### 4. Affichage Formaté

```algo
// ✅ Bon : affichage avec espacement
pour i de 0 à n - 1 faire
    pour j de 0 à m - 1 faire
        ecrire(M[i][j])
        si j < m - 1 alors
            ecrire(" ")
        finsi
    finpour
    ecrireln()
finpour
```

---

## 🔍 Comparaison Tableaux 1D vs 2D

| Aspect | Tableau 1D | Matrice (2D) |
|--------|------------|--------------|
| **Déclaration** | `tableau[n] de type` | `tableau[n][m] de type` |
| **Accès** | `T[i]` | `M[i][j]` |
| **Affectation** | `T[i] = val` | `M[i][j] = val` |
| **Parcours** | 1 boucle | 2 boucles imbriquées |
| **Usage** | Listes, séquences | Grilles, tables |

---

## 🚫 Erreurs Courantes

### 1. Index Hors Limites

```algo
// ❌ Erreur
variables
    M : tableau[3][3] de entier
debut
    M[3][3] = 10  // ERREUR : indices valides 0-2
```

### 2. Mauvais Ordre des Indices

```algo
// ❌ Confusion ligne/colonne
M[colonne][ligne] = valeur  // Mauvais ordre

// ✅ Correct
M[ligne][colonne] = valeur
```

### 3. Oubli d'un Index

```algo
// ❌ Erreur
M[i] = 10  // Manque le second index

// ✅ Correct
M[i][j] = 10
```

---

## 📊 Performances

### Conseils

1. **Éviter les accès répétés** : Stocker dans une variable locale si utilisé plusieurs fois
2. **Ordre de parcours** : Parcourir ligne par ligne est plus efficace
3. **Taille raisonnable** : Limiter la taille pour éviter les problèmes de mémoire

```algo
// ✅ Efficace
valeur = M[i][j]
x = valeur * 2
y = valeur + 3

// ❌ Moins efficace
x = M[i][j] * 2
y = M[i][j] + 3
```

---

## 🎉 Résumé

### Syntaxe Complète

```algo
algorithme ExempleComplet

variables
    M : tableau[lignes][colonnes] de type
    i, j : entier

debut
    // Initialisation
    pour i de 0 à lignes - 1 faire
        pour j de 0 à colonnes - 1 faire
            M[i][j] = valeur
        finpour
    finpour
    
    // Lecture
    valeur = M[i][j]
    
    // Modification
    M[i][j] = nouvelle_valeur
    
    // Affichage
    pour i de 0 à lignes - 1 faire
        pour j de 0 à colonnes - 1 faire
            ecrire(M[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

---

## 📚 Voir Aussi

- [HELP.md](HELP.md) - Guide complet du compilateur
- [Tableaux 1D](HELP.md#tableaux) - Documentation des tableaux simples
- [Exemples](examples/) - Dossier avec exemples de matrices

---

**Version** : 1.6.0  
**Date** : 5 Décembre 2024  
**Statut** : ✅ **IMPLÉMENTÉ ET TESTÉ**

🎉 **Les matrices sont maintenant supportées dans Algo-Compiler !** 🚀

