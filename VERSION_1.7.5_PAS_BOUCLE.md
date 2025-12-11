# Version 1.7.5 - Support du Pas dans les Boucles Pour

**Date** : 11 décembre 2025

## ✨ Nouvelles Fonctionnalités

### 🔄 Support du Pas (Step) dans la Boucle `pour`

Vous pouvez maintenant spécifier un **pas** (step) dans les boucles `pour`, permettant des incréments ou décréments personnalisés.

#### Syntaxe

```algo
pour variable de debut à fin pas increment faire
    ...
finpour
```

#### Comportement

- **Pas par défaut** :
  - Si `pas` n'est pas spécifié et `debut ≤ fin` → pas = **1** (boucle ascendante)
  - Si `pas` n'est pas spécifié et `debut > fin` → pas = **-1** (boucle descendante)
- **Pas personnalisé** :
  - Le pas peut être **positif** pour une boucle ascendante
  - Le pas peut être **négatif** pour une boucle descendante
  - Le pas **ne peut pas être 0** (erreur d'exécution)

#### Exemples

##### Boucle ascendante avec pas par défaut (1)
```algo
pour i de 0 à 10 faire
    ecrire(i, " ")  // 0 1 2 3 4 5 6 7 8 9 10
finpour
```

##### Boucle descendante avec pas par défaut (-1)
```algo
pour i de 10 à 0 faire
    ecrire(i, " ")  // 10 9 8 7 6 5 4 3 2 1 0
finpour
```

##### Boucle avec pas positif explicite
```algo
// Nombres pairs de 0 à 20
pour i de 0 à 20 pas 2 faire
    ecrire(i, " ")  // 0 2 4 6 8 10 12 14 16 18 20
finpour
```

##### Boucle avec pas négatif explicite
```algo
// Compte à rebours par pas de -2
pour i de 20 à 0 pas -2 faire
    ecrire(i, " ")  // 20 18 16 14 12 10 8 6 4 2 0
finpour
```

##### Boucle avec pas -5
```algo
pour i de 100 à 50 pas -5 faire
    ecrire(i, " ")  // 100 95 90 85 80 75 70 65 60 55 50
finpour
```

#### Cas d'Usage Pratiques

##### 1. Afficher les nombres pairs
```algo
pour i de 0 à 100 pas 2 faire
    ecrireln(i)
finpour
```

##### 2. Afficher les multiples de 5
```algo
pour i de 0 à 50 pas 5 faire
    ecrireln(i)
finpour
```

##### 3. Compte à rebours
```algo
ecrireln("Décollage dans:")
pour i de 10 à 0 faire  // pas -1 par défaut
    ecrireln(i)
finpour
ecrireln("Décollage !")
```

##### 4. Parcourir un tableau tous les 2 éléments
```algo
variables
    T : tableau[10] de entier
    i : entier
debut
    T = [1,2,3,4,5,6,7,8,9,10]
    
    pour i de 0 à 9 pas 2 faire
        ecrireln(T[i])  // 1 3 5 7 9
    finpour
fin
```

## 🔧 Extension VS Code

### Nouveau Snippet

- **`pourpas`** → Boucle pour avec pas
  ```algo
  pour i de 0 à 10 pas 1 faire
      ...
  finpour
  ```

## 📝 Documentation

- Mise à jour du fichier `HELP.md` avec la documentation complète du pas
- Mise à jour du `README.md` avec des exemples
- Ajout de `exemples/exemple_pas.algo` avec des cas d'usage complets

## 🧪 Tests

Tous les tests ont été effectués avec succès :
- ✅ Boucle ascendante avec pas par défaut
- ✅ Boucle descendante avec pas par défaut  
- ✅ Boucle avec pas positif explicite
- ✅ Boucle avec pas négatif explicite
- ✅ Validation du pas = 0 (erreur)

## 📦 Installation

1. **Compiler** : Le JAR a été recompilé avec les nouveaux changements
2. **Extension VS Code** : Version 1.7.5 générée et installée
3. **Documentation** : Tous les fichiers d'aide mis à jour

---

**Notes** : Cette fonctionnalité rend les boucles `pour` beaucoup plus flexibles et proches des langages modernes tout en gardant la syntaxe algorithmique claire et pédagogique.

