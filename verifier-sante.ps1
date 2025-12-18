# Script de vérification de la santé du compilateur Algo
# Vérifie l'absence de crashs et la compilation

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  VERIFICATION SANTE ALGO COMPILER" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Étape 1: Vérification des fichiers de crash
Write-Host "[1/4] Vérification des fichiers de crash..." -ForegroundColor Yellow
$crashFiles = Get-ChildItem -Filter "hs_err_*.log" -ErrorAction SilentlyContinue
if ($crashFiles.Count -gt 0) {
    Write-Host "[ERREUR] $($crashFiles.Count) fichier(s) de crash détecté(s)!" -ForegroundColor Red
    $crashFiles | ForEach-Object { Write-Host "  - $($_.Name)" -ForegroundColor Red }
    Write-Host ""
    Write-Host "Suggestion: Vérifier gradle.properties et build.gradle.kts" -ForegroundColor Yellow
    exit 1
} else {
    Write-Host "[OK] Aucun fichier de crash" -ForegroundColor Green
}
Write-Host ""

# Étape 2: Nettoyage du projet
Write-Host "[2/4] Nettoyage du projet..." -ForegroundColor Yellow
$cleanResult = & .\gradlew clean --quiet 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERREUR] Le nettoyage a échoué" -ForegroundColor Red
    Write-Host $cleanResult
    exit 1
}
Write-Host "[OK] Projet nettoyé" -ForegroundColor Green
Write-Host ""

# Étape 3: Compilation
Write-Host "[3/4] Compilation du projet..." -ForegroundColor Yellow
$buildResult = & .\gradlew build 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERREUR] La compilation a échoué" -ForegroundColor Red
    Write-Host $buildResult
    exit 1
}
Write-Host "[OK] Compilation réussie" -ForegroundColor Green
Write-Host ""

# Étape 4: Vérification finale
Write-Host "[4/4] Vérification finale des crashs..." -ForegroundColor Yellow
$newCrashFiles = Get-ChildItem -Filter "hs_err_*.log" -ErrorAction SilentlyContinue
if ($newCrashFiles.Count -gt 0) {
    Write-Host "[ERREUR] Nouveau crash détecté pendant la compilation!" -ForegroundColor Red
    $newCrashFiles | ForEach-Object { Write-Host "  - $($_.Name)" -ForegroundColor Red }
    exit 1
} else {
    Write-Host "[OK] Aucun crash pendant la compilation" -ForegroundColor Green
}
Write-Host ""

# Résumé final
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  VERIFICATION TERMINEE AVEC SUCCES" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Le compilateur Algo fonctionne correctement!" -ForegroundColor Green
Write-Host "JAR disponible: build\libs\algo-compiler-1.6.0.jar" -ForegroundColor Cyan
Write-Host ""

# Afficher les statistiques
$jarPath = "build\libs\algo-compiler-1.6.0.jar"
if (Test-Path $jarPath) {
    $jarSize = (Get-Item $jarPath).Length / 1MB
    Write-Host "Taille du JAR: $([math]::Round($jarSize, 2)) MB" -ForegroundColor Cyan
}

Write-Host ""
Write-Host "✓ Projet prêt à être utilisé!" -ForegroundColor Green

