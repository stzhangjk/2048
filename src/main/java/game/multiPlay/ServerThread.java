package game.multiPlay;

import game.interfaces.view.mulitiPlay.IConnectView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ServerThread extends Thread{


    private Socket cSocket;
    private InetAddress localAddr;
    private ServerSocket sSocket;
    private IConnectView view;

    public ServerThread(InetAddress serverAddr, IConnectView view) {
        this.localAddr = serverAddr;
        this.view = view;

    }

    @Override
    public void run() {
        try{
            view.showMessage("启动服务器……");
            sSocket = new ServerSocket(ConnUtil.port,1, localAddr);
            view.showMessage("准备就绪!");
            cSocket = sSocket.accept();
            if(cSocket != null){
                view.showMessage("连接成功！");
                view.showMessage("连接到：" + getClientAddress());
            }else view.showMessage("连接失败！");

        }catch (IOException e){
            view.showMessage("出错：" + e.getMessage());
            view.showMessage("启动失败!");
        }

    }

    private String getClientAddress(){
        return cSocket.getInetAddress().getHostAddress();
    }
}
