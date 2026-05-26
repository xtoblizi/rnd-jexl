package com.playground.rnd.utils;

import lombok.Getter;

/**
 * These are default expression. more like initial phase defined expression.
 * Expression can be overridden by custom expression defined by system admin, product admin or ops team from the portal.
 * The v2 defaults are mocks expression expected to be defined by any of the above users of the system, when the adequate controller and endpoint are built.
 */
@Getter
public class ExpressionDefaults {
    public static final String DEFAULT_DISCOUNT_EXPRESSION = "transactionRecord.amount >= discountAmount " +
            "? transactionRecord.amount - (lowerDiscount * transactionRecord.amount)" +
            ": transactionRecord.amount - (higherDiscount * transactionRecord.amount) ";

    public static final String FLAG_TRANSACTION_DEFAULT_v1 = "transactionRecord.amount >= config.flagAmount " +
            "&& customer.kycLevel == 'Level1'";

    public static final String FLAG_TRANSACTION_DEFAULT_v2 = "transactionRecord.amount >= config.flagAmount " +
            "&& (customer.createdAt.plusDays(config.flagPeriodInDays).isAfter(now)) " +
            "&& (customer.kycLevel == 'Level1' || customer.kycLevel == 'Level2') " +
            "&& (!today.isWeekend())";
}
