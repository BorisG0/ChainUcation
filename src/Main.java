import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    public static int port = 5999;
    public static int destPort = 5998;
    public static InetAddress address;
    public static DatagramSocket socket;

    public static void main(String[] args) {
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if(args.length >= 2){
            port = Integer.parseInt(args[0]);
            destPort = Integer.parseInt(args[1]);
        }

        System.out.println("starting client on port: " + port + ", destPort: " + destPort);

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        new Sender().start();
        new Receiver().start();

    }
}
