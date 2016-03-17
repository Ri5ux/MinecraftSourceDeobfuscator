@ECHO OFF
SET JARNAME=srgsrc.jar
SET CLASSPATH=com.arisux.srgsrc.SRGSRC

DEL %JARNAME%
DIR /s /B *.java > tmp
MKDIR bin
JAVAC -d bin @tmp
DEL tmp

ECHO Manifest-Version: 1.0 >>tmp
ECHO Main-Class: %CLASSPATH% >>tmp

CD bin
JAR cfm ../%JARNAME% ../tmp *
CD..
RMDIR /s /q bin
DEL tmp