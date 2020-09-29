package nz.ac.vuw.ecs.swen225.gp30.maze.item;

import nz.ac.vuw.ecs.swen225.gp30.maze.GameObject;

public enum Item {
    KEY_BLUE(ItemType.KEY),
    KEY_GREEN(ItemType.KEY),
    KEY_RED(ItemType.KEY),
    KEY_YELLOW(ItemType.KEY);

    public final ItemType type;

    private Item(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }
}
