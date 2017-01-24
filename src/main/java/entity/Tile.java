package entity;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public class Tile {

    private int x;
    private int y;
    private int value;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", value=" + value +
                '}';
    }
}
