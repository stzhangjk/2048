package gui.animate;

import entity.Tile;
import gui.singlePlay.GamePanel;
import gui.singlePlay.TilePanel;
import util.ColorSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.1.27.
 */
public class MoveAnimate {

    /**
     * 每秒帧数
     */
    private final int FPS = 100;
    /**
     * 一帧多少秒
     */
    private final double SPF = 1.0 / FPS;
    /**
     * 每帧间隔(ms)
     */
    private final long tGap = (long) (SPF * 1000);
    /**
     * 动画总时长秒
     */
    private final double duration = 0.2;
    /**
     * 总帧数
     */
    private int sum = (int) (duration * FPS);
    /**
     * 瓦片间隙
     */
    private int GAP;
    private GamePanel gamePanel;
    private TilePanel[][] tilePanels;
    /**
     * 瓦片的图像
     */
    private List<BufferedImage> tileFroms;
    /**
     * 动作单元序列
     */
    private List<AnimateUnit> animateUnits;

    public MoveAnimate(GamePanel gamePanel, List<AnimateUnit> animateUnits) {
        this.gamePanel = gamePanel;
        this.GAP = gamePanel.getGAP();
        this.tilePanels = gamePanel.getTilePanels();
        this.animateUnits = animateUnits;
        tileFroms = new ArrayList<>(animateUnits.size());
    }


    public void play() {
        try {
            /*保存现场*/
            BufferedImage state = new BufferedImage(gamePanel.getWidth(), gamePanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics gState = state.getGraphics();
            gamePanel.paint(gState);
            for (int i = 0, len = animateUnits.size(); i < len; i++) {
                //for(AnimateUnit unit : animateUnits) {
                AnimateUnit unit = animateUnits.get(i);
                /*准备数据*/
                Tile from = unit.getFrom();
                Tile to = unit.getTo();
                TilePanel fromJP = tilePanels[from.getI()][from.getJ()];
                TilePanel toJP = tilePanels[to.getI()][to.getJ()];
                Rectangle rectFrom = fromJP.getBounds();
                Rectangle rectTo = toJP.getBounds();
                /*把from位置置为0*/
                gState.setColor(ColorSet.getBGColor(0));
                gState.fillRoundRect(rectFrom.x, rectFrom.y, rectFrom.width, rectFrom.height, GAP, GAP);
                /*保存Tile*/
                BufferedImage tile = new BufferedImage(fromJP.getWidth(), fromJP.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics gTile = tile.getGraphics();
                gTile.setColor(ColorSet.BACKGROUND);
                gTile.fillRect(0, 0, tile.getWidth(), tile.getHeight());
                fromJP.paint(gTile);
                gTile.dispose();
                tileFroms.add(tile);
                int src = 0;
                int dst = 0;
                if (from.getJ() == to.getJ()) {
                    /*竖*/
                    src = rectFrom.y;
                    dst = rectTo.y;

                } else if (from.getI() == to.getI()) {
                    /*横*/
                    src = rectFrom.x;
                    dst = rectTo.x;
                }
                double distance = dst - src;
                unit.setvMax(calVMax(distance));
                //System.out.println("distance = "+ distance + ",vmax = " + unit.getvMax());
                //double speed = distance / duration;//每秒多少像素
                //unit.setStep(speed / FPS);//每帧多少像素
            }
            gState.dispose();
            /*复制现场*/
            Image copy = gamePanel.createImage(gamePanel.getWidth(), gamePanel.getHeight());
            Graphics gCopy = copy.getGraphics();
            /*开始画*/
            Graphics g = gamePanel.getGraphics();
            /*时刻*/
            for (int i = 1; i <= sum; i++) {
                /*复制现场*/
                gCopy.drawImage(state, 0, 0, null);
                /*画*/
                for (int j = 0, size = animateUnits.size(); j < size; j++) {
                    AnimateUnit unit = animateUnits.get(j);
                    /*准备数据*/
                    Tile from = unit.getFrom();
                    Tile to = unit.getTo();
                    Rectangle rectFrom = tilePanels[from.getI()][from.getJ()].getBounds();
                    if (from.getJ() == to.getJ()) {
                        /*竖*/
                        //System.out.println(calTmpDistance(vMax[j],t));
                        gCopy.drawImage(tileFroms.get(j), rectFrom.x, (int) (rectFrom.y + calTmpDistance(unit.getvMax(), i * tGap)), null);
                        //gCopy.drawImage(tileFroms.get(j),rectFrom.x,(int)(rectFrom.y + unit.getStep() * i),null);
                    } else if (from.getI() == to.getI()) {
                        /*横*/
                        gCopy.drawImage(tileFroms.get(j), (int) (rectFrom.x + calTmpDistance(unit.getvMax(), i * tGap)), rectFrom.y, null);
                        //gCopy.drawImage(tileFroms.get(j),(int)(rectFrom.x + unit.getStep() * i),rectFrom.y,null);
                    }
                    //System.out.println("sD = " + calTmpDistance(unit.getvMax(), i * tGap));
                }
                g.drawImage(copy, 0, 0, null);
                //System.out.println(t);
                Thread.sleep(tGap);
            }
            tileFroms.clear();
            gCopy.dispose();
            g.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 计算速度曲线最大振幅也就是最大速度
     *
     * @param distance
     * @return
     */
    private double calVMax(double distance) {
        return 2 * distance / duration;
    }

    /**
     * 计算某一时刻应该走过的距离，也就是速度曲线f(t)的0到t时刻积分
     *
     * @param vMax
     * @param t    毫秒
     * @return
     */
    private double calTmpDistance(double vMax, double t) {
        //System.out.println(vMax + "," + t);
        return vMax * 0.5 * (t * 0.001 - Math.sin(2 * Math.PI * t * 0.001 / duration) * duration / 2 / Math.PI);
    }
}
