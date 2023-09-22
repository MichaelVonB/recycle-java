package com.dddeurope.recycle.domain;

import com.dddeurope.recycle.domain.model.*;
import com.dddeurope.recycle.repository.AccountRepository;
import com.dddeurope.recycle.repository.CityRepository;
import com.dddeurope.recycle.repository.CustomerRepository;
import com.dddeurope.recycle.repository.VisitsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class RecyclingCentre {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final VisitsRepository visitsRepository;
    private final CityRepository cityRepository;

    public RecyclingCentre(AccountRepository accountRepository, CustomerRepository customerRepository, VisitsRepository visitsRepository, CityRepository cityRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.visitsRepository = visitsRepository;
        this.cityRepository = cityRepository;
    }

    public void register(String address, String cardId, String city, String personId) {
        if (customerRepository.existsByCardId(CardId.of(cardId))) return;
        Account account = accountRepository.getOrRegister(address, cityRepository.get(city));
        customerRepository.register(CardId.of(cardId), address, personId, account);
    }

    public void enter(String cardId, LocalDate date) {
        visitsRepository.enter(CardId.of(cardId), date);
    }

    public void dropFraction(String cardId, String wasteType, int weight) {
        Customer customer = customerRepository.getByCardId(CardId.of(cardId));
        Visit visit = visitsRepository.getByCardId(CardId.of(cardId));
        visit.dropFraction(WasteType.of(wasteType), weight);
        System.out.println("Customer " + customer.getPersonId() + " dropped " + wasteType + " " + weight + "kg");
    }

    public void leave(String cardId) {
        Visit visit = visitsRepository.leave(CardId.of(cardId));
        Customer customer = customerRepository.getByCardId(CardId.of(cardId));
        for (Map.Entry<WasteType, Integer> entry : visit.getFractions().entrySet()) {
            customer.getAccount().addWaste(visit.getDate(), entry.getKey(), entry.getValue());
        }
    }

    public int calculatePriceInCent(String cardId) {
        Customer customer = customerRepository.getByCardId(CardId.of(cardId));

        System.out.println("Visitor " + customer.getPersonId() + " dropped");
        int priceInCent = customer.getAccount().calculatePriceInCent();

        System.out.println("Total price: " + priceInCent);
        System.out.println("Customer was checked out");
        System.out.println("***************************");
        return priceInCent;
    }

    public void reset() {
        this.accountRepository.reset();
        this.visitsRepository.reset();
        this.customerRepository.reset();
    }

    public void grantExemption(String cardId, String fractionType, int weight, LocalDate expiryDate) {
        Customer customer = customerRepository.getByCardId(CardId.of(cardId));
        customer.grantExemption(WasteType.of(fractionType), weight, expiryDate);
    }

    public void discountWasBought(String cardId, float discountPercentage, int weight, LocalDate expiryDate, String fractionType) {
        Customer customer = customerRepository.getByCardId(CardId.of(cardId));
        Account account = customer.getAccount();
        account.addDiscount(new Discount(discountPercentage, weight, expiryDate, WasteType.of(fractionType)));
    }
}
