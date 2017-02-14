package game.multiPlay;

import game.interfaces.view.IInfoView;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by STZHANGJK on 2017.2.14.
 */
public class LocalInfoViewProxy implements InvocationHandler{
    private IInfoView local;
    private IInfoView remote;

    public LocalInfoViewProxy(IInfoView local, IInfoView remote) {
        this.local = local;
        this.remote = remote;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(local,args);
        method.invoke(remote,args);
        return null;
    }
}
