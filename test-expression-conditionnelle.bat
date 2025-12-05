@echo off
echo ===============================================
echo Test de l'Expression Conditionnelle (Ternaire)
echo ===============================================
echo.

echo Test 1: Ternaire simple
echo -----------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_ternaire_simple.algo"
echo.
echo.

echo Test 2: Expression conditionnelle complete
echo ------------------------------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_expression_conditionnelle.algo"
echo.
echo.

echo ===============================================
echo Tests termines
echo ===============================================
pause

