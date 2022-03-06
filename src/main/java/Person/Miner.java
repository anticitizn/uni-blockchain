package Person;

import Blockchain.Block;
import Blockchain.Blockchain;
import Blockchain.Wallet;
import Blockchain.Transaction;

public class Miner extends Person {
    private final Blockchain blockchain;

    public Miner(String name, Blockchain blockchain) {
        super(name, new Wallet(blockchain));
        this.blockchain = blockchain;
    }

    public void mine(Block block) {
        Transaction transaction = new Transaction(blockchain.getCoinbase().getPublicKey(), wallet.getPublicKey(), blockchain.getMiningReward(), null);
        block.addTransaction(transaction);
        block.mineBlock();
        blockchain.addBlock(block);
    }
}
