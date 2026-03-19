# Contribuer à Algo Compiler Mobile

Merci de votre intérêt pour contribuer à Algo Compiler Mobile ! 🎉

## Table des Matières

1. [Code de Conduite](#code-de-conduite)
2. [Comment Contribuer](#comment-contribuer)
3. [Signaler un Bug](#signaler-un-bug)
4. [Proposer une Fonctionnalité](#proposer-une-fonctionnalité)
5. [Processus de Pull Request](#processus-de-pull-request)
6. [Style de Code](#style-de-code)
7. [Structure des Commits](#structure-des-commits)
8. [Configuration de Développement](#configuration-de-développement)

## Code de Conduite

Ce projet adhère à un code de conduite. En participant, vous acceptez de respecter ce code.

### Nos Engagements

- Être respectueux envers tous
- Accepter les critiques constructives
- Se concentrer sur ce qui est le mieux pour la communauté
- Faire preuve d'empathie

### Comportements Inacceptables

- Langage ou images à caractère sexuel
- Trolling, insultes ou attaques personnelles
- Harcèlement public ou privé
- Publication d'informations privées sans permission

## Comment Contribuer

Il existe plusieurs façons de contribuer :

### 1. Signaler des Bugs 🐛

Avant de signaler un bug, vérifiez qu'il n'a pas déjà été signalé dans les [Issues](https://github.com/oughzal/Algo-compiler/issues).

### 2. Proposer des Fonctionnalités 💡

Ouvrez une Issue avec le tag `enhancement` pour discuter de votre idée.

### 3. Améliorer la Documentation 📚

La documentation peut toujours être améliorée !

### 4. Écrire du Code 💻

Consultez les Issues avec le tag `good first issue` pour commencer.

## Signaler un Bug

### Avant de Signaler

- Vérifiez que vous utilisez la dernière version
- Consultez les Issues existantes
- Reproduisez le bug de manière consistante

### Template de Bug Report

```markdown
**Description du Bug**
Une description claire et concise du bug.

**Étapes pour Reproduire**
1. Aller à '...'
2. Cliquer sur '...'
3. Faire défiler jusqu'à '...'
4. Voir l'erreur

**Comportement Attendu**
Ce qui devrait se passer normalement.

**Comportement Actuel**
Ce qui se passe actuellement.

**Captures d'Écran**
Si applicable, ajouter des captures d'écran.

**Environnement**
- Appareil : [ex. Samsung Galaxy S21]
- Version Android : [ex. Android 13]
- Version de l'App : [ex. 1.0.0]

**Code de l'Algorithme** (si applicable)
```algo
// Votre code ici
```

**Logs**
```
Coller les logs ici
```

**Contexte Additionnel**
Tout autre information pertinente.
```

## Proposer une Fonctionnalité

### Template de Feature Request

```markdown
**Problème à Résoudre**
Une description claire du problème que cette fonctionnalité résoudrait.

**Solution Proposée**
Une description claire de ce que vous voulez qu'il se passe.

**Alternatives Considérées**
D'autres solutions ou fonctionnalités que vous avez considérées.

**Mockups/Exemples** (optionnel)
Des captures d'écran ou exemples de ce à quoi cela ressemblerait.

**Impact**
Qui bénéficierait de cette fonctionnalité ?

**Implémentation** (optionnel)
Des idées sur comment implémenter cela.
```

## Processus de Pull Request

### 1. Fork le Projet

```bash
# Fork via GitHub UI, puis :
git clone https://github.com/VOTRE-USERNAME/Algo-compiler.git
cd Algo-compiler/algo-compiler-android
```

### 2. Créer une Branche

```bash
git checkout -b feature/ma-nouvelle-fonctionnalite
# ou
git checkout -b fix/correction-bug
```

### 3. Faire vos Modifications

- Suivre le [Style de Code](#style-de-code)
- Ajouter des tests si applicable
- Mettre à jour la documentation

### 4. Tester

```bash
# Tests unitaires
./gradlew test

# Tests instrumentés
./gradlew connectedAndroidTest

# Lint
./gradlew lint
```

### 5. Commiter

```bash
git add .
git commit -m "feat: ajouter la coloration syntaxique"
```

Suivre la [Structure des Commits](#structure-des-commits)

### 6. Pousser

```bash
git push origin feature/ma-nouvelle-fonctionnalite
```

### 7. Ouvrir une Pull Request

- Aller sur GitHub
- Cliquer sur "New Pull Request"
- Remplir le template

### Template de Pull Request

```markdown
**Description**
Une description claire de ce que fait cette PR.

**Type de Changement**
- [ ] Bug fix (non-breaking change qui corrige un problème)
- [ ] Nouvelle fonctionnalité (non-breaking change qui ajoute une fonctionnalité)
- [ ] Breaking change (correction ou fonctionnalité qui changerait le comportement existant)
- [ ] Documentation

**Comment Tester**
Étapes pour tester les changements :
1. ...
2. ...

**Checklist**
- [ ] Mon code suit le style du projet
- [ ] J'ai commenté mon code, particulièrement les parties complexes
- [ ] J'ai mis à jour la documentation
- [ ] Mes changements ne génèrent pas de nouveaux warnings
- [ ] J'ai ajouté des tests qui prouvent que ma correction/fonctionnalité marche
- [ ] Tous les tests passent localement
- [ ] J'ai vérifié que mes changements ne cassent rien

**Captures d'Écran** (si applicable)

**Issues Liées**
Closes #123
```

## Style de Code

### Kotlin

Suivre les [Conventions de Code Kotlin](https://kotlinlang.org/docs/coding-conventions.html)

#### Formatage

```kotlin
// Bon
class MyClass {
    fun myFunction(param: String): Int {
        return param.length
    }
}

// Mauvais
class MyClass
{
    fun myFunction( param:String ) :Int
    {
        return param.length
    }
}
```

#### Nommage

- **Classes** : PascalCase (`MyClass`)
- **Fonctions** : camelCase (`myFunction`)
- **Variables** : camelCase (`myVariable`)
- **Constantes** : UPPER_SNAKE_CASE (`MAX_SIZE`)
- **Composables** : PascalCase (`MyScreen`)

#### Commentaires

```kotlin
// Commentaire sur une ligne

/**
 * Commentaire sur plusieurs lignes
 * expliquant une fonction complexe
 * 
 * @param param Description du paramètre
 * @return Description du retour
 */
fun myFunction(param: String): Int {
    // ...
}
```

### Jetpack Compose

```kotlin
// Bon
@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    viewModel: MyViewModel = viewModel()
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello")
    }
}

// Préfixes pour Composables
@Composable
fun MyScreenContent() { } // Écran complet

@Composable
fun MyItem() { } // Élément de liste

@Composable
fun MyDialog() { } // Dialog
```

### Organisation des Fichiers

```
MyFile.kt:
1. Package declaration
2. Imports (groupés et triés)
3. Constants
4. Data classes
5. Main class/composable
6. Helper functions
7. Preview composables
```

Exemple :
```kotlin
package com.algocompiler.mobile.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

private const val TAG = "MyScreen"
private const val MAX_ITEMS = 100

data class MyData(val name: String)

@Composable
fun MyScreen() {
    // Implementation
}

private fun helperFunction() {
    // Helper
}

@Preview
@Composable
private fun MyScreenPreview() {
    MyScreen()
}
```

## Structure des Commits

Utiliser [Conventional Commits](https://www.conventionalcommits.org/)

### Format

```
<type>(<scope>): <description>

[body optionnel]

[footer optionnel]
```

### Types

- **feat** : Nouvelle fonctionnalité
- **fix** : Correction de bug
- **docs** : Documentation uniquement
- **style** : Formatage, point-virgules, etc.
- **refactor** : Refactoring du code
- **perf** : Amélioration de performance
- **test** : Ajout ou modification de tests
- **chore** : Maintenance, dépendances, etc.

### Exemples

```bash
feat(editor): ajouter la coloration syntaxique

Implémente la coloration syntaxique pour les mots-clés,
chaînes, nombres et commentaires.

Closes #42
```

```bash
fix(compiler): corriger l'évaluation des expressions

Le parseur ne gérait pas correctement les parenthèses
dans les expressions arithmétiques.

Fixes #87
```

```bash
docs(readme): mettre à jour les instructions d'installation
```

### Scope (Portée)

- **editor** : Éditeur de code
- **compiler** : Compilateur
- **ui** : Interface utilisateur
- **files** : Gestion de fichiers
- **console** : Console
- **docs** : Documentation
- **build** : Build et configuration

## Configuration de Développement

### 1. Installer les Prérequis

- Android Studio Hedgehog ou supérieur
- JDK 17
- Android SDK 34

### 2. Cloner et Configurer

```bash
git clone https://github.com/oughzal/Algo-compiler.git
cd Algo-compiler/algo-compiler-android
```

### 3. Ouvrir dans Android Studio

File → Open → Sélectionner le dossier

### 4. Configuration du Formatage

Android Studio → Settings → Editor → Code Style → Kotlin
Importer le fichier de style (si disponible)

### 5. Activer les Inspections

Settings → Editor → Inspections
Activer toutes les inspections Kotlin et Android

### 6. Pre-commit Hooks (Optionnel)

```bash
# Créer .git/hooks/pre-commit
#!/bin/sh
./gradlew ktlintCheck test
```

## Tests

### Tests Unitaires

```kotlin
class MyViewModelTest {
    @Test
    fun `test file creation`() {
        // Given
        val viewModel = AlgoViewModel()
        
        // When
        viewModel.createFile("test")
        
        // Then
        assertEquals(1, viewModel.files.value.size)
    }
}
```

### Tests UI

```kotlin
@Test
fun testEditorScreen() {
    composeTestRule.setContent {
        EditorScreen(viewModel = viewModel)
    }
    
    composeTestRule
        .onNodeWithText("Enregistrer")
        .assertExists()
}
```

### Exécuter les Tests

```bash
# Tous les tests
./gradlew test

# Tests spécifiques
./gradlew test --tests MyViewModelTest

# Coverage
./gradlew testDebugUnitTestCoverage
```

## Documentation

### KDoc

Documenter les fonctions publiques et classes :

```kotlin
/**
 * Compile et exécute un algorithme.
 *
 * @param file Le fichier contenant l'algorithme
 * @return Le résultat de l'exécution
 * @throws CompilerException si la compilation échoue
 */
fun executeAlgorithm(file: AlgoFile): String {
    // ...
}
```

### README

Mettre à jour le README si vos changements :
- Ajoutent une nouvelle fonctionnalité
- Changent l'installation ou la configuration
- Affectent l'utilisation de l'app

## Questions ?

N'hésitez pas à :
- Ouvrir une Issue pour poser des questions
- Rejoindre les discussions GitHub
- Contacter les mainteneurs

## Remerciements

Merci d'avoir pris le temps de contribuer ! 🙏

Chaque contribution, petite ou grande, est appréciée et aide à faire de ce projet un meilleur outil pour tous.

---

**Note** : Ce guide peut être mis à jour. Consultez-le régulièrement pour les dernières conventions.

