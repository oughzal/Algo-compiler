# Guide de Build et Déploiement - Algo Compiler Mobile

## Table des Matières

1. [Prérequis](#prérequis)
2. [Configuration de l'Environnement](#configuration-de-lenvironnement)
3. [Build Debug](#build-debug)
4. [Build Release](#build-release)
5. [Signature de l'APK](#signature-de-lapk)
6. [Déploiement sur Play Store](#déploiement-sur-play-store)
7. [Tests](#tests)
8. [Troubleshooting](#troubleshooting)

## Prérequis

### Logiciels Requis

- **Android Studio** : Hedgehog (2023.1.1) ou supérieur
- **JDK** : Version 17 ou supérieur
- **Android SDK** :
  - Minimum SDK : 24 (Android 7.0)
  - Target SDK : 34 (Android 14)
  - Compile SDK : 34

### Configuration Matérielle Recommandée

- **RAM** : 8 GB minimum (16 GB recommandé)
- **Stockage** : 10 GB d'espace libre
- **Processeur** : Intel i5 ou équivalent

## Configuration de l'Environnement

### 1. Installer Android Studio

1. Télécharger depuis [developer.android.com](https://developer.android.com/studio)
2. Installer avec les paramètres par défaut
3. Lancer Android Studio
4. Suivre l'assistant de configuration

### 2. Configurer le SDK

Dans Android Studio :
1. **File** → **Settings** → **Appearance & Behavior** → **System Settings** → **Android SDK**
2. Installer les composants suivants :
   - Android SDK Platform 34
   - Android SDK Build-Tools 34.0.0
   - Android SDK Platform-Tools
   - Android Emulator

### 3. Configurer JDK

1. **File** → **Settings** → **Build, Execution, Deployment** → **Build Tools** → **Gradle**
2. Gradle JDK : Sélectionner JDK 17 ou supérieur

### 4. Cloner le Projet

```bash
git clone https://github.com/oughzal/Algo-compiler.git
cd Algo-compiler/algo-compiler-android
```

### 5. Ouvrir dans Android Studio

1. **File** → **Open**
2. Sélectionner le dossier `algo-compiler-android`
3. Attendre la synchronisation Gradle

## Build Debug

### Via Android Studio

1. **Build** → **Make Project** (Ctrl+F9)
2. Attendre la fin du build
3. APK généré dans : `app/build/outputs/apk/debug/app-debug.apk`

### Via Ligne de Commande

```bash
# Windows
.\gradlew assembleDebug

# Linux/Mac
./gradlew assembleDebug
```

L'APK sera dans : `app/build/outputs/apk/debug/app-debug.apk`

### Installation de l'APK Debug

#### Sur Émulateur

1. Lancer un émulateur
2. **Run** → **Run 'app'** (Shift+F10)

#### Sur Appareil Physique

1. Activer le **Mode Développeur** sur l'appareil :
   - Paramètres → À propos du téléphone
   - Appuyer 7 fois sur "Numéro de build"

2. Activer le **Débogage USB** :
   - Paramètres → Options pour les développeurs
   - Activer "Débogage USB"

3. Connecter l'appareil via USB

4. Exécuter :
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

## Build Release

### 1. Préparer la Configuration

Créer le fichier `keystore.properties` à la racine du projet :

```properties
storePassword=VOTRE_MOT_DE_PASSE
keyPassword=VOTRE_MOT_DE_PASSE
keyAlias=algocompiler
storeFile=../keystore/algocompiler.jks
```

⚠️ **Ne jamais commiter ce fichier sur Git !**

### 2. Créer un Keystore

```bash
keytool -genkey -v -keystore keystore/algocompiler.jks -keyalg RSA -keysize 2048 -validity 10000 -alias algocompiler
```

Répondre aux questions :
- Mot de passe du keystore
- Nom complet
- Organisation
- Ville, État, Pays

### 3. Configurer build.gradle.kts

Ajouter dans `app/build.gradle.kts` :

```kotlin
android {
    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val keystoreProperties = Properties()
                keystoreProperties.load(FileInputStream(keystorePropertiesFile))

                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### 4. Build Release

#### Via Android Studio

1. **Build** → **Generate Signed Bundle / APK**
2. Sélectionner **APK**
3. Sélectionner le keystore
4. Entrer les mots de passe
5. Sélectionner **release**
6. Cliquer sur **Finish**

#### Via Ligne de Commande

```bash
# Windows
.\gradlew assembleRelease

# Linux/Mac
./gradlew assembleRelease
```

L'APK sera dans : `app/build/outputs/apk/release/app-release.apk`

## Signature de l'APK

### Vérifier la Signature

```bash
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk
```

### Afficher les Informations du Keystore

```bash
keytool -list -v -keystore keystore/algocompiler.jks
```

## Android App Bundle (AAB)

Pour le Play Store, utiliser AAB au lieu d'APK :

```bash
# Windows
.\gradlew bundleRelease

# Linux/Mac
./gradlew bundleRelease
```

Le bundle sera dans : `app/build/outputs/bundle/release/app-release.aab`

## Optimisation

### ProGuard/R8

Modifier `app/proguard-rules.pro` :

```proguard
# Keep compiler classes
-keep class com.algocompiler.mobile.compiler.** { *; }

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# Keep Compose
-keep class androidx.compose.** { *; }

# Optimizations
-optimizationpasses 5
-allowaccessmodification
```

### Réduire la Taille de l'APK

1. **Activer R8** (activé par défaut)
2. **Activer shrinkResources**
3. **Utiliser WebP** pour les images
4. **Supprimer** les ressources inutilisées

```kotlin
buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
    }
}
```

## Déploiement sur Play Store

### 1. Créer un Compte Développeur

1. Aller sur [play.google.com/console](https://play.google.com/console)
2. Payer les frais d'inscription (25 USD)
3. Remplir les informations du compte

### 2. Créer une Application

1. **Create app**
2. Remplir les informations :
   - Nom : Algo Compiler
   - Langue par défaut : Français
   - Type : Application
   - Gratuite

### 3. Préparer la Fiche Store

#### Captures d'Écran

Prendre des captures d'écran de :
- Écran d'accueil (liste fichiers)
- Éditeur avec code
- Console avec résultats
- Menu de navigation

Tailles requises :
- **Téléphone** : 16:9 ou 9:16
- **Tablette** : 16:9 ou 9:16 (optionnel)

#### Description Courte

```
IDE mobile pour créer et exécuter des algorithmes. 
Éditeur, console intégrée, gestion de fichiers.
```

#### Description Complète

```
Algo Compiler Mobile - IDE Complet pour Algorithmes

Créez, éditez et exécutez vos algorithmes directement sur votre appareil Android !

FONCTIONNALITÉS :

📝 Éditeur de Code Avancé
• Numéros de ligne
• Coloration syntaxique
• Sauvegarde automatique

📁 Gestionnaire de Fichiers
• Création de fichiers .algo
• Organisation en dossiers
• Renommer, supprimer

▶️ Exécution en Temps Réel
• Console intégrée
• Messages d'erreur détaillés
• Exécution rapide

🎨 Interface Moderne
• Material Design 3
• Thème sombre/clair
• Navigation intuitive

LANGAGE ALGO :
• Types : entier, réel, chaîne, caractère, booléen, tableau
• Instructions : si, selon, pour, tantque, repeter
• Fonctions intégrées : ecrire, lire, racine, abs, etc.

IDÉAL POUR :
• Étudiants en informatique
• Apprentissage de la programmation
• Exercices d'algorithmique
• Tests rapides d'algorithmes

Gratuit et open source !
```

### 4. Uploader l'AAB

1. **Production** → **Releases**
2. **Create new release**
3. Uploader `app-release.aab`
4. Remplir les notes de version
5. **Save** → **Review release**
6. **Start rollout to Production**

### 5. Remplir le Contenu

- **Classement du contenu** (ESRB, PEGI, etc.)
- **Public cible**
- **Politique de confidentialité**
- **Coordonnées**

## Tests

### Tests Unitaires

Créer dans `app/src/test/` :

```kotlin
class AlgoViewModelTest {
    @Test
    fun testFileCreation() {
        val viewModel = AlgoViewModel()
        viewModel.createFile("test")
        // Assert
    }
}
```

Exécuter :
```bash
.\gradlew test
```

### Tests Instrumentés

Créer dans `app/src/androidTest/` :

```kotlin
@Test
fun testCompilerExecution() {
    val code = """
        algorithme test
        debut
            ecrireln("Hello")
        fin
    """
    // Test execution
}
```

Exécuter :
```bash
.\gradlew connectedAndroidTest
```

### Tests UI

```kotlin
@Test
fun testEditorScreen() {
    composeTestRule.setContent {
        EditorScreen(viewModel)
    }
    
    composeTestRule.onNodeWithText("Enregistrer").assertExists()
}
```

## Troubleshooting

### Erreur de Synchronisation Gradle

```
Solution : File → Invalidate Caches / Restart
```

### Erreur de Build

```bash
# Nettoyer le projet
.\gradlew clean

# Rebuild
.\gradlew build --stacktrace
```

### Erreur de Signature

```
Vérifier keystore.properties
Vérifier que le keystore existe
Vérifier les mots de passe
```

### APK Trop Volumineux

```
Activer minifyEnabled et shrinkResources
Utiliser APK Analyzer : Build → Analyze APK
```

### Problème de Permissions

```
Vérifier AndroidManifest.xml
Tester sur Android 13+ et Android < 13
```

## Checklist Avant Release

- [ ] Tester sur plusieurs appareils
- [ ] Tester sur différentes versions Android
- [ ] Vérifier les permissions
- [ ] Tester hors ligne
- [ ] Vérifier la taille de l'APK
- [ ] Lancer les tests unitaires
- [ ] Mettre à jour le versionCode et versionName
- [ ] Créer les captures d'écran
- [ ] Rédiger les notes de version
- [ ] Signer l'APK/AAB
- [ ] Tester l'APK release sur un appareil

## Versioning

Suivre le **Semantic Versioning** (SemVer) :

```
MAJOR.MINOR.PATCH

Example: 1.2.3
- 1 : Version majeure (breaking changes)
- 2 : Version mineure (nouvelles fonctionnalités)
- 3 : Version patch (corrections de bugs)
```

Dans `app/build.gradle.kts` :

```kotlin
defaultConfig {
    versionCode = 1  // Incrémenter à chaque release
    versionName = "1.0.0"  // Version visible par l'utilisateur
}
```

## Resources

- [Documentation Android](https://developer.android.com/docs)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Play Console Help](https://support.google.com/googleplay/android-developer)

## Conclusion

Suivre ce guide garantit :
- Build reproductible
- APK optimisé
- Déploiement sécurisé
- Release de qualité

Bonne chance avec votre application ! 🚀

