package tp2.pa.model;

public class Account {
    private final int idAccount;
    private Double balance;

    public Account(int idAccount, Double initialbalance) {
        this.idAccount = idAccount;
        this.balance = initialbalance;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
