# 📦 Guide d'Installation - Algo-Compiler

## 🚀 Installation Automatique

Deux installateurs sont disponibles pour installer automatiquement le compilateur Algo et l'ajouter au PATH système.

---

## 📋 Prérequis

✅ **Java 21 ou supérieur** doit être installé
- Télécharger depuis : https://www.oracle.com/java/technologies/downloads/
- Ou depuis : https://adoptium.net/

✅ **Droits administrateur** requis pour l'installation

---

## 🔧 Méthode 1 : Installateur Batch (.bat)

### Utilisation

1. **Compiler le projet** (si pas déjà fait) :
   ```batch
   gradlew build
   ```

2. **Exécuter l'installateur** :
   - Clic droit sur `install-algo-compiler.bat`
   - Sélectionner **"Exécuter en tant qu'administrateur"**

3. **Suivre les instructions** à l'écran

4. **Fermer et rouvrir** votre terminal

### Ce que fait l'installateur

✅ Vérifie que Java est installé
✅ Crée le répertoire `C:\Program Files\Algo-Compiler`
✅ Copie le fichier JAR du compilateur
✅ Crée un script wrapper `algoc.bat` pour faciliter l'utilisation
✅ Ajoute le répertoire au PATH système
✅ Crée un désinstallateur `uninstall.bat`
✅ Propose de créer un raccourci sur le bureau (optionnel)

---

## 💻 Méthode 2 : Installateur PowerShell (.ps1)

### Utilisation

1. **Compiler le projet** (si pas déjà fait) :
   ```powershell
   .\gradlew build
   ```

2. **Exécuter PowerShell en administrateur** :
   - Touche Windows + X
   - Sélectionner "Windows PowerShell (Admin)"

3. **Naviguer vers le projet** :
   ```powershell
   cd "C:\Users\Omar\Documents\Algo-compiler"
   ```

4. **Autoriser l'exécution du script** (si nécessaire) :
   ```powershell
   Set-ExecutionPolicy -ExecutionPolicy Bypass -Scope Process
   ```

5. **Lancer l'installateur** :
   ```powershell
   .\install-algo-compiler.ps1
   ```

6. **Suivre les instructions** à l'écran

7. **Fermer et rouvrir** votre terminal

### Avantages de la version PowerShell

✨ Interface plus moderne avec couleurs
✨ Meilleure gestion des erreurs
✨ Création automatique de documentation
✨ Désinstallateur PowerShell inclus

---

## 🎯 Après l'Installation

### Vérifier l'installation

Ouvrir un **nouveau** terminal et taper :

```bash
algoc
```

Vous devriez voir :
```
Usage: algoc fichier.algo
```

### Utiliser le compilateur

```bash
# Compiler et exécuter un fichier
algoc hello.algo

# Avec un chemin complet
algoc C:\mes_algos\programme.algo

# Avec un fichier d'exemple
algoc examples\factorielle.algo
```

---

## 📁 Emplacement d'Installation

### Répertoire principal
```
C:\Program Files\Algo-Compiler\
├── algo-compiler-1.0.0.jar    # Le compilateur
├── algoc.bat                   # Script wrapper
├── uninstall.bat              # Désinstallateur (batch)
├── uninstall.ps1              # Désinstallateur (PowerShell)
└── README.txt                 # Documentation
```

### PATH Système

Le chemin suivant est ajouté automatiquement :
```
C:\Program Files\Algo-Compiler
```

---

## 🗑️ Désinstallation

### Méthode 1 : Script Batch

1. Clic droit sur `C:\Program Files\Algo-Compiler\uninstall.bat`
2. **"Exécuter en tant qu'administrateur"**

### Méthode 2 : Script PowerShell

1. Ouvrir PowerShell en administrateur
2. Exécuter :
   ```powershell
   & "C:\Program Files\Algo-Compiler\uninstall.ps1"
   ```

### Ce que fait le désinstallateur

✅ Supprime le compilateur du PATH système
✅ Supprime tous les fichiers du répertoire d'installation
✅ Nettoie complètement l'installation

---

## 🛠️ Dépannage

### Java n'est pas trouvé

**Problème** : L'installateur dit que Java n'est pas installé

**Solution** :
1. Installer Java 21+ depuis https://adoptium.net/
2. Redémarrer le terminal
3. Vérifier : `java -version`

### Erreur de droits administrateur

**Problème** : "Ce script nécessite les droits administrateur"

**Solution** :
- **Batch** : Clic droit → "Exécuter en tant qu'administrateur"
- **PowerShell** : Lancer PowerShell en admin puis exécuter le script

### Le fichier JAR n'existe pas

