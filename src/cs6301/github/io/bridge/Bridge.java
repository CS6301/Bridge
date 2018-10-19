package cs6301.github.io.bridge;

public interface Bridge {
  enum Direction {
    EAST_BOUND,
    WEST_BOUND,
  }

  void arriveBridge(Direction direction);

  void leaveBridge(Direction direction);

}
