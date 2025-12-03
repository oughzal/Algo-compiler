# Algo Compiler - Extension VS Code

Extension Visual Studio Code pour le **Compilateur Algo** - Permet d'écrire, éditer et exécuter des programmes en pseudocode algorithmique français.

## ✨ Fonctionnalités

### 📝 Coloration syntaxique complète
- Mots-clés: `algorithme`, `début`, `fin`, `si`, `pour`, `tantque`, etc.
- Déclarations: `variables`, `var`, `constantes`, `const`, `fonction`, `procedure`
- Types de données: `entier`, `reel`, `chaine`, `caractere`, `booleen`, `tableau`
- **40 fonctions intégrées**:
  - Mathématiques: `abs`, `min`, `max`, `racine`, `puissance`, `sin`, `cos`, etc.
  - Chaînes: `longueur`, `majuscule`, `contient`, `position`, `estVide`, etc.
  - Caractères: `ord`, `chr`, `estLettre`, `estChiffre`, `versMajuscule`, etc.
  - Conversions: `versEntier`, `versReel`, `versChaine`
- Opérateurs arithmétiques: `+`, `-`, `*`, `/`, `div`, `mod`, `%`
- Opérateurs logiques: `et`, `ou`, `non`
- Commentaires: `//` (ligne), `#` (ligne Python), `/* */` (bloc)

### ▶️ Exécution directe
- **Exécuter**: `Ctrl+Shift+R` (Cmd+Shift+R sur Mac)
- Bouton dans la barre d'outils de l'éditeur
- Menu contextuel (clic droit)
- Palette de commandes: `Algo: Exécuter le fichier Algo`

### 🎨 Snippets intelligents
- `algo` - Structure complète d'algorithme
- `fonction` - Définir une fonction
- `procedure` - Définir une procédure
- `si` - Structure conditionnelle
- `pour` - Boucle pour
- `tantque` - Boucle tantque
- `tableau` - Déclarer un tableau
- `variables` - Bloc de variables
- `const` - Bloc de constantes
- `div`, `mod` - Opérateurs division/modulo
- `//`, `#`, `/*` - Commentaires
- Et plus encore...

### 🔧 Configuration
Accédez aux paramètres via `File > Preferences > Settings` puis recherchez "Algo Compiler":

- `algoCompiler.compilerPath` - Chemin vers le JAR du compilateur (auto-détecté par défaut)
- `algoCompiler.javaPath` - Chemin vers Java (`java` par défaut)
- `algoCompiler.clearOutputBeforeRun` - Effacer la console avant exécution
- `algoCompiler.showExecutionTime` - Afficher le temps d'exécution

## 📦 Installation

### Prérequis
1. **Java 21 ou supérieur** doit être installé
2. Le **compilateur Algo** doit être compilé:
   ```bash
   cd Algo-compiler
   gradlew build
   ```

### Installation de l'extension

#### Option 1: Depuis le fichier VSIX
1. Compiler l'extension:
   ```bash
   cd algo-compiler-vscode
   npm install
   npm run compile
   npm run package
   ```
2. Installer dans VS Code:
   - `Ctrl+Shift+P` → `Extensions: Install from VSIX...`
   - Sélectionner le fichier `.vsix` généré

#### Option 2: Mode développement
1. Ouvrir le dossier `algo-compiler-vscode` dans VS Code
2. Installer les dépendances:
   ```bash
   npm install
   ```
3. Appuyer sur `F5` pour lancer l'extension en mode debug

## 🚀 Utilisation

### Créer un nouveau programme

1. Créer un fichier avec l'extension `.algo`
2. Taper `algo` et appuyer sur `Tab` pour utiliser le snippet
3. Écrire votre code
4. Appuyer sur `Ctrl+Shift+R` pour exécuter

### Exemple

```algo
algorithme Calculatrice

fonction somme(a : entier, b : entier) : entier
début
    retourner a + b
fin

variables
    x, y, resultat : entier

début
    écrire("Entrez deux nombres:")
    lire(x, y)
    
    resultat = somme(x, y)
    écrire("La somme est:", resultat)
fin
```

## 📚 Fonctionnalités du langage

### Types de données
- `entier` - Nombres entiers
- `reel` - Nombres décimaux
- `chaine` - Chaînes de caractères
- `booleen` - Valeurs true/false
- `tableau[n] de type` - Tableaux

### Structures de contrôle
- `si ... alors ... sinon ... finsi`
- `pour ... de ... à ... faire ... finpour`
- `tantque ... faire ... fintantque`
- `repeter ... jusqua ...`

### Fonctions intégrées

#### Mathématiques
- `abs(x)` - Valeur absolue
- `racine(x)` - Racine carrée
- `puissance(x, y)` - x^y
- `arrondi(x)` - Arrondi
- `sin(x)`, `cos(x)`, `tan(x)` - Trigonométrie

#### Chaînes de caractères
- `longueur(s)` - Longueur
- `majuscule(s)` - MAJUSCULES
- `minuscule(s)` - minuscules
- `sousChaine(s, debut, fin)` - Extraire

#### Aléatoire
- `aleatoire()` - Entre 0 et 1
- `aleatoire(max)` - Entre 0 et max
- `aleatoire(min, max)` - Entre min et max

## 🎯 Raccourcis clavier

| Raccourci | Action |
|-----------|--------|
| `Ctrl+Shift+R` | Exécuter le fichier |
| `Ctrl+Space` | Autocomplétion |
| `Ctrl+/` | Commenter/Décommenter |

## 🐛 Dépannage

### Le compilateur n'est pas trouvé
1. Vérifiez que le projet est compilé: `gradlew build`
2. Configurez le chemin manuellement dans les paramètres:
   ```json
   "algoCompiler.compilerPath": "C:/chemin/vers/algo-compiler-1.0.0.jar"
   ```

### Java n'est pas trouvé
Configurez le chemin Java dans les paramètres:
```json
"algoCompiler.javaPath": "C:/Program Files/Java/jdk-21/bin/java.exe"
```

## 📄 Licence

MIT License - Voir LICENSE pour plus de détails

## 🤝 Contribution

Les contributions sont les bienvenues! N'hésitez pas à ouvrir une issue ou un pull request.

## 📧 Contact

Pour toute question ou suggestion, n'hésitez pas à nous contacter.

---

**Bon codage en Algo! 🚀**
