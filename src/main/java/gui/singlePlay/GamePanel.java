package gui.singlePlay;

import entity.Tile;
import game.interfaces.game.IGameEngine;
import game.interfaces.view.IControlView;
import game.interfaces.view.IGameView;
import gui.GameContext;
import gui.MainFrame;
import gui.animate.MergeAnimate;
import gui.animate.MoveAnimate;
import gui.animate.AnimateUnit;
import util.ColorSet;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by STZHANGJK on 2016.11.24.
 */
public class GamePanel extends JPanel implements IGameView{

    private IGameEngine engine;
    private TilePanel[][] tilePanels;
    private final int GAP = 10;

    public GamePanel() {
        setBackground(ColorSet.BACKGROUND);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeTilePanel();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        setFocusable(true);
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
                        new Thread(()->engine.doUp()).start();
                        break;
                    case KeyEvent.VK_DOWN:
                        new Thread(()->engine.doDown()).start();
                        break;
                    case KeyEvent.VK_LEFT:
                        new Thread(()->engine.doLeft()).start();
                        break;
                    case KeyEvent.VK_RIGHT:
                        new Thread(()->engine.doRight()).start();
                        break;
                }
            }
        });
    }

    @Override
    public void init(Tile[][] tiles) {
        SwingUtilities.invokeLater(()->{
            GamePanel.this.removeAll();
            tilePanels = new TilePanel[tiles.length][tiles.length];
            int width = (getWidth()-(2+tiles.length-1)*GAP)/tiles.length;
            int height = (getHeight()-(2+tiles.length-1)*GAP)/tiles.length;
            setLayout(null);
            for(int i=0,len = tilePanels.length;i<len;i++){
                for(int j=0;j<len;j++){
                    tilePanels[i][j] = new TilePanel(tiles[i][j].getValue());
                    tilePanels[i][j].setSize(width,height);
                    tilePanels[i][j].setLocation(GAP*(j+1)+width*j,GAP*(i+1)+height*i);
                    GamePanel.this.add(tilePanels[i][j]);
                    tilePanels[i][j].validate();
                    tilePanels[i][j].repaint();
                }
            }
        });
    }

    /**
     * 重新设置瓦片大小和位置
     */
    public void resizeTilePanel(){
        if(tilePanels != null){
            int len = tilePanels.length;
            int width = (getWidth()-(1+len)*GAP)/len;
            int height = (getHeight()-(1+len)*GAP)/len;
            for(int i=0;i<len;i++){
                for(int j=0;j<len;j++){
                    tilePanels[i][j].setSize(width,height);
                    tilePanels[i][j].setLocation(GAP*(j+1)+width*j,GAP*(i+1)+height*i);
                    tilePanels[i][j].validate();
                }
            }
        }
    }

    /**
     * 播放移动动画
     *
     * @param animateUnits
     */
    @Override
    public void doMoveAnimate(List<AnimateUnit> animateUnits,int order) {
        if(animateUnits.size() > 0){
//            SwingUtilities.invokeLater(()->{
                new MoveAnimate(this,animateUnits).play();
//            });
        }
    }

    /**
     * 合并效果
     */
    @Override
    public int doMergeAnimate(List<AnimateUnit> animateUnits,int order) {
        if(animateUnits.size() > 0) {
            return new MergeAnimate(this, animateUnits).play();
        }else return 0;
    }

    /**
     *
     * @param tile
     */
    @Override
    public void updateValue(Tile tile,int order) {
        tilePanels[tile.getI()][tile.getJ()].updateValue(tile.getValue());
        revalidate();
    }

    @Override
    public void gameOver() {
        JOptionPane.showMessageDialog(GameContext.getMainFrame(),"哈哈，你输了！","游戏结束",JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void win() throws RemoteException {
        JOptionPane.showMessageDialog(GameContext.getMainFrame(),"恭喜！你赢了！","游戏结束",JOptionPane.WARNING_MESSAGE);
    }


    public int getGAP() {
        return GAP;
    }

    public TilePanel[][] getTilePanels() {
        return tilePanels;
    }

    /**
     * 注入游戏引擎
     *
     * @param engine
     */
    @Override
    public void setEngine(IGameEngine engine) {
        this.engine = engine;
    }


}
