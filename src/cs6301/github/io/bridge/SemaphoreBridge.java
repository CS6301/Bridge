package cs6301.github.io.bridge;

import java.util.concurrent.Semaphore;

public class SemaphoreBridge implements Bridge {

  private final Semaphore[] mutex = new Semaphore[]{
    new Semaphore(1),
    new Semaphore(1),
  };
  private final Semaphore service = new Semaphore(1);
  private final Semaphore resource = new Semaphore(1);
  private final int[] count = new int[]{0, 0};

  @Override
  public void arriveBridge(Direction direction) {
    int dir = direction.ordinal();
    service.acquireUninterruptibly();
    mutex[dir].acquireUninterruptibly();
    count[dir]++;
    if (count[dir] == 1)
      resource.acquireUninterruptibly();
    mutex[dir].release();
    service.release();
  }

  @Override
  public void leaveBridge(Direction direction) {
    int dir = direction.ordinal();
    mutex[dir].acquireUninterruptibly();
    count[dir]--;
    if (count[dir] == 0)
      resource.release();
    mutex[dir].release();
  }
}
