CD ../../
DIR /s /B *.java > scripts\routes

RD  bin\ /S/Q
MKDIR bin\
XCOPY src\res bin\res\ /E/Y

javac -Xlint:unchecked -Xdiags:verbose -d bin @scripts\routes
CD scripts\windows