package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.ExitTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.InfoTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

/**
 * The GameWorld is the main class in the maze package that has functionality for changing and checking the state of objects within the game
 * Provides functionality for setting and getting objects within the game.
 * 
 * @author campliosca 300489876
 */
public class GameWorld {
    private Maze maze;
    private Chap chap;
    private String levelInfo;
    private MobManager mobMgr;
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
        Tile t = maze.getTileAt(chap.getX(), chap.getY());
        t.addChap(chap);
    }

    /**
     * Logic to move chap on the board. Returns true of the move was successful.
     * 
     * @param move the move to execute
     */
    public void moveChap(Move move) {
        int oldX = chap.getX();
        int oldY = chap.getY();
        Tile oldTile = maze.getTileAt(oldX, oldY);
        Tile newTile = maze.getNewTileFromMove(move, oldX, oldY);

        if(newTile.canMoveTo(chap)) {
            oldTile.removeChap();
            newTile.addChap(chap);
            chap.setDirection(move);
        }
    }

    /**
     * Returns the time left in the game.
     *
     * @return time left in the game
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * Decrements the time left in the game by 1.
     */
    public void decrementTimeLeft() {
        timeLeft--;
    }

    /**
     * Sets the time left in the game.
     *
     * @param time the time left to set.
     */
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
     * Returns the amount of chips left for chap to collect.
     *
     * @return chips left for chap to collect
     */
    public int getChipsLeft() {
        return ExitLockTile.getChipsRequired() - chap.getChipsCollected();
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
