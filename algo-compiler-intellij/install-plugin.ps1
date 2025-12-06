# Script d'installation automatique du plugin Algo-Compiler pour IntelliJ IDEA
# © 2024 Omar OUGHZAL

Write-Host "═══════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "   Installation du plugin Algo-Compiler pour IntelliJ IDEA    " -ForegroundColor Cyan
Write-Host "═══════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

# Chemins
$pluginDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$pluginZip = Join-Path $pluginDir "build\distributions\algo-compiler-intellij-1.6.0.zip"
$compilerJar = Join-Path (Split-Path -Parent $pluginDir) "build\libs\algo-compiler-1.6.0.jar"

# Étape 1 : Vérifier si le build est terminé
Write-Host "🔍 Vérification du plugin..." -ForegroundColor Yellow
if (-not (Test-Path $pluginZip)) {
    Write-Host "❌ Le plugin n'est pas encore compilé." -ForegroundColor Red
    Write-Host ""
    Write-Host "📦 Lancement du build..." -ForegroundColor Yellow
    Write-Host "⏳ Cela peut prendre 10-15 minutes (téléchargement SDK IntelliJ)" -ForegroundColor Gray
    Write-Host ""

    Set-Location $pluginDir
    & "..\gradlew.bat" buildPlugin

    if ($LASTEXITCODE -ne 0) {
        Write-Host "❌ Échec du build. Vérifiez les erreurs ci-dessus." -ForegroundColor Red
        exit 1
    }
}

if (Test-Path $pluginZip) {
    $pluginSize = (Get-Item $pluginZip).Length / 1MB
    Write-Host "✅ Plugin prêt : $pluginZip" -ForegroundColor Green
    Write-Host "   Taille : $([math]::Round($pluginSize, 2)) MB" -ForegroundColor Gray
} else {
    Write-Host "❌ Le plugin n'a pas pu être trouvé." -ForegroundColor Red
    exit 1
}

# Étape 2 : Vérifier le compilateur JAR
Write-Host ""
Write-Host "🔍 Vérification du compilateur..." -ForegroundColor Yellow
if (Test-Path $compilerJar) {
    Write-Host "✅ Compilateur trouvé : $compilerJar" -ForegroundColor Green
} else {
    Write-Host "⚠️  Compilateur non trouvé. Il sera nécessaire de le configurer." -ForegroundColor Yellow
    Write-Host "   Chemin attendu : $compilerJar" -ForegroundColor Gray
}

# Étape 3 : Rechercher IntelliJ IDEA
Write-Host ""
Write-Host "🔍 Recherche d'IntelliJ IDEA..." -ForegroundColor Yellow

$ideaPaths = @(
    "$env:LOCALAPPDATA\JetBrains\Toolbox\apps\IDEA-U\ch-0",
    "$env:LOCALAPPDATA\JetBrains\Toolbox\apps\IDEA-C\ch-0",
    "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition",
    "C:\Program Files\JetBrains\IntelliJ IDEA",
    "C:\Program Files (x86)\JetBrains\IntelliJ IDEA Community Edition",
    "C:\Program Files (x86)\JetBrains\IntelliJ IDEA"
)

$ideaFound = $false
$ideaExe = $null

foreach ($path in $ideaPaths) {
    if (Test-Path $path) {
        $exes = Get-ChildItem -Path $path -Filter "idea64.exe" -Recurse -ErrorAction SilentlyContinue
        if ($exes) {
            $ideaExe = $exes[0].FullName
            $ideaFound = $true
            Write-Host "✅ IntelliJ IDEA trouvé : $ideaExe" -ForegroundColor Green
            break
        }
    }
}

if (-not $ideaFound) {
    Write-Host "⚠️  IntelliJ IDEA n'a pas été trouvé automatiquement." -ForegroundColor Yellow
    Write-Host "   Vous devrez installer le plugin manuellement." -ForegroundColor Gray
}

# Étape 4 : Instructions d'installation
Write-Host ""
Write-Host "═══════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "   INSTRUCTIONS D'INSTALLATION                                 " -ForegroundColor Cyan
Write-Host "═══════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

Write-Host "📋 Étape 1 : Installer le plugin" -ForegroundColor White
Write-Host "   1. Ouvrez IntelliJ IDEA" -ForegroundColor Gray
Write-Host "   2. File → Settings → Plugins" -ForegroundColor Gray
Write-Host "   3. Cliquez sur ⚙️ → Install Plugin from Disk..." -ForegroundColor Gray
Write-Host "   4. Sélectionnez :" -ForegroundColor Gray
Write-Host "      $pluginZip" -ForegroundColor Cyan
Write-Host "   5. Cliquez sur OK et redémarrez IntelliJ IDEA" -ForegroundColor Gray
Write-Host ""

Write-Host "📋 Étape 2 : Configurer le compilateur" -ForegroundColor White
Write-Host "   1. File → Settings → Tools → Algo Compiler" -ForegroundColor Gray
Write-Host "   2. Définir le chemin du JAR :" -ForegroundColor Gray
Write-Host "      $compilerJar" -ForegroundColor Cyan
Write-Host "   3. Appliquer et OK" -ForegroundColor Gray
Write-Host ""

Write-Host "📋 Étape 3 : Tester" -ForegroundColor White
Write-Host "   1. Créez un fichier test.algo" -ForegroundColor Gray
Write-Host "   2. Écrivez votre code Algo" -ForegroundColor Gray
Write-Host "   3. Appuyez sur Ctrl+Shift+F10 pour exécuter" -ForegroundColor Gray
Write-Host ""

# Étape 5 : Copier les chemins dans le presse-papiers
Write-Host "═══════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

Write-Host "💡 ASTUCE : Les chemins ont été préparés pour vous :" -ForegroundColor Yellow
Write-Host ""
Write-Host "Chemin du plugin :" -ForegroundColor White
Write-Host $pluginZip -ForegroundColor Cyan
Write-Host ""
Write-Host "Chemin du compilateur :" -ForegroundColor White
Write-Host $compilerJar -ForegroundColor Cyan
Write-Host ""

# Option pour ouvrir l'explorateur
Write-Host "───────────────────────────────────────────────────────────────" -ForegroundColor Gray
$response = Read-Host "Voulez-vous ouvrir le dossier du plugin dans l'explorateur ? (O/N)"
if ($response -eq "O" -or $response -eq "o") {
    explorer (Split-Path $pluginZip)
}

# Option pour lancer IntelliJ IDEA
if ($ideaFound) {
    Write-Host ""
    $response = Read-Host "Voulez-vous lancer IntelliJ IDEA maintenant ? (O/N)"
    if ($response -eq "O" -or $response -eq "o") {
        Start-Process $ideaExe
        Write-Host "✅ IntelliJ IDEA lancé !" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "═══════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "   Installation terminée ! Suivez les instructions ci-dessus   " -ForegroundColor Cyan
Write-Host "═══════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""
Write-Host "📚 Documentation complète : algo-compiler-intellij\README.md" -ForegroundColor Gray
Write-Host "🚀 Guide de démarrage : algo-compiler-intellij\QUICK_START.md" -ForegroundColor Gray
Write-Host ""

