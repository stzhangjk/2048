package game.interfaces.view;


import game.interfaces.game.IGameEngine;

/**
 * Created by STZHANGJK on 2017.2.6.
 */
public interface IControlView {
    /**
     * 设置分数
     */
    void setScore(int score);

    /**
     * 注入引擎
     * @param engine
     */
    void setEngine(IGameEngine engine);
}
