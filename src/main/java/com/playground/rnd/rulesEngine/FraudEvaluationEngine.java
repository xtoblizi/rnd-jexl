package com.playground.rnd.rulesEngine;

import com.playground.rnd.configs.FlagTransactionConfig;
import com.playground.rnd.jexlValidators.JexlValidator;
import com.playground.rnd.models.Customer;
import com.playground.rnd.models.TransactionRecord;
import com.playground.rnd.utils.ExpressionDefaults;
import com.playground.rnd.utils.Today;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.introspection.JexlPermissions;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class FraudEvaluationEngine {

    private final Today today;
    private final FlagTransactionConfig flagTransactionConfig; // can be replaced with a service to fetch the config from db or redis
    private static final String FLAG_TRANSACTION_DEFAULT = ExpressionDefaults.FLAG_TRANSACTION_DEFAULT_v1;
    private JexlContext jexlParamsContext;
    private String expression;

    /**
     * build context for the expression
     * @param customExpression, userCustomExpression
     */
    public void build(@Nullable String customExpression, boolean useCustomExpression)
    {
        setExpression(customExpression, useCustomExpression);
        if(jexlParamsContext == null){
            throw new RuntimeException("Context has not been configured. Provide the needed object variables via the @configureContext method");
        }

        if(Strings.isBlank(this.expression)){
            throw new RuntimeException("Business rule expression for the fraud evaluation not set");
        }

        JexlValidator.validateExpression(this.expression, this.jexlParamsContext);
    }

    /**
     * Set expression that utilizes the fields of the objects provided in the @configureContext function.
     * perform discount evaluation.
     * @return
     */
    private void setExpression (@Nullable String customExpression,
                               boolean useCustomExpression) {
        if(!Strings.isBlank(customExpression) && useCustomExpression){
            this.expression = customExpression;
        }else {
            this.expression = FLAG_TRANSACTION_DEFAULT;
        }
    }


    public void configureContext(TransactionRecord transactionRecord,
                                 Customer customer){
        this.jexlParamsContext = new MapContext(Map.of(
                "transactionRecord", transactionRecord,
                "now", LocalDateTime.now(),
                "today", today,
                "config", flagTransactionConfig,
                "customer", customer
        ));
    }

    /**
     * Build an instance of the Jexl engine with basic security in mind using:(class and package limiting)
     * @return JexlEngine
     */
    private JexlEngine buildJexlEngine(){
        JexlFeatures features = new JexlFeatures()
                .loops(false)
                .sideEffectGlobal(false)
                .sideEffect(false);

        JexlPermissions classLimit = new JexlPermissions.ClassPermissions(java.lang.Math.class,
                com.playground.rnd.configs.FlagTransactionConfig.class,
                com.playground.rnd.utils.Today.class
        );
        JexlPermissions combinedPermissions = classLimit.compose("java.util.*",
                "java.time.*",
                "com.playground.rnd.models.*");

        return  new JexlBuilder()
                .features(features)
                .permissions(combinedPermissions)
                .strict(true)
                .create();
    }


    public boolean isTransactionSuspicious(TransactionRecord transactionRecord,
                                           Customer customer){

        if(Strings.isBlank(this.expression) || this.jexlParamsContext == null)
            throw new RuntimeException("Required objects not set. Kindly set the JexlContext via @configureContext and call the build action");

        JexlEngine jexlEngine = buildJexlEngine();
        var jexlExpression = jexlEngine.createExpression(this.expression);
        var result = jexlExpression.evaluate(jexlParamsContext);
        var booleanResult = (Boolean)result;

        System.out.printf("Is Customer transaction flagged fraudulent/suspicous: %s%n", (booleanResult == true ? "YES": "NO"));
        return  (Boolean)result;
    }
}
