# 📤 Guide de Publication - Extension v1.7.4 sur VS Code Marketplace

## ✅ Prérequis Validés

- [x] **Version** : 1.7.4 (incrémentée depuis 1.7.0)
- [x] **Package VSIX** : `algo-compiler-1.7.4.vsix` (40.63 KB)
- [x] **CHANGELOG** : Mis à jour avec les nouveautés
- [x] **README** : Optimisé avec lien direct JAR
- [x] **Tests** : Extension installée et testée localement
- [x] **Date** : 9 Décembre 2025

---

## 🚀 Méthode 1 : Publication via la Ligne de Commande

### Étape 1 : Se Connecter au Marketplace

```powershell
# Se placer dans le dossier de l'extension
cd C:\Users\Omar\Documents\Algo-compiler\algo-compiler-vscode

# Se connecter (si nécessaire)
vsce login omarcomputer
```

**Note** : Un Personal Access Token (PAT) sera demandé si ce n'est pas déjà configuré.

### Étape 2 : Publier l'Extension

```powershell
# Publier la nouvelle version
vsce publish
```

**Résultat attendu** :
```
Publishing omarcomputer.algo-compiler@1.7.4...
Successfully published omarcomputer.algo-compiler@1.7.4!
```

### Étape 3 : Vérifier la Publication

```powershell
# La version devrait apparaître sur le Marketplace dans quelques minutes
# URL : https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler
```

---

## 🌐 Méthode 2 : Publication via l'Interface Web (Recommandé)

### Étape 1 : Accéder au Portail de Publication

