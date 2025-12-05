# Initialisation de Variables et Tableaux Littéraux

## Description

Le compilateur AlgoC supporte maintenant deux fonctionnalités puissantes pour simplifier l'initialisation des données :

1. **Déclaration avec initialisation** : `var a : entier = 5`
2. **Tableaux littéraux** : `T = [1, 2, 3, 4]`

---

## 1. Déclaration avec Initialisation

### Syntaxe

```algo
variables
    nom : type = valeur
```

### Types Supportés

Tous les types de base peuvent être initialisés :

```algo
variables
    compteur : entier = 10
    pi : reel = 3.14159
    nom : chaine = "Alice"
    actif : booleen = vrai
    lettre : caractere = 'A'
```

### Caractéristiques

- ✅ **Initialisation immédiate** : La valeur est affectée dès la déclaration
- ✅ **Expressions supportées** : Peut contenir des calculs
- ✅ **Expression conditionnelle** : Peut utiliser l'opérateur ternaire
- ✅ **Compatible avec déclaration multiple** : Toutes les variables reçoivent la même valeur

### Exemples

#### Exemple 1 : Initialisation Simple

```algo
algorithme InitSimple

variables
    age : entier = 25
    prix : reel = 19.99
    prenom : chaine = "Marie"
    estMajeur : booleen = vrai

debut
    ecrire("Age : ")
    ecrireln(age)
    ecrire("Prix : ")
    ecrireln(prix)
    ecrire("Prénom : ")
    ecrireln(prenom)
fin
```

#### Exemple 2 : Initialisation avec Expressions

```algo
variables
    x : entier = 10
    double : entier = x * 2
    carre : entier = x ** 2
    somme : entier = 5 + 10 + 15
```

#### Exemple 3 : Initialisation avec Expression Conditionnelle

```algo
variables
    note : reel = 14.5
    statut : chaine = si note >= 10 alors "Admis" sinon "Refusé"
    bonus : entier = si note >= 16 alors 10 sinon 0
```

#### Exemple 4 : Déclaration Multiple (même valeur)

```algo
variables
    a, b, c : entier = 5
    
debut
    // a, b et c valent tous 5
    ecrireln(a)  // 5
    ecrireln(b)  // 5
    ecrireln(c)  // 5
fin
```

### Comparaison Avant/Après

#### ❌ Avant (deux étapes)

```algo
variables
    compteur : entier

debut
    compteur = 10
    // ...
fin
```

#### ✅ Après (une seule étape)

```algo
variables
    compteur : entier = 10

debut
    // compteur est déjà à 10
    // ...
fin
```

---

## 2. Tableaux Littéraux

### Syntaxe

```algo
tableau = [valeur1, valeur2, valeur3, ...]
```

### Caractéristiques

- ✅ **Syntaxe concise** : Pas besoin de boucles d'initialisation
- ✅ **Expressions supportées** : Chaque élément peut être une expression
- ✅ **Types mixtes** : Le type est déduit du premier élément
- ✅ **Taille dynamique** : La taille est déterminée par le nombre d'éléments
- ✅ **Tableau vide** : `[]` crée un tableau vide

### Exemples

#### Exemple 1 : Initialisation Simple

```algo
algorithme TableauSimple

variables
    nombres : tableau[5] de entier
    i : entier

debut
    // Initialisation avec littéral
    nombres = [10, 20, 30, 40, 50]
    
    // Affichage
    pour i de 0 à 4 faire
        ecrireln(nombres[i])
    finpour
fin
```

#### Exemple 2 : Tableaux avec Expressions

```algo
variables
    calculs : tableau[5] de entier

debut
    // Chaque élément peut être une expression
    calculs = [5 * 2, 10 + 5, 20 - 3, 8 ** 2, 100 div 3]
    // Résultat : [10, 15, 17, 64, 33]
fin
```

#### Exemple 3 : Tableaux de Différents Types

