package org.superbiz.tf.annotation;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AllowedShapeTransformationTest {

    // @ShapeTransformation("1,N->N"), @ShapeTransformation("N,1->N"), @ShapeTransformation("N,N->N")})

    @Test
    public void parse() {
        final AllowedShapeTransformation allowedShapeTransformation = AllowedShapeTransformation.parse("1,N->N");
        assertNotNull(allowedShapeTransformation);

        final String[] lefts = allowedShapeTransformation.getLefts();
        final String right = allowedShapeTransformation.getRight();

        assertEquals(2, lefts.length);
        assertArrayEquals(new String[]{"1", "N"}, lefts);
        assertEquals("N", right);
    }
}