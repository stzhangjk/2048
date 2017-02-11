package game.multiPlay;

import entity.Tile;
import game.GameEngine;
import game.interfaces.game.IGameEngine;
import game.interfaces.view.mulitiPlay.IConnectView;
import gui.GameContext;
import gui.MainFrame;
import gui.multiPlay.MultiPlayPanel;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ServerThread extends Thread{

    private Socket cSocket;
    private InetAddress localAddr;
    private ServerSocket sSocket;
    private IConnectView view;
    private BufferedReader reader;
    private PrintStream ps;
    private IGameEngine engine;
    private RecvThread recvThread;
    private volatile boolean pause;

    public ServerThread(InetAddress serverAddr, IConnectView view) {
        this.localAddr = serverAddr;
        this.view = view;
    }

    @Override
    public void run() {
        try{
            view.showMessage("启动服务器……");
            view.onConnecting();
            sSocket = new ServerSocket(ConnUtil.port,1, localAddr);
            view.showMessage("准备就绪!");
            cSocket = sSocket.accept();

            if(cSocket != null){
                view.showMessage("连接成功！");
                view.showMessage("连接到：" + getClientAddress());
                view.onSuccess();
                ps = new PrintStream(cSocket.getOutputStream());
                String temp;
                reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                synchronized (reader){
                    while(true){
                        temp = reader.readLine();
                        if(temp != null){
                            System.out.println(temp);
                            if(temp.equals("start")){
                                startGameFromRemote();
                            }
                            view.showMessage(temp);
                            if(pause)
                                reader.wait();
                        }else break;
                    }
                }
            }else {
                view.showMessage("连接失败！");
            }
        } catch (Exception e) {
            view.showMessage("出错：" + e.getMessage());
        }
    }

    public void send(String message){
        if(ps != null){
            ps.println(message);
            ps.flush();
        }
    }

    private String getClientAddress(){
        return cSocket.getInetAddress().getHostAddress();
    }

    public void startGame(){
        try {
            /*创建引擎代理*/
            IGameEngine local = new GameEngine();
            IGameEngine remote = new GameEngine();
            setEngine(remote);
            InvocationHandler handlerLocal = new GameEngineProxy(local, ps);
            IGameEngine localProxy = (IGameEngine) Proxy.newProxyInstance(local.getClass().getClassLoader(),local.getClass().getInterfaces(),handlerLocal);
            MultiPlayPanel mpp = new MultiPlayPanel();
            mpp.setEngine(localProxy,remote);
            /*代理会发送start给另一方*/
            localProxy.start();
            /*发送tiles给另一方*/
            Tile[][] tiles = local.getTiles();
            ObjectOutputStream out = new ObjectOutputStream(cSocket.getOutputStream());
            out.writeObject(tiles);
            out.flush();
            /*初始化本地远程界面*/
            remote.initForRemote(copy(tiles));
            /*开始接受远程按键输入*/
            listen();
            /*跳转到多人游戏界面*/
            SwingUtilities.invokeLater(()->{
                GameContext.getMainFrame().getContentPane().add(mpp, MainFrame.MULTI_PLAY_PANEL_NAME);
                GameContext.getMainFrame().showView(MainFrame.MULTI_PLAY_PANEL_NAME);
                mpp.repaint();
                mpp.getLocalGamePanel().requestFocus();
            });
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startGameFromRemote(){
        try{
            ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());
            Tile[][] tiles = (Tile[][]) in.readObject();
            System.out.println(Arrays.toString(tiles));
             /*创建引擎代理*/
            IGameEngine local = new GameEngine();
            IGameEngine remote = new GameEngine();
            setEngine(remote);
            InvocationHandler handlerLocal = new GameEngineProxy(local, ps);
            IGameEngine localProxy = (IGameEngine) Proxy.newProxyInstance(local.getClass().getClassLoader(),local.getClass().getInterfaces(),handlerLocal);
            MultiPlayPanel mpp = new MultiPlayPanel();
            mpp.setEngine(localProxy,remote);
            local.initForRemote(tiles);
            remote.initForRemote(copy(tiles));
            SwingUtilities.invokeLater(()->{
                GameContext.getMainFrame().getContentPane().add(mpp, MainFrame.MULTI_PLAY_PANEL_NAME);
                GameContext.getMainFrame().showView(MainFrame.MULTI_PLAY_PANEL_NAME);
                mpp.repaint();
                mpp.getLocalGamePanel().requestFocus();
            });
            /*开始接受远程按键输入*/
            System.out.println("Client初始化完成！");
            listen();
            ps.println("XXX");
            ps.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void listen(){
        pause = true;
        recvThread = new RecvThread(reader,engine);
        recvThread.start();
    }

    private Tile[][] copy(Tile[][] tiles){
        Tile[][] r = new Tile[tiles.length][tiles.length];
        for(int i=0,len = tiles.length;i<len;i++){
            for(int j=0;j<len;j++){
                r[i][j] = new Tile(i,j);
                r[i][j].setValue(tiles[i][j].getValue());
            }
        }
        return r;
    }

    public void setEngine(IGameEngine engine) {
        this.engine = engine;
    }

    public void close(){
        try{
            if(!cSocket.isClosed() || !sSocket.isClosed()){
                sSocket.close();
                cSocket.close();
                view.onClose();
                view.showMessage("已关闭连接");
            }
        } catch (IOException e) {
            view.showMessage("关闭连接时出错");
            e.printStackTrace();
        }
    }

}
