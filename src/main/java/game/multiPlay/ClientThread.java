package game.multiPlay;

import game.interfaces.view.mulitiPlay.IConnectView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ClientThread extends Thread {

    private Socket cSocket;
    private InetAddress local;
    private InetAddress remote;
    private IConnectView view;

    public ClientThread(InetAddress local, InetAddress remote, IConnectView view) {
        this.local = local;
        this.remote = remote;
        this.view = view;
    }

    @Override
    public void run() {
        try{
            view.showMessage("连接到：" + getServerAddress());
            cSocket = new Socket(remote,ConnUtil.port);
            view.showMessage("连接成功!");

        }catch (IOException e){

        }

    }

    private String getServerAddress(){
        return remote.getHostAddress();
    }
}
