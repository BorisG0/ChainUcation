package DataObjects;

import org.junit.Test;

import java.security.KeyPair;

public class BlockChainTest {

    @Test
    public void add3TransactionsAndMine(){
        BlockChain blockChain = new BlockChain();
        blockChain.addTransaction("S1", "R1", 50);
        blockChain.addTransaction("S1", "R2", 80);
        blockChain.addTransaction("S2", "R2", 10);

        blockChain.minePendingTransactions("S1");

        System.out.println(blockChain);
    }

    @Test
    public void add25TransactionsAndMine(){
        BlockChain blockChain = new BlockChain();
        for(int i = 0; i < 25; i++){
            blockChain.addTransaction("Sender1", "Receiver1", i);
        }

        blockChain.minePendingTransactions("Miner1");

        System.out.println(blockChain);
    }

    @Test
    public void generateKey(){
        BlockChain blockChain = new BlockChain();
        KeyPair keyPair = blockChain.generateKeys();

        System.out.println("public: " + keyPair.getPublic());
        System.out.println("private: " + keyPair.getPrivate());
    }
}
