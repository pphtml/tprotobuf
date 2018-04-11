package com.qml.usecases;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import org.flexdata.csv.DataReader;
import org.flexdata.csv.DataRecord;
import org.flexdata.csv.Resource;
import org.junit.Ignore;
import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.Constant;
import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.Variable;
import org.superbiz.tf.type.VectorWrapper;

import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static org.flexdata.csv.CSVReader.CSVReaderBuilder.createCSVReader;
import static org.superbiz.tf.QMLContext.createSession;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

public class QML003Test {
// import numpy as np
//import tensorflow as tf
//
//x_data = np.random.rand(100).astype(np.float32)
//print(x_data)
//
//y_data = x_data * 3 + 2
//y_data = np.vectorize(lambda y: y + np.random.normal(loc=0.0, scale=0.1))(y_data)
//print(y_data)
//
//print(zip(x_data, y_data)[0:5])
//
//a = tf.Variable(1.0)
//b = tf.Variable(0.2)
//y = a * x_data + b
//
//loss = tf.reduce_mean(tf.square(y - y_data))
//
//optimizer = tf.train.GradientDescentOptimizer(0.5)
//train = optimizer.minimize(loss)
//
//init = tf.global_variables_initializer()
//
//graph_def = tf.get_default_graph().as_graph_def()
//tf.train.write_graph(graph_def, '../../../../../../src/main/resources/com/qml/usecases', 'ex03.pb', False)
//
//sess = tf.Session()
//sess.run(init)
//
//train_data = []
//for step in range(100):
//    evals = sess.run([train, a, b])[1:]
//    if step % 5 == 0:
//        print(step, evals)
//        train_data.append(evals)
    @Test
    public void basicLinearRegression() {
        // TODO predelat na float az bude chodit
        Random random = new Random(42);
        double[] xData = IntStream.range(0, 100).mapToDouble(index -> random.nextDouble()).toArray();
        double[] yData = DoubleStream.of(xData).map(x -> x * 3.0 + 2.0 + random.nextGaussian() * 0.1).toArray();

        try (QMLContext tf = createSession()) {
            TF<Variable, Double> a = tf.variable(value(1.0), named("a"));
            TF<Variable, Double> b = tf.variable(value(0.2), named("b"));
            TF<Constant, Double> xDataTF = tf.constant(values(xData), named("xData"));
            TF<Constant, Double> yDataTF = tf.constant(values(yData), named("yData"));
            TF<Operation.Add, Double> y = a.multiply(xDataTF).add(b);

            // TODO Operation.Square
            TF<Operation.Subtract, Double> difference = y.subtract(yDataTF, named("difference"));
            TF<Operation.Square, Double> square = tf.square(difference);
            //loss = tf.reduce_mean(tf.square(y.subtract(yDataTF, named("difference"))));

            tf.run(tf.globalVariablesInitializer());
            VectorWrapper<Double> result = tf.fetchVector(square);
            System.out.println(result.getList1D());
//            assertEquals(7.3, result.doubleValue(), 0.001);
        }


        //a = tf.Variable(1.0)
        //b = tf.Variable(0.2)
        //y = a * x_data + b
        //
        //loss = tf.reduce_mean(tf.square(y - y_data))
        //
        //optimizer = tf.train.GradientDescentOptimizer(0.5)
        //train = optimizer.minimize(loss)
        //
        //init = tf.global_variables_initializer()
        //
        //graph_def = tf.get_default_graph().as_graph_def()
        //tf.train.write_graph(graph_def, '../../../../../../src/main/resources/com/qml/usecases', 'ex03.pb', False)
        //
        //sess = tf.Session()
        //sess.run(init)
        //
        //train_data = []
        //for step in range(100):
        //    evals = sess.run([train, a, b])[1:]
        //    if step % 5 == 0:
        //        print(step, evals)
        //        train_data.append(evals)
    }

    @Test
    @Ignore
    public void lifeSatisfactionLinearRegression() {
        try (DataReader dataReader = createCSVReader()
                .withSkipLines(1)
                .withResource(Resource.fromClasspath("linear_regression.csv"))
                .build()) {
            List<DataRecord> dataRecords = dataReader.readAllRecords();
            System.out.println(dataRecords);
        }
    }
}
