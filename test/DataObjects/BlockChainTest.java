package DataObjects;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class BlockChainTest {

    @Test
    public void add3TransactionsAndMine(){
        BlockChain blockChain = new BlockChain();
        KeyPair keyPair = blockChain.generateKeys();

        blockChain.addTransaction("S1", "R1", 50, keyPair, keyPair);
        blockChain.addTransaction("S1", "R2", 80, keyPair, keyPair);
        blockChain.addTransaction("S2", "R2", 10, keyPair, keyPair);

        blockChain.minePendingTransactions("S1");

        System.out.println(blockChain);
    }

    @Test
    public void add25TransactionsAndMine(){
        BlockChain blockChain = new BlockChain();
        KeyPair keyPair = blockChain.generateKeys();

        for(int i = 0; i < 25; i++){
            blockChain.addTransaction("Sender1", "Receiver1", i, keyPair, keyPair);
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

    @Test
    public void signingAndDecrypting(){
        BlockChain blockChain = new BlockChain();
        KeyPair keyPair = blockChain.generateKeys();

        Transaction transaction = new Transaction("s1","r1",10);
        System.out.println("created: " + transaction);
        System.out.println("with hash: " + transaction.getHash());

        transaction.sign(keyPair, keyPair);
        System.out.println("signature: " + transaction.getSignature());

        Cipher decryptCipher = null;
        try {
            decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());

            byte[] decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(transaction.getSignature()));
            String decryptedMessage = new String(decryptedMessageBytes);

            System.out.println("decrypted: " + decryptedMessage);
            System.out.println("matching: " + decryptedMessage.equals(transaction.getHash()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
