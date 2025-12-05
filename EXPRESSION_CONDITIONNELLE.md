# Expression Conditionnelle (Opérateur Ternaire)

## Description

L'**expression conditionnelle** (aussi appelée **opérateur ternaire**) permet d'utiliser une condition **directement dans une expression** pour choisir entre deux valeurs. C'est une forme compacte de l'instruction `si...alors...sinon` qui retourne une valeur.

## Syntaxe

```algo
variable = si condition alors valeur1 sinon valeur2
```

## Caractéristiques

- ✅ **Expression, pas instruction** : Peut être utilisée partout où une expression est attendue
- ✅ **Retourne une valeur** : Le résultat peut être affecté à une variable
- ✅ **Imbrication possible** : On peut imbriquer plusieurs expressions conditionnelles
- ✅ **Évaluation paresseuse** : Seule la branche choisie est évaluée
- ✅ **Type flexible** : Peut retourner des entiers, réels, chaînes, caractères, booléens

## Exemples

### Exemple 1 : Maximum de deux nombres

```algo
algorithme Maximum

variables
    a, b, max : entier

debut
    a = 15
    b = 23
    
    // Expression conditionnelle pour trouver le maximum
    max = si a > b alors a sinon b
    
    ecrire("Maximum : ")
    ecrireln(max)
fin
```

**Sortie** : `Maximum : 23`

### Exemple 2 : Statut selon l'âge

```algo
algorithme StatutAge

variables
    age : entier
    statut : chaine

debut
    age = 17
    
    // Expression conditionnelle avec chaînes
    statut = si age >= 18 alors "Majeur" sinon "Mineur"
    
    ecrire("Statut : ")
    ecrireln(statut)
fin
```

**Sortie** : `Statut : Mineur`

### Exemple 3 : Parité des nombres

```algo
algorithme Parite

variables
    i : entier
    parite : chaine

debut
    pour i de 1 à 10 faire
        parite = si i mod 2 = 0 alors "pair" sinon "impair"
        ecrire(i)
        ecrire(" est ")
        ecrireln(parite)
    finpour
fin
```

**Sortie** :
```
1 est impair
2 est pair
3 est impair
...
```

### Exemple 4 : Valeur absolue

```algo
algorithme ValeurAbsolue

variables
    x, absX : entier

debut
    x = -10
    
    // Valeur absolue avec expression conditionnelle
    absX = si x < 0 alors -x sinon x
    
    ecrire("Valeur absolue de ")
    ecrire(x)
    ecrire(" = ")
    ecrireln(absX)
fin
```

**Sortie** : `Valeur absolue de -10 = 10`

### Exemple 5 : Utilisation directe dans ecrire()

```algo
algorithme EcrireConditionnel

variables
    x : entier

debut
    x = 5
    
    // Expression conditionnelle directement dans ecrire
    ecrire("Le nombre est ")
    ecrire(si x > 0 alors "positif" sinon "négatif ou nul")
    ecrireln()
fin
```

**Sortie** : `Le nombre est positif`

### Exemple 6 : Expression imbriquée (Mention selon note)

```algo
algorithme MentionNote

variables
    note : reel
    mention : chaine

debut
    note = 14.5
    
    // Imbrication d'expressions conditionnelles
    mention = si note >= 16 alors "Très Bien" sinon si note >= 14 alors "Bien" sinon si note >= 12 alors "Assez Bien" sinon si note >= 10 alors "Passable" sinon "Échec"
    
    ecrire("Note : ")
    ecrireln(note)
    ecrire("Mention : ")
    ecrireln(mention)
fin
```

**Sortie** :
```
Note : 14.5
Mention : Bien
```

### Exemple 7 : Dans une expression arithmétique

```algo
algorithme ExpressionArithmetique

variables
    x, y : entier

debut
    x = 5
    
    // Expression conditionnelle dans un calcul
    y = si x > 0 alors x * 2 sinon x * -1
    
    ecrire("Résultat : ")
    ecrireln(y)
fin
```

**Sortie** : `Résultat : 10`

### Exemple 8 : Signe d'un nombre

```algo
algorithme SigneNombre

variables
    x : entier
    signe : chaine

debut
    x = -5
    
    // Déterminer le signe
    signe = si x > 0 alors "+" sinon si x < 0 alors "-" sinon "0"
    
    ecrire("Signe : ")
    ecrireln(signe)
fin
```

**Sortie** : `Signe : -`

## Comparaison : Expression vs Instruction

### ❌ Avec Instruction Si (plus verbeux)

```algo
variables
    a, b, max : entier

debut
    a = 10
    b = 20
    
    si a > b alors
        max = a
    sinon
        max = b
    finsi
    
    ecrire(max)
fin
```

### ✅ Avec Expression Conditionnelle (plus compact)

```algo
variables
    a, b, max : entier

debut
    a = 10
    b = 20
    
    max = si a > b alors a sinon b
    
    ecrire(max)
fin
```

## Avantages

