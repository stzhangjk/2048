package game.multiPlay;

import game.interfaces.view.IGameView;
import gui.animate.AnimateUnit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.2.12.
 */
public class LocalGameViewProxy implements InvocationHandler {

    private IGameView localView;
    private IGameView remoteView;

    public LocalGameViewProxy(IGameView localView, IGameView remoteView) {
        this.localView = localView;
        this.remoteView = remoteView;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("doMoveAnimate")){
            AnimateUnit[] units = (AnimateUnit[]) ((List)args[0]).toArray(new AnimateUnit[0]);
            remoteView.doMoveAnimate(units);
        }else if(method.getName().equals("doMergeAnimate")){
            AnimateUnit[] units = (AnimateUnit[]) ((List)args[0]).toArray(new AnimateUnit[0]);
            remoteView.doMergeAnimate(units);
        }else{
            method.invoke(remoteView,args);
        }
        method.invoke(localView,args);
        return null;
    }
}
