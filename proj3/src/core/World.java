package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import javax.swing.text.Utilities;
import java.util.Random;
/*
2d board filled with floor tiles surrounded by walls (hallways and rooms)
 */
public class World {
    //world render engine
    private TERenderer world;
    //2d array of tiles with size of width and height
    private TETile[][] worldTiles;
    private int width ;
    private int height;
    //java.util.Random generates a deterministic sequence of random numbers
    Random randomNumber;

    /*
    Takes 3 integer values and initialises world
    @param random -number used for pseudo random generation
    @param width -width of array
    @param height -height of array
     */
    public World(int random,int width, int height) {
        this.width = width;
        this.height = height;
        worldTiles = new TETile[width][height];
        randomNumber = new Random(random);
        worldTiles = fillWorldTiles(worldTiles);
        world = new TERenderer();
        world.initialize(width, height);
    }

    private class Position {
        int x;
        int y;
        Direction direction;

        private Position() {
            x = 0;
            y = 0;
            direction = null;
        }
    }

    /*
    Filling tiles with green dots (Tileset.FLOOR)
    by traversing the array.
     */
    private TETile[][] fillWorldTiles(TETile[][] tiles) {
        tiles = fillWorldFlorTiles(tiles);
        //tiles = fillWorldBorderTiles(tiles);
        tiles = fillRandomWallTiles(tiles);
        return tiles;
    }

    private TETile[][] fillRandomWallTiles(TETile[][] tiles) {
        int direction;
        Position pos = randomStartPosition();
        pos.direction = Random4Direction();

        addWall(tiles, pos);

        return tiles;

        //return fillWall(tiles,pos);
    }

    private void addWall(TETile[][] tiles, Position pos) {
        tiles[pos.x][pos.y] = Tileset.WALL;
    }

    /*
    private TETile[][] fillWall(TETile[][] tiles,Position pos) {
        while (pos.direction != null) {
                pos = fillDirection(tiles, pos);
        }
        return tiles;
    }

    private Position fillDirection(TETile[][] tiles, Position pos) {
        int count = 0;
        int newDirection;
        if(canFillminimum()) {
                x, y, direction = fillThree(tiles, x, y, direction);
            }

            while (count < 5) {
                newDirection = Random3Direction(x, y);
                if (newDirection != direction)
                    direction = newDirection;
                return x,y, direction;
                if(!canFill())
                    break;
                x, y, direction = fillSameDirection(tiles, x, y, direction);
                count++;
            }

        return x,y,Random2Direction(x, y);//returns -1 if not possible
    }

   x,y,direction fill(TETile[][] tiles, int x, int y, int direction) {
        int x = 0;
        int y = 0;

        if (direction == Direction.LEFT) {
                x = x - 1;
        }
        if (direction == Direction.RIGHT) {
                x = x + 1;
        }
        if (direction == Direction.UP) {
            y = y + 1;
        }
        if (direction == Direction.DOWN) {
            y = y - 1;
        }
        if(canFill(d.x + x, d.y + y)) {
            d.x += x;
            d.y += y;
            fill(d.x, d.y);
        }

        return x,y,direction;
    }

    private boolean canFill(x, y, direction) {
    return true;
    }
     */

    private Direction Random4Direction() {
        int d = RandomUtils.uniform(randomNumber, 4);
        return Direction.values()[d];
    }

    private Position randomStartPosition() {
        Position pos = new Position();
        pos.x = RandomUtils.uniform(randomNumber, width - 6) + 3;
        pos.y = RandomUtils.uniform(randomNumber, height - 6) + 3;

        return pos;
    }

    /*
  Filling tiles with green dots (Tileset.FLOOR) by traversing the array.
   */
    private TETile[][]  fillWorldFlorTiles(TETile[][] tiles) {
        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y >= 0; y--) {
                tiles[x][y] = Tileset.FLOOR;
            }
        }
        return tiles;
    }

    /*
 Filling boarder tiles with Tileset.Nothing(black space) by traversing the boarder of array.
  */
    private TETile[][]  fillWorldBorderTiles(TETile[][] tiles) {
        for (int x = 0; x < width; x = x + width - 1) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }

        for (int y = 0; y < height; y = y + height - 1) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        return tiles;
    }
    /*
    Render 2d board height and width dimensions
     */
    public void renderFrame() {
        world.renderFrame(worldTiles);
    }

    /*
    return copy of a world tiles for testing
     */
    public TETile[][] getTiles() {
        TETile[][] copyTiles = new TETile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y >= 0; y--) {
                copyTiles[x][y] = worldTiles[x][y];
            }
        }
        return copyTiles;
    }

}
