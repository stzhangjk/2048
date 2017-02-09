package gui;

import javax.swing.*;
import java.awt.*;


/**
 * Created by STZHANGJK on 2016.11.24.
 */
public class MainFrame extends JFrame{

    public static String MENU_PANEL_NAME = "menu";
    public static String OPTION_PANEL_NAME = "option";
    public static String SINGLE_PLAY_PANEL_NAME = "singlePlay";
    public static String MULTI_PLAY_PANEL_NAME = "multiPlay";
    public static String CONN_PANEL = "conn";


    private MenuPanel menuPanel;
    private OptionPanel optionPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setSize(600,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        /*创建菜单面板*/
        menuPanel = new MenuPanel();
        /*创建配置面板*/
        optionPanel = new OptionPanel();

        /*设置卡片布局*/
        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);
        getContentPane().add(menuPanel,MENU_PANEL_NAME);
        getContentPane().add(optionPanel,OPTION_PANEL_NAME);

        GameContext.setMainFrame(this);
    }

    public void showView(String name){
        cardLayout.show(getContentPane(),name);
    }

}
