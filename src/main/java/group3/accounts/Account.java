package group3.accounts;

import group3.constants.AccountType;

public class Account {
    private final String id;
    private final String password;
    private final AccountType accountType;
    public Account(String id, String password) {
        this.id = id;
        this.password = password;
        this.accountType = AccountType.PASSENGER;
    }

    public Account(String id, String password, AccountType accountType) {
        this.id = id;
        this.password = password;
        this.accountType = accountType;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
