package game.interfaces.view;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public interface IMainView {
    /**
     * 跳转到游戏线程
     */
    void gotoGameView();

    /**
     * 获取游戏规模
     * @return 规模，最小为4
     */
    int getMapSize();

    /**
     * 获取游戏界面
     * @return
     */
    IGameView getGameView();
}
