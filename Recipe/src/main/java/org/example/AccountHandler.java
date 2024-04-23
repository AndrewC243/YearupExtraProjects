package org.example;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountHandler implements Serializable {
    private List<Account> accountList;

    public AccountHandler() {
        if (new File("src/main/resources/accounts.data").exists()) {
            accountList = (List<Account>) FileHandler.readFile("src/main/resources/accounts.data");
        }
        else {
            accountList = new ArrayList<>();
        }
    }

    public Account getAccount(String username) {
        return accountList.stream()
                .filter(account -> account.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
    public List<Account> getAccountList() {
        return accountList;
    }
    public void save() {
        FileHandler.writeFile("src/main/resources/accounts.data", accountList);
    }
}
