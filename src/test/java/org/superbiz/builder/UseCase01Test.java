package org.superbiz.builder;

import org.junit.Test;
import org.superbiz.tf.TF;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.type.Constant;
import org.superbiz.tf.type.Operation;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import static org.junit.Assert.assertEquals;
import static org.superbiz.tf.QMLContext.create;
import static org.superbiz.tf.attribute.Attribute.named;

public class UseCase01Test {
    // import tensorflow as tf
    //
    //zero = tf.constant(0.0)
    //step = tf.constant(1.0)
    //xVar = tf.Variable(initial_value=zero, name="x")
    //x = tf.assign(xVar, zero)
    //xUpdateOp = tf.assign_add(x, step)

    /**
     * Two constants are added.
     */
    @Test
    public void buildGraph() {
        try (QMLContext tf = create("TensorFlow")) {
            TF<Constant> c1 = tf.constant(1.0f, named("start"));
            TF<Constant> c2 = tf.constant(2.0f, named("step"));
            TF<Operation.Add> add = c1.add(c2);

            Graph graph = tf.buildGraph();
            Float result = tf.run(add);
            assertEquals(3.0f, result.floatValue(), 0.000001);
        }
    }
}