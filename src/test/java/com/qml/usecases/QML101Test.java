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
import java.util.Random;

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

            TF<Gradient.Gradients, Float> gradients = tf.gradients(c, Arrays.asList(a));

            tf.run(tf.globalVariablesInitializer());
            VectorWrapper<Float> result = tf.fetchVector(gradients);
            System.out.println(result.getList1D());
        }
    }

    @Test
    public void basicGradientComputation11() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Constant, Float> b = tf.constant(value(3.5f), named("b"));

            TF<Operation.Multiply, Float> c = tf.multiply(b, a, named("c"));

            TF<Gradient.Gradients, Float> gradients = tf.gradients(c, Arrays.asList(a));

            tf.run(tf.globalVariablesInitializer());
            VectorWrapper<Float> result = tf.fetchVector(gradients);
            System.out.println(result.getList1D());
        }
    }

    @Test
    public void basicGradientComputation12() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(2.0f), named("a"));
            TF<Constant, Float> b = tf.constant(value(3.5f), named("b"));

            TF<Operation.Multiply, Float> c = tf.multiply(a, b, named("c"));

            TF<Gradient.Gradients, Float> gradients = tf.gradients(c, Arrays.asList(a));

            tf.run(tf.globalVariablesInitializer());
            VectorWrapper<Float> result = tf.fetchVector(gradients);
            System.out.println(result.getList1D());
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

    @Test
    public void basicGradientComputation20() {
        try (QMLContext tf = QMLContext.createContext()) {
            TF<Variable, Float> a = tf.variable(value(1.0f), named("a"));

            TF<Operation.Multiply, Float> b1 = tf.multiply(a, tf.constant(value(0.52f), named("c52")), named("b1mul"));
            TF<Operation.Multiply, Float> b2 = tf.multiply(a, tf.constant(value(0.9f), named("c9")), named("b2mul"));

            TF<Operation.Subtract, Float> difference = tf.subtract(b1, b2, named("difference"));

            TF<Gradient.Gradients, Float> gradients = tf.gradients(difference, Arrays.asList(a));



            tf.run(tf.globalVariablesInitializer());
            //VectorWrapper<Float> result = tf.fetchVector(loss);
            //VectorWrapper<Float> result = tf.fetchVector(gradients);
            VectorWrapper<Float> result = tf.fetchVector("neg_0");
//            VectorWrapper<Float> result = tf.fetchVector("gradients_0/add_1_grad/BroadcastGradientArgs");
            System.out.println(result.getList1D());
            //System.out.println((Float)tf.fetch("b1mul"));
//            assertEquals(7.3, result.FloatValue(), 0.001);
        }
    }
}