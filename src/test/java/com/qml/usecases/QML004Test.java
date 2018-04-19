package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.Constant;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.type.VectorWrapper;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.createContext;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

/**
 * Advanced op operations for scalars and vectors of constants.
 */
public class QML004Test extends AbstractTestBase {

    /**
     * Square is computed from a float constant
     */
    @Test
    public void squareFloat() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Constant, Float> x = tf.constant(value(3.1f), named("x"));
            TF<Operation.Square, Float> square = tf.square(x);
            Float result = tf.fetch(square);
            assertEquals(9.61f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    /**
     * Square is computed from an integer constant
     */
    @Test
    public void squareInteger() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Constant, Integer> x = tf.constant(value(3), named("x"));
            TF<Operation.Square, Integer> square = tf.square(x);
            Integer result = tf.fetch(square);
            assertEquals(9, result.intValue());
        }
    }

    /**
     * Mean is calculated from a float vector constant
     */
    @Test
    public void reduceMeanFloat() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Constant, Float> x = tf.constant(values(1.1f, 2.2f, 3.3f), named("x"));
            TF<Operation.ReduceMean, Float> mean = tf.reduceMean(x);

            Float result = tf.fetch(mean);
            assertEquals(2.2f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    // node {
    //  name: "Const"
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
    //            size: 2
    //          }
    //        }
    //        tensor_content: "ff\346?\315\314\f@"
    //      }
    //    }
    //  }
    //}
    //node {
    //  name: "Cast"
    //  op: "Cast"
    //  input: "Const"
    //  attr {
    //    key: "SrcT"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "DstT"
    //    value {
    //      type: DT_INT32
    //    }
    //  }
    //}
    //node {
    //  name: "init"
    //  op: "NoOp"
    //}
    //versions {
    //  producer: 26
    //}
    /**
     * Cast from float to int
     */
    @Test
    public void castFloatToInt() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Constant, Float> x = tf.constant(values(1.1f, 2.2f, 3.3f), named("x"));
            TF<Operation.ReduceMean, Integer> xInt = tf.cast(x, Integer.class);

            VectorWrapper<Integer> result = tf.fetchVector(xInt);
            assertEquals(Arrays.asList(1, 2, 3), result.getList1D());
        }
    }
}
