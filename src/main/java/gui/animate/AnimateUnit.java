package gui.animate;

import entity.Tile;

import java.awt.*;

/**
 * Created by STZHANGJK on 2017.1.31.
 * 动画单元
 */
public class AnimateUnit {

    private Tile from;
    private Tile to;
    /**每帧多少像素*/
    private double step;

    public AnimateUnit(Tile from, Tile to) {
        this.from = from;
        this.to = to;
    }

    public Tile getFrom() {
        return from;
    }

    public Tile getTo() {
        return to;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
