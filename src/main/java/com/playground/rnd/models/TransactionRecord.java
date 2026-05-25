package com.playground.rnd.models;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TransactionRecord {
    private String transactionId;
    private Long amount;
    private String currency;
    private String transactionType;
    private LocalDateTime timestamp;
    private String merchantId;

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", timestamp=" + timestamp +
                ", merchantId='" + merchantId + '\'' +
                '}';
    }
}
