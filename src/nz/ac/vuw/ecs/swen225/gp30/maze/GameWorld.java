package nz.ac.vuw.ecs.swen225.gp30.maze;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.ExitTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.InfoTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

public class GameWorld {
    private Maze maze;
    private Chap chap;

    public GameWorld(Maze maze, Chap chap) {
        this.maze = maze;
        this.chap = chap;
        setChapOnMaze(chap.getX(), chap.getY());
    }

    public Tile getNewTile(Move move, int oldX, int oldY) {
        switch(move) {
            case UP: return maze.getTileAt(oldX, oldY+1);
            case DOWN: return maze.getTileAt(oldX, oldY-1);
            case LEFT: return maze.getTileAt(oldX-1, oldY);
            case RIGHT: return maze.getTileAt(oldX+1, oldY);
        }
        return null;
    }

    public boolean moveChap(Move move) {
        int oldX = chap.getX();
        int oldY = chap.getY();
        Tile oldTile = maze.getTileAt(oldX, oldY);
        Tile newTile = getNewTile(move, oldX, oldY);

        if(!newTile.canMoveTo(chap)) { return false; }
        else {
            oldTile.removeChap();
            setChapOnMaze(newTile.getX(), newTile.getY());
        }
        return true;
    }

    public void setChapOnMaze(int x, int y) {
        Preconditions.checkArgument(chap != null, "chap cannot be null");
        Tile tile = maze.getTileAt(x, y);
        tile.addChap(chap);
        assert(tile.getX() == chap.getX() && tile.getY() == chap.getY());
    }

    public boolean isChapOnInfo() {
        Tile t = maze.getTileAt(chap.getX(), chap.getY());
        return t instanceof InfoTile;
    }

    public boolean isChapOnExit() {
        Tile t = maze.getTileAt(chap.getX(), chap.getY());
        return t instanceof ExitTile;
    }

    public boolean isChapActive() {
        return false;
    }

    public void setChap(Chap chap) {
        this.chap = chap;
    }

    public Chap getChap() {
        return chap;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public Maze getMaze() {
        return maze;
    }
}
