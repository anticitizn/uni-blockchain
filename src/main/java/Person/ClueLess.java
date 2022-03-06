package Person;
import Blockchain.Wallet;

public class ClueLess extends Person{
    private final BankAccount bankAccount;

    public ClueLess(String name, Wallet wallet, BankAccount bankAccount) {
        super(name, wallet);
        this.bankAccount = bankAccount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
