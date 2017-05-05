package game;

import entity.Tile;
import game.interfaces.game.IGameEngine;
import game.interfaces.view.IControlView;
import game.interfaces.view.IGameView;
import game.interfaces.view.IInfoView;
import gui.animate.AnimateUnit;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by STZHANGJK on 2017.1.21.
 * 游戏线程
 */
public class GameEngine implements IGameEngine {

    /**
     * 瓦片
     */
    private Tile[][] tiles;
    /**
     * 游戏界面
     */
    private IGameView gameView;
    /**
     * 分数界面
     */
    private IInfoView infoView;
    /**
     * 控制
     */
    private IControlView ctlView;
    /**
     * 移动动作单元列表
     */
    private List<AnimateUnit> moveAnimateUnits;
    /**
     * 合并动作单元列表
     */
    private List<AnimateUnit> mergeAnimateUnits;
    private boolean isMove;
    private boolean isMerged;
    /**
     * 分数
     */
    private int score;
    /**最大分数*/
    private int maxScore;
    /**
     * 最大的值
     */
    private int max;
    /**胜利条件*/
    private final int WIN_NUM = 2048;
    private volatile boolean end;

    /**
     * 初始化瓦片
     *
     * @param mapSize 地图规模
     */
    private void initTiles(int mapSize) {
        tiles = new Tile[mapSize][mapSize];
        for (int i = 0, len = tiles.length; i < len; i++) {
            for (int j = 0; j < len; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    /**
     * 初始化游戏
     */
    @Override
    public void initGame() {
        moveAnimateUnits = new ArrayList<>();
        mergeAnimateUnits = new ArrayList<>();
        initTiles(4);
        /*生成两个2*/
        createTile();
        createTile();
        /*分数置0*/
        score = 0;
        max = 0;
        end = false;
        loadMaxScore();
    }

    @Override
    public Tile[][] getTiles() {
        return tiles;
    }

    @Override
    public void initForRemote(Tile[][] tiles) {
        moveAnimateUnits = new ArrayList<>();
        mergeAnimateUnits = new ArrayList<>();
        this.tiles = tiles;
        score = 0;
        max = 0;
        end= false;
    }

    /**
     * 开始游戏
     */
    public void start() {
        initGame();
    }

    /**
     * 重新开始
     */
    public void restart() {
        initGame();
    }

    /**
     * 从/MaxScore.properties文件读取历史最大分数
     */
    private void loadMaxScore(){

        try (InputStream in = getClass().getResourceAsStream("/MaxScore.properties")){
            Properties p = new Properties();
            p.load(in);
            maxScore = Integer.parseInt(p.getProperty("max"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存最大分数
     */
    private void saveScore(){
        try(OutputStream out = new FileOutputStream(getClass().getResource("/MaxScore.properties").getPath())){
            Properties p = new Properties();
            p.put("max",String.valueOf(score));
            p.store(out,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成新的数字
     */
    private Tile createTile() {
        List<Tile> zero = new ArrayList<>();
        Tile newTile = null;
        for (int i = 0, len = tiles.length; i < len; i++) {
            for (int j = 0; j < len; j++)
                if (tiles[i][j].getValue() == 0)
                    zero.add(tiles[i][j]);
        }

        if (!zero.isEmpty()) {
            Random rand = new Random();
            int index = rand.nextInt(zero.size());
            newTile = zero.get(index);
            newTile.setValue(2);
        }
        return newTile;
    }

    private void createTileAndUpdate() throws RemoteException {
        Tile newT = createTile();
        if (newT != null) {
            SwingUtilities.invokeLater(()->{
                try {
                    gameView.updateValue(newT,-1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public synchronized void doUp() {
        if (!end) {
            isMove = false;
            isMerged = false;
            for (int j = 0, len = tiles.length; j < len; j++) {
                int lastMergeX = -1;    //必须一排一排遍历才有用,不需要记纵轴
                for (int from = 1; from < len; from++) {
                    int k = from;
                    if (tiles[from][j].getValue() != 0) {
                        while (k > 0 && tiles[k - 1][j].getValue() == 0) {
                    /*如果前方有空位...*/
                            k--;
                        }
                        if (k != from) {
                            if (k > 0 && lastMergeX != k - 1 && tiles[from][j].getValue() == tiles[k - 1][j].getValue()) {
                                move(tiles[from][j], tiles[k - 1][j]);
                                merge(tiles[from][j], tiles[k - 1][j]);
                                isMerged = true;
                                lastMergeX = k - 1;
                            } else {
                                move(tiles[from][j], tiles[k][j]);
                                isMove = true;
                            }
                        } else {
                            if (k > 0 && lastMergeX != k - 1 && tiles[from][j].getValue() == tiles[k - 1][j].getValue()) {
                                move(tiles[from][j], tiles[k - 1][j]);
                                merge(tiles[from][j], tiles[k - 1][j]);
                                isMerged = true;
                                lastMergeX = k - 1;
                            }
                        }
                    }
                }
            }
        /*处理数据并播放动画*/
            playAnimate();
        }

    }

    public synchronized void doDown() {
        if (!end) {
            isMove = false;
            isMerged = false;
            for (int j = 0, len = tiles.length; j < len; j++) {
                int lastMergeX = -1;
                for (int from = len - 2; from >= 0; from--) {
                    int k = from;
                    if (tiles[from][j].getValue() != 0) {
                        while (k < len - 1 && tiles[k + 1][j].getValue() == 0) {
                    /*如果前方有空位...*/
                            k++;
                        }
                        if (k != from) {
                            if (k < len - 1 && lastMergeX != k + 1 && tiles[from][j].getValue() == tiles[k + 1][j].getValue()) {
                                move(tiles[from][j], tiles[k + 1][j]);
                                merge(tiles[from][j], tiles[k + 1][j]);
                                isMerged = true;
                                lastMergeX = k + 1;
                            } else {
                                move(tiles[from][j], tiles[k][j]);
                                isMove = true;
                            }
                        } else {
                            if (k < len - 1 && lastMergeX != k + 1 && tiles[from][j].getValue() == tiles[k + 1][j].getValue()) {
                                move(tiles[from][j], tiles[k + 1][j]);
                                merge(tiles[from][j], tiles[k + 1][j]);
                                isMerged = true;
                                lastMergeX = k + 1;
                            }
                        }


                    }
                }
            }
         /*处理数据并播放动画*/
            playAnimate();
        }
    }

    public synchronized void doLeft() {
        if (!end) {
            isMove = false;
            isMerged = false;
            for (int i = 0, len = tiles.length; i < len; i++) {
                int lastMergeY = -1;
                for (int from = 1; from < len; from++) {
                    int k = from;
                    if (tiles[i][from].getValue() != 0) {
                        while (k > 0 && tiles[i][k - 1].getValue() == 0) {
                    /*如果前方有空位...*/
                            k--;
                        }
                        if (k != from) {
                            if (k > 0 && lastMergeY != k - 1 && tiles[i][from].getValue() == tiles[i][k - 1].getValue()) {
                                move(tiles[i][from], tiles[i][k - 1]);
                                merge(tiles[i][from], tiles[i][k - 1]);
                                isMerged = true;
                                lastMergeY = k - 1;
                            } else {
                                move(tiles[i][from], tiles[i][k]);
                                isMove = true;
                            }
                        } else {
                            if (k > 0 && lastMergeY != k - 1 && tiles[i][from].getValue() == tiles[i][k - 1].getValue()) {
                                move(tiles[i][from], tiles[i][k - 1]);
                                merge(tiles[i][from], tiles[i][k - 1]);
                                isMerged = true;
                                lastMergeY = k - 1;
                            }
                        }
                    }
                }
            }
            /*处理数据并播放动画*/
            playAnimate();
        }
    }

    public synchronized void doRight() {
        if (!end) {
            isMove = false;
            isMerged = false;
            for (int i = 0, len = tiles.length; i < len; i++) {

                int lastMergeY = -1;
                for (int from = len - 2; from >= 0; from--) {
                    int k = from;
                    if (tiles[i][from].getValue() != 0) {
                        while (k < len - 1 && tiles[i][k + 1].getValue() == 0) {
                    /*如果前方有空位...*/
                            k++;
                        }
                        if (k != from) {
                            if (k < len - 1 && lastMergeY != k + 1 && tiles[i][from].getValue() == tiles[i][k + 1].getValue()) {
                                move(tiles[i][from], tiles[i][k + 1]);
                                merge(tiles[i][from], tiles[i][k + 1]);
                                isMerged = true;
                                lastMergeY = k + 1;
                            } else {
                                move(tiles[i][from], tiles[i][k]);
                                isMove = true;
                            }
                        } else {
                            if (k < len - 1 && lastMergeY != k + 1 && tiles[i][from].getValue() == tiles[i][k + 1].getValue()) {
                                move(tiles[i][from], tiles[i][k + 1]);
                                merge(tiles[i][from], tiles[i][k + 1]);
                                isMerged = true;
                                lastMergeY = k + 1;
                            }
                        }
                    }
                }
            }
        /*处理数据并播放动画*/
            playAnimate();
        }
    }

    private void playAnimate() {
        try {
             /*处理数据*/
            AtomicInteger order = new AtomicInteger();//0
            if (isMove || isMerged) {
                SwingUtilities.invokeAndWait(() -> {
                    try {
                        gameView.doMoveAnimate(moveAnimateUnits,order.addAndGet(1));
//                        for (AnimateUnit unit : moveAnimateUnits) {
//                            Tile from = unit.getFrom();
//                            Tile to = unit.getTo();
//                            to.setValue(from.getValue());
//                            from.setValue(0);
//                            gameView.updateValue(from,order.addAndGet(1));
//                            gameView.updateValue(to,order.addAndGet(1));
//                        }
                        moveAnimateUnits.clear();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }

            if (isMerged) {
                SwingUtilities.invokeAndWait(() -> {
                    try {
                        int v = gameView.doMergeAnimate(mergeAnimateUnits,order.addAndGet(1));
                        score += mergeAnimateUnits.size();
                        infoView.setScore(score);
                        if (v > max) {
                            max = v;
                            infoView.setMax(v);
                        }
                        mergeAnimateUnits.clear();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }

            if (isMerged || isMove) {
                createTileAndUpdate();
                System.out.println(order.get());
            }
            judgeDeath();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断死亡
     */
    private void judgeDeath() throws RemoteException {
        boolean dead = true;
        for (int i = 0, len = tiles.length; i < len && dead; i++) {
            for (int j = 0; j < len && dead; j++) {
                if (tiles[i][j].getValue() != 0) {
                    if (dead && i > 0) {
                        dead = tiles[i - 1][j].getValue() != tiles[i][j].getValue();
                    }
                    if (dead && i < len - 1) {
                        dead = tiles[i + 1][j].getValue() != tiles[i][j].getValue();
                    }
                    if (dead && j > 0) {
                        dead = tiles[i][j - 1].getValue() != tiles[i][j].getValue();
                    }
                    if (dead && j < len - 1) {
                        dead = tiles[i][j + 1].getValue() != tiles[i][j].getValue();
                    }
                } else dead = false;
            }
        }
        if (dead) {
            end = true;
            SwingUtilities.invokeLater(()->{
                try {
                    gameView.gameOver();
                    ctlView.end();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        } else if (max == WIN_NUM) {
            end = true;
            SwingUtilities.invokeLater(()->{
                try {
                    gameView.win();
                    ctlView.end();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
        if(score > maxScore){
            saveScore();
        }
    }



    /**
     * 移动
     *
     * @param from
     * @param to
     */
    private void move(Tile from, Tile to) {
        moveAnimateUnits.add(createUnit(from, to));
        to.setValue(from.getValue());
        from.setValue(0);
    }

    /**
     * 合并两个瓦片
     *
     * @param from
     * @param to
     */
    private void merge(Tile from, Tile to) {
        mergeAnimateUnits.add(createUnit(from, to));
        to.setValue(to.getValue() << 1);
        from.setValue(0);
    }

    private AnimateUnit createUnit(Tile from, Tile to) {
        AnimateUnit unit = null;
        try {
            unit =  new AnimateUnit(from.clone(), to.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return unit;
    }

    @Override
    public void setGameView(IGameView view) {
        this.gameView = view;
    }

    @Override
    public void setInfoView(IInfoView view) {
        this.infoView = view;
    }

    @Override
    public void setCtlView(IControlView ctlView) {
        this.ctlView = ctlView;
    }

    @Override
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * 打印瓦片的值和坐标，debug用
     */
    private void printTiles() {
        for (Tile[] ts : tiles) {
            System.out.println(Arrays.toString(ts));
        }
    }
}
