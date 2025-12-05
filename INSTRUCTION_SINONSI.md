# Instruction SinonSi (Else If)

## Description

La clause **`sinonSi`** permet d'ajouter des conditions supplémentaires dans une instruction `si`, offrant une alternative à l'enchaînement de multiples `si...sinon` imbriqués.

## Syntaxe

```algo
si condition1 alors
    instructions1
sinonSi condition2 alors
    instructions2
sinonSi condition3 alors
    instructions3
sinon
    instructions_par_defaut
finsi
```

## Caractéristiques

- ✅ **Multiple clauses** : Vous pouvez avoir autant de clauses `sinonSi` que nécessaire
- ✅ **Évaluation séquentielle** : Les conditions sont évaluées dans l'ordre jusqu'à ce qu'une soit vraie
- ✅ **Court-circuit** : Dès qu'une condition est vraie, les suivantes ne sont pas évaluées
- ✅ **Clause sinon optionnelle** : La clause `sinon` finale est optionnelle

## Exemples

### Exemple 1 : Système de notation

```algo
algorithme NotesEtudiants

variables
    note : entier

debut
    ecrire("Entrez la note (0-100): ")
    lire(note)
    
    si note >= 90 alors
        ecrireln("Excellent ! Grade A")
    sinonSi note >= 80 alors
        ecrireln("Très bien ! Grade B")
    sinonSi note >= 70 alors
        ecrireln("Bien ! Grade C")
    sinonSi note >= 60 alors
        ecrireln("Satisfaisant. Grade D")
    sinon
        ecrireln("Insuffisant. Échec.")
    finsi
fin
```

### Exemple 2 : Comparaison de valeurs

```algo
algorithme ComparaisonNombres

variables
    a, b : entier

debut
    a = 15
    b = 20
    
    si a > b alors
        ecrireln("a est plus grand que b")
    sinonSi a = b alors
        ecrireln("a est égal à b")
    sinon
        ecrireln("a est plus petit que b")
    finsi
fin
```

### Exemple 3 : Jour de la semaine

```algo
algorithme JoursSemaine

variables
    jour : entier

debut
    ecrire("Entrez un numéro de jour (1-7): ")
    lire(jour)
    
    si jour = 1 alors
        ecrireln("Lundi")
    sinonSi jour = 2 alors
        ecrireln("Mardi")
    sinonSi jour = 3 alors
        ecrireln("Mercredi")
    sinonSi jour = 4 alors
        ecrireln("Jeudi")
    sinonSi jour = 5 alors
        ecrireln("Vendredi")
    sinonSi jour = 6 alors
        ecrireln("Samedi")
    sinonSi jour = 7 alors
        ecrireln("Dimanche")
    sinon
        ecrireln("Numéro de jour invalide")
    finsi
fin
```

### Exemple 4 : Classification de températures

```algo
algorithme ClassificationTemperature

variables
    temp : reel

debut
    ecrire("Entrez la température (°C): ")
    lire(temp)
    
    si temp >= 30 alors
        ecrireln("Il fait très chaud")
    sinonSi temp >= 20 alors
        ecrireln("Il fait chaud")
    sinonSi temp >= 10 alors
        ecrireln("Il fait doux")
    sinonSi temp >= 0 alors
        ecrireln("Il fait froid")
    sinon
        ecrireln("Il gèle !")
    finsi
fin
```

## Comparaison : Avant vs Après

### ❌ Avant (imbrication de si...sinon)

```algo
si note >= 90 alors
    ecrireln("Grade A")
sinon
    si note >= 80 alors
        ecrireln("Grade B")
    sinon
        si note >= 70 alors
            ecrireln("Grade C")
        sinon
            ecrireln("Échec")
        finsi
    finsi
finsi
```

### ✅ Après (avec sinonSi)

