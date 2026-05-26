package com.playground.rnd.rulesEngine;

import com.playground.rnd.models.TransactionRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DiscountComputationEngineIntegrationTest {

    @Autowired
    private DiscountComputationEngine discountComputationEngine;

    @Test
    void evaluateAndGetDiscount_largeAmount_appliesLowerDiscount() {
        // Default lowerDiscount=0.02, threshold=2000 → 5000 - (0.02 * 5000) = 4900.0
        TransactionRecord tx = buildTransaction(5000L);

        Object result = discountComputationEngine.evaluateAndGetDiscount(tx);

        assertThat(result).isNotNull();
        assertThat(((Number) result).doubleValue()).isEqualTo(4900.0);
    }

    @Test
    void evaluateAndGetDiscount_smallAmount_appliesHigherDiscount() {
        // Default higherDiscount=0.04, threshold=2000 → 500 - (0.04 * 500) = 480.0
        TransactionRecord tx = buildTransaction(500L);

        Object result = discountComputationEngine.evaluateAndGetDiscount(tx);

        assertThat(result).isNotNull();
        assertThat(((Number) result).doubleValue()).isEqualTo(480.0);
    }

    @Test
    void evaluateAndGetDiscount_resultIsAlwaysLessThanOriginalAmount() {
        TransactionRecord tx = buildTransaction(3000L);

        Object result = discountComputationEngine.evaluateAndGetDiscount(tx);

        assertThat(((Number) result).doubleValue()).isLessThan(3000.0);
    }

    private TransactionRecord buildTransaction(long amount) {
        return TransactionRecord.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType("WEB")
                .timestamp(LocalDateTime.now().minusDays(1))
                .amount(amount)
                .currency("NGN")
                .merchantId("integration-merchant")
                .build();
    }
}
