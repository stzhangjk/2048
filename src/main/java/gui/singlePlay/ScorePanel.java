package gui.singlePlay;

import util.ColorSet;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by STZHANGJK on 2017.2.14.
 */
public class ScorePanel extends JPanel {

    private JLabel title;
    private JLabel score;

    public ScorePanel(String text, String initScore) {

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

    public void setText(String text){
        score.setText(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(ColorSet.MENU_BACKGROUND);
        g.fillRect(0,0,getWidth(),getHeight());
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20);
        g.setClip(rect);
        super.paintComponent(g);
    }
}