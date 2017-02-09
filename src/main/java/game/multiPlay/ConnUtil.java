package game.multiPlay;



import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by STZHANGJK on 2017.2.8.
 */
public class ConnUtil {

    public static int port = 24500;

    public String getIpInString(){
        String add = null;
        try {
            add = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return add;
    }

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
