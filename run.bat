@echo off
rem Compile les fichiers Java
javac -d bin src\**\*.java -cp res\

rem Exécute le programme
java -cp bin;res main.Main

rem Pause pour voir les messages d'erreur éventuels
pause
