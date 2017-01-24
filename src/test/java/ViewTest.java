import gui.MainFrame;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by STZHANGJK on 2017.1.21.
 */
public class ViewTest {
    @Test
    public void testMainFrame() {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    @AfterClass
    public static void afterClass() throws IOException {
        System.in.read();
    }
}
