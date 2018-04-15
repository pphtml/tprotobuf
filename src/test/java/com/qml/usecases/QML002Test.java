package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.BasicOperations;
import org.superbiz.tf.operation.Constant;
import org.superbiz.tf.type.VectorWrapper;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.*;
import static org.superbiz.tf.attribute.Attribute.named;

/**
 * Basic op operations for scalars and vectors of constants. Operations are add, subtract, multiply and divide.
 */
public class QML002Test {

    /**
     * Two integer constants are added.
     */
    @Test
    public void addIntegers() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Integer> x = tf.constant(value(3), named("x"));
            TF<Constant, Integer> y = tf.constant(value(4), named("y"));
            TF<BasicOperations.Add, Integer> add = x.add(y);

            Integer result = tf.fetch(add);
            assertEquals(7, result.intValue());
        }
    }

    /**
     * Two float constants are added.
     */
    @Test
    public void addFloats() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Float> x = tf.constant(value(3.1f), named("x"));
            TF<Constant, Float> y = tf.constant(value(4.2f), named("y"));
            TF<BasicOperations.Add, Float> add = x.add(y);

            Float result = tf.fetch(add);
            assertEquals(7.3, result.floatValue(), 0.001);
        }
    }

    /**
     * Two float constants are added.
     */
    @Test
    public void addDoubles() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Double> x = tf.constant(value(3.1), named("x"));
            TF<Constant, Double> y = tf.constant(value(4.2), named("y"));
            TF<BasicOperations.Add, Double> add = x.add(y);

            Double result = tf.fetch(add);
            assertEquals(7.3, result.doubleValue(), 0.001);
        }
    }

    /**
     * Two integer constants are multiplied.
     */
    @Test
    public void multiplyIntegers() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Integer> x = tf.constant(value(3), named("x"));
            TF<Constant, Integer> y = tf.constant(value(4), named("y"));
            TF<BasicOperations.Multiply, Integer> multiply = x.multiply(y);

            Integer result = tf.fetch(multiply);
            assertEquals(12, result.intValue());
        }
    }

    /**
     * Two float constants are multiplied.
     */
    @Test
    public void multiplyFloats() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Float> x = tf.constant(value(3.1f), named("x"));
            TF<Constant, Float> y = tf.constant(value(4.2f), named("y"));
            TF<BasicOperations.Multiply, Float> multiply = x.multiply(y);

            Float result = tf.fetch(multiply);
            assertEquals(13.02, result.floatValue(), 0.001);
        }
    }

    // node {
    //  name: "Const_1"
    //  op: "Const"
    //  attr {
    //    key: "value"
    //    value {
    //      tensor {
    //        dtype: DT_INT32
    //        tensor_shape {
    //          dim {
    //            size: 3
    //          }
    //        }
    //        tensor_content: "\001\000\000\000\002\000\000\000\003\000\000\000"
    //      }
    //    }
    //  }
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_INT32
    //    }
    //  }
    //}

    /**
     * Scalar constant and vector constant are multiplied.
     */
    @Test
    public void multiplyIntegersScalarAndVector() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Integer> x = tf.constant(value(4), named("x"));
            TF<Constant, Integer> y = tf.constant(values(1, 2, 3), named("y"));
            TF<BasicOperations.Multiply, Integer> multiply = x.multiply(y);

            VectorWrapper<Integer> result = tf.fetchVector(multiply);
            assertEquals(Arrays.asList(4, 8, 12), result.getList1D());
        }
    }

    /**
     * Scalar constant and vector constant are multiplied.
     */
    @Test
    public void multiplyLongScalarAndVector() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Long> x = tf.constant(value(4L), named("x"));
            TF<Constant, Long> y = tf.constant(values(1L, 2L, 3L), named("y"));
            TF<BasicOperations.Multiply, Long> multiply = x.multiply(y);

            VectorWrapper<Long> result = tf.fetchVector(multiply);
            assertEquals(Arrays.asList(4L, 8L, 12L), result.getList1D());
        }
    }

    // node {
    //  name: "Const_1"
    //  op: "Const"
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "value"
    //    value {
    //      tensor {
    //        dtype: DT_FLOAT
    //        tensor_shape {
    //          dim {
    //            size: 3
    //          }
    //        }
    //        tensor_content: "\315\314\214?\315\314\f@33S@"
    //      }
    //    }
    //  }
    //}

    /**
     * Scalar constant and vector constant are multiplied.
     */
    @Test
    public void multiplyFloatScalarAndVector() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Float> x = tf.constant(value(4.4f), named("x"));
            TF<Constant, Float> y = tf.constant(values(1.1f, 2.2f, 3.3f), named("y"));
            TF<BasicOperations.Multiply, Float> multiply = x.multiply(y);

            VectorWrapper<Float> result = tf.fetchVector(multiply);
            assertEquals(Arrays.asList(4.84f, 9.68f, 14.52f), result.getList1D());
        }
    }

    /**
     * Scalar constant and vector constant are multiplied.
     */
    @Test
    public void multiplyDoubleScalarAndVector() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Double> x = tf.constant(value(4.4), named("x"));
            TF<Constant, Double> y = tf.constant(values(1.1, 2.2, 3.3), named("y"));
            TF<BasicOperations.Multiply, Double> multiply = x.multiply(y);

            VectorWrapper<Double> result = tf.fetchVector(multiply);
            assertEquals(Arrays.asList(4.84, 9.68, 14.52), result.getList1D());
        }
    }

    /**
     * Two float constants are divided.
     */
    @Test
    public void divideFloats() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Float> x = tf.constant(value(3.1f), named("x"));
            TF<Constant, Float> y = tf.constant(value(4.2f), named("y"));
            TF<BasicOperations.Divide, Float> divide = x.divide(y);

            Float result = tf.fetch(divide);
            assertEquals(0.738095223903656, result.floatValue(), 0.000001);
        }
    }

    /**
     * Two double constants are divided.
     */
    @Test
    public void divideDouble() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Double> x = tf.constant(value(3.2), named("x"));
            TF<Constant, Double> y = tf.constant(value(4.2), named("y"));
            TF<BasicOperations.Divide, Double> divide = x.divide(y);

            Double result = tf.fetch(divide);
            assertEquals(0.761904776096344, result.floatValue(), 0.000001);
        }
    }

//    /**
//     * Two integer constants are divided.
//     */
//    @Test
//    public void divideIntegers() {
//        try (QMLContext tf = createSession()) {
//            TF<Constant, Integer> x = tf.constant(value(3), named("x"));
//            TF<Constant, Integer> y = tf.constant(value(4), named("y"));
//            TF<BasicOperations.Divide, Integer> divide = x.divideWithCastToFloat(y);
//            divide.castTo(Float.class);
//
//            Double result = tf.fetch(divide);
//            assertEquals(0.12345678, result.floatValue(), 0.001);
//        }
//    }

    // TODO divideLongs

    /**
     * Two float constants are added.
     */
    @Test
    public void subtractFloats() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Float> x = tf.constant(value(18.1f), named("x"));
            TF<Constant, Float> y = tf.constant(value(3.2f), named("y"));
            TF<BasicOperations.Subtract, Float> subtract = x.subtract(y);

            Float result = tf.fetch(subtract);
            assertEquals(14.9, result.floatValue(), 0.000001);
        }
    }

}
