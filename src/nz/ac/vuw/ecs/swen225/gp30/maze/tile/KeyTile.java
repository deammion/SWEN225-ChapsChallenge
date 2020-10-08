package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;

import static com.google.common.base.Preconditions.checkArgument;

public class KeyTile extends Tile {
    private final Item key;
    private boolean collected = false;

    public KeyTile(int x, int y, Item key) {
        super(x, y);
        this.key = key;
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return true;
    }

    @Override
    public boolean addChap(Chap chap) {
        checkArgument(chap != null, "Chap cannot be null");
        if(!collected) {
            collected = true;
            chap.addItemToInventory(key);
        }
        chap.setAt(getX(), getY());
        this.chap = chap;
        return true;
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
        return hasChap()? 'c' : collected? '_' : '%';
    }

    @Override
    public String getImageString() {
        return collected? "tile_free.png" : "tile_" + key.toString().toLowerCase() + ".png";
    }
}
