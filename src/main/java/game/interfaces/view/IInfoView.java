package game.interfaces.view;


import game.interfaces.game.IGameEngine;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by STZHANGJK on 2017.2.6.
 */
public interface IInfoView extends Remote {

    /**
     * 设置最大的瓦片的值
     * @param value
     */
    void setMax(int value)throws RemoteException;

    /**
     * 设置分数
     */
    void setScore(int score)throws RemoteException;

    /**
     * 注入引擎
     * @param engine
     */
    void setEngine(IGameEngine engine)throws RemoteException;
}
