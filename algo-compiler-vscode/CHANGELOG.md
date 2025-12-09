# Changelog

Toutes les modifications notables de ce projet seront documentées dans ce fichier.

## [1.7.4] - 2025-12-09

### 🎯 Améliorations de Configuration

#### Installation Simplifiée
- **Chemin par défaut configuré** : `c:\algo-compiler-1.6.0.jar`
- **Aucune configuration manuelle nécessaire** après installation
- L'extension détecte automatiquement le compilateur à l'emplacement standard
- Installation réduite à 3 étapes simples

#### Détection Automatique Améliorée
- **Support multi-versions** : Détection automatique des versions 1.6.0, 1.7.0, 1.8.0
- **Ordre de recherche intelligent** :
  1. Chemin configuré par l'utilisateur
  2. Chemin par défaut `c:\algo-compiler-1.6.0.jar`
  3. Versions futures
  4. Emplacements du workspace
- **Compatibilité ascendante** garantie

#### Interface Améliorée
- **Nouveau logo professionnel** dans l'extension
- **README optimisé** avec lien direct vers le JAR
- **Instructions d'installation** plus claires et concises
- Branding cohérent avec le projet

### 📚 Documentation
- Guide d'installation visuel complet
- Configuration détaillée pour tous les scénarios
- Exemples d'utilisation enrichis

## [1.6.0] - 2024-12-05

### ✨ Nouvelles Fonctionnalités Majeures

#### 📊 Matrices (Tableaux 2D)
- **Support complet des tableaux à deux dimensions** : `M : tableau[3][3] de entier`
- Syntaxe intuitive : `M[i][j] = valeur`
- Initialisation automatique avec valeurs par défaut
- Vérification automatique des limites (lignes et colonnes)
- **3 nouveaux snippets** :
  - `matrice` : Déclarer une matrice
  - `parcmat` : Parcourir une matrice avec boucles imbriquées
  - `affmat` : Afficher une matrice formatée

#### ⚡ Opérateur Puissance ^
- **Nouvel alias pour l'opérateur puissance** : `2 ^ 3` équivalent à `2 ** 3`
- Syntaxe plus concise et familière
- Les deux opérateurs (`^` et `**`) fonctionnent de manière identique
- **1 nouveau snippet** : `puis` pour l'opérateur puissance

### 🔧 Améliorations

#### Snippets Optimisés
- **Code plus propre** : Suppression des commentaires dans le code généré
- **Fonctions simplifiées** : Les snippets de fonctions génèrent `nom()` sans paramètres
- Navigation plus intuitive avec `$0` pour le curseur final
- Meilleure ergonomie générale

### 📚 Documentation
- Mise à jour du README avec exemples de matrices
- Guide complet des matrices disponible
- Exemples d'utilisation de l'opérateur ^

## [1.4.0] - 2025-12-05

### ✨ Nouvelles Fonctionnalités Majeures

#### 🔄 Clause `sinonSi`
- **Structure conditionnelle améliorée** : Testez plusieurs conditions sans imbrication excessive
- Syntaxe : `si condition1 alors ... sinonSi condition2 alors ... sinonSi condition3 alors ... sinon ... finsi`
- Coloration syntaxique ajoutée pour `sinonSi` et `sinonsi`
- **4 nouveaux snippets** :
  - `sinonsi` : Structure complète si-sinonSi-sinon
  - `sinsi` : Clause sinonSi seule
  - Support dans tous les contextes (fonctions, boucles, etc.)

#### 🎯 Expression Conditionnelle (Opérateur Ternaire)
- **Syntaxe concise** : `variable = si condition alors valeur1 sinon valeur2`
- Utilisable partout où une expression est attendue
- Support de l'imbrication pour des conditions multiples
- **4 nouveaux snippets** :
  - `siexpr` : Expression conditionnelle simple
  - `siexpri` : Expression conditionnelle imbriquée
  - `maxsi` : Maximum avec expression conditionnelle
  - `abssi` : Valeur absolue avec expression conditionnelle

#### 📝 Initialisation de Variables
- **Déclaration avec initialisation** : `var a : entier = 5`
- Plus besoin d'initialiser séparément
- Fonctionne avec tous les types : entier, réel, chaîne, caractère, booléen
- Support des expressions et expressions conditionnelles
- **2 nouveaux snippets** :
  - `varinit` : Variable avec initialisation
  - `varsinit` : Bloc de variables avec initialisation

