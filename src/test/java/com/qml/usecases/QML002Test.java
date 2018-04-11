package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.Constant;
import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.VectorWrapper;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.*;
import static org.superbiz.tf.attribute.Attribute.named;

public class QML002Test {
    // w = tf.constant(3)
    // x = w + 2
    //
    // with tf.Session() as sess:
    //     result = x.eval()


    /**
     * Two integer constants are added.
     */
    @Test
    public void addIntegers() {
        try (QMLContext tf = createSession()) {
            TF<Constant, Integer> x = tf.constant(value(3), named("x"));
            TF<Constant, Integer> y = tf.constant(value(4), named("y"));
            TF<Operation.Add, Integer> add = x.add(y);

            // tf.run(tf.globalVariablesInitializer());
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
            TF<Operation.Add, Float> add = x.add(y);

            // tf.run(tf.globalVariablesInitializer());
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
            TF<Operation.Add, Double> add = x.add(y);

            // tf.run(tf.globalVariablesInitializer());
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
            TF<Operation.Multiply, Integer> multiply = x.multiply(y);

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
            TF<Operation.Multiply, Float> multiply = x.multiply(y);

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
            TF<Operation.Multiply, Integer> multiply = x.multiply(y);

            VectorWrapper<Integer> result = tf.fetchVector(multiply);
            System.out.println(result.getList1D());
            // TODO [4, 8, 12]
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
            TF<Operation.Multiply, Float> multiply = x.multiply(y);

            VectorWrapper<Float> result = tf.fetchVector(multiply);
            System.out.println(result.getList1D());
            // TODO [ 4.84  9.68 14.52]
        }
    }
}
