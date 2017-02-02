package game;

import entity.Tile;
import game.interfaces.view.IGameView;
import game.interfaces.view.IMainView;
import gui.GameContext;
import gui.animate.AnimateUnit;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by STZHANGJK on 2017.1.21.
 * 游戏线程
 */
public class GameEngine {

    /**瓦片*/
    private Tile[][] tiles;
    /**
     * 游戏界面
     */
    private IGameView gameView;
    /**
     * 移动动作单元列表
     */
    private List<AnimateUnit> moveAnimateUnits;
    /**
     * 合并动作单元列表
     */
    private List<AnimateUnit> mergeAnimateUnits;
    /**
     * 主界面
     */
    private IMainView mainView;
    private boolean isMove;
    private boolean isMerged;

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
    private void initGame() {
        moveAnimateUnits = new ArrayList<>();
        mergeAnimateUnits = new ArrayList<>();
        initTiles(4);
        gameView.init(tiles);
        /*生成两个2*/
        createTile();
        createTile();
    }

    /**
     * 开始游戏
     */
    public void start() {
        gameView = GameContext.getGameView();
        initGame();
    }

    /**
     * 生成新的数字
     */
    private void createTile() {
        List<Tile> zero = new ArrayList<>();

        for (int i = 0, len = tiles.length; i < len; i++) {
            for (int j = 0; j < len; j++)
                if (tiles[i][j].getValue() == 0)
                    zero.add(tiles[i][j]);
        }

        if (!zero.isEmpty()) {
            Random rand = new Random();
            int index = rand.nextInt(zero.size());
            Tile newTile = zero.get(index);
            newTile.setValue(2);
            gameView.updateValue(newTile);
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public synchronized void doUp() {
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
                        }else{
                            move(tiles[from][j], tiles[k][j]);
                            isMove = true;
                        }
                    }else{
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
        /*处理数据*/
        playAnimate();
    }

    public synchronized void doDown() {
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
                        }else{
                            move(tiles[from][j], tiles[k][j]);
                            isMove = true;
                        }
                    }else{
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
        /*处理数据*/
        playAnimate();
    }

    public synchronized void doLeft() {
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
                        }else {
                            move(tiles[i][from], tiles[i][k]);
                            isMove = true;
                        }
                    }else {
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
        /*处理数据*/
        playAnimate();
    }

    public synchronized void doRight() {
        isMove = false;
        isMerged = false;
        for (int i = 0, len = tiles.length; i < len; i++) {

            int lastMergeY = -1;
            for (int from = len - 2; from >= 0; from--) {
                int k = from;
                if (tiles[i][from].getValue() != 0) {
                    while (k < len-1 && tiles[i][k + 1].getValue() == 0) {
                    /*如果前方有空位...*/
                        k++;
                    }
                    if (k != from) {
                        if (k < len-1 && lastMergeY != k + 1 && tiles[i][from].getValue() == tiles[i][k + 1].getValue()) {
                            move(tiles[i][from], tiles[i][k + 1]);
                            merge(tiles[i][from], tiles[i][k + 1]);
                            isMerged = true;
                            lastMergeY = k + 1;
                        }else {
                            move(tiles[i][from], tiles[i][k]);
                            isMove = true;
                        }
                    }else {
                        if (k < len-1 && lastMergeY != k + 1 && tiles[i][from].getValue() == tiles[i][k + 1].getValue()) {
                            move(tiles[i][from], tiles[i][k + 1]);
                            merge(tiles[i][from], tiles[i][k + 1]);
                            isMerged = true;
                            lastMergeY = k + 1;
                        }
                    }
                }
            }
        }
        /*处理数据*/
        playAnimate();
    }

    private void playAnimate(){
        try {
             /*处理数据*/
            if(isMove || isMerged){
                SwingUtilities.invokeAndWait(()->{
                    gameView.doMoveAnimate(moveAnimateUnits);
                    for(AnimateUnit unit : moveAnimateUnits){
                        Tile from = unit.getFrom();
                        Tile to = unit.getTo();
                        to.setValue(from.getValue());
                        from.setValue(0);
                        gameView.updateValue(from);
                        gameView.updateValue(to);
                    }
                    moveAnimateUnits.clear();
                });
            }

            if(isMerged){
                SwingUtilities.invokeAndWait(()->{
                    gameView.doMergeAnimate(mergeAnimateUnits);
                    mergeAnimateUnits.clear();
                });
            }

            if (isMerged || isMove) {
                createTile();
            }
            judgeDeath();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断死亡
     */
    private void judgeDeath(){
        boolean dead = true;
        for(int i=0,len = tiles.length;i<len && dead;i++){
            for(int j=0;j<len && dead;j++){
                if(tiles[i][j].getValue() != 0){
                    if(dead && i > 0){
                        dead = tiles[i-1][j].getValue() != tiles[i][j].getValue();
                    }
                    if(dead && i < len-1){
                        dead = tiles[i+1][j].getValue() != tiles[i][j].getValue();
                    }
                    if(dead && j > 0){
                        dead = tiles[i][j-1].getValue() != tiles[i][j].getValue();
                    }
                    if(dead && j < len-1){
                        dead = tiles[i][j+1].getValue() != tiles[i][j].getValue();
                    }
                }else dead = false;
            }
        }
        if(dead){
            gameView.gameOver();
        }
    }

    /**
     * 移动
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

    private AnimateUnit createUnit(Tile from, Tile to){
        Tile f = new Tile(from.getI(),from.getJ());
        f.setValue(from.getValue());
        Tile t = new Tile(to.getI(),to.getJ());
        t.setValue(to.getValue());
        return new AnimateUnit(f,t);
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
