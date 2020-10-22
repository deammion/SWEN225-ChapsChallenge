package nz.ac.vuw.ecs.swen225.gp30.maze;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Maze class stores the grid of tiles in the game. Provides functionality for getting and setting tiles at a position.
 * 
 * @author campliosca 300489876
 */
public class Maze {
    private final Tile[][] grid;

    /**
     * Constructs a Maze object with number of rows and columns.
     * 
     * @param cols the columns of the maze.
     * @param rows the rows of the maze.
     */
    public Maze(int cols, int rows) {
        checkArgument(cols > 0 && rows > 0, "invalid maze size");
        grid = new Tile[cols][rows];
    }

    /**
     * Sets a tile at an x and y position.
     * 
     * @param x the x position to set the tile at
     * @param y the y position to set the tile at
     * @param tile the tile to set
     */
    public void setTileAt(int x, int y, Tile tile) {
        checkArgument(x >= 0 && x < grid.length, "x (" + x + ") must be within bounds: 0 - " + (grid.length-1));
        checkArgument(y >= 0 && y < grid[0].length, "y (" + y + ") must be within bounds: 0 - " + (grid[0].length-1));
        checkArgument(tile != null, "tile must not be null");
        grid[x][y] = tile;
    }

    /**
     * Returns Tile at the x and y position.
     * 
     * @param x the x position of the tile
     * @param y the y position of the tile
     * @return the tile at the x and y position
     */
    public Tile getTileAt(int x, int y) {
        checkArgument(x >= 0 && x < grid.length, "x (" + x + ") must be within bounds: 0 - " + (grid.length-1));
        checkArgument(y >= 0 && y < grid[0].length, "y (" + y + ") must be within bounds: 0 - " + (grid[0].length-1));
        return grid[x][y];
    }

    /**
     * Gets the new tile from a move.
     * 
     * @param move the move to get new tile from
     * @param oldX the old x position for reference
     * @param oldY the old y position for reference
     * @return the tile from move
     */
    public Tile getNewTileFromMove(Move move, int oldX, int oldY) {
        checkNotNull(move);
        switch(move) {
            case UP: return getTileAt(oldX, oldY-1);
            case DOWN: return getTileAt(oldX, oldY+1);
            case LEFT: return getTileAt(oldX-1, oldY);
            case RIGHT: return getTileAt(oldX+1, oldY);
        }
        return null;
    }

    /**
     * Return the tiles in the maze as a stream. 
     * 
     * @return a stream of tiles in the maze
     */
    public Stream<Tile> getTiles() {
        return Arrays.stream(grid).flatMap(Stream::of);
    }

    /**
     * Returns the number of rows in the maze.
     *
     * @return rows in the maze
     */
    public int getRows() {
        return grid[0].length;
    }

    /**
     * Return the number of cols in the maze.
     *
     * @return cols in the maze
     */
    public int getCols() {
        return grid.length;
    }

    /**
     * Returns string representation of maze with or without chap.
     *
     * @param withChap whether chap should be printed
     * @return the string representation of maze
     */
    public String toString(boolean withChap) {
        StringBuilder gridString = new StringBuilder();
        for(int y=0; y<grid[0].length; y++) {
            for(int x=0; x<grid.length; x++) {
                if(x == 0) {
                    if(y != 0) { gridString.append("\n"); }
                    gridString.append("|");
                }
                Tile t = getTileAt(x, y);
                if(t.hasChap() && withChap) { gridString.append("c"); }
                else { gridString.append(t.getChar()); }
                gridString.append("|");
            }
        }
        return gridString.toString();
    }
}
