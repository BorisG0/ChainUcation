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
}
