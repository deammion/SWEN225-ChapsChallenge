package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The ExitTile class represents the tile that will complete the level when moved to.
 * 
 * @author campliosca
 */
public class ExitTile extends Tile {

    /**
     * Constructs an ExitTile with x and y position.
     * @param x the x position of the tile
     * @param y the y position of the tile
     */
    public ExitTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return true;
    }

    @Override
    public void addChap(Chap chap) throws IllegalMoveException {
        checkNotNull(chap);
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
        return 'E';
    }

    @Override
    public boolean isMobAllowed() {
        return false;
    }

    @Override
    public String getImageString() {
        return "tile_exit.png";
    }
}
