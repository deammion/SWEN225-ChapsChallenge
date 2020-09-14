package nz.ac.vuw.ecs.swen225.gp30.maze;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

public class Maze {
    private Tile[][] grid;

    public Maze(int rows, int cols) {
        grid = new Tile[rows][cols];
    }

    public void setTileAt(int x, int y, Tile tile) {
        grid[x][y] = tile;
    }

    public Tile getTileAt(int x, int y) {
        return grid[x][y];
    }
}
