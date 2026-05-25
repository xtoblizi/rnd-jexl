package com.playground.rnd.jexlValidators;

import org.apache.commons.jexl3.MapContext;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class JexlValidatorTest {

    @Test
    void findMissingVariables_allVarsPresent_returnsEmpty() {
        MapContext ctx = new MapContext();
        ctx.set("amount", 5000);
        ctx.set("discount", 0.05);

        assertThat(JexlValidator.findMissingVariables("amount * discount", ctx)).isEmpty();
    }

    @Test
    void findMissingVariables_oneVarMissing_returnsMissingVar() {
        MapContext ctx = new MapContext();
        ctx.set("amount", 5000);

        Set<String> missing = JexlValidator.findMissingVariables("amount * discount", ctx);
        assertThat(missing).containsExactly("discount");
    }

    @Test
    void findMissingVariables_emptyContext_returnsAllVariables() {
        Set<String> missing = JexlValidator.findMissingVariables("x + y", new MapContext());
        assertThat(missing).containsExactlyInAnyOrder("x", "y");
    }

    @Test
    void findMissingVariables_literalExpression_returnsEmpty() {
        Set<String> missing = JexlValidator.findMissingVariables("1 + 2 * 3", new MapContext());
        assertThat(missing).isEmpty();
    }

    @Test
    void findMissingVariables_nestedPropertyMissingRoot_returnsRoot() {
        Set<String> missing = JexlValidator.findMissingVariables("record.amount > 100", new MapContext());
        assertThat(missing).containsExactly("record");
    }

    @Test
    void findMissingVariables_nestedPropertyRootPresent_returnsEmpty() {
        MapContext ctx = new MapContext();
        ctx.set("record", new Object());

        Set<String> missing = JexlValidator.findMissingVariables("record.amount > 100", ctx);
        assertThat(missing).isEmpty();
    }

    @Test
    void findMissingVariables_ternaryExpression_detectsAllMissingVars() {
        MapContext ctx = new MapContext();
        ctx.set("amount", 3000);

        Set<String> missing = JexlValidator.findMissingVariables(
                "amount >= threshold ? amount * low : amount * high", ctx);
        assertThat(missing).containsExactlyInAnyOrder("threshold", "low", "high");
    }
}
