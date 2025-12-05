@echo off
echo ============================================
echo TEST DES MATRICES - Algo-Compiler v1.6.0
echo ============================================
echo.

echo Test 1 : Matrice 3x3 de base
echo ------------------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_matrices.algo"
echo.
echo.

echo Test 2 : Operations matricielles
echo ----------------------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_operations_matrices.algo"
echo.
echo.

echo Test 3 : Table de multiplication
echo ----------------------------------
"C:\Program Files\Java\jdk-21\bin\java.exe" -jar "C:\Users\Omar\Documents\Algo-compiler\build\libs\algo-compiler-1.0.0.jar" "C:\Users\Omar\Documents\Algo-compiler\examples\test_table_multiplication.algo"
echo.
echo.

echo ============================================
echo TOUS LES TESTS TERMINÉS
echo ============================================
pause

