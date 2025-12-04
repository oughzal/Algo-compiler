# ============================================
#     INSTALLATEUR ALGO-COMPILER v1.0.0
# ============================================

# Fonction pour vérifier les droits administrateur
function Test-Administrator {
    $currentUser = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
    return $currentUser.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

# Fonction pour ajouter au PATH système
function Add-ToSystemPath {
    param([string]$Path)

    $currentPath = [Environment]::GetEnvironmentVariable("Path", "Machine")

    if ($currentPath -notlike "*$Path*") {
        $newPath = "$currentPath;$Path"
        [Environment]::SetEnvironmentVariable("Path", $newPath, "Machine")
        return $true
    }
    return $false
}

# Fonction pour afficher un message
function Write-Step {
    param(
        [string]$Message,
        [string]$Status = "INFO"
    )

    $color = switch ($Status) {
        "OK" { "Green" }
        "ERROR" { "Red" }
        "WARNING" { "Yellow" }
        default { "Cyan" }
    }

    Write-Host "[$Status] " -ForegroundColor $color -NoNewline
    Write-Host $Message
}

# Banner
Clear-Host
Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "   INSTALLATEUR ALGO-COMPILER v1.0.0" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Vérifier les droits administrateur
if (-not (Test-Administrator)) {
    Write-Step "Ce script nécessite les droits administrateur." "ERROR"
    Write-Host ""
    Write-Host "Relancez PowerShell en tant qu'administrateur :" -ForegroundColor Yellow
    Write-Host "  1. Clic droit sur PowerShell" -ForegroundColor Yellow
    Write-Host "  2. 'Exécuter en tant qu'administrateur'" -ForegroundColor Yellow
    Write-Host ""
    Read-Host "Appuyez sur Entrée pour quitter"
    exit 1
}

# Définir les chemins
$installDir = "C:\Program Files\Algo-Compiler"
$jarName = "algo-compiler-1.0.0.jar"
$scriptName = "algoc.bat"
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$sourceJar = Join-Path $scriptDir "build\libs\$jarName"

Write-Step "Vérification des prérequis..." "INFO"
Write-Host ""

# Vérifier Java
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Step "Java est installé" "OK"
    Write-Host "    $javaVersion" -ForegroundColor Gray
} catch {
    Write-Step "Java n'est pas installé ou n'est pas dans le PATH" "ERROR"
    Write-Host ""
    Write-Host "Téléchargez et installez Java 21 ou supérieur depuis :" -ForegroundColor Yellow
    Write-Host "  - https://www.oracle.com/java/technologies/downloads/" -ForegroundColor Yellow
    Write-Host "  - https://adoptium.net/" -ForegroundColor Yellow
    Write-Host ""
    Read-Host "Appuyez sur Entrée pour quitter"
    exit 1
}
Write-Host ""

# Vérifier que le JAR existe
Write-Step "Vérification du fichier JAR..." "INFO"
if (-not (Test-Path $sourceJar)) {
    Write-Step "Le fichier $jarName n'existe pas dans build\libs\" "ERROR"
    Write-Host ""
    Write-Host "Veuillez compiler le projet d'abord avec :" -ForegroundColor Yellow
    Write-Host "  .\gradlew build" -ForegroundColor Yellow
    Write-Host ""
    Read-Host "Appuyez sur Entrée pour quitter"
    exit 1
}
Write-Step "Fichier JAR trouvé" "OK"
Write-Host ""

# Créer le répertoire d'installation
Write-Step "Création du répertoire d'installation..." "INFO"
if (-not (Test-Path $installDir)) {
    New-Item -ItemType Directory -Path $installDir -Force | Out-Null
    Write-Step "Répertoire créé : $installDir" "OK"
} else {
    Write-Step "Répertoire existe déjà : $installDir" "OK"
}
Write-Host ""

# Copier le JAR
Write-Step "Copie du compilateur..." "INFO"
try {
    Copy-Item $sourceJar -Destination $installDir -Force
    Write-Step "Compilateur copié vers $installDir" "OK"
} catch {
    Write-Step "Impossible de copier le compilateur : $_" "ERROR"
    Read-Host "Appuyez sur Entrée pour quitter"
    exit 1
}
Write-Host ""

# Créer le script wrapper
Write-Step "Création du script wrapper..." "INFO"
$wrapperContent = @"
@echo off
:: Algo-Compiler Wrapper Script
:: Usage: algoc fichier.algo

if "%~1"=="" (
    echo Usage: algoc fichier.algo
    echo.
    echo Exemples:
    echo   algoc hello.algo
    echo   algoc C:\mes_algos\programme.algo
    exit /b 1
)

java -jar "$installDir\$jarName" %*
"@

$wrapperPath = Join-Path $installDir $scriptName
$wrapperContent | Out-File -FilePath $wrapperPath -Encoding ASCII -Force
Write-Step "Script wrapper créé : $scriptName" "OK"
Write-Host ""

# Ajouter au PATH
Write-Step "Ajout au PATH système..." "INFO"
if (Add-ToSystemPath $installDir) {
    Write-Step "Chemin ajouté au PATH système" "OK"
    Write-Host "    $installDir" -ForegroundColor Gray
} else {
    Write-Step "Le chemin est déjà dans le PATH" "OK"
}
Write-Host ""

# Créer le désinstallateur
Write-Step "Création du désinstallateur..." "INFO"
$uninstallContent = @"
# Désinstallateur Algo-Compiler

