package gui.playPanel;

import game.interfaces.view.IScoreView;
import gui.GameContext;
import gui.MainFrame;
import util.ColorSet;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public class InfoPanel extends JPanel{


    private JPanel upPanel;
    private JPanel downPanel;
    private ScorePanel cur;
    private ScorePanel best;

    public InfoPanel() {
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
        GameContext.setScoreView(cur);
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
            GameContext.getEngine().restart();
            ((JPanel)GameContext.getGameView()).repaint();
            ((JPanel)GameContext.getGameView()).requestFocus();
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

    private class ScorePanel extends JPanel implements IScoreView{
        private JLabel title;
        private JLabel score;

        ScorePanel(String text, String initScore) {

            title = new JLabel(text);
            title.setFont(new Font("微软雅黑",Font.BOLD,15));
            title.setForeground(new Color(0xeee4da));
            Box titleBox = Box.createHorizontalBox();
            titleBox.add(Box.createHorizontalStrut(15));
            titleBox.add(title);
            titleBox.add(Box.createHorizontalStrut(15));


            score = new JLabel(initScore);
            score.setFont(new Font("微软雅黑",Font.BOLD,30));
            score.setForeground(Color.WHITE);
            Box scoreBox = Box.createHorizontalBox();
            scoreBox.add(Box.createHorizontalStrut(15));
            scoreBox.add(score);
            scoreBox.add(Box.createHorizontalStrut(15));


            setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            add(Box.createVerticalStrut(5));
            add(Box.createVerticalGlue());
            add(titleBox);
            add(Box.createVerticalStrut(3));
            add(scoreBox);
            add(Box.createVerticalGlue());
            add(Box.createVerticalStrut(5));


            setBackground(ColorSet.BACKGROUND);
            setMaximumSize(new Dimension(200,100));
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(ColorSet.MENU_BACKGROUND);
            g.fillRect(0,0,getWidth(),getHeight());
            RoundRectangle2D.Double rect = new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20);
            g.setClip(rect);
            super.paint(g);
        }

        /**
         * 设置分数
         *
         * @param score
         */
        @Override
        public void setScore(int score) {
            this.score.setText(String.valueOf(score));
        }
    }

    private class CtlButton extends JButton{

        CtlButton(String text) {
            super(text);
            setBackground(ColorSet.BACKGROUND);
            setFont(new Font("微软雅黑",Font.PLAIN,15));
        }
    }
}
