package game.interfaces.game;

import entity.Tile;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by STZHANGJK on 2017.2.13.
 */
public interface IMultiPlayEngine extends Remote {

    void createView() throws RemoteException, AlreadyBoundException;
    void initView(Tile[][] tiles)throws RemoteException;
    void createEngine()throws RemoteException;
    void initEngine(Tile[][] tiles)throws RemoteException;
    void initViewProxy() throws RemoteException, MalformedURLException, NotBoundException;
    void showGameView()throws RemoteException;
    void buildConnection()throws RemoteException;
    void deConnect()throws RemoteException;
    void deConnectFromRemote() throws RemoteException, NotBoundException;
    void endGame()throws RemoteException;
    void send(String message)throws RemoteException;
    void showMessage(String message)throws RemoteException;
    void getRemoteEngine(InetAddress remoteAddress)throws RemoteException;
}
