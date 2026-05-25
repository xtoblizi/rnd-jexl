package com.playground.rnd.utils;

import lombok.Getter;

@Getter
public class ExpressionDefaults {
    public static final String DEFAULT_DISCOUNT_EXPRESSION = "transactionRecord.amount >= discountAmount " +
            "? transactionRecord.amount - (lowerDiscount * transactionRecord.amount)" +
            ": transactionRecord.amount - (higherDiscount * transactionRecord.amount) ";
}
