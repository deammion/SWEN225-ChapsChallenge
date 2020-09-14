package nz.ac.vuw.ecs.swen225.gp30.maze;

import nz.ac.vuw.ecs.swen225.gp30.maze.tile.FreeTile;
import nz.ac.vuw.ecs.swen225.gp30.maze.tile.WallTile;
import org.junit.jupiter.api.Test;

public class TileTest {
    @Test
    public void testFreeTileAddAndRemove() {
        Chap chap = new Chap();
        FreeTile tile = new FreeTile(0,0);

        tile.addChap(chap);
        assert(tile.hasChap());
        tile.removeChap();
        assert(!tile.hasChap());
    }

    @Test
    public void testWallTileAddAndRemove() {
        Chap chap = new Chap();
        WallTile tile = new WallTile(0,0);
        assert(!tile.addChap(chap));
        assert(!tile.hasChap());
        assert(!tile.removeChap());
    }

}
