package nz.ac.vuw.ecs.swen225.gp30.maze;

import com.google.common.base.Preconditions;
import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.ExitTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.InfoTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The GameWorld is the main class in the maze package that has functionality for changing and checking the state of objects within the game
 * Provides functionality for setting and getting objects within the game.
 * 
 * @author campliosca
 *
 */
public class GameWorld {
    private Maze maze;
    private Chap chap;
    private String levelInfo;
    public MobManager mobMgr;

    private int timeLeft;

    /**
     * Constructs a GameWorld object with maze and chap.
     * 
     * @param maze the maze to set
     * @param chap the chap to set
     */
    public GameWorld(Maze maze, Chap chap) {
        this.maze = maze;
        this.chap = chap;
        setChapOnMaze(chap.getX(), chap.getY());
    }

    /**
     * Logic to move chap on the board. Returns true of the move was successful.
     * 
     * @param move the move to execute
     * @return true if the move was successful
     */
    public boolean moveChap(Move move) {
        int oldX = chap.getX();
        int oldY = chap.getY();
        Tile oldTile = maze.getTileAt(oldX, oldY);
        Tile newTile = maze.getNewTileFromMove(move, oldX, oldY);

        if(!newTile.canMoveTo(chap)) { return false; }
        else {
            oldTile.removeChap();
            setChapOnMaze(newTile.getX(), newTile.getY());
            chap.setDirection(move);
        }
        return true;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void decrementTimeLeft() {
        timeLeft--;
    }

    public void setTimeLeft(int time) {
        this.timeLeft = time;
    }

    /**
     * Advances mobs in the game.
     */
    public void advance() {
        mobMgr.advanceMobs();
    }

    /**
     * Sets chap on a tile in the maze using x and y position. 
     * 
     * @param x the x position to use
     * @param y the y position to use
     */
    public void setChapOnMaze(int x, int y) {
        Preconditions.checkNotNull(chap);
        Tile tile = maze.getTileAt(x, y);
        tile.addChap(chap);
        assert(tile.getX() == chap.getX() && tile.getY() == chap.getY());
    }

    public int getChipsLeft() {
        return ExitLockTile.CHIPS_REQUIRED - chap.getChipsCollected();
    }

    /**
     * Returns true of chap is on the info tile.
     * 
     * @return true if chap is on the info
     */
    public boolean isChapOnInfo() {
        Tile t = maze.getTileAt(chap.getX(), chap.getY());
        return t instanceof InfoTile;
    }

    /**
     * Returns true if chap is on the exit tile.
     * 
     * @return true if chap is on the exit tile
     */
    public boolean isChapOnExit() {
        Tile t = maze.getTileAt(chap.getX(), chap.getY());
        return t instanceof ExitTile;
    }
    
    /**
     * Returns true if chap is active.
     * 
     * @return true if chap is active
     */
    public boolean isChapActive() {
        return chap.isActive();
    }

    /**
     * Sets the chap in the game.
     * 
     * @param chap the chap to set
     */
    public void setChap(Chap chap) {
        this.chap = chap;
    }
    
    /**
     * Returns the chap in the game.
     * 
     * @return the chap in the game.
     */
    public Chap getChap() {
        return chap;
    }

    /**
     * Sets the maze in the game.
     * 
     * @param maze the maze to set
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Gets the maze in the game.
     * 
     * @return the maze in the game
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Sets the mob manager in the game.
     * 
     * @param m the mob manager to set
     */
    public void setMobManager(MobManager m) {
        this.mobMgr = m;
    }

    /**
     * Gets the mob manager in the game.
     * 
     * @return the mob manager in the game
     */
    public MobManager getMobManager() {
        return mobMgr;
    }

    /**
     * Sets the level information.
     * 
     * @param info the info to set.
     */
    public void setLevelInfo(String info) {
        this.levelInfo = info;
    }

    /**
     * Gets the level information.
     * 
     * @return the level info
     */
    public String getLevelInfo() {
        return levelInfo;
    }
}
