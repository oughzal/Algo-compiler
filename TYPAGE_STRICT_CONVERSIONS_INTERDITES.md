# ✅ Typage Fort Strict - Conversions Interdites

## Date : 9 Décembre 2025

---

## 🎯 Objectif

Renforcer le typage en **interdisant** certaines conversions implicites et **forcer l'utilisation de fonctions de conversion explicites**.

---

## 🚫 CONVERSIONS INTERDITES

Les affectations suivantes génèrent maintenant des **erreurs explicites** :

### 1. ❌ chaine → caractere
```algo
variables
    s : chaine
    c : caractere

debut
    s = "Hello"
    c = s  // ❌ ERREUR: Impossible de convertir une chaine en caractere
fin
```

**Message d'erreur** :
```
Erreur de type : Impossible de convertir une chaine en caractere. 
La chaine peut contenir plusieurs caractères.
```

**Solution** : Utiliser un accès par index ou substr
```algo
c = s[0]  // Prendre le premier caractère (si implémenté)
```

---

### 2. ❌ entier → chaine
```algo
variables
    i : entier
    s : chaine

debut
    i = 42
    s = i  // ❌ ERREUR: Impossible de convertir un entier en chaine
fin
```

**Message d'erreur** :
```
Erreur de type : Impossible de convertir un entier en chaine. 
Utilisez versChaine() pour une conversion explicite.
```

**Solution** : Utiliser la fonction `versChaine()`
```algo
s = versChaine(i)  // ✅ Conversion explicite
```

---

### 3. ❌ reel → chaine
```algo
variables
    r : reel
    s : chaine

debut
    r = 3.14
    s = r  // ❌ ERREUR: Impossible de convertir un reel en chaine
fin
```

**Message d'erreur** :
```
Erreur de type : Impossible de convertir un reel en chaine. 
Utilisez versChaine() pour une conversion explicite.
```

**Solution** : Utiliser la fonction `versChaine()`
```algo
s = versChaine(r)  // ✅ Conversion explicite
```

---

### 4. ❌ booleen → chaine
```algo
variables
    b : booleen
    s : chaine

debut
    b = vrai
    s = b  // ❌ ERREUR: Impossible de convertir un booleen en chaine
fin
```

**Message d'erreur** :
```
Erreur de type : Impossible de convertir un booleen en chaine. 
Utilisez versChaine() pour une conversion explicite.
```

**Solution** : Utiliser la fonction `versChaine()` ou une condition
```algo
s = versChaine(b)  // ✅ Conversion explicite
// Ou
s = si b alors "vrai" sinon "faux"  // ✅ Expression conditionnelle
```

---

### 5. ❌ chaine → entier
```algo
variables
    s : chaine
    i : entier

debut
    s = "123"
    i = s  // ❌ ERREUR: Impossible de convertir une chaine en entier
fin
```

**Message d'erreur** :
```
Erreur de type : Impossible de convertir une chaine en entier. 
Utilisez versEntier() pour une conversion explicite.
```

**Solution** : Utiliser la fonction `versEntier()`
```algo
i = versEntier(s)  // ✅ Conversion explicite avec parsing
```

---

### 6. ❌ chaine → reel
```algo
variables
    s : chaine
    r : reel

debut
    s = "3.14"
    r = s  // ❌ ERREUR: Impossible de convertir une chaine en reel
fin
```

**Message d'erreur** :
```
Erreur de type : Impossible de convertir une chaine en reel. 
Utilisez versReel() pour une conversion explicite.
```

**Solution** : Utiliser la fonction `versReel()`
```algo
r = versReel(s)  // ✅ Conversion explicite avec parsing
```

---

## ✅ CONVERSIONS AUTORISÉES (Implicites)

Ces conversions continuent de fonctionner automatiquement :

### 1. ✅ entier → reel
```algo
variables
    i : entier
    r : reel

debut
    i = 42
    r = i  // ✅ Conversion automatique vers 42.0
fin
```

### 2. ✅ caractere → chaine
```algo
variables
    c : caractere
    s : chaine

debut
    c = 'A'
    s = c  // ✅ Conversion automatique vers "A"
fin
```

### 3. ✅ caractere → entier (ord)
```algo
variables
    c : caractere
    i : entier

debut
    c = 'A'
    i = c  // ✅ Conversion automatique vers 65 (code ASCII)
fin
```

### 4. ✅ entier → caractere (chr)
```algo
variables
    i : entier
    c : caractere

debut
    i = 65
    c = i  // ✅ Conversion automatique vers 'A' (si dans les limites)
fin
```

### 5. ✅ reel → entier (troncature)
```algo
variables
    r : reel
    i : entier

debut
    r = 3.14
    i = r  // ✅ Troncature automatique vers 3
fin
```

---

## 🎯 FONCTIONS DE CONVERSION EXPLICITES

Pour les conversions interdites en implicite, utilisez ces fonctions :

### Vers Chaîne
```algo
s = versChaine(42)      // entier → chaine
s = versChaine(3.14)    // reel → chaine
s = versChaine(vrai)    // booleen → chaine ("vrai")
```

### Vers Nombre
```algo
i = versEntier("123")   // chaine → entier (avec parsing)
r = versReel("3.14")    // chaine → reel (avec parsing)
```

### Vers Booléen
```algo
b = (s == "vrai")       // Comparaison explicite
```

---

## 📊 TABLEAU RÉCAPITULATIF

| Depuis ↓ / Vers → | entier | reel | chaine | caractere | booleen |
|-------------------|--------|------|--------|-----------|---------|
| **entier** | = | ✅ Auto | ❌ versChaine() | ✅ chr() | Auto |
| **reel** | ✅ Tronc | = | ❌ versChaine() | chr(int) | Auto |
| **chaine** | ❌ versEntier() | ❌ versReel() | = | ❌ INTERDIT | Comp. |
| **caractere** | ✅ ord() | ord() | ✅ Auto | = | Auto |
| **booleen** | Auto | Auto | ❌ versChaine() | - | = |

