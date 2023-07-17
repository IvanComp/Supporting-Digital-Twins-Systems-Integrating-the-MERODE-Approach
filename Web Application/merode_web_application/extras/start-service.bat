@echo off
echo Setting JAVA_HOME

set JAVA_HOME=C:\Program Files\Java\jre*
echo setting PATH
set "PATH="
	for /D %%b in ("C:\Program Files\Java\jre*") do (
		set "PATH=%%~b\bin;%PATH%"
	)
echo %PATH%
echo Display java version
java -version

dir
java -cp ../target/merode-web-application-1.0-jar-with-dependencies.jar MerodeApplicationService