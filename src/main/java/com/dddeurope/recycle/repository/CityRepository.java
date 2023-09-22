package com.dddeurope.recycle.repository;

import com.dddeurope.recycle.domain.model.City;
import com.dddeurope.recycle.domain.model.ProgressivePrice;
import com.dddeurope.recycle.domain.model.Waste;
import com.dddeurope.recycle.domain.model.WasteType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CityRepository {
    private final Map<String, City> cities = Map.of("South Park",
            new City("South Park",
                    Map.of(WasteType.GreenWaste, new Waste(12, 50),
                            WasteType.ConstructionWaste, new Waste(18, 100))),
            "Cottington",
            new City("Cottington",
                    Map.of(WasteType.ConstructionWaste,
                            new Waste(25, 50, cottingtonConfig(WasteType.ConstructionWaste)),
                            WasteType.GreenWaste,
                            new Waste(18, 20, cottingtonConfig(WasteType.GreenWaste)))));

    private City defaultCity(String cityName) {
        return new City(cityName, Map.of(
                WasteType.GreenWaste, new Waste(9, 0),
                WasteType.ConstructionWaste, new Waste(15, 0)));
    }


    public City get(String cityName) {
        return cities.getOrDefault(cityName, defaultCity(cityName));
    }

    private List<ProgressivePrice> cottingtonConfig(WasteType wasteType) {
        return switch (wasteType) {
            case ConstructionWaste -> List.of(new ProgressivePrice(12, WasteType.ConstructionWaste, 50));
            case GreenWaste -> List.of(new ProgressivePrice(7, WasteType.GreenWaste, 20),
                    new ProgressivePrice(12, WasteType.GreenWaste, 20));
        };

    }
}
