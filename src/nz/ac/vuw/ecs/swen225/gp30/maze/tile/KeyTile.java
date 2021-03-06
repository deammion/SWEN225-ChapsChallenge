package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The KeyTile class represents a tile that holds a key item that can be picked up.
 *
 * @author campliosca 300489876
 */
public class KeyTile extends Tile {
    private final Item key;
    private boolean collected = false;

    /**
     * Constructs a KeyTile with x and y position and the key item it contains
     *
     * @param x the x position of the tile
     * @param y the y position of the tile
     * @param key the key the tile contains
     */
    public KeyTile(int x, int y, Item key) {
        super(x, y);
        this.key = key;
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return true;
    }

    @Override
    public void addChap(Chap chap) throws IllegalMoveException {
        checkNotNull(chap);
        if(!collected) {
            collected = true;
            chap.addItemToInventory(key);
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
        return collected? '_' : key.getChar();
    }

    @Override
    public boolean isMobAllowed() {
        return collected;
    }

    @Override
    public String getImageString() {
        return collected? "tile_free.png" : "tile_" + key.toString().toLowerCase() + ".png";
    }

    @Override
    public String getSoundString() {
        return collected? "move_chap.wav" : "pick_key.wav";
    }
}
