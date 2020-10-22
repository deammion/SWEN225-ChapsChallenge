package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameObject;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;

/**
 * The Tile class abstracts tiles within the maze.
 *
 * Provides access to the x and y position of the tile.
 * Provides functionality which should be implemented by subclasses:
 * - check validity of movement.
 * - check whether the tile contains an actor.
 * - game logic for adding and removing an actor.
 * - image name for display.
 *
 * @author campliosca
 */
public abstract class Tile implements GameObject {
    private final int x, y;
    //private TileType type;
    protected Chap chap = null;
    private boolean hasMob;

    /**
     * Constructs a new Tile that stores the x and y position.
     * @param x the x position of the tile
     * @param y the y position of the tile
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns true if the chap is able to move to the tile.
     * @param chap chap being moved
     * @return true only if the chap is allowed to move to the tile
     */
    public abstract boolean canMoveTo(Chap chap);

    /**
     * Tries to adds the chap to the tile if allowed and executes relevant game logic.
     * Throws IllegalMoveException if move is not possible.
     * @param chap chap being moved
     * @throws IllegalMoveException if the move is not possible
     */
    public abstract void addChap(Chap chap) throws IllegalMoveException;

    /**
     * Removes chap from the tile.
     */
    public abstract void removeChap();

    /**
     * Returns true if the tile contains the chap.
     * @return true only if the tile contains the chap
     */
    public abstract boolean hasChap();

    /**
     * Returns character representation of the tile.
     * @return character representation of the tile
     */
    public abstract char getChar();

    /**
     * Returns the x position of the tile.
     * @return x position of the tile
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y position of the tile.
     * @return y position of the tile
     */
    public int getY() {
        return y;
    }

    /**
     * Returns true if mob is allowed on the tile.
     * @return true if mob is allowed on the tile.
     */
    public abstract boolean isMobAllowed();

    /**
     * Returns true if a mob occupies the tile.
     * @return true if mob occupies the tile.
     */
    public boolean occupiedByMob() {
        return hasMob;
    }

    /**
     * Adds mob to the tile, sets chap inactive if the tile contains chap.
     */
    public void addMob() {
        if(hasChap()) { chap.setActive(false); }
        hasMob = true;
    }

    /**
     * Removes mob from the tile.
     */
    public void removeMob() {
        hasMob = false;
    }


    /**
     * Returns string of this tiles image asset name.
     *
     * @return string of the image asset name
     */
    public abstract String getImageString();

    /**
     * Returns the string of this tiles sound asset name.
     *
     * @return string of the sound asset name
     */
    public abstract String getSoundString();
}
