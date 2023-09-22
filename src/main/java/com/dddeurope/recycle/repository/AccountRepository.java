package com.dddeurope.recycle.repository;

import com.dddeurope.recycle.domain.model.Account;
import com.dddeurope.recycle.domain.model.City;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepository extends com.dddeurope.recycle.repository.Repository {
    Map<String, Account> accounts = new HashMap<>();

    public Account getOrRegister(String address, City city) {
        Account account = accounts.get(address);
        if (account != null) return account;
        Account newAccount = new Account(address, city);
        accounts.put(address, newAccount);
        return newAccount;
    }

    public Account getByAddress(String cardId) {
        return accounts.get(cardId);
    }

    @Override
    public void reset() {
        this.accounts = new HashMap<>();
    }


}
