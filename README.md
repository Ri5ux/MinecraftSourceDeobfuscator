SRGSRC is a Minecraft modding tool for developers that use the Minecraft Coder Pack and/or Minecraft Forge. This tool takes a selected directory of java source files and remaps any known SRG names into the correctly mapped names.

Program Arguments

-a <SRG DIRECTORY> <SRC DIRECTORY> (Automatically runs the tool on launch with the specified SRG and SRC directories)

-nogui (Disables the graphical interface for use with consoles. Must be used with -a)

-help (Shows help)

EXAMPLE: 
java -jar srgsrc.jar -a "C:\srg\" "C:\src\" -nogui