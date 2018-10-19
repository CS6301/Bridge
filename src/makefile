JFLAGS = -g
JC = javac
JE = java
BASE = cs6301/github/io
OBJECTS = Main.class $(BASE)/bridge/*.class $(BASE)/test/*.class
BRIDGE = semaphore
CARS = 50

Main.class: $(BASE)/bridge/*.class $(BASE)/test/*.class Main.java
	$(JC) $(JFLAGS) Main.java

$(BASE)/bridge/*.class: $(BASE)/bridge/*.java
	$(JC) $(JFLAGS) $(BASE)/bridge/*.java

$(BASE)/test/*.class: $(BASE)/test/*.java
	$(JC) $(JFLAGS) $(BASE)/test/*.java

clean:
	$(RM) $(OBJECTS)

experiment: Main.class
	$(JE) Main $(BRIDGE) $(CARS)

.PHONY: experiment clean