```algo
si note >= 90 alors
    ecrireln("Grade A")
sinonSi note >= 80 alors
    ecrireln("Grade B")
sinonSi note >= 70 alors
    ecrireln("Grade C")
sinon
    ecrireln("Échec")
finsi
```

## Avantages

1. **Lisibilité** : Code plus clair et plus facile à comprendre
2. **Maintenance** : Plus facile à modifier et à déboguer
3. **Indentation** : Évite l'indentation excessive
4. **Logique** : Structure logique plus naturelle

## Différence avec `selon`

| Instruction | Utilisation |
|-------------|-------------|
| **sinonSi** | Pour des conditions complexes et variées |
| **selon** | Pour tester une seule variable contre plusieurs valeurs |

### Exemple avec `sinonSi` (conditions variées)

```algo
si age < 18 et etudiant alors
    ecrireln("Étudiant mineur")
sinonSi age >= 18 et age < 65 alors
    ecrireln("Adulte")
sinonSi age >= 65 alors
    ecrireln("Senior")
finsi
```

### Exemple avec `selon` (une variable)

```algo
selon jour
    cas 1 : ecrireln("Lundi")
    cas 2 : ecrireln("Mardi")
    cas 3 : ecrireln("Mercredi")
    defaut : ecrireln("Autre")
finselon
```

## Note importante sur l'alias 'a'

⚠️ **L'alias 'a' a été supprimé pour éviter les conflits avec les noms de variables.**

### ❌ N'est plus accepté :
```algo
pour i de 1 a 10 faire  // ERREUR
    ecrire(i)
finpour
```

### ✅ Utilisez uniquement 'à' :
```algo
pour i de 1 à 10 faire  // CORRECT
    ecrire(i)
finpour
```

**Raison** : Le caractère 'a' est très couramment utilisé comme nom de variable (ex: `a = 10`), ce qui créait des ambiguïtés dans le parseur.

## Règles d'évaluation

1. La condition principale du `si` est évaluée en premier
2. Si elle est vraie, le bloc `alors` est exécuté et on sort de l'instruction
3. Si elle est fausse, on évalue la première clause `sinonSi`
4. Si elle est vraie, son bloc est exécuté et on sort
5. Sinon, on continue avec les clauses `sinonSi` suivantes
6. Si aucune condition n'est vraie, le bloc `sinon` est exécuté (s'il existe)

## Combinaison avec d'autres structures

### Avec des boucles

```algo
pour i de 1 à 100 faire
    si i mod 15 = 0 alors
        ecrireln("FizzBuzz")
    sinonSi i mod 3 = 0 alors
        ecrireln("Fizz")
    sinonSi i mod 5 = 0 alors
        ecrireln("Buzz")
    sinon
        ecrireln(i)
    finsi
finpour
```

### Avec des fonctions

```algo
fonction categorieAge(age : entier) : chaine
debut
    si age < 0 alors
        retourner "invalide"
    sinonSi age < 13 alors
        retourner "enfant"
    sinonSi age < 18 alors
        retourner "adolescent"
    sinonSi age < 65 alors
        retourner "adulte"
    sinon
        retourner "senior"
    finsi
fin
```

## Bonnes pratiques

1. ✅ Mettez les conditions les plus restrictives en premier
2. ✅ Utilisez `sinonSi` pour des conditions mutuellement exclusives
3. ✅ Ajoutez une clause `sinon` finale pour gérer les cas imprévus
4. ✅ Gardez les conditions simples et lisibles
5. ❌ Évitez trop de clauses `sinonSi` (>5) - envisagez `selon` ou une autre structure

## Résumé

La clause **`sinonSi`** améliore considérablement la lisibilité du code lorsque vous devez tester plusieurs conditions séquentielles. Elle remplace avantageusement les imbrications de `si...sinon` tout en maintenant une structure claire et facile à maintenir.

---

**Version** : 1.0.0+  
**Date** : Décembre 2024  
**Statut** : ✅ Implémenté et testé

