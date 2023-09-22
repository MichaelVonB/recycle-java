package com.dddeurope.recycle.domain.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Visit {
    private CardId cardId;
    private final Map<WasteType, Integer> fractions = new HashMap<>();
    private final LocalDate date;


    public Visit(CardId cardId, LocalDate date) {
        this.cardId = cardId;
        this.date = date;
    }


    public void dropFraction(WasteType wasteType, int weight) {
        System.out.println("New Fraction was dropped " + wasteType + ", weight: " + weight);
        if (this.fractions.get(wasteType) == null) {
            this.fractions.put(wasteType, weight);
        } else {
            int totalWeight = this.fractions.get(wasteType) + weight;
            this.fractions.put(wasteType, totalWeight);
        }
    }

    public LocalDate getDate() {
        return this.date;
    }


    public Map<WasteType, Integer> getFractions() {
        return fractions;
    }
}
