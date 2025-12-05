@echo off
echo ===============================================
echo Test des Nouvelles Fonctionnalités
echo - Initialisation de variables
echo - Tableaux litteraux
echo ===============================================
echo.

echo Test 1: Initialisation de variables
echo ------------------------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_initialisation.algo"
echo.
echo.

echo Test 2: Tableaux litteraux
echo ---------------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_tableaux_litteraux.algo"
echo.
echo.

echo Test 3: Test complet
echo --------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_complet_initialisation.algo"
echo.
echo.

echo ===============================================
echo Tests termines
echo ===============================================
pause

