package com.dddeurope.recycle.application;

import com.dddeurope.recycle.domain.model.WasteType;

public class NeededExemptionRules {
    private final int year;
    private final WasteType wasteType;

    public NeededExemptionRules(int year, WasteType wasteType) {
        this.year = year;
        this.wasteType = wasteType;
    }

    public int getYear() {
        return year;
    }

    public WasteType getWasteType() {
        return wasteType;
    }
}
