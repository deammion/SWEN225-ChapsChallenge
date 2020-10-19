package nz.ac.vuw.ecs.swen225.gp30;

public enum Move {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Move getFromOrdinal(int ordinal) {
        return values()[ordinal];
    }
}
