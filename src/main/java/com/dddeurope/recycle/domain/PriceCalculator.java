package com.dddeurope.recycle.domain;

import com.dddeurope.recycle.events.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PriceCalculator {

    private final RecyclingCentre recyclingCentre;

    public PriceCalculator(RecyclingCentre recyclingCentre) {
        this.recyclingCentre = recyclingCentre;
    }

    public void handle(List<Event> events) {
        for (Event event : events) handle(event);
    }

    private void handle(Event event) {
        if (event instanceof IdCardRegistered cardRegistered) {
            recyclingCentre.register(cardRegistered.address(),
                    cardRegistered.cardId(),
                    cardRegistered.city(),
                    cardRegistered.personId());
        }
        if (event instanceof IdCardScannedAtEntranceGate cardScanned) {
            recyclingCentre.enter(cardScanned.cardId(), LocalDate.parse(cardScanned.date()));
        }
        if (event instanceof ExemptionWasGranted exemptionWasGranted) {
            recyclingCentre.grantExemption(exemptionWasGranted.cardId(),
                    exemptionWasGranted.fractionType(),
                    exemptionWasGranted.weight(),
                    LocalDate.parse(exemptionWasGranted.expiryDate()));
        }
        if (event instanceof FractionWasDropped fractionDropped) {
            recyclingCentre.dropFraction(fractionDropped.cardId(),
                    fractionDropped.fractionType(),
                    fractionDropped.weight());
        }
        if (event instanceof DiscountWasBought discountWasBought) {
            recyclingCentre.discountWasBought(discountWasBought.cardId(),
                    discountWasBought.discountPercentage(),
                    discountWasBought.weight(),
                    LocalDate.parse(discountWasBought.expiryDate()),
                    discountWasBought.fractionType());
        }
        if (event instanceof IdCardScannedAtExitGate cardScanned) {
            recyclingCentre.leave(cardScanned.cardId());
        }
    }

    public double calculatePrice(String cardId) {
        int priceInCent = recyclingCentre.calculatePriceInCent(cardId);
        return ((double) priceInCent / 100);
    }

    public void reset() {
        recyclingCentre.reset(); //each request expects a fresh data state;
    }
}
