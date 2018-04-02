package com.qml.usecases;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.Variable;
import org.tensorflow.Graph;

import static org.superbiz.tf.QMLContext.createSession;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.attribute.Attribute.named;
import static org.superbiz.tf.type.Shape.shape;

public class Ex01 {
    // x = tf.Variable(3, name="x")
    //y = tf.Variable(4, name="y")
    //f = x*x*y + y + 2
    //
    //with tf.Session() as sess:
    //    # x.initializer.run()
    //    # y.initializer.run()
    //    init = tf.global_variables_initializer()
    //    init.run()
    //    result = f.eval()
    //    print(result)

    public static void main(String[] args) {
        try (QMLContext tf = createSession("TensorFlow")) {
            TF<Variable> x = tf.variable(value(3), named("x"));
            TF<Variable> y = tf.variable(value(4), named("y"));

            TF<Operation.Add> add = x.add(y);
            //TF<Operation.Add> add = c1.add(v1);

            Graph graph = tf.buildGraph();
            Float result = tf.run(add);
            //assertEquals(3.0f, result.floatValue(), 0.000001);
        }

    }
}