1. **Concision** : Code plus court et lisible
2. **Expressivité** : Intention claire en une ligne
3. **Utilisable partout** : Dans affectations, calculs, appels de fonctions
4. **Imbrication** : Permet de chaîner plusieurs conditions
5. **Retour de valeur** : Peut être utilisé comme paramètre de fonction

## Cas d'Usage Courants

### 1. Maximum / Minimum

```algo
max = si a > b alors a sinon b
min = si a < b alors a sinon b
```

### 2. Valeur par défaut

```algo
valeur = si x != 0 alors x sinon 1
```

### 3. Formatage de message

```algo
message = si erreur alors "Erreur!" sinon "OK"
```

### 4. Choix de coefficient

```algo
coefficient = si premium alors 1.5 sinon 1.0
prix = prixBase * coefficient
```

### 5. Validation

```algo
resultat = si age >= 18 alors "Autorisé" sinon "Refusé"
```

## Imbrication

### Syntaxe Imbriquée

```algo
valeur = si condition1 alors
    resultat1
sinon si condition2 alors
    resultat2
sinon si condition3 alors
    resultat3
sinon
    resultatParDefaut
```

### Exemple : Classification de note

```algo
mention = si note >= 16 alors
    "Très Bien"
sinonsi note >= 14 alors
    "Bien"
sinonsi note >= 12 alors
    "Assez Bien"
sinonsi note >= 10 alors
    "Passable"
sinon
    "Échec"
```

## Bonnes Pratiques

### ✅ À Faire

1. **Utiliser pour des conditions simples**
   ```algo
   signe = si x >= 0 alors "+" sinon "-"
   ```

2. **Garder les expressions courtes**
   ```algo
   max = si a > b alors a sinon b
   ```

3. **Utiliser dans des affectations directes**
   ```algo
   statut = si actif alors "On" sinon "Off"
   ```

### ❌ À Éviter

1. **Imbrication excessive (> 3 niveaux)**
   ```algo
   // Trop complexe - Préférer une instruction si/sinonSi
   resultat = si x > 0 alors si y > 0 alors si z > 0 alors "Positif" sinon "Z négatif" sinon "Y négatif" sinon "X négatif"
   ```

2. **Expressions avec effets de bord**
   ```algo
   // Éviter les appels de procédures dans l'expression
   ```

3. **Conditions très longues**
   ```algo
   // Préférer une instruction si classique pour plus de clarté
   ```

## Différences avec l'Instruction Si

| Aspect | Expression Conditionnelle | Instruction Si |
|--------|---------------------------|----------------|
| **Usage** | Retourne une valeur | Exécute des instructions |
| **Syntaxe** | `si cond alors val1 sinon val2` | `si cond alors ... finsi` |
| **Affectation** | Directe : `x = si...` | Indirecte : dans le bloc |
| **Compacité** | Une ligne | Plusieurs lignes |
| **Utilisable dans** | Expressions, paramètres | Séquence d'instructions |
| **SinonSi** | Imbrication uniquement | Clause native `sinonSi` |

## Évaluation

### Évaluation Paresseuse (Lazy Evaluation)

**Seule la branche choisie est évaluée** :

```algo
// Si x > 0, alors 'dangereux()' n'est PAS appelé
resultat = si x > 0 alors safe() sinon dangereux()
```

Cela permet d'éviter des calculs inutiles et des erreurs potentielles.

## Exemples Avancés

### 1. Fonction avec expression conditionnelle

```algo
fonction maximum(a : entier, b : entier) : entier
debut
    retourner si a > b alors a sinon b
fin
```

### 2. Tableau avec expression conditionnelle

```algo
variables
    notes : tableau[5] de entier
    i : entier
    appreciation : chaine

debut
    // ... initialiser notes ...
    
    pour i de 0 à 4 faire
        appreciation = si notes[i] >= 10 alors "Réussi" sinon "Échoué"
        ecrireln(appreciation)
    finpour
fin
```

### 3. Calcul conditionnel

```algo
variables
    prix, prixFinal : reel
    reduction : booleen

debut
    prix = 100
    reduction = vrai
    
    // Appliquer réduction si applicable
    prixFinal = si reduction alors prix * 0.8 sinon prix
    
    ecrireln(prixFinal)
fin
```

## Compatibilité

### Avec les Autres Fonctionnalités

L'expression conditionnelle fonctionne avec :

- ✅ Toutes les expressions arithmétiques
- ✅ Toutes les expressions logiques
- ✅ Les appels de fonctions
- ✅ Les accès aux tableaux
- ✅ Les variables de tous types
- ✅ Les constantes
- ✅ Les opérateurs de comparaison

## Résumé

L'**expression conditionnelle** est un outil puissant pour écrire du code concis et expressif. Elle est idéale pour :

- Affectations conditionnelles simples
- Choix entre deux valeurs
- Paramètres conditionnels
- Retours de fonctions conditionnels

**Syntaxe** : `variable = si condition alors valeur1 sinon valeur2`

**Utilisez-la** pour simplifier votre code quand une instruction `si...sinon` serait trop verbeuse !

---

**Version** : 1.1.0+  
**Date** : Décembre 2024  
**Statut** : ✅ Implémenté et testé

