package game.multiPlay;

import game.interfaces.game.IGameEngine;
import game.interfaces.view.IInfoView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by STZHANGJK on 2017.2.14.
 */
public class RemoteInfoViewProxy extends UnicastRemoteObject implements IInfoView {

    private IInfoView view;

    public RemoteInfoViewProxy(IInfoView view) throws RemoteException {
        this.view = view;
    }

    @Override
    public void setMax(int value) throws RemoteException{
        view.setMax(value);
    }

    @Override
    public void setScore(int score) throws RemoteException{
        view.setScore(score);
    }

    @Override
    public void setEngine(IGameEngine engine) throws RemoteException{
        view.setEngine(engine);
    }
}
