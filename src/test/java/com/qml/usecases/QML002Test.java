package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.Constant;
import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.Variable;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.createSession;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.attribute.Attribute.named;

public class QML002Test {
    // w = tf.constant(3)
    // x = w + 2
    //
    // with tf.Session() as sess:
    //     result = x.eval()


    /**
     * Two integer variables are added.
     */
    @Test
    public void addIntegers() {
        try (QMLContext tf = createSession("TensorFlow")) {
            TF<Constant, Integer> x = tf.constant(value(3), named("x"));
            TF<Constant, Integer> y = tf.constant(value(4), named("y"));
            TF<Operation.Add, Integer> add = x.add(y);

            // tf.run(tf.globalVariablesInitializer());
            Integer result = tf.fetch(add);
            assertEquals(7, result.intValue());
        }
    }
}
