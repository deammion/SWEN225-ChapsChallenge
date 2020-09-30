package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameObject;

public abstract class Tile extends GameObject {
    private final int x, y;
    protected Chap chap = null;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean canMoveTo(Chap chap);

    public abstract boolean addChap(Chap chap);

    public abstract void removeChap();

    public abstract boolean hasChap();

    public abstract char getChar();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract String getImageString();
}
