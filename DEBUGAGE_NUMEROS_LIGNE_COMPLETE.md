# Amélioration du Débogage - Affichage des Numéros de Ligne

**Date:** 11 décembre 2025  
**Version:** 1.6.0  
**Statut:** ✅ Complété et testé

## Objectif

Améliorer les messages d'erreur du compilateur d'algorithmes en ajoutant le numéro de ligne où l'erreur s'est produite, permettant aux utilisateurs de localiser rapidement les problèmes dans leur code.

## Modifications Techniques

### 1. Fichier: `AST.kt`

**Changement principal:** Ajout du champ `line` à tous les nœuds de l'arbre de syntaxe abstraite (AST).

```kotlin
// Avant
sealed class ASTNode

// Après
sealed class ASTNode(open val line: Int = 0)
```

**Nœuds modifiés:**
- `ASTNode` (classe de base)
- `Statement` et toutes ses sous-classes (Assignment, IfStatement, ForLoop, WhileLoop, etc.)
- `Expression` et toutes ses sous-classes (BinaryOp, UnaryOp, Variable, etc.)
- Classes auxiliaires (ElseIfClause, WhenCase, ReadTarget, etc.)

**Impact:** 
- Valeur par défaut `0` pour compatibilité
- Tous les nœuds peuvent maintenant stocker leur position dans le code source

### 2. Fichier: `Parser.kt`

**Changement principal:** Capture et propagation des numéros de ligne lors de la création des nœuds AST.

**Méthode utilisée:**
```kotlin
// Capture du token avec son numéro de ligne
val startToken = current()
// ... parsing ...
// Création du nœud avec le numéro de ligne
Assignment(name, expression, startToken.line)
```

**Fonctions modifiées:**
- `parseAssignmentOrFunctionCall()`
- `parseIfStatement()`
- `parseForLoop()`
- `parseWhileLoop()`
- `parseRepeatUntilLoop()`
- `parseWhenStatement()`
- `parseWriteStatement()` / `parseWriteLnStatement()`
- `parseReadStatement()`
- `parseReturnStatement()`
- `parseConditional()`
- `parseLogicalOr()` / `parseLogicalAnd()`
- `parseComparison()` / `parseAdditive()` / `parseMultiplicative()` / `parsePower()`
- `parseUnary()`
- `parsePrimary()`

**Impact:**
- Chaque nœud AST créé contient maintenant le numéro de ligne du token correspondant
- Propagation automatique de l'information de position dans tout l'arbre

### 3. Fichier: `Interpreter.kt`

**Changements principaux:**

#### a) Ajout du tracking de ligne courante
```kotlin
private var currentLine = 0 // Numéro de ligne en cours d'exécution
```

#### b) Création de la méthode runtimeError
```kotlin
private fun runtimeError(message: String): Nothing {
    throw Exception("Erreur ligne $currentLine: $message")
}
```

#### c) Mise à jour de currentLine dans executeStatement
```kotlin
private fun executeStatement(statement: Statement) {
    currentLine = statement.line  // ← Ajout
    when (statement) {
        // ... exécution ...
    }
}
```

#### d) Mise à jour de currentLine dans evaluateExpression
```kotlin
private fun evaluateExpression(expression: Expression): Any {
    if (expression.line > 0) {
        currentLine = expression.line  // ← Ajout
    }
    return when (expression) {
        // ... évaluation ...
    }
}
```

#### e) Remplacement de tous les throw Exception
```bash
# Commande utilisée pour le remplacement automatique
$content = $content -replace 'throw Exception\(', 'runtimeError('
```

**Fonctions affectées:** Plus de 50 occurrences remplacées dans:
- `initializeVariable()`
- `executeAssignment()`
- `executeArrayAssignment()`
- `executeMatrixAssignment()`
- `executeReadStatement()`
- `executeFunctionCall()`
- `tryExecuteBuiltInFunction()` (toutes les fonctions intégrées)
- `evaluateExpression()`

**Impact:**
- Tous les messages d'erreur incluent maintenant le numéro de ligne
- Format standardisé: "Erreur ligne X: [message]"

## Tests Effectués

### Test 1: Index hors limites pour tableau ✅
```algo
algorithme TestDebug
variables
    T : tableau[5] de entier
debut
    x = T[10]  // ligne 11
fin
```
**Résultat:** `Erreur ligne 11: Index 10 hors limites pour le tableau 'T'`

### Test 2: Variable non définie ✅
```algo
algorithme TestDebug3
debut
    ecrireln(inexistante)  // ligne 8
fin
```
**Résultat:** `Erreur ligne 8: Variable 'inexistante' non définie`

### Test 3: Index hors limites pour matrice ✅
```algo
algorithme TestDebugMatrice
variables
    M : tableau[3][3] de entier
debut
    M[5][2] = 100  // ligne 14
fin
```
**Résultat:** `Erreur ligne 14: Index ligne 5 hors limites pour la matrice 'M'`

## Avantages pour l'Utilisateur

### Avant
```
Exception: Index 10 hors limites pour le tableau 'T'
```
❌ L'utilisateur doit chercher manuellement dans tout son code

### Après
```
Erreur ligne 11: Index 10 hors limites pour le tableau 'T'
```
✅ L'utilisateur sait exactement où se trouve le problème

## Bénéfices

1. **Débogage rapide:** Localisation immédiate de l'erreur
2. **Meilleure expérience utilisateur:** Messages clairs et précis
3. **Conformité aux standards:** Format "ligne X" utilisé par la plupart des compilateurs
4. **Pédagogie:** Facilite l'apprentissage pour les débutants
5. **Productivité:** Moins de temps perdu à chercher les erreurs

## Compilation et Tests

- ✅ Compilation réussie avec Gradle
- ✅ Tous les tests unitaires passent (92 tests)
- ✅ Tests manuels validés pour différents types d'erreurs
- ✅ Pas de régression sur les fonctionnalités existantes

## Fichiers Créés

1. `TESTS_DEBUGAGE_RESUME.md` - Résumé des tests de débogage
2. `exemples_debugage.algo` - Exemples d'erreurs courantes avec messages attendus
3. `test_debug.algo` - Test d'erreur d'index tableau
4. `test_debug3.algo` - Test de variable non définie
5. `test_debug_matrice.algo` - Test d'erreur d'index matrice
6. `test_debug_fonction.algo` - Test de fonction non définie

## Commit et Déploiement

**Commit:** `debdf28`  
**Message:** "Ajout numéros de ligne dans messages erreur"  
**Push:** ✅ Effectué sur GitHub (main)

## Prochaines Étapes Possibles

1. Ajouter la colonne en plus de la ligne pour une précision encore meilleure
2. Afficher un extrait du code source autour de l'erreur
3. Coloriser les messages d'erreur dans le terminal
4. Créer un mode "verbose" avec trace d'exécution complète
5. Générer un fichier de log des erreurs pour analyse

## Conclusion

Cette amélioration majeure du système de débogage rend le compilateur d'algorithmes beaucoup plus convivial et professionnel. Les utilisateurs peuvent maintenant identifier et corriger rapidement leurs erreurs grâce aux numéros de ligne précis dans tous les messages d'erreur.

---

**Documentation complète disponible dans:**
- `TESTS_DEBUGAGE_RESUME.md` - Tests et validation
- `exemples_debugage.algo` - Exemples pratiques

