# Bridge

Bridge implementation using __Semaphores__ and __Monitors__ and experiments.

__Author:__

- Hanlin He (hxh160630@utdallas.edu)
- Lizhong Zhang (lxz160730@utdallas.edu)

__Directory Tree:__

    .
    ├── LICENSE
    ├── README.md
    ├── doc
    │   ├── algorithm.pdf
    │   └── algorithm.tex
    └── src
        ├── Main.java
        ├── cs6301
        │   └── github
        │       └── io
        │           ├── bridge
        │           │   ├── Bridge.java
        │           │   ├── MonitorBridge.java
        │           │   └── SemaphoreBridge.java
        │           └── test
        │               └── TestDriver.java
        └── makefile

    7 directories, 10 files

## Compile

In `src` folder, the following commands can be used to compile and cleanup.

    # Compile all source code to Java binary file.
    make

    # Cleanup jar and class file.
    make clean

# Execution

After compilation, the following commands can be used to run experiments on the
bridge.

    # Run experiment on specified bridge type with some cars.
    make experiment BRIDGE=<bridge type> CARS=<# of cars to run experiment>

In the experiment, `Car` defines the basic action of a car as a thread. It
first sleeps for a random amount of time, and then arrives at the bridge (
invoke `arriveBridge` function of the Bridge instance ). Once return from
`arriveBridge`, it sleeps again for a random amount of time within 500ms, and
then leave the bridge ( invoking `leaveBridge` function of the Bridge
instance).

During runtime, each `Car` instance will record three event timestamp:
`arriveTime`, `enterTime`, `leaveTime`. Upon each event, Car thread will append
a event timestamp to a queue. This queue can be used to check if the execution
satisfy the algorithm demand.

Since the order of the event can only be check after all Car thread finished,
Car will print a dot (.) in standard output after leaving the bridge indicating
the thread will finish.

After all threads joined (finished), test will print the events list as well
as the car list based on `leaveTime` order in standard output.

# Example

