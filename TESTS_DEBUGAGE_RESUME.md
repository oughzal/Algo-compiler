# Résumé des tests de débogage avec numéros de ligne

## Tests effectués

### ✅ Test 1 : Index hors limites pour tableau
**Fichier:** test_debug.algo  
**Ligne d'erreur:** 11  
**Code:** `x = T[10]` (tableau de taille 5)  
**Message d'erreur:**
```
Erreur: Erreur ligne 11: Index 10 hors limites pour le tableau 'T'
```
**Résultat:** ✅ Le numéro de ligne est correctement affiché

---

### ✅ Test 2 : Variable non définie
**Fichier:** test_debug3.algo  
**Ligne d'erreur:** 8  
**Code:** `ecrireln(inexistante)`  
**Message d'erreur:**
```
Erreur: Erreur ligne 8: Variable 'inexistante' non définie
```
**Résultat:** ✅ Le numéro de ligne est correctement affiché

---

### ✅ Test 3 : Index hors limites pour matrice
**Fichier:** test_debug_matrice.algo  
**Ligne d'erreur:** 14  
**Code:** `M[5][2] = 100` (matrice 3x3)  
**Message d'erreur:**
```
Erreur: Erreur ligne 14: Index ligne 5 hors limites pour la matrice 'M'
```
**Résultat:** ✅ Le numéro de ligne est correctement affiché

---

## Modifications apportées

### 1. Ajout du numéro de ligne dans les nœuds AST
- Modifié `AST.kt` pour ajouter un champ `line: Int` à tous les nœuds
- `ASTNode`, `Statement`, `Expression`, et toutes leurs sous-classes ont maintenant un champ `line`

### 2. Mise à jour du Parser
- Modifié `Parser.kt` pour capturer et passer le numéro de ligne lors de la création de chaque nœud AST
- Le `Token` contient déjà le numéro de ligne, qui est maintenant propagé aux nœuds AST

### 3. Amélioration de l'Interpreter
- Ajout d'un champ `currentLine` dans `Interpreter.kt`
- Ajout d'une méthode `runtimeError(message: String)` qui préfixe les erreurs avec le numéro de ligne
- Mise à jour de `currentLine` dans `executeStatement()` avant l'exécution de chaque instruction
- Mise à jour de `currentLine` dans `evaluateExpression()` pour les expressions
- Remplacement de tous les `throw Exception` par `runtimeError()` pour inclure le numéro de ligne

### 4. Bénéfices
- **Messages d'erreur plus clairs:** Les utilisateurs voient immédiatement à quelle ligne se trouve l'erreur
- **Débogage facilité:** Plus besoin de chercher manuellement où se trouve le problème
- **Meilleure expérience utilisateur:** Les messages suivent le format standard "Erreur ligne X: ..."

## Exemples de messages d'erreur

Avant la modification:
```
Exception: Index 10 hors limites pour le tableau 'T'
```

Après la modification:
```
Erreur: Erreur ligne 11: Index 10 hors limites pour le tableau 'T'
```

Le numéro de ligne permet à l'utilisateur de localiser immédiatement l'erreur dans son code source.

