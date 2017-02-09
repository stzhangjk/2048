package gui;

import java.net.InetAddress;

/**
 * Created by STZHANGJK on 2017.1.26.
 */
public class GameContext {
    private static MainFrame mainFrame;
    private static InetAddress host;
    private static InetAddress remote;

    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void setMainFrame(MainFrame mainFrame) {
        GameContext.mainFrame = mainFrame;
    }

    public static InetAddress getHost() {
        return host;
    }

    public static void setHost(InetAddress host) {
        GameContext.host = host;
    }

    public static InetAddress getRemote() {
        return remote;
    }

    public static void setRemote(InetAddress remote) {
        GameContext.remote = remote;
    }
}
