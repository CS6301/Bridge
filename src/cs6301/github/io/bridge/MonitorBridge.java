package cs6301.github.io.bridge;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorBridge implements Bridge {
  private final Lock mutex = new ReentrantLock();
  private final int[] active = {0, 0};
  private final int[] waiting = {0, 0};
  private final Condition[] canPassCondition = {
    mutex.newCondition(),
    mutex.newCondition(),
  };
  private final boolean[] canPass = {false, false};

  @Override
  public void arriveBridge(Direction direction) {
    mutex.lock();
    try {
      int dir = direction.ordinal();
      int other = 1 - dir;
      if (active[other] + waiting[other] > 0) {
        waiting[dir]++;
        canPassCondition[dir].awaitUninterruptibly();
        waiting[dir]--;
      }
      active[dir]++;
    } finally {
      mutex.unlock();
    }
  }

  @Override
  public void leaveBridge(Direction direction) {
    mutex.lock();
    try {
      int dir = direction.ordinal();
      int other = 1 - dir;
      active[dir]--;
      if (active[dir] == 0 && waiting[other] > 0) {
        if (waiting[dir] > 0)
          canPassCondition[other].signal();
        else
          canPassCondition[other].signalAll();
      }
    } finally {
      mutex.unlock();
    }
  }
}
