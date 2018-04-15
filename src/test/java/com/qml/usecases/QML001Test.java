package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.BasicOperations;
import org.superbiz.tf.operation.Variable;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.createSession;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.attribute.Attribute.named;

public class QML001Test {
    // x = tf.Variable(3, name="x")
    // y = tf.Variable(4, name="y")
    // f = x + y
    //
    // with tf.Session() as sess:
    //     init = tf.global_variables_initializer()
    //     init.run()
    //     result = f.eval()

    /**
     * Two integer variables are added.
     */
    @Test
    public void addIntegers() {
        try (QMLContext tf = createSession("TensorFlow")) {
            TF<Variable, Integer> x = tf.variable(value(3), named("x"));
            TF<Variable, Integer> y = tf.variable(value(4), named("y"));
            TF<BasicOperations.Add, Integer> add = x.add(y);

            tf.run(tf.globalVariablesInitializer());
            Integer result = tf.fetch(add);
            assertEquals(7, result.intValue());
        }
    }

    // x = tf.Variable(3.1, name="x")
    // y = tf.Variable(4.2, name="y")
    // f = x + y
    //
    // with tf.Session() as sess:
    //     init = tf.global_variables_initializer()
    //     init.run()
    //     result = f.eval()

    /**
     * Two float variables are added.
     */
    @Test
    public void addFloats() {
        try (QMLContext tf = createSession("TensorFlow")) {
            TF<Variable, Float> x = tf.variable(value(3.1f), named("x"));
            TF<Variable, Float> y = tf.variable(value(4.2f), named("y"));
            TF<BasicOperations.Add, Float> add = x.add(y);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(add);
            assertEquals(7.3, result.floatValue(), 0.001);
        }
    }
}
