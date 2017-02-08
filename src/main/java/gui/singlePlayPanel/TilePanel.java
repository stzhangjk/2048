package gui.singlePlayPanel;

import util.ColorSet;

import javax.swing.*;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public class TilePanel extends JPanel {

    /**圆角的半径*/
    public static final int ARC = 20;
    private JLabel value;
    private BoxLayout boxLayout;
    private JPanel middleRow;

    public TilePanel(int v) {
        value = new JLabel(String.valueOf(v),SwingUtilities.CENTER);
        value.setFont(new Font("微软雅黑",Font.BOLD,40));

        middleRow = new JPanel();
        BoxLayout mdBoxLayout = new BoxLayout(middleRow,BoxLayout.X_AXIS);
        middleRow.setLayout(mdBoxLayout);
        middleRow.add(Box.createHorizontalGlue());
        middleRow.add(value);
        middleRow.add(Box.createHorizontalGlue());

        boxLayout = new BoxLayout(this,BoxLayout.Y_AXIS);
        setLayout(boxLayout);
        add(Box.createVerticalGlue());
        add(middleRow);
        add(Box.createVerticalGlue());

        updateValue(v);
    }

    public void updateValue(int value){
        this.value.setText(String.valueOf(value));
        this.value.setForeground(ColorSet.getTextColor(value));
        Color color = ColorSet.getBGColor(value);
        setBackground(color);
        middleRow.setBackground(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(ColorSet.BACKGROUND);
        g.fillRect(0,0,getWidth(),getHeight());
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double(0,0,getWidth(),getHeight(),20,20);
        g.setClip(rect);
        super.paintComponent(g);
    }
}
