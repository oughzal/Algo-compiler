# Script de diagnostic Java pour Algo Compiler Android
# Vérifie les installations Java disponibles sur le système

Write-Host "=== Diagnostic Java pour Algo Compiler ===" -ForegroundColor Cyan
Write-Host ""

# Vérifier la version Java courante
Write-Host "1. Version Java par défaut:" -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 3
    $javaVersion | ForEach-Object { Write-Host "   $_" }
} catch {
    Write-Host "   ERREUR: Java non trouvé dans PATH" -ForegroundColor Red
}
Write-Host ""

# Vérifier JAVA_HOME
Write-Host "2. Variable JAVA_HOME:" -ForegroundColor Yellow
if ($env:JAVA_HOME) {
    Write-Host "   $env:JAVA_HOME" -ForegroundColor Green
    if (Test-Path "$env:JAVA_HOME\bin\java.exe") {
        Write-Host "   ✓ Java.exe trouvé" -ForegroundColor Green
    } else {
        Write-Host "   ✗ Java.exe NON trouvé" -ForegroundColor Red
    }
} else {
    Write-Host "   Non définie" -ForegroundColor Red
}
Write-Host ""

# Rechercher les installations Java communes
Write-Host "3. Recherche d'installations Java:" -ForegroundColor Yellow
$javaLocations = @(
    "C:\Program Files\Java",
    "C:\Program Files (x86)\Java",
    "C:\Program Files\Eclipse Adoptium",
    "C:\Program Files\Android\Android Studio\jbr",
    "$env:LOCALAPPDATA\Programs\Eclipse Adoptium"
)

$foundJava = $false
foreach ($location in $javaLocations) {
    if (Test-Path $location) {
        Write-Host "   Trouvé: $location" -ForegroundColor Green
        Get-ChildItem $location -Directory | ForEach-Object {
            Write-Host "     - $($_.Name)"
        }
        $foundJava = $true
    }
}

if (-not $foundJava) {
    Write-Host "   Aucune installation Java trouvée dans les emplacements standards" -ForegroundColor Red
}
Write-Host ""

# Vérifier le status Gradle
Write-Host "4. Status Gradle Daemon:" -ForegroundColor Yellow
try {
    Push-Location "C:\Users\Omar\Documents\Algo-compiler\algo-compiler-android"
    $gradleStatus = .\gradlew.bat --status 2>&1 | Select-Object -First 20
    $gradleStatus | ForEach-Object { Write-Host "   $_" }
    Pop-Location
} catch {
    Write-Host "   ERREUR lors de l'exécution de Gradle" -ForegroundColor Red
    Pop-Location
}
Write-Host ""

# Recommandations
Write-Host "=== RECOMMANDATIONS ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "Le projet nécessite Java 17 LTS pour fonctionner correctement." -ForegroundColor Yellow
Write-Host ""
Write-Host "Si Java 17 n'est pas installé, téléchargez-le depuis:" -ForegroundColor White
Write-Host "  • Adoptium (recommandé): https://adoptium.net/temurin/releases/?version=17" -ForegroundColor Green
Write-Host "  • Oracle JDK: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html" -ForegroundColor Green
Write-Host ""
Write-Host "Après installation:" -ForegroundColor White
Write-Host "  1. Configurez JAVA_HOME vers le JDK 17" -ForegroundColor Cyan
Write-Host "  2. Redémarrez Android Studio" -ForegroundColor Cyan
Write-Host "  3. Dans Android Studio: File → Project Structure → JDK Location" -ForegroundColor Cyan
Write-Host "  4. Exécutez: .\gradlew clean" -ForegroundColor Cyan
Write-Host ""
Write-Host "Pour forcer Java Toolchain (téléchargement automatique):" -ForegroundColor White
Write-Host "  .\gradlew --info build" -ForegroundColor Cyan
Write-Host ""

