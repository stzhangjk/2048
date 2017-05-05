import entity.Tile;
import game.GameEngine;
import gui.MainFrame;
import gui.animate.AnimateUnit;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public class ViewTest {
    @Test
    public void testMainFrame() {
        GameEngine engine = new GameEngine();
        Method[] methods = engine.getClass().getMethods();
        for(Method m : methods){
            System.out.println(m.getName());
        }
    }


    @Test
    public void testArrayCopy(){
        String[] arr = {"1","2","3"};
        String[] arr2 = new String[arr.length];
        System.arraycopy(arr,0,arr2,0,3);
        arr2[0] = "0";//修改了引用
        System.out.println("arr1" + Arrays.toString(arr));
        System.out.println("arr2" + Arrays.toString(arr2));
    }

    /**
     * 只是拷贝引用
     */
    @Test
    public void testTile(){
        Tile[] ts = new Tile[]{
                new Tile(0,0),new Tile(1,1)
        };
        Tile[] ts2 = new Tile[ts.length];
        System.arraycopy(ts,0,ts2,0,ts.length);

        ts2[0].setValue(2);
        System.out.println("ts" + Arrays.toString(ts));
        System.out.println("ts2" + Arrays.toString(ts2));
    }

    @Test
    public void testClone(){
        List<AnimateUnit> units = new ArrayList<>();

    }

    @AfterClass
    public static void afterClass() throws IOException {
        System.in.read();
    }
}