```algo
variables
    notes : tableau[4] de reel
    noms : tableau[3] de chaine
    flags : tableau[2] de booleen

debut
    notes = [15.5, 12.0, 18.25, 14.75]
    noms = ["Alice", "Bob", "Charlie"]
    flags = [vrai, faux]
fin
```

#### Exemple 4 : Tableau Vide

```algo
variables
    temp : tableau[0] de entier

debut
    temp = []
    // Tableau vide (taille 0)
fin
```

#### Exemple 5 : Avec Expressions Conditionnelles

```algo
variables
    valeurs : tableau[3] de entier
    x : entier

debut
    x = 10
    valeurs = [
        si x > 5 alors 100 sinon 0,
        x * 2,
        si x mod 2 = 0 alors x sinon x + 1
    ]
fin
```

### Comparaison Avant/Après

#### ❌ Avant (boucle d'initialisation)

```algo
variables
    T : tableau[5] de entier
    i : entier

debut
    T[0] = 1
    T[1] = 2
    T[2] = 3
    T[3] = 4
    T[4] = 5
fin
```

#### ✅ Après (littéral)

```algo
variables
    T : tableau[5] de entier

debut
    T = [1, 2, 3, 4, 5]
fin
```

---

## Combinaison des Deux Fonctionnalités

### Déclaration de Tableau avec Initialisation

**Note** : Actuellement, l'initialisation dans la déclaration fonctionne mais le tableau doit être déclaré d'abord.

```algo
variables
    // Déclaration du tableau
    suite : tableau[5] de entier

debut
    // Initialisation avec littéral
    suite = [10, 20, 30, 40, 50]
fin
```

### Initialisation de Variable avec Élément de Tableau

```algo
variables
    T : tableau[3] de entier
    premier : entier

debut
    T = [5, 10, 15]
    premier = T[0]  // premier = 5
fin
```

---

## Exemples Complets

### Exemple 1 : Calcul de Moyenne

```algo
algorithme CalculMoyenne

variables
    notes : tableau[5] de reel
    somme : reel = 0.0
    moyenne : reel
    i : entier

debut
    // Initialisation avec littéral
    notes = [15.5, 12.0, 18.0, 14.5, 16.0]
    
    // Calcul de la somme
    pour i de 0 à 4 faire
        somme = somme + notes[i]
    finpour
    
    // Calcul de la moyenne
    moyenne = somme / 5
    
    ecrire("Moyenne : ")
    ecrireln(moyenne)
fin
```

### Exemple 2 : Statistiques

```algo
algorithme Statistiques

variables
    valeurs : tableau[10] de entier
    min : entier = 999999
    max : entier = 0
    somme : entier = 0
    i : entier

debut
    // Initialisation avec expressions
    valeurs = [10, 25, 3, 48, 12, 67, 34, 89, 15, 42]
    
    // Parcours pour stats
    pour i de 0 à 9 faire
        si valeurs[i] < min alors
            min = valeurs[i]
        finsi
        si valeurs[i] > max alors
            max = valeurs[i]
        finsi
        somme = somme + valeurs[i]
    finpour
    
    ecrire("Min : ")
    ecrireln(min)
    ecrire("Max : ")
    ecrireln(max)
    ecrire("Somme : ")
    ecrireln(somme)
fin
```

### Exemple 3 : Filtrage

```algo
algorithme Filtrage

variables
    nombres : tableau[10] de entier
    pairs : tableau[10] de entier
    compteur : entier = 0
    i : entier

debut
    // Nombres de 1 à 10
    nombres = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
    
    // Filtrer les nombres pairs
    pour i de 0 à 9 faire
        si nombres[i] mod 2 = 0 alors
            pairs[compteur] = nombres[i]
            compteur = compteur + 1
        finsi
    finpour
    
    ecrireln("Nombres pairs :")
    pour i de 0 à compteur - 1 faire
        ecrireln(pairs[i])
    finpour
fin
```

