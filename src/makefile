JFLAGS = -g
JC = javac
JE = java
OBJECTS = Main.class cs6301/github/io/bridge/*.class cs6301/github/io/test/*.class
BRIDGE = semaphore
CARS = 50

Main.class: Main.java
	$(JC) $(JFLAGS) Main.java

clean:
	$(RM) $(OBJECTS)

experiment: Main.class
	$(JE) Main $(BRIDGE) $(CARS)

.PHONY: all clean