1. Aller sur : [https://marketplace.visualstudio.com/manage](https://marketplace.visualstudio.com/manage)
2. Se connecter avec le compte Microsoft associé à `omarcomputer`

### Étape 2 : Sélectionner l'Extension

1. Cliquer sur l'éditeur **"omarcomputer"**
2. Trouver l'extension **"Algo Compiler"**
3. Cliquer sur le bouton **"..."** (trois points)
4. Sélectionner **"Update"**

### Étape 3 : Téléverser le Nouveau Package

1. Cliquer sur **"Choose file"** ou **"Parcourir"**
2. Sélectionner le fichier :
   ```
   C:\Users\Omar\Documents\Algo-compiler\algo-compiler-vscode\algo-compiler-1.7.4.vsix
   ```
3. Cliquer sur **"Upload"**

### Étape 4 : Attendre la Validation

- Le Marketplace va analyser le package
- Cela prend généralement 1-5 minutes
- Une fois validé, la version 1.7.4 sera disponible publiquement

---

## 📝 Notes de Version pour le Marketplace

### Titre
```
Version 1.7.4 - Installation Simplifiée & Détection Automatique Intelligente
```

### Description Courte
```
Installation simplifiée en 3 étapes avec détection automatique du compilateur. Nouveau logo professionnel et support multi-versions.
```

### Description Complète

```markdown
## 🎉 Nouveautés de la Version 1.7.4

### 🎯 Installation Simplifiée
- **Chemin par défaut configuré** : `c:\algo-compiler-1.6.0.jar`
- **Aucune configuration manuelle** nécessaire après installation
- Installation réduite à **3 étapes simples** :
  1. Télécharger [algo-compiler-1.6.0.jar](https://github.com/oughzal/Algo-compiler/releases/download/release/algo-compiler-1.6.0.jar)
  2. Placer à `c:\algo-compiler-1.6.0.jar`
  3. Installer cette extension

### 🔍 Détection Automatique Intelligente
- **Support multi-versions** : Détection automatique des versions 1.6.0, 1.7.0, 1.8.0+
- **Ordre de recherche optimisé** :
  1. Chemin configuré par l'utilisateur
  2. Chemin par défaut : `c:\algo-compiler-1.6.0.jar`
  3. Versions futures
  4. Emplacements du workspace
- **Compatibilité ascendante** garantie

### 🎨 Interface Améliorée
- **Nouveau logo professionnel**
- **README optimisé** avec lien direct vers le JAR
- **Documentation enrichie**
- Branding cohérent

### 📚 Documentation
- Guide d'installation visuel complet
- Configuration détaillée pour tous les scénarios
- Exemples d'utilisation pratiques
- Section dépannage complète

---

## 🚀 Fonctionnalités Principales

### 🎨 Coloration Syntaxique Complète
- Mots-clés, types, opérateurs
- 40+ fonctions intégrées
- Commentaires (// # /* */)

### ⚡ Exécution Directe
- Raccourci : `Ctrl+Shift+R`
- Terminal intégré
- Support interactif (lire/écrire)

### 📦 44+ Snippets Intelligents
- Structures (si, pour, tantque)
- Fonctions et procédures
- Tableaux et matrices
- Expressions conditionnelles

### 🧮 Fonctionnalités Avancées
- **Matrices 2D** : `M : tableau[3][3] de entier`
- **Opérateur puissance** : `2 ^ 3` ou `2 ** 3`
- **Clause sinonSi** : `si ... sinonSi ... sinon`
- **Expression conditionnelle** : `max = si a > b alors a sinon b`
- **Initialisation** : `a : entier = 5`
- **Tableaux littéraux** : `T = [1, 2, 3, 4]`

---

## 📖 Ressources

- 🌐 **Site Web** : [https://oughzal.github.io/Algo-compiler/](https://oughzal.github.io/Algo-compiler/)
- 📦 **Compilateur JAR** : [Télécharger](https://github.com/oughzal/Algo-compiler/releases/download/release/algo-compiler-1.6.0.jar)
- 📖 **Documentation** : [HELP.md](https://github.com/oughzal/Algo-compiler/blob/main/HELP.md)
- 🚀 **Quick Start** : [QUICKSTART.md](https://github.com/oughzal/Algo-compiler/blob/main/QUICKSTART.md)
- 🐛 **Issues** : [GitHub](https://github.com/oughzal/Algo-compiler/issues)

---

**Bon algorithme ! 🎓**
```

---

## 🔐 Configuration du Personal Access Token (PAT)

### Si Vous N'avez Pas de PAT

1. **Aller sur Azure DevOps** : [https://dev.azure.com/](https://dev.azure.com/)
2. **User Settings** → **Personal Access Tokens**
3. **New Token** :
   - Name : `VS Code Marketplace Publishing`
   - Organization : **All accessible organizations**
   - Expiration : **Custom** (1 an recommandé)
   - Scopes : **Marketplace** → **Manage** (cocher)
4. **Copier le token** (vous ne pourrez plus le voir après)

### Configurer le Token

```powershell
# Méthode 1 : Stocker dans vsce
vsce login omarcomputer
# Coller le PAT quand demandé

# Méthode 2 : Publier directement avec le token
vsce publish -p <votre-token>
```

---

## ✅ Checklist de Publication

### Avant Publication
- [x] Package VSIX créé (`algo-compiler-1.7.4.vsix`)
- [x] Version correcte dans `package.json` (1.7.4)
- [x] CHANGELOG mis à jour
- [x] README optimisé
- [x] Tests locaux réussis
- [x] Logo inclus (11.53 KB)
- [x] Taille du package acceptable (40.63 KB)

### Pendant la Publication
- [ ] Se connecter au Marketplace
- [ ] Téléverser le fichier VSIX
- [ ] Attendre la validation (1-5 min)
- [ ] Vérifier qu'il n'y a pas d'erreurs

### Après Publication
- [ ] Vérifier sur le Marketplace que la version 1.7.4 apparaît
- [ ] Tester l'installation depuis le Marketplace
- [ ] Créer un release GitHub v1.7.4 (optionnel)
- [ ] Mettre à jour le site web si nécessaire
- [ ] Communiquer la mise à jour

---

## 🐛 Résolution de Problèmes

### Erreur : "Extension already exists with this version"

**Solution** : La version 1.7.4 existe déjà sur le Marketplace
```powershell
# Incrémenter à nouveau la version
# Modifier package.json : version → 1.7.5
# Recompiler et republier
```

### Erreur : "Invalid Personal Access Token"

**Solution** : Le PAT a expiré ou est invalide
```powershell
# Créer un nouveau PAT sur Azure DevOps
# Se reconnecter
vsce login omarcomputer
```

### Erreur : "Package size too large"

**Solution** : Le package dépasse la limite (rare)
```powershell
# Vérifier .vscodeignore pour exclure les fichiers inutiles
# Optimiser les images
# Supprimer les fichiers de dev
```

---

## 📊 Statistiques Post-Publication

### À Surveiller

1. **Nombre d'installations**
   - Vérifier après 24h, 1 semaine, 1 mois

2. **Note et avis**
   - Répondre aux commentaires
   - Prendre en compte les suggestions

3. **Signalements de bugs**
   - Suivre sur GitHub Issues
   - Corriger dans les futures versions

4. **Demandes de fonctionnalités**
   - Évaluer la pertinence
   - Planifier les développements

---

## 🎯 Commandes Rapides

```powershell
# Aller dans le dossier de l'extension
cd C:\Users\Omar\Documents\Algo-compiler\algo-compiler-vscode

# Vérifier la version actuelle
type package.json | findstr "version"

# Vérifier que le VSIX existe
dir *.vsix | sort LastWriteTime | select -Last 1

# Publier sur le Marketplace
vsce publish

# Ou avec token
vsce publish -p <votre-pat>

# Vérifier après publication
start https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler
```

---

## 📞 Support

### En cas de problème lors de la publication

1. **Vérifier les logs** de vsce
2. **Consulter** : [Publishing Extensions](https://code.visualstudio.com/api/working-with-extensions/publishing-extension)
3. **Contacter** : [VS Code Extension Support](https://github.com/microsoft/vscode/issues)

---

## 🎉 Résumé

### Ce qui est prêt
✅ Extension version 1.7.4  
✅ Package VSIX créé (40.63 KB)  
✅ CHANGELOG mis à jour  
✅ README optimisé  
✅ Tests locaux validés  
✅ Documentation complète  

### Action à faire
📤 **Publier sur le Marketplace VS Code**

**Commande** :
```powershell
cd C:\Users\Omar\Documents\Algo-compiler\algo-compiler-vscode
vsce publish
```

**Ou via l'interface web** :
[https://marketplace.visualstudio.com/manage](https://marketplace.visualstudio.com/manage)

---

**© 2024 Omar OUGHZAL** - Tous droits réservés

**Extension v1.7.4 prête à être publiée ! 🚀**

