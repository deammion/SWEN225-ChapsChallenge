package nz.ac.vuw.ecs.swen225.gp30.maze;
import static com.google.common.base.Preconditions.checkArgument;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

public class Maze {
    private Tile[][] grid;

    public Maze(int rows, int cols) {
        grid = new Tile[rows][cols];
    }

    public void setTileAt(int x, int y, Tile tile) {
        checkArgument(x >= 0 && x < grid.length, "x must be within bounds: 0 - " + grid.length);
        checkArgument(y >= 0 && y < grid[0].length, "y must be within bounds: 0 - " + grid[0].length);
        checkArgument(tile != null, "tile must not be null");
        grid[x][y] = tile;
    }

    public Tile getTileAt(int x, int y) {
        checkArgument(x >= 0 && x < grid.length, "x must be within bounds: 0 - " + grid.length);
        checkArgument(y >= 0 && y < grid[0].length, "y must be within bounds: 0 - " + grid[0].length);
        return grid[x][y];
    }
}
