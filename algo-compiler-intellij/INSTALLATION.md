# Guide d'installation - Plugin IntelliJ IDEA Algo-Compiler

## Prérequis

✅ IntelliJ IDEA 2023.2 ou supérieur (Community ou Ultimate)  
✅ Java 21 ou supérieur  
✅ Fichier `algo-compiler-1.6.0.jar` téléchargé

## Étape 1 : Build du plugin

```bash
# Naviguez vers le dossier du plugin
cd algo-compiler-intellij

# Windows
..\gradlew.bat buildPlugin

# Linux/Mac
../gradlew buildPlugin
```

Le plugin sera généré dans : `build/distributions/algo-compiler-intellij-1.6.0.zip`

## Étape 2 : Installation dans IntelliJ IDEA

1. **Ouvrez IntelliJ IDEA**

2. **Accédez aux paramètres des plugins**
   - Windows/Linux : `File` → `Settings` → `Plugins`
   - Mac : `IntelliJ IDEA` → `Preferences` → `Plugins`

3. **Installez le plugin**
   - Cliquez sur l'icône ⚙️ (engrenage) en haut
   - Sélectionnez `Install Plugin from Disk...`
   - Naviguez vers `algo-compiler-intellij/build/distributions/`
   - Sélectionnez `algo-compiler-intellij-1.6.0.zip`
   - Cliquez sur `OK`

4. **Redémarrez IntelliJ IDEA**
   - Cliquez sur `Restart IDE` quand demandé

## Étape 3 : Configuration du compilateur

1. **Ouvrez les paramètres**
   - `File` → `Settings` → `Tools` → `Algo Compiler`

2. **Configurez le chemin du JAR**
   - Cliquez sur le bouton `...` à côté de "Chemin du compilateur JAR"
   - Naviguez vers votre fichier `algo-compiler-1.6.0.jar`
   - Exemple : `C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.6.0.jar`
   - Cliquez sur `OK`

3. **Vérifiez la commande Java** (optionnel)
   - Par défaut : `java`
   - Si Java n'est pas dans le PATH, spécifiez le chemin complet
   - Exemple : `C:\Program Files\Java\jdk-21\bin\java.exe`

4. **Appliquez les changements**
   - Cliquez sur `Apply` puis `OK`

## Étape 4 : Testez l'installation

1. **Créez un nouveau fichier**
   - `File` → `New` → `File`
   - Nommez-le `test.algo`

2. **Écrivez un programme simple**
   ```algo
   algorithme Test
   variables
   
   debut
       ecrireln("Hello from IntelliJ IDEA!")
   fin
   ```

3. **Exécutez le fichier**
   - Appuyez sur `Ctrl+Shift+F10` (Windows/Linux)
   - Ou clic droit → `Run 'test'`

4. **Vérifiez la sortie**
   - La console devrait afficher : `Hello from IntelliJ IDEA!`

## Fonctionnalités à tester

### ✅ Coloration syntaxique
- Les mots-clés (`algorithme`, `debut`, `fin`, etc.) doivent être en gras
- Les types (`entier`, `reel`, etc.) doivent être colorés
- Les chaînes et nombres doivent être colorés différemment

### ✅ Autocomplétion
- Tapez `si` puis `Ctrl+Space` → suggestions avec `si`, `sinon`, `sinonsi`
- Tapez `pour` puis `Ctrl+Space` → suggestion complète
- Tapez `abs` puis `Ctrl+Space` → fonction avec parenthèses

### ✅ Commentaires
- Sélectionnez une ligne et appuyez sur `Ctrl+/` → commentaire avec `//`
- Fonctionne aussi avec sélection multiple

### ✅ Exécution
- `Ctrl+Shift+F10` → exécute le fichier courant
- Menu `Run` → plusieurs options d'exécution

## Dépannage

### ❌ "Chemin du compilateur non configuré"
**Solution** : Allez dans `Settings` → `Tools` → `Algo Compiler` et définissez le chemin du JAR

### ❌ "java: command not found"
**Solution** : 
1. Vérifiez que Java 21+ est installé : `java -version`
2. Ajoutez Java au PATH système
3. Ou spécifiez le chemin complet dans les settings

### ❌ Le plugin n'apparaît pas après installation
**Solution** : 
1. Vérifiez que vous avez bien redémarré IntelliJ IDEA
2. Vérifiez dans `Settings` → `Plugins` que "Algo Compiler" est activé
3. Réinstallez le plugin si nécessaire

### ❌ Erreur "sinceBuild/untilBuild"
**Solution** : Votre version d'IntelliJ IDEA est trop ancienne ou trop récente
- Mettez à jour vers IntelliJ IDEA 2023.2 ou supérieur
- Ou modifiez `build.gradle.kts` : `sinceBuild` et `untilBuild`

### ❌ Les fichiers .algo ne sont pas reconnus
**Solution** : 
1. Fermez et rouvrez le fichier
2. Clic droit sur le fichier → `Associate with File Type...` → "Algo"

## Désinstallation

1. `File` → `Settings` → `Plugins`
2. Recherchez "Algo Compiler"
3. Cliquez sur `Uninstall`
4. Redémarrez IntelliJ IDEA

## Support

- 📖 Documentation complète : https://oughzal.github.io/Algo-compiler/
- 🐛 Signaler un bug : https://github.com/oughzal/Algo-compiler/issues
- 💬 Poser une question : https://github.com/oughzal/Algo-compiler/discussions

---

**Développé avec ❤️ par Omar OUGHZAL**  
© 2024 - Licence MIT

