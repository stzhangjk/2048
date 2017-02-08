package gui;

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