---

## Avantages

### 1. Lisibilité

**Avant** :
```algo
variables
    x, y, z : entier
debut
    x = 5
    y = 10
    z = 15
```

**Après** :
```algo
variables
    x : entier = 5
    y : entier = 10
    z : entier = 15
debut
    // Déjà initialisées
```

### 2. Concision

**Avant** (13 lignes) :
```algo
variables
    T : tableau[5] de entier
debut
    T[0] = 1
    T[1] = 2
    T[2] = 3
    T[3] = 4
    T[4] = 5
```

**Après** (3 lignes) :
```algo
variables
    T : tableau[5] de entier
debut
    T = [1, 2, 3, 4, 5]
```

### 3. Expressivité

```algo
// Tableau avec calculs directs
fibonacci = [1, 1, 2, 3, 5, 8, 13, 21, 34, 55]

// Variable avec expression conditionnelle
statut = si age >= 18 alors "majeur" sinon "mineur"
```

---

## Cas d'Usage

| Besoin | Solution |
|--------|----------|
| Compteur à 0 | `compteur : entier = 0` |
| Constante PI | `pi : reel = 3.14159` |
| Message initial | `msg : chaine = "Démarrage"` |
| Liste de notes | `notes = [15, 12, 18, 14]` |
| Suite de Fibonacci | `fib = [1, 1, 2, 3, 5, 8]` |
| Tableau de noms | `noms = ["A", "B", "C"]` |

---

## Limitations et Notes

### 1. Déclaration Multiple avec Initialisation

Lors d'une déclaration multiple, toutes les variables partagent la même valeur :

```algo
variables
    a, b, c : entier = 5
    
debut
    // a = 5, b = 5, c = 5
fin
```

### 2. Tableaux - Déclaration Séparée

Actuellement, les tableaux doivent être déclarés puis initialisés :

```algo
// ✅ Correct
variables
    T : tableau[5] de entier
debut
    T = [1, 2, 3, 4, 5]
fin
```

### 3. Taille du Tableau Littéral

La taille du tableau littéral doit correspondre ou être inférieure à la taille déclarée :

```algo
variables
    T : tableau[5] de entier
debut
    T = [1, 2]  // OK - les 3 derniers restent à 0
    // T = [1, 2, 3, 4, 5, 6]  // Erreur potentielle
fin
```

---

## Bonnes Pratiques

### ✅ À Faire

1. **Initialiser les compteurs et accumulateurs**
   ```algo
   compteur : entier = 0
   somme : reel = 0.0
   ```

2. **Utiliser des littéraux pour données fixes**
   ```algo
   joursSemaine = ["Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim"]
   ```

3. **Initialiser avec expressions significatives**
   ```algo
   tva : reel = 0.20
   prixTTC : reel = prixHT * (1 + tva)
   ```

### ❌ À Éviter

1. **Initialisation complexe dans déclaration**
   ```algo
   // Éviter
   x : entier = calculComplexe(a, b, c, d, e)
   
   // Préférer
   x : entier
   debut
       x = calculComplexe(a, b, c, d, e)
   ```

2. **Tableaux littéraux trop longs**
   ```algo
   // Éviter
   T = [1, 2, 3, ..., 100]  // Trop long
   
   // Préférer une boucle
   ```

---

## Résumé

### Déclaration avec Initialisation

**Syntaxe** : `nom : type = valeur`

**Avantages** :
- ✅ Code plus concis
- ✅ Intention claire
- ✅ Moins d'erreurs

### Tableaux Littéraux

**Syntaxe** : `tableau = [val1, val2, val3]`

**Avantages** :
- ✅ Initialisation rapide
- ✅ Lisibilité améliorée
- ✅ Expressions supportées

---

**Version** : 1.3.0  
**Date** : 5 Décembre 2024  
**Statut** : ✅ Implémenté et testé

