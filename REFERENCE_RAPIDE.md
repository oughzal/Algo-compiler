# ⚡ Guide de Référence Rapide - AlgoC v1.2.0

## 🎯 Nouvelles Fonctionnalités

### 1. Clause SinonSi
```algo
si condition1 alors
    action1
sinonSi condition2 alors
    action2
sinonSi condition3 alors
    action3
sinon
    actionDefaut
finsi
```

### 2. Expression Conditionnelle
```algo
variable = si condition alors valeur1 sinon valeur2
```

### 3. Boucle Pour (CHANGEMENT)
```algo
// ✅ CORRECT
pour i de 1 à 10 faire
    instructions
finpour

// ❌ ERREUR (alias 'a' supprimé)
pour i de 1 a 10 faire
    instructions
finpour
```

---

## 📝 Exemples Rapides

### SinonSi - Notes
```algo
si note >= 16 alors
    ecrire("Très Bien")
sinonSi note >= 14 alors
    ecrire("Bien")
sinonSi note >= 12 alors
    ecrire("Assez Bien")
sinon
    ecrire("Passable")
finsi
```

### Expression Conditionnelle - Maximum
```algo
max = si a > b alors a sinon b
```

### Expression Conditionnelle - Statut
```algo
statut = si age >= 18 alors "Majeur" sinon "Mineur"
```

### Expression Conditionnelle - Parité
```algo
parite = si x mod 2 = 0 alors "pair" sinon "impair"
```

### Expression Conditionnelle - Dans Boucle
```algo
pour i de 1 à 10 faire
    ecrire(si i mod 2 = 0 alors "pair" sinon "impair")
finpour
```

### Expression Conditionnelle - Imbriquée
```algo
mention = si note >= 16 alors "TB" sinon si note >= 14 alors "B" sinon "AB"
```

### Variable 'a' (Maintenant OK)
```algo
variables
    a, b : entier

debut
    a = 10
    b = 20
    pour i de 1 à 5 faire
        a = a + i
    finpour
fin
```

---

## 🔍 Comparaisons

### Avant vs Après - SinonSi

#### ❌ Avant (imbriqué)
```algo
si x > 20 alors
    ecrire("Grand")
sinon
    si x > 10 alors
        ecrire("Moyen")
    sinon
        ecrire("Petit")
    finsi
finsi
```

#### ✅ Après (sinonSi)
```algo
si x > 20 alors
    ecrire("Grand")
sinonSi x > 10 alors
    ecrire("Moyen")
sinon
    ecrire("Petit")
finsi
```

### Instruction vs Expression

#### Instruction Si
```algo
si a > b alors
    max = a
sinon
    max = b
finsi
```

#### Expression Conditionnelle
```algo
max = si a > b alors a sinon b
```

---

## 💡 Cas d'Usage

| Besoin | Solution |
|--------|----------|
| Tester plusieurs conditions | `sinonSi` |
| Choisir entre 2 valeurs | Expression conditionnelle |
| Maximum/Minimum | `max = si a > b alors a sinon b` |
| Valeur absolue | `abs = si x < 0 alors -x sinon x` |
| Validation | `ok = si age >= 18 alors vrai sinon faux` |
| Formatage | `msg = si err alors "KO" sinon "OK"` |

---

## ⚠️ Points Importants

### ✅ À Faire
- Utiliser `sinonSi` pour conditions multiples
- Utiliser expression conditionnelle pour affectations simples
- Toujours utiliser `à` dans les boucles `pour`
- Variable `a` est maintenant utilisable librement

### ❌ À Éviter
- Ne plus utiliser `a` dans `pour i de 1 a 10` → Utiliser `à`
- Éviter imbrication excessive (> 3 niveaux)
- Éviter expressions conditionnelles trop complexes

---

## 📚 Documentation Complète

- [Guide SinonSi](INSTRUCTION_SINONSI.md)
- [Guide Expression Conditionnelle](EXPRESSION_CONDITIONNELLE.md)
- [Preuve Suppression Alias A](PREUVE_SUPPRESSION_ALIAS_A.md)
- [Récapitulatif Complet](RECAPITULATIF_COMPLET_V1.2.0.md)
- [Architecture du Compilateur](COMMENT_CA_MARCHE.md)

---

## 🧪 Tester

### Exemples Disponibles
```bash
# SinonSi
examples/test_sinonsi_simple.algo
examples/test_sinonsi.algo
examples/calculatrice_avancee.algo
examples/gestion_etudiants.algo
examples/fizzbuzz_sinonsi.algo

# Alias 'a'
examples/test_alias_a_supprime.algo
examples/test_erreur_avec_a.algo

# Expression conditionnelle
examples/test_ternaire_simple.algo
examples/test_expression_conditionnelle.algo
examples/test_expression_conditionnelle_avance.algo
```

### Exécution
```bash
java -jar build/libs/algo-compiler-1.0.0.jar examples/[fichier].algo
```

---

## 🚀 Version

**AlgoC v1.2.0** - 5 Décembre 2024

---

**Guide de référence rapide pour les nouvelles fonctionnalités** ⚡

