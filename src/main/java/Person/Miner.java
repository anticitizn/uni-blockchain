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
        block.addTransaction(blockchain.getCoinbase().sendFunds(wallet.getPublicKey(), blockchain.getMiningReward()));
        block.mineBlock();
        blockchain.addBlock(block);
    }
}
