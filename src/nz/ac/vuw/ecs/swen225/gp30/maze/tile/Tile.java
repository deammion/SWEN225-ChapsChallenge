package nz.ac.vuw.ecs.swen225.gp30.maze.tile;

public abstract class Tile {
    private final int x, y;
    // private Chap chapToken = null

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean isInteractableTile();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
