package com.qml.usecases;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.Variable;
import org.tensorflow.Graph;

import static org.superbiz.tf.QMLContext.createSession;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.attribute.Attribute.named;

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
            // node {
            //  name: "init"
            //  op: "NoOp"
            //  input: "^x/Assign"
            //  input: "^y/Assign"
            //}

            // I tensorflow/core/platform/cpu_feature_guard.cc:140] Your CPU supports instructions that this TensorFlow binary was not compiled to use: SSE4.1 SSE4.2 AVX AVX2 FMA
            //Exception in thread "main" java.lang.IllegalStateException: Attempting to use uninitialized value y
            //	 [[Node: y/read = Identity[T=DT_INT32, _class=["loc:@y"], _device="/job:localhost/replica:0/task:0/device:CPU:0"](y)]]
            //	at org.tensorflow.Session.run(Native Method)
            //	at org.tensorflow.Session.access$100(Session.java:48)
            //init = tf.globalVariablesInitializer();

            TF<Operation.Add> add = x.add(y);
            //TF<Operation.Add> add = c1.add(v1);

            //Graph graph = tf.buildGraph();
            Float result = tf.run(add);
            //assertEquals(3.0f, result.floatValue(), 0.000001);
        }

    }
}