**Problème** : "Le fichier algo-compiler-1.0.0.jar n'existe pas"

**Solution** :
```bash
# Compiler le projet d'abord
gradlew build

# Vérifier que le JAR existe
dir build\libs\algo-compiler-1.0.0.jar
```

### La commande 'algoc' n'est pas reconnue

**Problème** : Après installation, `algoc` ne fonctionne pas

**Solutions** :
1. **Fermer et rouvrir** le terminal (le PATH n'est pas mis à jour dans les terminaux ouverts)
2. Vérifier le PATH :
   ```powershell
   $env:Path -split ';' | Select-String "Algo-Compiler"
   ```
3. Redémarrer l'ordinateur (dans les cas extrêmes)

### Désinstallation manuelle

Si les désinstallateurs ne fonctionnent pas :

1. **Supprimer du PATH manuellement** :
   - Touche Windows + "variable"
   - "Modifier les variables d'environnement système"
   - Variables système → Path → Modifier
   - Supprimer : `C:\Program Files\Algo-Compiler`

2. **Supprimer les fichiers** :
   ```powershell
   Remove-Item "C:\Program Files\Algo-Compiler" -Recurse -Force
   ```

---

## 📖 Utilisation Avancée

### Intégration avec VS Code

Après l'installation, configurez VS Code pour utiliser le compilateur :

1. Installer l'extension Algo-Compiler
2. Dans les paramètres VS Code :
   ```json
   {
     "algoCompiler.compilerPath": "C:\\Program Files\\Algo-Compiler\\algo-compiler-1.0.0.jar",
     "algoCompiler.javaPath": "java"
   }
   ```

### Utiliser depuis n'importe quel répertoire

Grâce au PATH, vous pouvez compiler depuis n'importe où :

```bash
# Naviguer vers votre projet
cd C:\Users\Omar\MesProjets

# Compiler un fichier
algoc mon_algo.algo
```

### Créer un alias personnalisé

Vous pouvez créer des alias pour simplifier l'utilisation :

**PowerShell** (dans votre profil `$PROFILE`) :
```powershell
function ac { algoc $args }
```

**CMD** : Créer un fichier `ac.bat` dans le même dossier :
```batch
@echo off
algoc %*
```

---

## 📊 Comparaison des Installateurs

| Caractéristique | Batch (.bat) | PowerShell (.ps1) |
|----------------|--------------|-------------------|
| Interface | Simple, texte | Moderne, couleurs |
| Compatibilité | Windows 7+ | Windows 7+ avec PowerShell |
| Facilité | Plus simple | Nécessite autorisation script |
| Documentation | Basique | Complète avec README |
| Raccourci bureau | Basique | Amélioré |
| Gestion erreurs | Standard | Avancée |

**Recommandation** : Utilisez PowerShell pour une meilleure expérience, ou Batch si vous préférez la simplicité.

---

## ✅ Checklist d'Installation

- [ ] Java 21+ installé et vérifié (`java -version`)
- [ ] Projet compilé (`gradlew build`)
- [ ] JAR existe dans `build\libs\`
- [ ] Installateur exécuté en administrateur
- [ ] Installation réussie (message de confirmation)
- [ ] Terminal fermé et rouvert
- [ ] Commande `algoc` fonctionne
- [ ] Test avec un fichier exemple réussi

---

## 🎓 Exemples Après Installation

```bash
# Hello World
algoc examples\hello.algo

# Factorielle
algoc examples\factorielle.algo

# Jeu de devinette
algoc examples\devine_nombre.algo

# Test des fonctions intégrées
algoc examples\test_fonctions_courantes.algo

# Test du type caractère
algoc examples\test_caractere.algo
```

---

## 📞 Support

Si vous rencontrez des problèmes :

1. **Documentation** : https://oughzal.github.io/Algo-compiler/
2. **GitHub Issues** : https://github.com/oughzal/Algo-compiler/issues
3. **README principal** : Consultez le README.md du projet

---

## 📝 Notes Importantes

⚠️ **Droits Administrateur** : Requis pour modifier le PATH système et installer dans Program Files

⚠️ **Antivirus** : Certains antivirus peuvent bloquer l'ajout au PATH. Ajoutez une exception si nécessaire.

⚠️ **Redémarrage Terminal** : Toujours fermer et rouvrir le terminal après l'installation pour que le PATH soit actif.

⚠️ **Version Java** : Java 21 minimum requis. Vérifiez votre version avec `java -version`.

---

**🚀 Bon codage avec Algo-Compiler !**

*Version du guide : 1.0.0*
*Date : 4 décembre 2025*

