package com.playground.rnd;

import com.playground.rnd.rulesEngine.DiscountComputationEngine;
import com.playground.rnd.rulesEngine.FraudEvaluationEngine;
import com.playground.rnd.models.Customer;
import com.playground.rnd.models.KycLevel;
import com.playground.rnd.models.MembershipType;
import com.playground.rnd.utils.ExpressionDefaults;
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
    private final FraudEvaluationEngine fraudEvaluationEngine;

    @PostConstruct
    public void ExecuteTransactionDiscountComputation(){
        log.info("Starting RND Transaction Discount Computation Operation");

        TransactionRecord transactionRecord = buildV1Transaction();
        discountComputationEngine.buildContext(transactionRecord,
                ExpressionDefaults.DEFAULT_DISCOUNT_EXPRESSION,
                true);

        var discountValue = discountComputationEngine.evaluateAndGetDiscount(transactionRecord);
        log.info("--------------------------------------Starting RND for Discount Computation v1-------------------------------------------");

        log.info("Transaction details: {}", transactionRecord);
        log.info("Original Transaction Amount: {}", transactionRecord.getAmount());
        log.info("Discounted Transaction Amount: {}", discountValue);

        log.info("------------------------------------- Closed Discount Computation --------------------------------------- \n \n");
    }

    @PostConstruct
    public void ExecuteCustomerTransactionFraudEvaluation(){
        log.info("------------------------------ Starting RND for Transaction Fraud Evaluation v1-------------------------");
        var customer = buildV1Customer();
        var transaction = buildV1Transaction();

        fraudEvaluationEngine.configureContext(transaction, customer);
        fraudEvaluationEngine.build(null, false);
        var isTransactionFraudulent = fraudEvaluationEngine.isTransactionSuspicious(transaction, customer);

        log.info("Transaction considered suspicious or fraudulent : {}", isTransactionFraudulent);
        log.info("Expression Used : {}", ExpressionDefaults.FLAG_TRANSACTION_DEFAULT_v1);

        log.info("--------------------------------- Completed the Fraud Evaluation Review ------------------------------------- \n \n");
    }


    @PostConstruct
    public void ExecuteCustomerTransactionFraudEvaluationV2(){
        log.info("------------------------------ Starting RND for Transaction Fraud Evaluation v1-------------------------");
        var customer = buildV2Customer();
        var transaction = buildV2Transaction();

        fraudEvaluationEngine.configureContext(transaction, customer);
        fraudEvaluationEngine.build(ExpressionDefaults.FLAG_TRANSACTION_DEFAULT_v2, true);
        var isTransactionFraudulent = fraudEvaluationEngine.isTransactionSuspicious(transaction, customer);

        log.info("Transaction considered suspicious or fraudulent : {}", isTransactionFraudulent);
        log.info("Expression Used : {}", ExpressionDefaults.FLAG_TRANSACTION_DEFAULT_v2);

        log.info("--------------------------------- Completed the Fraud Evaluation Review ------------------------------------- \n \n");
    }


    private TransactionRecord buildV1Transaction(){
       return TransactionRecord.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType("WEB")
                .timestamp(LocalDateTime.now().minusDays(1))
                .amount(3000L)
                .currency("NGN")
                .merchantId("Filling-Station")
                .build();

    }

    private TransactionRecord buildV2Transaction(){
        return TransactionRecord.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType("POS")
                .timestamp(LocalDateTime.now().minusDays(1))
                .amount(200000L)
                .currency("NGN")
                .merchantId("Filling-Station")
                .build();

    }

    private Customer buildV1Customer(){
        return Customer.builder()
                .firstName("Chris")
                .lastName("Odegard")
                .MembershipType(MembershipType.GOLD)
                .kycLevel(KycLevel.Level1)
                .createdAt(LocalDateTime.now().minusDays(10))
                .build();

    }

    private Customer buildV2Customer(){
        return Customer.builder()
                .firstName("Eze")
                .lastName("Eberechi")
                .MembershipType(MembershipType.GOLD)
                .kycLevel(KycLevel.Level2)
                .createdAt(LocalDateTime.now().minusDays(6))
                .build();

    }
}
