package entity;

import java.io.Serializable;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public class Tile implements Serializable,Cloneable{

    private int i;
    private int j;
    private int value;

    public Tile(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "i=" + i +
                ", j=" + j +
                ", value=" + value +
                '}';
    }

    @Override
    public Tile clone() throws CloneNotSupportedException {
        Tile t = new Tile(i,j);
        t.setValue(value);
        return t;
    }
}
