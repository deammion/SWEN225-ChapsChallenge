package nz.ac.vuw.ecs.swen225.gp30.application;

/**
 * The movement keys for Chap to move around the maze.
 *
 * @author jakeh.
 */
public class Key {
		public boolean pressed = false;
		public final static Key up = new Key();
		public final static Key down = new Key();
		public final static Key left = new Key();
		public final static Key right = new Key();
}
