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
        fillWorldTiles();
        world = new TERenderer();
        world.initialize(width, height);
    }

    /*
    Filling 2d array with tiles
     */
    private void fillWorldTiles() {
        fillWorldFlorTiles();
        fillWorldBorderTiles();
        fillRandomWallTiles();
    }


    /*
    choose random starting position, direction and fill with wall tiles choosing random direction
    with min 2 and max 8 tiles in same direction.
    */
    private void fillRandomWallTiles() {
        Position pos = randomStartPosition();
        pos.direction = Random4Direction();

        addWall(worldTiles, pos);

        fillWall(pos);
    }

    /*
    Add wall tiles on position
    @param pos position of 2d array(pos.x, pos.y)
     */
    private void addWall(TETile[][] tiles, Position pos) {
        tiles[pos.x][pos.y] = Tileset.WALL;
    }


    /*
    Fill with wall tiles.Every change of direction run fillDirection again.
    When filling is than fillDirection will return null.
     */
    private void fillWall(Position pos) {
        while (pos.direction != null) {
                pos = fillDirection(worldTiles, pos);
        }
    }

    /*
     From position fill in pos.direction tiles min 2 times, after that chose direction randomly.
     If going in that direction is not possible try picking new direction.
     After picking new direction return it.
     If all directions blocked return null
     */
    public Position fillDirection(TETile[][] tiles,Position pos) {
        int count = 0;
        Position newPos;


        fillMin(tiles, pos);
        while (count < 5) {
            newPos = new Position(pos.x, pos.y, pos.direction);
            RandomXDirection(newPos, false);

            if (newPos == null || newPos.direction != pos.direction) {
                pos.direction = newPos.direction;
                return pos;
            }
            fill(tiles,pos, 1);
            count++;
        }
        RandomXDirection(pos, true);
        return pos;//returns null if not possible

    }
    /*
    Fill 3 tiles in same direction if possible
     */

    private void fillMin(TETile[][] tiles,Position pos) {
        if (canFillminimum(pos)) {
            fill(tiles, pos, 3);
        } else {
            fill(tiles, pos, 1);
        }
    }


    /*
    Uses pos direction to update coordinates(pos.x, pos.y) and addWall on that position.
    Do it k times
    */
    public Position fill(TETile[][] tiles, Position pos, int k) {
        for (int i = 0; i < k; i++){
            updatePosition(pos);
            addWall(tiles, pos);
        }
        return pos;
    }
    /*
    Uses pos.direction to update coordinates(pos.x, pos.y)
     */
    public Position updatePosition(Position pos) {
        if ( pos.direction == Direction.LEFT) {
            pos.x = pos.x - 1;
        }
        if (pos.direction == Direction.RIGHT) {
            pos.x = pos.x + 1;
        }
        if (pos.direction == Direction.UP) {
            pos.y = pos.y + 1;
        }
        if (pos.direction == Direction.DOWN) {
            pos.y = pos.y - 1;
        }
        return pos;
    }


    /*
    Compare direction and return opposite one
     */
    private Direction oppositeDirection(Direction direction) {
        Direction opposite = null;
        if(direction == Direction.UP) { opposite = Direction.DOWN; }
        if(direction == Direction.DOWN) { opposite = Direction.UP; }
        if(direction == Direction.LEFT) { opposite = Direction.RIGHT; }
        if(direction == Direction.RIGHT) { opposite = Direction.LEFT; }
        return opposite;
    }

    /*
    Check if there is valid place to fill and call helper method,
    or assign null to direction
     */
    public void RandomXDirection(Position pos, boolean notCurrentDirection) {
        Direction oppositeDirection = oppositeDirection(pos.direction);
        Direction newDirection;

        if (canFillAny(pos, notCurrentDirection)) { RandomXDirectionHelper(pos, notCurrentDirection); }
        else { pos.direction = null; }
    }

    /*
   Return random direction(LEFT, RIGHT, UP, DOWN),
   if current direction is not allowed returns random direction(except opposite one
   and current one),
   if its allowed, include current one as one of potential random directions.
    */
    private void RandomXDirectionHelper(Position pos, boolean notCurrentDirection) {
        Direction oppositeDirection = oppositeDirection(pos.direction);
        Position newPos = new Position(pos.x, pos.y, pos.direction);


        while (true) {
            newPos.direction = Random4Direction();
            if (notCurrentDirection) {//if current direction is not valid
                if (newPos.direction != pos.direction && newPos.direction != oppositeDirection
                    && canFill(newPos)) {
                    pos.direction = newPos.direction;
                    break;
                }
            }
            if (newPos.direction != oppositeDirection && canFill(newPos)) {
                pos.direction = newPos.direction;
                break;
            }
        }
    }

    private boolean canFill(Position pos) {
        if (worldTiles[pos.x][pos.y] == Tileset.FLOOR) {//if one pos good
            return true;
        }
        return false;
    }

   private boolean canFillAny(Position pos, boolean notCurrentDirection) {
       Direction oppositeDirection = oppositeDirection(pos.direction);
       Position testPos;

       for (int k = 0; k < 4; k++) {
           testPos = new Position(pos.x, pos.y, pos.direction);
           if (Direction.values()[k] != oppositeDirection) {//you cant go backwards
               updatePosition(testPos);
               if (worldTiles[testPos.x][testPos.y] == Tileset.FLOOR) {//if one pos good
                   return true;
               }
           }
       }
       return false;
   }

   private boolean canFillminimum(Position pos) {
       Position newPos = new Position(pos.x, pos.y, pos.direction);
       for (int i = 0; i < 3; i++) {
           updatePosition(newPos);
           if (worldTiles[newPos.x][newPos.y] != Tileset.FLOOR) {
               return false;
           }
       }
       return true;
   }

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
    private void   fillWorldFlorTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = height - 1; y >= 0; y--) {
                worldTiles[x][y] = Tileset.FLOOR;
            }
        }
    }

    /*
 Filling boarder tiles with Tileset.Nothing(black space) by traversing the boarder of array.
  */
    private TETile[][]  fillWorldBorderTiles() {
        for (int x = 0; x < width; x = x + width - 1) {
            for (int y = 0; y < height; y++) {
                worldTiles[x][y] = Tileset.NOTHING;
            }
        }

        for (int y = 0; y < height; y = y + height - 1) {
            for (int x = 0; x < width; x++) {
                worldTiles[x][y] = Tileset.NOTHING;
            }
        }
        return worldTiles;
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
