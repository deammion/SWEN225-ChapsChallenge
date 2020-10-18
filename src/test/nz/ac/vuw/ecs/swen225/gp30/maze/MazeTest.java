package test.nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.GameWorld;
import nz.ac.vuw.ecs.swen225.gp30.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeTest {

    /**
     * Tests trying to move chap onto a FreeTile.
     */
    @Test
    public void validMoveTest_01() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new FreeTile(1, 0));
        GameWorld game = new GameWorld(maze, chap);


        String expected = "|_|c|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    /**
     * Tests trying to move chap onto an InfoTile.
     */
    @Test
    public void validMoveTest_02() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new InfoTile(1, 0));
        GameWorld game = new GameWorld(maze, chap);

        String[] expected = { "|c|i|", "|_|c|", "|c|i|" };
        assertEquals(expected[0], maze.toString());
        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], maze.toString());
        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    /**
     * Tests trying to move chap onto an ExitTile.
     */
    @Test
    public void validMoveTest_03() {

        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new ExitTile(1, 0));
        GameWorld game = new GameWorld(maze, chap);

        String[] expected = { "|_|c|", "|c|O|" };

        game.moveChap(Move.RIGHT);
        assertEquals(expected[0], maze.toString());
        game.moveChap(Move.LEFT);
        assertEquals(expected[1], maze.toString());
    }

    /**
     * Tests trying to move chap onto a LockedDoorTile with the correct key in inventory.
     */
    @Test
    public void validMoveTest_04() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new LockedDoorTile(1, 0, Item.KEY_BLUE));
        GameWorld game = new GameWorld(maze, chap);
        chap.addItemToInventory(Item.KEY_BLUE);

        String[] expected = { "|c|D|", "|_|c|", "|c|_|" };
        assertEquals(expected[0], maze.toString());

        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], maze.toString());

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    /**
     * Tests trying to move chap onto a KeyTile and picking up key.
     */
    @Test
    public void validMoveTest_05() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new KeyTile(1, 0, Item.KEY_BLUE));
        GameWorld game = new GameWorld(maze, chap);

        String[] expected = { "|c|%|", "|_|c|", "|c|_|" };
        assertEquals(expected[0], maze.toString());

        game.moveChap(Move.RIGHT);
        assert(chap.getInventory().size() == 1);
        assertEquals(expected[1], maze.toString());

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    /**
     * Tests trying to move chap onto a TreasureTile to collect treasure.
     */
    @Test
    public void validMoveTest_06() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new TreasureTile(1, 0));
        GameWorld game = new GameWorld(maze, chap);

        String[] expected = { "|c|*|", "|_|c|", "|c|_|" };
        assertEquals(expected[0], maze.toString());

        game.moveChap(Move.RIGHT);
        assert(chap.getChipsCollected() == 1);
        assertEquals(expected[1], maze.toString());

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    /**
     * Tests trying to move chap onto an ExitLockTile.
     */
    @Test
    public void validMoveTest_07() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new ExitLockTile(1, 0, 1));
        GameWorld game = new GameWorld(maze, chap);

        String[] expected = { "|c|X|", "|_|c|", "|c|_|" };

        assertEquals(expected[0], maze.toString());

        chap.collectChip();
        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], maze.toString());

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    /**
     * Tests trying to move chap onto a wall tile.
     */
    @Test
    public void invalidMoveTest_01() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new WallTile(1, 0));
        GameWorld game = new GameWorld(maze, chap);

        String expected = "|c|#|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    /**
     * Tests trying to move chap to a LockedDoorTile without a key.
     */
    @Test void invalidMoveTest_02() { // try move chap to locked door tile without key
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new LockedDoorTile(1, 0, Item.KEY_BLUE));
        GameWorld game = new GameWorld(maze, chap);

        String expected = "|c|D|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    /**
     * Tests trying to move chap to an ExitLockTile with an invalid amount of chips.
     */
    @Test void invalidMoveTest_03() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new ExitLockTile(1, 0, 1));
        GameWorld game = new GameWorld(maze, chap);

        String expected = "|c|X|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    /* HELPER METHODS */

    public Maze makeTestMaze(Tile testTile) {
        Maze maze = new Maze(2, 1);
        maze.setTileAt(0,0, new FreeTile(0, 0));
        maze.setTileAt(1,0, testTile);
        return maze;
    }
}