An example execution from compile to execute is listed below.

    $ make
    javac -g cs6301/github/io/bridge/*.java
    javac -g cs6301/github/io/test/*.java
    javac -g Main.java

    $ make experiment BRIDGE=semaphore CARS=10
    java Main semaphore 10
    Initializing 10 cars.
    Starting experiment for semaphore bridge with 10 cars.
    ..........
    Experiment finished.

    Events timeline is as follow:
    Car	0	of	EAST_BOUND	arrive	at	2018-10-21T10:24:13.411
    Car	7	of	WEST_BOUND	arrive	at	2018-10-21T10:24:13.411
    Car	0	of	EAST_BOUND	enter	at	2018-10-21T10:24:13.414
    Car	6	of	EAST_BOUND	arrive	at	2018-10-21T10:24:13.653
    Car	0	of	EAST_BOUND	leave	at	2018-10-21T10:24:13.653
    Car	7	of	WEST_BOUND	enter	at	2018-10-21T10:24:13.653
    Car	3	of	EAST_BOUND	arrive	at	2018-10-21T10:24:13.797
    Car	9	of	WEST_BOUND	arrive	at	2018-10-21T10:24:13.848
    Car	1	of	EAST_BOUND	arrive	at	2018-10-21T10:24:13.861
    Car	7	of	WEST_BOUND	leave	at	2018-10-21T10:24:13.992
    Car	6	of	EAST_BOUND	enter	at	2018-10-21T10:24:13.993
    Car	3	of	EAST_BOUND	enter	at	2018-10-21T10:24:13.993
    Car	3	of	EAST_BOUND	leave	at	2018-10-21T10:24:14.196
    Car	2	of	WEST_BOUND	arrive	at	2018-10-21T10:24:14.329
    Car	4	of	WEST_BOUND	arrive	at	2018-10-21T10:24:14.376
    Car	6	of	EAST_BOUND	leave	at	2018-10-21T10:24:14.431
    Car	9	of	WEST_BOUND	enter	at	2018-10-21T10:24:14.432
    Car	9	of	WEST_BOUND	leave	at	2018-10-21T10:24:14.657
    Car	1	of	EAST_BOUND	enter	at	2018-10-21T10:24:14.657
    Car	5	of	WEST_BOUND	arrive	at	2018-10-21T10:24:14.676
    Car	8	of	WEST_BOUND	arrive	at	2018-10-21T10:24:14.676
    Car	1	of	EAST_BOUND	leave	at	2018-10-21T10:24:14.983
    Car	4	of	WEST_BOUND	enter	at	2018-10-21T10:24:14.984
    Car	2	of	WEST_BOUND	enter	at	2018-10-21T10:24:14.984
    Car	5	of	WEST_BOUND	enter	at	2018-10-21T10:24:14.984
    Car	8	of	WEST_BOUND	enter	at	2018-10-21T10:24:14.984
    Car	4	of	WEST_BOUND	leave	at	2018-10-21T10:24:15.236
    Car	2	of	WEST_BOUND	leave	at	2018-10-21T10:24:15.271
    Car	8	of	WEST_BOUND	leave	at	2018-10-21T10:24:15.447
    Car	5	of	WEST_BOUND	leave	at	2018-10-21T10:24:15.482

    Sorting all cars by leaving bridge time:
    Car{id=0,	direction=EAST_BOUND,	arriveTime=2018-10-21T10:24:13.411,	enterTime=2018-10-21T10:24:13.414,	leaveTime=2018-10-21T10:24:13.653}
    Car{id=7,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:13.411,	enterTime=2018-10-21T10:24:13.653,	leaveTime=2018-10-21T10:24:13.992}
    Car{id=3,	direction=EAST_BOUND,	arriveTime=2018-10-21T10:24:13.797,	enterTime=2018-10-21T10:24:13.993,	leaveTime=2018-10-21T10:24:14.196}
    Car{id=6,	direction=EAST_BOUND,	arriveTime=2018-10-21T10:24:13.653,	enterTime=2018-10-21T10:24:13.993,	leaveTime=2018-10-21T10:24:14.431}
    Car{id=9,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:13.848,	enterTime=2018-10-21T10:24:14.432,	leaveTime=2018-10-21T10:24:14.657}
    Car{id=1,	direction=EAST_BOUND,	arriveTime=2018-10-21T10:24:13.861,	enterTime=2018-10-21T10:24:14.657,	leaveTime=2018-10-21T10:24:14.983}
    Car{id=4,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:14.376,	enterTime=2018-10-21T10:24:14.984,	leaveTime=2018-10-21T10:24:15.236}
    Car{id=2,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:14.329,	enterTime=2018-10-21T10:24:14.983,	leaveTime=2018-10-21T10:24:15.271}
    Car{id=8,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:14.676,	enterTime=2018-10-21T10:24:14.984,	leaveTime=2018-10-21T10:24:15.447}
    Car{id=5,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:14.676,	enterTime=2018-10-21T10:24:14.984,	leaveTime=2018-10-21T10:24:15.482}

    $ make experiment BRIDGE=monitor CARS=10
    java Main monitor 10
    Initializing 10 cars.
    Starting experiment for monitor bridge with 10 cars.
    ..........
    Experiment finished.

    Events timeline is as follow:
    Car	2	of	EAST_BOUND	arrive	at	2018-10-21T10:24:28.386
    Car	2	of	EAST_BOUND	enter	at	2018-10-21T10:24:28.388
    Car	8	of	EAST_BOUND	arrive	at	2018-10-21T10:24:28.417
    Car	8	of	EAST_BOUND	enter	at	2018-10-21T10:24:28.417
    Car	7	of	WEST_BOUND	arrive	at	2018-10-21T10:24:28.620
    Car	2	of	EAST_BOUND	leave	at	2018-10-21T10:24:28.621
    Car	5	of	EAST_BOUND	arrive	at	2018-10-21T10:24:28.663
    Car	8	of	EAST_BOUND	leave	at	2018-10-21T10:24:28.739
    Car	7	of	WEST_BOUND	enter	at	2018-10-21T10:24:28.740
    Car	1	of	WEST_BOUND	arrive	at	2018-10-21T10:24:28.861
    Car	4	of	WEST_BOUND	arrive	at	2018-10-21T10:24:28.998
    Car	7	of	WEST_BOUND	leave	at	2018-10-21T10:24:29.015
    Car	5	of	EAST_BOUND	enter	at	2018-10-21T10:24:29.016
    Car	6	of	WEST_BOUND	arrive	at	2018-10-21T10:24:29.055
    Car	5	of	EAST_BOUND	leave	at	2018-10-21T10:24:29.127
    Car	1	of	WEST_BOUND	enter	at	2018-10-21T10:24:29.127
    Car	4	of	WEST_BOUND	enter	at	2018-10-21T10:24:29.127
    Car	6	of	WEST_BOUND	enter	at	2018-10-21T10:24:29.128
    Car	0	of	EAST_BOUND	arrive	at	2018-10-21T10:24:29.134
    Car	6	of	WEST_BOUND	leave	at	2018-10-21T10:24:29.433
    Car	1	of	WEST_BOUND	leave	at	2018-10-21T10:24:29.445
    Car	3	of	WEST_BOUND	arrive	at	2018-10-21T10:24:29.527
    Car	4	of	WEST_BOUND	leave	at	2018-10-21T10:24:29.559
    Car	0	of	EAST_BOUND	enter	at	2018-10-21T10:24:29.560
    Car	9	of	WEST_BOUND	arrive	at	2018-10-21T10:24:29.826
    Car	0	of	EAST_BOUND	leave	at	2018-10-21T10:24:30.037
    Car	3	of	WEST_BOUND	enter	at	2018-10-21T10:24:30.037
    Car	9	of	WEST_BOUND	enter	at	2018-10-21T10:24:30.037
    Car	9	of	WEST_BOUND	leave	at	2018-10-21T10:24:30.154
    Car	3	of	WEST_BOUND	leave	at	2018-10-21T10:24:30.203

    Sorting all cars by leaving bridge time:
    Car{id=2,	direction=EAST_BOUND,	arriveTime=2018-10-21T10:24:28.386,	enterTime=2018-10-21T10:24:28.388,	leaveTime=2018-10-21T10:24:28.621}
    Car{id=8,	direction=EAST_BOUND,	arriveTime=2018-10-21T10:24:28.417,	enterTime=2018-10-21T10:24:28.417,	leaveTime=2018-10-21T10:24:28.739}
    Car{id=7,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:28.620,	enterTime=2018-10-21T10:24:28.740,	leaveTime=2018-10-21T10:24:29.015}
    Car{id=5,	direction=EAST_BOUND,	arriveTime=2018-10-21T10:24:28.663,	enterTime=2018-10-21T10:24:29.016,	leaveTime=2018-10-21T10:24:29.127}
    Car{id=6,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:29.055,	enterTime=2018-10-21T10:24:29.128,	leaveTime=2018-10-21T10:24:29.433}
    Car{id=1,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:28.861,	enterTime=2018-10-21T10:24:29.127,	leaveTime=2018-10-21T10:24:29.445}
    Car{id=4,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:28.998,	enterTime=2018-10-21T10:24:29.127,	leaveTime=2018-10-21T10:24:29.559}
    Car{id=0,	direction=EAST_BOUND,	arriveTime=2018-10-21T10:24:29.134,	enterTime=2018-10-21T10:24:29.560,	leaveTime=2018-10-21T10:24:30.037}
    Car{id=9,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:29.826,	enterTime=2018-10-21T10:24:30.037,	leaveTime=2018-10-21T10:24:30.154}
    Car{id=3,	direction=WEST_BOUND,	arriveTime=2018-10-21T10:24:29.527,	enterTime=2018-10-21T10:24:30.037,	leaveTime=2018-10-21T10:24:30.203}

    $ make clean
    rm -f Main.class cs6301/github/io/bridge/*.class cs6301/github/io/test/*.class
