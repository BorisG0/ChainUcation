package DataObjects;

import java.io.Serializable;
import java.security.*;
import java.util.ArrayList;

public class BlockChain implements Serializable {
    private ArrayList<Block> blocks;
    private ArrayList<Transaction> pendingTransactions;
    private final int MAX_BLOCK_SIZE = 10;
    private final int MINER_REWARD = 50;

    public BlockChain(){
        this.blocks = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
        addGenesisBlock();
    }

    public KeyPair generateKeys(){
        KeyPairGenerator generator = null;

        try {
            generator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        return pair;
//        PrivateKey privateKey = pair.getPrivate();
//        PublicKey publicKey = pair.getPublic();
    }

    public void addTransaction(String sender, String receiver, int amount, KeyPair key, KeyPair senderKey){
        Transaction newTransaction = new Transaction(sender, receiver, amount);

        newTransaction.sign(key, senderKey);
        if(!newTransaction.isValid()){
            return;
        }

        pendingTransactions.add(newTransaction);
        System.out.println("adding Transaction: " + newTransaction);
    }

    public boolean minePendingTransactions(String miner){
        if(pendingTransactions.size() <= 1){
            return false;
        }

        for(int i = 0; i < pendingTransactions.size(); i+= MAX_BLOCK_SIZE){
            int end = i + MAX_BLOCK_SIZE;
            if(end >= pendingTransactions.size() - 1){
                end = pendingTransactions.size();
            }

            ArrayList<Transaction> transactions = new ArrayList<>(pendingTransactions.subList(i, end));

            Block newBlock = new Block(transactions, blocks.size(), System.currentTimeMillis());
            newBlock.setPrev(blocks.get(blocks.size() - 1).getHash());
            newBlock.mine();
            blocks.add(newBlock);
        }

        Transaction payMiner = new Transaction("Miner Rewards", miner, MINER_REWARD);
        pendingTransactions = new ArrayList<>();
        pendingTransactions.add(payMiner);

        return true;
    }

    private void addGenesisBlock(){
        Block genesis = new Block(new ArrayList<>(), 0, System.currentTimeMillis());
        genesis.setPrev("none");
        blocks.add(genesis);
    }

//    public void addBlock(Block block){
//        if(blocks.size() > 0){
//            block.setPrev(blocks.get(blocks.size() - 1).getHash());
//        }else{
//            block.setPrev("none");
//        }
//        blocks.add(block);
//    }

    public boolean isValidChain(){
        for(int i = 1; i < blocks.size(); i++){
            Block b1 = blocks.get(i - 1);
            Block b2 = blocks.get(i);

            if(!b1.hasValidTransactions()){
                return false;
            }

            if(b2.getHash().equals(b1.getPrev())){
                return false;
            }

        }
        return true;
    }

    public String toString(){
        String s = "";

        for(Block b: blocks){
            s += b.toString() + "\n";
        }

        s += "pending:\n";
        for(Transaction t: pendingTransactions){
            s += t.toString() + "\n";
        }

        return s;
    }
}
