# 🚀 Quick Start - Plugin IntelliJ IDEA Algo-Compiler

## ⚡ Démarrage rapide (5 minutes)

### Étape 1 : Build du plugin

```powershell
# Ouvrez un terminal dans le dossier algo-compiler-intellij
cd "C:\Users\Omar\Documents\Algo-compiler\algo-compiler-intellij"

# Build le plugin (première fois : télécharge IntelliJ SDK ~750 MB)
..\gradlew.bat buildPlugin
```

⏱️ **Temps estimé** : 
- Premier build : 10-15 minutes (téléchargement SDK)
- Builds suivants : 1-2 minutes

📦 **Résultat** : `build/distributions/algo-compiler-intellij-1.6.0.zip`

---

### Étape 2 : Installation dans IntelliJ IDEA

1. **Ouvrez IntelliJ IDEA** (Community ou Ultimate 2023.2+)

2. **Accédez aux plugins**
   ```
   File → Settings → Plugins
   ```

3. **Installez le plugin**
   - Cliquez sur ⚙️ (roue dentée en haut)
   - Sélectionnez `Install Plugin from Disk...`
   - Naviguez vers : `algo-compiler-intellij/build/distributions/`
   - Sélectionnez `algo-compiler-intellij-1.6.0.zip`
   - Cliquez sur `OK`

4. **Redémarrez IntelliJ IDEA**
   - Cliquez sur `Restart IDE`

✅ **Le plugin est installé !**

---

### Étape 3 : Configuration

1. **Ouvrez les settings**
   ```
   File → Settings → Tools → Algo Compiler
   ```

2. **Configurez le chemin du JAR**
   - Cliquez sur le bouton `...`
   - Sélectionnez : `C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.6.0.jar`
   - Cliquez sur `OK`

3. **Vérifiez Java** (optionnel)
   - Par défaut : `java`
   - Si besoin, spécifiez : `C:\Program Files\Java\jdk-21\bin\java.exe`

4. **Appliquez**
   - Cliquez sur `Apply` puis `OK`

✅ **Configuration terminée !**

---

### Étape 4 : Premier test

1. **Créez un fichier**
   ```
   File → New → File
   ```
   Nommez-le : `premier_test.algo`

2. **Écrivez du code**
   ```algo
   algorithme PremierTest
   variables
       nom : chaine
       age : entier
   
   debut
       ecrire("Votre nom: ")
       lire(nom)
       ecrire("Votre âge: ")
       lire(age)
       
       ecrireln("Bonjour ", nom, " !")
       ecrireln("Vous avez ", age, " ans.")
       
       // Test des fonctions
       ecrireln("En majuscules: ", majuscule(nom))
       ecrireln("Longueur du nom: ", longueur(nom))
   fin
   ```

3. **Testez les fonctionnalités**

   **🎨 Coloration**
   - Les mots-clés doivent être en gras et colorés
   - Les types doivent être colorés différemment
   - Les chaînes et nombres colorés

   **💡 Autocomplétion**
   - Tapez `si` puis `Ctrl+Space` → voir suggestions
   - Tapez `abs` puis `Ctrl+Space` → `abs()` avec curseur entre ()
   - Essayez : `pour`, `tantque`, `racine`, `majuscule`

   **📝 Commentaires**
   - Sélectionnez une ligne
   - Appuyez sur `Ctrl+/` → commentaire ajouté
   - Réappuyez → commentaire retiré

4. **Exécutez**
   - Appuyez sur `Ctrl+Shift+F10`
   - Ou clic droit → `Run 'premier_test'`

5. **Vérifiez la sortie**
   - La console s'ouvre en bas
   - Entrez les valeurs demandées
   - Vérifiez le résultat

✅ **Tout fonctionne !**

---

## 🎯 Fonctionnalités à explorer

### 1. Autocomplétion des fonctions

Tapez le début d'une fonction et appuyez sur `Ctrl+Space` :

```algo
abs        → abs()         # Valeur absolue
rac        → racine()      # Racine carrée
maj        → majuscule()   # Convertir en majuscules
lon        → longueur()    # Longueur d'une chaîne
ord        → ord()         # Code ASCII d'un caractère
chr        → chr()         # Caractère depuis code ASCII
ale        → aleatoire()   # Nombre aléatoire
```

### 2. Snippets de structure

Tapez un mot-clé et `Ctrl+Space` :

```algo
si         → si ... alors ... sinon ... finsi
pour       → pour ... de ... à ... faire ... finpour
tantque    → tantque ... faire ... fintantque
selon      → selon ... cas ... finselon
```

### 3. Commentaires rapides

```algo
// Une ligne        → Ctrl+/
/* Plusieurs        → Sélectionner + Ctrl+Shift+/
   lignes */
```

### 4. Navigation

- `Ctrl+Click` sur une variable → voir sa déclaration
- `Ctrl+F` → rechercher dans le fichier
- `Ctrl+H` → rechercher et remplacer

---

## 🔧 Dépannage rapide

### ❌ "Chemin du compilateur non configuré"
→ `Settings → Tools → Algo Compiler` → définir le chemin du JAR

### ❌ Plugin n'apparaît pas
→ Vérifier : `Settings → Plugins` → "Algo Compiler" activé
→ Redémarrer IntelliJ IDEA

### ❌ Pas de coloration syntaxique
→ Fermer/rouvrir le fichier .algo
→ Clic droit → `Associate with File Type...` → "Algo"

### ❌ Erreur lors de l'exécution
→ Vérifier que Java 21+ est installé : `java -version`
→ Vérifier le chemin du JAR dans les settings
→ Vérifier que le fichier .algo est valide

---

## 📚 Ressources

- **Documentation** : [INSTALLATION.md](INSTALLATION.md) (guide complet)
- **README** : [README.md](README.md) (toutes les fonctionnalités)
- **Site web** : https://oughzal.github.io/Algo-compiler/
- **Issues** : https://github.com/oughzal/Algo-compiler/issues

---

## ✅ Checklist de validation

Après installation, vérifiez :

- [ ] Le plugin apparaît dans `Settings → Plugins`
- [ ] Les fichiers .algo ont une icône spéciale
- [ ] La coloration syntaxique fonctionne
- [ ] L'autocomplétion propose des mots-clés
- [ ] `Ctrl+/` commente une ligne
- [ ] `Ctrl+Shift+F10` exécute le fichier
- [ ] La console affiche la sortie correctement
- [ ] Les fonctions avec parenthèses s'autocomplètent

---

## 🎉 Bravo !

Vous êtes maintenant prêt à utiliser Algo-Compiler dans IntelliJ IDEA !

**Prochaines étapes :**
- Explorez les 40+ fonctions intégrées
- Testez les tableaux et matrices
- Créez vos propres algorithmes
- Partagez avec vos étudiants/collègues

**Besoin d'aide ?**
- Consultez [HELP.md](../HELP.md) pour la syntaxe complète
- Ouvrez une issue sur GitHub pour les bugs
- Contribuez au projet !

---

© 2024 Omar OUGHZAL - MIT License

