package nz.ac.vuw.ecs.swen225.gp30.maze;

/**
 * Bug class is a type of mob.
 * 
 * @author campliosca
 *
 */
public class Bug extends Mob {
	
		/**
		 * Constructs a bug with x position, y position and its path.
		 * 
		 * @param x the x position
		 * @param y the y position
		 * @param path the path of the bug
		 */
		public Bug(int x, int y, int[] path) {
				super(x, y, path);
		}

		@Override
		public String getImageString() {
				switch(dir) {
						case UP:
								return "mob_bug_up.png";
						case DOWN:
								return "mob_bug_down.png";
						case LEFT:
								return "mob_bug_left.png";
						case RIGHT:
								return "mob_bug_right.png";
				}
				return null;
		}
}
