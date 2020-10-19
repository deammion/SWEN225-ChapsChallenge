package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

public abstract class Actor implements GameObject {
		protected int x, y;

		public Actor(int x, int y) {
				this.x = x;
				this.y = y;
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
}
