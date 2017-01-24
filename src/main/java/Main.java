import gui.MainFrame;

import javax.swing.*;

/**
 * Created by STZHANGJK on 2017.1.22.
 */
public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
