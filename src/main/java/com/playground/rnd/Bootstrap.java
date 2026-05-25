package com.playground.rnd;

import com.playground.rnd.discount.DiscountComputationEngine;
import com.playground.rnd.utils.ExpressionDefaults;
import com.playground.rnd.utils.Today;
import com.playground.rnd.models.TransactionRecord;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bootstrap {

    private final DiscountComputationEngine discountComputationEngine;
    private final Today today;

    @PostConstruct
    public void ExecuteBootstrap(){
        log.info("Starting RND Bootstrap operation");

        TransactionRecord transactionRecord = buildTransaction();
        discountComputationEngine.buildContext(transactionRecord,
                ExpressionDefaults.DEFAULT_DISCOUNT_EXPRESSION,
                true);

        var discountValue = discountComputationEngine.evaluateAndGetDiscount(transactionRecord);
        log.info("Discount value for this transaction: {} is {}", transactionRecord, discountValue);
    }


    private TransactionRecord buildTransaction(){
       return TransactionRecord.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType("WEB")
                .timestamp(LocalDateTime.now().minusDays(1))
                .amount(3000L)
                .currency("NGN")
                .merchantId("Filling-Station")
                .build();

    }
}
