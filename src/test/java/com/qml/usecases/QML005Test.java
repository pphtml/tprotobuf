package com.qml.usecases;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.Constant;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.type.VectorWrapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.createContext;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

/**
 * Tile & Reshape operations.
 */
public class QML005Test extends AbstractTestBase {

    private static final Float FLOAT_ONE = Float.valueOf(1.0f);

    /**
     * Tile operation.
     */
    @Test
    public void tile() {
        try (QMLContext tf = createContext()) {
            TF<Constant, Float> one = tf.constant(values(1.0f), named("one"));
            TF<Constant, Integer> repeatsCount = tf.constant(values(100), named("repeatsCount"));
            TF<Operation.Tile, Float> tilesOfOnes = tf.tile(one, repeatsCount, named("tilesOfOnes"));

            VectorWrapper<Float> result = tf.fetchVector(tilesOfOnes);
            List<Float> expectedResult = IntStream.range(0, 100).mapToObj(i -> FLOAT_ONE).collect(Collectors.toList());
            assertEquals(expectedResult, result.getList1D());
        }
    }
}
