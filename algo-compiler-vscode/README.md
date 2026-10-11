# Algo Compiler - Extension VS Code

[![Version](https://img.shields.io/visual-studio-marketplace/v/omarcomputer.algo-compiler)](https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler)
[![Downloads](https://img.shields.io/visual-studio-marketplace/d/omarcomputer.algo-compiler)](https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler)
[![Rating](https://img.shields.io/visual-studio-marketplace/r/omarcomputer.algo-compiler)](https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler)

Extension Visual Studio Code pour **Algo-Compiler** - Écrivez, éditez et exécutez des programmes en pseudocode algorithmique français avec **40 fonctions intégrées** !

---

## 📥 Installation Rapide

### 1. Télécharger le Compilateur
🔗 **[algo-compiler-1.6.0.jar](https://github.com/oughzal/Algo-compiler/releases/download/release/algo-compiler-1.6.0.jar)** ← Cliquez pour télécharger

📍 **Placer le fichier à** : `c:\algo-compiler\algo-compiler-1.6.0.jar`

### 2. Installer Java 21+
☕ **[Java 21+](https://www.oracle.com/java/technologies/downloads/#java21)** ← Télécharger si nécessaire

### 3. Installer cette Extension
📦 Dans VS Code : Extensions → Rechercher "Algo Compiler" → Installer

**C'est tout ! L'extension détectera automatiquement le compilateur.**

💡 **Nouveau : configuration assistée**
- Ouvrir la palette de commandes (`Ctrl+Shift+P`)
- Lancer **`Algo: Configurer l'environnement Algo`**
- L'extension peut télécharger le JAR automatiquement (avec votre consentement) et guider l'installation de Java si nécessaire.

---



🌐 **Site web** : [https://oughzal.github.io/Algo-compiler/](https://oughzal.github.io/Algo-compiler/)

---

## 🎉 Nouveautés Version 1.8.0

### ⚡ Évaluation en Court-Circuit (Short-Circuit)
Les opérateurs logiques `et` et `ou` utilisent maintenant l'évaluation en court-circuit comme dans Kotlin :
```algo
// Pour ET : si le premier est faux, le second n'est pas évalué
x = 0
resultat = (x == 1) et (10 / x > 0)  // Pas d'erreur de division par zéro !

// Pour OU : si le premier est vrai, le second n'est pas évalué
resultat = (x == 0) ou (10 / x > 0)  // Pas d'erreur de division par zéro !
```
**Avantages** : Meilleures performances et code plus sûr

### 🔄 Passage par Référence
Modifiez les variables directement avec le mot-clé `ref` :
```algo
procedure echanger(ref a : entier, ref b : entier)
variables
    temp : entier
debut
    temp = a
    a = b
    b = temp
fin

variables
    x, y : entier
debut
    x = 10
    y = 20
    echanger(x, y)  // x = 20, y = 10
fin
```

### 🌍 Variables Globales
Les fonctions/procédures peuvent accéder et modifier les variables de l'algorithme principal :
```algo
variables
    compteur : entier

procedure incrementer()
debut
    compteur = compteur + 1  // Modifie la variable globale
fin

debut
    compteur = 0
    incrementer()  // compteur = 1
fin
```

### ✅ Expressions avec Fonctions
Écrivez des expressions naturelles commençant par des fonctions :
```algo
resultat = racine(16) + abs(-5)     // 9
valeur = puissance(2, 3) * abs(-2)  // 16
```

### 📊 Matrices (Tableaux 2D)
Créez et manipulez des tableaux à deux dimensions :
```algo
variables
    M : tableau[3][3] de entier
    i, j : entier

debut
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            M[i][j] = (i + 1) * 10 + (j + 1)
        finpour
    finpour
fin
```

### ⚡ Opérateur Puissance ^
Deux syntaxes pour la puissance :
```algo
resultat = 2 ^ 3   // 8
aire = rayon ^ 2
volume = cote ** 3  // ** fonctionne toujours
```

### ✨ Clause `sinonSi`
Testez plusieurs conditions sans imbrication excessive :
```algo
si note >= 90 alors
    ecrire("Grade A")
sinonSi note >= 80 alors
    ecrire("Grade B")
sinonSi note >= 70 alors
    ecrire("Grade C")
sinon
    ecrire("Échec")
finsi
```

### 🎯 Expression Conditionnelle
```algo
max = si a > b alors a sinon b
statut = si age >= 18 alors "Majeur" sinon "Mineur"
```

### 📝 Initialisation de Variables
```algo
variables
    compteur : entier = 0
    pi : reel = 3.14159
    nom : chaine = "Alice"
```

### 📦 Tableaux Littéraux
```algo
nombres = [10, 20, 30, 40, 50]
notes = [15.5, 12.0, 18.0]
noms = ["Alice", "Bob", "Charlie"]
```

**Nouveaux snippets** ajoutés pour matrices et opérateur puissance !

---

## ✨ Fonctionnalités

### 🎨 Coloration Syntaxique Complète
- **Mots-clés** : `algorithme`, `début`, `fin`, `si`, `sinonSi`, `pour`, `tantque`, etc.
- **Types** : `entier`, `reel`, `chaine`, `caractere`, `booleen`, `tableau`
- **40 fonctions intégrées** avec coloration dédiée :
  - Mathématiques (15) : `abs`, `min`, `max`, `racine`, `sin`, `cos`...
  - Chaînes (9) : `longueur`, `contient`, `position`, `estVide`...
  - Caractères (8) : `ord`, `chr`, `estLettre`, `estChiffre`...
  - Conversions (3) : `versEntier`, `versReel`, `versChaine`
  - Aléatoires (3) : `aleatoire()` et variantes
- **Opérateurs** : `+`, `-`, `*`, `/`, `div`, `mod`, `et`, `ou`, `non`
- **Commentaires** : `//`, `#`, `/* */`

### ▶️ Exécution Directe
- **Raccourci** : `Ctrl+Shift+R` (Windows/Linux) ou `Cmd+Shift+R` (Mac)
- **Bouton** dans la barre d'outils
- **Menu contextuel** : Clic droit → "Algo: Exécuter"
- **Palette** : `Ctrl+Shift+P` → "Algo: Exécuter le fichier Algo"
- Sortie dans le terminal intégré avec support interactif (`lire`)

### 📦 44+ Snippets Intelligents
Tapez le préfixe et appuyez sur `Tab` :

**Nouveaux snippets v1.4.0** :
- `sinonsi` → Structure si avec sinonSi
- `siexpr` → Expression conditionnelle
- `varinit` → Variable avec initialisation
- `tablit` → Tableau littéral
- ... et 10 autres !

**Snippets classiques** :
- `algo` → Structure complète d'algorithme
- `fonction` → Définir une fonction
- `procedure` → Définir une procédure
- `si` → Structure conditionnelle
- `pour` → Boucle pour
- `tantque` → Boucle tantque
- `tableau` → Déclarer un tableau
- `ord`, `chr`, `min`, `max`, `contient`... → Fonctions intégrées
- Et bien plus !

### ⚙️ Configuration Flexible
- Chemin du compilateur (auto-détection ou manuel)
- Chemin Java personnalisable
- Options d'affichage
- Nettoyage de sortie configurable

---

## 📦 Installation

### Étape 1 : Installer Java 21+

Le compilateur nécessite **Java 21 ou supérieur**.

#### Windows
```powershell
# Vérifier Java
java -version

# Si Java n'est pas installé, télécharger depuis :
# https://www.oracle.com/java/technologies/downloads/
# ou https://adoptium.net/
```

#### macOS
```bash
# Avec Homebrew
brew install openjdk@21

# Vérifier
java -version
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt install openjdk-21-jdk

# Fedora
sudo dnf install java-21-openjdk

# Vérifier
java -version
```

### Étape 2 : Télécharger le Compilateur

#### Option A : Depuis GitHub Releases (Recommandé)

1. Allez sur [GitHub Releases](https://github.com/oughzal/Algo-compiler/releases/latest)
2. Téléchargez **`algo-compiler-1.0.0.jar`**
3. Placez-le dans un dossier de votre choix, par exemple :
   - Windows : `C:\Program Files\algo-compiler\algo-compiler-1.0.0.jar`
   - macOS/Linux : `/usr/local/bin/algo-compiler-1.0.0.jar` ou `~/algo-compiler/algo-compiler-1.0.0.jar`

#### Option B : Compiler depuis les sources

```bash
# Cloner le repo
git clone https://github.com/oughzal/algo-compiler.git
cd algo-compiler

# Compiler (Windows)
.\gradlew.bat build

# Compiler (macOS/Linux)
./gradlew build

# Le JAR sera dans : build/libs/algo-compiler-1.0.0.jar
```

### Étape 3 : Installer l'Extension VS Code

#### Option A : Depuis le VS Marketplace (Recommandé)

1. Ouvrir VS Code
2. Aller dans **Extensions** (`Ctrl+Shift+X`)
3. Rechercher **"Algo Compiler"**
4. Cliquer sur **Install**

#### Option B : Depuis un fichier VSIX

1. Télécharger `algo-compiler-1.3.0.vsix` depuis [GitHub Releases](https://github.com/oughzal/algo-compiler/releases/latest)
2. Dans VS Code : `Ctrl+Shift+P` → `Extensions: Install from VSIX...`
3. Sélectionner le fichier téléchargé

### Étape 4 : Configurer l'Extension

1. Ouvrir les **Paramètres** : `File > Preferences > Settings` (ou `Ctrl+,`)
2. Rechercher **"Algo Compiler"**
3. Configurer **Compiler Path** :
   - Windows : `C:\Program Files\algo-compiler\algo-compiler-1.0.0.jar`
   - macOS/Linux : `/usr/local/bin/algo-compiler-1.0.0.jar`
   
   *Si vous avez compilé depuis les sources, utilisez le chemin vers `build/libs/algo-compiler-1.0.0.jar`*

4. (Optionnel) Configurer **Java Path** si Java n'est pas dans le PATH :
   - Windows : `C:\Program Files\Java\jdk-21\bin\java.exe`
   - macOS : `/usr/libexec/java_home -v 21`
   - Linux : `/usr/bin/java`

---

## 🚀 Utilisation

### Créer votre premier programme

1. **Créer un fichier** avec l'extension `.algo` (ex: `test.algo`)

2. **Utiliser le snippet** : Tapez `algo` puis `Tab`
   ```algo
   algorithme MonPremierProgramme

   variables
       nom : chaine
       age : entier

   debut
       ecrire("Entrez votre nom: ")
       lire(nom)
       
       ecrire("Entrez votre âge: ")
       lire(age)
       
       ecrireln("Bonjour ", nom, " !")
       ecrireln("Vous avez ", age, " ans.")
   fin
   ```

3. **Exécuter** : `Ctrl+Shift+R` ou clic droit → "Algo: Exécuter"

4. Le programme s'exécute dans le **Terminal** intégré !

### Exemples de Code

#### Boucles avec Pas (Step)

```algo
algorithme ExemplesPas

variables
    i : entier

debut
    // Boucle ascendante avec pas par défaut (1)
    ecrireln("Nombres de 1 à 10:")
    pour i de 1 à 10 faire
        ecrire(i, " ")
    finpour
    ecrireln()
    
    // Boucle descendante avec pas par défaut (-1)
    ecrireln("Compte à rebours:")
    pour i de 10 à 0 faire
        ecrire(i, " ")
    finpour
    ecrireln()
    
    // Boucle avec pas positif explicite
    ecrireln("Nombres pairs de 0 à 20:")
    pour i de 0 à 20 pas 2 faire
        ecrire(i, " ")
    finpour
    ecrireln()
    
    // Boucle avec pas négatif explicite
    ecrireln("De 20 à 0 par pas de -2:")
    pour i de 20 à 0 pas -2 faire
        ecrire(i, " ")
    finpour
    ecrireln()
fin
```

#### Utiliser les 40 fonctions intégrées

```algo
algorithme ExemplesFonctions

variables
    texte : chaine
    c : caractere
    resultat : entier

debut
    // Fonctions de chaînes
    texte = "Bonjour le monde"
    
    si contient(texte, "monde") alors
        resultat = position(texte, "monde")
        ecrireln("'monde' trouvé à la position: ", resultat)
    finsi
    
    ecrireln("Longueur: ", longueur(texte))
    ecrireln("Majuscules: ", majuscule(texte))
    
    // Fonctions de caractères
    c = 'A'
    ecrireln("Code ASCII de 'A': ", ord(c))
    ecrireln("Caractère de code 66: ", chr(66))
    
    si estMajuscule(c) alors
        ecrireln("'A' est une majuscule")
    finsi
    
    // Fonctions mathématiques
    ecrireln("min(5, 10) = ", min(5, 10))
    ecrireln("max(5, 10) = ", max(5, 10))
    ecrireln("racine(16) = ", racine(16))
fin
```

#### Type Caractère

```algo
algorithme TypeCaractere

variables
    lettre : caractere
    code : entier

debut
    lettre = 'A'
    code = ord(lettre)
    
    ecrireln("Caractère: ", lettre)
    ecrireln("Code ASCII: ", code)
    
    lettre = chr(code + 1)
    ecrireln("Caractère suivant: ", lettre)
fin
```

### Raccourcis Clavier

| Raccourci | Action |
|-----------|--------|
| `Ctrl+Shift+R` | Exécuter le fichier algo |
| `Ctrl+Space` | Autocomplétion |
| `Tab` | Compléter un snippet |

---

## ⚙️ Configuration Avancée

### Paramètres Disponibles

Accédez via `File > Preferences > Settings` → Rechercher "Algo Compiler"

| Paramètre | Description | Défaut |
|-----------|-------------|--------|
| `algoCompiler.compilerPath` | Chemin vers algo-compiler-1.6.0.jar | Auto-détecté |
| `algoCompiler.javaPath` | Chemin vers l'exécutable Java | `java` |
| `algoCompiler.clearOutputBeforeRun` | Effacer la sortie avant exécution | `true` |
| `algoCompiler.showExecutionTime` | Afficher le temps d'exécution | `true` |

### Configuration Manuelle (settings.json)

```json
{
  "algoCompiler.compilerPath": "C:\\algo-compiler\\algo-compiler-1.6.0.jar",
  "algoCompiler.javaPath": "C:\\Program Files\\Java\\jdk-21\\bin\\java.exe",
  "algoCompiler.clearOutputBeforeRun": true,
  "algoCompiler.showExecutionTime": true
}
```

### Vérifier la Configuration

1. Ouvrir un fichier `.algo`
2. Essayer d'exécuter (`Ctrl+Shift+R`)
3. Si erreur : vérifier les chemins dans les paramètres

---

## 🎓 Ressources

### Documentation
- 🌐 **Site web** : [https://oughzal.github.io/Algo-compiler/](https://oughzal.github.io/Algo-compiler/)
- 📚 **Guide complet** : [README.md](https://github.com/oughzal/Algo-compiler/blob/main/README.md)
- 📖 **Aide complète** : [HELP.md](https://github.com/oughzal/Algo-compiler/blob/main/HELP.md)
- 🚀 **Démarrage rapide** : [QUICKSTART.md](https://github.com/oughzal/Algo-compiler/blob/main/QUICKSTART.md)
- 🧮 **40+ fonctions** : [FONCTIONS_INTEGREES_COURANTES.md](https://github.com/oughzal/Algo-compiler/blob/main/FONCTIONS_INTEGREES_COURANTES.md)
- 📊 **Matrices** : [SUPPORT_MATRICES.md](https://github.com/oughzal/Algo-compiler/blob/main/SUPPORT_MATRICES.md)
- ⚡ **Opérateur ^** : [OPERATEUR_PUISSANCE_ALIAS.md](https://github.com/oughzal/Algo-compiler/blob/main/OPERATEUR_PUISSANCE_ALIAS.md)

### Exemples
Plus de 30 exemples disponibles dans le dossier [examples/](https://github.com/oughzal/Algo-compiler/tree/main/examples) :
- Hello World
- Factorielle
- Jeu de devinette
- Manipulation de chaînes
- Type caractère
- Tableaux
- Et bien plus !

### Support
- 🐛 **Signaler un bug** : [GitHub Issues](https://github.com/oughzal/algo-compiler/issues)
- 💬 **Discussions** : [GitHub Discussions](https://github.com/oughzal/algo-compiler/discussions)
- 📖 **Releases** : [GitHub Releases](https://github.com/oughzal/Algo-compiler/releases)

---

## 🔧 Dépannage

### L'extension ne trouve pas le compilateur

**Solution** : Configurer manuellement le chemin dans les paramètres
1. `Ctrl+,` → Rechercher "Algo Compiler"
2. Définir **Compiler Path** avec le chemin complet vers le JAR

### Java n'est pas trouvé

**Solution** : Vérifier que Java 21+ est installé
```bash
java -version
```
Si non installé, télécharger depuis [Oracle](https://www.oracle.com/java/technologies/downloads/) ou [Adoptium](https://adoptium.net/)

### L'exécution ne fonctionne pas

**Vérifications** :
1. Java 21+ installé : `java -version`
2. Compilateur téléchargé et chemin correct
3. Fichier `.algo` sauvegardé
4. Vérifier la sortie du terminal pour les erreurs

### Erreur "Cannot find Java"

**Solution** : Configurer le chemin Java manuellement
- Paramètres → `algoCompiler.javaPath` → Chemin vers `java.exe` (Windows) ou `java` (macOS/Linux)

---

## 📝 Notes de Version

### v1.3.0 (Actuelle)
- ✅ Support de 40 fonctions intégrées
- ✅ Type `caractere` avec coloration
- ✅ 30+ snippets enrichis
- ✅ Amélioration de la coloration syntaxique
- ✅ Documentation mise à jour

### v1.2.0
- ✅ Exécution interactive avec `lire`
- ✅ Support de `ecrireln`
- ✅ Améliorations de stabilité

Voir [CHANGELOG.md](CHANGELOG.md) pour l'historique complet.

---

## 🤝 Contribution

Les contributions sont les bienvenues ! 

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

---

## 📄 Licence

Ce projet est distribué sous licence libre. Voir LICENSE pour plus de détails.

---

## 🙏 Remerciements

Développé avec ❤️ pour faciliter l'apprentissage de l'algorithmique en français.

**© 2024 Omar OUGHZAL** - Tous droits réservés

**Algo-Compiler** - Compilateur de Pseudo-code Français
- 🌐 Site web : [https://oughzal.github.io/Algo-compiler/](https://oughzal.github.io/Algo-compiler/)
- 🐙 GitHub : [https://github.com/oughzal/Algo-compiler](https://github.com/oughzal/Algo-compiler)
- 📦 Releases : [https://github.com/oughzal/Algo-compiler/releases](https://github.com/oughzal/Algo-compiler/releases)

---

**Bon algorithme ! 🚀**
