package game;

import entity.Tile;
import game.interfaces.view.IGameView;
import game.interfaces.view.IMainView;
import gui.GamePanel;

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
     * 主界面
     */
    private IMainView mainView;

    public GameEngine(IMainView mainView) {
        this.mainView = mainView;
    }

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
        initTiles(mainView.getMapSize());
        gameView = mainView.getGameView();
        gameView.init(tiles);
        /*生成两个2*/
        createTile();
        createTile();

    }

    /**
     * 开始游戏
     */
    public void start() {
        initGame();
        mainView.gotoGameView();
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

    public void doUp() {
        boolean mergedOrMoved = false;
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
                        swap(tiles[from][j], tiles[k][j]);
                        mergedOrMoved = true;
                    }

                    if (k > 0 && lastMergeX != k - 1 && tiles[k][j].getValue() == tiles[k - 1][j].getValue()) {
                        merge(tiles[k][j], tiles[k - 1][j]);
                        mergedOrMoved = true;
                        lastMergeX = k - 1;
                    }
                }
            }
        }
        if (mergedOrMoved) {
            createTile();
        }
    }

    public void doDown() {
        boolean mergedOrMoved = false;
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
                        swap(tiles[from][j], tiles[k][j]);
                        mergedOrMoved = true;
                    }

                    if (k < len - 1 && lastMergeX != k + 1 && tiles[k][j].getValue() == tiles[k + 1][j].getValue()) {
                        merge(tiles[k][j], tiles[k + 1][j]);
                        mergedOrMoved = true;
                        lastMergeX = k + 1;
                    }
                }
            }
        }
        if (mergedOrMoved) {
            createTile();
        }
    }

    public void doLeft() {
        boolean mergedOrMoved = false;
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
                        swap(tiles[i][from], tiles[i][k]);
                        mergedOrMoved = true;
                    }

                    if (k > 0 && lastMergeY != k - 1 && tiles[i][k].getValue() == tiles[i][k - 1].getValue()) {
                        merge(tiles[i][k], tiles[i][k - 1]);
                        mergedOrMoved = true;

                        lastMergeY = k - 1;

                    }
                }
            }
        }
        if (mergedOrMoved) {
            createTile();
        }
    }

    public void doRight() {
        boolean mergedOrMoved = false;
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
                        swap(tiles[i][from], tiles[i][k]);
                        mergedOrMoved = true;
                    }

                    if (k < len-1 && lastMergeY != k + 1 && tiles[i][k].getValue() == tiles[i][k + 1].getValue()) {
                        merge(tiles[i][k], tiles[i][k + 1]);
                        mergedOrMoved = true;
                        lastMergeY = k + 1;
                    }
                }
            }
        }
        if (mergedOrMoved) {
            createTile();
        }
    }


    private void swap(Tile from, Tile to) {
        gameView.move(from, to);
        int temp = from.getValue();
        from.setValue(to.getValue());
        to.setValue(temp);
    }

    /**
     * 合并两个瓦片
     *
     * @param from
     * @param to
     */
    private void merge(Tile from, Tile to) {
        to.setValue(to.getValue() << 1);
        from.setValue(0);
        gameView.updateValue(from);
        gameView.updateValue(to);
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
