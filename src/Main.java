import DataObjects.BlockChain;

import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.util.Base64;

public class Main {
    public static int port = 5999;
    public static int destPort = 5998;
    public static InetAddress address;
    public static DatagramSocket socket;

    public static BlockChain blockChain;

    public static String senderName = "default";
    public static KeyPair keyPair;

    public static void main(String[] args) {
        blockChain = new BlockChain();
        keyPair = blockChain.generateKeys();
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if(args.length >= 3){
            port = Integer.parseInt(args[0]);
            destPort = Integer.parseInt(args[1]);
            senderName = args[2];
        }

        System.out.println("starting client on port: " + port + ", destPort: " + destPort);
        System.out.println("name: " + senderName);
        System.out.println("public key: " + keyPair.getPublic());
        System.out.println("private key: " + keyPair.getPrivate());

        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        new Sender().start();
        new Receiver().start();

    }

    public static String To_String( Serializable object ) throws IOException {
        ByteArrayOutputStream Byte_Array_Output_Stream = new ByteArrayOutputStream();
        ObjectOutputStream Object_Output_Stream = new ObjectOutputStream( Byte_Array_Output_Stream );
        Object_Output_Stream.writeObject( object );
        Object_Output_Stream.close();
        return Base64.getEncoder().encodeToString(Byte_Array_Output_Stream.toByteArray());
    }

    public static Object From_String( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] Byte_Data = Base64.getDecoder().decode( s );
        ObjectInputStream Object_Input_Stream = new ObjectInputStream( new ByteArrayInputStream(Byte_Data) );
        Object Demo_Object  = Object_Input_Stream.readObject();
        Object_Input_Stream.close();
        return Demo_Object;
    }
}
