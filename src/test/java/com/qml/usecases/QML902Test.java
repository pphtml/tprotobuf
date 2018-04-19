package com.qml.usecases;

import org.junit.Test;
import org.superbiz.engine.LocalJDKEngine;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.operation.Constant;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.type.VectorWrapper;
import org.superbiz.util.TestUtil;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.superbiz.tf.QMLContext.createContext;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

/**
 * Basic op operations for scalars and vectors of constants. Operations are add, subtract, multiply and divide.
 */
public class QML902Test extends AbstractTestBase {

    /**
     * Two integer constants are added.
     */
    @Test
    public void addIntegers() {
        try (QMLContext tf = createContext(LocalJDKEngine.NAME)) {
            TF<Constant, Integer> x = tf.constant(value(4), named("x"));
            TF<Constant, Integer> y = tf.constant(value(5), named("y"));
            TF<Operation.Add, Integer> add = x.add(y);

            Integer result = tf.fetch(add);
            assertEquals(9, result.intValue());
        }
    }
}
