@echo off
cd /d "%~dp0"
echo Testing MatriceSimple...
java -jar "build\libs\algo-compiler-1.6.0.jar" "test_simple.algo"
echo.
echo Done.
pause

