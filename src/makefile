JFLAGS = -g
JC = javac
JE = java
BASE = cs6301/github/io
OBJECTS = Main.class $(BASE)/bridge/*.class $(BASE)/test/*.class
BRIDGE = semaphore
CARS = 50

Main.class: Main.java
	$(JC) $(JFLAGS) Main.java

clean:
	$(RM) $(OBJECTS)

experiment: Main.class
	$(JE) Main $(BRIDGE) $(CARS)

.PHONY: all clean
