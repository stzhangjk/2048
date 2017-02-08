package game.interfaces.view;

import game.GameEngine;

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
    void setEngine(GameEngine engine);
}
