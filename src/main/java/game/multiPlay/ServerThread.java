package game.multiPlay;

import game.interfaces.view.mulitiPlay.IConnectView;
import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ServerThread extends MultiPlayEngine {

    public ServerThread(IConnectView view) throws RemoteException {
        super(view);
    }

    @Override
    public void run() {
        try{
            view.onConnecting();
            rEngine = LocateRegistry.createRegistry(ConnUtil.ENGINE_PORT_SERVER);
            rEngine.bind("remoteEngine", this);
            view.showMessage("已准备好连接客户端！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getEnginePortRemote() {
        return ConnUtil.ENGINE_PORT_CLIENT;
    }

    @Override
    protected int getViewPortLocal() {
        return ConnUtil.VIEW_PORT_SERVER;
    }

    @Override
    protected int getViewPortRemote() {
        return ConnUtil.VIEW_PORT_CLIENT;
    }

    @Override
    protected int getInfoPortLocal() {
        return ConnUtil.INFO_VIEW_PORT_SERVER;
    }

    @Override
    protected int getInfoPortRemote() {
        return ConnUtil.INFO_VIEW_PORT_CLIENT;
    }


}
