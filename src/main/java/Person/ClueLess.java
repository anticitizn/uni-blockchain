package Person;

public class ClueLess {

    private String firstName;
    private String lastName;
    private BankAccount bankAccount;

    public ClueLess(String firstName, String lastName, BankAccount bankAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bankAccount;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}
