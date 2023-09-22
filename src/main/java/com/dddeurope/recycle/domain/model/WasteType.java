package com.dddeurope.recycle.domain.model;

public enum WasteType {
    ConstructionWaste,
    GreenWaste;

    public static WasteType of(String value) {
        return switch (value) {
            case "Green waste" -> GreenWaste;
            case "Construction waste" -> ConstructionWaste;
            default -> throw new IllegalArgumentException("No waste type found for " + value);
        };
    }
    
}
