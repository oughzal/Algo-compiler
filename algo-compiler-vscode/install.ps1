# Script d'installation de l'extension Algo Compiler pour VS Code
# Version 1.3.0

Write-Host "========================================" -ForegroundColor Cyan
Write-Host " Installation Extension Algo Compiler" -ForegroundColor Cyan
Write-Host " Version 1.3.0" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier que nous sommes dans le bon dossier
if (-not (Test-Path "package.json")) {
    Write-Host "ERREUR: Ce script doit être exécuté depuis le dossier algo-compiler-vscode" -ForegroundColor Red
    exit 1
}

# 1. Vérifier Node.js
Write-Host "[1/5] Vérification de Node.js..." -ForegroundColor Yellow
try {
    $nodeVersion = node --version 2>$null
    Write-Host "OK - Node.js $nodeVersion installé" -ForegroundColor Green
} catch {
    Write-Host "ERREUR: Node.js n'est pas installé!" -ForegroundColor Red
    Write-Host "Téléchargez-le depuis https://nodejs.org/" -ForegroundColor Yellow
    exit 1
}

# 2. Installer les dépendances
Write-Host ""
Write-Host "[2/5] Installation des dépendances npm..." -ForegroundColor Yellow
npm install
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERREUR lors de l'installation des dépendances" -ForegroundColor Red
    exit 1
}
Write-Host "OK - Dépendances installées" -ForegroundColor Green

# 3. Compiler TypeScript
Write-Host ""
Write-Host "[3/5] Compilation TypeScript..." -ForegroundColor Yellow
npm run compile
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERREUR lors de la compilation" -ForegroundColor Red
    exit 1
}
Write-Host "OK - Compilation réussie" -ForegroundColor Green

# 4. Créer le package VSIX
Write-Host ""
Write-Host "[4/5] Création du package VSIX..." -ForegroundColor Yellow

# Vérifier si vsce est installé
$vsceInstalled = Get-Command vsce -ErrorAction SilentlyContinue
if (-not $vsceInstalled) {
    Write-Host "Installation de vsce (VS Code Extension Manager)..." -ForegroundColor Yellow
    npm install -g @vscode/vsce
    if ($LASTEXITCODE -ne 0) {
        Write-Host "ERREUR lors de l'installation de vsce" -ForegroundColor Red
        exit 1
    }
}

vsce package
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERREUR lors de la création du package" -ForegroundColor Red
    exit 1
}
Write-Host "OK - Package VSIX créé: algo-compiler-1.3.0.vsix" -ForegroundColor Green

# 5. Installer dans VS Code
Write-Host ""
Write-Host "[5/5] Installation dans VS Code..." -ForegroundColor Yellow

# Vérifier si VS Code est installé
$codeInstalled = Get-Command code -ErrorAction SilentlyContinue
if (-not $codeInstalled) {
    Write-Host "AVERTISSEMENT: La commande 'code' n'est pas disponible" -ForegroundColor Yellow
    Write-Host "Vous devez installer manuellement l'extension:" -ForegroundColor Yellow
    Write-Host "  1. Ouvrez VS Code" -ForegroundColor White
    Write-Host "  2. Ctrl+Shift+P → 'Extensions: Install from VSIX...'" -ForegroundColor White
    Write-Host "  3. Sélectionnez: algo-compiler-1.3.0.vsix" -ForegroundColor White
} else {
    code --install-extension algo-compiler-1.3.0.vsix
    if ($LASTEXITCODE -eq 0) {
        Write-Host "OK - Extension installée dans VS Code" -ForegroundColor Green
    } else {
        Write-Host "ERREUR lors de l'installation dans VS Code" -ForegroundColor Red
        Write-Host "Essayez l'installation manuelle:" -ForegroundColor Yellow
        Write-Host "  1. Ouvrez VS Code" -ForegroundColor White
        Write-Host "  2. Ctrl+Shift+P → 'Extensions: Install from VSIX...'" -ForegroundColor White
        Write-Host "  3. Sélectionnez: algo-compiler-1.3.0.vsix" -ForegroundColor White
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host " Installation terminée!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "L'extension Algo Compiler v1.3.0 est maintenant installée." -ForegroundColor White
Write-Host ""
Write-Host "Nouvelles fonctionnalités:" -ForegroundColor Yellow
Write-Host "  - Support du type 'caractere'" -ForegroundColor White
Write-Host "  - 40 fonctions intégrées (vs 21 auparavant)" -ForegroundColor White
Write-Host "  - Nouvelles fonctions: ord, chr, min, max, contient, etc." -ForegroundColor White
Write-Host "  - Snippets enrichis" -ForegroundColor White
Write-Host ""
Write-Host "Pour utiliser:" -ForegroundColor Yellow
Write-Host "  1. Créez un fichier .algo" -ForegroundColor White
Write-Host "  2. Tapez 'algo' + Tab pour le template" -ForegroundColor White
Write-Host "  3. Ctrl+Shift+R pour exécuter" -ForegroundColor White
Write-Host ""

