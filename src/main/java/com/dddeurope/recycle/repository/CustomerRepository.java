package com.dddeurope.recycle.repository;

import com.dddeurope.recycle.domain.model.Account;
import com.dddeurope.recycle.domain.model.CardId;
import com.dddeurope.recycle.domain.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CustomerRepository extends com.dddeurope.recycle.repository.Repository {

    Map<CardId, Customer> customers = new HashMap<>();

    public void register(CardId cardId, String address, String personId, Account account) {
        customers.put(cardId, new Customer(address, cardId, personId, account));
    }

    public boolean existsByCardId(CardId cardId) {
        return customers.containsKey(cardId);
    }

    public Customer getByCardId(CardId cardId) {
        return customers.get(cardId);
    }

    @Override
    public void reset() {
        this.customers = new HashMap<>();
    }
}
