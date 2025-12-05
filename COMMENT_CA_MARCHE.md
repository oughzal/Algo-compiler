# Comment fonctionne le compilateur d'algorithmes (AlgoC)

## Vue d'ensemble

Le **Compilateur d'Algorithmes** (AlgoC) est un interpréteur de pseudo-code français écrit en Kotlin. Il permet d'exécuter des algorithmes écrits dans une syntaxe française naturelle, facilitant ainsi l'apprentissage de la programmation.

## Architecture du compilateur

Le compilateur suit une architecture classique en 4 phases :

```
Code source (.algo)
       ↓
   [LEXER] → Tokens
       ↓
   [PARSER] → AST (Abstract Syntax Tree)
       ↓
[INTERPRETER] → Exécution
       ↓
   Résultat
```

### 1. Phase d'analyse lexicale (Lexer)

**Fichier**: `Lexer.kt`

Le Lexer (analyseur lexical) transforme le code source en une suite de **tokens** (jetons).

#### Rôle du Lexer :
- Lit le code source caractère par caractère
- Identifie les mots-clés, identificateurs, nombres, chaînes, opérateurs
- Ignore les espaces blancs et commentaires
- Produit une liste de tokens

#### Exemple de tokenisation :

**Code source** :
```algo
variables
    x : entier
debut
    x = 10
    ecrire(x)
fin
```

**Tokens générés** :
```
VARIABLES
NEWLINE
IDENTIFICATEUR(x)
DEUX_POINTS
ENTIER
NEWLINE
DEBUT
NEWLINE
IDENTIFICATEUR(x)
AFFECTATION
NOMBRE(10)
NEWLINE
ECRIRE
PAREN_OUVRANTE
IDENTIFICATEUR(x)
PAREN_FERMANTE
NEWLINE
FIN
```

#### Fonctionnalités du Lexer :

1. **Normalisation** : Gestion des accents (insensible à la casse et aux accents)
   ```kotlin
   "écrire" → "ecrire"
   "répéter" → "repeter"
   ```

2. **Commentaires** : Support de 3 types
   - Ligne simple : `// commentaire`
   - Ligne simple : `# commentaire`
   - Multi-lignes : `/* commentaire */`

3. **Chaînes de caractères** : Avec échappements
   ```
   "Ligne 1\nLigne 2" → reconnaissance de \n, \t, \\, \"
   ```

4. **Caractères** : Délimités par guillemets simples
   ```
   'A' → CARACTERE_LITERAL('A')
   '\n' → CARACTERE_LITERAL('\n')
   ```

5. **Nombres** : Entiers et réels
   ```
   42 → NOMBRE(42)
   3.14 → NOMBRE(3.14)
   ```

6. **Opérateurs** :
   - Arithmétiques : `+`, `-`, `*`, `/`, `div`, `mod`, `**`
   - Comparaison : `=`, `<>`, `!=`, `<`, `>`, `<=`, `>=`
   - Logiques : `et`, `ou`, `non`

### 2. Phase d'analyse syntaxique (Parser)

**Fichier**: `Parser.kt`

Le Parser (analyseur syntaxique) transforme la liste de tokens en un **AST** (Arbre de Syntaxe Abstraite).

#### Rôle du Parser :
- Vérifie la structure grammaticale du code
- Construit un arbre représentant la structure du programme
- Détecte les erreurs de syntaxe

#### Structure de l'AST :

**Fichier**: `AST.kt`

L'AST est composé de nœuds représentant les différentes structures du langage :

```kotlin
sealed class ASTNode

// Nœud racine
data class Program(
    val name: String,
    val variables: List<VariableDeclaration>,
    val constants: List<ConstantDeclaration>,
    val functions: List<FunctionDeclaration>,
    val statements: List<Statement>
)

// Déclarations
data class VariableDeclaration(
    val name: String,
    val type: String,
    val arraySize: Int? = null
)

// Instructions
sealed class Statement : ASTNode()
data class Assignment(val variable: String, val expression: Expression)
data class IfStatement(val condition: Expression, 
                       val thenBranch: List<Statement>,
                       val elseBranch: List<Statement>?)
data class ForLoop(val variable: String,
                   val start: Expression,
                   val end: Expression,
                   val body: List<Statement>)

// Expressions
sealed class Expression : ASTNode()
data class NumberLiteral(val value: Double)
data class BinaryOp(val left: Expression, 
                    val operator: String, 
                    val right: Expression)
```

