# Plugin IntelliJ IDEA pour Algo-Compiler

Plugin officiel pour le support du langage de pseudo-code Algo dans IntelliJ IDEA.

## Fonctionnalités

✨ **Coloration syntaxique** : Support complet de la syntaxe Algo avec mise en évidence des mots-clés, types, fonctions et commentaires

🚀 **Exécution directe** : Exécutez vos fichiers .algo directement depuis l'IDE avec Ctrl+Shift+F10

💡 **Autocomplétion intelligente** : Suggestions contextuelles pour :
- Mots-clés (algorithme, si, pour, tantque, etc.)
- Types de données (entier, réel, chaîne, caractère, booléen)
- 40+ fonctions intégrées (abs, racine, ord, chr, etc.)

🔧 **Snippets** : Templates prêts à l'emploi pour structures courantes

📝 **Commentaires** : Support des commentaires avec Ctrl+/ (// et /* */)

🎨 **Appariement de parenthèses** : Mise en évidence automatique des paires de parenthèses/crochets

## Installation

### Depuis le marketplace IntelliJ (à venir)

1. Ouvrez IntelliJ IDEA
2. Allez dans `File` → `Settings` → `Plugins`
3. Recherchez "Algo Compiler"
4. Cliquez sur `Install`
5. Redémarrez l'IDE

### Installation manuelle

1. Téléchargez le fichier `.zip` du plugin depuis [Releases](https://github.com/oughzal/Algo-compiler/releases)
2. Dans IntelliJ IDEA, allez dans `File` → `Settings` → `Plugins`
3. Cliquez sur l'icône ⚙️ → `Install Plugin from Disk...`
4. Sélectionnez le fichier `.zip` téléchargé
5. Redémarrez l'IDE

### Build depuis les sources

```bash
cd algo-compiler-intellij
./gradlew buildPlugin
```

Le plugin sera généré dans `build/distributions/algo-compiler-intellij-1.6.0.zip`

## Configuration

1. Allez dans `File` → `Settings` → `Tools` → `Algo Compiler`
2. Définissez le chemin vers `algo-compiler-1.6.0.jar`
3. (Optionnel) Définissez le chemin vers l'exécutable Java si différent de `java`

## Utilisation

### Créer un fichier Algo

1. `File` → `New` → `File`
2. Nommez le fichier avec l'extension `.algo` (ex: `test.algo`)
3. Écrivez votre code

### Exécuter un fichier

**Méthode 1 : Raccourci clavier**
- Appuyez sur `Ctrl+Shift+F10` (Windows/Linux) ou `Cmd+Shift+F10` (Mac)

**Méthode 2 : Menu contextuel**
- Clic droit sur le fichier → `Run`

**Méthode 3 : Menu Run**
- `Run` → `Run...` → Sélectionnez votre configuration

### Exemple de code

```algo
algorithme Bonjour
variables
    nom : chaine
    age : entier

debut
    ecrire("Entrez votre nom: ")
    lire(nom)
    ecrire("Entrez votre âge: ")
    lire(age)
    
    ecrireln("Bonjour ", nom, "!")
    ecrireln("Vous avez ", age, " ans.")
fin
```

## Fonctionnalités du langage supportées

### Types de données
- `entier` : nombres entiers
- `reel` : nombres décimaux
- `chaine` : chaînes de caractères
- `caractere` : caractères uniques
- `booleen` : vrai/faux
- `tableau` : tableaux 1D et matrices 2D

### Structures de contrôle
- `si ... alors ... sinonsi ... sinon ... finsi`
- `pour ... de ... à ... faire ... finpour`
- `tantque ... faire ... fintantque`
- `repeter ... jusqua`
- `selon ... cas ... finselon`

### Fonctions intégrées (40+)

**Mathématiques** : abs, racine, puissance (ou ^), arrondi, plancher, plafond, min, max, sin, cos, tan, log, exp, div, mod

**Chaînes** : longueur, majuscule, minuscule, sousChaine, estVide, contient, commence, termine, position

**Caractères** : ord, chr, estLettre, estChiffre, estMajuscule, estMinuscule, versMajuscule, versMinuscule

**Conversions** : versEntier, versReel, versChaine

**Aléatoire** : aleatoire(), aleatoire(max), aleatoire(min, max)

## Compatibilité

- IntelliJ IDEA 2023.2+ (Community ou Ultimate)
- Nécessite Java 21+
- Compatible avec Windows, macOS et Linux

## Support et contribution

- 🐛 **Issues** : [GitHub Issues](https://github.com/oughzal/Algo-compiler/issues)
- 📖 **Documentation** : [Guide complet](https://oughzal.github.io/Algo-compiler/)
- 💬 **Discussions** : [GitHub Discussions](https://github.com/oughzal/Algo-compiler/discussions)

## Licence

MIT License - © 2024 Omar OUGHZAL

## Changelog

### Version 1.6.0 (2024-12-06)
- ✨ Version initiale du plugin
- 🎨 Coloration syntaxique complète
- 🚀 Support de l'exécution directe
- 💡 Autocomplétion intelligente
- 📝 Support des commentaires
- 🔧 Configuration du compilateur

## Ressources

- [Site web](https://oughzal.github.io/Algo-compiler/)
- [Compilateur](https://github.com/oughzal/Algo-compiler)
- [Extension VS Code](https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler)

