@echo off
REM Script de vérification de la santé du compilateur Algo
REM Vérifie l'absence de crashs et la compilation

echo ========================================
echo   VERIFICATION SANTE ALGO COMPILER
echo ========================================
echo.

echo [1/4] Verification des fichiers de crash...
if exist hs_err_*.log (
    echo [ERREUR] Fichiers de crash detectes!
    dir /b hs_err_*.log
    echo.
    echo Suggestion: Verifier gradle.properties et build.gradle.kts
    pause
    exit /b 1
) else (
    echo [OK] Aucun fichier de crash
)
echo.

echo [2/4] Nettoyage du projet...
call gradlew clean --quiet
if %ERRORLEVEL% NEQ 0 (
    echo [ERREUR] Le nettoyage a echoue
    pause
    exit /b 1
)
echo [OK] Projet nettoye
echo.

echo [3/4] Compilation du projet...
call gradlew build --quiet
if %ERRORLEVEL% NEQ 0 (
    echo [ERREUR] La compilation a echoue
    pause
    exit /b 1
)
echo [OK] Compilation reussie
echo.

echo [4/4] Verification finale des crashs...
if exist hs_err_*.log (
    echo [ERREUR] Nouveau crash detecte pendant la compilation!
    dir /b hs_err_*.log
    pause
    exit /b 1
) else (
    echo [OK] Aucun crash pendant la compilation
)
echo.

echo ========================================
echo   VERIFICATION TERMINEE AVEC SUCCES
echo ========================================
echo.
echo Le compilateur Algo fonctionne correctement!
echo JAR disponible: build\libs\algo-compiler-1.6.0.jar
echo.
pause

