package com.playground.rnd.jexlValidators;

import org.apache.commons.jexl3.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class JexlValidator {

    public static Set<String> findMissingVariables(String expressionString, JexlContext context) {
        // 1. Initialize a basic, strict engine just for parsing
        JexlEngine jexl = new JexlBuilder().strict(true).create();

        // 2. CRITICAL FIX: Create a Script instead of an Expression
        JexlScript script = jexl.createScript(expressionString);

        // 3. Extract all variables required by this expression
        // This returns a Set of Lists representing variable paths (e.g., [['user', 'name'], ['tax']])
        Set<List<String>> requiredVariablePaths = script.getVariables();

        Set<String> missingVariables = new HashSet<>();

        // 4. Check if the root variable of each path exists in the context
        for (List<String> path : requiredVariablePaths) {
            if (!path.isEmpty()) {
                String rootVariable = path.getFirst(); // The top-level variable name

                if (!context.has(rootVariable)) {
                    missingVariables.add(rootVariable);
                }
            }
        }

        return missingVariables;
    }

    public static void validateExpression (String expressionString, JexlContext context) {
        // 1. Initialize a basic, strict engine just for parsing
        JexlEngine jexl = new JexlBuilder().strict(true).create();

        // 2. CRITICAL FIX: Create a Script instead of an Expression
        JexlScript script = jexl.createScript(expressionString);

        // 3. Extract all variables required by this expression
        // This returns a Set of Lists representing variable paths (e.g., [['user', 'name'], ['tax']])
        Set<List<String>> requiredVariablePaths = script.getVariables();

        Set<String> missingVariables = new HashSet<>();

        // 4. Check if the root variable of each path exists in the context
        for (List<String> path : requiredVariablePaths) {
            if (!path.isEmpty()) {
                String rootVariable = path.getFirst(); // The top-level variable name

                if (!context.has(rootVariable)) {
                    missingVariables.add(rootVariable);
                }
            }
        }

        if(!missingVariables.isEmpty()) {
            throw new RuntimeException(String.format("Invalid expression or parameter context. Missing variable: %s", missingVariables));
        }
    }
}
