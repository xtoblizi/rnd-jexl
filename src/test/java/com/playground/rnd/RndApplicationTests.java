package com.playground.rnd;

import com.playground.rnd.rulesEngine.DiscountComputationEngine;
import com.playground.rnd.utils.Today;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RndApplicationTests {

    @Autowired
    private DiscountComputationEngine discountComputationEngine;

    @Autowired
    private Today today;

    @Autowired
    private Map<LocalDate, String> holidays;

    @Test
    void contextLoads() {
    }

    @Test
    void discountComputationEngineBean_isPresent() {
        assertThat(discountComputationEngine).isNotNull();
    }

    @Test
    void todayBean_isPresent() {
        assertThat(today).isNotNull();
    }

    @Test
    void holidaysBean_containsConfiguredDates() {
        assertThat(holidays).isNotEmpty();
        assertThat(holidays).containsKey(LocalDate.of(2026, 1, 30));
        assertThat(holidays).containsKey(LocalDate.of(2026, 5, 25));
    }
}
