package com.dddeurope.recycle.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Waste {
    private int price;
    private int exemptionRuleInKgPerYear;
    private List<ProgressivePrice> progressivePrices = new ArrayList<>();

    public Waste(int price, int exemptionRuleInKgPerYear) {
        this.price = price;
        this.exemptionRuleInKgPerYear = exemptionRuleInKgPerYear;
    }

    public Waste() {

    }

    public Waste(int price, int exemptionRuleInKgPerYear, List<ProgressivePrice> progressivePrices) {
        this.price = price;
        this.exemptionRuleInKgPerYear = exemptionRuleInKgPerYear;
        this.progressivePrices = progressivePrices;
    }

    public int getPrice() {
        return price;
    }

    public int getExemptionRuleInKgPerYear() {
        return exemptionRuleInKgPerYear;
    }

    public List<ProgressivePrice> getProgressivePrices() {
        return progressivePrices;
    }
}
