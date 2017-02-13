package gui;

import game.GameEngine;
import game.interfaces.game.IGameEngine;
import game.interfaces.view.IMenuView;
import gui.multiPlay.ConnectPanel;
import gui.singlePlay.GamePanel;
import gui.singlePlay.SinglePlayPanel;
import util.ColorSet;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.io.IOException;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public class MenuPanel extends JPanel implements IMenuView{

    private Image bg;
    private JLabel title;
    private JButton singlePlay;
    private JButton multiPlay;
    private JButton option;

    public MenuPanel() {


        title = new JLabel("2048联机版");
        title.setFont(new Font("微软雅黑",Font.PLAIN,60));
        title.setForeground(ColorSet.getTextColor(4));
        Box titleBox = Box.createHorizontalBox();
        titleBox.add(Box.createHorizontalGlue());
        //titleBox.add(Box.createHorizontalStrut(10));
        titleBox.add(title);
        titleBox.add(Box.createHorizontalGlue());


        singlePlay = new MenuButton("单机游戏");
        singlePlay.addActionListener(e->{
            new Thread(()->{
                SwingUtilities.invokeLater(()->{
                    SinglePlayPanel playPanel = new SinglePlayPanel();
                    GamePanel gamePanel = playPanel.getGamePanel();
                    IGameEngine engine = new GameEngine();
                    playPanel.getInfoPanel().setEngine(engine);
                    gamePanel.setEngine(engine);
                    engine.setControlView(playPanel.getInfoPanel());
                    engine.setGameView(gamePanel);
                    engine.start();
                    MainFrame frame = GameContext.getMainFrame();
                    frame.getContentPane().add(playPanel,MainFrame.SINGLE_PLAY_PANEL_NAME);
                    frame.showView(MainFrame.SINGLE_PLAY_PANEL_NAME);
                    gamePanel.requestFocus();
                });
            }).start();
        });
        Box singleBox = Box.createHorizontalBox();
        singleBox.add(Box.createHorizontalGlue());
        //singleBox.add(Box.createHorizontalStrut(10));
        singleBox.add(singlePlay);
        singleBox.add(Box.createHorizontalGlue());


        multiPlay = new MenuButton("双人联机");
        multiPlay.addActionListener(e->{
            SwingUtilities.invokeLater(()->{
                ConnectPanel connectPanel = new ConnectPanel();
                GameContext.getMainFrame().getContentPane().add(connectPanel,MainFrame.CONN_PANEL);
                GameContext.getMainFrame().showView(MainFrame.CONN_PANEL);
            });
        });
        Box multiBox = Box.createHorizontalBox();
        multiBox.add(Box.createHorizontalGlue());
        //multiBox.add(Box.createHorizontalStrut(10));
        multiBox.add(multiPlay);
        multiBox.add(Box.createHorizontalGlue());


        option = new MenuButton("设置");
        option.addActionListener(e->{
            SwingUtilities.invokeLater(()->GameContext.getMainFrame().showView(MainFrame.OPTION_PANEL_NAME));
        });
        Box optionBox = Box.createHorizontalBox();
        optionBox.add(Box.createHorizontalGlue());
        //optionBox.add(Box.createHorizontalStrut(10));
        optionBox.add(option);
        optionBox.add(Box.createHorizontalGlue());



        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        add(titleBox);
        add(Box.createVerticalStrut(10));
        add(singleBox);
        add(Box.createVerticalStrut(10));
        add(multiBox);
        add(Box.createVerticalStrut(10));
        add(optionBox);
        add(Box.createVerticalGlue());

        setBackground(ColorSet.MENU_BACKGROUND);
        try{
            bg = ImageIO.read(ClassLoader.getSystemResourceAsStream("BG.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, getWidth() - bg.getWidth(this), getHeight() - bg.getHeight(this), this);
    }

    private class MenuButton extends JButton{
        MenuButton(String text) {
            super(text);
            setFont(new Font("微软雅黑",Font.PLAIN,28));
            setUI(new BasicButtonUI());
            setContentAreaFilled(false);
            setMargin(new Insets(0, 0, 0, 0));
            setBorderPainted(false);
            setForeground(ColorSet.getTextColor(4));
        }
    }
}

