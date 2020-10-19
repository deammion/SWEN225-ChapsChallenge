package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import static com.google.common.base.Preconditions.checkNotNull;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;

/**
 * The TreasureTile class represents a tile that contains a treasure which can be collected if chap moves onto the tile.
 *
 * @author campliosca
 */
public class TreasureTile extends Tile {
    private boolean collected = false;

    /**
     * Constructs a TreasureTile with x and y position.
     * @param x the x position of the tile
     * @param y the y position of the tile
     */
    public TreasureTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return true;
    }

    @Override
    public void addChap(Chap chap) throws IllegalMoveException {
        checkNotNull(chap);
        if(!collected) {
            chap.collectChip();
            collected = true;
        }
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
        return hasChap() ? 'c' : collected? '_' : '*';
    }

    @Override
    public boolean isMobAllowed() {
        return false;
    }

    @Override
    public String getImageString() {
        return collected? "tile_free.png" : "tile_treasure.png";
    }
}
