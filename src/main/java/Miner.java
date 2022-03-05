public class Miner {
    private final String name;
    private final Wallet wallet;
    private final Blockchain blockchain;

    public Miner(String name, Blockchain blockchain) {
        this.name = name;
        this.blockchain = blockchain;
        wallet = new Wallet(blockchain);
    }

    public void mine(Block block) {
        Transaction transaction = new Transaction(blockchain.getCoinbase().getPublicKey(), wallet.getPublicKey(), blockchain.getMiningReward(), null);
        block.addTransaction(transaction);
        block.mineBlock();
        blockchain.addBlock(block);
    }
}
