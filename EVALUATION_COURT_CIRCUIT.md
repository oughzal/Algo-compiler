# Évaluation en court-circuit des opérateurs logiques ET et OU

## Vue d'ensemble

Le compilateur Algo-Compiler implémente maintenant l'**évaluation en court-circuit** (short-circuit evaluation) pour les opérateurs logiques `et` et `ou`, similaire au comportement des opérateurs `&&` et `||` dans Kotlin, Java, C++, JavaScript et autres langages modernes.

## Qu'est-ce que l'évaluation en court-circuit ?

L'évaluation en court-circuit est une optimisation qui évite d'évaluer le second opérande d'une expression logique lorsque le résultat peut être déterminé uniquement à partir du premier opérande.

### Pour l'opérateur ET (`et`)

Avec l'opérateur `et`, si le **premier opérande est faux**, le **second opérande n'est pas évalué** car le résultat sera nécessairement faux.

**Règle** : `faux ET n'importe_quoi = faux`

### Pour l'opérateur OU (`ou`)

Avec l'opérateur `ou`, si le **premier opérande est vrai**, le **second opérande n'est pas évalué** car le résultat sera nécessairement vrai.

**Règle** : `vrai OU n'importe_quoi = vrai`

## Avantages

1. **Performance améliorée** : Évite des calculs inutiles
2. **Sécurité accrue** : Prévient les erreurs d'exécution potentielles
3. **Code plus expressif** : Permet des vérifications conditionnelles sûres

## Exemples

### Exemple 1 : Éviter une division par zéro avec ET

```algo
algorithme ExempleET
variables
    x : entier
    resultat : booleen
debut
    x = 0
    
    // Sans court-circuit, ceci causerait une erreur de division par zéro
    // Avec court-circuit, le second terme n'est jamais évalué car x == 1 est faux
    resultat = (x == 1) et (10 / x > 0)
    
    ecrireln("Résultat : ", resultat)  // Affiche : Résultat : false
    ecrireln("Pas d'erreur de division par zéro!")
fin
```

**Explication** :
- `x == 1` est évalué en premier et donne `faux`
- Comme le premier opérande est faux, `10 / x > 0` **n'est jamais évalué**
- Pas d'erreur de division par zéro !

### Exemple 2 : Éviter une division par zéro avec OU

```algo
algorithme ExempleOU
variables
    x : entier
    resultat : booleen
debut
    x = 0
    
    // Le premier terme est vrai, donc le second (qui causerait une erreur) n'est pas évalué
    resultat = (x == 0) ou (10 / x > 0)
    
    ecrireln("Résultat : ", resultat)  // Affiche : Résultat : true
    ecrireln("Pas d'erreur de division par zéro!")
fin
```

**Explication** :
- `x == 0` est évalué en premier et donne `vrai`
- Comme le premier opérande est vrai, `10 / x > 0` **n'est jamais évalué**
- Pas d'erreur de division par zéro !

### Exemple 3 : Vérification sécurisée d'accès à un tableau

```algo
algorithme VerificationTableau
variables
    tab : tableau[5] de entier
    index : entier
    valeur : entier
debut
    // Initialiser le tableau
    pour i de 0 à 4 faire
        tab[i] = i * 10
    finpour
    
    // Vérification sécurisée avec plusieurs conditions
    index = 10  // Hors limites
    
    // Sans court-circuit, tab[index] causerait une erreur
    // Avec court-circuit, tab[index] n'est jamais évalué si index est invalide
    si (index >= 0) et (index < 5) et (tab[index] == 30) alors
        ecrireln("Valeur 30 trouvée à l'index ", index)
    sinon
        ecrireln("Index hors limites ou valeur non trouvée")
    finsi
    
    // Test avec un index valide
    index = 3
    si (index >= 0) et (index < 5) et (tab[index] == 30) alors
        ecrireln("Valeur 30 trouvée à l'index ", index)
    sinon
        ecrireln("Valeur non trouvée")
    finsi
fin
```

