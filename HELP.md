# 📚 AIDE COMPLÈTE - Algo-Compiler

**Version** : 1.5.0  
**Date** : 5 Décembre 2024  
**Langage** : Pseudo-code algorithmique en français

---

## 📋 Table des Matières

1. [Installation](#installation)
2. [Démarrage Rapide](#démarrage-rapide)
3. [Structure d'un Algorithme](#structure-dun-algorithme)
4. [Types de Données](#types-de-données)
5. [Variables et Constantes](#variables-et-constantes)
6. [Opérateurs](#opérateurs)
7. [Instructions de Contrôle](#instructions-de-contrôle)
8. [Tableaux](#tableaux)
9. [Fonctions et Procédures](#fonctions-et-procédures)
10. [Entrées/Sorties](#entréessorties)
11. [Fonctions Intégrées](#fonctions-intégrées)
12. [Exemples Complets](#exemples-complets)
13. [Dépannage](#dépannage)

---

## 📦 Installation

### Prérequis

- **Java 21 ou supérieur** installé sur votre système
- **VS Code** (recommandé) ou tout éditeur de texte

### Méthode 1 : Installation Rapide (Recommandée)

#### Étape 1 : Installer Java

**Windows** :
```powershell
# Vérifier si Java est installé
java -version

# Si Java n'est pas installé :
# Télécharger depuis https://www.oracle.com/java/technologies/downloads/
# ou https://adoptium.net/
```

**Linux** :
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-21-jdk

# Fedora
sudo dnf install java-21-openjdk
```

**macOS** :
```bash
# Avec Homebrew
brew install openjdk@21
```

#### Étape 2 : Télécharger le Compilateur

1. Allez sur la page des releases : [GitHub Releases](https://github.com/oughzal/Algo-compiler/releases)
2. Téléchargez `algo-compiler-1.0.0.jar`
3. Placez-le dans un dossier (ex: `C:\Program Files\algo-compiler\` sur Windows)

#### Étape 3 : Installer l'Extension VS Code

**Option A - Depuis le Marketplace** (Recommandé) :
1. Ouvrez VS Code
2. Allez dans Extensions (`Ctrl+Shift+X`)
3. Recherchez "Algo Compiler"
4. Cliquez sur "Install"

**Option B - Depuis un fichier VSIX** :
1. Téléchargez `algo-compiler-1.4.0.vsix`
2. Dans VS Code : `Ctrl+Shift+P` → "Install from VSIX..."
3. Sélectionnez le fichier VSIX

#### Étape 4 : Configurer l'Extension

1. Ouvrez les paramètres de VS Code (`Ctrl+,`)
2. Recherchez "Algo Compiler"
3. Configurez les chemins :
   - **Compiler Path** : Chemin vers le JAR téléchargé
     - Windows : `C:\Program Files\algo-compiler\algo-compiler-1.0.0.jar`
     - Linux/Mac : `/usr/local/bin/algo-compiler-1.0.0.jar`
   - **Java Path** : Chemin vers le dossier bin de Java
     - Windows : `C:\Program Files\Java\jdk-21\bin`
     - Linux/Mac : `/usr/lib/jvm/java-21-openjdk/bin`

### Méthode 2 : Compilation depuis les Sources

```bash
# Cloner le dépôt
git clone https://github.com/oughzal/Algo-compiler.git
cd Algo-compiler

# Compiler avec Gradle
./gradlew build

# Le JAR sera dans build/libs/
```

### Vérification de l'Installation

Créez un fichier `test.algo` :
```algo
algorithme Test
debut
    ecrireln("Installation réussie !")
fin
```

Exécutez :
```bash
java -jar algo-compiler-1.0.0.jar test.algo
```

Ou dans VS Code : `Ctrl+Shift+R`

---

## 🚀 Démarrage Rapide

### Premier Programme

Créez un fichier `hello.algo` :

```algo
algorithme HelloWorld

debut
    ecrireln("Bonjour, monde !")
fin
```

### Avec Variables

```algo
algorithme Salutations

variables
    nom : chaine

debut
    ecrire("Entrez votre nom : ")
    lire(nom)
    ecrireln("Bonjour, ", nom, " !")
fin
```

### Exécution

**Dans VS Code** :
- Appuyez sur `Ctrl+Shift+R`
- Ou clic droit → "Algo: Exécuter"

**En ligne de commande** :
```bash
java -jar algo-compiler-1.0.0.jar hello.algo
```

---

## 📐 Structure d'un Algorithme

### Structure Minimale

```algo
algorithme NomAlgorithme

debut
    // Votre code ici
fin
```

### Structure Complète

```algo
algorithme NomComplet

// Déclaration des variables (optionnel)
variables
    x, y : entier
    nom : chaine
    valide : booleen

// Déclaration des constantes (optionnel)
constantes
    PI : reel = 3.14159
    MAX : entier = 100

// Déclaration des fonctions (optionnel)
fonction double(n : entier) : entier
debut
    retourner n * 2
fin

// Corps principal
debut
    // Instructions
fin
```

### Commentaires

```algo
// Commentaire sur une ligne avec //

# Commentaire sur une ligne avec #

/* 
   Commentaire
   sur plusieurs
   lignes
*/

algorithme Exemple
debut
    // Ceci est un commentaire
    ecrire("Code")  # Commentaire en fin de ligne
fin
```

---

## 🔢 Types de Données

### Types Primitifs

| Type | Description | Exemples |
|------|-------------|----------|
| `entier` | Nombre entier | `42`, `-10`, `0` |
| `reel` | Nombre à virgule | `3.14`, `-0.5`, `2.0` |
| `chaine` | Texte entre guillemets | `"Bonjour"`, `"123"` |
| `caractere` | Un seul caractère | `'A'`, `'x'`, `'5'` |
| `booleen` | Vrai ou Faux | `vrai`, `faux` |

### Exemples

```algo
algorithme TypesDonnees

variables
    age : entier
    taille : reel
    nom : chaine
    initial : caractere
    actif : booleen

debut
    age = 25
    taille = 1.75
    nom = "Alice"
    initial = 'A'
    actif = vrai
    
    ecrireln("Age : ", age)
    ecrireln("Taille : ", taille)
    ecrireln("Nom : ", nom)
    ecrireln("Initiale : ", initial)
    ecrireln("Actif : ", actif)
fin
```

### Caractères Spéciaux

```algo
algorithme CaracteresSpeciaux

variables
    c : caractere

debut
    c = '\n'  // Nouvelle ligne
    c = '\t'  // Tabulation
    c = '\''  // Apostrophe
    c = '\\'  // Backslash
    
    ecrire("Ligne 1\nLigne 2")  // Saut de ligne
    ecrire("Col1\tCol2")        // Tabulation
fin
```

---

## 📦 Variables et Constantes

### Déclaration de Variables

#### Syntaxe Simple

```algo
variables
    x : entier
    y : reel
    nom : chaine
```

#### Déclaration Multiple

```algo
variables
    a, b, c : entier
    x, y : reel
```

#### Avec Initialisation ✨ NOUVEAU

```algo
variables
    compteur : entier = 0
    pi : reel = 3.14159
    message : chaine = "Bonjour"
    actif : booleen = vrai
```

#### Exemple Complet

```algo
algorithme Variables

variables
    // Sans initialisation
    x, y : entier
    
    // Avec initialisation
    compteur : entier = 0
    somme : reel = 0.0
    nom : chaine = "Non défini"

debut
    x = 10
    y = 20
    compteur = compteur + 1
    
    ecrireln("x = ", x)
    ecrireln("y = ", y)
    ecrireln("Compteur = ", compteur)
fin
```

### Constantes

```algo
constantes
    PI : reel = 3.14159
    MAX_SIZE : entier = 100
    APP_NAME : chaine = "MonApp"
    DEBUG : booleen = vrai

debut
    ecrire(PI)           // OK - Lecture
    // PI = 3.14        // ERREUR - Modification interdite
fin
```

### Alias

Vous pouvez utiliser des abréviations :
- `var` au lieu de `variables`
- `const` au lieu de `constantes`

```algo
var
    x : entier = 10

const
    MAX : entier = 100
```

---

## ⚙️ Opérateurs

### Opérateurs Arithmétiques

| Opérateur | Description | Exemple | Résultat |
|-----------|-------------|---------|----------|
| `+` | Addition | `5 + 3` | `8` |
| `-` | Soustraction | `5 - 3` | `2` |
| `*` | Multiplication | `5 * 3` | `15` |
| `/` | Division réelle | `7 / 2` | `3.5` |
| `div` | Division entière | `7 div 2` | `3` |
| `mod` ou `%` | Modulo (reste) | `7 mod 2` | `1` |
| `**` ou `^` | Puissance ✨ | `2 ** 3` ou `2 ^ 3` | `8` |

#### Exemples

```algo
algorithme Arithmetique

variables
    a, b, q, r : entier
    x, y : reel

debut
    a = 17
    b = 5
    
    // Division réelle
    x = a / b              // x = 3.4
    
    // Division entière et modulo
    q = a div b            // q = 3
    r = a mod b            // r = 2
    
    // Puissance (deux syntaxes)
    y = 2 ** 3             // y = 8
    y = 2 ^ 3              // y = 8 (identique)
    
    ecrireln("17 / 5 = ", x)
    ecrireln("17 div 5 = ", q)
    ecrireln("17 mod 5 = ", r)
    ecrireln("2 ^ 3 = ", y)
fin
```

### Opérateurs de Comparaison

| Opérateur | Description | Exemple |
|-----------|-------------|---------|
| `=` | Égal à | `x = 5` |
| `<>` ou `!=` | Différent de | `x <> 5` |
| `<` | Inférieur à | `x < 5` |
| `>` | Supérieur à | `x > 5` |
| `<=` | Inférieur ou égal | `x <= 5` |
| `>=` | Supérieur ou égal | `x >= 5` |

#### Exemples

```algo
algorithme Comparaisons

variables
    age : entier
    majeur : booleen

debut
    age = 17
    
    majeur = age >= 18          // faux
    
    si age < 18 alors
        ecrireln("Mineur")
    sinon
        ecrireln("Majeur")
    finsi
fin
```

### Opérateurs Logiques

| Opérateur | Description | Exemple |
|-----------|-------------|---------|
| `et` | ET logique | `x > 0 et x < 10` |
| `ou` | OU logique | `x < 0 ou x > 10` |
| `non` | NON logique | `non valide` |

#### Exemples

```algo
algorithme Logiques

variables
    age : entier
    permis : booleen

debut
    age = 20
    permis = vrai
    
    si age >= 18 et permis alors
        ecrireln("Peut conduire")
    finsi
    
    si age < 18 ou non permis alors
        ecrireln("Ne peut pas conduire")
    finsi
fin
```

### Priorité des Opérateurs

De la plus haute à la plus basse :

1. `**` ou `^` (puissance)
2. `*`, `/`, `div`, `mod`
3. `+`, `-`
4. `=`, `<>`, `<`, `>`, `<=`, `>=`
5. `non`
6. `et`
7. `ou`

```algo
// Exemples de priorité
resultat = 3 + 2 * 5      // = 13 (pas 25)
resultat = 2 ^ 3 + 1      // = 9 (pas 16)
resultat = (3 + 2) * 5    // = 25 (parenthèses forcent)
```

---

## 🔀 Instructions de Contrôle

### Instruction SI

#### Syntaxe Simple

```algo
si condition alors
    // Instructions
finsi
```

#### Avec SINON

```algo
si condition alors
    // Instructions si vrai
sinon
    // Instructions si faux
finsi
```

#### Avec SINONSI ✨ NOUVEAU

```algo
si condition1 alors
    // Instructions si condition1 vraie
sinonSi condition2 alors
    // Instructions si condition2 vraie
sinonSi condition3 alors
    // Instructions si condition3 vraie
sinon
    // Instructions si toutes fausses
finsi
```

#### Exemples

```algo
algorithme TestSi

variables
    note : reel

debut
    ecrire("Entrez la note : ")
    lire(note)
    
    // Avec sinonSi
    si note >= 16 alors
        ecrireln("Très Bien")
    sinonSi note >= 14 alors
        ecrireln("Bien")
    sinonSi note >= 12 alors
        ecrireln("Assez Bien")
    sinonSi note >= 10 alors
        ecrireln("Passable")
    sinon
        ecrireln("Insuffisant")
    finsi
fin
```

### Expression Conditionnelle ✨ NOUVEAU

Syntaxe : `variable = si condition alors valeur1 sinon valeur2`

```algo
algorithme ExpressionConditionnelle

variables
    age : entier
    statut : chaine
    max, a, b : entier

debut
    age = 17
    
    // Expression conditionnelle
    statut = si age >= 18 alors "Majeur" sinon "Mineur"
    ecrireln(statut)  // Affiche : Mineur
    
    // Trouver le maximum
    a = 10
    b = 20
    max = si a > b alors a sinon b
    ecrireln("Max : ", max)  // Affiche : 20
    
    // Imbriqué
    note = 15
    mention = si note >= 16 alors "TB" 
              sinon si note >= 14 alors "B" 
              sinon "AB"
fin
```

### Boucle POUR

```algo
pour variable de debut à fin faire
    // Instructions
finpour
```

#### Exemples

```algo
algorithme BouclePour

variables
    i, somme : entier

debut
    // Compter de 1 à 10
    pour i de 1 à 10 faire
        ecrire(i, " ")
    finpour
    ecrireln()
    
    // Calculer une somme
    somme = 0
    pour i de 1 à 100 faire
        somme = somme + i
    finpour
    ecrireln("Somme : ", somme)  // 5050
    
    // Tableaux
    pour i de 0 à 4 faire
        tab[i] = i * 10
    finpour
fin
```

### Boucle TANTQUE

```algo
tantque condition faire
    // Instructions
fintantque
```

#### Exemples

```algo
algorithme BoucleTantque

variables
    compteur, somme : entier

debut
    // Compter jusqu'à 5
    compteur = 1
    tantque compteur <= 5 faire
        ecrireln(compteur)
        compteur = compteur + 1
    fintantque
    
    // Saisie avec validation
    somme = 0
    tantque somme < 100 faire
        ecrire("Entrez un nombre : ")
        lire(nombre)
        somme = somme + nombre
    fintantque
fin
```

### Boucle REPETER...JUSQUA

```algo
repeter
    // Instructions (exécutées au moins une fois)
jusqua condition
```

#### Exemples

```algo
algorithme BoucleRepeter

variables
    choix : entier

debut
    // Menu avec répétition
    repeter
        ecrireln("=== MENU ===")
        ecrireln("1. Option 1")
        ecrireln("2. Option 2")
        ecrireln("0. Quitter")
        ecrire("Choix : ")
        lire(choix)
        
        selon(choix)
            cas 1: ecrireln("Option 1 sélectionnée")
            cas 2: ecrireln("Option 2 sélectionnée")
        finselon
    jusqua choix = 0
    
    ecrireln("Au revoir !")
fin
```

### Instruction SELON (Switch)

```algo
selon(expression)
    cas valeur1: instructions1
    cas valeur2: instructions2
    defaut: instructions_defaut
finselon
```

#### Exemples

```algo
algorithme InstructionSelon

variables
    jour : entier
    mois : chaine

debut
    // Avec entiers
    ecrire("Jour (1-7) : ")
    lire(jour)
    
    selon(jour)
        cas 1: ecrireln("Lundi")
        cas 2: ecrireln("Mardi")
        cas 3: ecrireln("Mercredi")
        cas 4: ecrireln("Jeudi")
        cas 5: ecrireln("Vendredi")
        cas 6: ecrireln("Samedi")
        cas 7: ecrireln("Dimanche")
        defaut: ecrireln("Jour invalide")
    finselon
    
    // Avec chaînes
    mois = "janvier"
    
    selon(mois)
        cas "janvier": ecrireln("Hiver")
        cas "avril": ecrireln("Printemps")
        cas "juillet": ecrireln("Été")
        cas "octobre": ecrireln("Automne")
        defaut: ecrireln("Mois inconnu")
    finselon
fin
```

---

## 📊 Tableaux

### Déclaration

```algo
variables
    nom : tableau[taille] de type
```

#### Exemples

```algo
variables
    nombres : tableau[10] de entier
    notes : tableau[5] de reel
    noms : tableau[3] de chaine
    flags : tableau[8] de booleen
```

### Initialisation

#### Méthode Classique

```algo
algorithme InitTableau

variables
    tab : tableau[5] de entier
    i : entier

debut
    // Initialisation élément par élément
    tab[0] = 10
    tab[1] = 20
    tab[2] = 30
    tab[3] = 40
    tab[4] = 50
    
    // Ou avec une boucle
    pour i de 0 à 4 faire
        tab[i] = i * 10
    finpour
fin
```

#### Avec Littéral ✨ NOUVEAU

```algo
algorithme TableauLitteral

variables
    nombres : tableau[5] de entier
    notes : tableau[3] de reel
    noms : tableau[2] de chaine

debut
    // Initialisation avec littéral
    nombres = [10, 20, 30, 40, 50]
    notes = [15.5, 12.0, 18.0]
    noms = ["Alice", "Bob"]
    
    // Avec expressions
    nombres = [5 * 2, 10 + 5, 8 ^ 2, 100 div 3, 50]
fin
```

### Accès et Modification

```algo
algorithme AccesTableau

variables
    tab : tableau[5] de entier
    i : entier

debut
    tab = [1, 2, 3, 4, 5]
    
    // Accès en lecture
    ecrireln(tab[0])  // Affiche : 1
    ecrireln(tab[4])  // Affiche : 5
    
    // Modification
    tab[2] = 100
    ecrireln(tab[2])  // Affiche : 100
    
    // Parcours
    pour i de 0 à 4 faire
        ecrire(tab[i], " ")
    finpour
fin
```

### Exemples Avancés

#### Recherche dans un Tableau

```algo
algorithme RechercheTableau

variables
    tab : tableau[5] de entier
    valeur, i : entier
    trouve : booleen

debut
    tab = [10, 25, 30, 15, 40]
    valeur = 30
    trouve = faux
    
    pour i de 0 à 4 faire
        si tab[i] = valeur alors
            trouve = vrai
            ecrireln("Trouvé à l'index ", i)
        finsi
    finpour
    
    si non trouve alors
        ecrireln("Non trouvé")
    finsi
fin
```

#### Tri à Bulles

```algo
algorithme TriBulles

variables
    tab : tableau[5] de entier
    i, j, temp : entier

debut
    tab = [64, 34, 25, 12, 22]
    
    // Tri
    pour i de 0 à 3 faire
        pour j de 0 à 3 - i faire
            si tab[j] > tab[j + 1] alors
                // Échange
                temp = tab[j]
                tab[j] = tab[j + 1]
                tab[j + 1] = temp
            finsi
        finpour
    finpour
    
    // Affichage
    pour i de 0 à 4 faire
        ecrire(tab[i], " ")
    finpour
fin
```

---

## 🎯 Matrices (Tableaux 2D)

### Déclaration

```algo
variables
    matrice : tableau[lignes][colonnes] de type
```

#### Exemples

```algo
variables
    M : tableau[3][3] de entier          // Matrice 3x3 d'entiers
    notes : tableau[5][10] de reel       // Matrice 5x10 de réels
    grille : tableau[8][8] de caractere  // Matrice 8x8 de caractères
```

### Initialisation et Utilisation

```algo
algorithme MatriceSimple

variables
    M : tableau[3][3] de entier
    i, j : entier

debut
    // Initialisation
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            M[i][j] = (i + 1) * 10 + (j + 1)
        finpour
    finpour
    
    // Accès en lecture
    valeur = M[1][2]
    
    // Affichage
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            ecrire(M[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

**Sortie** :
```
11 12 13
21 22 23
31 32 33
```

### Addition de Matrices

```algo
algorithme AdditionMatrices

variables
    A, B, C : tableau[2][2] de entier
    i, j : entier

debut
    // Initialiser A
    A[0][0] = 1
    A[0][1] = 2
    A[1][0] = 3
    A[1][1] = 4
    
    // Initialiser B
    B[0][0] = 5
    B[0][1] = 6
    B[1][0] = 7
    B[1][1] = 8
    
    // C = A + B
    pour i de 0 à 1 faire
        pour j de 0 à 1 faire
            C[i][j] = A[i][j] + B[i][j]
        finpour
    finpour
    
    // Afficher C
    ecrireln("C = A + B :")
    pour i de 0 à 1 faire
        pour j de 0 à 1 faire
            ecrire(C[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

### Matrice Identité

```algo
algorithme MatriceIdentite

variables
    I : tableau[4][4] de entier
    i, j : entier

debut
    // Créer matrice identité
    pour i de 0 à 3 faire
        pour j de 0 à 3 faire
            si i = j alors
                I[i][j] = 1
            sinon
                I[i][j] = 0
            finsi
        finpour
    finpour
    
    // Afficher
    pour i de 0 à 3 faire
        pour j de 0 à 3 faire
            ecrire(I[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

**Sortie** :
```
1 0 0 0
0 1 0 0
0 0 1 0
0 0 0 1
```

### Points Importants

- **Indices** : Commencent à 0
- **Premier élément** : `M[0][0]`
- **Dernier élément** (matrice 3x3) : `M[2][2]`
- **Ordre** : Toujours `M[ligne][colonne]`
- **Limites** : Vérifiées automatiquement

**Documentation complète** : [SUPPORT_MATRICES.md](SUPPORT_MATRICES.md)

---

## 🔧 Fonctions et Procédures

### Fonctions (avec retour)

```algo
fonction nom(param1 : type1, param2 : type2) : typeRetour
variables
    // Variables locales
debut
    // Instructions
    retourner valeur
fin
```

#### Exemples

```algo
algorithme AvecFonctions

// Fonction puissance
fonction puissance(base : entier, exposant : entier) : entier
variables
    resultat, i : entier
debut
    resultat = 1
    pour i de 1 à exposant faire
        resultat = resultat * base
    finpour
    retourner resultat
fin

// Fonction maximum
fonction max(a : entier, b : entier) : entier
debut
    si a > b alors
        retourner a
    sinon
        retourner b
    finsi
fin

// Fonction factorielle
fonction factorielle(n : entier) : entier
debut
    si n <= 1 alors
        retourner 1
    sinon
        retourner n * factorielle(n - 1)
    finsi
fin

// Programme principal
variables
    x, y, resultat : entier

debut
    x = 2
    y = 3
    
    resultat = puissance(x, y)
    ecrireln("2^3 = ", resultat)
    
    resultat = max(10, 20)
    ecrireln("Max = ", resultat)
    
    resultat = factorielle(5)
    ecrireln("5! = ", resultat)
fin
```

### Procédures (sans retour)

```algo
procedure nom(param1 : type1, param2 : type2)
variables
    // Variables locales
debut
    // Instructions
fin
```

#### Exemples

```algo
algorithme AvecProcedures

// Procédure d'affichage
procedure afficherLigne(caractere : caractere, longueur : entier)
variables
    i : entier
debut
    pour i de 1 à longueur faire
        ecrire(caractere)
    finpour
    ecrireln()
fin

// Procédure de salutation
procedure saluer(nom : chaine)
debut
    ecrireln("Bonjour, ", nom, " !")
    ecrireln("Bienvenue dans le programme.")
fin

// Programme principal
debut
    afficherLigne('=', 40)
    saluer("Alice")
    afficherLigne('-', 40)
fin
```

---

## 📝 Entrées/Sorties

### Écriture (Sortie)

#### ecrire() - Sans saut de ligne

```algo
algorithme Ecrire

debut
    ecrire("Bonjour")
    ecrire(" ")
    ecrire("monde")
    // Affiche : Bonjour monde (sur une ligne)
fin
```

#### ecrireln() - Avec saut de ligne

```algo
algorithme Ecrireln

debut
    ecrireln("Ligne 1")
    ecrireln("Ligne 2")
    ecrireln("Ligne 3")
    // Affiche sur 3 lignes différentes
fin
```

#### Affichage Multiple

```algo
algorithme AffichageMultiple

variables
    nom : chaine
    age : entier

debut
    nom = "Alice"
    age = 25
    
    // Plusieurs valeurs avec ecrireln
    ecrireln("Nom : ", nom)
    ecrireln("Age : ", age)
    
    // Sur une même ligne
    ecrire("Nom : ")
    ecrire(nom)
    ecrire(", Age : ")
    ecrireln(age)
fin
```

#### Caractères Spéciaux

```algo
algorithme CaracteresSpeciaux

debut
    // Nouvelle ligne avec \n
    ecrire("Ligne 1\nLigne 2\nLigne 3")
    
    // Tabulation avec \t
    ecrire("Col1\tCol2\tCol3")
    
    // Combinaison
    ecrireln("Nom\tAge\tVille")
    ecrireln("Alice\t25\tParis")
    ecrireln("Bob\t30\tLyon")
fin
```

### Lecture (Entrée)

#### lire() - Lecture d'une variable

```algo
algorithme Lire

variables
    nom : chaine
    age : entier
    taille : reel

debut
    // Avec prompt explicite
    ecrire("Entrez votre nom : ")
    lire(nom)
    
    ecrire("Entrez votre âge : ")
    lire(age)
    
    ecrire("Entrez votre taille (en m) : ")
    lire(taille)
    
    ecrireln("Bonjour ", nom)
    ecrireln("Vous avez ", age, " ans")
    ecrireln("Vous mesurez ", taille, " m")
fin
```

#### Lecture dans un Tableau

```algo
algorithme LireTableau

variables
    notes : tableau[5] de reel
    i : entier

debut
    ecrireln("Entrez 5 notes :")
    
    pour i de 0 à 4 faire
        ecrire("Note ", i + 1, " : ")
        lire(notes[i])
    finpour
    
    ecrireln("Vous avez saisi :")
    pour i de 0 à 4 faire
        ecrireln(notes[i])
    finpour
fin
```

---

## 🔢 Fonctions Intégrées

Le compilateur fournit **40+ fonctions intégrées** organisées par catégorie.

### Fonctions Mathématiques (15)

| Fonction | Description | Exemple | Résultat |
|----------|-------------|---------|----------|
| `abs(x)` | Valeur absolue | `abs(-5)` | `5` |
| `min(a, b)` | Minimum | `min(3, 7)` | `3` |
| `max(a, b)` | Maximum | `max(3, 7)` | `7` |
| `racine(x)` | Racine carrée | `racine(16)` | `4.0` |
| `puissance(x, y)` | x puissance y | `puissance(2, 3)` | `8` |
| `arrondi(x)` | Arrondi | `arrondi(3.7)` | `4` |
| `plancher(x)` | Arrondi inférieur | `plancher(3.7)` | `3` |
| `plafond(x)` | Arrondi supérieur | `plafond(3.2)` | `4` |
| `reste(a, b)` | Reste division | `reste(17, 5)` | `2` |
| `quotient(a, b)` | Quotient entier | `quotient(17, 5)` | `3` |
| `sin(x)` | Sinus (radians) | `sin(0)` | `0` |
| `cos(x)` | Cosinus (radians) | `cos(0)` | `1` |
| `tan(x)` | Tangente (radians) | `tan(0)` | `0` |
| `log(x)` | Logarithme naturel | `log(2.718)` | `≈1` |
| `exp(x)` | Exponentielle | `exp(1)` | `≈2.718` |

#### Exemples

```algo
algorithme FonctionsMath

variables
    x, y : reel
    a, b : entier

debut
    // Valeur absolue
    x = abs(-42)              // 42
    
    // Min et Max
    a = min(10, 20)           // 10
    b = max(10, 20)           // 20
    
    // Racine carrée
    x = racine(25)            // 5.0
    
    // Puissance
    y = puissance(2, 10)      // 1024
    
    // Arrondis
    a = arrondi(3.7)          // 4
    a = plancher(3.7)         // 3
    a = plafond(3.2)          // 4
    
    // Division
    a = quotient(17, 5)       // 3
    b = reste(17, 5)          // 2
    
    // Trigonométrie
    x = sin(0)                // 0
    y = cos(0)                // 1
    
    ecrireln("Résultats calculés")
fin
```

### Fonctions de Chaînes (9)

| Fonction | Description | Exemple | Résultat |
|----------|-------------|---------|----------|
| `longueur(s)` | Longueur | `longueur("Hello")` | `5` |
| `majuscule(s)` | En majuscules | `majuscule("hello")` | `"HELLO"` |
| `minuscule(s)` | En minuscules | `minuscule("HELLO")` | `"hello"` |
| `sousChaine(s, i, j)` | Sous-chaîne | `sousChaine("Hello", 0, 4)` | `"Hell"` |
| `estVide(s)` | Test si vide | `estVide("")` | `vrai` |
| `contient(s, sub)` | Recherche | `contient("Hello", "ell")` | `vrai` |
| `commence(s, pre)` | Commence par | `commence("Hello", "He")` | `vrai` |
| `termine(s, suf)` | Termine par | `termine("Hello", "lo")` | `vrai` |
| `position(s, sub)` | Position | `position("Hello", "ll")` | `2` |

#### Exemples

```algo
algorithme FonctionsChaines

variables
    texte, sous : chaine
    longueur, pos : entier
    trouve : booleen

debut
    texte = "Bonjour le monde"
    
    // Longueur
    longueur = longueur(texte)
    ecrireln("Longueur : ", longueur)  // 16
    
    // Majuscules/Minuscules
    ecrireln(majuscule(texte))         // BONJOUR LE MONDE
    ecrireln(minuscule(texte))         // bonjour le monde
    
    // Sous-chaîne
    sous = sousChaine(texte, 0, 7)
    ecrireln(sous)                     // Bonjour
    
    // Recherche
    trouve = contient(texte, "monde")
    ecrireln("Contient 'monde' : ", trouve)  // vrai
    
    // Position
    pos = position(texte, "le")
    ecrireln("Position de 'le' : ", pos)     // 8
    
    // Tests
    ecrireln(commence(texte, "Bon"))   // vrai
    ecrireln(termine(texte, "nde"))    // vrai
    ecrireln(estVide(texte))           // faux
fin
```

### Fonctions de Caractères (8)

| Fonction | Description | Exemple | Résultat |
|----------|-------------|---------|----------|
| `ord(c)` | Code ASCII | `ord('A')` | `65` |
| `chr(code)` | ASCII → Car | `chr(65)` | `'A'` |
| `estLettre(c)` | Test lettre | `estLettre('A')` | `vrai` |
| `estChiffre(c)` | Test chiffre | `estChiffre('5')` | `vrai` |
| `estMajuscule(c)` | Test maj | `estMajuscule('A')` | `vrai` |
| `estMinuscule(c)` | Test min | `estMinuscule('a')` | `vrai` |
| `versMajuscule(c)` | → Majuscule | `versMajuscule('a')` | `'A'` |
| `versMinuscule(c)` | → Minuscule | `versMinuscule('A')` | `'a'` |

#### Exemples

```algo
algorithme FonctionsCaracteres

variables
    c : caractere
    code : entier
    test : booleen

debut
    c = 'A'
    
    // Conversion caractère ↔ code
    code = ord(c)
    ecrireln("Code de 'A' : ", code)  // 65
    
    c = chr(66)
    ecrireln("chr(66) = ", c)         // B
    
    // Tests
    test = estLettre('A')             // vrai
    test = estChiffre('5')            // vrai
    test = estMajuscule('A')          // vrai
    test = estMinuscule('a')          // vrai
    
    // Conversions
    c = versMajuscule('a')            // A
    c = versMinuscule('Z')            // z
    
    // Parcourir l'alphabet
    pour code de ord('A') à ord('Z') faire
        ecrire(chr(code), " ")
    finpour
fin
```

### Fonctions de Conversion (3)

| Fonction | Description | Exemple | Résultat |
|----------|-------------|---------|----------|
| `versEntier(x)` | → Entier | `versEntier(3.7)` | `3` |
| `versReel(x)` | → Réel | `versReel(5)` | `5.0` |
| `versChaine(x)` | → Chaîne | `versChaine(42)` | `"42"` |

#### Exemples

```algo
algorithme FonctionsConversion

variables
    entier_val : entier
    reel_val : reel
    chaine_val : chaine

debut
    // Vers entier
    entier_val = versEntier(3.7)      // 3
    entier_val = versEntier("42")     // 42
    
    // Vers réel
    reel_val = versReel(5)            // 5.0
    reel_val = versReel("3.14")       // 3.14
    
    // Vers chaîne
    chaine_val = versChaine(42)       // "42"
    chaine_val = versChaine(3.14)     // "3.14"
    
    // Concaténation avec conversion
    ecrireln("Résultat : " + versChaine(123))
fin
```

### Fonctions Aléatoires (3)

| Fonction | Description | Exemple | Résultat |
|----------|-------------|---------|----------|
| `aleatoire()` | Réel [0, 1[ | `aleatoire()` | `0.437...` |
| `aleatoire(max)` | Entier [0, max[ | `aleatoire(10)` | `0` à `9` |
| `aleatoire(min, max)` | Entier [min, max[ | `aleatoire(1, 7)` | `1` à `6` |

#### Exemples

```algo
algorithme FonctionsAleatoires

variables
    x : reel
    n, de : entier
    i : entier

debut
    // Nombre aléatoire entre 0 et 1
    x = aleatoire()
    ecrireln("Aléatoire [0, 1[ : ", x)
    
    // Nombre entier entre 0 et 9
    n = aleatoire(10)
    ecrireln("Aléatoire [0, 10[ : ", n)
    
    // Lancer de dé (1 à 6)
    de = aleatoire(1, 7)
    ecrireln("Dé : ", de)
    
    // Simuler 5 lancers de dé
    pour i de 1 à 5 faire
        de = aleatoire(1, 7)
        ecrire(de, " ")
    finpour
fin
```

---

## 🎯 Exemples Complets

### Exemple 1 : Calculatrice

```algo
algorithme Calculatrice

variables
    a, b : reel
    operation : chaine
    resultat : reel

debut
    ecrireln("=== CALCULATRICE ===")
    
    ecrire("Premier nombre : ")
    lire(a)
    
    ecrire("Opération (+, -, *, /) : ")
    lire(operation)
    
    ecrire("Deuxième nombre : ")
    lire(b)
    
    selon(operation)
        cas "+": resultat = a + b
        cas "-": resultat = a - b
        cas "*": resultat = a * b
        cas "/": 
            si b <> 0 alors
                resultat = a / b
            sinon
                ecrireln("Division par zéro !")
            finsi
        defaut: ecrireln("Opération invalide")
    finselon
    
    ecrireln("Résultat : ", resultat)
fin
```

### Exemple 2 : Jeu de Devinette

```algo
algorithme JeuDevinette

variables
    secret, essai, tentatives : entier

debut
    ecrireln("=== JEU DE DEVINETTE ===")
    ecrireln("Devinez le nombre entre 1 et 100")
    
    secret = aleatoire(1, 101)
    tentatives = 0
    
    repeter
        ecrire("Votre essai : ")
        lire(essai)
        tentatives = tentatives + 1
        
        si essai < secret alors
            ecrireln("C'est plus !")
        sinonSi essai > secret alors
            ecrireln("C'est moins !")
        finsi
    jusqua essai = secret
    
    ecrireln("Bravo ! Trouvé en ", tentatives, " tentatives")
fin
```

### Exemple 3 : Statistiques sur Tableau

```algo
algorithme StatistiquesTableau

variables
    valeurs : tableau[10] de entier
    i, somme, min, max : entier
    moyenne : reel

debut
    ecrireln("=== STATISTIQUES ===")
    
    // Initialisation
    valeurs = [15, 23, 8, 42, 16, 31, 9, 27, 34, 12]
    
    // Initialisation min/max
    min = valeurs[0]
    max = valeurs[0]
    somme = 0
    
    // Calculs
    pour i de 0 à 9 faire
        somme = somme + valeurs[i]
        
        si valeurs[i] < min alors
            min = valeurs[i]
        finsi
        
        si valeurs[i] > max alors
            max = valeurs[i]
        finsi
    finpour
    
    moyenne = somme / 10
    
    // Affichage
    ecrireln("Valeurs : ")
    pour i de 0 à 9 faire
        ecrire(valeurs[i], " ")
    finpour
    ecrireln()
    
    ecrireln("Minimum : ", min)
    ecrireln("Maximum : ", max)
    ecrireln("Somme : ", somme)
    ecrireln("Moyenne : ", moyenne)
fin
```

### Exemple 4 : Recherche et Tri

```algo
algorithme RechercheEtTri

variables
    tab : tableau[8] de entier
    i, j, temp, valeur : entier
    trouve : booleen

debut
    ecrireln("=== RECHERCHE ET TRI ===")
    
    // Initialisation
    tab = [64, 34, 25, 12, 22, 11, 90, 88]
    
    // Affichage initial
    ecrireln("Tableau initial :")
    pour i de 0 à 7 faire
        ecrire(tab[i], " ")
    finpour
    ecrireln()
    
    // Recherche
    valeur = 22
    trouve = faux
    pour i de 0 à 7 faire
        si tab[i] = valeur alors
            trouve = vrai
            ecrireln("Valeur ", valeur, " trouvée à l'index ", i)
        finsi
    finpour
    
    // Tri à bulles
    pour i de 0 à 6 faire
        pour j de 0 à 6 - i faire
            si tab[j] > tab[j + 1] alors
                temp = tab[j]
                tab[j] = tab[j + 1]
                tab[j + 1] = temp
            finsi
        finpour
    finpour
    
    // Affichage après tri
    ecrireln("Tableau trié :")
    pour i de 0 à 7 faire
        ecrire(tab[i], " ")
    finpour
    ecrireln()
fin
```

### Exemple 5 : Gestion de Notes

```algo
algorithme GestionNotes

variables
    notes : tableau[5] de reel
    i : entier
    somme, moyenne, min, max : reel
    mention : chaine

debut
    ecrireln("=== GESTION DE NOTES ===")
    
    // Saisie des notes
    ecrireln("Entrez 5 notes :")
    pour i de 0 à 4 faire
        ecrire("Note ", i + 1, " : ")
        lire(notes[i])
    finpour
    
    // Calculs
    somme = 0
    min = notes[0]
    max = notes[0]
    
    pour i de 0 à 4 faire
        somme = somme + notes[i]
        min = si notes[i] < min alors notes[i] sinon min
        max = si notes[i] > max alors notes[i] sinon max
    finpour
    
    moyenne = somme / 5
    
    // Déterminer la mention
    si moyenne >= 16 alors
        mention = "Très Bien"
    sinonSi moyenne >= 14 alors
        mention = "Bien"
    sinonSi moyenne >= 12 alors
        mention = "Assez Bien"
    sinonSi moyenne >= 10 alors
        mention = "Passable"
    sinon
        mention = "Insuffisant"
    finsi
    
    // Affichage des résultats
    ecrireln()
    ecrireln("=== RÉSULTATS ===")
    ecrireln("Note minimale : ", min)
    ecrireln("Note maximale : ", max)
    ecrireln("Moyenne : ", moyenne)
    ecrireln("Mention : ", mention)
fin
```

---

## 🔍 Dépannage

### Erreurs Courantes

#### 1. "Type de variable invalide"

**Cause** : Type non reconnu dans la déclaration

```algo
// ❌ Incorrect
variables
    x : integer  // Erreur : "integer" n'existe pas

// ✅ Correct
variables
    x : entier
```

#### 2. "Variable non déclarée"

**Cause** : Utilisation d'une variable sans déclaration

```algo
// ❌ Incorrect
debut
    x = 10  // Erreur : x non déclaré

// ✅ Correct
variables
    x : entier
debut
    x = 10
```

#### 3. "Expression invalide"

**Cause** : Syntaxe incorrecte dans une expression

```algo
// ❌ Incorrect
x = 5 + * 3  // Erreur : syntaxe invalide

// ✅ Correct
x = 5 + 2 * 3
```

#### 4. "Index hors limites"

**Cause** : Accès à un index inexistant du tableau

```algo
variables
    tab : tableau[5] de entier

debut
    // ❌ Incorrect
    tab[5] = 10  // Erreur : indices 0 à 4 seulement
    
    // ✅ Correct
    tab[4] = 10  // Dernier index valide
fin
```

#### 5. "Modification de constante interdite"

**Cause** : Tentative de modifier une constante

```algo
constantes
    PI : reel = 3.14159

debut
    // ❌ Incorrect
    PI = 3.14  // Erreur : constante non modifiable
fin
```

### Problèmes d'Installation

#### Le compilateur ne démarre pas

**Solution** :
1. Vérifier que Java 21+ est installé : `java -version`
2. Vérifier le chemin du JAR
3. Essayer : `java -jar chemin/vers/algo-compiler-1.0.0.jar`

#### L'extension VS Code ne fonctionne pas

**Solutions** :
1. Vérifier la configuration dans Settings
2. Redémarrer VS Code
3. Vérifier les chemins :
   - Compiler Path : chemin vers le JAR
   - Java Path : chemin vers le dossier bin de Java

#### Erreur "Java not found"

**Solution** :
```bash
# Windows
setx JAVA_HOME "C:\Program Files\Java\jdk-21"
setx PATH "%PATH%;%JAVA_HOME%\bin"

# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export PATH=$PATH:$JAVA_HOME/bin
```

### Conseils de Débogage

#### 1. Utiliser des affichages

```algo
// Afficher les valeurs pour déboguer
ecrireln("Valeur de x : ", x)
ecrireln("Condition vraie ?")
```

#### 2. Tester étape par étape

```algo
// Tester chaque partie séparément
ecrireln("Début du programme")
// ... code ...
ecrireln("Après calcul")
// ... code ...
ecrireln("Fin du programme")
```

#### 3. Vérifier les types

```algo
// S'assurer que les types correspondent
variables
    x : entier
    y : reel

debut
    x = 5        // OK
    y = 3.14     // OK
    x = y        // Peut perdre la précision
    y = x        // OK, conversion implicite
fin
```

---

## 📚 Ressources Supplémentaires

### Documentation Détaillée

- **INSTRUCTION_SINONSI.md** - Guide complet sur sinonSi
- **EXPRESSION_CONDITIONNELLE.md** - Opérateur ternaire
- **INITIALISATION_VARIABLES_TABLEAUX.md** - Init variables et tableaux
- **OPERATEUR_PUISSANCE_ALIAS.md** - Opérateur ^ et **
- **FONCTIONS_INTEGREES_COURANTES.md** - 40+ fonctions
- **REFERENCE_RAPIDE.md** - Référence rapide

### Liens Utiles

- **Site web** : https://oughzal.github.io/Algo-compiler
- **GitHub** : https://github.com/oughzal/Algo-compiler
- **Extension VS Code** : https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler
- **Issues** : https://github.com/oughzal/Algo-compiler/issues

### Communauté

- Signaler un bug : [GitHub Issues](https://github.com/oughzal/Algo-compiler/issues)
- Proposer une amélioration : [GitHub Discussions](https://github.com/oughzal/Algo-compiler/discussions)
- Contribuer : [CONTRIBUTING.md](https://github.com/oughzal/Algo-compiler/blob/main/CONTRIBUTING.md)

---

## 🎓 Conclusion

Ce guide couvre toutes les fonctionnalités du compilateur Algo-Compiler. Pour plus d'informations :

1. Consultez les exemples dans le dossier `examples/`
2. Lisez la documentation détaillée dans les fichiers MD
3. Utilisez les snippets de l'extension VS Code (tapez le préfixe + Tab)
4. Explorez les 40+ fonctions intégrées

**Bon codage en pseudo-code français !** 🚀

---

**Version du document** : 1.5.0  
**Dernière mise à jour** : 5 Décembre 2024  
**Auteur** : Algo-Compiler Team  
**License** : MIT

