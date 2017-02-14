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
    private GamePanel localGamePanel;
    private GamePanel remoteGamePanel;

    public MultiPlayPanel() {

        localGamePanel = new GamePanel();
        localGamePanel.setFocusable(true);
        remoteGamePanel = new GamePanel();
        remoteGamePanel.setFocusable(false);

        infoPanel = new MultiInfoPanel();
        lInfoView = new InfoPanel();
        rInfoView = new InfoPanel();

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(2,2,10,10));
        gamePanel.add(lInfoView);
        gamePanel.add(rInfoView);
        gamePanel.add(localGamePanel);
        gamePanel.add(remoteGamePanel);

        setLayout(new BorderLayout(0,0));
        add(infoPanel,BorderLayout.NORTH);
        add(gamePanel,BorderLayout.CENTER);
    }

    public GamePanel getLocalGamePanel() {
        return localGamePanel;
    }

    public GamePanel getRemoteGamePanel() {
        return remoteGamePanel;
    }

    public InfoPanel getLInfoView() {
        return lInfoView;
    }

    public InfoPanel getRInfoView() {
        return rInfoView;
    }
}
