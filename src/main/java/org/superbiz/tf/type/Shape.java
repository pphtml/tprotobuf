package org.superbiz.tf.type;

import java.util.Arrays;
import java.util.stream.Stream;

public class Shape {
    public static final Shape SCALAR = new Shape(new Integer[]{});
    public static final Shape UNKNOWN = new Shape(new Integer[]{-1});
    private final Integer[] sizes;

    private Shape(Integer[] sizes) {
        this.sizes = sizes;
    }

//    public static Shape of(Integer ...sizes) {
//        return new Shape(sizes);
//    }

    public static Shape shape(Integer ...sizes) {
        return new Shape(sizes);
    }

    //        tensor_shape {
    //          dim {
    //            size: 3
    //          }
    //        }

    public static String toProtobufString(Shape shape) {
        if (shape == null) {
            return "{\n}";
        } else {
            String result = String.format("{\n  dim {\n    size: %d\n  }\n}", shape.sizes[0]);
            return result;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Shape{");
        sb.append(Arrays.toString(sizes));
        sb.append('}');
        return sb.toString();
    }

    public boolean isEmpty() {
        return sizes.length == 0;
    }

    public int dimensions() {
        return sizes.length;
    }

    public int[] asInts() {
        return Stream.of(sizes).mapToInt(i -> i).toArray();
    }

    public Integer getSize0() {
        return sizes[0];
    }
}
