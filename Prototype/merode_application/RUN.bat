@echo off
echo setting PATH

set "PATH="
	for /D %%b in ("C:\Program Files\Java\jre*") do (
		set "PATH=%%~b\bin;%PATH%"
	)
set "PATH=C:\WINDOWS\system32;%PATH%"
java -version

@set CLASSPATH=.
@set CLASSPATH=%CLASSPATH%;.\bin
@set CLASSPATH=%CLASSPATH%;.\lib\jgraphx.jar
@set CLASSPATH=%CLASSPATH%;.\lib\jcalendar-1.3.3.jar
@set CLASSPATH=%CLASSPATH%;.\lib\commons-beanutils-1.6.1.jar
@set CLASSPATH=%CLASSPATH%;.\lib\commons-configuration.jar
@set CLASSPATH=%CLASSPATH%;.\lib\looks-1.2.2.jar
@set CLASSPATH=%CLASSPATH%;.\lib\miglayout15-swing.jar
@set CLASSPATH=%CLASSPATH%;.\lib\swingx-0.9.2.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\antlr-2.7.6.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\commons-collections-3.1.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\dom4j-1.6.1.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\hibernate3.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\hibernate-jpa-2.0-api-1.0.1.Final.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\javassist-3.12.0.GA.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\jta-1.1.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\slf4j-api-1.6.1.jar
@set CLASSPATH=%CLASSPATH%;.\lib\hibernate3-jars\slf4j-simple-1.6.4.jar
@set CLASSPATH=%CLASSPATH%;.\database\hsqldb\lib\hsqldb.jar

java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -noExit -d .\bin .\src >logFile 2>&1


rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\dao\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\handlers\MerodeMainEventHandler.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\ui\tabs\operations\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\ui\tabs\operations\create\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\ui\tabs\operations\modify\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\ui\tabs\tables\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\ui\tabs\tables\views\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\ui\tabs\lists\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\driver\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\testing\*.java
rem java -jar lib\ecj-4.2.jar -classpath %CLASSPATH% -1.6 -nowarn -d .\bin .\src\tescav\*.java

findstr "ERROR " logFile
IF ERRORLEVEL 1 (
echo Application successfully compiled
echo Starting the application

javaw driver.MerodeMainEventHandlerGui

) else (
@echo:
@echo:
@echo:
echo ================================================================
echo There were errors while compiling your application.
echo PLEASE CHECK YOUR MODEL IN JMERMAID by doing Model - check model 
echo ================================================================
@pause
)
del logFile
EXIT












