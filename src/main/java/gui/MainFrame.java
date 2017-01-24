package gui;

import game.GameEngine;
import game.interfaces.view.IGameView;
import game.interfaces.view.IMainView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Created by STZHANGJK on 2016.11.24.
 */
public class MainFrame extends JFrame implements IMainView{

    private JPanel optionPanel;
    private GamePanel gamePanel;
    private JButton start;
    private CardLayout cardLayout;
    private GameEngine engine;

    public MainFrame() {
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        /*创建配置面板*/
        optionPanel = new JPanel();
        optionPanel.setLayout(new FlowLayout());
        start = new JButton("开始");
        start.addActionListener(e-> {
            /*创建游戏面板*/
            engine = new GameEngine(MainFrame.this);
            gamePanel = new GamePanel(engine);
            MainFrame.this.getContentPane().add(gamePanel);
            engine.start();
        });

        optionPanel.add(start);

        /*设置卡片布局*/
        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);
        getContentPane().add(optionPanel);

        engine = new GameEngine(MainFrame.this);
        gamePanel = new GamePanel(engine);
        MainFrame.this.getContentPane().add(gamePanel);
        engine.start();
        System.out.println("main init");
    }

    @Override
    public void gotoGameView() {
        cardLayout.next(getContentPane());
    }

    @Override
    public int getMapSize() {
        return 4;
    }

    @Override
    public IGameView getGameView() {
        return gamePanel;
    }
}
