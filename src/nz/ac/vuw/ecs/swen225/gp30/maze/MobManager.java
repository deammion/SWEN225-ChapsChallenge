package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * MobManager stores all of the mobs in the maze and manages their movement.
 * 
 * @author campliosca
 *
 */
public class MobManager {
		private List<Mob> mobs;
		private Maze maze;

		/**
		 * Constructs a MobManager with reference to the maze.
		 * 
		 * @param maze the of the game for reference
		 */
		public MobManager(Maze maze) {
				this.maze = maze;
				mobs = new ArrayList<>();
		}

		/**
		 * Adds a mob to the list of mobs in the manager.
		 * 
		 * @param mob the mob to add
		 */
		public void addMob(Mob mob) {
				mobs.add(mob);
		}

		/**
		 * Gets the lsit of mobs in the manager.
		 * 
		 * @return the list of mobs
		 */
		public List<Mob> getMobs() {
				return mobs;
		}

		/**
		 * Sets the list of mobs in the manager.
		 * 
		 * @param mobs the list of mobs to set
		 */
		public void setMobs(List<Mob> mobs) {
				this.mobs = mobs;
		}

		/**
		 * Moves each mob in the manager on the maze according to their next move.
		 */
		public void advanceMobs() {
				for(Mob m : mobs) {
						Move move = m.getNextMove();
						Tile prevTile = maze.getTileAt(m.getX(), m.getY());
						Tile newTile = maze.getNewTileFromMove(move, m.getX(), m.getY());
						if(!newTile.isMobAllowed()) { return; }
						else {
								prevTile.removeMob();
								newTile.addMob();
								m.setAt(newTile.getX(), newTile.getY());
								m.setDirection(move);
						}
				}
		}
}
