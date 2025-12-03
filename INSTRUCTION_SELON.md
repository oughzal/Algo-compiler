# Instruction SELON (Switch) - Style Kotlin

## 🎯 Nouvelle Fonctionnalité

L'instruction `selon` (équivalent du `switch` ou `when` dans d'autres langages) a été ajoutée au compilateur Algo. Elle utilise une syntaxe inspirée de Kotlin avec le format `valeur : action`.

---

## 📝 Syntaxe

```algo
selon(expression)
    cas valeur1: instruction1
    cas valeur2: instruction2
    cas valeur3:
        instruction3a
        instruction3b
    defaut: instructionDefaut
finselon
```

### Éléments de la Syntaxe

- **`selon(expression)`** : Expression à évaluer (variable ou expression)
- **`cas valeur:`** : Cas à tester (valeur suivie de deux-points)
- **`defaut:`** : Cas par défaut (optionnel, exécuté si aucun cas ne correspond)
- **`finselon`** : Fin de l'instruction selon

---

## ✨ Caractéristiques

### 1. Break Automatique
Contrairement au `switch` en C/Java, l'instruction `selon` inclut un **break automatique**. Dès qu'un cas correspond, seules ses instructions sont exécutées, puis le contrôle sort de l'instruction `selon`.

### 2. Types Supportés
- **Entiers** : `cas 1:`, `cas 42:`
- **Chaînes** : `cas "lundi":`, `cas "rouge":`
- **Caractères** : `cas 'A':`, `cas 'x':`
- **Expressions** : `cas x + 1:` (toute expression valide)

### 3. Instructions Multiples
Un cas peut contenir plusieurs instructions sans accolades :
```algo
cas 1:
    ecrireln("Première ligne")
    ecrireln("Deuxième ligne")
    x = x + 1
```

### 4. Cas par Défaut Optionnel
Le cas `defaut` est optionnel. S'il est absent et qu'aucun cas ne correspond, rien ne se passe.

---

## 💡 Exemples

### Exemple 1 : Jour de la Semaine

```algo
algorithme JourSemaine

variables
    jour : entier

debut
    ecrire("Entrez un jour (1-7): ")
    lire(jour)
    
    selon(jour)
        cas 1: ecrireln("Lundi")
        cas 2: ecrireln("Mardi")
        cas 3: ecrireln("Mercredi")
        cas 4: ecrireln("Jeudi")
        cas 5: ecrireln("Vendredi")
        cas 6: ecrireln("Samedi")
        cas 7: ecrireln("Dimanche")
        defaut: ecrireln("Jour invalide")
    finselon
fin
```

### Exemple 2 : Menu

```algo
algorithme Menu

variables
    choix : entier

debut
    ecrireln("=== MENU ===")
    ecrireln("1. Ajouter")
    ecrireln("2. Modifier")
    ecrireln("3. Supprimer")
    ecrireln("4. Quitter")
    ecrire("Votre choix: ")
    lire(choix)
    
    selon(choix)
        cas 1: ecrireln("Mode ajout")
        cas 2: ecrireln("Mode modification")
        cas 3: ecrireln("Mode suppression")
        cas 4: ecrireln("Au revoir !")
        defaut: ecrireln("Choix invalide")
    finselon
fin
```

### Exemple 3 : Chaînes de Caractères

```algo
algorithme CouleurPreferee

variables
    couleur : chaine

debut
    ecrire("Quelle est votre couleur préférée? ")
    lire(couleur)
    
    selon(couleur)
        cas "rouge": ecrireln("Couleur de la passion")
        cas "bleu": ecrireln("Couleur de la sérénité")
        cas "vert": ecrireln("Couleur de la nature")
        cas "jaune": ecrireln("Couleur du soleil")
        defaut: ecrireln("Jolie couleur: ", couleur)
    finselon
fin
```

### Exemple 4 : Instructions Multiples

```algo
algorithme NoteEtudiant

variables
    note : entier

debut
    ecrire("Entrez la note: ")
    lire(note)
    
    selon(note)
        cas 20:
            ecrireln("EXCELLENT !")
            ecrireln("Note maximale atteinte")
            ecrireln("Félicitations !")
        cas 19:
            ecrireln("Très bien !")
            ecrireln("Continue comme ça")
        cas 18:
            ecrireln("Bien !")
        cas 10:
            ecrireln("Moyen")
            ecrireln("Tu peux mieux faire")
        cas 0:
            ecrireln("Insuffisant")
            ecrireln("Il faut réviser")
        defaut:
            ecrireln("Note: ", note, "/20")
    finselon
fin
```

### Exemple 5 : Calculatrice

