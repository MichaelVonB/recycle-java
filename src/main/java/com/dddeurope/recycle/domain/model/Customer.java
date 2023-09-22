package com.dddeurope.recycle.domain.model;

import java.time.LocalDate;

public class Customer {
    private final String address;
    private CardId cardId;
    private String personId;

    private Account account;

    public Customer(String address, CardId cardId, String personId, Account account) {
        this.address = address;
        this.cardId = cardId;
        this.personId = personId;
        this.account = account;
    }

    public String getPersonId() {
        return personId;
    }

    public Account getAccount() {
        return account;
    }

    public void grantExemption(WasteType wasteType, int weight, LocalDate expiryDate) {
        this.account.grantExemption(wasteType, weight, expiryDate);
    }
}
