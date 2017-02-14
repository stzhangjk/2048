package gui.multiPlay;

import gui.singlePlay.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class MultiPlayPanel extends JPanel{

    private MultiInfoPanel infoPanel;
    private InfoPanel lInfoView;
    private InfoPanel rInfoView;
    private GamePanel lGameView;
    private GamePanel rGameView;

    public MultiPlayPanel() {

        lGameView = new GamePanel();
        lGameView.setFocusable(true);
        rGameView = new GamePanel();
        rGameView.setFocusable(false);

        infoPanel = new MultiInfoPanel();
        lInfoView = new InfoPanel();
        rInfoView = new InfoPanel();

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(lInfoView,gbc);
        add(lInfoView);

        gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        layout.setConstraints(rInfoView,gbc);
        add(rInfoView);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.gridheight = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        layout.setConstraints(lGameView,gbc);
        add(lGameView);

        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.gridheight = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        layout.setConstraints(rGameView,gbc);
        add(rGameView);
    }

    public GamePanel getlGameView() {
        return lGameView;
    }

    public GamePanel getrGameView() {
        return rGameView;
    }

    public InfoPanel getLInfoView() {
        return lInfoView;
    }

    public InfoPanel getRInfoView() {
        return rInfoView;
    }
}
