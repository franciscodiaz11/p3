README
CIIC 4020 - Data Structures
============================================
------------------
Project Author
------------------
+ Francisco Diaz
+ 802-15-2172
+ Inst. Kelvin Roche
+ Prof. Pedro Rivera

==============================================
------------------
Project Description
------------------
This programming project works with virtual disks systems. It implements a virtual file system
which allows the user to manipulate to a certain extent files inside such disks. Commands such as ls and cp are implemented to work on the files created. 

==============================================
------------------
Program Details:
------------------
+ The project is based on implementing a Virtual file System which allows the user
to manipulate files inside the virtual disk systems.
+ The main package in the program is diskUtilities:
	
	* Which contains the DiskUnit, VirtualDiskBlock, DirManager, DiskManager, DiskUtils, FileLoaderandManager, FreeBlockManager and INodeManager java files.
	
+ The classes are based on reading and writing information on to the RandomAccessFiles simulating a disk system, through the virtual file system and its virtual shell.

===============================================
------------------------------
Instructions for Eclipse:
------------------------------
+ First unzip the file: P2_4035_802152172_162.zip
+ Open the project in Eclipse.
+ Make sure the Eclipse encoding is set to UTF-8.	
+ Run p3Main.java file inside the main package.
  
================================================
-------------------------------
Instructions for Terminal 
-------------------------------
+ Open your terminal or command prompt.
+ Enter the file in which the project is stored.
+ On Windows use the dir command and MacOS or Linux use ls command to verify you are in the correct file.
+ Once inside the project file, compile the java files:
	>>>javac -d src -sourcepath src src/main/p3Main.java
	
+ Run the java class files using one of either commands, depending on the situation:
	>>>java -classpath src main.p3Main

