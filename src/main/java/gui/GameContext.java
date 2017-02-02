package gui;

import game.GameEngine;
import game.interfaces.view.IGameView;

/**
 * Created by STZHANGJK on 2017.1.26.
 */
public class GameContext {
    private static MainFrame mainFrame;
    private static MenuPanel menuPanel;
    private static OptionPanel optionPanel;
    private static IGameView gameView;
    private static GameEngine engine;

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        GameContext.mainFrame = mainFrame;
    }

    public static MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public static void setMenuPanel(MenuPanel menuPanel) {
        GameContext.menuPanel = menuPanel;
    }

    public static OptionPanel getOptionPanel() {
        return optionPanel;
    }

    public static void setOptionPanel(OptionPanel optionPanel) {
        GameContext.optionPanel = optionPanel;
    }

    public static IGameView getGameView() {
        return gameView;
    }

    public static void setGamePanel(IGameView gameView) {
        GameContext.gameView = gameView;
    }

    public static GameEngine getEngine() {
        return engine;
    }

    public static void setEngine(GameEngine engine) {
        GameContext.engine = engine;
    }
}
