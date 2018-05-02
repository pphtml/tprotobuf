package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.Constant;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.operation.Placeholder;
import org.superbiz.tf.operation.Variable;
import org.superbiz.tf.type.VectorWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.createContext;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

/**
 * Placeholder operations.
 */
public class QML006Test extends AbstractTestBase {

    private static final Float FLOAT_ONE = Float.valueOf(1.0f);

    /**
     * Basic placeholder operation.
     */
    @Test
    public void placeholder() {
        try (QMLContext tf = createContext()) {
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

            TF<Placeholder, Float> a = tf.placeholder(Float.class, named("a"));
            TF<Constant, Float> ten = tf.constant(value(10.0f), named("ten"));
            TF<Operation.Multiply, Float> c = tf.multiply(a, ten);

            Map<String, Object> feedDict = new HashMap<>();
            feedDict.put("a", xData);

            VectorWrapper<Float> result = tf.fetchVector(c, feedDict);
            System.out.println(result.getList1D());
//            List<Float> expectedResult = IntStream.range(0, 100).mapToObj(i -> FLOAT_ONE).collect(Collectors.toList());
//            assertEquals(expectedResult, result.getList1D());
        }
    }
}
