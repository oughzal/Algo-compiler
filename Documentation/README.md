# 📚 Documentation Algo Compiler

Documentation complète du compilateur Algo avec cours d'algorithmique intégré.

## 📖 Contenu

Cette documentation propose un **cours complet d'algorithmique** utilisant le langage Algo, avec :

- ✨ **Syntax highlighter JavaScript personnalisé** pour la coloration syntaxique
- 📱 **Design responsive** adapté aux mobiles et tablettes  
- 🎨 **Interface moderne et intuitive** avec navigation latérale
- 💡 **Exemples interactifs** avec coloration syntaxique en temps réel
- 📝 **Explications détaillées** de chaque concept
- 🎯 **Exercices pratiques** pour s'entraîner

## 🗂️ Structure

```
Documentation/
├── index.html                      # Page d'accueil
├── assets/
│   ├── css/
│   │   └── style.css              # Styles CSS complets
│   └── js/
│       └── algo-highlighter.js    # Syntax highlighter Algo
└── chapitres/
    ├── 01-introduction.html       # Introduction à l'algorithmique
    ├── 02-variables-types.html    # Variables et types de données
    ├── 06-boucles.html            # Les boucles (pour, tantque, répéter)
    └── 18-reference.html          # Référence complète du langage
```

## 🚀 Utilisation

### Visualisation locale

Ouvrez simplement le fichier `index.html` dans votre navigateur :

```bash
# Sous Windows
start index.html

# Sous Linux/Mac
open index.html
```

### Hébergement web

Pour héberger cette documentation sur un serveur web :

1. Copiez le dossier `Documentation/` sur votre serveur
2. Assurez-vous que le serveur peut servir des fichiers HTML, CSS et JS
3. Accédez à `index.html` depuis votre navigateur

### GitHub Pages

Pour publier sur GitHub Pages :

1. Poussez le dossier `Documentation/` dans votre repository
2. Allez dans Settings → Pages
3. Sélectionnez la branche et le dossier `Documentation/`
4. La documentation sera accessible à `https://[username].github.io/[repo]/Documentation/`

## 🎨 Personnalisation

### Thème de couleurs

Les couleurs sont définies dans `assets/css/style.css` via des variables CSS :

```css
:root {
    --primary-color: #0d6efd;
    --algo-keyword: #d73a49;
    --algo-type: #005cc5;
    --algo-string: #22863a;
    /* ... */
}
```

Modifiez ces variables pour personnaliser le thème.

### Syntax Highlighter

Le syntax highlighter est dans `assets/js/algo-highlighter.js`. Il reconnaît automatiquement :

- 🔤 Les mots-clés Algo (`algorithme`, `si`, `pour`, etc.)
- 🎨 Les types de données (`entier`, `reel`, `chaine`, etc.)
- 🔢 Les nombres et chaînes
- 💬 Les commentaires (`//` et `#`)
- ⚙️ Les fonctions intégrées (`ecrire`, `lire`, `abs`, etc.)

#### Utilisation

Pour appliquer la coloration syntaxique, utilisez :

```html
<pre><code class="algo">
algorithme MonAlgo
debut
    ecrireln("Hello World!")
fin
</code></pre>
```

Le highlighter s'active automatiquement au chargement de la page.

#### Avec numéros de ligne

```html
<pre data-line-numbers><code class="algo">
algorithme Exemple
debut
    x = 10
fin
</code></pre>
```

## 📚 Chapitres disponibles

### Introduction
- 📚 Qu'est-ce que l'algorithmique ?
- 🏗️ Structure d'un algorithme
- 💬 Premier programme

### Les Bases
- 🔢 Variables et types de données
- ➕ Opérateurs arithmétiques et logiques
- 💬 Entrées et sorties

### Structures de Contrôle
- 🔀 Conditions (Si...Alors...Sinon)
- 🔄 Boucles (Pour, TantQue, Répéter)
- 🎯 Instruction Selon (Switch)

### Structures de Données
- 📊 Tableaux 1D
- 🔢 Matrices (tableaux 2D)
- 📝 Chaînes de caractères

### Fonctions
- ⚙️ Fonctions et procédures
- 🛠️ Fonctions intégrées
- 🔗 Passage par référence

### Avancé
- ♻️ Récursivité
- 🚀 Algorithmes classiques

### Ressources
- 💡 Exemples complets
- ✏️ Exercices avec solutions
- 📖 Référence complète du langage

## 🛠️ Technologies utilisées

- **HTML5** - Structure des pages
- **CSS3** - Styles et mise en page responsive
- **JavaScript (Vanilla)** - Syntax highlighter personnalisé
- **Aucune dépendance externe** - Fonctionne sans connexion Internet

## 📱 Compatibilité

- ✅ Chrome / Edge (dernières versions)
- ✅ Firefox (dernières versions)
- ✅ Safari (dernières versions)
- ✅ Mobile (iOS / Android)

## 🤝 Contribution

Pour ajouter de nouveaux chapitres :

1. Créez un fichier HTML dans `chapitres/`
2. Utilisez la structure des chapitres existants
3. Ajoutez le lien dans le menu de `index.html`
4. Utilisez `<code class="algo">` pour la coloration syntaxique

## 📄 Licence

© 2026 Omar OUGHZAL - Tous droits réservés

Cette documentation fait partie du projet [Algo Compiler](https://github.com/oughzal/Algo-compiler).

## 🔗 Liens utiles

- 🌐 [Site web du projet](https://oughzal.github.io/Algo-compiler)
- 💻 [Repository GitHub](https://github.com/oughzal/Algo-compiler)
- 🧩 [Extension VS Code](https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler)
- 📥 [Télécharger le compilateur](https://github.com/oughzal/Algo-compiler/releases)

## ✨ Fonctionnalités du Syntax Highlighter

### Support complet du langage Algo

- ✅ Tous les mots-clés (algorithme, si, pour, tantque, etc.)
- ✅ Types de données (entier, reel, chaine, etc.)
- ✅ Opérateurs arithmétiques et logiques
- ✅ Fonctions intégrées (mathématiques, chaînes, caractères)
- ✅ Chaînes de caractères et caractères
- ✅ Nombres (entiers et décimaux)
- ✅ Commentaires (// et #)
- ✅ Constantes booléennes (vrai/faux)

### Fonctionnalités avancées

- 🎨 Coloration syntaxique en temps réel
- 🔢 Numéros de ligne optionnels
- 📱 Responsive et adapté mobile
- ⚡ Performance optimisée (pas de dépendance externe)
- 🌈 Thème de couleurs personnalisable

---

**Fait avec ❤️ pour faciliter l'apprentissage de l'algorithmique**

