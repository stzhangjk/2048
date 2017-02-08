package game.interfaces.view;

import entity.Tile;
import game.GameEngine;
import gui.animate.AnimateUnit;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public interface IGameView {
    /**
     * 初始化界面
     */
    void init(Tile[][] tiles);

    /**
     * 播放移动动画
     * @param animateUnits
     */
    void doMoveAnimate(List<AnimateUnit> animateUnits);

    /**
     * 合并
     */
    void doMergeAnimate(List<AnimateUnit> animateUnits);

    /**
     * 更新一个瓦片的值
     */
    void updateValue(Tile tile);

    /**
     * 死了
     */
    void gameOver();

    /**
     * 注入游戏引擎
     */
    void setEngine(GameEngine engine);

}
