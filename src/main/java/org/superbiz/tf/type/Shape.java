package org.superbiz.tf.type;

public class Shape {
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

//    public Integer tfValue() { // TODO docasne, po odladeni vyhodit
//        if (sizes.length == 1) {
//            return sizes[0];
//        } else {
//            throw new UnsupportedOperationException();
//        }
//    }
}
