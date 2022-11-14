package DataObjects;

import org.junit.Test;

import java.util.ArrayList;

public class BlockChainTest {
    @Test
    public void add3Blocks(){
        BlockChain blockChain = new BlockChain();

        ArrayList<Transaction> transactions = new ArrayList<>();

        blockChain.addBlock(new Block(transactions, 0, System.currentTimeMillis()));
        blockChain.addBlock(new Block(transactions, 1, System.currentTimeMillis()));
        blockChain.addBlock(new Block(transactions, 2, System.currentTimeMillis()));

        System.out.println(blockChain);
    }

    @Test
    public void add3TransactionsAndMine(){
        BlockChain blockChain = new BlockChain();
        blockChain.addBlock(new Block(new ArrayList<Transaction>(), 0, System.currentTimeMillis()));
        blockChain.addTransaction("S1", "R1", 50);
        blockChain.addTransaction("S1", "R2", 80);
        blockChain.addTransaction("S2", "R2", 10);

        blockChain.minePendingTransactions("S1");

        System.out.println(blockChain);
    }

    @Test
    public void add25TransactionsAndMine(){
        BlockChain blockChain = new BlockChain();
        blockChain.addBlock(new Block(new ArrayList<Transaction>(), 0, System.currentTimeMillis()));
        for(int i = 0; i < 25; i++){
            blockChain.addTransaction("Sender1", "Receiver1", i);
        }

        blockChain.minePendingTransactions("Miner1");

        System.out.println(blockChain);
    }
}
