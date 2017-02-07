package gui;

import game.interfaces.view.IMenuView;
import gui.playPanel.GamePanel;

import javax.swing.*;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public class MenuPanel extends JPanel implements IMenuView{

    private JButton singlePlay;
    private JButton multiPlay;
    private JButton option;

    public MenuPanel() {
        singlePlay = new JButton("单机游戏");
        singlePlay.addActionListener(e->{
            new Thread(()->{
                SwingUtilities.invokeLater(()->{
                    GameContext.getEngine().start();
                    GameContext.getMainFrame().showView(MainFrame.PLAY_PANEL_NAME);
                    ((GamePanel)GameContext.getGameView()).requestFocus();
                });
            }).start();
        });
        multiPlay = new JButton("双人联机");
        option = new JButton("设置");
        option.addActionListener(e->{
            SwingUtilities.invokeLater(()->GameContext.getMainFrame().showView(MainFrame.OPTION_PANEL_NAME));
        });
        add(singlePlay);
        add(multiPlay);
        add(option);

        GameContext.setMenuPanel(this);
    }
}

