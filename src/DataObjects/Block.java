package DataObjects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Block {
    private ArrayList<Transaction> transactions;
    private long index;
    private long time;

    private String prev;

    public Block(ArrayList<Transaction> transactions, long index, long time) {
        this.transactions = transactions;
        this.index = index;
        this.time = time;
    }

    public void setPrev(String prev){
        this.prev = prev;
    }

    public String getHash(){
        String transactionHashes = "";
        for(Transaction t: transactions){
            transactionHashes += t.getHash();
        }

        String combinedString = transactionHashes + index + time + prev;

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

    public String toString(){
        String s = "Block(index: " + index + ", time: " + time + ", transactions: " + transactions.size() + ", prev: " + prev + ", hash: " + getHash() + ")";

        return s;
    }
}
