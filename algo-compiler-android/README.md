# Algo Compiler Mobile - Application Android
- [Documentation](https://oughzal.github.io/Algo-compiler)
- [Compilateur Desktop](https://github.com/oughzal/Algo-compiler/releases)
- [Extension VS Code](https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler)

## Liens

- Site Web : [https://oughzal.github.io/Algo-compiler](https://oughzal.github.io/Algo-compiler)
- GitHub : [oughzal](https://github.com/oughzal)

## Contact

Copyright © 2025 Omar OUGHZAL

## Licence

- Soumettre des pull requests
- Proposer des fonctionnalités
- Signaler des bugs
Les contributions sont les bienvenues ! N'hésitez pas à :

## Contribution

`app/build/outputs/apk/release/app-release.apk`
L'APK sera généré dans :

```
./gradlew assembleRelease
```bash

Pour créer un APK de release :

## Build APK

- Et plus encore...
- `ord()`, `chr()` - Caractères
- `majuscule()`, `minuscule()`, `longueur()` - Chaînes
- `racine()`, `abs()`, `puissance()` - Mathématiques
- `lire()` - Saisie
- `ecrire()`, `ecrireln()` - Affichage
### Fonctions Intégrées

- `repeter...jusqua` - Boucle do-while
- `tantque...faire...fintantque` - Boucle while
- `pour...de...à...faire...finpour` - Boucle for
- `selon...cas...finselon` - Switch
- `sinonsi` - Conditions multiples
- `si...alors...sinon...finsi` - Conditions
### Instructions

- `tableau` - Tableaux et matrices
- `booleen` - Vrai/Faux
- `caractere` - Caractères simples
- `chaine` - Chaînes de caractères
- `reel` - Nombres décimaux
- `entier` - Nombres entiers
### Types de Données

Le compilateur supporte toutes les fonctionnalités du langage Algo :

## Langage Algo

Sur Android 13+, l'application utilise le stockage scopé (pas de permission nécessaire).

- `WRITE_EXTERNAL_STORAGE` - Écrire les fichiers (Android < 13)
- `READ_EXTERNAL_STORAGE` - Lire les fichiers (Android < 13)
L'application nécessite les permissions suivantes :

## Permissions

3. Voir les résultats dans l'onglet "Console"
2. Cliquer sur le bouton "Exécuter" (▶️)
1. Ouvrir le fichier dans l'éditeur
### Exécuter un Algorithme

```
fin
    ecrireln("Bonjour ", nom, " !")
    lire(nom)
    ecrire("Entrez votre nom : ")
debut
    nom : chaine
variables
algorithme HelloWorld
```algo
### Écrire un Algorithme

4. Commencer à coder !
3. Entrer le nom du fichier
2. Cliquer sur le bouton "+" (en bas à droite)
1. Aller dans l'onglet "Fichiers"
### Créer un Fichier

## Utilisation

```
│       └── xml/
│       │   └── themes.xml
│       │   ├── colors.xml
│       │   ├── strings.xml
│       ├── values/
│   └── res/                   # Ressources
│   │   └── MainActivity.kt    # Activité principale
│   │   │   └── theme/         # Thème
│   │   │   │   └── EditorScreen.kt
│   │   │   │   ├── FileExplorerScreen.kt
│   │   │   ├── screens/       # Écrans
│   │   ├── ui/                # Interface utilisateur
│   │   │   └── AlgoViewModel.kt
│   │   ├── viewmodel/         # ViewModels
│   │   │   └── FileManager.kt # Gestionnaire de fichiers
│   │   │   ├── AlgoFile.kt    # Modèle de fichier
│   │   ├── data/              # Gestion des données
│   │   │   └── ...
│   │   │   ├── Interpreter.kt # Interpréteur
│   │   │   ├── Parser.kt      # Analyse syntaxique
│   │   │   ├── Lexer.kt       # Analyse lexicale
│   │   ├── compiler/          # Moteur de compilation
│   ├── java/com/algocompiler/mobile/
├── src/main/
app/
```

## Structure du Projet

   - Cliquer sur "Run" (▶️)
   - Connecter un appareil Android ou lancer un émulateur
4. Exécuter l'application :

   - Fichier > Sync Project with Gradle Files
3. Synchroniser Gradle :

2. Ouvrir le projet dans Android Studio

```
cd Algo-compiler/algo-compiler-android
git clone https://github.com/oughzal/Algo-compiler.git
```bash
1. Cloner le projet :

## Installation

- Appareil Android 7.0 (API 24) ou supérieur
- JDK 17
- Android SDK 34
- Android Studio Hedgehog | 2023.1.1 ou supérieur

## Prérequis

- **Material Design 3** - Design moderne
- **Coroutines** - Gestion asynchrone
- **MVVM Architecture** - Architecture propre et maintenable
- **Kotlin** - Langage de développement
- **Jetpack Compose** - Interface utilisateur moderne

## Technologies

- Navigation par onglets
- Thème sombre/clair automatique
- Interface responsive optimisée pour mobile
- Design Material Design 3
### 🎨 Interface Moderne

- Messages d'erreur détaillés
- Console intégrée pour afficher les résultats
- Compilation et exécution en temps réel
### ▶️ Exécution

- Navigation intuitive
- Renommer et supprimer des fichiers
- Organisation en dossiers
- Création de fichiers .algo
### 📁 Gestionnaire de Fichiers

- Sauvegarde automatique
- Support complet du langage Algo
- Coloration syntaxique
- Éditeur de code avec numéros de ligne
### 📝 Éditeur de Code

## Fonctionnalités

Application Android IDE pour créer, éditer et exécuter des algorithmes écrits en langage Algo.


