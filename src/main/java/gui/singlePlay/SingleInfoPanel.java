package gui.singlePlay;

import game.interfaces.game.IGameEngine;
import game.interfaces.view.IInfoView;
import gui.GameContext;
import gui.MainFrame;
import util.ColorSet;

import javax.swing.*;
import java.awt.*;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public class SingleInfoPanel extends JPanel implements IInfoView {

    private JPanel upPanel;
    private JPanel downPanel;
    private GamePanel gamePanel;
    private ScorePanel cur;
    private ScorePanel best;
    private IGameEngine engine;

    public SingleInfoPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setBackground(ColorSet.MENU_BACKGROUND);

        //-------------//
        /*构造上部分*/
        upPanel = new JPanel();
        upPanel.setBackground(ColorSet.MENU_BACKGROUND);
        //标题
        JLabel titleLbl = new JLabel("2048");
        titleLbl.setFont(new Font("微软雅黑",Font.BOLD,45));
        titleLbl.setForeground(new Color(0x776E64));
        //分数
        cur = new ScorePanel("SCORE","0");
        best = new ScorePanel("BEST","0");
        /**/
        upPanel.setLayout(new BoxLayout(upPanel,BoxLayout.X_AXIS));
        upPanel.add(Box.createHorizontalStrut(10));
        upPanel.add(titleLbl);
        upPanel.add(Box.createHorizontalGlue());
        upPanel.add(cur);
        upPanel.add(Box.createHorizontalStrut(10));
        upPanel.add(best);
        upPanel.add(Box.createHorizontalStrut(10));
        //-------------//

        //-------------//
        /*构造下部分*/
        downPanel = new JPanel();
        JLabel tips = new JLabel("Join the numbers and get to the 2048 tile!");
        tips.setFont(new Font("微软雅黑",Font.PLAIN,15));
        tips.setForeground(new Color(0x776E64));
        JButton back = new CtlButton("结束游戏");
        back.addActionListener(e->{
            SwingUtilities.invokeLater(()->{
                GameContext.getMainFrame().showView(MainFrame.MENU_PANEL_NAME);
            });
        });
        JButton restart = new CtlButton("重新开始");
        restart.addActionListener(e->{
            engine.restart();
            SingleInfoPanel.this.gamePanel.repaint();
            SingleInfoPanel.this.gamePanel.requestFocus();
        });
        downPanel.setLayout(new BoxLayout(downPanel,BoxLayout.X_AXIS));
        downPanel.add(Box.createHorizontalStrut(10));
        downPanel.add(tips);
        downPanel.add(Box.createHorizontalGlue());
        downPanel.add(back);
        downPanel.add(Box.createHorizontalStrut(10));
        downPanel.add(restart);
        downPanel.add(Box.createHorizontalStrut(10));
        downPanel.setBackground(ColorSet.MENU_BACKGROUND);
        //-------------//


        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(10));
        add(Box.createVerticalGlue());
        add(upPanel);
        add(Box.createVerticalStrut(10));
        add(downPanel);
        add(Box.createVerticalGlue());
        add(Box.createVerticalStrut(10));
    }

    /**
     * 设置分数
     *
     * @param score
     */
    @Override
    public void setScore(int score) {
        cur.setText(String.valueOf(score));
    }

    /**
     * 注入引擎
     *
     * @param engine
     */
    @Override
    public void setEngine(IGameEngine engine) {
        this.engine = engine;
    }

    @Override
    public void setMax(int value) {

    }

    private class CtlButton extends JButton{
        CtlButton(String text) {
            super(text);
            setBorderPainted(false);
            setContentAreaFilled(false);//透明
            setMargin(new Insets(0,0,0,0));
            setFont(new Font("微软雅黑",Font.PLAIN,15));
        }
    }
}
