package game.interfaces.view;

import entity.Tile;

import java.awt.*;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public interface IGameView {
    /**
     * 初始化界面
     */
    void init(Tile[][] tiles);

    /**
     * 瓦片移动
     * @param from 从哪
     * @param to 到哪
     */
    void move(Tile from,Tile to);

    /**
     * 更新一个瓦片的值
     */
    void updateValue(Tile tile);

    /**
     * 死了
     */
    void gameOver();
}