#### Exemple de parsing :

**Code** :
```algo
si x > 10 alors
    ecrire("Grand")
sinon
    ecrire("Petit")
finsi
```

**AST généré** :
```
IfStatement(
    condition = BinaryOp(
        left = Variable("x"),
        operator = ">",
        right = NumberLiteral(10.0)
    ),
    thenBranch = [
        WriteStatement([StringLiteral("Grand")])
    ],
    elseBranch = [
        WriteStatement([StringLiteral("Petit")])
    ]
)
```

#### Règles de grammaire supportées :

1. **Structure de base** :
   ```
   algorithme NomAlgorithme
   [variables ...]
   [constantes ...]
   [fonctions/procédures ...]
   debut
       instructions
   fin
   ```

2. **Déclarations de variables** :
   ```
   variables
       x, y : entier
       nom : chaine
       tab : tableau[10] de entier
   ```

3. **Instructions conditionnelles** :
   ```
   si condition alors
       instructions
   sinon
       instructions
   finsi
   ```

4. **Boucles** :
   ```
   pour i de 1 à 10 faire
       instructions
   finpour
   
   tantque condition faire
       instructions
   fintantque
   
   répéter
       instructions
   jusqu'à condition
   ```

5. **Instruction selon (switch)** :
   ```
   selon expression
       cas valeur1 :
           instructions
       cas valeur2 :
           instructions
       défaut :
           instructions
   finselon
   ```

6. **Fonctions et procédures** :
   ```
   fonction nom(param1: type1, param2: type2) : typeRetour
   variables
       locales : type
   debut
       instructions
       retourner valeur
   fin
   
   procédure nom(param1: type1)
   debut
       instructions
   fin
   ```

### 3. Phase d'interprétation (Interpreter)

**Fichier**: `Interpreter.kt`

L'interpréteur parcourt l'AST et exécute les instructions.

#### Rôle de l'interpréteur :
- Gère les variables et leur portée
- Exécute les instructions dans l'ordre
- Évalue les expressions
- Gère les fonctions intégrées et utilisateur

#### Composants de l'interpréteur :

1. **Contexte d'exécution** :
   ```kotlin
   private val variables = mutableMapOf<String, Any>()
   private val constants = mutableSetOf<String>()
   private val functions = mutableMapOf<String, FunctionDeclaration>()
   ```

2. **Initialisation des variables** :
   - `entier` → 0
   - `reel` → 0.0
   - `chaine` → ""
   - `caractere` → '\u0000'
   - `booleen` → false
   - `tableau[n]` → MutableList(n) { valeurParDéfaut }

3. **Exécution des instructions** :
   - `Assignment` : Affectation de valeur
   - `IfStatement` : Évaluation condition + branche
   - `ForLoop` : Boucle avec compteur
   - `WhileLoop` : Boucle conditionnelle
   - `WriteStatement` : Affichage
   - `ReadStatement` : Lecture entrée utilisateur
   - `FunctionCall` : Appel de fonction

4. **Évaluation des expressions** :
   ```kotlin
   private fun evaluateExpression(expr: Expression): Any {
       return when (expr) {
           is NumberLiteral -> expr.value
           is StringLiteral -> expr.value
           is CharLiteral -> expr.value
           is BooleanLiteral -> expr.value
           is Variable -> variables[normalize(expr.name)]!!
           is BinaryOp -> evaluateBinaryOp(expr)
           is UnaryOp -> evaluateUnaryOp(expr)
           is FunctionCallExpression -> executeFunctionCall(...)
           is ArrayAccess -> evaluateArrayAccess(expr)
       }
   }
   ```

