package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;

/**
 * Actor class abstracts characters in the game that move (e.g. chap or mob).
 * 
 * @author campliosca
 *
 */
public abstract class Actor implements GameObject {
		protected int x, y;
		protected Move dir;

		/**
		 * Constructs an Actor with x and y position.
		 * 
		 * @param x the x position
		 * @param y the y position
		 */
		public Actor(int x, int y) {
				this.x = x;
				this.y = y;
				dir = Move.DOWN;
		}

		/**
		 * Returns the x position of the actor.
		 * 
		 * @return the x position
		 */
		public int getX() {
				return x;
		}

		/**
		 * Returns the y position of the actor.
		 * 
		 * @return the y position of the actor
		 */
		public int getY() {
				return y;
		}

		/**
		 * Sets the actor at an x and y position.
		 * 
		 * @param x the x position to set
		 * @param y the y position to set
		 */
		public void setAt(int x, int y) {
				this.x = x;
				this.y = y;
		}

		/**
		 * Sets the current direction of chap.
		 *
		 * @param dir the direction to set
		 */
		public void setDirection(Move dir) {
				this.dir = dir;
		}
}
