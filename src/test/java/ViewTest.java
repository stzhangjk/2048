import game.GameEngine;
import gui.MainFrame;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;

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

    @AfterClass
    public static void afterClass() throws IOException {
        System.in.read();
    }
}
