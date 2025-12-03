# 🎉 Algo-Compiler v1.0.0 - Release Initiale

## Compilateur de Pseudo-code Algorithmique en Français

Premier release stable du compilateur Algo-Compiler avec 40 fonctions intégrées et support complet du type caractère.

---

## 📦 Téléchargements

### Compilateur JAR
- **algo-compiler-1.0.0.jar** - Compilateur et interpréteur prêt à l'emploi
  - Nécessite Java 21+
  - Exécution : `java -jar algo-compiler-1.0.0.jar votre_fichier.algo`

### Extension VS Code
- **algo-compiler-1.3.0.vsix** - Extension VS Code avec coloration syntaxique et exécution directe
  - Installation : Dans VS Code → Extensions → Install from VSIX

### Code source
- **Source code (zip)** - Code source complet du projet
- **Source code (tar.gz)** - Code source complet du projet

---

## ✨ Fonctionnalités principales

### 🔤 Type Caractère
- Support complet du type `caractere`
- Littéraux avec échappements : `'a'`, `'\n'`, `'\t'`, `'\''`, `'\\'`
- Tableaux de caractères
- Support des apostrophes typographiques (conversion automatique)
- Lecture, écriture, comparaisons

### 🧮 40 Fonctions Intégrées

#### Mathématiques (15)
- `abs(x)`, `min(a, b)`, `max(a, b)`
- `racine(x)`, `puissance(x, y)`
- `arrondi(x)`, `plancher(x)`, `plafond(x)`
- `reste(a, b)`, `quotient(a, b)`
- `sin(x)`, `cos(x)`, `tan(x)`, `log(x)`, `exp(x)`

#### Chaînes (9)
- `longueur(s)`, `majuscule(s)`, `minuscule(s)`, `sousChaine(s, debut, fin)`
- `estVide(s)`, `contient(s, sous_chaine)`
- `commence(s, prefixe)`, `termine(s, suffixe)`
- `position(s, sous_chaine)`

#### Caractères (8)
- `ord(c)` - Caractère → Code ASCII
- `chr(code)` - Code ASCII → Caractère
- `estLettre(c)`, `estChiffre(c)`
- `estMajuscule(c)`, `estMinuscule(c)`
- `versMajuscule(c)`, `versMinuscule(c)`

#### Conversions (3)
- `versEntier(x)`, `versReel(x)`, `versChaine(x)`

#### Aléatoires (3 variantes)
- `aleatoire()` - Entre 0.0 et 1.0
- `aleatoire(max)` - Entre 0 et max-1
- `aleatoire(min, max)` - Entre min et max-1

### 📝 Syntaxe Flexible
- Accents optionnels : `debut` ou `début`, `ecrire` ou `écrire`
- Casse insensible : `DEBUT`, `Debut`, `debut`
- Abréviations : `var` pour `variables`, `const` pour `constantes`
- Affectation : `=` ou `:=`
- Commentaires multiples : `//`, `#`, `/* */`

### 🔧 Autres Améliorations
- **Fonction `lire()`** : Ne crée plus de prompt automatique
- **Fonction `ecrire()`** : Sans saut de ligne automatique
- **Fonction `ecrireln()`** : Avec saut de ligne automatique
- Support `\n` et `\t` dans les chaînes
- Opérateurs `div` et `mod` pour divisions entières
- Messages d'erreur améliorés avec codes Unicode
- Mode debug tokens : `-Ddebug.tokens=1`

---

## 🚀 Installation et Utilisation

### Prérequis
- **Java 21 ou supérieur**
  ```bash
  java -version
  ```

### Installation

#### Option 1 : Télécharger le JAR
1. Télécharger `algo-compiler-1.0.0.jar`
2. Exécuter :
   ```bash
   java -jar algo-compiler-1.0.0.jar votre_fichier.algo
   ```

#### Option 2 : Compiler depuis les sources
```bash
git clone https://github.com/oughzal/algo-compiler.git
cd algo-compiler
./gradlew build
java -jar build/libs/algo-compiler-1.0.0.jar examples/hello.algo
```

### Extension VS Code
1. Télécharger `algo-compiler-1.3.0.vsix`
2. Dans VS Code : `Ctrl+Shift+P` → `Extensions: Install from VSIX...`
3. Sélectionner le fichier `.vsix`
4. Créer un fichier `.algo` et utiliser `Ctrl+Shift+R` pour exécuter

---

## 📚 Documentation

### Guides inclus
- **README.md** - Guide utilisateur complet
- **QUICKSTART.md** - Démarrage rapide
- **FONCTIONS_INTEGREES_COURANTES.md** - Documentation des 40 fonctions
- **MODIFICATIONS_TYPE_CARACTERE.md** - Guide du type caractère
- **FONCTIONS_ORD_CHR.md** - Guide ord() et chr()
- **CORRECTION_FONCTION_LIRE.md** - Explication fonction lire()

