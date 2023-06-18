@echo off
echo.
echo [msg] clean package path.
echo.

%~d0
cd %~dp0

cd ..
call mvn clean

pause