package gui;

import game.interfaces.view.IOptionView;

import javax.swing.*;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public class OptionPanel extends JPanel {

    private JButton start;

    public OptionPanel() {
        start = new JButton("设置界面test");
        add(start);
    }
}
