# 📸 Images pour l'Installation - Guide

## Images Utilisées dans index.html

### 1. ✅ vscode_settings.png (DISPONIBLE)
- **Localisation** : `C:\Users\Omar\Documents\Algo-compiler\vscode_settings.png`
- **Usage** : Illustre les paramètres de configuration de l'extension dans VS Code
- **Section** : Étape 4 - Configurer l'Extension
- **Statut** : ✅ Présente et utilisée

### 2. ⚠️ vscode_marketplace.png (À AJOUTER)
- **Localisation attendue** : `C:\Users\Omar\Documents\Algo-compiler\vscode_marketplace.png`
- **Usage** : Illustre l'extension sur le Visual Studio Marketplace
- **Section** : Étape 3 - Installer l'Extension VS Code
- **Statut** : ⚠️ Manquante (l'image ne s'affichera pas pour l'instant)

---

## Comment Ajouter vscode_marketplace.png

### Option 1 : Capture d'écran du Marketplace

1. **Ouvrir le navigateur** et aller sur :
   ```
   https://marketplace.visualstudio.com/items?itemName=omarcomputer.algo-compiler
   ```

2. **Prendre une capture d'écran** de l'extension montrant :
   - Le nom "Algo Compiler"
   - L'icône
   - La description
   - Le bouton "Install"
   - Les étoiles/notes
   - Le nombre de téléchargements

3. **Recadrer et optimiser** :
   - Taille recommandée : 800-1000px de largeur
   - Format : PNG
   - Optimiser pour le web (< 200 KB)

4. **Sauvegarder** comme :
   ```
   C:\Users\Omar\Documents\Algo-compiler\vscode_marketplace.png
   ```

### Option 2 : Capture depuis VS Code

1. **Ouvrir VS Code**
2. **Aller dans Extensions** (Ctrl+Shift+X)
3. **Rechercher** "Algo Compiler"
4. **Prendre une capture** de la page d'extension montrant :
   - L'icône et le nom
   - La description
   - Le bouton "Install"
   - Les détails de la version

5. **Sauvegarder** comme `vscode_marketplace.png`

---

## Implémentation dans index.html

### Code Actuel (avec gestion d'erreur)

```html
<!-- Étape 3 - Installer l'Extension -->
<div style="margin-top: 15px; text-align: center;">
    <img src="vscode_marketplace.png" 
         alt="Extension Algo Compiler sur VS Marketplace" 
         style="max-width: 100%; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border: 1px solid #e2e8f0;" 
         onerror="this.style.display='none'">
    <p style="font-size: 0.8rem; color: #64748b; margin-top: 5px; font-style: italic;">
        Extension sur le Visual Studio Marketplace
    </p>
</div>
```

**Note** : L'attribut `onerror="this.style.display='none'"` cache l'image si elle n'est pas trouvée.

### Code pour vscode_settings.png (déjà fonctionnel)

```html
<!-- Étape 4 - Configurer l'Extension -->
<div style="margin-top: 15px; text-align: center;">
    <img src="vscode_settings.png" 
         alt="Configuration de l'extension dans VS Code" 
         style="max-width: 100%; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border: 1px solid #e2e8f0;">
    <p style="font-size: 0.8rem; color: #64748b; margin-top: 5px; font-style: italic;">
        Paramètres de l'extension dans VS Code
    </p>
</div>
```

---

## Styles CSS Appliqués

```css
.install-step img {
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border: 1px solid var(--border-color);
    margin-top: 15px;
    transition: transform 0.3s, box-shadow 0.3s;
}

.install-step img:hover {
    transform: scale(1.02);
    box-shadow: 0 8px 12px rgba(0, 0, 0, 0.15);
}
```

**Effets** :
- ✅ Bordure arrondie
- ✅ Ombre portée
- ✅ Bordure légère
- ✅ Effet de zoom au survol
- ✅ Responsive (s'adapte à la largeur du conteneur)

---

## Recommandations pour les Captures

### Qualité
- **Résolution** : 2x retina (haute résolution)
- **Format** : PNG (transparence possible)
- **Taille** : < 200 KB (optimisé pour le web)
- **Dimensions** : 800-1000px de largeur

### Contenu à Capturer

#### Pour vscode_marketplace.png
- ✅ Logo de l'extension
- ✅ Nom "Algo Compiler"
- ✅ Description courte
- ✅ Bouton "Install"
- ✅ Version (v1.4.0)
- ✅ Nombre d'installations
- ✅ Étoiles/notes (si disponibles)

#### Pour vscode_settings.png (déjà fait)
- ✅ Interface des paramètres VS Code
- ✅ Section "Algo Compiler"
- ✅ Champ "Compiler Path"
- ✅ Exemple de chemin configuré

---

## Alternatives Temporaires

### Si l'image marketplace n'est pas disponible immédiatement

**Option A** : Utiliser l'icône existante temporairement
```html
<img src="icon.png" alt="Extension Algo Compiler" ...>
```

**Option B** : Laisser l'espace vide (gestion d'erreur active)
- L'image sera cachée automatiquement
- Seul le texte descriptif s'affichera

**Option C** : Créer une image de placeholder
- Texte "VS Marketplace" sur fond violet
- Logo VS Code + texte

---

## Vérification

### Liste de Contrôle

- [x] vscode_settings.png présente et fonctionnelle
- [ ] vscode_marketplace.png à ajouter
- [x] Code HTML implémenté avec gestion d'erreur
- [x] CSS pour styles d'images ajouté
- [x] Images responsive
- [x] Effet hover ajouté
- [x] Textes descriptifs présents

---

## Commande Rapide

Pour vérifier les images présentes :
```powershell
ls C:\Users\Omar\Documents\Algo-compiler\*.png
```

Pour ajouter l'image manquante après capture :
```powershell
# Copier l'image capturée
Copy-Item "path\to\screenshot.png" "C:\Users\Omar\Documents\Algo-compiler\vscode_marketplace.png"
```

---

## Résultat Attendu

### Étape 3 (Installer l'Extension)
```
┌─────────────────────────────────────┐
│  [Image: Extension sur Marketplace] │
│                                     │
│  - Logo Algo Compiler               │
│  - Bouton Install                   │
│  - Version v1.4.0                   │
│  - 44+ snippets                     │
└─────────────────────────────────────┘
   Extension sur le Visual Studio
         Marketplace
```

### Étape 4 (Configurer l'Extension)
```
┌─────────────────────────────────────┐
│  [Image: Paramètres VS Code]        │
│                                     │
│  Settings > Algo Compiler           │
│  - Compiler Path: [...]             │
│  - Java Path: [...]                 │
└─────────────────────────────────────┘
   Paramètres de l'extension dans
            VS Code
```

---

## 📝 Notes

1. **Gestion d'erreur active** : Si `vscode_marketplace.png` n'existe pas, l'image sera automatiquement cachée (attribut `onerror`)

2. **Fallback gracieux** : Le texte descriptif s'affichera même si l'image est manquante

3. **Performance** : Les images sont optimisées avec `max-width: 100%` pour le responsive

4. **Accessibilité** : Attributs `alt` descriptifs présents sur toutes les images

---

**Date** : 5 Décembre 2024  
**Statut** : vscode_settings.png ✅ | vscode_marketplace.png ⚠️ (à ajouter)  
**Impact** : Aucun (gestion d'erreur en place)

