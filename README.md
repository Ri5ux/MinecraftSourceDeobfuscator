MinecraftSourceDeobfuscator is a tool for Minecraft developers that correctly remaps the obfuscated function and field names in a selected set of source files.

Program Arguments

-a <SRG DIRECTORY> <SRC DIRECTORY> (Automatically runs the tool on launch with the specified SRG and SRC directories)

-nogui (Disables the graphical interface for use with consoles. Must be used with -a)

-help (Shows help)

EXAMPLE: 
java -jar mc-src-deobf.jar -a "C:\srg\" "C:\src\" -nogui