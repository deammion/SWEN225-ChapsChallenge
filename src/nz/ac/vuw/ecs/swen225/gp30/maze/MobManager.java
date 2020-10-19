package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class MobManager {
		private List<Mob> mobs;
		private Maze maze;

		public MobManager(Maze maze) {
				this.maze = maze;
				mobs = new ArrayList<>();
		}

		public void addMob(Mob mob) {
				mobs.add(mob);
		}

		public List<Mob> getMobs() {
				return mobs;
		}

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
								//m.setDirection(move);
						}
				}
		}
}
