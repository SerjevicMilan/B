import core.AutograderBuddy;
import core.Direction;
import core.Position;
import core.World;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;


public class WorldGenTests {
  /*
    @Test
    public void basicTest() {
        // put different seeds here to test different worlds
        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1234567890123456789s");

        TERenderer ter = new TERenderer();
        ter.initialize(tiles.length, tiles[0].length);
        ter.renderFrame(tiles);
        StdDraw.pause(5000); // pause for 5 seconds so you can see the output
    }
 */
  @Test
  public void fillTest() {
      //initialise world with floor tiles and nothing tiles
      World world = new World(1, 10, 10);
      TETile[][] worldTiles = new TETile[10][10];
      Position pos = new Position(4, 4, Direction.RIGHT);

      //fills expextedTiles with floor tiles and boarder tiles with nothing
      TETile[][] expextedTiles = new TETile[10][10];
      for(int x = 0; x < 10; x++) {
          for(int y = 0; y < 10; y++) {
                expextedTiles[x][y] = Tileset.FLOOR;
                worldTiles[x][y] = Tileset.FLOOR;
          }
      }
      expextedTiles[5][4] = Tileset.WALL;
      world.fill(worldTiles, pos, 1);

      for(int x = 0; x < 10; x++) {
          for(int y = 0; y < 10; y++) {
              assertWithMessage("Tiles are not matching at position x,y : " + x + "," + y)
                      .that(worldTiles[x][y]).isEqualTo(expextedTiles[x][y]);
          }
      }
  }
  /*
  @Test public RandomXDirectionTest() {
      World world = new World(1, 10, 10);
      Position pos = new Position(4,4, Direction.RIGHT);

      world.RandomXDirection()
  }

   */

    @Test
    public void updatePositionTest() {
        //initialise world with floor tiles and nothing tiles
        World world = new World(1, 10, 10);
        Position pos = new Position(4, 4, Direction.DOWN);

        Position newPos = world.updatePosition(pos);
        assertThat(newPos.getPos()).isEqualTo("x,y(4,3)");

        pos = new Position(4, 4, Direction.UP);
        newPos = world.updatePosition(pos);
        assertThat(newPos.getPos()).isEqualTo("x,y(4,5)");

        pos = new Position(4, 4, Direction.LEFT);
        newPos = world.updatePosition(pos);
        assertThat(newPos.getPos()).isEqualTo("x,y(3,4)");

        pos = new Position(4, 4, Direction.RIGHT);
        newPos = world.updatePosition(pos);
        assertThat(newPos.getPos()).isEqualTo("x,y(5,4)");
    }

    @Test
    public void basicInteractivityTest() {
        // TODO: write a test that uses an input like "n123swasdwasd"
    }

    @Test
    public void basicSaveTest() {
        // TODO: write a test that calls getWorldFromInput twice, with "n123swasd:q" and with "lwasd"
    }
}
