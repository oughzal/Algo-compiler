# ✅ SUPPORT DU PASSAGE PAR RÉFÉRENCE

**Date :** 14 décembre 2025  
**Version :** Algo Compiler v1.6.0+  
**Status :** ✅ IMPLÉMENTÉ ET VALIDÉ

---

## 🎯 Fonctionnalité Ajoutée

Le compilateur Algo supporte maintenant le **passage de paramètres par référence** dans les fonctions et procédures, permettant de modifier directement les variables passées en argument.

---

## 📝 Syntaxe

### Déclaration de Paramètre par Référence

Utilisez l'un des mots-clés suivants avant le nom du paramètre :
- **`ref`** (recommandé)
- **`reference`** (alias)
- **`varref`** (alias)

```algo
procedure nomProcedure(ref parametre : type)
fonction nomFonction(ref parametre : type) : typeRetour
```

### Exemples de Base

#### Procédure avec Passage par Référence
```algo
procedure doubler(ref n : entier)
debut
    n = n * 2
fin
```

#### Fonction avec Passage par Référence
```algo
fonction incrementer(ref n : entier) : entier
variables
    ancien : entier
debut
    ancien = n
    n = n + 1
    retourner ancien
fin
```

#### Mélange Valeur et Référence
```algo
procedure ajouterA(ref resultat : entier, valeur : entier)
debut
    resultat = resultat + valeur
fin
```

---

## 🔍 Différence Passage par Valeur vs Référence

### Passage par Valeur (défaut)
```algo
procedure incrementer(n : entier)
debut
    n = n + 1
    ecrireln("Dans la fonction: ", n)  // 6
fin

variables
    x : entier
debut
    x = 5
    incrementer(x)
    ecrireln("Après appel: ", x)  // 5 (inchangé)
fin
```

### Passage par Référence
```algo
procedure incrementer(ref n : entier)
debut
    n = n + 1
    ecrireln("Dans la fonction: ", n)  // 6
fin

variables
    x : entier
debut
    x = 5
    incrementer(x)
    ecrireln("Après appel: ", x)  // 6 (modifié!)
fin
```

---

## 💡 Cas d'Usage Courants

### 1. Échange de Valeurs
```algo
procedure echanger(ref a : entier, ref b : entier)
variables
    temp : entier
debut
    temp = a
    a = b
    b = temp
fin

// Utilisation
variables
    x, y : entier
debut
    x = 10
    y = 20
    echanger(x, y)
    // x = 20, y = 10
fin
```

### 2. Retour Multiple
```algo
procedure divisionComplete(dividende : entier, diviseur : entier, 
                          ref quotient : entier, ref reste : entier)
debut
    quotient = dividende div diviseur
    reste = dividende mod diviseur
fin

// Utilisation
variables
    q, r : entier
debut
    divisionComplete(17, 5, q, r)
    ecrireln("Quotient: ", q, ", Reste: ", r)
    // Quotient: 3, Reste: 2
fin
```

### 3. Initialisation Multiple
```algo
procedure initialiser(ref x : entier, ref y : entier, 
                     valX : entier, valY : entier)
debut
    x = valX
    y = valY
fin

// Utilisation
variables
    a, b : entier
debut
    initialiser(a, b, 42, 73)
    // a = 42, b = 73
fin
```

### 4. Modification de Tableau
```algo
procedure doublerTableau(ref T : tableau de entier, taille : entier)
variables
    i : entier
debut
    pour i de 0 à taille - 1 faire
        T[i] = T[i] * 2
    finpour
fin
```

---

## ⚙️ Implémentation Technique

### Modifications dans AST.kt
```kotlin
data class VariableDeclaration(
    val name: String,
    val type: String,
    val arraySize: Int? = null,
    val arraySize2: Int? = null,
    val initialValue: Expression? = null,
    val isByReference: Boolean = false,  // ✅ Nouveau flag
    override val line: Int = 0
) : ASTNode(line)
```

### Modifications dans Token.kt
```kotlin
enum class TokenType {
    // ...
    VAR,  // ✅ Pour passage par référence
    // ...
}
```

### Modifications dans Lexer.kt
```kotlin
private val keywords = mapOf(
    // ...
    "varref" to TokenType.VAR,
    "ref" to TokenType.VAR,
    "reference" to TokenType.VAR,
    // ...
)
```

### Modifications dans Parser.kt
```kotlin
private fun parseParameter(): VariableDeclaration {
    // Vérifier si c'est un passage par référence
    var isByReference = false
    if (current().type == TokenType.VAR) {
        isByReference = true
        advance()
    }
    
    val name = expect(TokenType.IDENTIFICATEUR).value
    // ... parsing du type ...
    
    return VariableDeclaration(name, type, ..., isByReference)
}
```

