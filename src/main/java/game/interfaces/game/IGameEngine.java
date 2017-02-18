package game.interfaces.game;

import entity.Tile;
import game.interfaces.view.IControlView;
import game.interfaces.view.IInfoView;
import game.interfaces.view.IGameView;

/**
 * Created by STZHANGJK on 2017.2.10.
 */
public interface IGameEngine {
    void initGame();
    void initForRemote(Tile[][] tiles);
    void start();
    void restart();
    void doUp();
    void doDown();
    void doLeft();
    void doRight();
    Tile[][] getTiles();

    void setGameView(IGameView view);
    void setInfoView(IInfoView view);
    void setCtlView(IControlView view);
    int getMaxScore();
}
