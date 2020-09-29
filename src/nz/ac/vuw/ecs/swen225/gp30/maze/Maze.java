package nz.ac.vuw.ecs.swen225.gp30.maze;
import static com.google.common.base.Preconditions.checkArgument;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.util.Arrays;

public class Maze {
    private Tile[][] grid;

    public Maze(int cols, int rows) {
        grid = new Tile[cols][rows];
    }

    public void setTileAt(int x, int y, Tile tile) {
        checkArgument(x >= 0 && x < grid.length, "x (" + x + ") must be within bounds: 0 - " + (grid.length-1));
        checkArgument(y >= 0 && y < grid[0].length, "y (" + y + ") must be within bounds: 0 - " + (grid[0].length-1));
        checkArgument(tile != null, "tile must not be null");
        grid[x][y] = tile;
    }

    public Tile getTileAt(int x, int y) {
        checkArgument(x >= 0 && x < grid.length, "x (" + x + ") must be within bounds: 0 - " + (grid.length-1));
        checkArgument(y >= 0 && y < grid[0].length, "y (" + y + ") must be within bounds: 0 - " + (grid[0].length-1));
        return grid[x][y];
    }

    @Override
    public String toString() {
        StringBuilder gridString = new StringBuilder();
        for(int x=0; x<grid.length; x++) {
            for(int y=0; y<grid[0].length; y++) {
                if(x == 0) { gridString.append("|"); }
                Tile t = getTileAt(x, y);
                gridString.append(t.getChar());
                gridString.append("|");
            }
        }
        return gridString.toString();
    }
}
