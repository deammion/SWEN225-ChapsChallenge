package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.IllegalMoveException;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;

import static com.google.common.base.Preconditions.checkArgument;

public class LockedDoorTile extends Tile {
    private final Item keyToUnlock;
    private boolean unlocked = false;

    public LockedDoorTile(int x, int y, Item keyToUnlock) {
        super(x, y);
        this.keyToUnlock = keyToUnlock;
    }

    @Override
    public boolean canMoveTo(Chap chap) {
        return unlocked || chap.hasItem(keyToUnlock);
    }

    @Override
    public void addChap(Chap chap) throws IllegalMoveException {
        checkArgument(chap != null, "chap cannot be null");
        if(!unlocked) {
            if(chap.useItem(keyToUnlock)) { unlocked = true; }
            else { throw new IllegalMoveException("door is locked: " + keyToUnlock.toString() + " required to add chap"); }
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
        return hasChap() ? 'c' : unlocked? '_' : 'D';
    }

    @Override
    public String getImageString() {
        return unlocked? "tile_free.png" :
        "tile_locked_door_" + keyToUnlock.toString().toLowerCase() + ".png";
    }
}
