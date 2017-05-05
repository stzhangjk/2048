package game.multiPlay;

import entity.Tile;
import game.interfaces.game.IGameEngine;
import game.interfaces.view.IGameView;
import gui.animate.AnimateUnit;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.2.14.
 */
public class RemoteGameViewProxy extends UnicastRemoteObject implements IGameView {

    private volatile static int order;
    private IGameView gameView;

    public RemoteGameViewProxy(IGameView gameView) throws RemoteException {
        this.gameView = gameView;
    }

    @Override
    public void init(Tile[][] tiles) throws RemoteException {
        synchronized (this){
//            System.out.println(Thread.currentThread().getName());
            gameView.init(tiles);
        }
    }

    @Override
    public void updateValue(Tile tile,int order) throws RemoteException {
        synchronized (gameView){
            while(true){
                if(order == -1 || RemoteGameViewProxy.order +1 == order){

                    gameView.updateValue(tile,-1);

                    if(order == -1){
                        RemoteGameViewProxy.order = 0;
                    }else{
                        RemoteGameViewProxy.order++;
                    }
                    System.out.println(RemoteGameViewProxy.order);
                    gameView.notifyAll();
                    break;
                }else{
                    try {
                        gameView.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void gameOver() throws RemoteException {
        synchronized (gameView){
            gameView.gameOver();
        }
    }

    @Override
    public  void win() throws RemoteException {
        synchronized (gameView){
            gameView.win();
        }
    }

    @Override
    public void setEngine(IGameEngine engine) throws RemoteException {
        gameView.setEngine(engine);
    }

    @Override
    public void doMoveAnimate(List<AnimateUnit> animateUnits,int order)  throws RemoteException{
        synchronized (gameView){
            while(true) {
                if (RemoteGameViewProxy.order + 1 == order) {

                    gameView.doMoveAnimate(animateUnits, -1);

                    RemoteGameViewProxy.order++;
                    System.out.println(RemoteGameViewProxy.order);
                    gameView.notifyAll();
                    break;
                } else {
                    try {
                        gameView.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public int doMergeAnimate(List<AnimateUnit> animateUnits,int order)  throws RemoteException{
        synchronized (gameView){
            while(true) {
                if (RemoteGameViewProxy.order + 1 == order) {

                    gameView.doMergeAnimate(animateUnits, -1);

                    RemoteGameViewProxy.order++;
                    System.out.println(RemoteGameViewProxy.order);
                    gameView.notifyAll();
                    break;
                } else {
                    try {
                        gameView.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return 0;
    }
}
