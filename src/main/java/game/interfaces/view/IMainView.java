package game.interfaces.view;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public interface IMainView {
    /**
     * 跳转到游戏界面
     */
    void gotoGameView();

    /**
     * 获取游戏界面
     * @return
     */
    IGameView getGameView();

    /**
     * 获取游戏配置界面
     */
    IOptionView getOptionView();

    /**
     * 获取菜单界面
     */
    IMenuView getMenuView();
}
