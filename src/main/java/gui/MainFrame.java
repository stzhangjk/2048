package gui;

import game.GameEngine;
import gui.playPanel.PlayPanel;

import javax.swing.*;
import java.awt.*;


/**
 * Created by STZHANGJK on 2016.11.24.
 */
public class MainFrame extends JFrame{

    public static String MENU_PANEL_NAME = "menu";
    public static String OPTION_PANEL_NAME = "option";
    public static String PLAY_PANEL_NAME = "play";


    private MenuPanel menuPanel;
    private OptionPanel optionPanel;
    private PlayPanel playPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setSize(600,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        /*创建菜单面板*/
        menuPanel = new MenuPanel();
        /*创建配置面板*/
        optionPanel = new OptionPanel();
        GameEngine engine = new GameEngine();
        GameContext.setEngine(engine);
        /*创建游戏面板*/
        playPanel = new PlayPanel();
        /*设置卡片布局*/
        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);

        getContentPane().add(menuPanel,MENU_PANEL_NAME);
        getContentPane().add(optionPanel,OPTION_PANEL_NAME);
        getContentPane().add(playPanel,PLAY_PANEL_NAME);

        GameContext.setMainFrame(this);
    }

    public void showView(String name){
        cardLayout.show(getContentPane(),name);
    }
}
