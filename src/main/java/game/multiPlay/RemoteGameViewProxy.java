package game.multiPlay;

import entity.Tile;
import game.interfaces.game.IGameEngine;
import game.interfaces.view.IGameView;
import gui.animate.AnimateUnit;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.2.14.
 */
public class RemoteGameViewProxy extends UnicastRemoteObject implements IGameView {

    private IGameView gameView;

    public RemoteGameViewProxy(IGameView gameView) throws RemoteException {
        this.gameView = gameView;
    }

    @Override
    public void init(Tile[][] tiles) throws RemoteException {
        SwingUtilities.invokeLater(()->{
            try {
                gameView.init(tiles);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updateValue(Tile tile) throws RemoteException {
        SwingUtilities.invokeLater(()->{
            try {
                gameView.updateValue(tile);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void gameOver() throws RemoteException {
        SwingUtilities.invokeLater(()->{
            try {
                gameView.gameOver();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void setEngine(IGameEngine engine) throws RemoteException {
        SwingUtilities.invokeLater(()->{
            try {
                gameView.setEngine(engine);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void doMoveAnimate(List<AnimateUnit> animateUnits)  throws RemoteException{
        SwingUtilities.invokeLater(()->{
            try {
                gameView.doMoveAnimate(animateUnits);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int doMergeAnimate(List<AnimateUnit> animateUnits)  throws RemoteException{
        SwingUtilities.invokeLater(()->{
            try {
                gameView.doMergeAnimate(animateUnits);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
        return 0;
    }
}
