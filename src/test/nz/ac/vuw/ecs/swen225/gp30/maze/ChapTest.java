package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.item.Key;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.FreeTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.WallTile;
import org.junit.jupiter.api.Test;

public class ChapTest {
    @Test
    public void testAddAndRemoveInventory() { }

    @Test
    public void testMoveUp() {
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = new Maze(2, 2);


        for(int x=0; x<2; x++) {
            for(int y=0; y<2; y++) {
                maze.setTileAt(x, y, new FreeTile(x, y));
            }
        }
        chap.setX(0);
        chap.setY(0);
        game.setChap(chap);
        game.setMaze(maze);

        game.move(Move.UP);
        assert(maze.getTileAt(0, 1).hasChap());
        assert(!maze.getTileAt(0, 0).hasChap());
        assert(chap.getX() == 0 && chap.getY() == 1);
    }
}
