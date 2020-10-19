package nz.ac.vuw.ecs.swen225.gp30.maze;

public class Bug extends Mob {
		public Bug(int x, int y) {
				super(x, y);
		}

		@Override
		public String getImageString() {
				return "mob_bug.png";
		}
}
