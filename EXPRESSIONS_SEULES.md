# Expressions Seules - Affichage Automatique du Résultat

## 📝 Description

Le compilateur Algo supporte maintenant les **expressions seules** qui affichent automatiquement leur résultat au format `expression = résultat`.

## 🎯 Syntaxe

Il suffit d'écrire une expression sur une ligne, sans affectation :

```algo
variables
    a, b : entier
debut
    a = 7
    b = 6
    
    a + b        // Affiche: a + b = 13
fin
```

## ✨ Fonctionnalités

### 1. Expressions Simples

```algo
variables
    a, b : entier
debut
    a = 10
    b = 5
    
    a + b        // a + b = 15
    a - b        // a - b = 5
    a * b        // a * b = 50
    a / b        // a / b = 2
fin
```

### 2. Expressions avec Parenthèses

Les parenthèses sont correctement affichées :

```algo
variables
    a, b : entier
debut
    a = 7
    b = 6
    
    (a + b) * 2         // (a + b) * 2 = 26
    (a + b) * (a - b)   // (a + b) * (a - b) = 13
fin
```

### 3. Expressions avec Fonctions

```algo
variables
    a, b : entier
debut
    a = 15
    b = 10
    
    max(a, b)           // max(a, b) = 15
    min(a, b)           // min(a, b) = 10
    racine(a + b)       // racine(a + b) = 5
fin
```

### 4. Expressions avec Nombres Directs

```algo
debut
    5 + 3               // 5 + 3 = 8
    10 * 2              // 10 * 2 = 20
    2 ^ 8               // 2 ^ 8 = 256
fin
```

### 5. Expressions avec Réels

```algo
variables
    x, y : reel
debut
    x = 3.5
    y = 2.5
    
    x * y               // x * y = 8.75
    x / y               // x / y = 1.4
fin
```

### 6. Expressions Complexes

```algo
variables
    a, b, c : entier
debut
    a = 5
    b = 3
    c = 2
    
    a * b + c           // a * b + c = 17
    (a + b) * c         // (a + b) * c = 16
    a ^ 2 + b ^ 2       // a ^ 2 + b ^ 2 = 34
fin
```

## 🎓 Cas d'Usage

### 1. Débogage Rapide

```algo
variables
    prix : reel
    quantite : entier
debut
    prix = 15.50
    quantite = 3
    
    // Vérifier le calcul
    prix * quantite     // prix * quantite = 46.5
fin
```

### 2. Vérification de Formules

```algo
variables
    rayon : reel
    pi : reel
debut
    rayon = 5
    pi = 3.14159
    
    // Calculer l'aire du cercle
    pi * rayon ^ 2      // pi * rayon ^ 2 = 78.53975
fin
```

### 3. Tests Mathématiques

```algo
variables
    a, b, c : entier
debut
    a = 3
    b = 4
    c = 5
    
    // Théorème de Pythagore
    a ^ 2 + b ^ 2       // a ^ 2 + b ^ 2 = 25
    c ^ 2               // c ^ 2 = 25
fin
```

## 📊 Exemples Complets

### Exemple 1 : Calculs Simples

```algo
algorithme CalculsSimples

variables
    a, b : entier

debut
    a = 7
    b = 6
    
    ecrireln("Addition:")
    a + b
    
    ecrireln("Multiplication:")
    a * b
    
    ecrireln("Puissance:")
    a ^ 2
fin
```

**Sortie :**
```
Addition:
a + b = 13
Multiplication:
a * b = 42
Puissance:
a ^ 2 = 49
```

### Exemple 2 : Avec Fonctions

```algo
algorithme AvecFonctions

variables
    x, y : entier

debut
    x = 15
    y = 10
    
    max(x, y)
    min(x, y)
    abs(x - y)
    racine(x * y)
fin
```

**Sortie :**
```
max(x, y) = 15
min(x, y) = 10
abs(x - y) = 5
racine(x * y) = 12.247448713915889
```

### Exemple 3 : Expressions Complexes

```algo
algorithme ExpressionsComplexes

variables
    a, b, c : entier

debut
    a = 5
    b = 3
    c = 2
    
    ecrireln("Expressions:")
    a * b + c
    (a + b) * c
    a ^ 2 + b ^ 2 + c ^ 2
fin
```

**Sortie :**
```
Expressions:
a * b + c = 17
(a + b) * c = 16
a ^ 2 + b ^ 2 + c ^ 2 = 38
```

## 🔍 Détails Techniques

### Priorité des Opérateurs

Les parenthèses sont ajoutées automatiquement selon la priorité des opérateurs :

1. `ou` (priorité 1)
2. `et` (priorité 2)
3. Comparaison : `==`, `<>`, `!=`, `<`, `>`, `<=`, `>=` (priorité 3)
4. Addition/Soustraction : `+`, `-` (priorité 4)
5. Multiplication/Division : `*`, `/`, `div`, `mod`, `%` (priorité 5)
6. Puissance : `^`, `**` (priorité 6)

### Format d'Affichage

- **Entiers** : Affichés sans décimales (`13` et non `13.0`)
- **Réels** : Affichés avec décimales (`8.75`)
- **Booléens** : `vrai` ou `faux`
- **Chaînes** : Entre guillemets (`"Hello"`)
- **Caractères** : Entre apostrophes (`'A'`)
- **Tableaux** : `[1, 2, 3]`

## 💡 Avantages

1. **Débogage Rapide** : Voir immédiatement le résultat d'une expression
2. **Pédagogique** : Comprendre l'évaluation des expressions
3. **Productivité** : Pas besoin d'écrire `ecrireln()` pour chaque test
4. **Lisibilité** : Format clair `expression = résultat`

## 🚀 Utilisation

### Avec Variables

```algo
a = 7
b = 6
a + b    // a + b = 13
```

### Avec Nombres Directs

```algo
5 + 3    // 5 + 3 = 8
```

### Avec Fonctions

```algo
max(10, 20)    // max(10, 20) = 20
```

### Avec Expressions Complexes

```algo
(a + b) * (a - b)    // (a + b) * (a - b) = 13
```

---

## 📝 Notes

- Les expressions seules **n'affectent pas** les variables
- Elles **affichent uniquement** le résultat
- Utilisez l'affectation (`=`) pour stocker le résultat dans une variable

---

**Version** : 1.7.7  
**Date** : 14 décembre 2025  
**Développé par** : Omar OUGHZAL

