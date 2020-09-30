package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;

import static com.google.common.base.Preconditions.checkArgument;

public class LockedDoorTile extends Tile {
    private Item keyToUnlock;
    boolean unlocked = false;

    public LockedDoorTile(int x, int y, Item keyToUnlock) {
        super(x, y);
        this.keyToUnlock = keyToUnlock;
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return unlocked || chap.hasItem(keyToUnlock);
    }

    @Override
    public boolean addChap(Chap chap) {
        checkArgument(chap != null, "Chap cannot be null");
        if(chap.consumeItem(keyToUnlock)) { unlocked = true; }
        if(unlocked) {
            chap.setAt(getX(), getY());
            this.chap = chap;
            return true;
        }
        return false;
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
        return hasChap() ? 'c' : unlocked? '_' : 'D';
    }

    @Override
    public String getImageString() {
        return unlocked? "tile_free.png" :
        "tile_locked_door_" + keyToUnlock.toString().toLowerCase() + ".png";
    }
}
