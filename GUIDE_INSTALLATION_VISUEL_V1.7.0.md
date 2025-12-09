# 📖 Guide Visuel d'Installation - Algo Compiler v1.7.0

## Installation Simplifiée en 3 Étapes

---

## Étape 1️⃣ : Installer Java 21+

### Vérifier si Java est Installé

```powershell
java -version
```

**Résultat attendu** :
```
java version "21.0.x" 2024-xx-xx
Java(TM) SE Runtime Environment (build 21.0.x+xx)
Java HotSpot(TM) 64-Bit Server VM (build 21.0.x+xx, mixed mode, sharing)
```

### Si Java n'est pas Installé

🔗 **Télécharger Java 21** : [https://www.oracle.com/java/technologies/downloads/#java21](https://www.oracle.com/java/technologies/downloads/#java21)

**Instructions** :
1. Cliquer sur le lien ci-dessus
2. Choisir votre système d'exploitation (Windows x64 Installer)
3. Télécharger et exécuter l'installateur
4. Suivre les instructions d'installation
5. Redémarrer le terminal et vérifier : `java -version`

---

## Étape 2️⃣ : Télécharger et Placer le Compilateur

### Télécharger le Compilateur

🔗 **Lien Direct** : [algo-compiler-1.6.0.jar](https://github.com/oughzal/Algo-compiler/releases/download/release/algo-compiler-1.6.0.jar)

**Instructions** :
1. Cliquer sur le lien ci-dessus
2. Le téléchargement commence automatiquement
3. Taille du fichier : ~1.75 MB

### Placer le Fichier à l'Emplacement Par Défaut

**⚠️ Important** : Placer le fichier exactement à cet emplacement :

```
c:\algo-compiler-1.6.0.jar
```

**Pas dans un sous-dossier, directement à la racine du disque C:**

✅ **Correct** : `c:\algo-compiler-1.6.0.jar`  
❌ **Incorrect** : `c:\Users\Omar\Downloads\algo-compiler-1.6.0.jar`  
❌ **Incorrect** : `c:\Program Files\algo-compiler-1.6.0.jar`

### Vérifier l'Emplacement

```powershell
Test-Path "c:\algo-compiler-1.6.0.jar"
```

**Résultat attendu** : `True`

---

## Étape 3️⃣ : Installer l'Extension VS Code

### Option A : Depuis le Marketplace (Recommandé)

1. **Ouvrir Visual Studio Code**

2. **Aller dans Extensions**
   - Cliquer sur l'icône Extensions dans la barre latérale
   - Ou appuyer sur `Ctrl+Shift+X`

3. **Rechercher l'Extension**
   - Taper : `Algo Compiler`
   - Chercher : `omarcomputer.algo-compiler`

4. **Installer**
   - Cliquer sur le bouton **Install**
   - Attendre la fin de l'installation

5. **Redémarrer VS Code** (si demandé)

### Option B : Depuis un Fichier VSIX

1. **Télécharger le fichier VSIX**
   - Depuis [GitHub Releases](https://github.com/oughzal/Algo-compiler/releases/latest)
   - Fichier : `algo-compiler-1.7.0.vsix`

2. **Installer dans VS Code**
   - Ouvrir VS Code
   - Appuyer sur `Ctrl+Shift+P`
   - Taper : `Extensions: Install from VSIX...`
   - Sélectionner le fichier téléchargé

---

## ✅ Vérification de l'Installation

### Test Simple

1. **Créer un nouveau fichier**
   - `Fichier` → `Nouveau fichier`
   - Sauvegarder avec l'extension `.algo` (ex: `test.algo`)

2. **Utiliser un snippet**
   - Taper : `algo`
   - Appuyer sur `Tab`
   - Une structure complète d'algorithme apparaît !

3. **Écrire un programme simple**
   ```algo
   algorithme Test
   
   debut
       ecrireln("Hello, Algo!")
   fin
   ```

4. **Exécuter le programme**
   - Appuyer sur `Ctrl+Shift+R`
   - Ou clic droit → `Algo: Exécuter le fichier Algo`
   - Le résultat s'affiche dans le terminal !

**Résultat attendu** :
```
Hello, Algo!
```

---

## 🎨 Aperçu de l'Extension

### Coloration Syntaxique

L'extension colore automatiquement :
- **Mots-clés** : `algorithme`, `debut`, `fin`, `si`, `pour`, `tantque`...
- **Types** : `entier`, `reel`, `chaine`, `caractere`, `booleen`, `tableau`
- **Fonctions** : `ecrire`, `lire`, `abs`, `min`, `max`, `longueur`...
- **Opérateurs** : `+`, `-`, `*`, `/`, `et`, `ou`, `non`, `^`
- **Commentaires** : `//`, `#`, `/* */`

### Snippets Disponibles

Taper le préfixe puis `Tab` :

| Préfixe | Description |
|---------|-------------|
| `algo` | Structure complète d'algorithme |
| `si` | Structure conditionnelle |
| `sinonsi` | Si avec clause sinonSi |
| `pour` | Boucle pour |
| `tantque` | Boucle tantque |
| `fonction` | Définir une fonction |
| `tableau` | Déclarer un tableau |
| `varinit` | Variable avec initialisation |

### Raccourcis Clavier

| Raccourci | Action |
|-----------|--------|
| `Ctrl+Shift+R` | Exécuter le programme |
| `Ctrl+Space` | Autocomplétion |
| `Tab` | Compléter un snippet |
| `Ctrl+/` | Commenter/Décommenter |

---

## ⚙️ Configuration (Optionnelle)

### Si le Compilateur est Ailleurs

Si vous avez placé le compilateur ailleurs que `c:\algo-compiler-1.6.0.jar` :

1. **Ouvrir les Paramètres**
   - `Fichier` → `Préférences` → `Paramètres`
   - Ou `Ctrl+,`

2. **Rechercher "Algo Compiler"**

3. **Modifier "Compiler Path"**
   - Entrer le chemin complet vers le JAR
   - Exemple : `D:\MonDossier\algo-compiler-1.6.0.jar`

4. **Sauvegarder**
   - Les modifications sont automatiques

### Configuration Avancée

Autres paramètres disponibles :

- **Java Path** : Chemin vers l'exécutable Java (par défaut : `java`)
- **Clear Output Before Run** : Effacer la console avant exécution
- **Show Execution Time** : Afficher le temps d'exécution

---

## 🔧 Dépannage

### Problème : "Compilateur Algo non trouvé"

**Solutions** :

1. **Vérifier l'emplacement du JAR**
   ```powershell
   Test-Path "c:\algo-compiler-1.6.0.jar"
   ```
   Doit retourner : `True`

2. **Configurer manuellement le chemin**
   - Paramètres → Rechercher "Algo Compiler"
   - Modifier "Compiler Path"

3. **Vérifier les permissions**
   - S'assurer d'avoir les droits de lecture sur le fichier

### Problème : "Cannot find Java"

**Solutions** :

1. **Vérifier Java**
   ```powershell
   java -version
   ```

2. **Si Java n'est pas dans le PATH**
   - Paramètres → "Algo Compiler" → "Java Path"
   - Entrer le chemin complet : `C:\Program Files\Java\jdk-21\bin\java.exe`

### Problème : "Erreur lors de l'exécution"

**Vérifications** :
- ✅ Fichier `.algo` sauvegardé
- ✅ Pas d'erreurs de syntaxe
- ✅ Java installé et fonctionnel
- ✅ Compilateur à l'emplacement correct

---

## 📚 Exemples de Code

### Hello World

```algo
algorithme HelloWorld

debut
    ecrireln("Bonjour le monde !")
fin
```

### Programme Interactif

```algo
algorithme Salutation

variables
    nom : chaine
    age : entier

debut
    ecrire("Entrez votre nom: ")
    lire(nom)
    
    ecrire("Entrez votre age: ")
    lire(age)
    
    ecrireln("Bonjour ", nom, " !")
    ecrireln("Vous avez ", age, " ans.")
fin
```

### Utiliser les Fonctions Intégrées

```algo
algorithme Fonctions

variables
    texte : chaine
    resultat : entier

debut
    texte = "Bonjour"
    
    ecrireln("Longueur: ", longueur(texte))
    ecrireln("Majuscules: ", majuscule(texte))
    
    si contient(texte, "jour") alors
        ecrireln("Le mot contient 'jour'")
    finsi
    
    resultat = min(10, 20)
    ecrireln("Min(10, 20) = ", resultat)
fin
```

### Tableaux et Boucles

```algo
algorithme Tableaux

variables
    nombres : tableau[5] de entier
    i : entier
    somme : entier

debut
    // Initialisation avec littéral
    nombres = [10, 20, 30, 40, 50]
    
    // Calcul de la somme
    somme = 0
    pour i de 0 à 4 faire
        somme = somme + nombres[i]
    finpour
    
    ecrireln("Somme: ", somme)
    ecrireln("Moyenne: ", somme / 5)
fin
```

---

## 🎯 Fonctionnalités Avancées

### Matrices (Tableaux 2D)

```algo
algorithme Matrices

variables
    M : tableau[3][3] de entier
    i, j : entier

debut
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
        ecrireln("")
    finpour
fin
```

### Expression Conditionnelle

```algo
algorithme Expressions

variables
    a, b, max : entier

debut
    a = 10
    b = 20
    
    // Expression conditionnelle
    max = si a > b alors a sinon b
    
    ecrireln("Le maximum est: ", max)
fin
```

### Clause SinonSi

```algo
algorithme Notes

variables
    note : reel

debut
    ecrire("Entrez la note: ")
    lire(note)
    
    si note >= 90 alors
        ecrireln("Grade: A")
    sinonSi note >= 80 alors
        ecrireln("Grade: B")
    sinonSi note >= 70 alors
        ecrireln("Grade: C")
    sinonSi note >= 60 alors
        ecrireln("Grade: D")
    sinon
        ecrireln("Grade: F")
    finsi
fin
```

---

## 📖 Ressources Supplémentaires

### Documentation Complète

- 🌐 **Site Web** : [https://oughzal.github.io/Algo-compiler/](https://oughzal.github.io/Algo-compiler/)
- 📖 **Guide Complet** : [HELP.md](https://github.com/oughzal/Algo-compiler/blob/main/HELP.md)
- 🚀 **Démarrage Rapide** : [QUICKSTART.md](https://github.com/oughzal/Algo-compiler/blob/main/QUICKSTART.md)
- 📝 **README** : [README.md](https://github.com/oughzal/Algo-compiler/blob/main/README.md)

### Fonctions Intégrées

- 🧮 **40+ Fonctions** : [FONCTIONS_INTEGREES_COURANTES.md](https://github.com/oughzal/Algo-compiler/blob/main/FONCTIONS_INTEGREES_COURANTES.md)
- 📊 **Matrices** : [IMPLEMENTATION_MATRICES_COMPLETE.md](https://github.com/oughzal/Algo-compiler/blob/main/IMPLEMENTATION_MATRICES_COMPLETE.md)
- ⚡ **Opérateur ^** : [OPERATEUR_PUISSANCE_ALIAS.md](https://github.com/oughzal/Algo-compiler/blob/main/OPERATEUR_PUISSANCE_ALIAS.md)

### Support et Communauté

- 🐛 **Signaler un Bug** : [GitHub Issues](https://github.com/oughzal/Algo-compiler/issues)
- 💬 **Discussions** : [GitHub Discussions](https://github.com/oughzal/Algo-compiler/discussions)
- 📋 **Releases** : [GitHub Releases](https://github.com/oughzal/Algo-compiler/releases)

---

## ✅ Checklist d'Installation

Cochez les étapes au fur et à mesure :

- [ ] Java 21+ installé (`java -version`)
- [ ] Compilateur téléchargé
- [ ] Compilateur placé à `c:\algo-compiler-1.6.0.jar`
- [ ] Extension VS Code installée
- [ ] Test d'un fichier `.algo` réussi
- [ ] Exécution fonctionnelle (`Ctrl+Shift+R`)

---

**© 2024 Omar OUGHZAL** - Tous droits réservés

**Bon algorithme ! 🚀**

