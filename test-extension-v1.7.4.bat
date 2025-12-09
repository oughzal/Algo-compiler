@echo off
REM Script de test pour l'extension VSCode Algo Compiler v1.7.4
REM Ce script teste que les commandes ne s'affichent pas lors de l'exécution

echo ============================================================
echo TEST DE L'EXTENSION ALGO COMPILER v1.7.4
echo ============================================================
echo.

echo [1/4] Verification du fichier jar...
if not exist "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.6.0.jar" (
    echo ERREUR: Fichier JAR non trouve
    pause
    exit /b 1
)
echo OK - Fichier JAR trouve

echo.
echo [2/4] Verification du fichier de test...
if not exist "C:\Users\Omar\algo\test-echo.algo" (
    echo ERREUR: Fichier test-echo.algo non trouve
    pause
    exit /b 1
)
echo OK - Fichier de test trouve

echo.
echo [3/4] Test d'execution avec @echo off...
echo ============================================================
java -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.6.0.jar" "C:\Users\Omar\algo\test-echo.algo"
echo ============================================================

echo.
echo [4/4] Verification de l'extension VSCode...
code --list-extensions | findstr /C:"algo-compiler" >nul
if %errorlevel% equ 0 (
    echo OK - Extension Algo Compiler installee
) else (
    echo ATTENTION - Extension peut-etre non installee
    echo Installez avec: code --install-extension algo-compiler-1.7.4.vsix
)

echo.
echo ============================================================
echo TEST TERMINE
echo ============================================================
echo.
echo Pour tester dans VSCode:
echo 1. Ouvrir le fichier test-echo.algo
echo 2. Appuyer sur F5
echo 3. Verifier que SEULE la ligne "Bonjour depuis l'algorithme!" apparait
echo    (pas de commandes java visibles)
echo.
pause

