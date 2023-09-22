package com.dddeurope.recycle.domain.model;

import java.time.LocalDate;

public class Discount {
    private final float discountPercentage;
    private final int weight;
    private final LocalDate expiryDate;
    private final WasteType fractionType;

    private int usedWeight = 0;

    public boolean active() {
        return true; //bug
//        return weight > usedWeight;
    }

    public boolean isApplicable(LocalDate date, WasteType wasteType) {
        return date.isBefore(getExpiryDate()) && wasteType.equals(getFractionType());
    }

    private void use(int weight) {
        this.usedWeight += weight;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public int getWeight() {
        return weight;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public WasteType getFractionType() {
        return fractionType;
    }

    public Discount(float discountPercentage, int weight, LocalDate expiryDate, WasteType fractionType) {
        this.discountPercentage = discountPercentage;
        this.weight = weight;
        this.expiryDate = expiryDate;
        this.fractionType = fractionType;
    }

    public int applyDiscount(int weight, int priceInCent) {
        if (priceInCent == 0) return 0;
//        int discountedWeight = Math.min(weight, this.usedWeight);
        int discountedWeight = weight;
        this.use(discountedWeight);
        float price = discountedWeight * priceInCent * (1 - discountPercentage) + ((weight - discountedWeight) * priceInCent);
        return (int) price;
    }
}
