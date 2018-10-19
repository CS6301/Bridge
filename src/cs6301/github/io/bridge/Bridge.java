package cs6301.github.io.bridge;

/**
 * A bridge has two functions `arriveBridge` and `leaveBridge` that take
 * direction as an argument.
 * The interface also define the `Direction` enum to represent two directions.
 */
public interface Bridge {
  enum Direction {
    EAST_BOUND,
    WEST_BOUND,
  }

  void arriveBridge(Direction direction);

  void leaveBridge(Direction direction);

}
