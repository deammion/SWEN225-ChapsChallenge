package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import java.util.Arrays;

/**
 * Mob class is a type of actor that moves around the maze on its own from a specified path.
 * 
 * @author campliosca 300489876
 */
public abstract class Mob extends Actor {
		private final int[] path;
		private int move = 0;

		/**
		 * Constructs a Mob with x position, y position and path.
		 * 
		 * @param x the x position
		 * @param y the y position
		 * @param path the path of the mob
		 */
		public Mob(int x, int y, int[] path) {
				super(x, y);
				this.path = path.clone();
		}

		/**
		 * Returns the next move of the mob from its path.
		 * 
		 * @return the next move of the mob
		 */
		public Move getNextMove() {
				return Move.getFromOrdinal(path[move++ % path.length]);
		}

		/**
		 * Returns the path of the mob.
		 *
		 * @return the path of the mob
		 */
		public int[] getPath() {
				return Arrays.copyOf(path, path.length);
		}
}
