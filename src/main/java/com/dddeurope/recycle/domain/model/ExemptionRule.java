package com.dddeurope.recycle.domain.model;

import java.time.LocalDate;

public class ExemptionRule implements Comparable<ExemptionRule> {
    private final WasteType wasteType;
    private int remainingWeight;
    private final LocalDate expiryDate;
    private boolean isExtra = false;
    private final LocalDate startDate;
    private int price = 0;


    public ExemptionRule(WasteType wasteType, int weight, LocalDate expiryDate) {
        this.wasteType = wasteType;
        this.remainingWeight = weight;
        this.expiryDate = expiryDate;
        this.isExtra = true;
        this.startDate = LocalDate.MIN;
    }

    public ExemptionRule(WasteType wasteType, int remainingWeight, int year, int priceInCent) {
        this.wasteType = wasteType;
        this.remainingWeight = remainingWeight;
        this.expiryDate = LocalDate.of(year, 12, 31);
        this.startDate = LocalDate.of(year, 1, 1);
        this.price = priceInCent;
    }


    public ExemptionRule(WasteType wasteType, int remainingWeight, int year) {
        this.wasteType = wasteType;
        this.remainingWeight = remainingWeight;
        this.expiryDate = LocalDate.of(year, 12, 31);
        this.startDate = LocalDate.of(year, 1, 1);
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public int getRemainingWeight() {
        return remainingWeight;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public boolean isApplicable(LocalDate year, WasteType wasteType) {
        return this.remainingWeight > 0 && wasteType.equals(this.wasteType) && year.isAfter(startDate) && year.isBefore(
                expiryDate);
    }

    public void reduceWeight(int usedWeight) {
        this.remainingWeight -= usedWeight;
    }

    @Override
    public int compareTo(ExemptionRule o) {
        int dateCompare = getExpiryDate().compareTo(o.getExpiryDate());
        if (dateCompare != 0) return dateCompare;

        return price - o.price;
    }

    public int getPrice() {
        return price;
    }
}
