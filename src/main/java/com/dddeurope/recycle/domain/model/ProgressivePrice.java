package com.dddeurope.recycle.domain.model;

public class ProgressivePrice {
    private final int price;
    private final WasteType wasteType;
    private final int quota;

    public ProgressivePrice(int price, WasteType wasteType, int quota) {
        this.price = price;
        this.wasteType = wasteType;
        this.quota = quota;
    }

    public int getPrice() {
        return price;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public int getQuota() {
        return quota;
    }
}
