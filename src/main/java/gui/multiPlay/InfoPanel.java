package gui.multiPlay;

import game.interfaces.game.IGameEngine;
import game.interfaces.view.IInfoView;
import gui.singlePlay.ScorePanel;
import util.ColorSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by STZHANGJK on 2017.2.14.
 */
public class InfoPanel extends JPanel implements IInfoView {

    private JLabel value;
    private JProgressBar valueBar;
    private ScorePanel score;

    public InfoPanel() {
        Font font = new Font("微软雅黑",Font.PLAIN,20);
        valueBar = new JProgressBar(2,2048);
        valueBar.setForeground(ColorSet.getBGColor(2));
        valueBar.setBackground(ColorSet.BACKGROUND);
        value = new JLabel("2");
        value.setFont(font);
        value.setForeground(ColorSet.getTextColor(2));
        value.setHorizontalAlignment(SwingConstants.CENTER);
        value.setVerticalAlignment(SwingConstants.CENTER);

        JLayeredPane valuePane = new JLayeredPane();
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
        setLayout(new BorderLayout());
        add(valuePane,BorderLayout.CENTER);
        add(score,BorderLayout.EAST);
    }

    @Override
    public void setScore(int score) {
        this.score.setText(String.valueOf(score));
    }

    @Override
    public void setEngine(IGameEngine engine) {

    }

    @Override
    public void setMax(int value) {
        System.out.println("max : " + value);
        valueBar.setValue(value);
        this.value.setText(String.valueOf(value));
        this.value.setForeground(ColorSet.getTextColor(value));
        valueBar.setForeground(ColorSet.getBGColor(value));
    }
}