**Explication** :
- Premier test avec `index = 10` :
  - `index >= 0` → vrai
  - `index < 5` → **faux** → le reste n'est pas évalué
  - Pas d'erreur d'accès au tableau hors limites !

- Second test avec `index = 3` :
  - `index >= 0` → vrai
  - `index < 5` → vrai
  - `tab[index] == 30` → évalué en toute sécurité car les vérifications précédentes sont vraies

### Exemple 4 : Recherche avec conditions multiples

```algo
algorithme RechercheSecurisee
variables
    texte : chaine
    position : entier
debut
    texte = "Bonjour"
    position = 0
    
    // Rechercher la lettre 'o' à partir de la position 0
    tantque (position < longueur(texte)) et (sousChaine(texte, position, position + 1) <> "o") faire
        position = position + 1
    fintantque
    
    si position < longueur(texte) alors
        ecrireln("Lettre 'o' trouvée à la position ", position)
    sinon
        ecrireln("Lettre 'o' non trouvée")
    finsi
fin
```

**Explication** :
- À chaque itération, `position < longueur(texte)` est vérifié en premier
- Si cette condition est fausse (fin de chaîne atteinte), `sousChaine(...)` n'est pas évalué
- Évite une erreur d'accès hors limites de la chaîne

### Exemple 5 : Optimisation avec fonctions coûteuses

```algo
algorithme OptimisationFonctions
variables
    cacheValide : booleen
    valeurCache : entier
debut
    cacheValide = vrai
    valeurCache = 42
    
    // Si le cache est valide, la fonction coûteuse n'est pas appelée
    si cacheValide ou calculComplexe() == 42 alors
        ecrireln("Condition satisfaite")
    finsi
    
    // Utiliser la valeur du cache si disponible
    si cacheValide alors
        ecrireln("Utilisation du cache : ", valeurCache)
    finsi
fin

fonction calculComplexe() : entier
debut
    ecrireln("Calcul complexe exécuté (coûteux)")
    // ... calculs longs ...
    retourner 42
fin
```

**Explication** :
- Si `cacheValide` est vrai, la fonction `calculComplexe()` n'est jamais appelée
- Économise du temps de calcul en évitant des opérations inutiles

## Tableau récapitulatif

| Expression | Premier opérande | Second opérande évalué ? | Résultat |
|------------|------------------|-------------------------|----------|
| `faux et X` | faux | ❌ Non | faux |
| `vrai et X` | vrai | ✅ Oui | Dépend de X |
| `vrai ou X` | vrai | ❌ Non | vrai |
| `faux ou X` | faux | ✅ Oui | Dépend de X |

## Comparaison avec d'autres langages

Cette fonctionnalité est cohérente avec le comportement standard des opérateurs logiques dans la plupart des langages de programmation modernes :

- **Kotlin** : `&&` et `||`
- **Java** : `&&` et `||`
- **C/C++** : `&&` et `||`
- **JavaScript** : `&&` et `||`
- **Python** : `and` et `or`
- **C#** : `&&` et `||`

## Tests unitaires

Des tests unitaires complets ont été ajoutés dans `ShortCircuitTest.kt` pour vérifier le bon fonctionnement de cette fonctionnalité.

## Notes techniques

- L'évaluation en court-circuit est implémentée directement dans la méthode `evaluateBinaryOp` de l'interpréteur
- Les opérateurs `et` et `ou` sont traités de manière spéciale avant l'évaluation des autres opérateurs binaires
- Cette optimisation n'affecte pas la sémantique du programme, elle améliore uniquement les performances et la sécurité

## Conclusion

L'évaluation en court-circuit des opérateurs logiques est une fonctionnalité importante qui rend le langage plus performant, plus sûr et plus proche des langages de programmation modernes. Elle permet d'écrire du code plus expressif et d'éviter des erreurs d'exécution courantes.

