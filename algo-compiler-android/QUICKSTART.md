# Guide de Démarrage Rapide - Algo Compiler Mobile

## Installation Rapide

### Depuis Android Studio (Développeurs)

1. **Prérequis**
   - Installer Android Studio
   - Installer JDK 17+
   - Configurer Android SDK 34

2. **Cloner et Ouvrir**
   ```bash
   git clone https://github.com/oughzal/Algo-compiler.git
   cd Algo-compiler/algo-compiler-android
   ```
   Ouvrir dans Android Studio

3. **Exécuter**
   - Connecter un appareil ou lancer un émulateur
   - Cliquer sur Run (▶️)

### Depuis un APK (Utilisateurs)

1. Télécharger l'APK depuis [Releases](https://github.com/oughzal/Algo-compiler/releases)
2. Autoriser l'installation depuis sources inconnues
3. Installer l'APK
4. Lancer l'application

## Premier Algorithme

### 1. Créer un Fichier
- Aller dans l'onglet **Fichiers**
- Appuyer sur le bouton **+** (flottant en bas)
- Entrer le nom : `premier`
- Cliquer sur **Créer**

### 2. Écrire du Code
```algo
algorithme premier
variables
    nom : chaine
    age : entier
debut
    ecrire("Votre nom : ")
    lire(nom)
    ecrire("Votre âge : ")
    lire(age)
    
    ecrireln()
    ecrireln("Bonjour ", nom, " !")
    ecrireln("Vous avez ", age, " ans.")
fin
```

### 3. Exécuter
- Aller dans l'onglet **Éditeur**
- Appuyer sur le bouton **Exécuter** (▶️)
- Aller dans l'onglet **Console**
- Voir les résultats !

## Exemples d'Algorithmes

### Calcul de la Moyenne

```algo
algorithme moyenne
variables
    n : entier
    somme : reel
    note : reel
    moy : reel
    i : entier
debut
    ecrire("Nombre de notes : ")
    lire(n)
    
    somme = 0
    pour i de 1 à n faire
        ecrire("Note ", i, " : ")
        lire(note)
        somme = somme + note
    finpour
    
    moy = somme / n
    ecrireln("Moyenne : ", moy)
fin
```

### Tri d'un Tableau

```algo
algorithme tri_bulles
variables
    T : tableau[10] de entier
    n, i, j, temp : entier
debut
    n = 10
    
    // Remplissage
    pour i de 0 à n-1 faire
        ecrire("T[", i, "] = ")
        lire(T[i])
    finpour
    
    // Tri à bulles
    pour i de 0 à n-2 faire
        pour j de 0 à n-i-2 faire
            si T[j] > T[j+1] alors
                temp = T[j]
                T[j] = T[j+1]
                T[j+1] = temp
            finsi
        finpour
    finpour
    
    // Affichage
    ecrireln("Tableau trié :")
    pour i de 0 à n-1 faire
        ecrire(T[i], " ")
    finpour
fin
```

### Nombres Premiers

```algo
algorithme nombres_premiers
variables
    n, i, j : entier
    est_premier : booleen
debut
    ecrire("Nombres premiers jusqu'à : ")
    lire(n)
    
    ecrireln("Nombres premiers :")
    pour i de 2 à n faire
        est_premier = vrai
        
        j = 2
        tantque j * j <= i et est_premier faire
            si i mod j = 0 alors
                est_premier = faux
            finsi
            j = j + 1
        fintantque
        
        si est_premier alors
            ecrire(i, " ")
        finsi
    finpour
fin
```

## Organisation des Fichiers

### Créer des Dossiers

1. Onglet **Fichiers**
2. Bouton **📁** (nouveau dossier)
3. Entrer le nom du dossier
4. Les fichiers peuvent être créés dans ce dossier

### Structure Recommandée

```
AlgoCompiler/
├── exercices/
│   ├── exercice1.algo
│   ├── exercice2.algo
│   └── ...
├── projets/
│   ├── tri/
│   │   ├── tri_bulles.algo
│   │   └── tri_insertion.algo
│   └── calculs/
│       ├── moyenne.algo
│       └── stats.algo
└── tests/
    ├── test1.algo
    └── test2.algo
```

## Astuces

### 1. Sauvegarde Automatique
- Les fichiers sont automatiquement sauvegardés lors de l'exécution
- Utiliser le bouton 💾 pour sauvegarder manuellement

### 2. Console
- Effacer la console avec le bouton 🗑️
- Faire défiler pour voir tout l'historique
- Les erreurs sont affichées en rouge

### 3. Navigation
- Utiliser les onglets en bas pour naviguer
- **Fichiers** : Gestion des fichiers
- **Éditeur** : Écrire du code
- **Console** : Voir les résultats

### 4. Édition
- Les numéros de ligne aident à repérer les erreurs
- L'éditeur supporte le copier-coller
- Zoom possible selon l'appareil

## Résolution de Problèmes

### L'application ne démarre pas
- Vérifier la version Android (minimum 7.0)
- Réinstaller l'application
- Vider le cache

### Erreur d'exécution
- Vérifier la syntaxe dans l'éditeur
- Consulter le message d'erreur dans la console
- Vérifier les numéros de ligne

### Fichiers non visibles
- Vérifier les permissions de stockage
- Redémarrer l'application
- Vérifier l'emplacement : Documents/AlgoCompiler

### Performance lente
- Fermer les autres applications
- Éviter les algorithmes trop complexes
- Limiter la taille des tableaux

## Support

Pour obtenir de l'aide :
- Consulter la [documentation complète](https://oughzal.github.io/Algo-compiler)
- Voir les [exemples sur GitHub](https://github.com/oughzal/Algo-compiler/tree/main/examples)
- Signaler un bug sur [GitHub Issues](https://github.com/oughzal/Algo-compiler/issues)

## Prochaines Fonctionnalités

- [ ] Coloration syntaxique avancée
- [ ] Auto-complétion
- [ ] Débogueur interactif
- [ ] Import/Export de fichiers
- [ ] Thèmes personnalisables
- [ ] Mode sombre/clair manuel
- [ ] Partage d'algorithmes
- [ ] Historique d'exécution

## Contributeurs

Développé par **Omar OUGHZAL**

Contributions bienvenues ! 🎉

