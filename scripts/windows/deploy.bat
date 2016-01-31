CD ../../
MKDIR packages\
javapackager -deploy -native image -Bicon=Jurm.ico -outdir packages -outfile Jurm -srcdir dist -appclass main.Main -name Jurm -title "Jurm" 
CD scripts\windows