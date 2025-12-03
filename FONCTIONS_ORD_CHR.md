# Fonctions ord() et chr() - Documentation

## Date
3 Décembre 2025

## Résumé

Les fonctions intégrées `ord()` et `chr()` ont été ajoutées au compilateur pour permettre la manipulation avancée des caractères via leurs codes ASCII.

---

## Fonctions ajoutées

### 1. `ord(caractere)` : Caractère → Code ASCII

**Description :** Retourne le code ASCII (0-127) d'un caractère.

**Paramètres :**
- `caractere` : Un caractère (type `caractere` ou premier caractère d'une chaîne)

**Retour :** Entier (code ASCII)

**Exemples :**
```algo
c : caractere
code : entier

c = 'A'
code = ord(c)        // code = 65

code = ord('Z')      // code = 90
code = ord('a')      // code = 97
code = ord('0')      // code = 48
code = ord(' ')      // code = 32 (espace)
```

**Codes ASCII courants :**
- `'0'` à `'9'` : 48 à 57
- `'A'` à `'Z'` : 65 à 90
- `'a'` à `'z'` : 97 à 122
- `' '` (espace) : 32
- `'\n'` (nouvelle ligne) : 10
- `'\t'` (tabulation) : 9

---

### 2. `chr(code)` : Code ASCII → Caractère

**Description :** Retourne le caractère correspondant à un code ASCII.

**Paramètres :**
- `code` : Un entier entre 0 et 127 (code ASCII)

**Retour :** Caractère (type `caractere`)

**Exemples :**
```algo
lettre : caractere
code : entier

lettre = chr(65)      // lettre = 'A'
lettre = chr(90)      // lettre = 'Z'
lettre = chr(97)      // lettre = 'a'
lettre = chr(48)      // lettre = '0'
lettre = chr(32)      // lettre = ' ' (espace)
```

**Erreur :** Si le code n'est pas dans la plage 0-127, une exception est levée.

---

## Cas d'utilisation

### 1. Conversion aller-retour
```algo
algorithme ConversionAllerRetour

variables
    original : caractere
    code : entier
    retour : caractere

debut
    original = 'M'
    code = ord(original)          // code = 77
    retour = chr(code)            // retour = 'M'
    
    si original == retour alors
        ecrireln("✓ Conversion réussie!")
    finsi
fin
```

### 2. Chiffrement César (décalage simple)
```algo
algorithme ChiffrementCesar

variables
    message : caractere
    chiffre : caractere
    decalage : entier

debut
    message = 'A'
    decalage = 3
    
    // Chiffrer : A + 3 = D
    chiffre = chr(ord(message) + decalage)
    ecrire("Message: ", message, " -> Chiffré: ", chiffre)
    ecrireln()
    
    // Déchiffrer : D - 3 = A
    message = chr(ord(chiffre) - decalage)
    ecrire("Déchiffré: ", message)
    ecrireln()
fin
```

### 3. Vérifier le type de caractère
```algo
algorithme VerifierTypeCaractere

variables
    c : caractere

debut
    ecrire("Entrez un caractère: ")
    lire(c)
    
    // Vérifier si c'est une majuscule
    si ord(c) >= ord('A') et ord(c) <= ord('Z') alors
        ecrireln("C'est une MAJUSCULE")
    finsi
    
    // Vérifier si c'est une minuscule
    si ord(c) >= ord('a') et ord(c) <= ord('z') alors
        ecrireln("C'est une minuscule")
    finsi
    
    // Vérifier si c'est un chiffre
    si ord(c) >= ord('0') et ord(c) <= ord('9') alors
        ecrireln("C'est un CHIFFRE")
    finsi
fin
```

### 4. Conversion majuscule ↔ minuscule
```algo
algorithme ConversionCasse

variables
    c : caractere
    converti : caractere
    diff : entier

debut
    // La différence entre une minuscule et sa majuscule est 32
    diff = ord('a') - ord('A')    // diff = 32
    
    // Majuscule → Minuscule
    c = 'A'
    converti = chr(ord(c) + diff)
    ecrire("'", c, "' en minuscule: '", converti, "'")
    ecrireln()
    
    // Minuscule → Majuscule
    c = 'z'
    converti = chr(ord(c) - diff)
    ecrire("'", c, "' en majuscule: '", converti, "'")
    ecrireln()
fin
```

### 5. Générer l'alphabet
```algo
algorithme GenererAlphabet

variables
    i : entier
    lettre : caractere

debut
    ecrire("Alphabet majuscule: ")
    pour i de ord('A') à ord('Z') faire
        lettre = chr(i)
        ecrire(lettre, " ")
    finpour
    ecrireln()
    
    ecrire("Alphabet minuscule: ")
    pour i de ord('a') à ord('z') faire
        lettre = chr(i)
        ecrire(lettre, " ")
    finpour
    ecrireln()
fin
```

### 6. Calculer la distance entre deux lettres
```algo
algorithme DistanceLettres

variables
    c1, c2 : caractere
    distance : entier

debut
    c1 = 'A'
    c2 = 'D'
    
    distance = ord(c2) - ord(c1)
    ecrire("Distance entre '", c1, "' et '", c2, "': ", distance)
    ecrireln()
    // Résultat: 3 (D est 3 positions après A)
fin
```

---

## Limites et considérations

### Codes ASCII valides
- Plage acceptée : **0 à 127** (ASCII standard)
- Au-delà de 127 : Exception levée par `chr()`

### Caractères spéciaux
```algo
// Codes ASCII des caractères spéciaux courants
code = ord('\n')     // 10  (nouvelle ligne)
code = ord('\t')     // 9   (tabulation)
code = ord(' ')      // 32  (espace)
code = ord('!')      // 33
code = ord('?')      // 63
```

### Conversion de chaînes
```algo
// ord() peut prendre une chaîne et retourne le code du 1er caractère
s : chaine
s = "Hello"
code = ord(s)        // code = 72 (code de 'H')
```

---

## Table ASCII de référence rapide

| Plage | Caractères | Description |
|-------|------------|-------------|
| 0-31 | Contrôle | Caractères de contrôle (non imprimables) |
| 32 | ` ` | Espace |
| 48-57 | `0-9` | Chiffres |
| 65-90 | `A-Z` | Lettres majuscules |
| 97-122 | `a-z` | Lettres minuscules |
| 33-47, 58-64, 91-96, 123-126 | `!@#$%^&*` etc. | Symboles et ponctuation |

---

## Fichiers de test

**`examples/test_ord_chr.algo`** : Test complet des fonctions ord() et chr()
- Conversion caractère ↔ code ASCII
- Conversion aller-retour
- Décalage de caractères
- Vérification de type (majuscule/minuscule)

**Pour exécuter :**
```powershell
java -jar build/libs/algo-compiler-1.0.0.jar examples/test_ord_chr.algo
```

---

## Documentation mise à jour

Les sections suivantes ont été mises à jour :
- ✅ `README.md` : Section "Fonctions intégrées > Fonctions de caractères"
- ✅ `MODIFICATIONS_TYPE_CARACTERE.md` : Exemples et liste des fonctions
- ✅ `src/main/kotlin/com/algocompiler/Interpreter.kt` : Implémentation

---

## Compatibilité

- **Version du projet** : 1.0.0
- **Kotlin** : 1.9.20
- **Java** : 21
- **Date d'ajout** : 3 Décembre 2025

---

**Les fonctions `ord()` et `chr()` sont maintenant disponibles et pleinement fonctionnelles ! 🎉**

