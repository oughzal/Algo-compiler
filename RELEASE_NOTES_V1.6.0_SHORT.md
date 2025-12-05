# 🚀 Algo-Compiler v1.6.0

## 🎉 Nouveautés Majeures

### 📊 Matrices (Tableaux 2D)
Support complet des tableaux à deux dimensions !

```algo
variables
    M : tableau[3][3] de entier

debut
    M[i][j] = valeur
    x = M[i][j]
fin
```

**[📖 Documentation Matrices](SUPPORT_MATRICES.md)**

### ⚡ Opérateur Puissance ^
Nouvel alias pour l'opérateur de puissance :

```algo
resultat = 2 ^ 3      // 8
aire = rayon ^ 2      // Plus concis !
```

Les deux opérateurs (`^` et `**`) fonctionnent.

## 🔧 Améliorations

### Extension VS Code v1.6.0
- ✅ **50+ snippets** optimisés sans commentaires
- ✅ Fonctions simplifiées : `nom()` au lieu de `nom(param1, param2)`
- ✅ 4 nouveaux snippets : `matrice`, `parcmat`, `affmat`, `puis`

### Site Web
- ✅ 100% responsive (4 breakpoints)
- ✅ Optimisé mobile

## 📊 Fonctionnalités Complètes

- **5 types** : entier, reel, chaine, caractere, booleen
- **Matrices 2D** ✨ NOUVEAU
- **40+ fonctions** intégrées
- **Opérateur ^** ✨ NOUVEAU
- Expression conditionnelle : `x = si cond alors val1 sinon val2`
- Clause `sinonSi`
- Initialisation variables : `x : entier = 5`
- Tableaux littéraux : `T = [1, 2, 3]`

## 💾 Installation

### Compilateur
Téléchargez `algo-compiler-1.0.0.jar` depuis cette release.

### Extension VS Code
- **VS Marketplace** : https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler
- **Ou** téléchargez `algo-compiler-1.6.0.vsix` ci-dessous

## 📚 Documentation

- [README.md](README.md) - Guide complet
- [HELP.md](HELP.md) - Aide complète (1800+ lignes)
- [SUPPORT_MATRICES.md](SUPPORT_MATRICES.md) - Documentation matrices
- [QUICKSTART.md](QUICKSTART.md) - Démarrage rapide
- [Site Web](https://oughzal.github.io/Algo-compiler/)

## 🎯 Exemples

### Matrice Simple
```algo
algorithme MatriceSimple

variables
    M : tableau[3][3] de entier
    i, j : entier

debut
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            M[i][j] = (i + 1) * 10 + (j + 1)
        finpour
    finpour
    
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            ecrire(M[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

**Sortie** :
```
11 12 13
21 22 23
31 32 33
```

### Opérateur ^
```algo
algorithme Puissance

debut
    ecrireln(2 ^ 3)        // 8
    ecrireln(rayon ^ 2)    // Aire
    ecrireln(cote ^ 3)     // Volume
fin
```

## 🔄 Compatibilité

✅ **100% rétrocompatible** avec v1.4.0  
✅ Aucune modification de code nécessaire  
✅ Nouvelles fonctionnalités optionnelles  

## 📈 Statistiques

- **Code** : ~100 lignes Kotlin
- **Documentation** : 1300+ lignes
- **Extension** : 34.2 KB, 16 fichiers
- **Snippets** : 50+
- **Exemples** : 30+

## 🐛 Corrections

- ✅ Parser optimisé pour matrices
- ✅ Vérification des limites
- ✅ Messages d'erreur améliorés

## 🔗 Liens

- **Site** : https://oughzal.github.io/Algo-compiler/
- **GitHub** : https://github.com/oughzal/Algo-compiler
- **Issues** : https://github.com/oughzal/Algo-compiler/issues

## 📄 Licence

MIT - © 2024 Omar OUGHZAL

---

**Développé avec ❤️ pour l'apprentissage de l'algorithmique en français** 🇫🇷

