package game.multiPlay;

import game.interfaces.game.IGameEngine;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.2.10.
 */
public class GameEngineProxy implements InvocationHandler{

    private List<String> ms;
    private PrintStream ps;
    private IGameEngine target;

    public GameEngineProxy(IGameEngine target,PrintStream ps) {
        this.target = target;
        this.ps = ps;
        ms = Arrays.asList("doUp","doDown","doLeft","doRight","start");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if(ms.contains(name)){
            System.out.println("Server send:" + name);
            ps.println(method.getName());
            ps.flush();
        }
        method.invoke(target,args);
        return null;
    }
}
