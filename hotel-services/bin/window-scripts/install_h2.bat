@echo off
REM This script installs the H2 database for the hotel-services application and uses DATA_DIR and LOG_DIR.

REM Set the version of H2 to download
set "H2_VERSION=2.1.214"
set "H2_JAR=h2-%H2_VERSION%.jar"
set "H2_URL=https://repo1.maven.org/maven2/com/h2database/h2/%H2_VERSION%/%H2_JAR%"

REM Set the directory where the H2 database will be installed
set "H2_DIR=%USERPROFILE%\h2"
REM Set the data directory and log directory
set "DATA_DIR=%H2_DIR%\data"
set "LOG_DIR=%H2_DIR%\logs"

REM Create the directories if they do not exist
if not exist "%H2_DIR%" (
    mkdir "%H2_DIR%"
)
if not exist "%DATA_DIR%" (
    mkdir "%DATA_DIR%"
)
if not exist "%LOG_DIR%" (
    mkdir "%LOG_DIR%"
)

REM Download the H2 database jar file
if not exist "%H2_DIR%\%H2_JAR%" (
    echo Downloading H2 database version %H2_VERSION%...
    powershell -Command "Invoke-WebRequest -Uri '%H2_URL%' -OutFile '%H2_DIR%\%H2_JAR%'"
)

REM Check if the download was successful
if exist "%H2_DIR%\%H2_JAR%" (
    echo H2 database version %H2_VERSION% installed successfully in %H2_DIR%.
) else (
    echo Failed to download H2 database. Please check your internet connection or the URL.
    exit /b 1
)