#### 📦 Tableaux Littéraux
- **Syntaxe concise** : `T = [1, 2, 3, 4, 5]`
- Initialisation rapide sans boucles
- Support des expressions dans les éléments : `[5 * 2, 10 + 5, 8 ** 2]`
- Fonctionne avec tous les types de tableaux
- **4 nouveaux snippets** :
  - `tablit` : Tableau littéral d'entiers
  - `tablitstr` : Tableau littéral de chaînes
  - `tablitreal` : Tableau littéral de réels
  - `tablitexpr` : Tableau avec expressions

### ⚠️ Changements Importants
- **Suppression de l'alias 'a'** : Seul `à` est accepté dans les boucles `pour` (pour éviter confusion avec variables)
  - ❌ `pour i de 1 a 10 faire` n'est plus accepté
  - ✅ `pour i de 1 à 10 faire` requis

### 📚 Documentation
- Guide complet : INSTRUCTION_SINONSI.md
- Guide complet : EXPRESSION_CONDITIONNELLE.md
- Guide complet : INITIALISATION_VARIABLES_TABLEAUX.md
- Référence rapide : REFERENCE_RAPIDE.md

### 📊 Statistiques
- **14 nouveaux snippets** ajoutés
- Coloration syntaxique enrichie
- Documentation : 2000+ lignes créées
- 13 exemples de code ajoutés

## [1.3.2] - 2025-12-05

### 🐛 Corrections Critiques
- **Parser corrigé** : Résolution du bug qui causait des boucles infinies lors du parsing
  - Correction de la gestion du token EOF (End Of File)
  - Meilleure gestion de la position du curseur dans le flux de tokens
  - Les tests ne bloquent plus indéfiniment
  
- **Tests unitaires** : Tous les 77 tests passent maintenant avec succès (100%)
  - Correction de la normalisation des retours à la ligne Windows (`\r\n` → `\n`)
  - Correction du test `testOperators` (retrait de `:=` obsolète)
  - Correction du test `testVariableDeclaration` (ajout du type caractère)
  - Correction de la fonction `versChaine()` pour formater correctement les entiers (42 au lieu de 42.0)

### ⚡ Optimisations
- Configuration mémoire optimisée pour Gradle (`512m` au lieu de `1024m`)
- Réduction de l'empreinte mémoire du daemon Kotlin

## [1.3.0] - 2025-12-03

### ✨ Ajouté
- 🔀 **Instruction `selon` (switch/when)**
  - Syntaxe style Kotlin : `selon(expr) cas val: action finselon`
  - Break automatique (pas de fall-through)
  - Support entiers, chaînes, caractères
  - Cas par défaut optionnel : `defaut:`
  - 5 nouveaux snippets : `selon`, `selons`, `selonmenu`, `cas`, `defaut`

