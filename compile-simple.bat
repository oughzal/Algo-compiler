@echo off
echo ========================================
echo   Compilation Algo-Compiler
echo   Configuration Standard (sans ProGuard)
echo ========================================
echo.

echo Arret des processus Java...
taskkill /F /IM java.exe 2>nul >nul
timeout /t 2 /nobreak >nul

echo Compilation...
call gradlew.bat build --no-daemon

echo.
if exist "build\libs\algo-compiler-1.0.0.jar" (
    echo ========================================
    echo   SUCCES !
    echo ========================================
    echo.
    echo JAR genere: build\libs\algo-compiler-1.0.0.jar
    for %%A in (build\libs\algo-compiler-1.0.0.jar) do (
        set /a sizemb=%%~zA / 1048576
        echo Taille: !sizemb! MB
    )
    echo.
    echo Test:
    java -jar build\libs\algo-compiler-1.0.0.jar examples\hello.algo
) else (
    echo ========================================
    echo   ERREUR
    echo ========================================
    echo JAR non trouve
)
echo.
pause