5. **Opérateurs binaires** :
   - Arithmétiques : `+`, `-`, `*`, `/`, `div`, `mod`, `**`
   - Comparaison : `=`, `<>`, `<`, `>`, `<=`, `>=`
   - Logiques : `et`, `ou`

6. **Fonctions intégrées** :

   **Mathématiques** :
   - `abs(x)`, `racine(x)`, `puissance(x, y)`
   - `arrondi(x)`, `plancher(x)`, `plafond(x)`
   - `min(a, b)`, `max(a, b)`
   - `sin(x)`, `cos(x)`, `tan(x)`, `log(x)`, `exp(x)`

   **Chaînes** :
   - `longueur(s)`, `majuscule(s)`, `minuscule(s)`
   - `sousChaine(s, debut, fin)`
   - `contient(s, sous)`, `commence(s, pref)`, `termine(s, suff)`
   - `position(s, sous)`

   **Caractères** :
   - `ord(c)` : Code ASCII du caractère
   - `chr(code)` : Caractère correspondant au code
   - `estLettre(c)`, `estChiffre(c)`
   - `estMajuscule(c)`, `estMinuscule(c)`
   - `versMajuscule(c)`, `versMinuscule(c)`

   **Conversion** :
   - `versEntier(x)`, `versReel(x)`, `versChaine(x)`

   **Aléatoire** :
   - `aleatoire()` : 0.0 à 1.0
   - `aleatoire(max)` : 0 à max-1
   - `aleatoire(min, max)` : min à max-1

7. **Gestion des fonctions utilisateur** :
   - Sauvegarde du contexte des variables
   - Initialisation des paramètres
   - Exécution du corps de la fonction
   - Gestion du `retourner` avec exception
   - Restauration du contexte

### 4. Point d'entrée (Main)

**Fichier**: `Main.kt`

#### Flux d'exécution :

```kotlin
fun main(args: Array<String>) {
    // 1. Vérification des arguments
    val fileName = args[0]
    
    // 2. Lecture du fichier
    var code = file.readText()
    
    // 3. Normalisation des guillemets typographiques
    code = code.replace('\u2018', '\'')  // ' → '
    
    // 4. Tokenisation
    val lexer = Lexer(code)
    val tokens = lexer.tokenize()
    
    // 5. Parsing
    val parser = Parser(tokens)
    val ast = parser.parse()
    
    // 6. Interprétation
    val interpreter = Interpreter()
    interpreter.execute(ast)
}
```

## Fonctionnalités avancées

### 1. Gestion des constantes

Les constantes sont protégées contre la modification :

```algo
constantes
    PI : reel = 3.14159

debut
    ecrire(PI)     // OK
    PI = 3.14      // ERREUR : Impossible de modifier la constante 'PI'
fin
```

### 2. Support du type caractère

```algo
variables
    c : caractere

debut
    c = 'A'              // Affectation
    ecrire(ord(c))       // 65
    c = chr(66)          // 'B'
    c = '\n'             // Échappement
fin
```

### 3. Tableaux

```algo
variables
    tab : tableau[5] de entier

debut
    tab[0] = 10
    tab[1] = 20
    
    pour i de 0 à 4 faire
        ecrire(tab[i])
    finpour
fin
```

### 4. Entrée/Sortie flexible

```algo
// Écriture sans saut de ligne
ecrire("Entrez votre nom: ")
lire(nom)

// Écriture avec saut de ligne
ecrireln("Bonjour")

// Échappements
ecrire("Col1\tCol2\tCol3\n")
```

### 5. Instruction selon (switch)

```algo
selon jour
    cas 1 :
        ecrire("Lundi")
    cas 2 :
        ecrire("Mardi")
    défaut :
        ecrire("Autre jour")
finselon
```

### 6. Opérateurs div et mod

```algo
variables
    a, b, q, r : entier

debut
    a = 17
    b = 5
    q = a div b    // q = 3 (quotient)
    r = a mod b    // r = 2 (reste)
fin
```

### 7. Opérateurs logiques

