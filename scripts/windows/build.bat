CD ../../

DIR /s /B *.java > scripts\routes

RD  bin\res /S/Q
XCOPY src\res bin\res\ /E/Y

javac -Xlint:unchecked -Xdiags:verbose -d bin @scripts\routes
CD scripts\windows