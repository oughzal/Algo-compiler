# Notes de Version - AlgoC

## Version 1.1.0 (Décembre 2024)

### 🎉 Nouvelles Fonctionnalités

#### ✨ Clause `sinonSi` pour l'instruction `Si`
- Ajout de la clause `sinonSi` permettant de tester plusieurs conditions de manière séquentielle
- Syntaxe claire et lisible, évitant l'imbrication excessive de `si...sinon`
- Support de multiples clauses `sinonSi` dans une même instruction
- Évaluation en court-circuit : dès qu'une condition est vraie, les suivantes ne sont pas évaluées

**Exemple :**
```algo
si note >= 90 alors
    ecrire("Grade A")
sinonSi note >= 80 alors
    ecrire("Grade B")
sinonSi note >= 70 alors
    ecrire("Grade C")
sinon
    ecrire("Échec")
finsi
```

**Documentation complète** : [INSTRUCTION_SINONSI.md](INSTRUCTION_SINONSI.md)

### ⚠️ Changements Importants

#### Suppression de l'alias 'a' dans les boucles `pour`
- **Raison** : L'alias 'a' créait des conflits avec les variables nommées 'a' (usage très courant)
- **Impact** : Seul le caractère accentué **'à'** est maintenant accepté dans les boucles `pour`

**Avant :**
```algo
pour i de 1 a 10 faire  // Accepté (mais ambigü avec les variables)
    a = i * 2            // Confusion potentielle
finpour
```

**Maintenant :**
```algo
pour i de 1 à 10 faire  // ✅ SEULE syntaxe acceptée
    a = i * 2            // ✅ Pas de confusion, 'a' est clairement une variable
finpour
```

### 🔧 Améliorations Techniques

- Refactoring de l'AST avec ajout de la classe `ElseIfClause`
- Amélioration du parser pour gérer les clauses multiples
- Optimisation de l'interpréteur pour évaluer les conditions en court-circuit
- Ajout du token `SINONSI` dans le lexer

### 📚 Documentation

- Nouveau document : [INSTRUCTION_SINONSI.md](INSTRUCTION_SINONSI.md)
- Mise à jour du [README.md](README.md) avec exemples de `sinonSi`
- Nouveau document : [COMMENT_CA_MARCHE.md](COMMENT_CA_MARCHE.md) expliquant l'architecture du compilateur

### 📝 Exemples Ajoutés

- `examples/test_sinonsi_simple.algo` - Test basique de la clause sinonSi
- `examples/test_sinonsi.algo` - Test complet avec notes et comparaisons
- `examples/calculatrice_avancee.algo` - Calculatrice utilisant sinonSi pour les opérations
- `examples/gestion_etudiants.algo` - Système de gestion avec classification

### 🐛 Corrections de Bugs

- Correction de l'ambiguïté dans le parser entre 'a' (préposition) et 'a' (variable)
- Amélioration de la gestion des erreurs dans le parser pour les instructions `si`

---

## Version 1.0.0 (Novembre 2024)

### 🎉 Version Initiale

#### Fonctionnalités Principales

- **Type caractère** : Support complet avec `ord()` et `chr()`
- **Entrées/Sorties flexibles** : `ecrire()` et `ecrireln()`
- **Instruction `selon`** : Switch/case à la française
- **Opérateurs avancés** : `div`, `mod`, `**` (puissance)
- **Opérateurs logiques** : `et`, `ou`, `non`
- **Fonctions intégrées** :
  - Mathématiques : `abs()`, `racine()`, `puissance()`, `sin()`, `cos()`, etc.
  - Chaînes : `longueur()`, `majuscule()`, `minuscule()`, `sousChaine()`, etc.
  - Caractères : `ord()`, `chr()`, `estLettre()`, `estChiffre()`, etc.
  - Conversion : `versEntier()`, `versReel()`, `versChaine()`
  - Aléatoire : `aleatoire()`, `aleatoire(max)`, `aleatoire(min, max)`

#### Structures de Contrôle

- Instruction `si...alors...sinon...finsi`
- Boucle `pour...de...à...faire...finpour`
- Boucle `tantque...faire...fintantque`
- Boucle `répéter...jusqu'à`
- Instruction `selon...cas...défaut...finselon`

#### Types de Données

- `entier` : Nombres entiers
- `reel` : Nombres à virgule flottante
- `chaine` : Chaînes de caractères
- `caractere` : Caractère unique
- `booleen` : Vrai ou Faux
- `tableau[n] de type` : Tableaux

#### Fonctions et Procédures

- Définition de fonctions avec retour de valeur
- Définition de procédures sans retour
- Support des paramètres
- Variables locales dans les fonctions

#### Commentaires

- Commentaires ligne simple : `//` et `#`
- Commentaires multi-lignes : `/* ... */`

#### Constantes

- Déclaration de constantes avec protection contre modification
- Initialisation obligatoire des constantes

#### Extension VS Code

- Coloration syntaxique
- Snippets de code
- Autocomplétion
- Compilation à la volée
- Raccourcis clavier

#### Build et Déploiement

- Build avec Gradle 8.5
- Support Java 21
- Tests unitaires avec JUnit 5
- Scripts d'installation automatique (Windows)
- Publication sur GitHub Pages

---

## Roadmap Future

### Version 1.2.0 (Prévue)

- [ ] Support des structures de données avancées (listes, dictionnaires)
- [ ] Gestion des exceptions personnalisées
- [ ] Modules et imports
- [ ] Débogueur intégré

### Version 1.3.0 (Prévue)

- [ ] Optimisations de performance
- [ ] Compilation vers bytecode
- [ ] REPL interactif
- [ ] Génération de documentation automatique

### Version 2.0.0 (Vision)

- [ ] Support de la programmation orientée objet
- [ ] Support du parallélisme
- [ ] IDE dédié
- [ ] Compilateur natif

---

## Guide de Migration

### De la version 1.0.0 vers 1.1.0

**Changement requis** : Remplacer 'a' par 'à' dans les boucles `pour`

```algo
// ❌ Ne fonctionne plus
pour i de 1 a 10 faire
    ecrire(i)
finpour

// ✅ Nouvelle syntaxe
pour i de 1 à 10 faire
    ecrire(i)
finpour
```

**Recommandation** : Profitez de la clause `sinonSi` pour simplifier vos conditions imbriquées

```algo
// ❌ Ancien style (toujours valide mais moins lisible)
si x > 20 alors
    ecrire("Grand")
sinon
    si x > 10 alors
        ecrire("Moyen")
    sinon
        ecrire("Petit")
    finsi
finsi

// ✅ Nouveau style (recommandé)
si x > 20 alors
    ecrire("Grand")
sinonSi x > 10 alors
    ecrire("Moyen")
sinon
    ecrire("Petit")
finsi
```

---

**Projet** : AlgoC - Compilateur de Pseudo-code Français  
**Langage** : Kotlin  
**Auteur** : Omar Oughzal  
**Licence** : MIT  
**Repository** : https://github.com/oughzal/Algo-compiler