**Légende** :
- `=` : Même type, aucune conversion
- ✅ **Auto** : Conversion implicite automatique
- ❌ **versX()** : Conversion explicite requise via fonction
- ❌ **INTERDIT** : Conversion impossible/non supportée
- `Tronc` : Troncature automatique
- `Comp.` : Comparaison explicite nécessaire

---

## 🔧 MODIFICATIONS TECHNIQUES

### Fichier Modifié
`src/main/kotlin/com/algocompiler/Interpreter.kt`

### Fonction `castToType()`
```kotlin
private fun castToType(value: Any, targetType: String): Any {
    // ...
    when (normalizedTargetType) {
        "entier" -> {
            when (value) {
                is String -> {
                    // ❌ INTERDIT
                    throw Exception("Erreur de type : Impossible de convertir 
                        une chaine en entier. Utilisez versEntier()...")
                }
                // ...
            }
        }
        "chaine" -> {
            when (value) {
                is Int -> {
                    // ❌ INTERDIT
                    throw Exception("Erreur de type : Impossible de convertir 
                        un entier en chaine. Utilisez versChaine()...")
                }
                // ...
            }
        }
        "caractere" -> {
            when (value) {
                is String -> {
                    // ❌ INTERDIT
                    throw Exception("Erreur de type : Impossible de convertir 
                        une chaine en caractere...")
                }
                // ...
            }
        }
    }
}
```

---

## ✅ TESTS À EFFECTUER

### Test 1 : Erreur chaine → caractere
```algo
// test-erreur-chaine-char.algo
variables
    s : chaine
    c : caractere
debut
    s = "Hello"
    c = s  // ❌ Doit générer une erreur
fin
```

**Résultat attendu** : Exception avec message d'erreur clair

### Test 2 : Erreur entier → chaine
```algo
// test-erreur-entier-chaine.algo
variables
    i : entier
    s : chaine
debut
    i = 42
    s = i  // ❌ Doit générer une erreur
fin
```

**Résultat attendu** : Exception avec message "Utilisez versChaine()"

### Test 3 : Erreur chaine → nombre
```algo
// test-erreur-chaine-nombre.algo
variables
    s : chaine
    i : entier
debut
    s = "123"
    i = s  // ❌ Doit générer une erreur
fin
```

**Résultat attendu** : Exception avec message "Utilisez versEntier()"

### Test 4 : Conversion explicite OK
```algo
variables
    i : entier
    s : chaine
debut
    i = 42
    s = versChaine(i)  // ✅ Doit fonctionner
    ecrireln("s = ", s)
fin
```

**Résultat attendu** : `s = 42` affiché sans erreur

---

## 🎯 AVANTAGES

### Clarté du Code
- ✅ Les conversions sont **explicites** et **visibles**
- ✅ Intention du programmeur **claire**
- ✅ Code plus **lisible** et **maintenable**

### Prévention d'Erreurs
- ✅ Détection des conversions **non intentionnelles**
- ✅ Évite les bugs **subtils** de typage
- ✅ Messages d'erreur **clairs** et **informatifs**

### Typage Plus Fort
- ✅ Respect strict des **types déclarés**
- ✅ Moins de **conversions implicites** surprenantes
- ✅ Comportement **prévisible**

---

## 📚 EXEMPLES PRATIQUES

### Exemple 1 : Affichage de Nombres
```algo
algorithme AffichageNombres

variables
    age : entier
    message : chaine

debut
    age = 25
    
    // ❌ AVANT (autorisé mais maintenant interdit)
    // message = "Vous avez " + age + " ans"
    
    // ✅ MAINTENANT (conversion explicite)
    message = "Vous avez " + versChaine(age) + " ans"
    
    ecrireln(message)
fin
```

### Exemple 2 : Parsing de Chaînes
```algo
algorithme ParsingChaines

variables
    saisie : chaine
    nombre : entier

debut
    ecrire("Entrez un nombre: ")
    lire(saisie)
    
    // ❌ AVANT (autorisé mais maintenant interdit)
    // nombre = saisie
    
    // ✅ MAINTENANT (conversion explicite)
    nombre = versEntier(saisie)
    
    ecrireln("Le double est: ", nombre * 2)
fin
```

### Exemple 3 : Concaténation
```algo
algorithme Concatenation

variables
    pi : reel
    texte : chaine

debut
    pi = 3.14159
    
    // ❌ Conversion implicite interdite
    // texte = "Pi vaut " + pi
    
    // ✅ Conversion explicite requise
    texte = "Pi vaut " + versChaine(pi)
    
    ecrireln(texte)
fin
```

---

## 🔄 MIGRATION

### Si Vous Avez du Code Existant

1. **Identifier les conversions implicites**
   - Chercher les affectations entre types différents
   - Particulièrement : nombre → chaine, chaine → nombre

2. **Ajouter les conversions explicites**
   - Utiliser `versChaine()` pour nombre → chaine
   - Utiliser `versEntier()` ou `versReel()` pour chaine → nombre

3. **Tester le code**
   - Exécuter et corriger les erreurs de typage
   - Vérifier que le comportement est correct

---

## ✅ STATUT

- [x] Modifications implémentées dans `Interpreter.kt`
- [x] Messages d'erreur clairs ajoutés
- [x] Conversions implicites restreintes
- [ ] Compilation en cours
- [ ] Tests à effectuer
- [ ] Documentation à mettre à jour

---

**© 2024 Omar OUGHZAL** - Tous droits réservés

**Typage fort strict implémenté ! 🔒**

