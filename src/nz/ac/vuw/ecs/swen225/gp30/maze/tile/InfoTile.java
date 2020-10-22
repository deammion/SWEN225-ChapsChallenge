package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The InfoTile class represents a tile that if moved to will display level information.
 *
 * @author campliosca
 */
public class InfoTile extends Tile {

    /**
     * Constructs an InfoTile with x and y position.
     *
     * @param x the x position of the tile
     * @param y the y position of the tile
     */
    public InfoTile(int x, int y) {
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
        return 'i';
    }

    @Override
    public boolean isMobAllowed() {
        return false;
    }

    @Override
    public String getImageString() {
        return "tile_info.png";
    }

    @Override
    public String getSoundString() {
        return "move_chap.wav";
    }
}
