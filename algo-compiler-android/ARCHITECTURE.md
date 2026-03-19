# Architecture de l'Application Algo Compiler Mobile

## Vue d'Ensemble

L'application utilise l'architecture **MVVM (Model-View-ViewModel)** avec **Jetpack Compose** pour une séparation claire des responsabilités et une meilleure maintenabilité.

## Architecture MVVM

```
┌─────────────────────────────────────────────────────────┐
│                         View (UI)                        │
│  ┌────────────────┐  ┌────────────────┐  ┌───────────┐ │
│  │ FileExplorer   │  │ EditorScreen   │  │ Console   │ │
│  │    Screen      │  │                │  │  Screen   │ │
│  └────────────────┘  └────────────────┘  └───────────┘ │
└──────────────────────────┬──────────────────────────────┘
                           │
                           │ observe StateFlow
                           │ call functions
                           ▼
┌─────────────────────────────────────────────────────────┐
│                      ViewModel                           │
│  ┌──────────────────────────────────────────────────┐  │
│  │              AlgoViewModel                        │  │
│  │  • manage UI state                               │  │
│  │  • handle user actions                           │  │
│  │  • coordinate between model and view             │  │
│  └──────────────────────────────────────────────────┘  │
└──────────────────────────┬──────────────────────────────┘
                           │
                           │ use
                           │
                           ▼
┌─────────────────────────────────────────────────────────┐
│                      Model (Data)                        │
│  ┌──────────────┐  ┌────────────────┐  ┌────────────┐  │
│  │ FileManager  │  │ AlgoFile       │  │ Compiler   │  │
│  │              │  │                │  │            │  │
│  └──────────────┘  └────────────────┘  └────────────┘  │
└─────────────────────────────────────────────────────────┘
```

## Couches de l'Application

### 1. Couche UI (View)

**Localisation** : `ui/screens/`

**Responsabilités** :
- Afficher l'interface utilisateur
- Capturer les interactions utilisateur
- Observer les changements d'état
- Aucune logique métier

**Composants** :
- `FileExplorerScreen.kt` : Navigation et gestion des fichiers
- `EditorScreen.kt` : Édition de code
- `ConsoleScreen` : Affichage des résultats

**Technologies** :
- Jetpack Compose
- Material Design 3
- Composants réutilisables

### 2. Couche ViewModel

**Localisation** : `viewmodel/`

**Responsabilités** :
- Gérer l'état de l'interface
- Traiter les actions utilisateur
- Coordonner les opérations
- Exposer les données via StateFlow

**Classe principale** : `AlgoViewModel`

**États gérés** :
```kotlin
class AlgoViewModel : ViewModel() {
    private val _files = MutableStateFlow<List<AlgoFile>>(emptyList())
    val files: StateFlow<List<AlgoFile>> = _files.asStateFlow()
    
    private val _currentFile = MutableStateFlow<AlgoFile?>(null)
    val currentFile: StateFlow<AlgoFile?> = _currentFile.asStateFlow()
    
    private val _editorContent = MutableStateFlow("")
    val editorContent: StateFlow<String> = _editorContent.asStateFlow()
    
    private val _consoleOutput = MutableStateFlow("")
    val consoleOutput: StateFlow<String> = _consoleOutput.asStateFlow()
    
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()
}
```

**Opérations** :
- `loadFiles()` : Charger la liste des fichiers
- `createFile()` : Créer un nouveau fichier
- `openFile()` : Ouvrir un fichier
- `saveCurrentFile()` : Sauvegarder le fichier courant
- `runAlgorithm()` : Exécuter l'algorithme
- `deleteFile()` : Supprimer un fichier
- `renameFile()` : Renommer un fichier

### 3. Couche Model (Data)

**Localisation** : `data/` et `compiler/`

#### Data Layer

**FileManager** (`data/FileManager.kt`)
- Gestion du système de fichiers
- Opérations CRUD sur les fichiers
- Exécution des algorithmes
- Interface avec le compilateur

**AlgoFile** (`data/AlgoFile.kt`)
- Modèle de données pour un fichier/dossier
- Propriétés : nom, chemin, type, référence File

#### Compiler Layer

**Lexer** (`compiler/Lexer.kt`)
- Analyse lexicale
- Tokenization du code source
- Gestion des mots-clés et symboles

**Parser** (`compiler/Parser.kt`)
- Analyse syntaxique
- Construction de l'AST (Abstract Syntax Tree)
- Validation de la syntaxe

**Interpreter** (`compiler/Interpreter.kt`)
- Exécution de l'AST
- Gestion des variables et types
- Évaluation des expressions
- Exécution des instructions

**AST** (`compiler/AST.kt`)
- Définition des nœuds de l'arbre syntaxique
- Représentation du programme

## Flux de Données

### Flux Unidirectionnel (UDF)

```
User Action → ViewModel → Model → ViewModel → UI Update
```

### Exemple : Exécution d'un Algorithme

1. **User Action** : L'utilisateur appuie sur "Exécuter"
   ```kotlin
   IconButton(onClick = { viewModel.runAlgorithm() })
   ```

2. **ViewModel** : Traite l'action
   ```kotlin
   fun runAlgorithm() {
       viewModelScope.launch {
           _isRunning.value = true
           val result = fileManager.executeAlgorithm(file)
           appendToConsole(result)
           _isRunning.value = false
       }
   }
   ```

