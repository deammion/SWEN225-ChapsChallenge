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
				return "mob_bug.png";
		}
}
