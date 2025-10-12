@echo off
setlocal enabledelayedexpansion

rem === Path constants =======================================================
set "PROJECT_DIR=%~dp0"
set "FX_LIB=%PROJECT_DIR%lib"
set "MODULES=javafx.controls,javafx.fxml,javafx.media"

rem === Check prerequisites ==================================================
if not defined JAVA_HOME (
    echo [ERROR] Set JAVA_HOME to a JDK 17 or newer installation and reopen this terminal.
    exit /b 1
)

if not exist "%JAVA_HOME%\bin\javac.exe" (
    echo [ERROR] JAVA_HOME is set to "%JAVA_HOME%" but no javac.exe was found.
    exit /b 1
)

if not exist "%FX_LIB%" (
    echo [ERROR] JavaFX library folder not found: "%FX_LIB%".
    exit /b 1
)

rem === Compile sources ======================================================
pushd "%PROJECT_DIR%"
if not exist "out" mkdir "out"

set "SOURCES="
for /f "delims=" %%F in ('dir /b /s src\*.java') do (
    set "SOURCES=!SOURCES! "%%F""
)

echo Compiling sources...
"%JAVA_HOME%\bin\javac.exe" --module-path "%FX_LIB%" --add-modules %MODULES% -d out %SOURCES%
if errorlevel 1 (
    echo [ERROR] Compilation failed. See diagnostics above.
    popd
    exit /b 1
)

rem === Launch the game ======================================================
echo Launching game...
"%JAVA_HOME%\bin\java.exe" --module-path "%FX_LIB%" --add-modules %MODULES% -cp out Main
set "EXIT_CODE=%ERRORLEVEL%"
popd
exit /b %EXIT_CODE%
