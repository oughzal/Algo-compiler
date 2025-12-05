# 🚀 Algo-Compiler v1.6.0 - Release Notes

**Date de sortie** : 5 Décembre 2024  
**Auteur** : Omar OUGHZAL

---

## 🎉 Nouveautés Majeures

### 📊 Matrices (Tableaux 2D)

Support complet des tableaux à deux dimensions !

**Syntaxe** :
```algo
variables
    M : tableau[3][3] de entier
    notes : tableau[5][10] de reel
```

**Utilisation** :
```algo
// Initialisation
M[0][0] = 10
M[i][j] = valeur

// Lecture
x = M[i][j]

// Parcours
pour i de 0 à 2 faire
    pour j de 0 à 2 faire
        ecrire(M[i][j], " ")
    finpour
    ecrireln()
finpour
```

**Fonctionnalités** :
- ✅ Déclaration `tableau[lignes][colonnes]`
- ✅ Accès `M[i][j]`
- ✅ Tous types supportés (entier, reel, chaine, caractere, booleen)
- ✅ Initialisation automatique
- ✅ Vérification des limites

**Documentation** : [SUPPORT_MATRICES.md](SUPPORT_MATRICES.md)

---

### ⚡ Opérateur Puissance ^

Nouvel alias pour l'opérateur de puissance !

**Syntaxe** :
```algo
resultat = 2 ^ 3      // 8
aire = rayon ^ 2      // Aire d'un cercle
volume = cote ^ 3     // Volume d'un cube
```

**Les deux opérateurs fonctionnent** :
- `**` (original) : `2 ** 3` = 8
- `^` (nouveau) : `2 ^ 3` = 8

**Avantages** :
- ✅ Plus concis (1 caractère vs 2)
- ✅ Syntaxe familière (Excel, maths)
- ✅ 100% compatible avec l'ancien code

**Documentation** : [OPERATEUR_PUISSANCE_ALIAS.md](OPERATEUR_PUISSANCE_ALIAS.md)

---

## 🔧 Améliorations

### Extension VS Code v1.6.0

**Snippets Optimisés** :
- ✅ **Code propre** : Suppression de tous les commentaires générés
- ✅ **Fonctions simplifiées** : Génèrent `nom()` au lieu de `nom(param1, param2)`
- ✅ **50+ snippets** disponibles
- ✅ Navigation optimisée avec curseur positionné automatiquement

**Nouveaux Snippets** :
- `matrice` → Déclarer une matrice
- `parcmat` → Parcourir une matrice
- `affmat` → Afficher une matrice
- `puis` → Opérateur puissance ^

**Exemple avant/après** :
```algo
// Avant (v1.4.0)
si condition alors
    // Code    ← Commentaire à supprimer
finsi

// Après (v1.6.0) ✅
si condition alors
    |    ← Curseur prêt, code propre
finsi
```

---

### Site Web Responsive

**4 Breakpoints ajoutés** :
- ✅ 1024px (Tablettes)
- ✅ 768px (Petits écrans)
- ✅ 480px (Mobiles)
- ✅ 320px (Très petits écrans)

**Optimisations Mobile** :
- ✅ Navigation adaptative
- ✅ Images responsive
- ✅ Boutons optimisés
- ✅ Code lisible avec scroll

