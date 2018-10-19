package cs6301.github.io.bridge;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Bridge implementation using Monitors.
 */
public class MonitorBridge implements Bridge {

  // mutex here is used to create Condition and ensure exclusive access
  // to the two functions (otherwise `synchronize` keyword is needed).
  private final Lock mutex = new ReentrantLock();

  // active represent how many cars are currently on bridge for each direction.
  private final int[] active = {0, 0};

  // waiting represent how many cars are currently waiting to enter bridge.
  // for each direction.
  private final int[] waiting = {0, 0};

  // canPass indicate the waiting condition for each direction.
  private final Condition[] canPass = {
    mutex.newCondition(),
    mutex.newCondition(),
  };

  /**
   * If there are cars in other waiting or currently on the bridge, wait
   * for the other direction sending signal.
   * @param direction direction when arriving bridge.
   */
  @Override
  public void arriveBridge(Direction direction) {
    mutex.lock();
    try {
      int dir = direction.ordinal();
      int other = 1 - dir;
      if (active[other] + waiting[other] > 0) {
        waiting[dir]++;
        canPass[dir].awaitUninterruptibly();
        waiting[dir]--;
      }
      active[dir]++;
    } finally {
      mutex.unlock();
    }
  }

  /**
   * Leave bridge and notify the other end if there are cars waiting.
   * @param direction direction when leaving bridge.
   */
  @Override
  public void leaveBridge(Direction direction) {
    mutex.lock();
    try {
      int dir = direction.ordinal();
      int other = 1 - dir;
      active[dir]--;

      // If current car is the last of its direction to leave the bridge
      // for the moment, and there are cars waiting on the other side,
      // prepare to signal the other end to pass.
      if (active[dir] == 0 && waiting[other] > 0) {
        // if current car's direction also has cars waiting, then only
        // signal one car of the other end to enter.
        if (waiting[dir] > 0)
          canPass[other].signal();
        // otherwise, signal all car on the other direction to enter.
        else
          canPass[other].signalAll();
      }
    } finally {
      mutex.unlock();
    }
  }
}
