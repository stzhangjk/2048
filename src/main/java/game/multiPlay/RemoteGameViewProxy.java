package game.multiPlay;

import entity.Tile;
import game.interfaces.game.IGameEngine;
import game.interfaces.view.IGameView;
import gui.animate.AnimateUnit;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
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
        gameView.init(tiles);
    }

    @Override
    public void doMoveAnimate(AnimateUnit[] animateUnits) throws RemoteException {
        gameView.doMoveAnimate(Arrays.asList(animateUnits));
    }

    @Override
    public void doMergeAnimate(AnimateUnit[] animateUnits) throws RemoteException {
        gameView.doMergeAnimate(Arrays.asList(animateUnits));
    }

    @Override
    public void updateValue(Tile tile) throws RemoteException {
        gameView.updateValue(tile);
    }

    @Override
    public void gameOver() throws RemoteException {
        gameView.gameOver();
    }

    @Override
    public void setEngine(IGameEngine engine) throws RemoteException {
        gameView.setEngine(engine);
    }

    @Override
    public void doMoveAnimate(List<AnimateUnit> animateUnits) {

    }

    @Override
    public void doMergeAnimate(List<AnimateUnit> animateUnits) {

    }
}