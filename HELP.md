# HELP — Guide complet du compilateur Algo-Compiler

Ce fichier rassemble l'aide utilisateur pour le compilateur `Algo-Compiler` : installation, usage des principales instructions, fonctions intégrées et exemples rapides.

Sommaire
- Installation rapide
- Exécution du compilateur
- Extension VS Code (installation locale)
- Types et littéraux
- Entrée / Sortie (ecrire, ecrireln, lire)
- Caractères (type `caractere`, `ord`, `chr`, échappements)
- Tableaux & Matrices
- Contrôles (si / sinonSi / selon)
- Opérateurs (arithmétique, logique, puissance ^ et ^ alias **)
- Exemples rapides

---

Installation rapide

1. Télécharger le JAR depuis la release :
   https://github.com/oughzal/Algo-compiler/releases/download/release/algo-compiler-1.6.0.jar
2. Assurez-vous d'avoir Java 21+ :
   ```powershell
   java -version
   ```
3. Exécuter un fichier :
   ```powershell
   java -jar path\to\algo-compiler-1.6.0.jar monprogramme.algo
   ```

Extension VS Code (local)

- Le `.vsix` est généré dans `algo-compiler-vscode/algo-compiler-1.6.0.vsix`.
- Pour installer localement (PowerShell) :

```powershell
& "C:\Program Files\Microsoft VS Code\bin\code" --install-extension "c:\Users\Omar\Documents\Algo-compiler\algo-compiler-vscode\algo-compiler-1.6.0.vsix"
```

- Ouvrez un fichier `.algo`, configurez le chemin du JAR dans les paramètres de l'extension et utilisez Ctrl+Shift+R pour exécuter.

Types et littéraux

- entier, reel, chaine, caractere, booleen
- Chaînes : délimitées par guillemets doubles ""
- Caractères : délimités par quotes simples '' (ex: 'a', '\n', '\t', '\'')

Entrée / Sortie

- `ecrire(...)` écrit les arguments sans ajouter de saut de ligne automatique.
- `ecrireln(...)` écrit les arguments et ajoute un saut de ligne.
- Pour afficher une invite avant la lecture : utiliser `ecrire("Votre saisie : ")` puis `lire(var)`.

Exemples :
```
ecrire("Nom : ")
lire(nom)
ecrire("Bonjour ", nom)   // pas de \n
// pour finir par une nouvelle ligne
ecrireln("Fin")
```

Caractères

- Assignation : `lettre := 'A'`
- Échappements : `\n`, `\t`, `\\`, `\'`
- Fonctions intégrées :
  - `ord(c)` : renvoie le code entier du caractère `c` (unicode code point compatible kotlin `Char`)
  - `chr(code)` : renvoie le caractère correspondant à `code`

Exemples :
```
lettre := 'a'
code := ord(lettre)   // 97
c2 := chr(65)         // 'A'
```

Tableaux & Matrices

- Déclaration : `nombres : tableau[5] de entier`
- Littéral : `nombres = [1, 2, 3, 4, 5]`
- Matrice 2D : `M : tableau[3][3] de entier`
- Accès : `M[i][j]`

Contrôles

- Si / sinon / sinonSi :
```
si cond1 alors
  ...
sinonSi cond2 alors
  ...
sinon
  ...
finsi
```

- Expression conditionnelle (ternary) :
```
max = si a > b alors a sinon b
```

- Instruction `selon` (switch-like) :
```
selon x
cas 1 : ecrire("un")
cas 2 : ecrire("deux")
sinon : ecrire("autre")
finselon
```

Boucles

**Boucle Pour** :

Syntaxe de base :
```
pour variable de debut à fin faire
  ...
finpour
```

**Avec pas (step)** :
```
pour variable de debut à fin pas increment faire
  ...
finpour
```

**Comportement** :
- Si `pas` n'est pas spécifié :
  - Le pas par défaut est **1** si `debut ≤ fin` (boucle ascendante)
  - Le pas par défaut est **-1** si `debut > fin` (boucle descendante)