```algo
si x > 0 et x < 100 alors
    ecrire("Entre 0 et 100")
finsi

si a = 0 ou b = 0 alors
    ecrire("Au moins un est zéro")
finsi

si non estVide(s) alors
    ecrire("Chaîne non vide")
finsi
```

### 8. Opérateur de puissance

```algo
x = 2 ** 8    // x = 256
y = puissance(2, 8)  // Équivalent
```

## Gestion des erreurs

Le compilateur détecte plusieurs types d'erreurs :

1. **Erreurs lexicales** :
   - Caractère non terminé
   - Chaîne non fermée

2. **Erreurs syntaxiques** :
   - Token inattendu
   - Structure incorrecte

3. **Erreurs d'exécution** :
   - Variable non déclarée
   - Modification de constante
   - Index de tableau hors limites
   - Division par zéro
   - Fonction non définie
   - Type incompatible

## Compilation et exécution

### Construction du projet :

```bash
# Windows
.\gradlew.bat build

# Linux/Mac
./gradlew build
```

Le JAR est généré dans : `build/libs/algo-compiler-1.0.0.jar`

### Exécution :

```bash
# Méthode 1 : Directe
java -jar build/libs/algo-compiler-1.0.0.jar fichier.algo

# Méthode 2 : Après installation
algoc fichier.algo

# Mode debug (afficher les tokens)
java -Ddebug.tokens=true -jar build/libs/algo-compiler-1.0.0.jar fichier.algo
```

## Configuration Gradle

**Fichier**: `build.gradle.kts`

```kotlin
plugins {
    kotlin("jvm") version "1.9.0"
    application
}

application {
    mainClass.set("com.algocompiler.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.algocompiler.MainKt"
    }
    from(configurations.runtimeClasspath.get().map { 
        if (it.isDirectory) it else zipTree(it) 
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
```

## Tests unitaires

**Fichiers** : `src/test/kotlin/com/algocompiler/*Test.kt`

Les tests couvrent :
- **LexerTest** : Tokenisation
- **ParserTest** : Analyse syntaxique
- **InterpreterTest** : Exécution

Exécution des tests :
```bash
.\gradlew test
```

## Extension VS Code

**Dossier** : `algo-compiler-vscode/`

L'extension fournit :
- Coloration syntaxique
- Snippets de code
- Autocomplétion
- Compilation à la volée
- Raccourcis clavier

Installation :
```bash
code --install-extension algo-compiler-vscode/algo-compiler-1.3.3.vsix
```

Configuration dans VS Code :
- `algoCompiler.compilerPath` : Chemin vers le JAR
- `algoCompiler.javaPath` : Chemin vers Java

## Résumé technique

| Composant | Technologie | Rôle |
|-----------|-------------|------|
| **Lexer** | Kotlin | Analyse lexicale → Tokens |
| **Parser** | Kotlin | Analyse syntaxique → AST |
| **AST** | Kotlin (data classes) | Représentation abstraite |
| **Interpreter** | Kotlin | Exécution de l'AST |
| **Main** | Kotlin | Point d'entrée |
| **Build** | Gradle + Kotlin DSL | Construction du projet |
| **Extension** | TypeScript + JSON | Support VS Code |

## Flux complet d'exécution

```
1. Fichier source (exemple.algo)
   ↓
2. Lecture et normalisation du code
   ↓
3. Lexer.tokenize()
   → Liste de tokens
   ↓
4. Parser.parse()
   → AST (Program)
   ↓
5. Interpreter.execute(ast)
   → Initialisation variables/constantes
   → Enregistrement des fonctions
   → Exécution des instructions
   ↓
6. Résultat (sortie standard)
```

## Évolutions futures possibles

- Compilation vers bytecode ou code natif
- Support de structures de données complexes (listes, dictionnaires)
- Gestion des modules et imports
- Débogueur intégré
- Optimisations de performance
- Support de la programmation orientée objet
- Génération de documentation automatique
- REPL interactif

---

**Documentation créée pour le projet AlgoC**  
Version : 1.0.0  
Langage : Kotlin  
Build : Gradle 8.5  
Java : JDK 21