**URL** : [https://oughzal.github.io/Algo-compiler/](https://oughzal.github.io/Algo-compiler/)

---

## 📚 Documentation Enrichie

### Nouveaux Guides
- **HELP.md** : Guide complet 1800+ lignes
- **SUPPORT_MATRICES.md** : Documentation matrices (550+ lignes, 8 exemples)
- **OPERATEUR_PUISSANCE_ALIAS.md** : Guide opérateur ^

### Exemples Ajoutés
- `test_matrices.algo` - Test de base matrices 3x3
- `test_operations_matrices.algo` - Addition de matrices
- `test_table_multiplication.algo` - Table 10x10

---

## 📊 Fonctionnalités Complètes

### Types de Données (5)
- `entier`, `reel`, `chaine`, `caractere`, `booleen`

### Structures de Données
- Variables simples
- Constantes
- Tableaux 1D
- **Matrices 2D** ✨ NOUVEAU

### Opérateurs
- **Arithmétiques** : `+`, `-`, `*`, `/`, `div`, `mod`, `**`, `^` ✨
- **Comparaison** : `=`, `<>`, `!=`, `<`, `>`, `<=`, `>=`
- **Logiques** : `et`, `ou`, `non`

### Instructions de Contrôle
- `si...alors...sinonSi...sinon...finsi`
- Expression conditionnelle : `variable = si cond alors val1 sinon val2`
- `pour...de...à...faire...finpour`
- `tantque...faire...fintantque`
- `repeter...jusqua`
- `selon...cas...defaut...finselon`

### Fonctions Intégrées (40+)
- **Mathématiques** (15) : abs, min, max, racine, puissance, sin, cos, tan, etc.
- **Chaînes** (9) : longueur, majuscule, minuscule, contient, position, etc.
- **Caractères** (8) : ord, chr, estLettre, estChiffre, versMajuscule, etc.
- **Conversions** (3) : versEntier, versReel, versChaine
- **Aléatoires** (3) : aleatoire(), aleatoire(max), aleatoire(min, max)

---

## 🎯 Exemples d'Utilisation

### Exemple 1 : Matrice Simple
```algo
algorithme MatriceSimple

variables
    M : tableau[3][3] de entier
    i, j : entier

debut
    // Initialisation
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            M[i][j] = (i + 1) * 10 + (j + 1)
        finpour
    finpour
    
    // Affichage
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

### Exemple 2 : Addition de Matrices
```algo
algorithme AdditionMatrices

variables
    A, B, C : tableau[2][2] de entier
    i, j : entier

debut
    // Initialiser A et B
    A[0][0] = 1
    A[0][1] = 2
    A[1][0] = 3
    A[1][1] = 4
    
    B[0][0] = 5
    B[0][1] = 6
    B[1][0] = 7
    B[1][1] = 8
    
    // C = A + B
    pour i de 0 à 1 faire
        pour j de 0 à 1 faire
            C[i][j] = A[i][j] + B[i][j]
        finpour
    finpour
    
    // Afficher C
    ecrireln("C = A + B :")
    pour i de 0 à 1 faire
        pour j de 0 à 1 faire
            ecrire(C[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

### Exemple 3 : Opérateur ^
```algo
algorithme OperateurPuissance

variables
    x, y, resultat : entier
    rayon, aire : reel

debut
    // Avec ^
    x = 2
    y = 3
    resultat = x ^ y
    ecrireln("2^3 = ", resultat)  // 8
    
    // Aire d'un cercle
    rayon = 5.0
    aire = 3.14159 * rayon ^ 2
    ecrireln("Aire = ", aire)  // 78.53975
    
    // Les deux syntaxes fonctionnent
    ecrireln("2 ** 3 = ", 2 ** 3)  // 8
    ecrireln("2 ^ 3 = ", 2 ^ 3)    // 8
fin
```

---

## 💾 Installation

### Prérequis
- **Java 21 ou supérieur**
- **VS Code** (recommandé)

### Méthode 1 : Installation Rapide

1. **Télécharger le compilateur**
   - Allez sur [GitHub Releases](https://github.com/oughzal/Algo-compiler/releases/latest)
   - Téléchargez `algo-compiler-1.0.0.jar`
   - Placez-le dans `C:\Program Files\algo-compiler\`

2. **Installer l'extension VS Code**
   - **Option A** : [VS Marketplace](https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler)
   - **Option B** : Télécharger `algo-compiler-1.6.0.vsix` depuis Releases
   - Dans VS Code : Extensions → ⋯ → Install from VSIX

3. **Configurer l'extension**
   - Settings (Ctrl+,) → Rechercher "Algo Compiler"
   - Compiler Path : `C:\Program Files\algo-compiler\algo-compiler-1.0.0.jar`
   - Java Path : `C:\Program Files\Java\jdk-21\bin`

### Méthode 2 : Depuis les Sources

```bash
# Cloner le repo
git clone https://github.com/oughzal/Algo-compiler.git
cd Algo-compiler

# Compiler
./gradlew build

# Le JAR sera dans build/libs/
```

---

## 🚀 Utilisation

### Créer un Algorithme

1. **Créer** un fichier `.algo`
2. **Taper** `algo` + Tab (template complet)
3. **Écrire** votre algorithme
4. **Exécuter** : Ctrl+Shift+R

### Snippets Utiles

| Snippet | Description |
|---------|-------------|
| `algo` | Structure complète |
| `matrice` | Déclarer matrice ✨ |
| `parcmat` | Parcourir matrice ✨ |
| `affmat` | Afficher matrice ✨ |
| `puis` | Opérateur ^ ✨ |
| `si` | Structure si |
| `sinonsi` | Si avec sinonSi |
| `pour` | Boucle pour |
| `tableau` | Déclarer tableau |
| `fonction` | Définir fonction |

---

## 📈 Statistiques

### Code
- **Lignes ajoutées** : ~100 lignes Kotlin
- **Fichiers modifiés** : 3 (AST, Parser, Interpreter)
- **Compilation** : ✅ BUILD SUCCESSFUL

### Documentation
- **Nouveaux guides** : 3 fichiers (1050+ lignes)
- **Exemples** : 3 fichiers matrices
- **Total** : ~1300 lignes

### Extension VS Code
- **Version** : 1.6.0
- **Snippets** : 50+
- **Taille** : 34.2 KB
- **Fichiers** : 16

---

## 🐛 Corrections

### Bugs Corrigés
- ✅ Parser corrigé pour matrices
- ✅ Initialisation correcte des tableaux 2D
- ✅ Vérification des limites pour matrices

### Améliorations
- ✅ Snippets sans commentaires
- ✅ Fonctions simplifiées
- ✅ Site web responsive
- ✅ Messages d'erreur plus clairs

---

## 🔄 Compatibilité

### Rétrocompatibilité
- ✅ **100% compatible** avec le code v1.4.0
- ✅ L'opérateur `**` fonctionne toujours
- ✅ Tous les anciens programmes fonctionnent

### Versions Supportées
- **Kotlin** : 1.9.20
- **Java** : 21+
- **VS Code** : 1.75.0+
- **OS** : Windows, Linux, macOS

---

## 📝 Migration depuis v1.4.0

### Aucune Action Requise !
- ✅ Votre code existant fonctionne sans modification
- ✅ Les nouvelles fonctionnalités sont optionnelles
- ✅ Pas de breaking changes

### Pour Utiliser les Nouvelles Fonctionnalités

**Matrices** :
```algo
// Ancien code (fonctionne toujours)
variables
    tab : tableau[10] de entier

// Nouveau code (optionnel)
variables
    mat : tableau[3][3] de entier
```

**Opérateur ^** :
```algo
// Ancien code (fonctionne toujours)
resultat = 2 ** 3

// Nouveau code (optionnel)
resultat = 2 ^ 3  // Plus concis
```

---

## 🔗 Liens Utiles

### Téléchargements
- **Releases** : https://github.com/oughzal/Algo-compiler/releases
- **VS Marketplace** : https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler
- **Site Web** : https://oughzal.github.io/Algo-compiler/

### Documentation
- **Guide Complet** : [README.md](README.md)
- **Aide** : [HELP.md](HELP.md)
- **Démarrage Rapide** : [QUICKSTART.md](QUICKSTART.md)
- **Matrices** : [SUPPORT_MATRICES.md](SUPPORT_MATRICES.md)
- **40+ Fonctions** : [FONCTIONS_INTEGREES_COURANTES.md](FONCTIONS_INTEGREES_COURANTES.md)

### Support
- **Issues** : https://github.com/oughzal/Algo-compiler/issues
- **Discussions** : https://github.com/oughzal/Algo-compiler/discussions

---

## 👥 Contributeurs

**Développeur Principal** : Omar OUGHZAL

---

## 📄 Licence

MIT License - © 2024 Omar OUGHZAL - Tous droits réservés

---

## 🎉 Remerciements

Merci à tous les utilisateurs et contributeurs qui ont fait d'Algo-Compiler un outil d'apprentissage apprécié !

Développé avec ❤️ pour faciliter l'apprentissage de l'algorithmique en français.

---

## 🚀 Prochaines Versions

### En Développement
- Tableaux 3D
- Fonctions matricielles avancées
- Débogueur intégré
- Plus de snippets

### Suggestions ?
Ouvrez une [Issue](https://github.com/oughzal/Algo-compiler/issues) ou participez aux [Discussions](https://github.com/oughzal/Algo-compiler/discussions) !

---

**Algo-Compiler v1.6.0** - Pour un apprentissage moderne de l'algorithmique en français ! 🇫🇷

**Date** : 5 Décembre 2024  
**Version** : 1.6.0  
**Auteur** : Omar OUGHZAL

