package core;

public class Position {
    int x;
    int y;
    Direction direction;

    public Position() {
        x = 0;
        y = 0;
        direction = null;
    }

    public Position(int x, int y, Direction direction) {
        this.x = x;
       this.y = y;
        this.direction = direction;
    }

    public String getPos() {
        return "x,y(" + x  + "," + y + ")" ;
    }
}
