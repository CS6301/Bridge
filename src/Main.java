import cs6301.github.io.test.TestDriver;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    String bridgeType = "semaphore";
    if (args.length >= 1)
      bridgeType = args[0];

    int numOfCars = 50;
    if (args.length >= 2)
      numOfCars = Integer.parseInt(args[1]);

    TestDriver testDriver = new TestDriver(bridgeType);
    testDriver.test(numOfCars);
  }
}
