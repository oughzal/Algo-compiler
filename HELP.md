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

Opérateurs

- Puissance : `^` et `**` (alias). L'alias `^` et `**` sont supportés et `^` est recommandé pour la lisibilité.
- Logiques : `et`, `ou`, `non`
- Comparaison : `=`, `<>`, `!=`, `<`, `>`, `<=`, `>=`

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