- 🔤 **Support complet du type `caractere`**
  - Nouveau type de données pour les caractères
  - Littéraux avec échappements: `'a'`, `'\n'`, `'\t'`, `'\''`, `'\\'`
  - Tableaux de caractères supportés
  - Support des apostrophes typographiques (' ' ')

- 🎯 **40 Fonctions intégrées** (vs 21 auparavant)
  - **Mathématiques (15)**: `min`, `max`, `reste`, `quotient` (nouveaux) + existantes
  - **Chaînes (9)**: `estVide`, `contient`, `commence`, `termine`, `position` (nouveaux)
  - **Caractères (8)**: `ord`, `chr`, `estLettre`, `estChiffre`, `estMajuscule`, `estMinuscule`, `versMajuscule`, `versMinuscule` (nouveaux)
  - **Conversions (3)**: `versEntier`, `versReel`, `versChaine` (nouveaux)
  - **Aléatoires (3)**: `aleatoire()` et variantes

- 📝 **Fonctions ecrire/ecrireln clarifiées**
  - `ecrire()` : n'ajoute PAS de saut de ligne
  - `ecrireln()` : ajoute un saut de ligne automatique
  - Support des échappements `\n` et `\t` dans les chaînes

### 🎨 Amélioré
- Coloration syntaxique mise à jour pour toutes les nouvelles fonctions
- 15+ nouveaux snippets pour les fonctions intégrées
- Type `caractere` ajouté à la coloration syntaxique
- Messages d'erreur améliorés avec codes Unicode

### 📚 Documentation
- Guide complet des 40 fonctions intégrées
- Documentation du type `caractere` avec exemples
- Guide des fonctions `ord()` et `chr()`
- Exemples d'utilisation pratiques

## [1.2.0] - 2025-12-02

### 🐛 Corrigé
- **Exécution interactive dans un terminal**
  - L'exécution depuis le bouton play utilise maintenant un vrai terminal
  - Support complet de l'instruction `lire` avec saisie utilisateur
  - Meilleure gestion de l'affichage et des interactions

### ✨ Ajouté
- **Support de `ecrireln`**
  - Nouvelle instruction avec syntaxe multi-arguments
  - `Ecrireln(x, "+", y, "=", resultat)` 
  - Coloration syntaxique mise à jour

## [1.1.0] - 2025-12-02

### ✨ Ajouté
- 🔤 **Support case insensitive complet**
  - Les mots-clés peuvent être écrits en MAJUSCULES, minuscules ou MiXtE
  - Les noms de variables et fonctions sont insensibles à la casse
  
- 📋 **Constantes**
  - Nouveau mot-clé `constantes` ou abréviation `const`
  - Protection contre la modification des constantes
  - Initialisation obligatoire avec une valeur
  
- 🔢 **Nouveaux opérateurs**
  - `div` - Division entière
  - `%` - Modulo style Python (en plus de `mod`)
  
- 💬 **Support amélioré des commentaires**
  - `//` - Commentaires ligne (style C/Java)
  - `#` - Commentaires ligne (style Python)
  - `/* */` - Commentaires multi-lignes (style C)
  
- ⚡ **Abréviations**
  - `var` comme raccourci pour `variables`
  - `const` comme raccourci pour `constantes`

### 🎨 Amélioré
- Coloration syntaxique mise à jour pour tous les nouveaux mots-clés
- Nouveaux snippets pour constantes, div, mod et commentaires
- Documentation enrichie avec exemples

## [1.0.0] - 2025-12-02

### ✨ Ajouté
- 🎨 Coloration syntaxique complète pour le langage Algo
  - Mots-clés de contrôle (algorithme, début, fin, si, pour, etc.)
  - Types de données (entier, reel, chaine, booleen, tableau)
  - Fonctions intégrées (abs, racine, puissance, longueur, etc.)
  - Opérateurs logiques et arithmétiques
  - Commentaires ligne et bloc

- ▶️ Exécution directe des fichiers .algo
  - Bouton d'exécution dans la barre d'outils
  - Raccourci clavier `Ctrl+Shift+R` (Cmd+Shift+R sur Mac)
  - Menu contextuel (clic droit)
  - Sortie dans un terminal dédié "Algo Compiler"

- 📝 Snippets intelligents
  - Structure complète d'algorithme
  - Fonctions et procédures
  - Structures de contrôle (si, pour, tantque, repeter)
  - Déclarations de variables et tableaux
  - Instructions d'entrée/sortie

- ⚙️ Configuration personnalisable
  - Chemin du compilateur JAR
  - Chemin de l'exécutable Java
  - Options d'affichage (temps d'exécution, effacement console)
  - Auto-détection du compilateur

- 🔍 Support complet du langage
  - Tableaux avec indexation
  - Fonctions avec paramètres et valeur de retour
  - Procédures
  - Variables locales et globales
  - 17 fonctions intégrées (math, string, random)

### 🛠️ Technique
- Extension TypeScript compilée
- Intégration avec le compilateur Algo Java 21
- Auto-détection du JAR du compilateur
- Gestion des erreurs et messages informatifs
- Support multi-plateforme (Windows, Mac, Linux)

### 📚 Documentation
- README complet avec exemples
- Guide d'installation pas-à-pas
- Exemples de code
- Documentation des fonctionnalités

---

**Note**: Cette extension nécessite le compilateur Algo (Java 21) et fonctionne avec les fichiers `.algo` et `.alg`.
