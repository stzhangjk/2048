package game.multiPlay;

import entity.Tile;
import game.GameEngine;
import game.interfaces.game.IGameEngine;
import game.interfaces.game.IMultiPlayEngine;
import game.interfaces.view.IGameView;
import game.interfaces.view.mulitiPlay.IConnectView;
import gui.GameContext;
import gui.MainFrame;
import gui.multiPlay.MultiPlayPanel;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by STZHANGJK on 2017.2.13.
 */
public abstract class MultiPlayEngine extends UnicastRemoteObject implements IMultiPlayEngine,Runnable {

    private InetAddress remoteAddress;
    private IConnectView view;
    private IGameEngine localEngine;
    protected IMultiPlayEngine remoteEngine;
    private MultiPlayPanel mpp;
    private IGameView localView;
    private IGameView remoteView;
    private IGameView proxyView;

    public MultiPlayEngine(IConnectView view) throws RemoteException {
        this.view = view;
    }

    @Override
    public void send(String message) throws RemoteException {
        view.showMessage(message);
        remoteEngine.showMessage(message);
    }

    @Override
    public void showMessage(String message){
        view.showMessage(message);
    }

    @Override
    public void getRemoteEngine(InetAddress remoteAddress) throws RemoteException {
        try {
            this.remoteAddress = remoteAddress;
            remoteEngine = (IMultiPlayEngine) Naming.lookup("rmi://" + remoteAddress.getHostAddress() + ":" + getEnginePortRemote() + "/remoteEngine");
            if(remoteEngine != null){
                view.showMessage("连接成功！");
                view.showMessage("已连接到" + remoteAddress.getHostAddress());
                view.onSuccess();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    protected abstract int getEnginePortRemote();
    protected abstract int getViewPortLocal();
    protected abstract int getViewPortRemote();


    /**
     * 作为主动方开始游戏
     */
    public void startGame(){
        try {
            /*创建初始化本地引擎*/
            createEngine();
            localEngine.initGame();
            /*创建初始化本地界面*/
            createView();
            Tile[][] tiles = localEngine.getTiles();
            initView(tiles);

            /*创建和初始化对方引擎*/
            remoteEngine.createEngine();
            remoteEngine.initEngine(tiles);
            /*创建和初始化对方界面*/
            remoteEngine.createView();
            remoteEngine.initView(tiles);

            /*创建本地界面代理*/
            initViewProxy();
            /*创建对方界面代理*/
            remoteEngine.initViewProxy();

            /*建立对象之间的联系*/
            buildConnection();
            remoteEngine.buildConnection();
            /*跳转到游戏界面*/
            showGameView();
            remoteEngine.showGameView();
        } catch (IOException e){
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createView() throws RemoteException, AlreadyBoundException {
        System.out.println("createView");
        mpp = new MultiPlayPanel();
        localView = mpp.getLocalGamePanel();
        remoteView = mpp.getRemoteGamePanel();
        RemoteGameViewProxy remoteProxy = new RemoteGameViewProxy(remoteView);
        Registry rr = LocateRegistry.createRegistry(getViewPortLocal());
        rr.bind("gameView",remoteProxy);
    }

    @Override
    public void initView(Tile[][] tiles) throws RemoteException {
        System.out.println("initView");
        localView.init(tiles);
        remoteView.init(tiles);
    }

    @Override
    public void createEngine() throws RemoteException {
        System.out.println("createEngine");
        localEngine = new GameEngine();
    }

    @Override
    public void initEngine(Tile[][] tiles) throws RemoteException {
        System.out.println("initEngine");
        localEngine.initForRemote(tiles);
    }

    @Override
    public void initViewProxy() throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("initViewProxy");
        /*获取proxy使用的远程视图*/
        IGameView rv = (IGameView)Naming.lookup("rmi://" + remoteAddress.getHostAddress() + ":" + getViewPortRemote() +"/gameView");
        /*创建本地界面代理*/
        LocalGameViewProxy handler = new LocalGameViewProxy(localView,rv);
        proxyView = (IGameView) Proxy.newProxyInstance(localView.getClass().getClassLoader(),localView.getClass().getInterfaces(),handler);
    }

    @Override
    public void showGameView() throws RemoteException {
        System.out.println("showGameView");
        /*跳转到多人游戏界面*/
        SwingUtilities.invokeLater(()->{
            GameContext.getMainFrame().getContentPane().add(mpp, MainFrame.MULTI_PLAY_PANEL_NAME);
            GameContext.getMainFrame().showView(MainFrame.MULTI_PLAY_PANEL_NAME);
            mpp.repaint();
            mpp.getRemoteGamePanel().setFocusable(false);
            mpp.getLocalGamePanel().requestFocus();
        });
    }

    @Override
    public void buildConnection() throws RemoteException {
        System.out.println("buildConnection");
        localEngine.setGameView(proxyView);
        localEngine.setControlView(mpp.getLocalIJP());//暂时
        localView.setEngine(localEngine);
    }
}
