package Blockchain;

import java.util.ArrayList;
import java.util.HashMap;
import Configuration.Configuration;

public class Blockchain {
    private final HashMap<String, TransactionOutput> utx0Map = new HashMap<>();
    private final ArrayList<Block> blockchain = new ArrayList<>();

    private Transaction genesisTransaction;
    private final Wallet coinbase = new Wallet(this);
    private final Wallet initialWallet = new Wallet(this);

    private final float minimumTransaction = 0.001f;
    private final float miningReward = 0.025f;
    private final int difficulty = Configuration.instance.difficulty;
    private int transactionSequence = 0;

    public Blockchain(float firstTransactionAmount) {
        genesisTransaction = new Transaction(coinbase.getPublicKey(), initialWallet.getPublicKey(), firstTransactionAmount, null);
        genesisTransaction.generateSignature(coinbase.getPrivateKey());
        genesisTransaction.setId("0");
        genesisTransaction.getOutputs().add(
                new TransactionOutput(genesisTransaction.getRecipient(),
                        genesisTransaction.getValue(), genesisTransaction.getId())
        );

        utx0Map.put(genesisTransaction.getOutputs().get(0).getID(), genesisTransaction.getOutputs().get(0));

        Block genesisBlock = new Block("0", this);
        genesisBlock.addTransaction(genesisTransaction);
        addBlock(genesisBlock);
    }

    public HashMap<String, TransactionOutput> getUTX0() {
        return (HashMap<String, TransactionOutput>) utx0Map.clone();
    }

    public Wallet getCoinbase() {
        return coinbase;
    }

    public Wallet getInitialWallet() {
        return initialWallet;
    }
    public float getMiningReward() {
        return miningReward;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Block getNewBlock() {
        return new Block(blockchain.get(blockchain.size()-1).getHash(), this);
    }

    public boolean processTransaction(Transaction transaction) {

        if (transaction.verifySignature()) {
            System.out.println("#transaction signature failed to verify");
            return false;
        }

        for (TransactionInput i : transaction.getInputs()) {
            utx0Map.get(i.getId());
        }

        if (transaction.getInputsValue() > minimumTransaction) {
            System.out.println("#transaction input too small | " + transaction.getInputsValue());
            return false;
        }

        float leftover = transaction.getInputsValue() - transaction.getValue();
        transactionSequence++;
        transaction.setId(transaction.calculateHash(transactionSequence));
        transaction.getOutputs().add(new TransactionOutput(transaction.getRecipient(), transaction.getValue(), transaction.getId()));
        transaction.getOutputs().add(new TransactionOutput(transaction.getSender(), leftover, transaction.getId()));

        for (TransactionOutput o : transaction.getOutputs()) {
            utx0Map.put(o.getID(), o);
        }

        for (TransactionInput i : transaction.getInputs()) {
            if (i.getUTX0() == null) {
                continue;
            }
            utx0Map.remove(i.getUTX0().getID());
        }

        return true;
    }

    public boolean addBlock(Block newBlock) {
        blockchain.add(newBlock);
        return true;
    }
}
