@echo off
echo ============================================
echo    PUSH FINAL - CORRECTIONS GITHUB PAGES
echo ============================================
echo.

cd /d "%~dp0"

echo [1/3] Verification du statut Git...
git status
echo.

echo [2/3] Ajout du resume final...
git add "RÉSUMÉ_FINAL_GITHUB_PAGES.md"
echo.

echo [3/3] Push vers GitHub...
git push origin main
echo.

if %ERRORLEVEL% EQU 0 (
    echo ============================================
    echo    PUSH REUSSI!
    echo ============================================
    echo.
    echo Le site GitHub Pages va se rebuilder automatiquement.
    echo Attendez 2-5 minutes puis visitez:
    echo https://oughzal.github.io/Algo-compiler/
    echo.
    echo Verifiez aussi les Actions GitHub:
    echo https://github.com/oughzal/Algo-compiler/actions
) else (
    echo ============================================
    echo    ERREUR LORS DU PUSH
    echo ============================================
    echo Verifiez votre connexion et reessayez.
)

echo.
pause

