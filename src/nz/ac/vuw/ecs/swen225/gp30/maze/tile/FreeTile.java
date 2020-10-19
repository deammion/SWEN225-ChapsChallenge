package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import static com.google.common.base.Preconditions.checkNotNull;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;

/**
 * The FreeTile class represents a tile that can be moved to freely without any requirements.
 */
public class FreeTile extends Tile {

    /**
     * Constructs a FreeTile with x and y position.
     * @param x the x position of the tile
     * @param y the y position of the tile
     */
    public FreeTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return true;
    }

    @Override
    public void addChap(Chap chap) throws IllegalMoveException {
        checkNotNull(chap);
        if(occupiedByMob()) { chap.setActive(false); }
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
        return hasChap() ? 'c' : '_';
    }

    @Override
    public boolean isMobAllowed() {
        return !occupiedByMob();
    }

    @Override
    public String getImageString() {
        return "tile_free.png";
    }
}
