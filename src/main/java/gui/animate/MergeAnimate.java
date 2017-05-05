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
 * Created by STZHANGJK on 2017.2.1.
 */
public class MergeAnimate {


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
    private final double duration = 0.5;
    /**
     * 总帧数
     */
    private int sum = (int) (duration * FPS);
    /**
     * 衰减因子,一定要是负数
     */
    private double k = -2;

    private GamePanel gameView;
    private List<AnimateUnit> animateUnits;
    /**
     * 方块的图像
     */
    private List<BufferedImage> toImgs;
    /**
     * 方块的坐标
     */
    private List<Rectangle> toBounds;
    private TilePanel[][] tilePanels;
    private int GAP;
    /**
     * 方块宽度
     */
    private int width;
    /**
     * 方块高度
     */
    private int height;


    public MergeAnimate(GamePanel gameView, List<AnimateUnit> animateUnits) {
        this.gameView = gameView;
        this.tilePanels = gameView.getTilePanels();
        width = tilePanels[0][0].getWidth();
        height = tilePanels[0][0].getHeight();
        this.GAP = gameView.getGAP();
        this.animateUnits = animateUnits;
    }

    /**
     * 播放动画
     *
     * @return 返回融合后的最大值
     */
    public int play() {
        int max = 0;
        toImgs = new ArrayList<>(animateUnits.size());
        toBounds = new ArrayList<>(animateUnits.size());
        /*保存现场*/
        BufferedImage state = new BufferedImage(gameView.getWidth(), gameView.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics gState = state.getGraphics();
        gameView.paint(gState);

        for (AnimateUnit unit : animateUnits) {
            Tile from = unit.getFrom();
            Tile to = unit.getTo();
            from.setValue(0);
            int v = to.getValue() << 1;
            to.setValue(v);
            gameView.updateValue(to,-1);
            max = Math.max(max, v);
            /*保存方块图像*/
            TilePanel t = tilePanels[to.getI()][to.getJ()];
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            t.paint(g);
            g.dispose();

            /*把to位置置为0*/
            Rectangle rect = t.getBounds();
            gState.setColor(ColorSet.BACKGROUND);
            gState.fillRect(rect.x, rect.y, rect.width, rect.height);

            toImgs.add(img);
            toBounds.add(rect);
        }
        gState.dispose();
        /*复制现场*/
        Image copy = gameView.createImage(gameView.getWidth(), gameView.getHeight());
        Graphics gCopy = copy.getGraphics();
        /*开始画*/
        Graphics g = gameView.getGraphics();
        try {
            /*时刻*/
            for (int i = 1; i <= sum; i++) {
                //System.out.println("t = " + i * tGap);
                double w = calLength(i * tGap * 0.001, width);
                double h = calLength(i * tGap * 0.001, height);
                //System.out.println("w = " + w + ",h = " + h);
                /*复制现场*/
                gCopy.drawImage(state, 0, 0, null);
                /*画*/
                for (int j = 0, size = animateUnits.size(); j < size; j++) {
                    Rectangle rect = toBounds.get(j);
                    Image scale = toImgs.get(j).getScaledInstance((int) w, (int) h, Image.SCALE_DEFAULT);
                    gCopy.drawImage(scale, (int) (rect.x - 0.5 * (w - width)), (int) (rect.y - 0.5 * (h - height)), null);
                }
                g.drawImage(copy, 0, 0, null);
                Thread.sleep(tGap);
            }
            toImgs.clear();
            toBounds.clear();
            gCopy.dispose();
            g.dispose();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return max;
    }

    /**
     * @param t 时刻,s
     * @param l 原长度
     * @return
     */
    private double calLength(double t, double l) {
        //System.out.println("u = " + 2 * GAP * Math.sin(2 * Math.PI / duration * t) );
        //System.out.println("v = " + Math.exp(k * t));
        return 2 * GAP * Math.sin(2 * Math.PI / duration * t) * Math.exp(k * t) + l;
    }

}