3. **Model** : Exécute l'opération
   ```kotlin
   fun executeAlgorithm(file: AlgoFile): String {
       val code = readFile(file)
       val lexer = Lexer(code)
       val parser = Parser(lexer.tokenize())
       val interpreter = Interpreter()
       return interpreter.execute(parser.parse())
   }
   ```

4. **UI Update** : L'état est observé et l'UI se met à jour
   ```kotlin
   val consoleOutput by viewModel.consoleOutput.collectAsState()
   Text(text = consoleOutput)
   ```

## Gestion de l'État

### StateFlow vs LiveData

L'application utilise **StateFlow** (pas LiveData) pour plusieurs raisons :
- Support natif des coroutines
- Type-safe
- Compatible Compose
- Meilleure performance

### État Immutable

Les états sont exposés comme **read-only StateFlow** :
```kotlin
private val _files = MutableStateFlow<List<AlgoFile>>(emptyList())
val files: StateFlow<List<AlgoFile>> = _files.asStateFlow()
```

Cela garantit que seul le ViewModel peut modifier l'état.

## Gestion des Coroutines

### ViewModelScope

Toutes les opérations asynchrones utilisent `viewModelScope` :
```kotlin
fun loadFiles() {
    viewModelScope.launch {
        _files.value = fileManager.getAllFiles()
    }
}
```

**Avantages** :
- Annulation automatique quand le ViewModel est détruit
- Pas de fuites mémoire
- Gestion d'erreur simplifiée

## Navigation

### Navigation par Onglets

L'application utilise une **NavigationBar** avec 3 onglets :
1. **Fichiers** : FileExplorerScreen
2. **Éditeur** : EditorScreen
3. **Console** : ConsoleScreen

**Implémentation** :
```kotlin
var selectedTab by remember { mutableStateOf(0) }

when (selectedTab) {
    0 -> FileExplorerScreen(viewModel)
    1 -> EditorScreen(viewModel)
    2 -> ConsoleScreen(viewModel)
}
```

## Gestion des Fichiers

### Stockage

Les fichiers sont stockés dans :
```
/storage/emulated/0/Documents/AlgoCompiler/
```

### Structure

```
AlgoCompiler/
├── fichier1.algo
├── fichier2.algo
├── dossier1/
│   ├── fichier3.algo
│   └── fichier4.algo
└── dossier2/
    └── fichier5.algo
```

### Opérations

Toutes les opérations fichier passent par `FileManager` :
- Isolation de la logique de stockage
- Gestion d'erreur centralisée
- Facilite les tests

## Thème et Style

### Material Design 3

L'application utilise **Material Design 3** pour :
- Design moderne et cohérent
- Support thème clair/sombre
- Composants accessibles
- Animations fluides

### Thème Dynamique

Sur Android 12+, l'application supporte le **thème dynamique** :
```kotlin
val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        if (darkTheme) dynamicDarkColorScheme(context) 
        else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
}
```

## Compilation du Code Algo

### Pipeline de Compilation

```
Source Code → Lexer → Tokens → Parser → AST → Interpreter → Output
```

### 1. Lexer (Analyse Lexicale)

**Entrée** : Code source (String)
**Sortie** : Liste de tokens

```kotlin
val lexer = Lexer(code)
val tokens = lexer.tokenize()
```

### 2. Parser (Analyse Syntaxique)

**Entrée** : Liste de tokens
**Sortie** : AST (Abstract Syntax Tree)

```kotlin
val parser = Parser(tokens)
val ast = parser.parse()
```

### 3. Interpreter (Exécution)

**Entrée** : AST
**Sortie** : Résultat d'exécution

```kotlin
val interpreter = Interpreter()
interpreter.execute(ast)
```

## Performance

### Optimisations

1. **Lazy Loading** : Les fichiers sont chargés à la demande
2. **Coroutines** : Opérations asynchrones pour ne pas bloquer l'UI
3. **StateFlow** : Mise à jour efficace de l'UI
4. **Remember** : Éviter les recompositions inutiles

### Gestion Mémoire

- ViewModelScope pour auto-nettoyage
- Pas de fuites de contexte
- Garbage collection efficace

## Tests

### Structure de Test (à implémenter)

```
app/src/test/
├── viewmodel/
│   └── AlgoViewModelTest.kt
├── data/
│   └── FileManagerTest.kt
└── compiler/
    ├── LexerTest.kt
    ├── ParserTest.kt
    └── InterpreterTest.kt
```

## Évolutions Futures

### Architecture

- [ ] Ajouter un Repository pattern
- [ ] Implémenter un Use Case layer
- [ ] Ajouter Dependency Injection (Hilt)
- [ ] Implémenter Clean Architecture complète

### Features

- [ ] Cache des fichiers récents
- [ ] Synchronisation cloud
- [ ] Mode hors ligne complet
- [ ] Historique des exécutions

### Performance

- [ ] Compilation incrémentale
- [ ] Cache de l'AST
- [ ] Optimisation du Lexer/Parser
- [ ] Background compilation

## Conclusion

L'architecture MVVM avec Jetpack Compose offre :
- **Séparation des préoccupations** claire
- **Testabilité** élevée
- **Maintenabilité** facilitée
- **Scalabilité** pour futures fonctionnalités
- **Performance** optimale

Cette architecture permet d'ajouter facilement de nouvelles fonctionnalités tout en maintenant un code propre et organisé.

