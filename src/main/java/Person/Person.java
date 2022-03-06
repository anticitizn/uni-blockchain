package Person;

import Blockchain.Wallet;

public abstract class Person {
    protected final String name;
    protected final Wallet wallet;

    public Person(String name, Wallet wallet) {
        this.name = name;
        this.wallet = wallet;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getName() {
        return name;
    }

}
