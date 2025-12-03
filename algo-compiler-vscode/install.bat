@echo off
echo ========================================
echo  Installation Extension Algo Compiler v1.3.0
echo ========================================
echo.

echo [1/4] Verification de Node.js...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERREUR: Node.js n'est pas installe!
    echo Telechargez-le depuis https://nodejs.org/
    pause
    exit /b 1
)
echo OK - Node.js est installe

echo.
echo [2/4] Installation des dependances...
call npm install
if %errorlevel% neq 0 (
    echo ERREUR lors de l'installation des dependances
    pause
    exit /b 1
)

echo.
echo [3/4] Compilation TypeScript...
call npm run compile
if %errorlevel% neq 0 (
    echo ERREUR lors de la compilation
    pause
    exit /b 1
)

echo.
echo [4/4] Creation du package VSIX...
call npm run package
if %errorlevel% neq 0 (
    echo ERREUR lors de la creation du package
    pause
    exit /b 1
)

echo.
echo ========================================
echo  Installation terminee avec succes!
echo ========================================
echo.
echo Le fichier algo-compiler-1.0.0.vsix a ete cree.
echo.
echo Pour installer l'extension dans VS Code:
echo 1. Ouvrir VS Code
echo 2. Ctrl+Shift+P
echo 3. Taper: Extensions: Install from VSIX...
echo 4. Selectionner le fichier algo-compiler-1.0.0.vsix
echo.
pause
