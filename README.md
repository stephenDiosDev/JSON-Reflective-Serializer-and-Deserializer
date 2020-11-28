# CPSC 501 Assignment 3 Report
Repo Link: https://gitlab.cpsc.ucalgary.ca/stephen.dios/cpsc-501-assignment-3

## NOTE:
The tabbing for the visualizer is a little bit buggy.
- Objects are displayed in the order they occur, so if you have an object with instances a, b, c, then Objects a, b, and c
will be listed directly under that object in the exact order (sorry, didn't have enough time for tab order).
- When displaying an object5, it will display the results twice (bug)

# Network Code
The same code exists on 2 branches:
- master
- networkAttempt2ElectricBoogaloo

The code on master uses a Thread to simulate the client so you don't need a different machine to run code on master. 

The code on networkAttempt2ElectricBoogaloo requires 2 computers on the same network connection. You may have to do some port forwarding or
if on windows going to your network profile and setting it to "Private". The server computer runs "ReflectiveObjectSerialization.java" and
the client computer runs "Receiver.java". 

### How to set your windows network profile to Private
In the search bar for windows 10 go Windows Button --> Settings Gear Icon --> Network & Internet --> Properties --> Private
I also needed to port forward the ports I was using to get a successful connection.

# Junit Issue
The  JUnit test class "VizualizerOutputTest" will fail all of its tests, but only because it has different line seperators (I was developing on 
both windows and linux at the same time so it got a little messed up). Looking at the comparison differences, IntelliJ will tell 
you that the tests fail because of the line seprators issue (otherwise it passes perfectly). I can confirm that running the unit test code
on linux passes all tests. I apologize for this issue.

# Refactors

### Refactor 1: Remove unneeded imports, code, comments
#### Commit: 
9eb505a8
#### File Altered: 
Deserializer.java, ObjectCreator.java, Receiver.java, ReflectiveObjectSerialization.java, Sender.java, Serializer.java, Visualizer.java
#### Code Altered: 
As seen in the commit, I removed code that had been commented (mostly debug print statements), unused imports,
and a few comment that were there for my own understanding as I was coding, but is not needed anymore.
#### Purpose and Reward
The code was very cluttered with commented out code as I was working on it which made some sections of
code confusing and possibly dangerous if the code was uncommented. By removing them, I make the file
less cluttered and remove any possible maintenance issues that could arise from keeping them.

--------------------------------------------------------------------------------------------------------------------------

### Refactor 2: Remove Duplicate Code
#### Commit
9a8359e9
#### File Altered
Deserializer.java
#### Code Altered
I originally had 5 if statements that separate my custom objects found in ObjectCreator.java.
These if statements did the exact same thing, but served as a way to catch only those custom objects.
As seen in the commit history, I moved it all to 1 if statement, and the if statement checks for
any of those 5 custom objects.
#### Purpose and Reward
The main thing here was maintenance cost. If I wanted to change how these custom objects are handled,
I would have to change each if and else if statement to the exact same thing. Now it is much easier
to maintain. The downside though is if I wanted to give a special behaviour to one custom object,
I would need to partially undo this refactor.

--------------------------------------------------------------------------------------------------------------------------

### Refactor 3: Remove Duplicate Code
#### Commit
e7481473
#### File Altered
Deserializer.java
#### Code Altered
Around line 92 in Deserializer.java, I have a for loop that goes through each field/entry of an object
so I can look at the reference and process it to connect later. As seen in the commit history, I originally
had 1 loop for fields and one for array entries. I simply cut this down to one loop, and made the variable
named fieldEntries get either the "fields" JsonArray, or the "entries" JsonArray.
#### Purpose and Reward
This is another plus for maintenance as I only need to change the behaviour of the loop in one place
if I decide to process fields or entries differently. Similar to Refactor 2 though, if I want specialized
behaviour for fields or entries, I will have to partially undo the refactor.