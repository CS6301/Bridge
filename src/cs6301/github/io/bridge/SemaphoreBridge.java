package cs6301.github.io.bridge;

import java.util.concurrent.Semaphore;

/**
 * Bridge implementation using Semaphores.
 */
public class SemaphoreBridge implements Bridge {

  // semaphores used as mutex to protect count.
  private final Semaphore[] mutex = new Semaphore[]{
    new Semaphore(1),
    new Semaphore(1),
  };

  // semaphore to assure one car arrive at the bridge at a time.
  private final Semaphore service = new Semaphore(1);

  // semaphore to assure one direction can pass at a time.
  private final Semaphore resource = new Semaphore(1);

  // count of waiting cars of each direction.
  private final int[] count = new int[]{0, 0};

  @Override
  public void arriveBridge(Direction direction) {
    int dir = direction.ordinal();
    service.acquireUninterruptibly();
    try {
      mutex[dir].acquireUninterruptibly();
      try {
        count[dir]++;
        // If there are cars in other waiting or currently on the bridge,
        // and resource is unavailable, wait for the other direction to
        // release the resource.
        // Otherwise, enter bridge directly.
        if (count[dir] == 1)
          resource.acquireUninterruptibly();
      } finally {
        mutex[dir].release();
      }
    } finally {
      service.release();
    }
  }

  @Override
  public void leaveBridge(Direction direction) {
    int dir = direction.ordinal();
    mutex[dir].acquireUninterruptibly();
    try {
      count[dir]--;
      // If current car is the last of its direction to leave the bridge
      // for the moment, release resource immediately.
      if (count[dir] == 0)
        resource.release();
    } finally {
      mutex[dir].release();
    }
  }
}
