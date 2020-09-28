package test.nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.Chap;
import nz.ac.vuw.ecs.swen225.gp30.maze.Game;
import nz.ac.vuw.ecs.swen225.gp30.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp30.maze.Move;
import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChapTest {

    @Test
    public void validMoveTest_01() { // move chap onto free tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new FreeTile(1, 0));
        setupGame(game, maze, chap);

        String expected = "|_|c|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    @Test
    public void validMoveTest_02() { // move chap onto info tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new InfoTile(1, 0));
        setupGame(game, maze, chap);

        String[] expected = { "|c|i|", "|_|c|", "|c|i|" };
        assertEquals(expected[0], maze.toString());
        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], maze.toString());
        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    @Test
    public void validMoveTest_03() { // move chap onto exit tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new ExitTile(1, 0));
        setupGame(game, maze, chap);

        String[] expected = { "|_|c|", "|c|O|" };

        game.moveChap(Move.RIGHT);
        assertEquals(expected[0], maze.toString());
        game.moveChap(Move.LEFT);
        assertEquals(expected[1], maze.toString());
    }

    @Test
    public void validMoveTest_04() { // move chap onto locked door tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new LockedDoorTile(1, 0, Item.BLUE_KEY));
        setupGame(game, maze, chap);
        chap.addItemToInventory(Item.BLUE_KEY);

        String[] expected = { "|c|D|", "|_|c|", "|c|_|" };
        assertEquals(expected[0], maze.toString());

        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], maze.toString());

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    @Test
    public void validMoveTest_05() { // move chap to key tile and collect key
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new KeyTile(1, 0, Item.BLUE_KEY));
        setupGame(game, maze, chap);

        String[] expected = { "|c|%|", "|_|c|", "|c|_|" };
        assertEquals(expected[0], maze.toString());

        game.moveChap(Move.RIGHT);
        assert(chap.getInventory().size() == 1);
        assertEquals(expected[1], maze.toString());

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    @Test
    public void validMoveTest_06() { // move chap onto treasure tile and collect treasure
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new TreasureTile(1, 0));
        setupGame(game, maze, chap);

        String[] expected = { "|c|*|", "|_|c|", "|c|_|" };
        assertEquals(expected[0], maze.toString());

        game.moveChap(Move.RIGHT);
        assert(chap.getChipsCollected() == 1);
        assertEquals(expected[1], maze.toString());

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    @Test
    public void validMoveTest_07() { // move chap onto exit lock tile (with required treasure #)
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new ExitLockTile(1, 0, 1));
        setupGame(game, maze, chap);

        String[] expected = { "|c|X|", "|_|c|", "|c|_|" };

        assertEquals(expected[0], maze.toString());

        chap.collectChip();
        game.moveChap(Move.RIGHT);
        assertEquals(expected[1], maze.toString());

        game.moveChap(Move.LEFT);
        assertEquals(expected[2], maze.toString());
    }

    @Test
    public void invalidMoveTest_01() { // try move chap onto wall tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new WallTile(1, 0));
        setupGame(game, maze, chap);

        String expected = "|c|#|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    @Test void invalidMoveTest_02() { // try move chap to locked door tile without key
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new LockedDoorTile(1, 0, Item.BLUE_KEY));
        setupGame(game, maze, chap);

        String expected = "|c|D|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    @Test void invalidMoveTest_03() { // try move chap to exit lock tile without enough chips
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new ExitLockTile(1, 0, 1));
        setupGame(game, maze, chap);

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

    public void setupGame(Game game, Maze maze, Chap chap) {
        game.setChap(chap);
        game.setMaze(maze);
        game.setChapOnMaze(0,0);
    }
}
