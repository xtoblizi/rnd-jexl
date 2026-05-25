package com.playground.rnd.discount;
import com.playground.rnd.jexlValidators.JexlValidator;
import com.playground.rnd.models.TransactionRecord;
import com.playground.rnd.utils.ExpressionDefaults;
import com.playground.rnd.utils.Today;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.introspection.JexlPermissions;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class DiscountComputationEngine {

    private final Today today;

    @Value("${lower.bound.amount.discount:0.02}")
    private Double lowerDiscount;

    @Value("${higher.bound.amount.discount:0.04}")
    private Double higherDiscount;

    @Value("${transaction.discount.amount:2000}")
    private Double discountAmount;

    public String discountComputationExpression;
    public JexlContext jexlParamsContext;
    private static final String DEFAULT_DISCOUNT_EXPRESSION = ExpressionDefaults.DEFAULT_DISCOUNT_EXPRESSION;

    private static boolean isContextLoaded = false;

    /**
     * build context for the expression
     * @param transactionRecord
     */
    public void buildContext(TransactionRecord transactionRecord,
                             @Nullable String customExpression,
                             boolean useCustomExpression)
    {
        setExpression(customExpression, useCustomExpression);
        this.jexlParamsContext = getContext(transactionRecord);
        var missing = JexlValidator.findMissingVariables(this.discountComputationExpression, this.jexlParamsContext);
        if(!missing.isEmpty()){
            throw new RuntimeException("Invalid expression or map of parameters");
        }
        isContextLoaded = true;
    }

    /**
     * Create and return a sample expression that utilizes the fields of the transaction record object to
     * perform discount evaluation.
     * @return
     */
    private void setExpression(@Nullable String customExpression, boolean useCustomExpression) {
        if(!Strings.isBlank(customExpression) && useCustomExpression){
            this.discountComputationExpression = customExpression;
        }else {
            this.discountComputationExpression = DEFAULT_DISCOUNT_EXPRESSION;
        }
    }

    /**
     * Build
     * @param transactionRecord
     * @return
     */
    private JexlContext getContext(TransactionRecord transactionRecord){
        return new MapContext(Map.of(
                "transactionRecord", transactionRecord,
                "now", LocalDateTime.now(),
                "today", today,
                "lowerDiscount", lowerDiscount,
                "higherDiscount", higherDiscount,
                "discountAmount", discountAmount
        ));
    }

    /**
     * Build an instance of the Jexl engine with basic security in mind using:(class and package limiting)
     * @return
     */
    private JexlEngine buildJexlEngine(){
        JexlFeatures features = new JexlFeatures()
                .loops(false)
                .sideEffectGlobal(false)
                .sideEffect(false);

        JexlPermissions classLimit = new JexlPermissions.ClassPermissions(java.lang.Math.class,
                com.playground.rnd.models.TransactionRecord.class);
        JexlPermissions combinedPermissions = classLimit.compose("java.util.*", "java.time.*");

        return  new JexlBuilder()
                .features(features)
                .permissions(combinedPermissions)
                .strict(true)
                .create();
    }

    /**
     * Perform evaluation of the expression with the provided instance/object TransactionRecord
     * The end result should return the discount amount
     * @param transactionRecord
     * @return
     */
    public Object evaluateAndGetDiscount (TransactionRecord transactionRecord){
        if(!isContextLoaded){
            throw new RuntimeException("Valid JEXL expression or context not set");
        }
        JexlEngine jexlEngine = buildJexlEngine();
        var jexlExpression = jexlEngine.createExpression(this.discountComputationExpression);

        var discountedAmount = jexlExpression.evaluate(getContext(transactionRecord));
        System.out.println("Calculated Discount is => "+ discountedAmount);
        return discountedAmount;
    }
}
