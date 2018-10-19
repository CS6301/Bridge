package cs6301.github.io.test;

import cs6301.github.io.bridge.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * TestDriver defines the experiment suite to test Bridge implementation
 * using Semaphores and Monitors.
 */
public class TestDriver {

  private final static int ONBRIDGE = 500;
  private final static DateFormat DATE_FORMAT =
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
  private final static String EVENT_FORMAT = "Car\t%d\tof\t%s\t%s\tat\t%s";
  private final static Queue<String> timeline = new ConcurrentLinkedQueue<>();

  /**
   * Car defines the basic action of a car as a thread. It first sleeps for a
   * random amount of time, and then arrives at the bridge ( invoke arriveBridge
   * function of the Bridge instance ). Once return from arriveBridge, it sleeps
   * again for a random amount of time within 500ms, and then leave the bridge
   * ( invoking leaveBridge function of hte Bridge instance).
   *
   * During runtime, each Car instance will record three event timestamp:
   *    1. arriveTime,
   *    2. enterTime,
   *    3. leaveTime.
   * Upon each event, Car thread will append a event timestamp to a queue. This
   * queue can be used to check if the execution satisfy the algorithm demand.
   *
   * Since the order of the event can only be check after all Car thread
   * finished, Car will print a dot (.) in standard output after leaving the
   * bridge indicating the thread will finish.
   *
   * After all threads joined (finished), test will output the events list as
   * well as the car list based on leaveTime order.
   */
  class Car implements Runnable, Comparable<Car> {
    int id;
    Bridge.Direction direction;
    long arriveTime;
    long enterTime;
    long leaveTime;

    Car(int id) {
      this.id = id;
      this.direction = Bridge.Direction.values()[new Random().nextInt(2)];
    }

    String arrive() {
      return String.format(EVENT_FORMAT, id, direction, "arrive", DATE_FORMAT.format(arriveTime));
    }

    String enter() {
      return String.format(EVENT_FORMAT, id, direction, "enter", DATE_FORMAT.format(enterTime));
    }

    String leave() {
      return String.format(EVENT_FORMAT, id, direction, "leave", DATE_FORMAT.format(leaveTime));
    }

    @Override
    public void run() {
      try {
        Thread.sleep(new Random().nextInt(ONBRIDGE * 3));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      arriveTime = System.currentTimeMillis();
      timeline.offer(this.arrive());
      bridge.arriveBridge(this.direction);
      enterTime = System.currentTimeMillis();
      timeline.offer(this.enter());
      try {
        Thread.sleep(new Random().nextInt(ONBRIDGE));
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        leaveTime = System.currentTimeMillis();
        timeline.offer(this.leave());
        bridge.leaveBridge(this.direction);
      }
      System.out.print('.');
    }

    @Override
    public int compareTo(Car o) {
      return Long.compare(this.leaveTime, o.leaveTime);
    }

    @Override
    public String toString() {
      return "Car{" +
        "id=" + id +
        ",\tdirection=" + direction +
        ",\tarriveTime=" + DATE_FORMAT.format(arriveTime) +
        ",\tenterTime=" + DATE_FORMAT.format(enterTime) +
        ",\tleaveTime=" + DATE_FORMAT.format(leaveTime) +
        '}';
    }
  }

  private final String bridgeType;
  private final Bridge bridge;

  public TestDriver(String bridgeType) {
    if (bridgeType.toLowerCase().startsWith("semaphore")) {
      this.bridge = new SemaphoreBridge();
      this.bridgeType = "semaphore";
    } else {
      this.bridge = new MonitorBridge();
      this.bridgeType = "monitor";
    }
  }

  private void printEventTimeline() {
    for (String event : timeline)
      System.out.println(event);
  }

  private void printLeavTimeline(Car[] cars) {
    System.out.println();
    System.out.println("Sorting all cars by leaving bridge time:");
    Arrays.sort(cars);
    for (Car car : cars)
      System.out.println(car);
  }

  public void test(int numOfCars) throws InterruptedException {
    System.out.printf("Initializing %d cars.\n", numOfCars);
    final Car[] cars = new Car[numOfCars];
    final Thread[] carThreads = new Thread[numOfCars];
    for (int i = 0; i < numOfCars; i++)
      cars[i] = new Car(i);
    for (int i = 0; i < numOfCars; i++)
      carThreads[i] = new Thread(cars[i]);

    System.out.printf("Starting experiment for %s bridge with %d cars.\n", bridgeType, numOfCars);
    for (Thread thread : carThreads)
      thread.start();
    for (Thread thread : carThreads)
      thread.join();
    System.out.println();
    System.out.println("Experiment finished.");
    System.out.println();
    System.out.println("Events timeline is as follow:");

    printEventTimeline();
    printLeavTimeline(cars);
  }
}
