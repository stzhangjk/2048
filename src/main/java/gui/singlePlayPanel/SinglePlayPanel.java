package gui.singlePlayPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public class SinglePlayPanel extends JPanel {

    private GamePanel gamePanel;
    private SingleInfoPanel infoPanel;

    public SinglePlayPanel() {
        gamePanel = new GamePanel();
        infoPanel = new SingleInfoPanel(gamePanel);
        setLayout(new BorderLayout());
        add(infoPanel,BorderLayout.NORTH);
        add(gamePanel,BorderLayout.CENTER);
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public SingleInfoPanel getInfoPanel() {
        return infoPanel;
    }
}
