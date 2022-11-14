import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Scanner;

public class Sender extends Thread{
    public void run(){
        Scanner scanner = new Scanner(System.in);

        String lineIn;

        while(true){
            lineIn = scanner.nextLine();
            byte[] data = lineIn.getBytes();

            DatagramPacket packet = new DatagramPacket(data, data.length, Main.address, Main.destPort);

            try {
                Main.socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}