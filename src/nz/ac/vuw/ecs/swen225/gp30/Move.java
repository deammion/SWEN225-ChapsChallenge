package nz.ac.vuw.ecs.swen225.gp30;

/**
 * The Move enum holds values that represent how actors can move.
 * 
 * @author campliosca
 */
public enum Move {
    /**
     * Move UP
     */
    UP,
    /**
     * Move Down
     */
    DOWN,
    /**
     * Move Left
     */
    LEFT,
    /**
     * Move Right
     */
    RIGHT;

	/**
	 * Returns a Move from its ordinal value.
	 * 
	 * @param ordinal the ordinal value
	 * @return the move from ordinal
	 */
    public static Move getFromOrdinal(int ordinal) {
        return values()[ordinal];
    }
}
