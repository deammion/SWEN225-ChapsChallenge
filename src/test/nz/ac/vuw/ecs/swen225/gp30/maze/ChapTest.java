package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.item.Key;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.ExitLockTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.FreeTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.Tile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.WallTile;
import org.junit.jupiter.api.Test;

public class ChapTest {

    @Test
    public void validMoveTest_01() { // move chap onto wall tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new FreeTile(1, 0));

        game.setChap(chap);
        game.setMaze(maze);
        game.setChapOnMaze(0,0);

        game.moveChap(Move.RIGHT);
        assert(maze.getTileAt(1, 0).hasChap());
        assert(!maze.getTileAt(0, 0).hasChap());
    }

    @Test
    public void invalidMoveTest_01() { // move chap onto wall tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new WallTile(1, 0));

        game.setChap(chap);
        game.setMaze(maze);
        game.setChapOnMaze(0,0);

        game.moveChap(Move.RIGHT);
        assert(!maze.getTileAt(1, 0).hasChap());
        assert(maze.getTileAt(0, 0).hasChap());
    }

    public void validChipCollectionTest_01() {
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new ExitLockTile(1, 0));

        game.setChap(chap);
        game.setMaze(maze);
        game.setChapOnMaze(0,0);
        game.moveChap(Move.RIGHT);

        assert(chap.getChipsCollected() == 1);
        assert(maze.getTileAt(1, 0) instanceof FreeTile);
    }

    public Maze makeTestMaze(Tile testTile) {
        Maze maze = new Maze(2, 1);
        maze.setTileAt(0,0, new FreeTile(0, 0));
        maze.setTileAt(1,0, testTile);
        return maze;
    }
}
