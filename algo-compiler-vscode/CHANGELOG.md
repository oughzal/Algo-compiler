# Changelog

Toutes les modifications notables de ce projet seront documentées dans ce fichier.

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
