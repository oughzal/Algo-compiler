# 🚀 Installation immédiate - IntelliJ IDEA déjà installé

Puisque IntelliJ IDEA est déjà installé sur votre PC, voici comment installer et tester le plugin **immédiatement** !

---

## ⚡ Option 1 : Installation automatique (RECOMMANDÉ)

### Exécutez simplement ce script :

```powershell
cd "C:\Users\Omar\Documents\Algo-compiler\algo-compiler-intellij"
.\install-plugin.ps1
```

**Ce script va :**
- ✅ Vérifier si le build est terminé (sinon le lancer)
- ✅ Localiser votre installation IntelliJ IDEA
- ✅ Afficher les instructions étape par étape
- ✅ Proposer d'ouvrir l'explorateur et IntelliJ IDEA

---

## ⚡ Option 2 : Installation manuelle (si le build est terminé)

### Étape 1 : Vérifier le build

```powershell
cd "C:\Users\Omar\Documents\Algo-compiler\algo-compiler-intellij"
ls build\distributions\
```

**Fichier attendu :** `algo-compiler-intellij-1.6.0.zip`

Si le fichier n'existe pas, lancez le build :
```powershell
..\gradlew.bat buildPlugin
```

### Étape 2 : Installer dans IntelliJ IDEA

1. **Ouvrez IntelliJ IDEA**

2. **Menu Plugins**
   ```
   File → Settings (Ctrl+Alt+S)
   → Plugins (dans le menu de gauche)
   ```

3. **Installer depuis le disque**
   - Cliquez sur l'icône ⚙️ (roue dentée) en haut à droite
   - Sélectionnez `Install Plugin from Disk...`
   
4. **Sélectionnez le fichier**
   ```
   C:\Users\Omar\Documents\Algo-compiler\algo-compiler-intellij\build\distributions\algo-compiler-intellij-1.6.0.zip
   ```

5. **Redémarrez IntelliJ IDEA**
   - Cliquez sur `Restart IDE` quand demandé

### Étape 3 : Configurer le compilateur

1. **Ouvrez les Settings**
   ```
   File → Settings (Ctrl+Alt+S)
   → Tools → Algo Compiler
   ```

2. **Définir le chemin du JAR**
   - Cliquez sur le bouton `...` à côté de "Chemin du compilateur JAR"
   - Naviguez vers :
   ```
   C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.6.0.jar
   ```

3. **Appliquer**
   - Cliquez sur `Apply` puis `OK`

### Étape 4 : Tester !

1. **Créer un fichier test**
   ```
   File → New → File
   → Nommez-le : test.algo
   ```

2. **Écrire du code**
   ```algo
   algorithme MonPremierTest
   variables
       nom : chaine
       age : entier
   
   debut
       ecrire("Votre nom : ")
       lire(nom)
       ecrire("Votre âge : ")
       lire(age)
       
       ecrireln("Bonjour ", majuscule(nom), " !")
       ecrireln("Dans 10 ans vous aurez ", age + 10, " ans.")
       
       // Test de fonction
       ecrireln("Longueur de votre nom : ", longueur(nom))
   fin
   ```

3. **Exécuter**
   - Appuyez sur `Ctrl+Shift+F10`
   - Ou clic droit → `Run 'test'`

4. **Vérifier le résultat**
   - La console s'ouvre en bas
   - Entrez les valeurs
   - Admirez le résultat ! 🎉

---

## 🎨 Fonctionnalités à tester

### 1. Coloration syntaxique

Dès que vous ouvrez le fichier `.algo`, vous devriez voir :
- ✅ Mots-clés en **gras** et colorés (`algorithme`, `debut`, `si`, etc.)
- ✅ Types colorés différemment (`entier`, `reel`, `chaine`)
- ✅ Chaînes et nombres colorés
- ✅ Commentaires en gris

### 2. Autocomplétion

Testez ces touches magiques :

**Dans le code :**
```algo
si     [Ctrl+Space]  → Suggestions : si, sinon, sinonsi
pour   [Ctrl+Space]  → Suggestion complète de structure
abs    [Ctrl+Space]  → abs() avec curseur entre ()
maj    [Ctrl+Space]  → majuscule()
```

**Liste complète des fonctions suggérées :**
- Mathématiques : `abs`, `racine`, `puissance`, `arrondi`, etc.
- Chaînes : `longueur`, `majuscule`, `minuscule`, etc.
- Caractères : `ord`, `chr`, `estLettre`, etc.
- Conversions : `versEntier`, `versReel`, `versChaine`
- Aléatoire : `aleatoire`

### 3. Commentaires rapides

