@echo off
echo.
echo  [mag] run web project.
echo.

cd %~dp0
cd ../target

set JAVA_OPTS=-Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m

java -jar %JAVA_OPTS% mylogin_back.jar

cd bin
pause