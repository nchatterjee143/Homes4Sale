# Instructions #

In order to run this project, you must keep file in the repository in the same directory when you attempt to run Homes4SaleServer.java and Homes4SaleGUI.java. This ensures that everything will be read properly. First, you must ensure that the server is started by running Homes4SaleServer.java, then you must run Homes4SaleGUI.java, as this is the program that actually hosts the project program.
+ NOTE 1: When trying to create a new property as a seller, please ensure that there are no commas when setting any of the entries the program will ask you. For example, when asked for a description of the house you are selling, enter "3 bed 2 bath" instead of "3 bed, 2 bath"
+ NOTE 2: Please ensure there is at least 1 property in marketplace.txt before trying to act as a buyer.

# Student Roles #
+ Student 1: Neel Chatterjee -- Submitted Report on Brightspace, Submitted Vocareum Workspace
+ Student 2: Andreina Davila -- Coordinated meet up times, Debugged code, Submitted Project Presentation Video
+ Student 3: Kathleen O'Sullivan -- Worked on transition from terminal to GUI code

# Class Descriptions #

## Homes4SaleGUI.java ##
This class is the only other class that needs to be ran in this scenario. It is the main method of the entire project. The user interface of this project is handled through this program.

## Homes4SaleServer.java ##
This java class simply exists to ensure that the server is up and running. All processing from the client is handled through here. Please make sure that this is running before running Homes4SaleGUI.java!

## accounts.txt ##
This text file simply stores account information about each account, including their email addresses, passwords, and roles as either "sellers" or "buyers"

## marketplace.txt ##
Similar to accounts.txt, this text file stores information about the houses being sold that are in the marketplace.

## receipt.txt ##
This text file stores proof of every transaction that occurs.