### Site Web
🌐 **https://oughzal.github.io/algo-compiler/**
- Documentation interactive
- Exemples de code
- Guide d'installation
- Liste complète des fonctions

---

## 💡 Exemples

### Hello World
```algo
algorithme HelloWorld

variables
    nom : chaine

debut
    ecrire("Entrez votre nom: ")
    lire(nom)
    ecrireln("Bonjour ", nom, " !")
fin
```

### Type Caractère
```algo
algorithme TestCaractere

variables
    c : caractere
    code : entier

debut
    c = 'A'
    code = ord(c)
    ecrireln("Code ASCII de 'A': ", code)
    
    c = chr(code + 1)
    ecrireln("Caractère suivant: ", c)
fin
```

### Fonctions Intégrées
```algo
algorithme TestFonctions

variables
    texte : chaine
    resultat : entier

debut
    texte = "Bonjour le monde"
    
    si contient(texte, "monde") alors
        resultat = position(texte, "monde")
        ecrireln("Trouvé à la position: ", resultat)
    finsi
    
    ecrireln("Min(5, 10) = ", min(5, 10))
    ecrireln("Max(5, 10) = ", max(5, 10))
fin
```

### Plus de 30 exemples
Consultez le dossier `examples/` pour des exemples complets :
- Factorielle
- Jeu de devinette
- Manipulation de chaînes
- Tableaux
- Fonctions utilisateur
- Et bien plus...

---

## 🔧 Configuration Technique

### Compilateur
- **Version** : 1.0.0
- **Langage** : Kotlin 1.9.20
- **JVM** : Java 21 (toolchain)
- **Build** : Gradle 8.5

### Extension VS Code
- **Version** : 1.3.0
- **Fonctionnalités** :
  - Coloration syntaxique
  - 30+ snippets
  - Exécution directe (Ctrl+Shift+R)
  - Support des 40 fonctions

---

## 📊 Statistiques

- **Types de données** : 5 (entier, reel, chaine, caractere, booleen)
- **Fonctions intégrées** : 40
- **Exemples fournis** : 30+
- **Lignes de documentation** : 3000+
- **Tests** : Tous validés ✅

---

## 🐛 Problèmes Connus

Aucun problème critique connu dans cette version.

Pour signaler un bug, utilisez : https://github.com/oughzal/algo-compiler/issues

---

## 🔄 Changelog Complet

### v1.0.0 (3 Décembre 2025)

#### Ajouté
- ✅ Type `caractere` complet avec littéraux et échappements
- ✅ 40 fonctions intégrées (vs 21 dans les versions précédentes)
- ✅ Fonctions `ord()` et `chr()` pour conversions ASCII
- ✅ Fonctions mathématiques : `min()`, `max()`, `reste()`, `quotient()`
- ✅ Fonctions de chaînes : `estVide()`, `contient()`, `commence()`, `termine()`, `position()`
- ✅ Fonctions de caractères : `estLettre()`, `estChiffre()`, `estMajuscule()`, etc.
- ✅ Fonctions de conversion : `versEntier()`, `versReel()`, `versChaine()`
- ✅ Support apostrophes typographiques (conversion automatique)
- ✅ Mode debug tokens
- ✅ Extension VS Code v1.3.0 avec 30+ snippets
- ✅ Site web GitHub Pages

#### Modifié
- ✅ Fonction `lire()` : Ne crée plus de prompt automatique
- ✅ Fonction `ecrire()` : Corrigé (pas de "0" ajouté)
- ✅ Fonction `ecrireln()` : Comportement clarifié (avec saut de ligne)
- ✅ Messages d'erreur améliorés avec codes Unicode

#### Corrigé
- ✅ Bug "0" ajouté par `ecrire()`
- ✅ Prompt automatique de `lire()`
- ✅ Support apostrophes typographiques

---

## 🙏 Contribution

Les contributions sont les bienvenues !

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

---

## 📄 Licence

Ce projet est distribué sous licence libre. Consultez le fichier LICENSE pour plus de détails.

---

## 📞 Support

- **Documentation** : https://oughzal.github.io/algo-compiler/
- **Issues** : https://github.com/oughzal/algo-compiler/issues
- **Discussions** : https://github.com/oughzal/algo-compiler/discussions

---

## 🎓 Utilisation en Enseignement

Ce compilateur est parfait pour :
- ✅ Cours d'algorithmique
- ✅ Apprentissage de la programmation
- ✅ TP et exercices
- ✅ Prototypage d'algorithmes
- ✅ Tests de logique

---

## 🌟 Remerciements

Merci à tous ceux qui ont contribué à ce projet !

---

**Développé avec ❤️ en Kotlin et Java**

*Compilateur de Pseudo-code Français - Version 1.0.0*
*Date de release : 3 Décembre 2025*

