package gui;

import game.GameEngine;
import game.interfaces.view.IGameView;
import game.interfaces.view.IControlView;

/**
 * Created by STZHANGJK on 2017.1.26.
 */
public class GameContext {
    private static MainFrame mainFrame;
    private static MenuPanel menuPanel;

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        GameContext.mainFrame = mainFrame;
    }
}
