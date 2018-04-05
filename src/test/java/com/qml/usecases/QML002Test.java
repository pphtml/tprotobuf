package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.Variable;

import static org.superbiz.tf.QMLContext.createSession;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.attribute.Attribute.named;

public class QML002Test {
    // import tensorflow as tf
    //
    //zero = tf.constant(0.0)
    //step = tf.constant(1.0)
    //xVar = tf.Variable(initial_value=zero, name="x")
    //x = tf.assign(xVar, zero)
    //xUpdateOp = tf.assign_add(x, step)

//    /**
//     * Two constants are added.
//     */
//    @Test
//    public void buildGraph() {
//        try (QMLContext tf = createSession("TensorFlow")) {
//            TF<Variable> x = tf.variable(value(3), named("x"));
//            TF<Variable> y = tf.variable(value(4), named("y"));
//            TF<Operation.Add> add = x.add(y);
//
//            tf.run(tf.globalVariablesInitializer());
//            Float result = tf.fetch(add);
//        }
//    }
}
