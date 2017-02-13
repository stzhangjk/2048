package game.multiPlay;


import game.interfaces.view.mulitiPlay.IConnectView;
import gui.GameContext;

import java.io.*;
import java.net.InetAddress;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ClientThread extends MultiPlayEngine {

    private InetAddress serverAddr;
    private IConnectView view;

    public ClientThread(InetAddress serverAddr, IConnectView view) throws RemoteException {
        super(view);
        this.serverAddr = serverAddr;
        this.view = view;
    }

    @Override
    public void run() {
        try{
            view.onConnecting();
            getRemoteEngine(serverAddr);
            if(remoteEngine != null){
                Registry rr = LocateRegistry.createRegistry(ConnUtil.ENGINE_PORT_CLIENT);
                rr.bind("remoteEngine", this);
                remoteEngine.getRemoteEngine(GameContext.getHost());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getEnginePortRemote() {
        return ConnUtil.ENGINE_PORT_SERVER;
    }

    @Override
    protected int getViewPortLocal() {
        return ConnUtil.VIEW_PORT_CLIENT;
    }

    @Override
    protected int getViewPortRemote() {
        return ConnUtil.VIEW_PORT_SERVER;
    }

    @Override
    public void close() {

    }
}
