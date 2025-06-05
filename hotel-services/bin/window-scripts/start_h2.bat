@echo off
REM This script starts the H2 database server for the hotel-services application with user root and password P@ssword.
REM It uses DATA_DIR and LOG_DIR for data and log storage.
set DB_USER=root
set DB_PASSWORD=P@ssword
set H2_DIR=%USERPROFILE%\h2
set DATA_DIR=%H2_DIR%\data
set LOG_DIR=%H2_DIR%\logs
set H2_JAR=%H2_DIR%\h2-2.1.214.jar
set H2_PORT=9092

REM Check if the H2 jar file exists
if not exist "%H2_JAR%" (
    echo H2 jar file not found at %H2_JAR%. Please ensure it is installed correctly.
    exit /b 1
)

REM Start the H2 database server for local only at port %H2_PORT%
echo Starting H2 database server...
start "" java -cp "%H2_JAR%" org.h2.tools.Server -tcp -ifNotExists -tcpPort %H2_PORT% -baseDir "%DATA_DIR%" > "%LOG_DIR%\h2_server.log" 2>&1

REM Check if the server started successfully by pinging the port
timeout /t 5 >nul
echo Checking if H2 server is running...
powershell -Command "Test-NetConnection -ComputerName localhost -Port %H2_PORT% | Select-Object -ExpandProperty TcpTestSucceeded"

REM Start the H2 Shell for creating the database
REM Also Create a default schema hotel if it does not exist
echo Creating default schema hotel...
start "" java -cp "%H2_JAR%" org.h2.tools.RunScript -url jdbc:h2:tcp://localhost:%H2_PORT%/~/h2/data/hotel_services -user %DB_USER% -password %DB_PASSWORD% -script "%H2_DIR%\create_schema.sql" -driver org.h2.Driver

REM Print Connection information
echo H2 database server started successfully.
echo You can connect to the H2 database using the following URL:
echo jdbc:h2:tcp://localhost:%H2_PORT%/~/h2/data/hotel_services;USER=%DB_USER%;PASSWORD=%DB_PASSWORD%