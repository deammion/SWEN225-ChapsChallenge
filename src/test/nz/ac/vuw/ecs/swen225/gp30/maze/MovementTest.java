package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.tile.FreeTile;
import org.junit.jupiter.api.Test;

public class MovementTest {
    @Test
    public void testFreeTileAddAndRemove() {
        Chap chap = new Chap();
        FreeTile tile = new FreeTile(0,0);

        tile.addChap(chap);
        assert(tile.hasChap());
        tile.removeChap();
        assert(!tile.hasChap());
    }

}
