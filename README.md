# Compilateur de Pseudo-code Français (AlgoC)

Un compilateur/interpréteur de pseudo-code français écrit en Kotlin, conçu pour faciliter l'apprentissage de la programmation avec une syntaxe française naturelle.

## Nouveautés importantes

- Support complet du type `caractere` (littéraux entre quotes simples, échappements `\n`, `\t`, `\'`, `\\`).
- I/O : `ecrire()` n'ajoute pas de saut de ligne ; `ecrireln()` ajoute un saut de ligne. `lire()` ne montre pas de prompt automatique (utilisez `ecrire()` avant `lire()` pour afficher une invite).
- Fonctions intégrées de caractères : `ord(c)` et `chr(code)` (prise en charge des code points Unicode valides pour Kotlin `Char`).
- Instruction `selon` (switch) supportée avec syntaxe `cas valeur :` (style Kotlin-like `v : action` pris en charge dans le parser).

## Caractéristiques

- **Syntaxe française naturelle** : Utilise des mots-clés français intuitifs
- **Variables et constantes typées** : Support des types entier, réel, chaîne, caractère et booléen
- **Tableaux** : Support complet des tableaux avec indexation
- **Fonctions et procédures** : Définition et appel de fonctions avec paramètres et valeurs de retour
- **Fonctions intégrées** : Bibliothèque riche de fonctions mathématiques, de chaînes et aléatoires
- **Structures de contrôle** : Si/Alors/Sinon, Pour, TantQue, Répéter/Jusqu'à
- **Opérations** : Arithmétiques (y compris div et mod), logiques et de comparaison
- **Entrée/Sortie** : Instructions Lire et Écrire avec support de plusieurs formats (voir détails)
- **Commentaires** : Support de commentaires simples (//, #) et multi-lignes (/* */)
- **Flexibilité syntaxique** : Accents optionnels et insensibilité à la casse

## Installation

### 🚀 Installation Automatique (Recommandée)

**Prérequis** : Java 21 ou supérieur

#### Méthode 1 : Installateur Batch (Simple)
1. Compiler le projet : `gradlew build`
2. Clic droit sur `install-algo-compiler.bat`
3. Sélectionner "Exécuter en tant qu'administrateur"
4. Suivre les instructions

#### Méthode 2 : Installateur PowerShell (Avancé)
1. Compiler le projet : `.\gradlew build`
2. Ouvrir PowerShell en administrateur
3. Exécuter : `.\install-algo-compiler.ps1`
4. Suivre les instructions

**Après installation**, utilisez simplement :
```bash
algoc fichier.algo
```

📖 **Guide complet** : Voir [GUIDE_INSTALLATION.md](GUIDE_INSTALLATION.md)

---

### 🔧 Installation Manuelle

#### Prérequis
- Java 21 ou supérieur
- Gradle (optionnel, le wrapper est inclus)

#### Construction du projet

```bash
# Sur Windows
.\gradlew.bat build

# Sur Linux/Mac
./gradlew build
```

Le fichier JAR sera généré dans `build/libs/algo-compiler-1.0.0.jar`

#### Utilisation manuelle

```bash
java -jar build/libs/algo-compiler-1.0.0.jar fichier.algo
```

### Installation via Release + VS Code (recommandé)
1. Téléchargez la release sur GitHub : https://github.com/oughzal/Algo-compiler/releases/latest
2. Téléchargez le fichier JAR (algo-compiler-*.jar) et placez-le dans un dossier (ex: C:\Program Files\algo-compiler) ou installez via l'installateur fourni.
3. Installez l'extension VS Code depuis le Marketplace : https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler
4. Configurez le chemin du compilateur dans les paramètres de l'extension :
   - Windows: C:\Program Files\algo-compiler\algo-compiler-1.0.0.jar
   - Linux/Mac: /usr/local/bin/algo-compiler-1.0.0.jar

## Syntaxe

### Structure de base

```
algorithme NomAlgorithme

// Déclaration des variables
variables
    x, y : entier
    nom : chaine
    resultat : reel

// Déclaration des constantes (optionnel)
constantes
    PI : reel = 3.14159
    MAX : entier = 100

début
    // Votre code ici
fin
```

**Note** : Vous pouvez utiliser les abréviations `var` pour `variables` et `const` pour `constantes`.

### Types de données
- `entier` : Nombres entiers
- `reel` : Nombres à virgule flottante
- `chaine` : Chaînes de caractères
- `caractere` : Caractère unique (délimité par des quotes simples)
- `booleen` : Vrai ou Faux

### Commentaires

Le compilateur supporte trois types de commentaires :

```
// Commentaire sur une seule ligne avec //

# Commentaire sur une seule ligne avec #

/* 
   Commentaire
   sur plusieurs lignes
*/
```

### Constantes

Les constantes sont déclarées avec une valeur initiale et ne peuvent pas être modifiées :

```
constantes
    PI : reel = 3.14159
    MAX_VALEUR : entier = 100
    APP_NAME : chaine = "Mon Application"

début
    écrire(PI)          // OK - Lecture autorisée
    PI := 3.14          // ERREUR - Modification interdite
fin
```

### Affectation
```
x := 10
nom := "Alice"
```

### Caractères

Les caractères sont délimités par des guillemets simples (`'`) :

```
variables
    lettre : caractere
    tableau_lettres : tableau[5] de caractere

debut
    // Affectation simple
    lettre := 'A'
    
    // Caractères spéciaux avec échappement
    lettre := '\n'  // Nouvelle ligne
    lettre := '\t'  // Tabulation
    lettre := '\''  // Apostrophe
    lettre := '\\'  // Backslash
    
    // Caractère par code
    lettre = chr(65) // 'A'
    code = ord(lettre) // 65
    
    // Tableau de caractères
    tableau_lettres[0] := 'H'
    tableau_lettres[1] := 'e'
    tableau_lettres[2] := 'l'
    tableau_lettres[3] := 'l'
    tableau_lettres[4] := 'o'
    
    // Comparaison de caractères
    si 'A' < 'B' alors
        ecrire("A vient avant B")
    finsi
fin
```

### Tableaux

Déclaration et utilisation de tableaux :

```
variables
    nombres : tableau[10] de entier
    notes : tableau[5] de reel
    noms : tableau[3] de chaine

début
    // Accès et modification d'éléments
    nombres[0] := 100
    nombres[1] := 200
    
    // Lecture dans un tableau
    lire(notes[0])
    
    // Affichage depuis un tableau
    écrire("Premier nombre:", nombres[0])
    
    // Parcours d'un tableau avec une boucle
    pour i de 0 à 9 faire
        nombres[i] := i * 10
    finpour
fin
```

### Entrée/Sortie

#### Affichage
La fonction `ecrire` n'ajoute PAS de saut de ligne automatiquement — elle écrit exactement les valeurs passées. Pour ajouter un saut de ligne automatique utilisez `ecrireln`. Vous pouvez aussi insérer des retours à la ligne ou des tabulations à l'intérieur des chaînes avec les séquences d'échappement `\n` et `\t`.

```algocode
// Affichage sans saut de ligne
ecrire("Bonjour ")
ecrire("monde")  // Affiche: Bonjour monde

// Affichage avec saut de ligne
ecrireln("Ligne 1")
ecrireln("Ligne 2")

// Échappements
ecrire("Ligne 1\nLigne 2")  // Saut de ligne
ecrire("Col1\tCol2")         // Tabulation
```

#### Lecture
La fonction `lire` lit l'entrée utilisateur **sans afficher de prompt automatique**. Vous devez utiliser `ecrire()` avant `lire()` pour créer votre propre message d'invite :

```algocode
// ✅ CORRECT - Créer son propre prompt
ecrire("Entrez votre nom: ")
lire(nom)

ecrire("Entrez votre âge: ")
lire(age)

// Lecture dans un tableau
ecrire("Entrez la note 1: ")
lire(notes[0])

// Écriture : pas de saut de ligne par défaut
ecrire("Bonjour", nom)         // n'ajoute pas de '\n' à la fin

// Pour forcer un saut de ligne :
ecrireln("Bonjour")            // ajoute un saut de ligne

// Ou inclure '\n' dans une chaîne :
ecrire("Ligne 1\nLigne 2")

// Tabulations avec \t
ecrire("Col1\tCol2\tCol3")

// Écriture avec plusieurs valeurs (concaténation automatique)
ecrire("x = ", x, ", y = ", y)
```

> Remarque : `ecrireln()` sans argument écrit simplement une ligne vide (saut de ligne).

### Conditions
```
si x > 0 alors
    ecrire("Positif")
sinon
    ecrire("Négatif ou nul")
finsi
```

### Boucles

#### Boucle Pour
```
pour i de 1 à 10 faire
    ecrire(i)
finpour
```

#### Boucle TantQue
```
tantque x < 100 faire
    x := x + 1
fintantque
```

#### Boucle Répéter/Jusqu'à
```
répéter
    x := x + 1
jusqu'à x > 10
```

### Opérateurs

#### Arithmétiques
- `+` : Addition
- `-` : Soustraction
- `*` : Multiplication
- `/` : Division réelle (retourne un nombre réel)
- `div` : Division entière (retourne le quotient)
- `mod` : Modulo (retourne le reste de la division)

**Exemple :**
```
a := 17
b := 5

x := a / b      // x = 3.4 (division réelle)
q := a div b    // q = 3 (quotient)
r := a mod b    // r = 2 (reste)
```

#### Comparaison
- `=` : Égal
- `<>` ou `!=` : Différent
- `<` : Inférieur
- `>` : Supérieur
- `<=` : Inférieur ou égal
- `>=` : Supérieur ou égal

#### Logiques
- `et` : ET logique
- `ou` : OU logique
- `non` : NON logique

**Exemple :**
```
si x > 0 et x < 100 alors
    ecrire("x est entre 0 et 100")
finsi

si non (a = b) alors
    ecrire("a et b sont différents")
finsi
```

### Fonctions et Procédures

#### Fonctions

Les fonctions retournent une valeur et peuvent avoir des paramètres :

```
fonction somme(a : entier, b : entier) : entier
debut
    retourner a + b
fin

fonction carre(x : reel) : reel
debut
    retourner x * x
fin

// Appel de fonction
variables
    resultat : entier
debut
    resultat := somme(5, 10)
    ecrire("Résultat:", resultat)
fin
```

#### Procédures

Les procédures n'ont pas de valeur de retour :

```
procédure afficherMessage(nom : chaine)
debut
    ecrire("Bonjour", nom, "!")
fin

// Appel de procédure
debut
    afficherMessage("Alice")
fin
```

#### Variables locales dans les fonctions

Les fonctions peuvent avoir leurs propres variables locales :

```
fonction calcul() : entier
variables
    temp, resultat : entier
debut
    temp := 10
    resultat := temp * 2
    retourner resultat
fin
```

#### Tableaux comme paramètres

Les fonctions peuvent accepter des tableaux en paramètre :

```
fonction maximum(arr : tableau de entier, taille : entier) : entier
variables
    i, max : entier
debut
    max := arr[0]
    pour i de 1 à taille - 1 faire
        si arr[i] > max alors
            max := arr[i]
        finsi
    finpour
    retourner max
fin

// Utilisation
variables
    notes : tableau[5] de entier
    maxNote : entier
debut
    // ... initialiser le tableau ...
    maxNote := maximum(notes, 5)
    ecrire("Note maximale:", maxNote)
fin
```

### Fonctions intégrées

Le compilateur inclut une bibliothèque de fonctions prêtes à l'emploi :

#### Fonctions mathématiques
- `abs(x)` : Valeur absolue
- `racine(x)` : Racine carrée
- `puissance(x, y)` : x à la puissance y
- `arrondi(x)` : Arrondi au plus proche
- `plancher(x)` : Arrondi inférieur (floor)
- `plafond(x)` : Arrondi supérieur (ceil)
- `min(a, b)` : Minimum de deux valeurs
- `max(a, b)` : Maximum de deux valeurs
- `reste(a, b)` : Reste de la division (équivalent à mod)
- `quotient(a, b)` : Quotient de la division entière (équivalent à div)
- `sin(x)` : Sinus (x en radians)
- `cos(x)` : Cosinus (x en radians)
- `tan(x)` : Tangente (x en radians)
- `log(x)` : Logarithme naturel
- `exp(x)` : Exponentielle

**Exemples :**
```
x := abs(-5.7)                      // x = 5.7
y := racine(16)                     // y = 4.0
z := puissance(2, 8)                // z = 256.0
a := arrondi(3.7)                   // a = 4
b := plancher(3.7)                  // b = 3
c := plafond(3.7)                   // c = 4
petit := min(5, 10)                 // petit = 5
grand := max(5, 10)                 // grand = 10
r := reste(17, 5)                   // r = 2
q := quotient(17, 5)                // q = 3
hypotenuse := racine(puissance(3, 2) + puissance(4, 2))  // hypotenuse = 5.0
```

#### Fonctions de chaînes
- `longueur(s)` : Longueur d'une chaîne
- `majuscule(s)` : Convertir en majuscules
- `minuscule(s)` : Convertir en minuscules
- `sousChaine(s, debut, fin)` : Extraire une sous-chaîne (de l'index debut à fin)
- `estVide(s)` : Vérifie si une chaîne est vide
- `contient(s, sous_chaine)` : Vérifie si s contient sous_chaine
- `commence(s, prefixe)` : Vérifie si s commence par prefixe
- `termine(s, suffixe)` : Vérifie si s termine par suffixe
- `position(s, sous_chaine)` : Trouve la position d'une sous-chaîne (-1 si non trouvée)

**Exemples :**
```
s := "Bonjour"
longueurS := longueur(s)            // longueurS = 7
maj := majuscule(s)                 // maj = "BONJOUR"
min := minuscule(s)                 // min = "bonjour"
sous := sousChaine(s, 0, 3)         // sous = "Bon"
vide := estVide("")                 // vide = vrai
trouve := contient("Hello", "ell")  // trouve = vrai
debut := commence("Hello", "Hel")   // debut = vrai
fin := termine("Hello", "lo")       // fin = vrai
pos := position("Hello", "ll")      // pos = 2
```

#### Fonctions aléatoires
- `aleatoire()` : Nombre aléatoire entre 0.0 et 1.0
- `aleatoire(max)` : Entier aléatoire entre 0 et max-1
- `aleatoire(min, max)` : Entier aléatoire entre min et max-1

**Exemples :**
```
x := aleatoire()              // x entre 0.0 et 1.0
n := aleatoire(10)            // n entre 0 et 9
m := aleatoire(5, 15)         // m entre 5 et 14
```

#### Fonctions de caractères
- `ord(c)` : Retourne le code ASCII d'un caractère (0-127)
- `chr(code)` : Retourne le caractère correspondant à un code ASCII
- `estLettre(c)` : Vérifie si c est une lettre (a-z, A-Z)
- `estChiffre(c)` : Vérifie si c est un chiffre (0-9)
- `estMajuscule(c)` : Vérifie si c est une lettre majuscule
- `estMinuscule(c)` : Vérifie si c est une lettre minuscule
- `versMajuscule(c)` : Convertit un caractère en majuscule
- `versMinuscule(c)` : Convertit un caractère en minuscule

**Exemples :**
```
c := 'A'
code := ord(c)                      // code = 65
lettre := chr(65)                   // lettre = 'A'

// Tests de caractères
test := estLettre('A')              // test = vrai
test := estChiffre('5')             // test = vrai
test := estMajuscule('A')           // test = vrai
test := estMinuscule('a')           // test = vrai

// Conversions
c := 'a'
maj := versMajuscule(c)             // maj = 'A'
c := 'Z'
min := versMinuscule(c)             // min = 'z'

// Décalage de caractère (chiffrement César simple)
c := 'A'
decale := chr(ord(c) + 3)           // decale = 'D'

// Vérifier si c'est une majuscule (méthode alternative)
si ord(c) >= ord('A') et ord(c) <= ord('Z') alors
    ecrire("C'est une majuscule")
finsi
```

#### Fonctions de conversion
- `versEntier(x)` : Convertit une valeur en entier
- `versReel(x)` : Convertit une valeur en réel
- `versChaine(x)` : Convertit une valeur en chaîne

**Exemples :**
```
x := versEntier(3.7)                // x = 3
y := versReel(42)                   // y = 42.0
s := versChaine(123)                // s = "123"
```

## Exemples

### Exemple 1 : Calcul de factorielle
```
algorithme Factorielle

variables
    n, i, resultat : entier

début
    écrire("Entrez un nombre:")
    lire(n)
    
    resultat := 1
    pour i de 1 à n faire
        resultat := resultat * i
    finpour
    
    écrire("Factorielle de", n, "=", resultat)
fin
```

### Exemple 2 : Nombres pairs
```
algorithme NombresPairs

variables
    i : entier

début
    écrire("Les 10 premiers nombres pairs:")
    
    pour i de 1 à 10 faire
        écrire(i * 2)
    finpour
fin
```

### Exemple 3 : Devine le nombre
```
algorithme DevineNombre

variables
    secret, essai, tentatives : entier

début
    secret := 42
    tentatives := 0
    
    répéter
        écrire("Devinez le nombre:")
        lire(essai)
        tentatives := tentatives + 1
        
        si essai < secret alors
            écrire("Trop petit!")
        sinon
            si essai > secret alors
                écrire("Trop grand!")
            finsi
        finsi
    jusqu'à essai = secret
    
    écrire("Bravo! Trouvé en", tentatives, "tentatives")
fin
```

### Exemple 4 : Utilisation de tableaux
```
algorithme TestTableaux

variables
    nombres : tableau[5] de entier
    i : entier

début
    // Initialiser le tableau
    pour i de 0 à 4 faire
        nombres[i] := i * 10
    finpour
    
    // Afficher le tableau
    écrire("Contenu du tableau:")
    pour i de 0 à 4 faire
        écrire("nombres[", i, "] =", nombres[i])
    finpour
fin
```

### Exemple 5 : Fonctions avec tableaux
```
algorithme AnalyseNotes

fonction maximum(arr : tableau de entier, taille : entier) : entier
variables
    i, max : entier
début
    max := arr[0]
    pour i de 1 à taille - 1 faire
        si arr[i] > max alors
            max := arr[i]
        finsi
    finpour
    retourner max
fin

fonction moyenne(arr : tableau de entier, taille : entier) : reel
variables
    i, somme : entier
    moy : reel
début
    somme := 0
    pour i de 0 à taille - 1 faire
        somme := somme + arr[i]
    finpour
    moy := somme / taille
    retourner moy
fin

variables
    notes : tableau[5] de entier
    i, maxNote : entier
    moyNote : reel

début
    écrire("Entrez 5 notes:")
    
    pour i de 0 à 4 faire
        écrire("Note", i + 1, ":")
        lire(notes[i])
    finpour
    
    maxNote := maximum(notes, 5)
    moyNote := moyenne(notes, 5)
    
    écrire("Note maximale:", maxNote)
    écrire("Moyenne:", moyNote)
fin
```

### Exemple 6 : Fonctions intégrées
```
algorithme TestFonctions

variables
    x, y, hypotenuse : reel
    s : chaine
    nombre : entier

début
    // Fonctions mathématiques
    x := 3
    y := 4
    hypotenuse := racine(puissance(x, 2) + puissance(y, 2))
    écrire("Hypoténuse:", hypotenuse)
    
    // Fonctions de chaînes
    s := "Bonjour"
    écrire("Longueur:", longueur(s))
    écrire("Majuscule:", majuscule(s))
    écrire("3 premiers caractères:", sousChaine(s, 0, 3))
    
    // Fonction aléatoire
    nombre := aleatoire(1, 100)
    écrire("Nombre aléatoire entre 1 et 99:", nombre)
fin
```

### Exemple 7 : Utilisation des constantes
```
algorithme CalculCercle

constantes
    PI : reel = 3.14159

variables
    rayon : reel
    aire, circonference : reel

début
    écrire("Entrez le rayon du cercle:")
    lire(rayon)
    
    aire := PI * puissance(rayon, 2)
    circonference := 2 * PI * rayon
    
    écrire("Aire:", aire)
    écrire("Circonférence:", circonference)
fin
```

## Notes importantes

### Flexibilité syntaxique
- **Accents optionnels** : Vous pouvez écrire `debut` au lieu de `début`, `ecrire` au lieu de `écrire`
- **Casse insensible** : Les mots-clés peuvent être écrits en majuscules, minuscules ou mixte (`DEBUT`, `debut`, `Debut`)
- **Abréviations** : `var` pour `variables`, `const` pour `constantes`
- **Affectation flexible** : Vous pouvez utiliser `:=` ou `=` pour l'affectation

### Types de commentaires
- `//` : Commentaire sur une seule ligne
- `#` : Commentaire sur une seule ligne (style Python)
- `/* ... */` : Commentaire multi-lignes

### Protection des constantes
Les constantes déclarées dans la section `constantes` ne peuvent pas être modifiées. Toute tentative de modification provoquera une erreur à l'exécution.

### Portée des variables
- Les variables globales sont déclarées au niveau de l'algorithme
- Les fonctions et procédures peuvent avoir leurs propres variables locales
- Les paramètres de fonctions sont locaux à la fonction

### Tableaux
- Les tableaux sont indexés à partir de 0
- La taille du tableau doit être spécifiée lors de la déclaration
- Les tableaux peuvent être passés en paramètre aux fonctions

### Fonctions intégrées
Le compilateur inclut **40 fonctions intégrées** :
- **Mathématiques** (15) : abs, racine, puissance, arrondi, plancher, plafond, min, max, reste, quotient, sin, cos, tan, log, exp
- **Chaînes** (9) : longueur, majuscule, minuscule, sousChaine, estVide, contient, commence, termine, position
- **Caractères** (8) : ord, chr, estLettre, estChiffre, estMajuscule, estMinuscule, versMajuscule, versMinuscule
- **Conversions** (3) : versEntier, versReel, versChaine
- **Aléatoires** (3 variantes) : aleatoire(), aleatoire(max), aleatoire(min, max)

## Développement

### Structure du projet
```
src/main/kotlin/com/algocompiler/
├── Main.kt           # Point d'entrée
├── Token.kt          # Définition des tokens
├── Lexer.kt          # Analyse lexicale
├── AST.kt            # Arbre syntaxique abstrait
├── Parser.kt         # Analyse syntaxique
└── Interpreter.kt    # Interpréteur
```

### Évolutions techniques

**Version 1.0.0** :
- ✅ Support complet des tableaux avec indexation
- ✅ Fonctions et procédures avec paramètres
- ✅ Gestion de la portée des variables (locale/globale)
- ✅ Passage de tableaux en paramètre
- ✅ Bibliothèque de fonctions intégrées (mathématiques, chaînes, aléatoires)
- ✅ Support des constantes avec protection contre la modification
- ✅ Commentaires multi-formats (//, #, /* */)
- ✅ Opérateurs div et mod pour les opérations entières
- ✅ Flexibilité syntaxique (accents optionnels, casse insensible)
- ✅ Variables locales dans les fonctions
- ✅ Expressions complexes et imbriquées

### Exemples disponibles

Le dossier `examples/` contient de nombreux exemples pour vous aider à démarrer :

**Basiques** :
- `hello.algo` - Programme Hello World
- `factorielle.algo` - Calcul de factorielle
- `nombres_pairs.algo` - Affichage des nombres pairs

**Intermédiaires** :
- `devine_nombre.algo` - Jeu de devinette simple
- `jeu_devine_nombre_avance.algo` - Version avancée avec fonctions intégrées
- `calculatrice.algo` - Calculatrice avec fonctions intégrées

**Tests de fonctionnalités** :
- `test_tableaux.algo` - Démonstration des tableaux
- `test_fonctions.algo` - Fonctions et procédures
- `test_fonctions_integrees.algo` - Toutes les fonctions intégrées
- `test_complet.algo` - Exemple complet avec tableaux et fonctions
- `test_commentaires_constantes.algo` - Commentaires et constantes
- `test_constante_protection.algo` - Protection des constantes
- `test_div_mod.algo` - Opérateurs div et mod
- `test_casse.algo` - Insensibilité à la casse
- `test_caractere.algo` - Type caractère (complet)
- `test_ord_chr.algo` - Fonctions ord() et chr()
- `test_ecrire.algo` - Fonctions ecrire et ecrireln

**Séries d'exercices** :
- `série01/` - Exercices de base
- `série03/` - Exercices avancés

## Licence

Ce projet est sous licence MIT.
