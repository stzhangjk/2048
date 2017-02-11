package game.interfaces.game;

import entity.Tile;
import game.interfaces.view.IControlView;
import game.interfaces.view.IGameView;

/**
 * Created by STZHANGJK on 2017.2.10.
 */
public interface IGameEngine {
    void start();
    void restart();
    void doUp();
    void doDown();
    void doLeft();
    void doRight();
    void initForRemote(Tile[][] tiles);
    Tile[][] getTiles();

    void setGameView(IGameView view);
    void setControlView(IControlView view);
}
