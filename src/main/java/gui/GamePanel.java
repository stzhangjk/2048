package gui;

import entity.Tile;
import game.GameEngine;
import game.interfaces.view.IGameView;
import util.ColorSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by STZHANGJK on 2016.11.24.
 */
public class GamePanel extends JPanel implements IGameView{

    private GameEngine engine;
    private TilePanel[][] tilePanels;
    private GridLayout layout;

    public GamePanel(GameEngine engine) {
        this.engine = engine;
        setBackground(ColorSet.BACKGROUND);
        setFocusable(true);
    }

    @Override
    public void init(Tile[][] tiles) {
        tilePanels = new TilePanel[tiles.length][tiles.length];
        layout = new GridLayout(tiles.length,tiles.length,10,10);
        setLayout(layout);
        for(int i=0,len = tilePanels.length,k=0;i<len;i++){
            for(int j=0;j<len;j++,k++){
                tilePanels[i][j] = new TilePanel(tiles[i][j].getValue());
                this.add(tilePanels[i][j]);
            }
        }

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        engine.doUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        engine.doDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        engine.doLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        engine.doRight();
                        break;
                }
            }
        });
    }

    @Override
    public void move(Tile from,Tile to) {
        tilePanels[to.getX()][to.getY()].updateValue(from.getValue());
        tilePanels[from.getX()][from.getY()].updateValue(0);
    }

    @Override
    public void updateValue(Tile tile) {
        tilePanels[tile.getX()][tile.getY()].updateValue(tile.getValue());
    }

    @Override
    public void gameOver() {
        JOptionPane.showMessageDialog(this,"死了！","游戏结束",JOptionPane.WARNING_MESSAGE);
    }
}
