# Extension VSCode Algo Compiler v1.7.4 - Installation et test

## 🚀 Installation rapide

```bash
cd C:\Users\Omar\Documents\Algo-compiler\algo-compiler-vscode
code --install-extension algo-compiler-1.7.4.vsix --force
```

## ✅ Vérification

Dans VSCode :
1. Extensions (Ctrl+Shift+X)
2. Rechercher "Algo Compiler"
3. Vérifier version **1.7.4**

## 🧪 Test rapide

1. Ouvrir `C:\Users\Omar\algo\test-echo.algo`
2. Appuyer sur **F5**
3. Vérifier : Seul "Bonjour depuis l'algorithme!" doit apparaître

## ✨ Nouveauté v1.7.4

**Les commandes ne s'affichent plus !**

### Avant
```
▶ Exécution de: test-echo.algo
java -jar "..." "..."
Bonjour depuis l'algorithme!
```

### Maintenant
```
Bonjour depuis l'algorithme!
```

## 📚 Documentation

- `VALIDATION_FINALE_V1.7.4.md` - Validation complète
- `MODIFICATION_EXTENSION_V1.7.4_FINALE.md` - Détails techniques
- `GUIDE_TEST_EXTENSION_V1.7.4.md` - Guide de test complet

## ⚙️ Configuration

Dans les paramètres VSCode (Ctrl+,) :
- `algoCompiler.compilerPath` : Chemin du JAR
- `algoCompiler.javaPath` : Chemin de Java (défaut: "java")

## 🎯 Commandes

- **F5** : Exécuter l'algorithme
- **Ctrl+Shift+P** → "Algo: Exécuter"
- **Ctrl+Shift+P** → "Algo: Compiler"

---

**Version** : 1.7.4  
**Date** : 7 décembre 2024  
**Statut** : ✅ Installée et opérationnelle

