package com.dddeurope.recycle.domain.model;

import java.time.LocalDate;

public class DroppedFraction {
    private final LocalDate date;
    private final WasteType wasteType;
    private final int Weight;

    public DroppedFraction(LocalDate date, WasteType wasteType, int weight) {
        this.date = date;
        this.wasteType = wasteType;
        Weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public WasteType getWasteType() {
        return wasteType;
    }

    public int getWeight() {
        return Weight;
    }
}