```algo
// Sélectionnez une ou plusieurs lignes
// Appuyez sur Ctrl+/
// → Les lignes sont commentées avec //
// Réappuyez pour décommenter
```

### 4. Exécution

**3 façons d'exécuter :**
1. `Ctrl+Shift+F10` (raccourci rapide)
2. Clic droit → `Run 'nom_fichier'`
3. Menu `Run` → `Run...` → Sélectionner la configuration

---

## 🔧 Si le build n'est pas encore terminé

Le build du plugin télécharge IntelliJ IDEA SDK (~750 MB) la première fois.

**Temps estimé :**
- Première fois : 10-15 minutes (dépend de votre connexion)
- Builds suivants : 1-2 minutes

**Pendant le build, vous pouvez :**
1. Laisser le build se terminer en arrière-plan
2. Lire la documentation :
   - `algo-compiler-intellij\README.md`
   - `algo-compiler-intellij\QUICK_START.md`
3. Préparer des exemples de code Algo

**Pour vérifier l'avancement :**
```powershell
cd "C:\Users\Omar\Documents\Algo-compiler\algo-compiler-intellij"
ls build\distributions\
```

---

## ✅ Checklist post-installation

Après installation, vérifiez que tout fonctionne :

- [ ] Le plugin apparaît dans `Settings → Plugins` avec le nom "Algo Compiler"
- [ ] Les fichiers `.algo` ont une icône spéciale (lettre A bleue)
- [ ] La coloration syntaxique fonctionne
- [ ] L'autocomplétion propose des mots-clés (`Ctrl+Space`)
- [ ] Les commentaires fonctionnent (`Ctrl+/`)
- [ ] L'exécution fonctionne (`Ctrl+Shift+F10`)
- [ ] La console affiche correctement la sortie

---

## 🎯 Exemples de code à tester

### Exemple 1 : Matrices (Nouveau !)
```algo
algorithme TestMatrice
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
    ecrireln("Matrice 3x3 :")
    pour i de 0 à 2 faire
        pour j de 0 à 2 faire
            ecrire(M[i][j], " ")
        finpour
        ecrireln()
    finpour
fin
```

### Exemple 2 : Caractères et ord/chr
```algo
algorithme TestCaracteres
variables
    c : caractere
    code : entier

debut
    c = 'A'
    ecrireln("Caractère : ", c)
    
    code = ord(c)
    ecrireln("Code ASCII de 'A' : ", code)
    
    ecrireln("chr(66) = ", chr(66))
    ecrireln("chr(67) = ", chr(67))
    
    // Test avec boucle
    ecrire("Alphabet : ")
    pour code de 65 à 90 faire
        ecrire(chr(code), " ")
    finpour
    ecrireln()
fin
```

### Exemple 3 : Opérateur puissance ^
```algo
algorithme TestPuissance
variables
    base, exposant, resultat : entier

debut
    ecrire("Base : ")
    lire(base)
    ecrire("Exposant : ")
    lire(exposant)
    
    resultat = base ^ exposant
    ecrireln(base, " ^ ", exposant, " = ", resultat)
    
    // Aussi disponible : base ** exposant
fin
```

---

## 🆘 Besoin d'aide ?

### Le plugin n'apparaît pas
1. Vérifiez dans `Settings → Plugins` qu'il est bien activé
2. Redémarrez IntelliJ IDEA
3. Réinstallez le plugin si nécessaire

### Pas de coloration syntaxique
1. Fermez et rouvrez le fichier `.algo`
2. Clic droit → `Associate with File Type...` → "Algo"

### Erreur lors de l'exécution
1. Vérifiez que Java 21+ est installé : `java -version`
2. Vérifiez le chemin du JAR dans `Settings → Tools → Algo Compiler`
3. Vérifiez que le fichier `.algo` est syntaxiquement correct

### Le build ne se termine pas
1. Soyez patient (première fois ~15 min)
2. Vérifiez votre connexion Internet
3. Si bloqué, interrompre (Ctrl+C) et relancer

---

## 📚 Documentation

- **README complet** : `algo-compiler-intellij\README.md`
- **Guide installation** : `algo-compiler-intellij\INSTALLATION.md`
- **Quick start** : `algo-compiler-intellij\QUICK_START.md`
- **Aide langage** : `..\HELP.md`

---

## 🎉 Prêt !

Une fois le plugin installé et configuré, vous avez un environnement de développement professionnel pour Algo !

**Avantages par rapport à VS Code :**
- 🎯 IDE plus puissant
- 🔍 Meilleure navigation
- 🛠️ Outils avancés
- 💻 Inspection de code

**Bon codage en Algo ! 🚀**

---

© 2024 Omar OUGHZAL - MIT License

