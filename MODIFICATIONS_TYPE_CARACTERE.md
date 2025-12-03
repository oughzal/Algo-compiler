# Modifications - Support du Type Caractère

## Date
3 Décembre 2025

## Résumé des modifications

Le compilateur a été mis à jour pour supporter complètement le type `caractere` avec toutes les fonctionnalités associées.

---

## 1. Fichiers modifiés

### 1.1 `src/main/kotlin/com/algocompiler/Main.kt`
- **Ajout** : Prétraitement automatique des guillemets typographiques
- **Raison** : Convertir automatiquement les apostrophes typographiques (' et ') en apostrophes ASCII (') avant la tokenisation
- **Impact** : Les utilisateurs peuvent maintenant copier/coller du code depuis Word, éditeurs riches sans erreur

```kotlin
// Normalisation des quotes typographiques → ASCII
code = code.replace('\u2018', '\'')  // ' → '
           .replace('\u2019', '\'')  // ' → '
           .replace('\u201C', '"')   // " → "
           .replace('\u201D', '"')   // " → "
```

### 1.2 `src/main/kotlin/com/algocompiler/Lexer.kt`
- **Ajout** : Fonction `isSingleQuote()` pour reconnaître les 3 variantes d'apostrophes
- **Ajout** : Reconnaissance des guillemets typographiques (' ' ") en plus de l'ASCII
- **Amélioration** : Messages d'erreur plus verbeux avec code Unicode du caractère problématique
- **Impact** : Support natif des apostrophes typographiques + meilleur diagnostic des erreurs

```kotlin
private fun isSingleQuote(ch: Char?): Boolean {
    return ch == '\'' || ch == ''' || ch == '''
}
```

### 1.3 `src/main/kotlin/com/algocompiler/Parser.kt`
- **Ajout** : Support du type `TokenType.CARACTERE` dans :
  - Déclarations de variables (`parseVariableDeclarations`)
  - Déclarations de constantes (`parseConstantDeclarations`)
  - Paramètres de fonction (`parseParameter`)
  - Types de retour de fonction (`parseFunctionDeclaration`)
- **Suppression** : Fonction inutilisée `peek()` (suppression du warning)
- **Impact** : Le parser accepte maintenant `caractere` comme type valide partout

### 1.4 `src/main/kotlin/com/algocompiler/Interpreter.kt`
- **Ajout** : Initialisation des variables `caractere` à `'\u0000'` (caractère nul)
- **Ajout** : Support du type `Char` dans les conversions :
  - `toDouble(Char)` → convertit via `char.code`
  - `toInt(Char)` → convertit via `char.code`
  - `toBoolean(Char)` → `false` si '\u0000', `true` sinon
- **Ajout** : `formatValue(Char)` → affichage correct via `toString()`
- **Amélioration** : Lecture (`lire`) intelligente pour variables `Char` :
  - Détecte si la variable cible est un `Char`
  - Stocke le premier caractère de l'entrée utilisateur
  - Support pour tableaux de caractères
- **Impact** : Le type `caractere` fonctionne complètement (lecture, écriture, conversions, comparaisons)

### 1.5 `README.md`
- **Mise à jour** : Documentation complète du type `caractere`
- **Ajout** : Section dédiée aux caractères avec exemples
- **Clarification** : Comportement de `ecrire` (pas de saut de ligne) vs `ecrireln` (avec saut)
- **Ajout** : Documentation des séquences d'échappement (`\n`, `\t`, `\'`, `\\`)
- **Impact** : Documentation à jour et complète

---

## 2. Fonctionnalités ajoutées

### 2.1 Type `caractere`
- Déclaration : `c : caractere`
- Tableaux : `lettres : tableau[5] de caractere`
- Littéraux : `'A'`, `'z'`, `'\n'`, `'\t'`
- Constantes : `const NEWLINE : caractere = '\n'`

### 2.2 Opérations supportées
- **Affectation** : `c = 'a'`
- **Comparaison** : `'A' < 'B'`, `c == 'Z'`
- **Conversion** : Char ↔ Int (via code ASCII)
- **Concaténation** : `ecrire("Lettre: ", c)` (convertit Char → String)
- **Lecture** : `lire(c)` (lit le premier caractère de l'entrée)

### 2.3 Échappements supportés
- `\n` : Nouvelle ligne
- `\t` : Tabulation
- `\r` : Retour chariot
- `\\` : Backslash
- `\'` : Apostrophe

### 2.4 Tolérance apostrophes typographiques
Le compilateur accepte maintenant :
- Apostrophe ASCII : `'` (U+0027) ✅
- Apostrophe typographique ouvrante : `'` (U+2018) ✅ (converti auto)
- Apostrophe typographique fermante : `'` (U+2019) ✅ (converti auto)
- Guillemets doubles typographiques : `"` et `"` ✅ (convertis auto)

---

## 3. Configuration du projet

### Versions utilisées
- **Projet** : 1.0.0
- **Kotlin** : 1.9.20
- **Java (toolchain)** : 21
- **Gradle** : 8.5
- **JDK** : jdk-21 (Microsoft/Oracle)

### Artefacts générés
- **JAR** : `build/libs/algo-compiler-1.0.0.jar`
- **Scripts** : `algo-compiler` (Unix) et `algo-compiler.bat` (Windows)

---

## 4. Utilisation

### 4.1 Compilation
```powershell
# Windows PowerShell
.\gradlew.bat assemble
```

### 4.2 Exécution normale
```powershell
# Avec Java dans le PATH
java -jar build/libs/algo-compiler-1.0.0.jar examples/test_caractere.algo

# Avec chemin complet vers Java 21
& "C:\Program Files\Java\jdk-21\bin\java.exe" -jar build/libs/algo-compiler-1.0.0.jar examples/test_caractere.algo
```

### 4.3 Mode debug (afficher les tokens)
```powershell
java -Ddebug.tokens=1 -jar build/libs/algo-compiler-1.0.0.jar examples/test_caractere.algo
```
Utile pour diagnostiquer les problèmes de tokenisation.

---

## 5. Exemples

### 5.1 Exemple simple
```
algorithme TestChar

variables
    c : caractere

debut
    c = 'a'
    ecrire("Caractere: ", c)
    ecrireln()
fin
```

### 5.2 Exemple avec tableau
```
algorithme MotSecret

variables
    mot : tableau[5] de caractere
    i : entier

debut
    mot[0] = 'H'
    mot[1] = 'e'
    mot[2] = 'l'
    mot[3] = 'l'
    mot[4] = 'o'
    
    pour i de 0 à 4 faire
        ecrire(mot[i])
    finpour
    ecrireln()
fin
```

### 5.3 Exemple avec lecture
```
algorithme LireInitiale

variables
    initiale : caractere

debut
    ecrire("Entrez votre initiale: ")
    lire(initiale)
    ecrire("Votre initiale est: ", initiale)
    ecrireln()
fin
```

### 5.4 Exemple avec comparaison
```
algorithme VerifierMajuscule

variables
    c : caractere

debut
    ecrire("Entrez une lettre: ")
    lire(c)
    
    si c >= 'A' et c <= 'Z' alors
        ecrireln("C'est une majuscule")
    sinon
        ecrireln("Ce n'est pas une majuscule")
    finsi
fin
```

### 5.5 Exemple avec ord() et chr()
```
algorithme ConversionCaracteres

variables
    c : caractere
    code : entier
    lettre : caractere

debut
    // Conversion caractère -> code ASCII
    c = 'A'
    code = ord(c)
    ecrire("Le code ASCII de '", c, "' est ", code)
    ecrireln()
    
    // Conversion code ASCII -> caractère
    lettre = chr(65)
    ecrire("Le caractère de code 65 est '", lettre, "'")
    ecrireln()
    
    // Décalage de caractère (chiffrement simple)
    c = 'A'
    lettre = chr(ord(c) + 3)
    ecrire("'", c, "' + 3 positions = '", lettre, "'")
    ecrireln()
fin
```

---

## 6. Tests disponibles

Les fichiers de test suivants sont fournis dans `examples/` :
- `test_caractere.algo` : Test complet du type caractère
- `test_char_simple.algo` : Test minimal d'affectation/affichage
- `test_ord_chr.algo` : Test des fonctions ord() et chr()
- `temp_test_char.algo` : Test rapide pour debug

---

## 7. Résolution de problèmes

### Problème : "Caractère non reconnu"
**Cause** : Apostrophe typographique dans le code source
**Solution** : Le prétraitement automatique convertit maintenant les apostrophes typographiques → apostrophes ASCII

### Problème : Erreur de compilation Java
**Cause** : Java pas dans le PATH ou version incorrecte
**Solution** :
```powershell
# Vérifier Java
& "C:\Program Files\Java\jdk-21\bin\java.exe" -version

# Ajouter Java au PATH (session courante)
$env:Path += ";C:\Program Files\Java\jdk-21\bin"
```

### Problème : JAR non trouvé
**Cause** : Le projet n'a pas été compilé
**Solution** :
```powershell
.\gradlew.bat assemble
```

### Problème : Voir les tokens produits (diagnostic)
**Solution** :
```powershell
java -Ddebug.tokens=1 -jar build/libs/algo-compiler-1.0.0.jar votre_fichier.algo
```

---

## 8. Prochaines améliorations possibles

- [ ] Support de `:=` comme alias d'affectation (en plus de `=`)
- [ ] Tests unitaires automatisés (JUnit)
- [ ] Support de `caractere` comme index de tableau
- [ ] Fonctions intégrées pour caractères (`estMajuscule()`, `estChiffre()`, `estLettre()`, etc.)
- [x] ~~Conversion explicite `ord()` et `chr()`~~ ✅ **Implémenté !**

---

## 9. Commandes utiles (PowerShell)

```powershell
# Compiler
.\gradlew.bat assemble

# Exécuter avec Java 21
& "C:\Program Files\Java\jdk-21\bin\java.exe" -jar build/libs/algo-compiler-1.0.0.jar examples/test_caractere.algo

# Mode debug tokens
& "C:\Program Files\Java\jdk-21\bin\java.exe" -Ddebug.tokens=1 -jar build/libs/algo-compiler-1.0.0.jar examples/test_caractere.algo

# Vérifier les apostrophes dans un fichier
$text = Get-Content examples/test_caractere.algo -Raw
$text.Contains("'"), $text.Contains("'")

# Remplacer les apostrophes typographiques par ASCII
(Get-Content fichier.algo -Raw) -replace "'","'" -replace "'","'" | Set-Content fichier.algo
```

---

## 10. Contact / Support

Pour toute question ou problème :
1. Vérifier cette documentation
2. Lancer en mode debug tokens pour diagnostiquer
3. Vérifier que Java 21 est installé et accessible

---

**Fin du document**

