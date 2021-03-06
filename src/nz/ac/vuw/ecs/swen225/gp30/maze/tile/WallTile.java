package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import static com.google.common.base.Preconditions.checkNotNull;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;

/**
 * The WallTile class represents a tile which cannot be moved to by chap.
 *
 * @author campliosca 300489876
 */
public class WallTile extends Tile {

    /**
     * Constructs a WallTile with x and y position.
     * @param x the x position of the tile
     * @param y the y position of the tile
     */
    public WallTile(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return false;
    }

    @Override
    public void addChap(Chap chap) throws IllegalMoveException {
        checkNotNull(chap);
        throw new IllegalMoveException("cannot move onto a wall tile");
    }

    @Override
    public void removeChap() {
        chap = null;
    }

    @Override
    public boolean hasChap() {
        return false;
    }

    @Override
    public char getChar() {
        return '#';
    }

    @Override
    public boolean isMobAllowed() {
        return false;
    }

    @Override
    public String getImageString() {
        return "tile_wall.png";
    }

    @Override
    public String getSoundString() {
        return "move_chap.wav";
    }
}
