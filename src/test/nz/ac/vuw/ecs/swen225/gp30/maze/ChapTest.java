package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.item.Item;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChapTest {

    @Test
    public void validMoveTest_01() { // move chap onto wall tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new FreeTile(1, 0));
        setupGame(game, maze, chap);

        String expected = "|_|c|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    @Test
    public void invalidMoveTest_01() { // move chap onto wall tile
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new WallTile(1, 0));
        setupGame(game, maze, chap);

        String expected = "|c|#|";

        game.moveChap(Move.RIGHT);
        assertEquals(expected, maze.toString());
    }

    @Test
    public void validChipCollectionTest_01() {
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new TreasureTile(1, 0));
        setupGame(game, maze, chap);

        String expected = "|_|c|";
        game.moveChap(Move.RIGHT);

        assert(chap.getChipsCollected() == 1);
        assertEquals(expected, maze.toString());
    }

    @Test
    public void validKeyCollectionTest_01() {
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new KeyTile(1, 0, Item.BLUE_KEY));
        setupGame(game, maze, chap);

        String expected = "|_|c|";
        game.moveChap(Move.RIGHT);

        assert(chap.getInventory().size() == 1);
        assertEquals(expected, maze.toString());
    }

    @Test
    public void validUnlockDoorTest_01() {
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new LockedDoorTile(1, 0, Item.BLUE_KEY));
        setupGame(game, maze, chap);

        chap.addItemToInventory(Item.BLUE_KEY);
        game.moveChap(Move.RIGHT);

        String expected = "|_|c|";

        assertEquals(expected, maze.toString());
    }

    @Test void invalidUnlockDoorTest_01() {
        Game game = new Game();
        Chap chap = new Chap();
        Maze maze = makeTestMaze(new LockedDoorTile(1, 0, Item.BLUE_KEY));
        setupGame(game, maze, chap);

        String expected = "|c|D|";

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
