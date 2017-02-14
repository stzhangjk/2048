package game.multiPlay;



import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ConnUtil {

    public static int ENGINE_PORT_SERVER = 27889;
    public static int ENGINE_PORT_CLIENT = 27890;
    public static int VIEW_PORT_SERVER = 27891;
    public static int VIEW_PORT_CLIENT = 27892;
    public static int INFO_VIEW_PORT_SERVER = 27990;
    public static int INFO_VIEW_PORT_CLIENT = 27991;

    public InetAddress[] getAddress(){
        InetAddress[] adds = null;
        try {
            adds = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return adds;
    }

}
