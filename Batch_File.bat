@echo off

javac *.java
java AutosarFileReorderer Normal_File.arxml
java AutosarFileReorderer Empty_File.arxml
java AutosarFileReorderer Not_Valid_File.xml
echo All files processed.
pause