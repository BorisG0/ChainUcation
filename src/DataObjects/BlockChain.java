package DataObjects;

import java.util.ArrayList;

public class BlockChain {
    private ArrayList<Block> blocks;
    private ArrayList<Transaction> pendingTransactions;
    private final int MAX_BLOCK_SIZE = 10;
    private final int MINER_REWARD = 50;

    public BlockChain(){
        this.blocks = new ArrayList<>();
        this.pendingTransactions = new ArrayList<>();
    }

    public void addTransaction(String sender, String receiver, int amount){
        pendingTransactions.add(new Transaction(sender, receiver, amount));
    }

    public boolean minePendingTransactions(String miner){
        if(pendingTransactions.size() <= 1){
            return false;
        }

        for(int i = 0; i < pendingTransactions.size(); i+= MAX_BLOCK_SIZE){
            int end = i + MAX_BLOCK_SIZE;
            if(end > pendingTransactions.size() - 1){
                end = pendingTransactions.size() - 1;
            }

            ArrayList<Transaction> transactions = new ArrayList<>(pendingTransactions.subList(i, end));

            Block newBlock = new Block(transactions, System.currentTimeMillis(), blocks.size());
            newBlock.setPrev(blocks.get(blocks.size() - 1).getHash());
            newBlock.mine();
            blocks.add(newBlock);
        }

        Transaction payMiner = new Transaction("Miner Rewards", miner, MINER_REWARD);
        pendingTransactions = new ArrayList<>();
        pendingTransactions.add(payMiner);

        return true;
    }

    public void addBlock(Block block){
        if(blocks.size() > 0){
            block.setPrev(blocks.get(blocks.size() - 1).getHash());
        }else{
            block.setPrev("none");
        }
        blocks.add(block);
    }

    public String toString(){
        String s = "";

        for(Block b: blocks){
            s += b.toString() + "\n";
        }

        return s;
    }
}