- Le pas peut être positif ou négatif
- Le pas ne peut pas être 0 (erreur d'exécution)

**Exemples** :
```algo
// Boucle ascendante avec pas par défaut (1)
pour i de 0 à 10 faire
  ecrire(i, " ")  // 0 1 2 3 4 5 6 7 8 9 10
finpour

// Boucle descendante avec pas par défaut (-1)
pour i de 10 à 0 faire
  ecrire(i, " ")  // 10 9 8 7 6 5 4 3 2 1 0
finpour

// Boucle avec pas positif explicite
pour i de 0 à 20 pas 3 faire
  ecrire(i, " ")  // 0 3 6 9 12 15 18
finpour

// Boucle avec pas négatif explicite
pour i de 20 à 0 pas -2 faire
  ecrire(i, " ")  // 20 18 16 14 12 10 8 6 4 2 0
finpour

// Boucle avec pas -5
pour i de 100 à 50 pas -5 faire
  ecrire(i, " ")  // 100 95 90 85 80 75 70 65 60 55 50
finpour
```

**Boucle TantQue** :
```
tantQue condition faire
  ...
finTantQue
```

**Boucle Répéter...Jusqu'à** :
```
repeter
  ...
jusqua condition
```

Exemples :
```algo
// TantQue
i = 0
tantQue i < 5 faire
  ecrireln(i)
  i = i + 1
finTantQue

// Répéter...Jusqu'à
i = 0
repeter
  ecrireln(i)
  i = i + 1
jusqua i >= 5
```

Opérateurs

**Arithmétiques** :
- Addition : `+`
- Soustraction : `-`
- Multiplication : `*`
- Division réelle : `/` (renvoie un réel)
- Division entière : `div` (renvoie un entier, ex: `17 div 5` → `3`)
- Modulo (reste) : `mod` ou `%` (ex: `17 mod 5` → `2`)
- Puissance : `^` et `**` (alias). L'alias `^` et `**` sont supportés et `^` est recommandé pour la lisibilité.

**Logiques** :
- `et` : ET logique
- `ou` : OU logique
- `non` : NON logique

**Comparaison** : 
- `==`, `<>`, `!=`, `<`, `>`, `<=`, `>=`

**Exemples d'opérateurs arithmétiques** :
```algo
a = 17
b = 5

// Division réelle
resultat = a / b    // 3.4

// Division entière
quotient = a div b  // 3

// Modulo (reste)
reste = a mod b     // 2

// Vérification: 17 = 5 × 3 + 2
ecrireln(a, " = ", b, " × ", quotient, " + ", reste)

// Puissance
p = 2 ** 8          // 256
p = 2 ^ 8           // 256 (alias)
```

Typage Fort et Comparaisons

Le compilateur implémente un système de typage fort pour les comparaisons :

**Règles de comparaison** :

1. **Nombres** (entier, reel) :
   - Comparaison numérique : `10 < 20` → VRAI
   - Nombres négatifs : `-5 < 10` → VRAI
   - Entier vs réel : `5 == 5.0` → VRAI (conversion automatique)

2. **Chaînes** :
   - Ordre lexicographique (alphabétique) : `"abc" < "def"` → VRAI
   - Longueur différente : `"abc" < "abcd"` → VRAI
   - Chaîne numérique vs nombre : `"10" == 10` → VRAI (conversion intelligente)

3. **Caractères** :
   - Comparaison par code ASCII : `'a' < 'z'` → VRAI
   - Caractère vs chaîne (1 car) : `'a' == "a"` → VRAI

4. **Booléens** :
   - `faux < vrai` → VRAI
   - Comparaison stricte : `vrai == vrai` → VRAI

**Exemples** :
```algo
// Comparaison de nombres
a = 10
b = 20
si a < b alors
    ecrireln("10 est inférieur à 20")  // ✅ Correct
finsi

// Comparaison de chaînes (ordre alphabétique)
s1 = "Alice"
s2 = "Bob"
si s1 < s2 alors
    ecrireln("Alice vient avant Bob")  // ✅ Correct
finsi

// Comparaison de caractères
c1 = 'a'
c2 = 'z'
si c1 < c2 alors
    ecrireln("a vient avant z")  // ✅ Correct
finsi

// Comparaison intelligente chaîne/nombre
si "10" == 10 alors
    ecrireln("Égaux")  // ✅ Conversion automatique
finsi
```

Exemples rapides

1) Caractère et ecrire/ecrireln :
```
variables
  c : caractere
debut
  c := 'a'
  ecrire("Lettre: ", c)
  ecrireln()
fin
```

2) ord/chr :
```
variables
  c : caractere
  code : entier
debut
  c := 'A'
  code := ord(c)
  ecrireln("Code de A = ", code)
  ecrireln("Char(65) = ", chr(65))
fin
```

3) Puissance et tableaux :
```
variables
  T : tableau[4] de entier
debut
  T = [1,2,3,4]
  ecrire("2^3 = ", 2 ^ 3)
  ecrireln()
fin
```

---

Si vous voulez, je peux aussi :
- ajouter un fichier `HELP.html` pour GitHub Pages ;
- générer/mettre à jour des tests unitaires ou scénarios d'exemple ;
- continuer la correction du parser (type caractère, affectation avec '=' vs ':=' et == pour égalité) si vous voulez que je modifie le code Kotlin.


