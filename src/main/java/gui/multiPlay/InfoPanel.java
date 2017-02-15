package gui.multiPlay;

import game.interfaces.game.IGameEngine;
import game.interfaces.view.IInfoView;
import gui.singlePlay.ScorePanel;
import util.ColorSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by STZHANGJK on 2017.2.14.
 */
public class InfoPanel extends JPanel implements IInfoView {

    private JLabel value;
    private JProgressBar valueBar;
    private ScorePanel score;

    public InfoPanel() {
        setBackground(ColorSet.MENU_BACKGROUND);
        Font font = new Font("微软雅黑",Font.PLAIN,20);
        valueBar = new JProgressBar(2,2048);
        valueBar.setForeground(ColorSet.getBGColor(2));
        valueBar.setBackground(ColorSet.BACKGROUND);
        valueBar.setBorderPainted(false);
        value = new JLabel("2");
        value.setFont(font);
        value.setForeground(ColorSet.getTextColor(2));
        value.setHorizontalAlignment(SwingConstants.CENTER);
        value.setVerticalAlignment(SwingConstants.CENTER);

        JLayeredPane valuePane = new JLayeredPane(){
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(ColorSet.MENU_BACKGROUND);
                g.drawRect(0,0,getWidth(),getHeight());
                RoundRectangle2D.Double rect = new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20);
                g.setClip(rect);
                super.paintComponent(g);
            }
        };
        valuePane.add(valueBar,new Integer(200));
        valuePane.add(value,new Integer(300));
        valuePane.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                value.setBounds(0, 0, valuePane.getWidth(), valuePane.getHeight());
                valueBar.setBounds(0, 0, valuePane.getWidth(), valuePane.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });


        score = new ScorePanel("SCORE","0");
        setLayout(new BorderLayout(5,0));

        add(valuePane,BorderLayout.CENTER);
        add(score,BorderLayout.EAST);
    }

    @Override
    public void setScore(int score) {
        SwingUtilities.invokeLater(()->{
            this.score.setText(String.valueOf(score));
        });
    }

    @Override
    public void setEngine(IGameEngine engine) {

    }

    @Override
    public void setMax(int value) {
        SwingUtilities.invokeLater(()->{
            valueBar.setValue(value);
            this.value.setText(String.valueOf(value));
            this.value.setForeground(ColorSet.getTextColor(value));
            valueBar.setForeground(ColorSet.getBGColor(value));
        });
    }
}
