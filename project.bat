@ECHO OFF
TITLE=MCPMappingPatcher Build Script
SET WORKSPACE=eclipse
SET LIBRARIES="bin\\"
SET CLASSPATH="com.arisux.srgsrc.SRGSRC"
SET JARNAME=mcpmappingpatcher.jar
SET PAR1=%1

IF '%PAR1%'=='setupWorkspace' GOTO :SETUP
IF '%PAR1%'=='run' GOTO :RUN
IF '%PAR1%'=='runOnly' GOTO :RUNONLY
IF '%PAR1%'=='build' GOTO :BUILD
IF '%PAR1%'=='clean' GOTO :CLEAN
IF '%PAR1%'=='cleanup' GOTO :CLEAN
IF NOT '%PAR1%'=='' GOTO :INVALIDPARAMETER

:NOPARAMETERS
ECHO No parameters were specified. Run this script with one of the following:
GOTO :PARAMETERS

:INVALIDPARAMETER
ECHO Invalid parameter specified. Please try again
GOTO :PARAMETERS

:PARAMETERS
ECHO.
ECHO setupWorkspace
ECHO build
ECHO run
ECHO runOnly
ECHO clean
ECHO cleanup
PAUSE
GOTO :END

:SETUP
SET /P QUERY="Continue setting up your workspace?[Y/N] "

IF NOT '%QUERY%'=='' SET QUERY=%QUERY:~0,1%
IF /I '%QUERY%'=='Y' GOTO :SETUP
IF /I '%QUERY%'=='N' GOTO :END
GOTO :END

:SETUP
ECHO Removing the existing eclipse project...

:CLEAN
IF EXIST "bin" (
RMDIR /S /Q "bin"
)
IF EXIST "launch" (
RMDIR /S /Q "launch"
)
IF EXIST ".project" (
DEL /Q .project
)
IF EXIST ".classpath" (
DEL /Q .classpath
)
ECHO Done.
IF '%PAR1%'=='setupWorkspace' GOTO :SETUPWORKSPACE
GOTO :END

:SETUPWORKSPACE
ECHO Creating the default eclipse project...
XCOPY /S /Y "%WORKSPACE%" ".\"
GOTO :END

:BUILD
ECHO Building the project...
IF NOT EXIST bin MKDIR bin
DEL %JARNAME%
DIR /s /B *.java > tmp
JAVAC -d bin @tmp
DEL tmp
GOTO :END

:RUN
CALL :BUILD
CALL :RUNONLY
GOTO :END

:RUNONLY
ECHO Running the project...
START JAVA -cp %LIBRARIES% %CLASSPATH%
GOTO :END

:END
