package org.superbiz.tf.type;

import org.junit.Test;

import java.nio.DoubleBuffer;

import static org.junit.Assert.*;

public class DTypeTest {

    @Test
    public void dTypeForJavaTypeExisting() {
        assertEquals(DType.DT_INT32, DType.dTypeForJavaType(Integer.class));
        assertEquals(DType.DT_INT64, DType.dTypeForJavaType(Long.class));
        assertEquals(DType.DT_FLOAT, DType.dTypeForJavaType(Float.class));
        assertEquals(DType.DT_DOUBLE, DType.dTypeForJavaType(Double.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void dTypeForJavaTypeNonExisting() {
        DType.dTypeForJavaType(Object.class);
    }
}