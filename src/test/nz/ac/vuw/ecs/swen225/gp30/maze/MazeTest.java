package test.nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.*;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeTest {

    /**
     * Tests trying to move chap onto a FreeTile, and tests directional movement.
     */
    @Test
    public void validMoveTest_01() {
        GameWorld game = new GameWorld(makeTestMaze(new FreeTile(1, 0)), new Chap(0,0));
        String[] expected = { "|_|c|\n|_|_|", "|_|_|\n|_|c|", "|_|_|\n|c|_|", "|c|_|\n|_|_|" };
        String expectedString = "Chap: {"
                + "\n\tx: 0"
                + "\n\ty: 0"
                + "\n\tchips_collected: 0"
                + "\n\tinventory: []"
                + "\n\timage: actor_chap_up.png"
                + "\n}";

        game.moveChap(Move.RIGHT);
        assertEquals(expected[0], game.getMaze().toString(true));
        game.moveChap(Move.DOWN);
        assertEquals(expected[1], game.getMaze().toString(true));
        game.moveChap(Move.LEFT);
        assertEquals(expected[2], game.getMaze().toString(true));
        game.moveChap(Move.UP);
        assertEquals(expected[3], game.getMaze().toString(true));
        assertEquals(expectedString, game.getChap().toString());
    }

    /**
     * Tests trying to move chap onto an InfoTile.
     */
    @Test
    public void validMoveTest_02() {
        GameWorld game = new GameWorld(makeTestMaze(new InfoTile(1, 0)), new Chap(0,0));

        String[] expected = { "|c|i|\n|_|_|", "|_|c|\n|_|_|", "|c|i|\n|_|_|" };
        assertEquals(expected[0], game.getMaze().toString(true));
        game.moveChap(Move.RIGHT);
        assert(game.isChapOnInfo());
        assertEquals(expected[1], game.getMaze().toString(true));
        game.moveChap(Move.LEFT);
        assertEquals(expected[2], game.getMaze().toString(true));
    }

    /**
     * Tests trying to move chap onto an ExitTile.
     */
    @Test
    public void validMoveTest_03() {
        GameWorld game = new GameWorld(makeTestMaze(new ExitTile(1, 0)), new Chap(0,0));

        String[] expected = { "|_|c|\n|_|_|", "|c|E|\n|_|_|" };

        game.moveChap(Move.RIGHT);
        assert(game.isChapOnExit());
        assertEquals(expected[0], game.getMaze().toString(true));
        game.moveChap(Move.LEFT);
        assertEquals(expected[1], game.getMaze().toString(true));
    }

    /**
     * Tests trying to move chap onto a LockedDoorTile with the correct key in inventory.
     */
    @Test
    public void validMoveTest_04() {
        GameWorld game = new GameWorld(makeTestMaze(new LockedDoorTile(1, 0, Item.KEY_BLUE)), new Chap(0,0));
        game.getChap().addItemToInventory(Item.KEY_BLUE);

        String[] expected = { "|c|B|\n|_|_|", "|_|c|\n|_|_|", "|c|_|\n|_|_|" };
        assertEquals(expected[0], game.getMaze().toString(true));

        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], game.getMaze().toString(true));

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], game.getMaze().toString(true));
    }

    /**
     * Tests trying to move chap onto a KeyTile and picking up key.
     */
    @Test
    public void validMoveTest_05() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new KeyTile(1, 0, Item.KEY_BLUE));
        GameWorld game = new GameWorld(maze, chap);

        String[] expected = { "|c|b|\n|_|_|", "|_|c|\n|_|_|", "|c|_|\n|_|_|" };
        assertEquals(expected[0], maze.toString(true));

        game.moveChap(Move.RIGHT);
        assert(chap.getInventory().size() == 1);
        assertEquals(expected[1], maze.toString(true));

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString(true));
    }

    /**
     * Tests trying to move chap onto a TreasureTile to collect treasure.
     */
    @Test
    public void validMoveTest_06() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new TreasureTile(1, 0));
        GameWorld game = new GameWorld(maze, chap);

        String[] expected = { "|c|T|\n|_|_|", "|_|c|\n|_|_|", "|c|_|\n|_|_|" };
        assertEquals(expected[0], maze.toString(true));

        game.moveChap(Move.RIGHT);
        assert(chap.getChipsCollected() == 1);
        assertEquals(expected[1], maze.toString(true));

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString(true));
    }

    /**
     * Tests trying to move chap onto an ExitLockTile.
     */
    @Test
    public void validMoveTest_07() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new ExitLockTile(1, 0));
        ExitLockTile.CHIPS_REQUIRED = 1;
        GameWorld game = new GameWorld(maze, chap);

        String[] expected = { "|c|l|\n|_|_|", "|_|c|\n|_|_|", "|c|_|\n|_|_|" };

        assertEquals(expected[0], maze.toString(true));
        assert(game.getChipsLeft() == 1);

        chap.collectChip();
        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], maze.toString(true));

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString(true));
    }

    @Test
    public void validMoveTest_08() {
        GameWorld game = new GameWorld(makeTestMaze(new FreeTile(1,0)), new Chap(0,0));
        game.setMobManager(new MobManager(game.getMaze()));
        int[] path = {2};
        game.getMobManager().addMob(new Bug(1,0, path));
        game.advance();
        assert(!game.isChapActive());
    }

    /**
     * Tests trying to move chap onto a wall tile.
     */
    @Test
    public void invalidMoveTest_01() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new WallTile(1, 0));
        GameWorld game = new GameWorld(maze, chap);

        String expected = "|c|#|\n|_|_|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString(true));
    }

    /**
     * Tests trying to move chap to a LockedDoorTile without a key.
     */
    @Test void invalidMoveTest_02() { // try move chap to locked door tile without key
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new LockedDoorTile(1, 0, Item.KEY_BLUE));
        GameWorld game = new GameWorld(maze, chap);

        String expected = "|c|B|\n|_|_|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString(true));
    }

    /**
     * Tests trying to move chap to an ExitLockTile with an invalid amount of chips.
     */
    @Test void invalidMoveTest_03() {
        Chap chap = new Chap(0,0);
        Maze maze = makeTestMaze(new ExitLockTile(1, 0));
        ExitLockTile.CHIPS_REQUIRED = 1;
        GameWorld game = new GameWorld(maze, chap);

        String expected = "|c|l|\n|_|_|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString(true));
    }

    /**
     * Tests trying to make an invalid move
     */
    @Test void invalidMoveTest_04() {
        Chap chap = new Chap(0,0);
        Tile wt = new WallTile(1,0);
        String expected = "cannot move onto a wall tile";

        try {
            wt.addChap(chap);
        } catch(IllegalMoveException ex) {
            assertEquals(expected, ex.getMessage());
        }
    }

    /* HELPER METHODS */

    public Maze makeTestMaze(Tile testTile) {
        Maze maze = new Maze(2, 2);
        maze.setTileAt(0,0, new FreeTile(0, 0));
        maze.setTileAt(0, 1, new FreeTile(0,1));
        maze.setTileAt(1, 1, new FreeTile(1,1));
        maze.setTileAt(1,0, testTile);

        return maze;
    }
}
