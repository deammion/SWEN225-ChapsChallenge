package nz.ac.vuw.ecs.swen225.gp30.maze.item;

/**
 * Item class stores objects that can be picked up by chap such as keys.
 * 
 * @author campliosca
 * 
 */
public enum Item {
    /**
     * A blue key
     */
    KEY_BLUE(true),
    /**
     * A green key
     */
    KEY_GREEN(false),
    /**
     * A red key
     */
    KEY_RED(true),
    /**
     * A yellow key
     */
    KEY_YELLOW(true);

    private final boolean consumable;

    Item(boolean consumable) {
        this.consumable = consumable;
    }

    /**
     * Returns true if the item is consumable.
     * @return true if the item is consumable
     */
    public boolean isConsumable() {
        return consumable;
    }
}
