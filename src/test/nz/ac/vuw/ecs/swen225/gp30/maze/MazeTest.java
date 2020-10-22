package test.nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.*;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test suit for testing maze package functionality.
 *
 * @author campliosca 300489876
 */
public class MazeTest {

    /**
     * Tests trying to move chap onto a FreeTile, and tests directional movement.
     */
    @Test
    public void validMoveTest_01() {
        GameWorld game = makeTestGame(new FreeTile(1,0));

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
        GameWorld game = makeTestGame(new InfoTile(1,0));

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
        GameWorld game = makeTestGame(new ExitTile(1, 0));

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
        GameWorld game = makeTestGame(new LockedDoorTile(1, 0, Item.KEY_BLUE));
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
        GameWorld game = makeTestGame(new KeyTile(1, 0, Item.KEY_BLUE));

        String[] expected = { "|c|b|\n|_|_|", "|_|c|\n|_|_|", "|c|_|\n|_|_|" };
        assertEquals(expected[0], game.getMaze().toString(true));

        game.moveChap(Move.RIGHT);
        assert(game.getChap().getInventory().size() == 1);
        assertEquals(expected[1], game.getMaze().toString(true));

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], game.getMaze().toString(true));
    }

    /**
     * Tests trying to move chap onto a TreasureTile to collect treasure.
     */
    @Test
    public void validMoveTest_06() {
        GameWorld game = makeTestGame(new TreasureTile(1, 0));

        String[] expected = { "|c|T|\n|_|_|", "|_|c|\n|_|_|", "|c|_|\n|_|_|" };
        assertEquals(expected[0], game.getMaze().toString(true));

        game.moveChap(Move.RIGHT);
        assert(game.getChap().getChipsCollected() == 1);
        assertEquals(expected[1], game.getMaze().toString(true));

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], game.getMaze().toString(true));
    }

    /**
     * Tests trying to move chap onto an ExitLockTile.
     */
    @Test
    public void validMoveTest_07() {
        GameWorld game = makeTestGame(new ExitLockTile(1, 0));
        ExitLockTile.setChipsRequired(1);


        String[] expected = { "|c|l|\n|_|_|", "|_|c|\n|_|_|", "|c|_|\n|_|_|" };

        assertEquals(expected[0], game.getMaze().toString(true));
        assert(game.getChipsLeft() == 1);

        game.getChap().collectChip();
        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], game.getMaze().toString(true));

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], game.getMaze().toString(true));
    }

    @Test
    public void validMoveTest_08() {
        GameWorld game = makeTestGame(new FreeTile(1, 0));
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
        GameWorld game = makeTestGame(new WallTile(1, 0));

        String expected = "|c|#|\n|_|_|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, game.getMaze().toString(true));
    }

    /**
     * Tests trying to move chap to a LockedDoorTile without a key.
     */
    @Test
    public void invalidMoveTest_02() { // try move chap to locked door tile without key
        GameWorld game = makeTestGame(new LockedDoorTile(1, 0, Item.KEY_BLUE));
        String expected = "|c|B|\n|_|_|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, game.getMaze().toString(true));
    }

    /**
     * Tests trying to move chap to an ExitLockTile with an invalid amount of chips.
     */
    @Test
    public void invalidMoveTest_03() {
        GameWorld game = makeTestGame(new ExitLockTile(1, 0));
        ExitLockTile.setChipsRequired(1);

        String expected = "|c|l|\n|_|_|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, game.getMaze().toString(true));
    }

    /**
     * Tests trying to make an invalid move
     */
    @Test
    public void invalidMoveTest_04() {
        Chap chap = new Chap(0,0);
        Tile wt = new WallTile(1,0);
        String expected = "cannot move onto a wall tile";

        try {
            wt.addChap(chap);
        } catch(IllegalMoveException ex) {
            assertEquals(expected, ex.getMessage());
        }
    }

    /**
     * Tests mob movement onto treasure tile.
     */
    @Test
    public void mobMoveTest_01() {
        GameWorld game = makeTestGame(new TreasureTile(1, 0));
        game.setMobManager(new MobManager(game.getMaze()));
        int[] path = {0};
        game.getMobManager().addMob(new Bug(1,1, path));
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 1);
        game.moveChap(Move.RIGHT);
        game.moveChap(Move.LEFT);
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 0);
        assertEquals("mob_bug_up.png", game.getMobManager().getMobs().get(0).getImageString());
    }

    /**
     * Tests mob movement onto key tile.
     */
    @Test
    public void mobMoveTest_02() {
        GameWorld game = makeTestGame(new KeyTile(1, 0, Item.KEY_BLUE));
        game.setMobManager(new MobManager(game.getMaze()));
        int[] path = {0};
        game.getMobManager().addMob(new Bug(1,1, path));
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 1);
        game.moveChap(Move.RIGHT);
        game.moveChap(Move.LEFT);
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 0);
        assertEquals("mob_bug_up.png", game.getMobManager().getMobs().get(0).getImageString());
    }

    /**
     * Tests mob movement onto locked door tile.
     */
    @Test
    public void mobMoveTest_03() {
        GameWorld game = makeTestGame(new LockedDoorTile(1, 0, Item.KEY_BLUE));
        game.getChap().addItemToInventory(Item.KEY_BLUE);
        game.setMobManager(new MobManager(game.getMaze()));
        int[] path = {0};
        game.getMobManager().addMob(new Bug(1,1, path));
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 1);
        game.moveChap(Move.RIGHT);
        game.moveChap(Move.LEFT);
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 0);
        assertEquals("mob_bug_up.png", game.getMobManager().getMobs().get(0).getImageString());
    }

    /**
     * Tests mob movement onto exit lock tile.
     */
    @Test
    public void mobMoveTest_04() {
        GameWorld game = makeTestGame(new ExitLockTile(1, 0));
        ExitLockTile.setChipsRequired(0);
        game.setMobManager(new MobManager(game.getMaze()));
        int[] path = {0};
        game.getMobManager().addMob(new Bug(1,1, path));
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 1);
        game.moveChap(Move.RIGHT);
        game.moveChap(Move.LEFT);
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 0);
        assertEquals("mob_bug_up.png", game.getMobManager().getMobs().get(0).getImageString());
    }

    /**
     * Tests mob movement onto wall tile
     */
    @Test
    public void mobMoveTest_05() {
        GameWorld game = makeTestGame(new WallTile(1, 0));
        game.setMobManager(new MobManager(game.getMaze()));
        int[] path = {0};
        game.getMobManager().addMob(new Bug(1,1, path));
        game.advance();
        assert(game.getMobManager().getMobs().get(0).getX() == 1 && game.getMobManager().getMobs().get(0).getY() == 1);
        assertEquals("mob_bug_down.png", game.getMobManager().getMobs().get(0).getImageString());
    }

    /**
     * Tests the time left and decrement function.
     */
    @Test
    public void validTimeTest() {
        GameWorld game = makeTestGame(new FreeTile(1, 0));
        game.setTimeLeft(100);
        game.decrementTimeLeft();
        assert(game.getTimeLeft() == 99);
    }


    /* HELPER METHODS */

    public GameWorld makeTestGame(Tile testTile) {
        Chap chap = new Chap(0,0);
        Maze maze = new Maze(2, 2);
        maze.setTileAt(0,0, new FreeTile(0, 0));
        maze.setTileAt(0, 1, new FreeTile(0,1));
        maze.setTileAt(1, 1, new FreeTile(1,1));
        maze.setTileAt(1,0, testTile);

        return new GameWorld(maze, chap);
    }
}
