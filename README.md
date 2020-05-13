# VT20-Ass_1

The program will start by initialising the class gui.
This class is the interface in which you decide what .xml-files to use.
Reduced Grid, Complete Grid or select and load your own xml-files.
Sbase default value is 100MVA, but can be set to a prefered value.

Class Start is initiated, which opens the class following classes:

1. ReadXML - Search through the cimobjects for the requested elements and attributes and add them to the corresponding Arraylist.

2. SQLdata - Takes the arraylists created in ReadXML and create a database and add all data in tables.

3. Topology - Decides the number of nodes by grouping up connectivity nodes, based on circuit breaker states.
This will result in Connectivity Node Groups (CNG), that each will represent one node in the grid.
By analysing the CNG, with the help of AC-Lines and PowerTransformers, all data that is required to calculate the Y-Matrix is gathered and
used by next called class

4. YMatrix - This class will calculate the line admittances and then add them upp to get the total Y-Matrix.

When the Y-Matrix is calculated it's presented in a table.

 

 