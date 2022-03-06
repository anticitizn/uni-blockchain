package Blockchain;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
    private final PublicKey sender;
    private final PublicKey recipient;
    private final float value;
    private final ArrayList<TransactionOutput> outputs = new ArrayList<>();
    private final ArrayList<TransactionInput> inputs;
    private String id;
    private byte[] signature;

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    public String calculateHash(int transactionSequence) {
        return StringUtility.applySha256(StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(recipient)
                + value + transactionSequence);
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(recipient) + value;
        signature = StringUtility.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = StringUtility.getStringFromKey(sender) + StringUtility.getStringFromKey(recipient) + value;
        return !StringUtility.verifyECDSASig(sender, data, signature);

    }

    public float getInputsValue() {
        float total = 0;

        for (TransactionInput i : inputs) {
            if (i.getUTX0() == null) {
                continue;
            }
            total += i.getUTX0().getValue();
        }

        return total;
    }

    public float getOutputsValue() {
        float total = 0;

        for (TransactionOutput o : outputs) {
            total += o.getValue();
        }

        return total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PublicKey getSender() {
        return sender;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public float getValue() {
        return value;
    }

    public ArrayList<TransactionInput> getInputs() {
        return inputs;
    }

    public ArrayList<TransactionOutput> getOutputs() {
        return outputs;
    }
}