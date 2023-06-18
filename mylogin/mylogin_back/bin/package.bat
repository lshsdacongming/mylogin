@echo off
echo.
echo [mag] package web project, generate war/jar file.
echo.

%~d0
cd %~dp0

cd ..
call mvn clean package -Dmaven.test.skip=true

pause