package DataObjects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Transaction {
    private String sender;
    private String receiver;
    private long amount;
    private long time;
    private String hash;

    public Transaction(String sender, String receiver, long amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.time = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    public String getHash(){
        return hash;
    }

    private String calculateHash(){
        String combinedString = sender + receiver + amount + time;

        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hash = digest.digest(
                combinedString.getBytes());

        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean isValid(){
        if(sender.equals(receiver)){
            return false;
        }
        return true;
    }

    public String toString(){
        return sender + " - " + receiver + ": " + amount + " (" + time + ")";
    }
}
