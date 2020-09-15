package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.tile.FreeTile;
import org.junit.jupiter.api.Test;
import test.nz.ac.vuw.ecs.swen225.gp30.maze.DynamicTest;

public class MovementTest {
    @DynamicTest
    public void testFreeTileAddAndRemove() {
        Chap chap = new Chap();
        FreeTile tile = new FreeTile(0,0);

        tile.addChap(chap);
        assert(tile.hasChap());
        tile.removeChap();
        assert(!tile.hasChap());
    }

}
