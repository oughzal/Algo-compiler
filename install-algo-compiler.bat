@echo off
setlocal EnableDelayedExpansion

:: ============================================
::     INSTALLATEUR ALGO-COMPILER v1.0.0
:: ============================================

echo.
echo ============================================
echo    INSTALLATEUR ALGO-COMPILER v1.0.0
echo ============================================
echo.

:: Vérifier les droits administrateur
net session >nul 2>&1
if %errorLevel% neq 0 (
    echo [ERREUR] Ce script necessite les droits administrateur.
    echo Clic droit sur le fichier et selectionner "Executer en tant qu'administrateur"
    echo.
    pause
    exit /b 1
)

:: Définir les chemins
set "INSTALL_DIR=C:\Program Files\Algo-Compiler"
set "JAR_NAME=algo-compiler-1.0.0.jar"
set "SCRIPT_NAME=algoc.bat"

echo [1/6] Verification des prerequis...
echo.

:: Vérifier Java
java -version >nul 2>&1
if %errorLevel% neq 0 (
    echo [ERREUR] Java n'est pas installe ou n'est pas dans le PATH.
    echo.
    echo Telechargez et installez Java 21 ou superieur depuis :
    echo - https://www.oracle.com/java/technologies/downloads/
    echo - https://adoptium.net/
    echo.
    pause
    exit /b 1
)

echo [OK] Java est installe.
java -version 2>&1 | findstr /C:"version"
echo.

echo [2/6] Creation du repertoire d'installation...
if not exist "%INSTALL_DIR%" (
    mkdir "%INSTALL_DIR%"
    echo [OK] Repertoire cree : %INSTALL_DIR%
) else (
    echo [OK] Repertoire existe deja : %INSTALL_DIR%
)
echo.

echo [3/6] Copie du compilateur...
set "SOURCE_JAR=%~dp0build\libs\%JAR_NAME%"

if not exist "%SOURCE_JAR%" (
    echo [ERREUR] Le fichier %JAR_NAME% n'existe pas dans build\libs\
    echo.
    echo Veuillez compiler le projet d'abord avec :
    echo   gradlew build
    echo.
    pause
    exit /b 1
)

copy /Y "%SOURCE_JAR%" "%INSTALL_DIR%\" >nul
if %errorLevel% equ 0 (
    echo [OK] Compilateur copie vers %INSTALL_DIR%
) else (
    echo [ERREUR] Impossible de copier le compilateur
    pause
    exit /b 1
)
echo.

echo [4/6] Creation du script wrapper...

:: Créer le script wrapper algoc.bat
(
echo @echo off
echo :: Algo-Compiler Wrapper Script
echo :: Usage: algoc fichier.algo
echo.
echo if "%%~1"=="" ^(
echo     echo Usage: algoc fichier.algo
echo     exit /b 1
echo ^)
echo.
echo java -jar "%INSTALL_DIR%\%JAR_NAME%" %%*
) > "%INSTALL_DIR%\%SCRIPT_NAME%"

if %errorLevel% equ 0 (
    echo [OK] Script wrapper cree : %SCRIPT_NAME%
) else (
    echo [ERREUR] Impossible de creer le script wrapper
    pause
    exit /b 1
)
echo.

echo [5/6] Ajout au PATH systeme...

:: Vérifier si le chemin est déjà dans le PATH
echo %PATH% | find /i "%INSTALL_DIR%" >nul
if %errorLevel% equ 0 (
    echo [OK] Le chemin est deja dans le PATH
) else (
    :: Ajouter au PATH système via le registre
    for /f "skip=2 tokens=3*" %%a in ('reg query "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v Path 2^>nul') do set "SYSTEM_PATH=%%a %%b"

    :: Supprimer les espaces en trop à la fin
    if "!SYSTEM_PATH:~-1!"==" " set "SYSTEM_PATH=!SYSTEM_PATH:~0,-1!"

    :: Ajouter le nouveau chemin
    setx PATH "!SYSTEM_PATH!;%INSTALL_DIR%" /M >nul 2>&1

    if %errorLevel% equ 0 (
        echo [OK] Chemin ajoute au PATH systeme
        echo     %INSTALL_DIR%
    ) else (
        echo [AVERTISSEMENT] Impossible d'ajouter au PATH automatiquement
        echo Ajoutez manuellement ce chemin au PATH :
        echo     %INSTALL_DIR%
    )
)
echo.

echo [6/6] Creation du desinstallateur...

:: Créer le désinstallateur
(
echo @echo off
echo setlocal
echo.
echo echo ============================================
echo echo    DESINSTALLATEUR ALGO-COMPILER
echo echo ============================================
echo echo.
echo.
echo net session ^>nul 2^>^&1
echo if %%errorLevel%% neq 0 ^(
echo     echo [ERREUR] Ce script necessite les droits administrateur.
echo     pause
echo     exit /b 1
echo ^)
echo.
echo echo Desinstallation d'Algo-Compiler...
echo echo.
echo.
echo :: Supprimer du PATH
echo for /f "skip=2 tokens=3*" %%%%a in ^('reg query "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v Path 2^^^>nul'^) do set "SYSTEM_PATH=%%%%a %%%%b"
echo set "SYSTEM_PATH=%%SYSTEM_PATH:%INSTALL_DIR%;=%%"
echo setx PATH "%%SYSTEM_PATH%%" /M ^>nul 2^>^&1
echo.
echo :: Supprimer les fichiers
echo echo Suppression des fichiers...
echo rd /s /q "%INSTALL_DIR%" 2^>nul
echo.
echo if exist "%INSTALL_DIR%" ^(
echo     echo [ERREUR] Impossible de supprimer completement le repertoire
echo     echo Supprimez manuellement : %INSTALL_DIR%
echo ^) else ^(
echo     echo [OK] Algo-Compiler desinstalle avec succes
echo ^)
echo.
echo pause
) > "%INSTALL_DIR%\uninstall.bat"

echo [OK] Desinstallateur cree : uninstall.bat
echo.

:: Créer un raccourci sur le bureau (optionnel)
echo Voulez-vous creer un raccourci sur le bureau ? (O/N)
set /p CREATE_SHORTCUT="> "

if /i "!CREATE_SHORTCUT!"=="O" (
    echo Creation du raccourci...
    :: Le raccourci sera créé par un script PowerShell temporaire
    powershell -Command "$WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%USERPROFILE%\Desktop\Algo-Compiler.lnk'); $Shortcut.TargetPath = '%INSTALL_DIR%\%SCRIPT_NAME%'; $Shortcut.IconLocation = '%INSTALL_DIR%\%JAR_NAME%'; $Shortcut.Description = 'Algo-Compiler - Compilateur de pseudo-code'; $Shortcut.Save()"
    echo [OK] Raccourci cree sur le bureau
    echo.
)

echo ============================================
echo    INSTALLATION TERMINEE AVEC SUCCES !
echo ============================================
echo.
echo Emplacement : %INSTALL_DIR%
echo Commande    : algoc
echo.
echo Pour utiliser le compilateur :
echo   algoc fichier.algo
echo.
echo Exemples :
echo   algoc examples\hello.algo
echo   algoc mon_programme.algo
echo.
echo IMPORTANT : Fermez et rouvrez votre terminal
echo             pour que le PATH soit mis a jour
echo.
echo Pour desinstaller : %INSTALL_DIR%\uninstall.bat
echo.
echo ============================================
echo.
pause

