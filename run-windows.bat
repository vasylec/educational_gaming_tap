@echo off
cd /d "%~dp0"

:: === CONFIG ===
set FX_PATH=lib
set BIN_PATH=out
set MAIN_CLASS=Main

:: === JVM OPTIONS ===
set JAVA_OPTS=-Xms256m -Xmx2048m ^
--enable-native-access=javafx.graphics ^
--enable-native-access=javafx.media ^
--module-path "%FX_PATH%" ^
--add-modules javafx.controls,javafx.fxml,javafx.media

:: === RUN ===
java %JAVA_OPTS% --enable-native-access=javafx.graphics --enable-native-access=javafx.media --module-path "%FX_PATH%" --add-modules javafx.controls,javafx.fxml,javafx.media -cp "%BIN_PATH%" %MAIN_CLASS%
pause
