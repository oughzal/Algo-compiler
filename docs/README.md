# Documentation GitHub Pages - Algo-Compiler

Ce dossier contient les fichiers pour le site web GitHub Pages du projet Algo-Compiler.

## 🌐 Site Web

Le site est accessible à : **https://algo-compiler.github.io** (ou votre URL personnalisée)

## 📁 Structure des fichiers

```
docs/
├── index.html          # Page principale du site
├── style.css           # Styles CSS
├── script.js           # JavaScript pour les interactions
├── _config.yml         # Configuration GitHub Pages
└── README.md           # Ce fichier
```

## 🚀 Configuration GitHub Pages

### Activation

1. Allez dans **Settings** de votre dépôt GitHub
2. Naviguez vers **Pages** dans le menu latéral
3. Dans **Source**, sélectionnez :
   - Branch : `main` (ou `master`)
   - Folder : `/docs`
4. Cliquez sur **Save**

Le site sera déployé automatiquement à chaque push sur la branche principale.

### URL personnalisée (optionnel)

Pour utiliser un domaine personnalisé :

1. Créez un fichier `CNAME` dans le dossier `docs/` avec votre domaine :
   ```
   www.algo-compiler.com
   ```

2. Configurez les DNS chez votre registrar :
   ```
   Type    Name    Value
   CNAME   www     algo-compiler.github.io
   ```

3. Attendez la propagation DNS (peut prendre jusqu'à 24h)

## 🎨 Personnalisation

### Modifier le contenu

Éditez `index.html` pour modifier :
- Textes et descriptions
- Exemples de code
- Liens vers la documentation
- Statistiques

### Modifier les styles

Éditez `style.css` pour personnaliser :
- Couleurs (variables CSS dans `:root`)
- Polices
- Espacements
- Animations

### Modifier les interactions

Éditez `script.js` pour ajouter/modifier :
- Comportement des onglets
- Animations au scroll
- Menu mobile
- Autres fonctionnalités JavaScript

## 🔧 Variables CSS principales

Dans `style.css`, vous pouvez modifier les couleurs principales :

```css
:root {
    --primary-color: #2563eb;      /* Bleu principal */
    --secondary-color: #7c3aed;     /* Violet secondaire */
    --accent-color: #10b981;        /* Vert accent */
    --dark-bg: #1e293b;             /* Fond sombre */
    --light-bg: #f8fafc;            /* Fond clair */
}
```

## 📱 Design Responsive

Le site est entièrement responsive et s'adapte à :
- 📱 Mobile (< 480px)
- 📱 Tablette (480px - 768px)
- 💻 Desktop (> 768px)

## 🧪 Test en local

Pour tester le site en local avant de pousser sur GitHub :

### Option 1 : Serveur HTTP simple (Python)
```bash
cd docs
python -m http.server 8000
```
Puis ouvrez : http://localhost:8000

### Option 2 : Live Server (VS Code)
1. Installez l'extension "Live Server"
2. Clic droit sur `index.html` → "Open with Live Server"

### Option 3 : Jekyll (si vous utilisez Jekyll)
```bash
cd docs
bundle exec jekyll serve
```
Puis ouvrez : http://localhost:4000

## 📊 Sections du site

### 1. Hero
- Titre principal
- Statistiques (40 fonctions, 5 types, 30+ exemples)
- Exemple de code
- Boutons d'action

### 2. Fonctionnalités
- 6 cartes présentant les fonctionnalités principales
- Type caractère, 40 fonctions, syntaxe flexible, etc.

### 3. Fonctions intégrées
- Onglets pour les 5 catégories
- Liste de toutes les fonctions avec descriptions

### 4. Installation
- 4 étapes d'installation
- Section dédiée à l'extension VS Code

### 5. Exemples
- 4 exemples de code complets
- Factorielle, caractères, recherche, jeu

### 6. Documentation
- 6 liens vers les docs GitHub
- Guides, références, tutoriels

### 7. Footer
- Liens importants
- Statistiques
- Copyright

## 🔄 Mise à jour du site

Pour mettre à jour le site :

1. Modifiez les fichiers dans le dossier `docs/`
2. Commit et push sur GitHub :
   ```bash
   git add docs/
   git commit -m "Mise à jour du site web"
   git push
   ```
3. GitHub Pages déploiera automatiquement (2-5 minutes)

## 📈 Analytics (optionnel)

Pour ajouter Google Analytics :

1. Créez un compte Google Analytics
2. Obtenez votre ID de suivi (UA-XXXXXXXXX-X ou G-XXXXXXXXXX)
3. Ajoutez dans `_config.yml` :
   ```yaml
   google_analytics: UA-XXXXXXXXX-X
   ```
4. Ou ajoutez directement dans `index.html` avant `</head>` :
   ```html
   <!-- Google Analytics -->
   <script async src="https://www.googletagmanager.com/gtag/js?id=GA_MEASUREMENT_ID"></script>
   <script>
     window.dataLayer = window.dataLayer || [];
     function gtag(){dataLayer.push(arguments);}
     gtag('js', new Date());
     gtag('config', 'GA_MEASUREMENT_ID');
   </script>
   ```

## 🎯 SEO

Le site inclut :
- Meta descriptions
- Meta keywords
- Semantic HTML (header, nav, section, footer)
- Balises Open Graph (à ajouter si nécessaire)

Pour améliorer le SEO, ajoutez dans `<head>` :

```html
<!-- Open Graph / Facebook -->
<meta property="og:type" content="website">
<meta property="og:url" content="https://algo-compiler.github.io/">
<meta property="og:title" content="Algo-Compiler">
<meta property="og:description" content="Compilateur de pseudo-code algorithmique en français">
<meta property="og:image" content="https://algo-compiler.github.io/images/preview.png">

<!-- Twitter -->
<meta property="twitter:card" content="summary_large_image">
<meta property="twitter:url" content="https://algo-compiler.github.io/">
<meta property="twitter:title" content="Algo-Compiler">
<meta property="twitter:description" content="Compilateur de pseudo-code algorithmique en français">
<meta property="twitter:image" content="https://algo-compiler.github.io/images/preview.png">
```

## 🐛 Dépannage

### Le site ne s'affiche pas
1. Vérifiez que GitHub Pages est activé dans Settings
2. Vérifiez que le dossier source est bien `/docs`
3. Attendez 2-5 minutes après le push
4. Videz le cache de votre navigateur (Ctrl+F5)

### Les styles ne s'appliquent pas
1. Vérifiez les chemins dans `index.html` :
   ```html
   <link rel="stylesheet" href="style.css">
   ```
2. Vérifiez que `style.css` est dans le même dossier que `index.html`

### JavaScript ne fonctionne pas
1. Ouvrez la console du navigateur (F12)
2. Vérifiez les erreurs JavaScript
3. Vérifiez que `script.js` est chargé :
   ```html
   <script src="script.js"></script>
   ```

## 📝 Checklist de déploiement

Avant de déployer :

- [ ] Tester en local
- [ ] Vérifier tous les liens
- [ ] Tester sur mobile
- [ ] Vérifier l'orthographe
- [ ] Optimiser les images
- [ ] Tester les animations
- [ ] Vérifier la console (F12) pour les erreurs
- [ ] Commit et push

## 🎉 Résultat

Une fois déployé, votre site sera :
- ✅ Accessible publiquement
- ✅ Responsive (mobile, tablette, desktop)
- ✅ Rapide à charger
- ✅ Professionnel et moderne
- ✅ SEO-friendly
- ✅ Mis à jour automatiquement

---

**Questions ?** Consultez la [documentation GitHub Pages](https://docs.github.com/en/pages)

