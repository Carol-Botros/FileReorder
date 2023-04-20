@echo off

for %%f in (%inputDir%\*.arxml) do (
    echo Reordering file "%%~nxf"...
    java -cp "%USERPROFILE%\Desktop\AutosarFileReorderer\;." autosarfilereorderer.AutosarFileReorderer "%%f"
    move "%%~dpnf_mod.arxml" %outputDir%\%%~nxf
    echo Output written to %outputDir%\%%~nxf
)

echo All files processed.
pause
