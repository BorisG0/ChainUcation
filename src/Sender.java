import java.io.*;
import java.net.DatagramPacket;
import java.util.Scanner;

public class Sender extends Thread{
    public void run(){
        Scanner scanner = new Scanner(System.in);

        String lineIn;

        while(true){
            lineIn = scanner.nextLine();
            handleLineIn(lineIn);
        }
    }

    private void handleLineIn(String lineIn){
        String[] parts = lineIn.split(" ");
        if(parts[0].equals("t")){
            createTransaction(parts);
        }
        if(parts[0].equals("m")){
            mine(parts);
        }
        if(parts[0].equals("b")){
            printBalance();
        }
    }

    private void createTransaction(String[] params){
        Main.blockChain.addTransaction(Main.senderName, params[1], Integer.parseInt(params[2]), Main.keyPair, Main.keyPair);
        System.out.println("Chain:");
        System.out.println(Main.blockChain);

        try {
            String data = Main.To_String(Main.blockChain);
            sendString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mine(String[] params){
        Main.blockChain.minePendingTransactions(Main.senderName);
        System.out.println("Chain:");
        System.out.println(Main.blockChain);
    }

    private void sendString(String data){
        byte[] byteData = data.getBytes();
        DatagramPacket packet = new DatagramPacket(byteData, byteData.length, Main.address, Main.destPort);

        try {
            Main.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printBalance(){
        System.out.println("balance: " + Main.blockChain.getBalance(Main.senderName));
    }
}