`$installDir = "$installDir"

if (-not ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)) {
    Write-Host "[ERREUR] Ce script nécessite les droits administrateur" -ForegroundColor Red
    Read-Host "Appuyez sur Entrée pour quitter"
    exit 1
}

Write-Host ""
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "   DÉSINSTALLATEUR ALGO-COMPILER" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Suppression du chemin du PATH système..." -ForegroundColor Yellow
`$currentPath = [Environment]::GetEnvironmentVariable("Path", "Machine")
`$newPath = `$currentPath -replace [regex]::Escape("`$installDir;"), ""
`$newPath = `$newPath -replace [regex]::Escape(";`$installDir"), ""
`$newPath = `$newPath -replace [regex]::Escape("`$installDir"), ""
[Environment]::SetEnvironmentVariable("Path", `$newPath, "Machine")
Write-Host "[OK] Supprimé du PATH" -ForegroundColor Green

Write-Host "Suppression des fichiers..." -ForegroundColor Yellow
Remove-Item -Path `$installDir -Recurse -Force -ErrorAction SilentlyContinue

if (Test-Path `$installDir) {
    Write-Host "[ERREUR] Impossible de supprimer complètement le répertoire" -ForegroundColor Red
    Write-Host "Supprimez manuellement : `$installDir" -ForegroundColor Yellow
} else {
    Write-Host "[OK] Algo-Compiler désinstallé avec succès" -ForegroundColor Green
}

Write-Host ""
Read-Host "Appuyez sur Entrée pour quitter"
"@

$uninstallPath = Join-Path $installDir "uninstall.ps1"
$uninstallContent | Out-File -FilePath $uninstallPath -Encoding UTF8 -Force
Write-Step "Désinstallateur créé : uninstall.ps1" "OK"
Write-Host ""

# Créer un fichier README dans le dossier d'installation
Write-Step "Création de la documentation..." "INFO"
$readmeContent = @"
# Algo-Compiler - Installation

## Informations d'installation

**Version** : 1.0.0
**Date d'installation** : $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
**Répertoire** : $installDir

## Utilisation

Pour compiler et exécuter un fichier algo :

```
algoc fichier.algo
```

## Exemples

```
algoc hello.algo
algoc C:\mes_algos\programme.algo
```

## Désinstallation

Exécutez le fichier suivant en tant qu'administrateur :
- PowerShell : `uninstall.ps1`
- Batch : `uninstall.bat` (si créé)

## Support

- GitHub : https://github.com/oughzal/Algo-compiler
- Documentation : https://oughzal.github.io/Algo-compiler/

## Fichiers installés

- $jarName : Le compilateur
- $scriptName : Script wrapper pour faciliter l'utilisation
- uninstall.ps1 : Script de désinstallation
- README.txt : Ce fichier
"@

$readmePath = Join-Path $installDir "README.txt"
$readmeContent | Out-File -FilePath $readmePath -Encoding UTF8 -Force
Write-Step "Documentation créée" "OK"
Write-Host ""

# Proposer de créer un raccourci sur le bureau
Write-Host "Voulez-vous créer un raccourci sur le bureau ? (O/N)" -ForegroundColor Cyan
$createShortcut = Read-Host "> "

if ($createShortcut -eq "O" -or $createShortcut -eq "o") {
    Write-Step "Création du raccourci..." "INFO"

    $desktopPath = [Environment]::GetFolderPath("Desktop")
    $shortcutPath = Join-Path $desktopPath "Algo-Compiler.lnk"

    $WshShell = New-Object -ComObject WScript.Shell
    $Shortcut = $WshShell.CreateShortcut($shortcutPath)
    $Shortcut.TargetPath = "cmd.exe"
    $Shortcut.Arguments = "/k cd /d %USERPROFILE% && echo Algo-Compiler v1.0.0 && echo Type 'algoc fichier.algo' pour compiler && echo."
    $Shortcut.Description = "Algo-Compiler - Compilateur de pseudo-code"
    $Shortcut.WorkingDirectory = $env:USERPROFILE
    $Shortcut.Save()

    Write-Step "Raccourci créé sur le bureau" "OK"
    Write-Host ""
}

# Message de succès
Write-Host ""
Write-Host "============================================" -ForegroundColor Green
Write-Host "   INSTALLATION TERMINÉE AVEC SUCCÈS !" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Green
Write-Host ""
Write-Host "Emplacement : " -NoNewline
Write-Host $installDir -ForegroundColor Yellow
Write-Host "Commande    : " -NoNewline
Write-Host "algoc" -ForegroundColor Yellow
Write-Host ""
Write-Host "Pour utiliser le compilateur :" -ForegroundColor Cyan
Write-Host "  algoc fichier.algo" -ForegroundColor White
Write-Host ""
Write-Host "Exemples :" -ForegroundColor Cyan
Write-Host "  algoc examples\hello.algo" -ForegroundColor White
Write-Host "  algoc mon_programme.algo" -ForegroundColor White
Write-Host ""
Write-Host "IMPORTANT : " -ForegroundColor Red -NoNewline
Write-Host "Fermez et rouvrez votre terminal"
Write-Host "            pour que le PATH soit mis à jour"
Write-Host ""
Write-Host "Pour désinstaller :" -ForegroundColor Cyan
Write-Host "  $installDir\uninstall.ps1" -ForegroundColor White
Write-Host ""
Write-Host "============================================" -ForegroundColor Green
Write-Host ""

Read-Host "Appuyez sur Entrée pour quitter"

