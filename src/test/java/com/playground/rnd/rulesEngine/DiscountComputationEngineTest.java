package com.playground.rnd.rulesEngine;

import com.playground.rnd.models.TransactionRecord;
import com.playground.rnd.utils.Today;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DiscountComputationEngineTest {

    @Mock
    private Today today;

    private DiscountComputationEngine engine;

    @BeforeEach
    void setUp() {
        engine = new DiscountComputationEngine(today);
        ReflectionTestUtils.setField(engine, "lowerDiscount", 0.02);
        ReflectionTestUtils.setField(engine, "higherDiscount", 0.04);
        ReflectionTestUtils.setField(engine, "discountAmount", 2000.0);
    }

    @Test
    void evaluateAndGetDiscount_amountAboveThreshold_appliesLowerDiscount() {
        // 3000 >= 2000 → 3000 - (0.02 * 3000) = 2940.0
        Object result = engine.evaluateAndGetDiscount(buildTransaction(3000L));
        assertThat(((Number) result).doubleValue()).isEqualTo(2940.0);
    }

    @Test
    void evaluateAndGetDiscount_amountBelowThreshold_appliesHigherDiscount() {
        // 1000 < 2000 → 1000 - (0.04 * 1000) = 960.0
        Object result = engine.evaluateAndGetDiscount(buildTransaction(1000L));
        assertThat(((Number) result).doubleValue()).isEqualTo(960.0);
    }

    @Test
    void evaluateAndGetDiscount_amountAtExactThreshold_appliesLowerDiscount() {
        // 2000 >= 2000 → 2000 - (0.02 * 2000) = 1960.0
        Object result = engine.evaluateAndGetDiscount(buildTransaction(2000L));
        assertThat(((Number) result).doubleValue()).isEqualTo(1960.0);
    }

    @Test
    void evaluateAndGetDiscount_returnsNonNullResult() {
        Object result = engine.evaluateAndGetDiscount(buildTransaction(5000L));
        assertThat(result).isNotNull();
    }

    @Test
    void evaluateAndGetDiscount_resultIsLessThanOriginalAmount() {
        long amount = 4000L;
        Object result = engine.evaluateAndGetDiscount(buildTransaction(amount));
        assertThat(((Number) result).doubleValue()).isLessThan(amount);
    }

    private TransactionRecord buildTransaction(long amount) {
        return TransactionRecord.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType("WEB")
                .timestamp(LocalDateTime.now())
                .amount(amount)
                .currency("NGN")
                .merchantId("test-merchant")
                .build();
    }
}
