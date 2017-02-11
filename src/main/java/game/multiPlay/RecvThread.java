package game.multiPlay;

import game.interfaces.game.IGameEngine;

import java.io.BufferedReader;
import java.lang.reflect.Method;

/**
 * Created by STZHANGJK on 2017.2.11.
 */
public class RecvThread extends Thread {

    private BufferedReader reader;
    private IGameEngine engine;

    public RecvThread(BufferedReader reader, IGameEngine engine) {
        this.reader = reader;
        this.engine = engine;
    }

    @Override
    public void run() {
        try{
            synchronized (reader){
                String temp;
                while(true){
                    temp = reader.readLine();
                    System.out.println("Client Rect:" + temp);
                    Method method = engine.getClass().getMethod(temp);
                    method.invoke(engine);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
