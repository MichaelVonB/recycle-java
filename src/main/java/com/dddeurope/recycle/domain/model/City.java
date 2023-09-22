package com.dddeurope.recycle.domain.model;

import java.util.Map;

public class City {
    private final String name;
    private final Map<WasteType, Waste> wasteMap;


    public City(String name, Map<WasteType, Waste> wasteMap) {
        this.name = name;
        this.wasteMap = wasteMap;
    }

    public Map<WasteType, Waste> getWasteMap() {
        return wasteMap;
    }

    public String getName() {
        return name;
    }

//    public boolean hasSpecialPrice(DroppedFraction droppedFraction) {
//        this.wasteMap.get(droppedFraction.getWasteType());
//
//    }
}
