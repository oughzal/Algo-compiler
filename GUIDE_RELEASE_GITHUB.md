# 🚀 Guide de Publication de la Release sur GitHub

## ✅ Préparation terminée

Tout est prêt pour publier la release v1.0.0 sur GitHub !

---

## 📦 Fichiers prêts à uploader

### 1. JAR du Compilateur
**Fichier** : `build/libs/algo-compiler-1.0.0.jar`
**Description** : Compilateur et interpréteur prêt à l'emploi (nécessite Java 21+)

### 2. Extension VS Code
**Fichier** : `algo-compiler-vscode/algo-compiler-1.3.0.vsix`
**Description** : Extension VS Code avec coloration syntaxique et exécution directe

### 3. Notes de Release
**Fichier** : `RELEASE_NOTES.md`
**Description** : Documentation complète de la release à copier dans GitHub

---

## 🎯 Étapes pour créer la release sur GitHub

### Étape 1 : Accéder aux Releases

1. Ouvrez votre navigateur
2. Allez sur : **https://github.com/oughzal/algo-compiler**
3. Cliquez sur **Releases** (dans le menu latéral droit)
4. Cliquez sur **Draft a new release** ou **Create a new release**

### Étape 2 : Configurer la Release

#### Tag
- **Tag version** : `v1.0.0`
- **Target** : `main` (branche principale)
- Le tag a déjà été créé et poussé ✅

#### Titre
```
🎉 Algo-Compiler v1.0.0 - Release Initiale
```

#### Description
Copiez le contenu de `RELEASE_NOTES.md` dans la description de la release.

Ou utilisez cette version courte :

```markdown
## Compilateur de Pseudo-code Algorithmique en Français

Premier release stable avec 40 fonctions intégrées et support complet du type caractère.

### 🎯 Téléchargements
- **algo-compiler-1.0.0.jar** - Compilateur JAR (Java 21+)
- **algo-compiler-1.3.0.vsix** - Extension VS Code

### ✨ Fonctionnalités
- 40 fonctions intégrées (mathématiques, chaînes, caractères, conversions)
- Type `caractere` avec `ord()` et `chr()`
- Syntaxe flexible (accents optionnels, casse insensible)
- Extension VS Code avec coloration et snippets
- Documentation complète + site web

### 🚀 Utilisation
```bash
java -jar algo-compiler-1.0.0.jar votre_fichier.algo
```

### 📚 Documentation
🌐 https://oughzal.github.io/algo-compiler/

**Voir RELEASE_NOTES.md pour les détails complets**
```

### Étape 3 : Uploader les Fichiers

Dans la section **Attach binaries by dropping them here or selecting them** :

1. **Glissez-déposez** ou cliquez pour sélectionner :
   - `build/libs/algo-compiler-1.0.0.jar`
   - `algo-compiler-vscode/algo-compiler-1.3.0.vsix`

2. Attendez que les fichiers soient uploadés (barre de progression)

### Étape 4 : Options supplémentaires

#### ✅ Cocher (optionnel mais recommandé)
- ☑️ **Set as the latest release** - Marquer comme dernière release
- ☐ **Set as a pre-release** - NE PAS cocher (c'est une release stable)
- ☐ **Create a discussion for this release** - Optionnel

#### Discussion (optionnel)
Si vous cochez "Create a discussion", une discussion sera automatiquement créée dans l'onglet Discussions du repo.

### Étape 5 : Publier

1. Vérifiez que tout est correct :
   - ✅ Tag : v1.0.0
   - ✅ Titre : 🎉 Algo-Compiler v1.0.0 - Release Initiale
   - ✅ Description complète
   - ✅ 2 fichiers attachés
   - ✅ "Set as latest release" coché

2. Cliquez sur **Publish release** (bouton vert)

3. La release sera publiée instantanément ! 🎉

---

## 🌐 Après Publication

### Vérification

1. **Page Releases**
   - Allez sur : https://github.com/oughzal/algo-compiler/releases
   - Votre release v1.0.0 doit apparaître en premier
   - Badge "Latest" doit être visible

2. **Téléchargements**
   - Cliquez sur chaque fichier pour vérifier qu'ils se téléchargent correctement
   - Les fichiers doivent avoir les bonnes tailles

3. **Description**
   - Vérifiez que la description s'affiche correctement
   - Les liens doivent être cliquables

### Partage

Partagez la release :
```
🎉 Algo-Compiler v1.0.0 est disponible !
https://github.com/oughzal/algo-compiler/releases/tag/v1.0.0

Compilateur de pseudo-code français avec 40 fonctions intégrées
```

### Mise à jour du README

Ajoutez un badge de release dans le README principal :

```markdown
[![Release](https://img.shields.io/github/v/release/oughzal/algo-compiler)](https://github.com/oughzal/algo-compiler/releases/latest)
[![Downloads](https://img.shields.io/github/downloads/oughzal/algo-compiler/total)](https://github.com/oughzal/algo-compiler/releases)
```

---

## 📊 Statistiques de la Release

### Fichiers
- **JAR** : ~XX MB (compilateur complet)
- **VSIX** : ~25 KB (extension VS Code)
- **Source code (zip)** : Automatique par GitHub
- **Source code (tar.gz)** : Automatique par GitHub

### Contenu
- 40 fonctions intégrées
- 5 types de données
- 30+ exemples
- 10+ documents
- Site web complet

---

## 🔄 Prochaines Releases

### Préparation pour v1.1.0

Pour créer une future release :

1. **Développer les nouvelles fonctionnalités**
2. **Mettre à jour le numéro de version** dans `build.gradle.kts`
3. **Compiler** : `./gradlew build`
4. **Commit** : `git commit -m "Version 1.1.0"`
5. **Tag** : `git tag -a v1.1.0 -m "Version 1.1.0"`
6. **Push** : `git push oughzal main && git push oughzal v1.1.0`
7. **Créer release** sur GitHub avec les nouveaux fichiers

---

## 🐛 Dépannage

### Le tag n'apparaît pas dans la liste

1. Vérifier que le tag a été poussé :
   ```bash
   git tag -l
   git push oughzal v1.0.0
   ```

2. Rafraîchir la page de création de release

### Erreur lors de l'upload des fichiers

1. Vérifier la taille (max 2 GB par fichier)
2. Vérifier le format (ZIP, JAR, VSIX acceptés)
3. Réessayer l'upload
4. Utiliser une connexion stable

### La release n'apparaît pas

1. Vérifier que vous avez cliqué sur "Publish release"
2. Attendre quelques secondes et rafraîchir
3. Vérifier que vous êtes sur le bon repo

---

## 📝 Checklist Finale

Avant de publier :

- [x] Tag v1.0.0 créé et poussé
- [x] Commit principal poussé
- [x] JAR compilé dans build/libs/
- [x] Extension VSIX disponible
- [x] RELEASE_NOTES.md créé
- [ ] Créer la release sur GitHub
- [ ] Uploader les 2 fichiers
- [ ] Publier la release
- [ ] Vérifier les téléchargements
- [ ] Partager l'URL

---

## 🎉 Félicitations !

Une fois publiée, votre release sera accessible à :

```
https://github.com/oughzal/algo-compiler/releases/tag/v1.0.0
```

Les utilisateurs pourront :
- ✅ Télécharger le JAR
- ✅ Télécharger l'extension VS Code
- ✅ Télécharger le code source
- ✅ Lire la documentation
- ✅ Voir les changements

---

**🎊 Prêt à publier la release ! 🎊**

*Suivez les étapes ci-dessus sur GitHub.com*

