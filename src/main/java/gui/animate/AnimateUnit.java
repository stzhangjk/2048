package gui.animate;

import entity.Tile;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by STZHANGJK on 2017.1.31.
 * 动画单元
 */
public class AnimateUnit implements Serializable,Cloneable{

    private Tile from;
    private Tile to;
    /**每帧多少像素*/
    private double step;
    /**最大速度*/
    private double vMax;

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

    public double getvMax() {
        return vMax;
    }

    public void setvMax(double vMax) {
        this.vMax = vMax;
    }

    @Override
    public AnimateUnit clone() throws CloneNotSupportedException {
        Tile f = from.clone();
        Tile t = to.clone();
        AnimateUnit u = new AnimateUnit(f,t);
        u.setStep(step);
        u.setvMax(vMax);
        return u;
    }
}
