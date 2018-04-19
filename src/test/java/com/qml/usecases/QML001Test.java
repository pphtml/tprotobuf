package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.operation.Variable;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.createContext;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.attribute.Attribute.named;

public class QML001Test extends AbstractTestBase {
    /**
     * Two integer variables are added.
     */
    @Test
    public void addIntegers() {
        try (QMLContext tf = createContext()) {
            TF<Variable, Integer> x = tf.variable(value(3), named("x"));
            TF<Variable, Integer> y = tf.variable(value(4), named("y"));
            TF<Operation.Add, Integer> add = x.add(y);

            tf.run(tf.globalVariablesInitializer());
            Integer result = tf.fetch(add);
            assertEquals(7, result.intValue());
        }
    }

    /**
     * Two float variables are added.
     */
    @Test
    public void addFloats() {
        try (QMLContext tf = createContext()) {
            TF<Variable, Float> x = tf.variable(value(3.1f), named("x"));
            TF<Variable, Float> y = tf.variable(value(4.2f), named("y"));
            TF<Operation.Add, Float> add = x.add(y);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(add);
            assertEquals(7.3, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }
}
