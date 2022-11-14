import java.io.IOException;
import java.net.DatagramPacket;

public class Receiver extends Thread{
    public void run(){
        while (true){
            DatagramPacket packet = new DatagramPacket(new byte[10000], 10000);
            try {
                Main.socket.receive(packet);
                String data = new String(packet.getData(), 0, packet.getLength());
                System.out.println(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
