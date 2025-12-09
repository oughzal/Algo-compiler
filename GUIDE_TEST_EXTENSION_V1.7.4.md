# Guide de test - Extension VSCode 1.7.4

## 🎯 Objectif du test
Vérifier que l'extension n'affiche plus les commandes lors de l'exécution.

## 📋 Étapes de test

### 1. Vérifier l'installation
Dans VSCode :
- Allez dans Extensions (Ctrl+Shift+X)
- Recherchez "Algo Compiler"
- Vérifiez que la version est **1.7.4**

### 2. Ouvrir un fichier de test
Ouvrez le fichier `C:\Users\Omar\algo\test-echo.algo` :

```algo
Algorithme TestEcho
Debut
    ecrire("Bonjour depuis l'algorithme!")
Fin
```

### 3. Exécuter l'algorithme
- Appuyez sur **F5** (ou clic droit → "Algo: Exécuter")
- Un terminal nommé "Algo Compiler" devrait s'ouvrir

### 4. Vérifier le résultat

#### ✅ Comportement attendu (v1.7.4)
```
Bonjour depuis l'algorithme!
```

#### ❌ Ancien comportement (v1.7.0)
```
▶ Exécution de: test-echo.algo
java -jar "C:\Users\Omar\...\algo-compiler-1.6.0.jar" "test-echo.algo"
Bonjour depuis l'algorithme!
```

## 🧪 Tests supplémentaires

### Test avec variables
Fichier : `test-simple.algo`

```algo
Algorithme TestSimple
Variables
    x, y : entier
    resultat : entier
Debut
    x = 10
    y = 5
    resultat = x + y
    ecrire("Le résultat de ", x, " + ", y, " est : ", resultat)
Fin
```

**Résultat attendu :**
```
Le résultat de 10 + 5 est : 15
```

### Test avec entrée utilisateur
```algo
Algorithme TestLecture
Variables
    nom : chaine
Debut
    ecrire("Entrez votre nom : ")
    lire(nom)
    ecrire("Bonjour ", nom, " !")
Fin
```

**Résultat attendu :**
```
Entrez votre nom : 
[utilisateur tape son nom]
Bonjour [nom] !
```

## 🔍 Points à vérifier

✅ Aucune ligne de commande Java visible  
✅ Pas de message "▶ Exécution de: ..."  
✅ Seule la sortie du programme est affichée  
✅ Le terminal s'appelle "Algo Compiler"  
✅ Pas de fichiers .bat visibles dans le dossier temporaire après exécution  

## 🐛 En cas de problème

Si les commandes s'affichent toujours :
1. Fermez complètement VSCode
2. Rouvrez VSCode
3. Vérifiez la version de l'extension (doit être 1.7.4)
4. Réessayez

Si l'erreur "jarfile not found" apparaît :
1. Vérifiez que le compilateur est construit : `gradlew build`
2. Vérifiez le paramètre `algoCompiler.compilerPath` dans les settings VSCode

## 📞 Support

En cas de problème persistant, vérifiez :
- Le fichier `MODIFICATION_EXTENSION_V1.7.4_FINALE.md` pour les détails techniques
- Les logs du terminal VSCode
- La console de développement VSCode (Aide → Basculer les outils de développement)

