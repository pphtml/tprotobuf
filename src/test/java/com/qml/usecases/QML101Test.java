package com.qml.usecases;

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

import static org.junit.Assert.assertEquals;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

public class QML101Test extends AbstractTestBase {
    @Test
    public void basicGradientComputation10() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Constant, Float> b = tf.constant(value(3.5f), named("b"));

            TF<Operation.Multiply, Float> c = tf.multiply(a, b, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(2.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation11() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Constant, Float> b = tf.constant(value(3.5f), named("b"));
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));

            TF<Operation.Multiply, Float> c = tf.multiply(b, a, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(2.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation12() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> dummyVar1 = tf.variable(value(6.0f), named("dummyVar1"));
            TF<Variable, Float> dummyVar2 = tf.variable(value(7.0f), named("dummyVar2"));
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Constant, Float> b = tf.constant(value(3.5f), named("b"));

            TF<Operation.Multiply, Float> c = tf.multiply(a, b, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(2.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation13() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> dummyVar1 = tf.variable(value(6.0f), named("dummyVar1"));
            TF<Variable, Float> dummyVar2 = tf.variable(value(7.0f), named("dummyVar2"));
            TF<Constant, Float> b = tf.constant(value(3.5f), named("b"));
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));

            TF<Operation.Multiply, Float> c = tf.multiply(b, a, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(2.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation20() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Variable, Float> b = tf.variable(value(3.5f), named("b"));

            TF<Operation.Multiply, Float> c = tf.multiply(a, b, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(2.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation21() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> b = tf.variable(value(3.5f), named("b"));
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));

            TF<Operation.Multiply, Float> c = tf.multiply(b, a, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(2.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation30() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(3.5f), named("a"));

            TF<Operation.Square, Float> c = tf.square(a, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(7.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation40() {
        try (QMLContext tf = QMLContext.createContext()) {
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

            //TF<Variable, Float> a = tf.variable(value(3.0f), named("a"));

            TF<Constant, Float> xDataTF = tf.constant(values(xData), named("xData"));
            //TF<Operation.Add, Float> b = tf.add(a, xDataTF);
            TF<Operation.ReduceMean, Float> c = tf.reduceMean(xDataTF);

            tf.gradient(c, xDataTF);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(2.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation50() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Variable, Float> b = tf.variable(value(3.5f), named("b"));

            TF<Operation.Subtract, Float> c = tf.subtract(a, b, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(1.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation51() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Variable, Float> b = tf.variable(value(3.5f), named("b"));

            TF<Operation.Subtract, Float> c = tf.subtract(a, b, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, b);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(-1.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation52() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Variable, Float> b = tf.variable(value(3.5f), named("b"));

            TF<Operation.Subtract, Float> c = tf.subtract(a, b, named("c"));

            List<TF<Gradient.Gradients, Float>> gradients = tf.gradients(c, Arrays.asList(a, b));
            tf.run(tf.globalVariablesInitializer());

            assertEquals(2, gradients.size());
            Float resultA = tf.fetch(gradients.get(0));
            assertEquals(1.0f, resultA.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
            Float resultB = tf.fetch(gradients.get(1));
            assertEquals(-1.0f, resultB.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation60() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Variable, Float> b = tf.variable(value(3.5f), named("bb"));

            TF<Operation.Add, Float> c = tf.add(a, b, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, a);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(1.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation61() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("aa"));
            TF<Variable, Float> b = tf.variable(value(3.5f), named("b"));

            TF<Operation.Add, Float> c = tf.add(a, b, named("c"));

            TF<Gradient.Gradients, Float> gradient = tf.gradient(c, b);

            tf.run(tf.globalVariablesInitializer());
            Float result = tf.fetch(gradient);
            assertEquals(1.0f, result.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }

    @Test
    public void basicGradientComputation62() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Variable, Float> b = tf.variable(value(3.5f), named("b"));

            TF<Operation.Add, Float> c = tf.add(a, b, named("c"));

            List<TF<Gradient.Gradients, Float>> gradients = tf.gradients(c, Arrays.asList(a, b));
            tf.run(tf.globalVariablesInitializer());

            assertEquals(2, gradients.size());
            Float resultA = tf.fetch(gradients.get(0));
            assertEquals(1.0f, resultA.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
            Float resultB = tf.fetch(gradients.get(1));
            assertEquals(1.0f, resultB.floatValue(), ROUNDING_ACCEPTABLE_DELTA);
        }
    }


    // difference = tf.subtract(b2, b1, name='difference')
    //
    //1.0
    //-1.0
    //0.9
    //1.0
    //-0.52
    //-1.0
    //0.38
    //
    //difference = tf.subtract(b1, b2, name='difference')
    //
    //1.0
    //-1.0
    //-0.9
    //-1.0
    //0.52
    //1.0
    //-0.38

//    @Test
//    public void basicGradientComputation20() {
//        try (QMLContext tf = QMLContext.createContext()) {
//            TF<Variable, Float> a = tf.variable(value(1.0f), named("a"));
//
//            TF<Operation.Multiply, Float> b1 = tf.multiply(a, tf.constant(value(0.52f), named("c52")), named("b1mul"));
//            TF<Operation.Multiply, Float> b2 = tf.multiply(a, tf.constant(value(0.9f), named("c9")), named("b2mul"));
//
//            TF<Operation.Subtract, Float> difference = tf.subtract(b1, b2, named("difference"));
//
//            TF<Gradient.Gradients, Float> gradients = tf.gradients(difference, Arrays.asList(a));
//
//
//
//            tf.run(tf.globalVariablesInitializer());
//            //VectorWrapper<Float> result = tf.fetchVector(loss);
//            //VectorWrapper<Float> result = tf.fetchVector(gradients);
//            VectorWrapper<Float> result = tf.fetchVector("neg_0");
////            VectorWrapper<Float> result = tf.fetchVector("gradients_0/add_1_grad/BroadcastGradientArgs");
//            System.out.println(result.getList1D());
//            //System.out.println((Float)tf.fetch("b1mul"));
////            assertEquals(7.3, result.FloatValue(), 0.001);
//        }
//    }
}