package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

public abstract class Mob extends Actor {
		private final int[] path; // up left down right
		private int move = 0;

		public Mob(int x, int y, int[] path) {
				super(x, y);
				this.path = path;
		}

		public int getX() {
				return x;
		}

		public int getY() {
				return y;
		}

		public void setAt(int x, int y) {
				this.x = x;
				this.y = y;
		}

		public Move getNextMove() {
				return Move.getFromOrdinal(path[move++ % path.length]);
		}
}
