package nz.ac.vuw.ecs.swen225.gp30.maze;

public class Bug extends Mob {
		public Bug(int x, int y, int[] path) {
				super(x, y, path);
		}

		@Override
		public String getImageString() {
				return "mob_bug.png";
		}
}