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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ClientThread extends Thread {

    private Socket cSocket;
    private InetAddress remote;
    private IConnectView view;
    private PrintStream ps;
    private BufferedReader reader;
    private IGameEngine engine;
    private RecvThread recvThread;
    private boolean pause;

    public ClientThread(InetAddress remote, IConnectView view) {
        this.remote = remote;
        this.view = view;
    }

    @Override
    public void run() {
        try{
            view.showMessage("连接到：" + getServerAddress());
            view.onConnecting();
            cSocket = new Socket(remote,ConnUtil.port);
            view.showMessage("连接成功!");
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
        } catch (SocketException e){
            close();
        } catch (IOException e){
            view.showMessage(e.getMessage());
            view.showMessage("连接失败!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void send(String message){
        if(ps != null){
            ps.println(message);
            ps.flush();
        }
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
            Tile[][] copyTs = copy(tiles);
            remote.initForRemote(copyTs);
            SwingUtilities.invokeLater(()->{
                GameContext.getMainFrame().getContentPane().add(mpp, MainFrame.MULTI_PLAY_PANEL_NAME);
                GameContext.getMainFrame().showView(MainFrame.MULTI_PLAY_PANEL_NAME);
                mpp.repaint();
                mpp.getLocalGamePanel().requestFocus();
            });
            System.out.println("Client初始化完成！");
            /*开始接受远程按键输入*/
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
        try {
            if(!cSocket.isClosed()){
                cSocket.close();
                view.onClose();
                view.showMessage("已关闭连接");
            }
        } catch (IOException e) {
            view.showMessage("关闭连接时出错");
            e.printStackTrace();
        }
    }

    private String getServerAddress(){
        return remote.getHostAddress();
    }

}