```algo
algorithme Calculatrice

variables
    operation : caractere
    a, b, resultat : reel

debut
    ecrire("Entrez le premier nombre: ")
    lire(a)
    
    ecrire("Entrez l'opération (+, -, *, /): ")
    lire(operation)
    
    ecrire("Entrez le deuxième nombre: ")
    lire(b)
    
    selon(operation)
        cas '+':
            resultat = a + b
            ecrireln(a, " + ", b, " = ", resultat)
        cas '-':
            resultat = a - b
            ecrireln(a, " - ", b, " = ", resultat)
        cas '*':
            resultat = a * b
            ecrireln(a, " * ", b, " = ", resultat)
        cas '/':
            si b == 0 alors
                ecrireln("Erreur: division par zéro")
            sinon
                resultat = a / b
                ecrireln(a, " / ", b, " = ", resultat)
            finsi
        defaut:
            ecrireln("Opération invalide")
    finselon
fin
```

---

## 🔄 Comparaison avec SI...ALORS

### Avec SI...ALORS (ancien style)

```algo
si choix == 1 alors
    ecrireln("Option 1")
sinon si choix == 2 alors
    ecrireln("Option 2")
sinon si choix == 3 alors
    ecrireln("Option 3")
sinon
    ecrireln("Option invalide")
finsi
```

### Avec SELON (nouveau style - plus clair)

```algo
selon(choix)
    cas 1: ecrireln("Option 1")
    cas 2: ecrireln("Option 2")
    cas 3: ecrireln("Option 3")
    defaut: ecrireln("Option invalide")
finselon
```

---

## ⚙️ Implémentation Technique

### Tokens Ajoutés
- `SELON` : Début de l'instruction
- `CAS` : Définit un cas
- `DEFAUT` : Cas par défaut
- `FINSELON` : Fin de l'instruction

### Mots-clés
- `selon` : Mot-clé principal
- `cas` : Définir un cas
- `defaut` : Cas par défaut
- `finselon` : Terminer l'instruction

### AST
```kotlin
data class WhenStatement(
    val expression: Expression,
    val cases: List<WhenCase>,
    val defaultCase: List<Statement>?
)

data class WhenCase(
    val value: Expression,
    val statements: List<Statement>
)
```

---

## 🎯 Règles et Limitations

### ✅ Autorisé

1. **Plusieurs cas avec la même valeur** : Non (seul le premier sera exécuté)
2. **Cas vide** : Oui (pas d'instruction après le `:`)
3. **Expression comme valeur de cas** : Oui (`cas x + 1:`)
4. **Défaut au milieu** : Non (doit être à la fin)
5. **Instructions imbriquées** : Oui (si, pour, tantque, etc.)

### ❌ Erreurs Communes

1. **Oublier les deux-points** après la valeur du cas
   ```algo
   cas 1 ecrireln("Test")  // ❌ Erreur
   cas 1: ecrireln("Test")  // ✅ Correct
   ```

2. **Oublier finselon**
   ```algo
   selon(x)
       cas 1: ecrireln("Un")
   // ❌ Manque finselon
   ```

3. **Défaut sans deux-points**
   ```algo
   defaut ecrireln("Défaut")  // ❌ Erreur
   defaut: ecrireln("Défaut")  // ✅ Correct
   ```

---

## 📊 Comparaison avec d'Autres Langages

### Kotlin (inspiration)
```kotlin
when (x) {
    1 -> println("Un")
    2 -> println("Deux")
    else -> println("Autre")
}
```

### Algo (notre syntaxe)
```algo
selon(x)
    cas 1: ecrireln("Un")
    cas 2: ecrireln("Deux")
    defaut: ecrireln("Autre")
finselon
```

### Java (switch)
```java
switch (x) {
    case 1:
        System.out.println("Un");
        break;
    case 2:
        System.out.println("Deux");
        break;
    default:
        System.out.println("Autre");
}
```

**Avantages de notre syntaxe** :
- ✅ Break automatique (pas besoin de `break`)
- ✅ Syntaxe claire avec `:`
- ✅ Mot-clé `defaut` en français
- ✅ Instructions multiples sans accolades

---

## 🧪 Tests

### Fichiers de Test Inclus

1. **test_selon_simple.algo** : Test basique avec entiers
2. **test_selon.algo** : Tests complets (entiers, chaînes, instructions multiples)

### Exécuter les Tests

```bash
# Test simple
java -jar build/libs/algo-compiler-1.0.0.jar examples/test_selon_simple.algo

# Test complet
java -jar build/libs/algo-compiler-1.0.0.jar examples/test_selon.algo
```

---

## 📚 Documentation Associée

- **README.md** : Guide utilisateur principal
- **QUICKSTART.md** : Démarrage rapide
- **examples/** : Exemples de code

---

## ✅ Résumé

L'instruction `selon` offre :

- ✅ **Syntaxe claire** : Style Kotlin avec `valeur : action`
- ✅ **Break automatique** : Pas de fall-through
- ✅ **Types variés** : Entiers, chaînes, caractères
- ✅ **Instructions multiples** : Sans accolades
- ✅ **Cas par défaut** : Optionnel avec `defaut:`
- ✅ **Lisibilité** : Plus clair que les `si...sinon si` en cascade

---

**Ajouté le** : 3 Décembre 2025
**Version** : 1.0.0+
**Inspiration** : Kotlin `when` expression

**🎉 L'instruction `selon` rend votre code plus lisible et maintenable ! 🎉**

