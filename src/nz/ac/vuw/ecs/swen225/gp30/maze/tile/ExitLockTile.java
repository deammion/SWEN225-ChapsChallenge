package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The ExitLockTile class represents the tile that is placed before the Exit tile.
 * - It must be unlocked by collecting a certain amount of chips for chap to be able to move here.
 *
 * @author campliosca
 */
public class ExitLockTile extends Tile {
    public static int CHIPS_REQUIRED;
    private boolean unlocked = false;

    /**
     * Constructs an ExitLockTile object.
     * - stores x and y position and amount of chips required to unlock
     *
     * @param x the x position of the tile
     * @param y the y position of the tile
     * @param chipsRequired the number of chips required to unlock the tile
     */
    public ExitLockTile(int x, int y, int chipsRequired) {
        super(x, y);
        this.CHIPS_REQUIRED = chipsRequired;
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return unlocked || chap.getChipsCollected() == CHIPS_REQUIRED;
    }

    @Override
    public void addChap(Chap chap) throws IllegalMoveException {
        checkNotNull(chap);
        if(!canMoveTo(chap)) { throw new IllegalMoveException("required chips does not match chips collected"); }
        else { unlocked = true; }
        chap.setAt(getX(), getY());
        this.chap = chap;
    }

    @Override
    public void removeChap() {
        chap = null;
    }

    @Override
    public boolean hasChap() {
        return chap != null;
    }

    @Override
    public char getChar() {
        return unlocked? '_' : 'l';
    }

    @Override
    public boolean isMobAllowed() {
        return unlocked;
    }

    @Override
    public String getImageString() {
        return unlocked? "tile_free.png" : "tile_exit_lock.png";
    }

    @Override
    public String getSoundString() {
        return unlocked? "move_chap.wav" : "open_exit.wav";
    }
}
