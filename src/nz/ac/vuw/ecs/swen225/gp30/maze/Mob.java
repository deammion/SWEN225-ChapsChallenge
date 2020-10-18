package nz.ac.vuw.ecs.swen225.gp30.maze;

public class Mob {
		private int x, y;

		public Mob(int x, int y) {
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
