package gui.singlePlay;

import javax.swing.*;
import java.awt.*;

/**
 * Created by STZHANGJK on 2017.1.25.
 */
public class SinglePlayPanel extends JPanel {

    private GamePanel gameView;
    private SingleInfoPanel infoView;

    public SinglePlayPanel() {
        gameView = new GamePanel();
        infoView = new SingleInfoPanel(gameView);
        setLayout(new BorderLayout());
        add(infoView,BorderLayout.NORTH);
        add(gameView,BorderLayout.CENTER);
    }

    public GamePanel getGameView() {
        return gameView;
    }

    public SingleInfoPanel getInfoView() {
        return infoView;
    }
}
