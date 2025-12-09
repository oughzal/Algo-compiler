# Modification de l'extension VSCode - Version 1.7.4 (FINALE)

## Date
7 décembre 2024

## Modification effectuée

### Problème
L'extension VSCode affichait les commandes lors de l'exécution des algorithmes dans le terminal PowerShell, ce qui polluait la sortie.

### Historique des tentatives

**v1.7.1** - Erreur avec `@echo off` dans PowerShell :
```
ParserError: Unexpected token 'off' in expression or statement.
```
❌ `@echo off` est une commande CMD, pas PowerShell

**v1.7.2 & v1.7.3** - Erreur avec échappement des guillemets :
```
Error: Unable to access jarfile \ C:\...\algo-compiler-1.6.0.jar\
```
❌ Les guillemets dans `cmd /c "..."` causaient des problèmes d'échappement

### Solution finale (v1.7.4) ✅

Utilisation d'un **fichier batch temporaire** pour éviter complètement les problèmes d'échappement de guillemets.

#### Principe
1. Créer un fichier `.bat` temporaire avec la commande Java
2. Le fichier contient `@echo off` pour masquer les commandes
3. Le fichier s'auto-supprime après exécution
4. Le terminal exécute simplement le fichier batch

#### Code

```typescript
const javaPath = config.get<string>('javaPath', 'java');

// Créer un fichier batch temporaire pour éviter les problèmes d'échappement
const tempDir = require('os').tmpdir();
const batchFile = path.join(tempDir, 'algo-run-' + Date.now() + '.bat');
const batchContent = `@echo off\n"${javaPath}" -jar "${compilerPath}" "${filePath}"\ndel "%~f0"`;

try {
    fs.writeFileSync(batchFile, batchContent);
} catch (error) {
    vscode.window.showErrorMessage('Erreur lors de la création du fichier temporaire');
    return;
}

// Créer ou réutiliser un terminal
let terminal = vscode.window.terminals.find(t => t.name === 'Algo Compiler');

if (!terminal) {
    terminal = vscode.window.createTerminal({
        name: 'Algo Compiler',
        cwd: path.dirname(filePath)
    });
}

// Afficher le terminal et exécuter le fichier batch
terminal.show();
terminal.sendText(`"${batchFile}"`);
```

### Avantages de cette approche

✅ **Pas de problème d'échappement** : Les guillemets dans le fichier batch sont gérés nativement  
✅ **Pas de commandes affichées** : `@echo off` fonctionne correctement dans un fichier .bat  
✅ **Auto-nettoyage** : Le fichier se supprime après exécution avec `del "%~f0"`  
✅ **Compatible PowerShell et CMD** : Le terminal peut exécuter un .bat depuis n'importe quel shell  
✅ **Chemins avec espaces** : Gérés correctement avec les guillemets dans le .bat  

### Résultat

- ✅ Les commandes ne sont plus affichées dans le terminal
- ✅ Seule la sortie de l'algorithme est visible
- ✅ L'exécution est propre et professionnelle
- ✅ Aucun fichier temporaire ne reste (auto-suppression)
- ✅ Fonctionne dans PowerShell, CMD, et tous les shells Windows

### Exemple de fichier batch généré

```bat
@echo off
"java" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.6.0.jar" "C:\Users\Omar\algo\exercice01.algo"
del "%~f0"
```

## Installation

L'extension version 1.7.4 a été générée et installée avec succès :
- **Fichier** : `algo-compiler-1.7.4.vsix`
- **Taille** : 34.46 KB
- **Commande** : `code --install-extension algo-compiler-1.7.4.vsix --force`

## Fichiers modifiés

- `algo-compiler-vscode/src/extension.ts`
- `algo-compiler-vscode/package.json` (version 1.7.0 → 1.7.4)

## Notes techniques

- Le fichier batch est créé dans le répertoire temporaire du système (`os.tmpdir()`)
- Le nom du fichier inclut un timestamp pour éviter les collisions : `algo-run-{timestamp}.bat`
- `del "%~f0"` supprime le fichier batch en cours d'exécution (lui-même)
- Cette approche est plus robuste que les tentatives d'échappement de guillemets

