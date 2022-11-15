package DataObjects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Block {
    private ArrayList<Transaction> transactions;
    private long index;
    private long time;

    private String prev;

    private long nonce;

    public Block(ArrayList<Transaction> transactions, long index, long time) {
        this.transactions = transactions;
        this.index = index;
        this.time = time;
        nonce = 0;
    }

    public void setPrev(String prev){
        this.prev = prev;
    }

    public String getPrev(){
        return prev;
    }

    public String getHash(){
        String transactionHashes = "";
        for(Transaction t: transactions){
            transactionHashes += t.getHash();
        }

        String combinedString = transactionHashes + index + time + prev + nonce;

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

    public void mine(){
        String hasToStartWith = "11";

        while(!getHash().startsWith(hasToStartWith)){
            nonce++;
            System.out.println("trying nonce: " + nonce + ": " + getHash());
        }
        System.out.println("Block " + index + " mined with nonce: " + nonce);
    }

    public boolean hasValidTransactions(){
        return true;
    }

    public String toString(){
        String s = "Block(index: " + index + ", time: " + time + ", transactions: " + transactions.size() + ", prev: " + prev + ", hash: " + getHash() + ")";
        for(Transaction t: transactions){
            s += "\n    " + t.toString();
        }

        return s;
    }
}
