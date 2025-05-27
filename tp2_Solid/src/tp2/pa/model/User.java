package tp2.pa.model;

public class User {
    private int id;
    private String name;
    private Account account;
    private String password;

    public User(int id, String name, Account account, String password) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }


    public String getPassword() {
        return password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
