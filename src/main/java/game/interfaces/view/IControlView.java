package game.interfaces.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by STZHANGJK on 2017.2.15.
 */
public interface IControlView extends Remote{
    /**
     * 结束游戏
     */
    void end()throws RemoteException;
}
