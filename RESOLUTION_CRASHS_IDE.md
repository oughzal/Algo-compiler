# Résolution des Crashs de l'IDE
**Date :** 2025-12-14

## Problème Identifié

L'IDE IntelliJ IDEA crashait fréquemment avec des erreurs de mémoire insuffisante. Les fichiers de rapport `hs_err_*.log` montraient :

```
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 534773760 bytes.
```

### Cause Principale

Les configurations mémoire dans `gradle.properties` étaient incorrectes :
- ❌ `-Xmx10000m` : Limite de mémoire trop élevée
- ❌ `kotlin.daemon.jvmargs=-Xmx111120m` : Valeur absurde (111 GB)
- ❌ Tests avec seulement `-Xmx512m` : Insuffisant pour l'exécution des tests

## Solution Appliquée

### 1. Correction de `gradle.properties`

**Avant :**
```properties
org.gradle.jvmargs=-Xmx10000m -XX:MaxMetaspaceSize=10000m
kotlin.daemon.jvmargs=-Xmx111120m
```

**Après :**
```properties
# Configuration mémoire optimale pour éviter les crashs
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError
kotlin.daemon.jvmargs=-Xmx2048m
```

**Explications :**
- `Xmx2048m` : Limite de mémoire à 2 GB (raisonnable pour la compilation)
- `XX:MaxMetaspaceSize=512m` : Limite pour les métadonnées de classes
- `XX:+HeapDumpOnOutOfMemoryError` : Génère un dump en cas d'erreur pour débogage

### 2. Configuration de `build.gradle.kts`

**Ajout de la configuration mémoire pour les tests :**

```kotlin
tasks.test {
    useJUnitPlatform()
    // Augmenter la mémoire pour éviter les crashs
    maxHeapSize = "2048m"
    jvmArgs = listOf(
        "-XX:MaxMetaspaceSize=512m",
        "-XX:+HeapDumpOnOutOfMemoryError",
        "-Xss2m"
    )
}
```

**Explications :**
- `maxHeapSize = "2048m"` : Allocation de 2 GB pour l'exécution des tests
- `-Xss2m` : Taille de la pile par thread (évite les StackOverflowError)

### 3. Correction du Test Défaillant

Le test `testParseErrorMissingOperatorAfterIdentifier()` échouait car le code `x 5` est maintenant accepté comme expression standalone.

**Solution :** Modification du test pour utiliser un vrai cas d'erreur de syntaxe :
```kotlin
// Avant : x 5 (maintenant accepté comme expression)
// Après : si x 5 alors (erreur de syntaxe dans une condition)
```

### 4. Nettoyage

- Suppression de tous les fichiers `hs_err_*.log` du projet
- Ces fichiers sont déjà dans `.gitignore` pour éviter de les commiter

## Résultats

✅ **Compilation réussie** sans crash de mémoire  
✅ **Tous les tests passent** (93 tests)  
✅ **Build complet** en ~7 secondes  
✅ **Pas de nouveaux fichiers de crash**

## Recommandations

### Pour l'avenir :

1. **Surveillance de la mémoire**
   - Si les crashs reviennent, augmenter progressivement la mémoire (2048m → 3072m → 4096m)
   - Utiliser `jconsole` ou `jvisualvm` pour monitorer l'utilisation mémoire

2. **Configuration machine**
   - Minimum recommandé : 8 GB RAM
   - Optimal : 16 GB RAM ou plus

3. **Gradle Daemon**
   - Le daemon Gradle peut consommer beaucoup de mémoire
   - Si nécessaire, le stopper avec : `.\gradlew --stop`

4. **Options JVM avancées** (si problèmes persistent)
   ```properties
   org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m \
       -XX:+HeapDumpOnOutOfMemoryError \
       -XX:+UseG1GC \
       -XX:MaxGCPauseMillis=200
   ```

## Commandes de Vérification

```powershell
# Nettoyer le projet
.\gradlew clean

# Compiler et tester
.\gradlew build

# Exécuter seulement les tests
.\gradlew test

# Stopper le daemon Gradle si nécessaire
.\gradlew --stop
```

## Status Final

🎯 **Problème résolu** : L'IDE ne devrait plus crasher avec les nouvelles configurations mémoire optimales.

