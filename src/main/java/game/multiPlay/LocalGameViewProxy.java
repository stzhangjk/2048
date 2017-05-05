package game.multiPlay;

import game.interfaces.view.IGameView;
import gui.animate.AnimateUnit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.2.12.
 */
public class LocalGameViewProxy implements InvocationHandler {

    private IGameView local;
    private IGameView remote;

    public LocalGameViewProxy(IGameView localView, IGameView remoteView) {
        this.local = localView;
        this.remote = remoteView;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        final List<AnimateUnit> us = new ArrayList<>();
        if("doMoveAnimate".equals(name) || "doMergeAnimate".equals(name)){
            List<AnimateUnit> units = (List)args[0];
            try{
                for(AnimateUnit u : units){
                    us.add(u.clone());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        new Thread(()->{
            try{
                if("win".equals(name)){
                    remote.gameOver();
                }else if("gameOver".equals(name)){
                    remote.win();
                }else if("doMoveAnimate".equals(name) || "doMergeAnimate".equals(name)){
                    method.invoke(remote,us,args[1]);
                } else {
                    method.invoke(remote,args);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }).start();
        return method.invoke(local,args);
    }
}
