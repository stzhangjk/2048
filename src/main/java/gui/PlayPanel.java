package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public class PlayPanel extends JPanel {

    private GamePanel gamePanel;
    private InfoPanel infoPanel;

    public PlayPanel() {
        infoPanel = new InfoPanel();
        gamePanel = new GamePanel();
        setLayout(new BorderLayout());
        add(infoPanel,BorderLayout.NORTH);
        add(gamePanel,BorderLayout.CENTER);
        GameContext.setGamePanel(gamePanel);
    }

}
