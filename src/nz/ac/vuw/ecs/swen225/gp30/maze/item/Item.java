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
    KEY_BLUE(true, 'b'),
    /**
     * A green key
     */
    KEY_GREEN(false, 'g'),
    /**
     * A red key
     */
    KEY_RED(true, 'r'),
    /**
     * A yellow key
     */
    KEY_YELLOW(true, 'y');

    private final boolean consumable;
    private final char itemChar;

    Item(boolean consumable, char itemChar) {
        this.consumable = consumable;
        this.itemChar = itemChar;
    }

    /**
     * Returns true if the item is consumable.
     * @return true if the item is consumable
     */
    public boolean isConsumable() {
        return consumable;
    }

    /**
     * Returns the char representation of item.
     *
     * @return char representation.
     */
    public char getChar() {
        return itemChar;
    }
}
