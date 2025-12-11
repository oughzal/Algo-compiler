# Tests de Débogage d'Exécution des Algorithmes

## Vue d'ensemble

Ce document décrit les tests créés pour valider le débogage d'exécution des algorithmes dans le compilateur Algo. Ces tests vérifient que les erreurs de parsing et d'exécution produisent des messages clairs avec numéro de ligne et informations sur le token.

## Fichiers de tests

- **DebugTest.kt** : Suite de tests pour le débogage (15 tests)
- **ParseException.kt** : Classe d'exception personnalisée avec numéro de ligne et token

## Tests d'erreurs de parsing

### 1. `testParseErrorMissingAlorsAfterSi`
Vérifie qu'une erreur est levée si le mot-clé `alors` est manquant après `si`.

```algo
si x == 5
    ecrire("cinq")  // ❌ Manque 'alors'
finsi
```

**Résultat attendu** : ParseException avec numéro de ligne

### 2. `testParseErrorInvalidType`
Vérifie qu'une erreur est levée si un type de variable invalide est utilisé.

```algo
variables
    x : invalid_type  // ❌ Type invalide
```

**Résultat attendu** : Message "Type de variable invalide" avec ligne 3

### 3. `testParseErrorMissingAInPourLoop`
Vérifie qu'une erreur est levée si `à` est manquant dans une boucle `pour`.

```algo
pour i de 1 jusqua 10 faire  // ❌ Manque 'à'
    ecrire(i)
finpour
```

**Résultat attendu** : Message "Attendu 'à'" avec numéro de ligne

### 4. `testParseErrorUnexpectedToken`
Vérifie qu'une erreur est levée si un token inattendu est rencontré.

```algo
x = 5
??? invalid  // ❌ Token invalide
```

**Résultat attendu** : Exception avec numéro de ligne

### 5. `testParseErrorMissingOperatorAfterIdentifier`
Vérifie qu'une erreur est levée si l'opérateur d'affectation est manquant.

```algo
x 5  // ❌ Manque '='
```

**Résultat attendu** : Message "Attendu '=' ou '[' ou '('" avec numéro de ligne

## Tests d'erreurs d'exécution (runtime)

### 6. `testRuntimeErrorUndefinedVariable`
Vérifie qu'une erreur est levée si une variable non définie est utilisée.

```algo
ecrire(x)  // ❌ Variable 'x' non définie
```

**Résultat attendu** : Message mentionnant "Variable non définie"

### 7. `testRuntimeErrorDivisionByZero`
Vérifie le comportement lors d'une division par zéro (produit Infinity en Kotlin).

```algo
x = 10
y = 0
z = x / y  // ⚠ Produit Infinity (pas d'exception en Kotlin)
```

**Résultat attendu** : Pas d'exception levée (Infinity) ou exception si implémenté

### 8. `testRuntimeErrorArrayIndexOutOfBounds`
Vérifie qu'une erreur est levée si l'index d'un tableau est hors limites.

```algo
T : tableau[5] de entier
T[10] = 42  // ❌ Index hors limites
```

**Résultat attendu** : Message "Index hors limites"

### 9. `testRuntimeErrorTypeIncompatibility`
Vérifie la vérification de type lors d'une affectation incompatible.

```algo
x : entier
s : chaine
x = 5
s = x  // ❌ Incompatibilité de type (si typage fort activé)
```

**Résultat attendu** : Exception de type ou casting implicite (selon implémentation)

### 10. `testRuntimeErrorFunctionNotFound`
Vérifie qu'une erreur est levée si une fonction inexistante est appelée.

```algo
inexistante()  // ❌ Fonction non définie
```

**Résultat attendu** : Message "Fonction non définie"

## Tests de messages d'erreur

### 11. `testParseExceptionContainsLineAndToken`
Vérifie que ParseException contient bien le numéro de ligne et le token.

**Résultat attendu** : 
- `exception.line == 42`
- `exception.token.value == "testToken"`
- `exception.toString()` contient "ligne 42" et "testToken"

### 12. `testParseExceptionWithoutToken`
Vérifie que ParseException fonctionne sans token.

**Résultat attendu** : 
- `exception.line == 15`
- `exception.token == null`
- `exception.toString()` contient "ligne 15" et "?"

## Tests de syntaxe complexe valide

### 13. `testComplexExpressionParsing`
Vérifie que les expressions complexes sont correctement parsées.

```algo
z = (x + y) * (x - y) + x ** y  // ✅ Expression complexe valide
```

**Résultat attendu** : Parsing réussi

### 14. `testNestedControlStructures`
Vérifie que les structures de contrôle imbriquées sont correctement parsées.

```algo
pour i de 1 à 3 faire
    si i == 2 alors
        pour j de 1 à 2 faire
            ecrire(i)
            ecrire(j)
        finpour
    finsi
finpour
```

**Résultat attendu** : Parsing réussi

### 15. `testMatrixOperations`
Vérifie que les opérations sur matrices sont correctement parsées.

```algo
M : tableau[2][2] de entier
pour i de 0 à 1 faire
    pour j de 0 à 1 faire
        M[i][j] = i * 2 + j
    finpour
finpour
```

**Résultat attendu** : Parsing réussi

## Classe ParseException

```kotlin
class ParseException(
    message: String, 
    val line: Int, 
    val token: Token? = null
) : RuntimeException(message)
```

### Propriétés
- `line: Int` : Numéro de ligne où l'erreur s'est produite
- `token: Token?` : Token associé à l'erreur (optionnel)

### Méthode toString()
Format : `ParseException: {message} (ligne {line}, token='{token.value}')`

Exemple : `ParseException: Erreur de parsing : Attendu ALORS, mais trouvé IDENTIFICATEUR (ligne 5, token='ecrire')`

## Exécution des tests

### Exécuter tous les tests de débogage
```bash
.\gradlew.bat test --tests "com.algocompiler.DebugTest"
```

### Exécuter tous les tests du projet
```bash
.\gradlew.bat test
```

### Résultats
✅ 15 tests de débogage passent avec succès
✅ Tous les tests du projet passent

## Améliorations futures

1. **Messages d'erreur enrichis** : Inclure un extrait de la ligne source où l'erreur s'est produite
2. **Stack trace contextualisée** : Afficher le contexte d'exécution (fonction en cours, variables locales)
3. **Suggestions de correction** : Proposer des corrections possibles pour les erreurs courantes
4. **Mode débogage interactif** : Permettre l'exécution pas à pas avec inspection des variables
5. **Vérification de division par zéro** : Ajouter une vérification explicite avant la division
6. **Rapport d'erreur HTML** : Générer un rapport visuel des erreurs avec mise en évidence du code

## Date de création
2025-01-11

## Auteur
Système de tests automatisé Algo-compiler

