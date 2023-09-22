package com.dddeurope.recycle.domain.model;

import com.dddeurope.recycle.application.NeededExemptionRules;

import java.time.LocalDate;
import java.util.*;

public class Account {
    private final String address;

    private final City city;
    private final List<DroppedFraction> droppedFractions = new ArrayList<>();
    private final List<ExemptionRule> exemptionRuleUsage = new ArrayList<>();
    private final List<Discount> discounts = new ArrayList<>();

    public Account(String address, City city) {
        this.address = address;
        this.city = city;
    }

    public void grantExemption(WasteType wasteType, int weight, LocalDate expiryDate) {
        this.exemptionRuleUsage.add(new ExemptionRule(wasteType, weight, expiryDate));
    }

    public void addWaste(LocalDate date, WasteType wasteType, int wasteInKg) {
        this.droppedFractions.add(new DroppedFraction(date, wasteType, wasteInKg));

    }


    public int calculatePriceInCent() {
        Map<LocalDate, Integer> prices = new HashMap<>();
        if (city.getName().equals("Cottington")) {
            System.out.println("Starting for Cottington");
        }
        initializeGeneralExemptionRules();
        Collections.sort(this.exemptionRuleUsage);
        for (DroppedFraction droppedFraction : this.droppedFractions) {
            int priceForVisits = 0;
            Waste waste = this.city.getWasteMap().get(droppedFraction.getWasteType());
            int weightAboveExemption = droppedFraction.getWeight();
            while (weightAboveExemption > 0 && hasApplicableExemptionRule(droppedFraction)) {
                ExemptionRule exemptionRule = determineApplicableExemptionRule(droppedFraction).get(); // isPresent is guaranteed in while
                int usedWeight = Math.min(weightAboveExemption, exemptionRule.getRemainingWeight());

                weightAboveExemption -= usedWeight;
                exemptionRule.reduceWeight(usedWeight);

                System.out.println("Applied exemption rule, used " + usedWeight + "kg weight");
                System.out.println("Exmeption rule has " + exemptionRule.getRemainingWeight() + "kg remaining weight");
                priceForVisits += useDiscountedPriceIfPossible(usedWeight,
                        droppedFraction.getDate(),
                        droppedFraction.getWasteType(),
                        exemptionRule.getPrice());
            }

            System.out.println("weightAboveExemption: " + weightAboveExemption);

            priceForVisits += useDiscountedPriceIfPossible(weightAboveExemption,
                    droppedFraction.getDate(),
                    droppedFraction.getWasteType(),
                    waste.getPrice());
            Integer currentVisitPrice = prices.get(droppedFraction.getDate());
            if (currentVisitPrice != null) {
                currentVisitPrice = currentVisitPrice + priceForVisits;
                prices.put(droppedFraction.getDate(), currentVisitPrice);
            }
            prices.putIfAbsent(droppedFraction.getDate(), priceForVisits);
            System.out.println("Price For All visits in till " + droppedFraction.getDate() + ": " + priceForVisits);
        }
        Set<LocalDate> keys = prices.keySet();
        if (keys.size() == 0) return 0;
        return prices.getOrDefault(prices.keySet().stream().sorted().toList().get(keys.size() - 1), 0);
    }

    private int useDiscountedPriceIfPossible(int weight, LocalDate date, WasteType wasteType, int wastePrice) {
        return getDiscountFor(date, wasteType).map(d -> d.applyDiscount(
                weight, wastePrice)).orElse(weight * wastePrice);
    }

    public void initializeGeneralExemptionRules() {
        List<NeededExemptionRules> neededRules = new ArrayList<>();
        this.droppedFractions.forEach(df -> {
            long existingRule = neededRules.stream().filter(nr -> nr.getWasteType().equals(df.getWasteType())).filter(nr -> nr.getYear() == df.getDate().getYear()).count();
            if (existingRule == 0) neededRules.add(new NeededExemptionRules(df.getDate().getYear(), df.getWasteType()));

        });
        neededRules.forEach(rule -> {
            int exemptionRuleInKgPerYear = this.city.getWasteMap().get(rule.getWasteType()).getExemptionRuleInKgPerYear();
            if (exemptionRuleInKgPerYear > 0) {
                this.exemptionRuleUsage.add(new ExemptionRule(rule.getWasteType(),
                        exemptionRuleInKgPerYear,
                        rule.getYear()));
            }
            List<ProgressivePrice> specialPrices = this.city.getWasteMap().get(rule.getWasteType()).getProgressivePrices();
            if (specialPrices != null) {
                specialPrices.forEach(sp -> {
                    this.exemptionRuleUsage.add(new ExemptionRule(rule.getWasteType(),
                            sp.getQuota(),
                            rule.getYear(),
                            sp.getPrice()));
                });
            }
        });
        System.out.println("Registered " + this.exemptionRuleUsage.size() + " exemption Rules");
    }

    private Optional<ExemptionRule> determineApplicableExemptionRule(DroppedFraction droppedFraction) {
        Collections.sort(this.exemptionRuleUsage);
        List<ExemptionRule> list = this.exemptionRuleUsage.stream().filter(er -> er.isApplicable(droppedFraction.getDate(),
                droppedFraction.getWasteType())).toList();
        if (list.size() == 0) return Optional.empty();
        return Optional.of(list.get(0));
    }

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
    }

    private boolean hasApplicableExemptionRule(DroppedFraction droppedFraction) {
        return determineApplicableExemptionRule(droppedFraction).isPresent();
    }

    private Optional<Discount> getDiscountFor(LocalDate date, WasteType wasteType) {
        if (discounts.size() == 0) return Optional.empty();
        for (Discount discount : discounts) {
            if (discount.active() && discount.isApplicable(date, wasteType)) return Optional.of(discount);
        }
        return Optional.empty();
    }
}
