package com.qml.usecases;

import static org.superbiz.tf.QMLContext.createContext;
import static org.superbiz.tf.QMLContext.value;

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

//    public static void main(String[] args) {
//        try (QMLContext tf = createContext("TensorFlow")) {
//            TF<Variable> x = tf.variable(value(3), named("x"));
//            TF<Variable> y = tf.variable(value(4), named("y"));
//            TF<BasicOperation.Add> add = x.add(y);
//
//            tf.run(tf.globalVariablesInitializer());
//            Float result = tf.fetch(add);
//        }
//
//    }
}
