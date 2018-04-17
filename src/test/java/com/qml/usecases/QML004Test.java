package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.BasicOperation;
import org.superbiz.tf.operation.Constant;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.type.VectorWrapper;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.createSession;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

/**
 * Advanced op operations for scalars and vectors of constants.
 */
public class QML004Test extends AbstractTestBase {


//    TF<BasicOperation.Square, Double> square = tf.square(difference);
//    TF<BasicOperation.ReduceMean, Double> reduceMean = tf.reduceMean(tf.square(y.subtract(yDataTF, named("difference")), named("square")), named("mean"));
//
    /**
     * Square is computed from a float constant
     */
    @Test
    public void squareFloat() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Float> x = tf.constant(value(3.1f), named("x"));
            TF<Operation.Square, Float> square = tf.square(x);
            Float result = tf.fetch(square);
            assertEquals(9.61f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    /**
     * Mean is calculated from a float vector constant
     */
    @Test
    public void multiplyFloatScalarAndVector() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Float> x = tf.constant(values(1.1f, 2.2f, 3.3f), named("y"));
            TF<Operation.ReduceMean, Float> mean = tf.reduceMean(x);

            Float result = tf.fetch(mean);
            assertEquals(2.2f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

}
