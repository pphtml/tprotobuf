package com.qml.usecases;

import com.google.common.primitives.Floats;
import org.flexdata.csv.DataReader;
import org.flexdata.csv.DataRecord;
import org.flexdata.csv.Resource;
import org.junit.Ignore;
import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.Constant;
import org.superbiz.tf.operation.Gradient;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.operation.Variable;
import org.superbiz.tf.type.VectorWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static org.flexdata.csv.CSVReader.CSVReaderBuilder.createCSVReader;
import static org.superbiz.tf.QMLContext.createContext;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

public class QML003Test extends AbstractTestBase {
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
        Random random = new Random(42);
        //float[] floatValues = Floats.toArray(Doubles.asList(values));

//        float[] xData = Floats.toArray(IntStream.range(0, 100).mapToObj(index -> Float.valueOf(random.nextFloat())).collect(Collectors.toList()));
//        float[] yData = Floats.toArray(Floats.asList(xData).stream().map(x -> x * 3.0 + 2.0 + random.nextGaussian() * 0.1).collect(Collectors.toList()));
        //float[] yData = Stream.of(xData).map(x -> x * 3.0 + 2.0 + random.nextGaussian() * 0.1).toArray();

        float[] xData = new float[]{0.5084773f, 0.45050743f, 0.26249877f, 0.42694545f, 0.91235477f, 0.06544802f, 0.3362566f, 0.8263691f, 0.591857f,
                0.97787154f, 0.9754896f, 0.21319687f, 0.14495614f, 0.97184163f, 0.87484f, 0.65439004f, 0.38100997f, 0.7957051f,
                0.93941826f, 0.9843869f, 0.38828883f, 0.92909086f, 0.84629023f, 0.64985895f, 0.9267604f, 0.19523112f, 0.67092854f,
                0.40732116f, 0.07909536f, 0.6977796f, 0.6039431f, 0.50627697f, 0.40087187f, 0.39805484f, 0.6829685f, 0.6030898f,
                0.5765959f, 0.33758417f, 0.76656896f, 0.57604396f, 0.41675377f, 0.93526024f, 0.13862047f, 0.648101f, 0.617069f,
                0.79915774f, 0.4179804f, 0.33842382f, 0.6087638f, 0.9144872f, 0.06256474f, 0.52752024f, 0.7200489f, 0.2947428f,
                0.5073653f, 0.47654673f, 0.39477304f, 0.63190114f, 0.74356943f, 0.7031697f, 0.6964455f, 0.7775143f, 0.59867984f,
                0.2671457f, 0.5138146f, 0.74707365f, 0.60540545f, 0.7542415f, 0.5765414f, 0.9381623f, 0.97237116f, 0.2863439f,
                0.26354256f, 0.7077794f, 0.26621276f, 0.7569819f, 0.3138613f, 0.9421915f, 0.8239749f, 0.2884851f, 0.8364592f,
                0.51019627f, 0.9351209f, 0.8446294f, 0.5395264f, 0.84271336f, 0.97084665f, 0.28346676f, 0.00225191f, 0.72276217f,
                0.03537436f, 0.8671024f, 0.3697224f, 0.8905249f, 0.5418597f, 0.13875394f, 0.9339013f, 0.3908971f, 0.43483633f,
                0.24581f};
        float[] yData = new float[]{3.60012087f, 3.40591076f, 2.8857028f, 3.22463428f, 4.78722158f, 2.17033073f, 3.09580803f, 4.55211963f, 3.70502823f,
                4.89393018f, 4.91716372f, 2.94928737f, 2.51394706f, 5.13519628f, 4.65976456f, 3.9188956f, 3.159699f, 4.44534023f,
                4.81331943f, 4.97442608f, 3.26097968f, 4.5889248f, 4.56280056f, 3.80342606f, 4.76527198f, 2.7698204f, 3.957877f,
                3.28799015f, 2.23208662f, 4.22764853f, 3.74747807f, 3.55969059f, 3.198679f, 2.98834591f, 4.0458736f, 3.93836562f,
                3.79242614f, 3.0216278f, 4.28957376f, 3.85237813f, 3.47936652f, 4.75295462f, 2.31443996f, 3.87077037f, 3.9296794f,
                4.38574428f, 3.17534438f, 3.11335396f, 3.77852262f, 4.64968048f, 2.1767642f, 3.63471464f, 4.06687023f, 2.89278868f,
                3.28672601f, 3.29046424f, 3.20139344f, 4.08341168f, 4.24755614f, 4.0445099f, 4.06186567f, 4.35069294f, 3.81153456f,
                2.88951439f, 3.5000918f, 4.05995477f, 3.66008351f, 4.24301682f, 3.71583879f, 4.79210172f, 4.76766244f, 2.81925223f,
                2.91727671f, 4.08614234f, 2.6590771f, 4.36167308f, 2.97663274f, 4.73814542f, 4.41897807f, 2.8586536f, 4.39075162f,
                3.57420472f, 4.77127177f, 4.5666737f, 3.64622612f, 4.33933459f, 4.92976979f, 2.94056279f, 1.86827826f, 4.21800246f,
                2.20864169f, 4.55911266f, 2.9195204f, 4.80216273f, 3.62375249f, 2.29272854f, 4.85654815f, 3.23401777f, 3.39311598f,
                2.5310189f};


        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(1.0f), named("a"));
            TF<Variable, Float> b = tf.variable(value(0.2f), named("b"));
            //TF<Variable, Float> c = tf.variable(value(1.234f), named("c"));

            TF<Constant, Float> xDataTF = tf.constant(values(xData), named("xData"));
            TF<Constant, Float> yDataTF = tf.constant(values(yData), named("yData"));
            TF<Operation.Add, Float> y = a.multiply(xDataTF).add(b);

            TF<Operation.Subtract, Float> difference = y.subtract(yDataTF, named("difference"));
            TF<Operation.Square, Float> square = tf.square(difference, named("square"));
            TF<Operation.ReduceMean, Float> loss = tf.reduceMean(square, named("loss"));
            //TF<BasicOperation.ReduceMean, Float> loss = tf.reduceMean(tf.square(y.subtract(yDataTF, named("difference")), named("square")), named("loss"));

            TF<Gradient.Gradients, Float> gradients = tf.gradients(loss, Arrays.asList(a, b));



            tf.run(tf.globalVariablesInitializer());
            //VectorWrapper<Float> result = tf.fetchVector(loss);
            VectorWrapper<Float> result = tf.fetchVector(gradients);
            //VectorWrapper<Float> result = tf.fetchVector("gradients_0/Fill");
//            VectorWrapper<Float> result = tf.fetchVector("gradients_0/add_1_grad/BroadcastGradientArgs");
            System.out.println(result.getList1D());
//            assertEquals(7.3, result.FloatValue(), 0.001);
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

//    @Test
//    @Ignore
//    public void lifeSatisfactionLinearRegression() {
//        try (DataReader dataReader = createCSVReader()
//                .withSkipLines(1)
//                .withResource(Resource.fromClasspath("linear_regression.csv"))
//                .build()) {
//            List<DataRecord> dataRecords = dataReader.readAllRecords();
//            System.out.println(dataRecords);
//        }
//    }
}
