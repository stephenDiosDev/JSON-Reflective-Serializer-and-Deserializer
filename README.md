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


### 1 Refactor
Commit: e7481473
I had 2 functions that did the exact same thing except for a single line, so I removed the duplicate
function.


This was the only case of a deliberate refactoring. I had instances throughout the project where I made small corrections
but did not document them as separate commits.


