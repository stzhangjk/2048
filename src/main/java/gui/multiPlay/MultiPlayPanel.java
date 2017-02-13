package gui.multiPlay;

import game.interfaces.game.IGameEngine;
import gui.singlePlay.GamePanel;
import gui.singlePlay.SingleInfoPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class MultiPlayPanel extends JPanel{

    private MultiInfoPanel infoPanel;
    private SingleInfoPanel localIJP;
    private SingleInfoPanel remoteIJP;
    private GamePanel localGamePanel;
    private GamePanel remoteGamePanel;

    public MultiPlayPanel() {
        infoPanel = new MultiInfoPanel();

        localGamePanel = new GamePanel();
        localGamePanel.setFocusable(true);
        remoteGamePanel = new GamePanel();
        remoteGamePanel.setFocusable(false);

        localIJP = new SingleInfoPanel(localGamePanel);
        remoteIJP = new SingleInfoPanel(remoteGamePanel);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(1,2,10,10));
        gamePanel.add(localGamePanel);
        gamePanel.add(remoteGamePanel);

        setLayout(new BorderLayout(0,0));
        add(infoPanel,BorderLayout.NORTH);
        add(gamePanel,BorderLayout.CENTER);


    }

    public void setEngine(IGameEngine localEngine){
        localGamePanel.setEngine(localEngine);
        localIJP.setEngine(localEngine);
    }

    public GamePanel getLocalGamePanel() {
        return localGamePanel;
    }

    public void setLocalGamePanel(GamePanel localGamePanel) {
        this.localGamePanel = localGamePanel;
    }

    public GamePanel getRemoteGamePanel() {
        return remoteGamePanel;
    }

    public SingleInfoPanel getLocalIJP() {
        return localIJP;
    }

    public void setLocalIJP(SingleInfoPanel localIJP) {
        this.localIJP = localIJP;
    }
}
