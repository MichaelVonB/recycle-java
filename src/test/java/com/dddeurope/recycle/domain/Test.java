package com.dddeurope.recycle.domain;

import com.dddeurope.recycle.domain.model.Account;
import com.dddeurope.recycle.domain.model.City;
import com.dddeurope.recycle.domain.model.Waste;
import com.dddeurope.recycle.domain.model.WasteType;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Test {

    @org.junit.jupiter.api.Test
    void test() {
        Account account = new Account("foo",
                new City("Springfield",
                        Map.of(WasteType.ConstructionWaste,
                                new Waste(10, 100),
                                WasteType.GreenWaste,
                                new Waste(10, 100))));
        account.addWaste(LocalDate.parse("2023-03-10"), WasteType.ConstructionWaste, 60);
        account.addWaste(LocalDate.parse("2023-03-10"), WasteType.GreenWaste, 30);
        account.addWaste(LocalDate.parse("2023-04-23"), WasteType.ConstructionWaste, 30);
        account.addWaste(LocalDate.parse("2023-04-23"), WasteType.GreenWaste, 20);
        account.addWaste(LocalDate.parse("2023-05-05"), WasteType.ConstructionWaste, 20);
        account.addWaste(LocalDate.parse("2023-05-05"), WasteType.GreenWaste, 10);

        account.initializeGeneralExemptionRules();

        assertThat(account).isNotNull();
    }

    @org.junit.jupiter.api.Test
    void test2() {
        LocalDate parse = LocalDate.parse("2023-03-10");
        int i = parse.compareTo(LocalDate.parse("2023-04-11"));

        assertThat(i).isEqualTo(-1);
    }
}