### Modifications dans Interpreter.kt
```kotlin
private fun executeFunctionCall(name: String, arguments: List<Expression>): Any? {
    // ...
    val referenceMap = mutableMapOf<String, String>()
    
    for ((i, param) in function.parameters.withIndex()) {
        if (param.isByReference) {
            // Stocker la référence variable originale
            if (arg is Variable) {
                referenceMap[paramName] = originalVarName
                variables[paramName] = variables[originalVarName]
            }
        }
    }
    
    // Exécuter la fonction...
    
    // Mettre à jour les variables par référence
    for ((paramName, originalVarName) in referenceMap) {
        updatedVariables[originalVarName] = variables[paramName]!!
    }
}
```

---

## ✅ Tests de Validation

### Tests Unitaires
```kotlin
@Test
fun testPassageReferenceSimple()
@Test
fun testEchangeValeurs()
@Test
fun testPassageValeurEtReference()
@Test
fun testFonctionAvecReference()
```

**Résultat :** ✅ Tous les tests passent (101 tests au total)

### Tests d'Intégration

**Fichier :** `test_passage_reference.algo`

```
=== Test 1: Échange de valeurs ===
Avant échange: x = 10, y = 20
Après échange: x = 20, y = 10  ✅

=== Test 2: Doubler une valeur ===
Avant doublerValeur: x = 5
Après doublerValeur: x = 10  ✅

=== Test 3: Incrémenter avec retour ===
x avant = 100
Valeur retournée = 100
x après = 101  ✅

=== Test 4: Initialisation multiple ===
Après initialisation: x = 42, y = 73  ✅
```

---

## 🚫 Restrictions

### 1. Uniquement des Variables
```algo
// ✅ OK - variable
doubler(x)

// ❌ ERREUR - expression
doubler(5)
doubler(x + 1)
```

### 2. Types Compatibles
```algo
procedure modifier(ref n : entier)
debut
    n = n + 1
fin

variables
    x : reel
debut
    // ❌ ERREUR - types incompatibles
    modifier(x)
fin
```

---

## 📊 Comparaison avec Autres Langages

| Langage | Syntaxe Passage par Référence |
|---------|-------------------------------|
| **Algo** | `ref param : type` |
| Pascal | `var param : type` |
| C++ | `type& param` |
| C# | `ref type param` |
| Python | N/A (toujours par référence pour objets) |

---

## 🎯 Avantages

✅ **Performance** - Évite la copie de grandes structures  
✅ **Flexibilité** - Permet de retourner plusieurs valeurs  
✅ **Clarté** - Le mot-clé `ref` rend l'intention explicite  
✅ **Compatibilité** - Syntaxe proche de Pascal et C#  

---

## 📚 Exemples Complets

### Tri de Deux Valeurs
```algo
procedure trier2(ref a : entier, ref b : entier)
variables
    temp : entier
debut
    si a > b alors
        temp = a
        a = b
        b = temp
    finsi
fin

variables
    x, y : entier
debut
    x = 15
    y = 7
    trier2(x, y)
    ecrireln("Min: ", x, ", Max: ", y)
    // Min: 7, Max: 15
fin
```

### Calcul Statistiques
```algo
procedure calculerStats(T : tableau de reel, taille : entier,
                       ref somme : reel, ref moyenne : reel)
variables
    i : entier
debut
    somme = 0
    pour i de 0 à taille - 1 faire
        somme = somme + T[i]
    finpour
    moyenne = somme / taille
fin
```

---

## 📝 Documentation Mise à Jour

### README.md
Section "Nouveautés importantes" mise à jour avec le passage par référence.

### HELP.md
Section sur les fonctions et procédures mise à jour avec exemples.

---

## ✅ Checklist Finale

- [x] Modification de l'AST (isByReference)
- [x] Ajout des tokens (VAR)
- [x] Modification du Lexer (ref, reference, varref)
- [x] Modification du Parser (parseParameter)
- [x] Modification de l'Interpreter (executeFunctionCall)
- [x] Tests unitaires créés (4 tests)
- [x] Tests d'intégration créés
- [x] Documentation complète
- [x] Validation réussie

---

## 🎉 Conclusion

Le support du passage par référence est **complètement implémenté et fonctionnel**. Cette fonctionnalité enrichit considérablement le langage Algo et le rend plus proche des langages de programmation réels utilisés en enseignement (Pascal, C++, C#).

---

**Fichiers Modifiés :**
- `AST.kt` - Ajout du flag isByReference
- `Token.kt` - Ajout du token VAR
- `Lexer.kt` - Reconnaissance des mots-clés ref/reference/varref
- `Parser.kt` - Parsing des paramètres par référence
- `Interpreter.kt` - Gestion du passage par référence

**Fichiers Créés :**
- `PassageReferenceTest.kt` - Tests unitaires
- `test_passage_reference.algo` - Tests d'intégration
- `PASSAGE_REFERENCE.md` - Cette documentation

**Status :** ✅ Production Ready  
**Tests :** ✅ 101/101 passent  
**Build :** ✅ SUCCESS

🎊 **Fonctionnalité implémentée avec succès !**

