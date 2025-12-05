@echo off
echo ===============================================
echo Test de suppression de l'alias 'a'
echo ===============================================
echo.

echo Test 1: Fichier avec variable 'a' et boucle 'a' (devrait REUSSIR)
echo ---------------------------------------------------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_alias_a_supprime.algo"
echo.
echo.

echo Test 2: Fichier avec 'a' dans la boucle pour (devrait ECHOUER)
echo ---------------------------------------------------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_erreur_avec_a.algo"
echo.
echo.

echo ===============================================
echo Tests termines
echo ===============================================
pause

