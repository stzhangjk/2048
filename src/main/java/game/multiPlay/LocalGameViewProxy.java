package game.multiPlay;

import game.interfaces.view.IGameView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
        if(method.getName().equals("win")){
            remote.gameOver();
        }else if(method.getName().equals("gameOver")){
            remote.win();
        }else method.invoke(remote,args);

        return method.invoke(local,args);
    }
}
