package org.superbiz.tf.type;

public class Shape {
    private final Integer[] sizes;

    private Shape(Integer[] sizes) {
        this.sizes = sizes;
    }

    public static Shape of(Integer ...sizes) {
        return new Shape(sizes);
    }

    public static Shape shape(Integer ...sizes) {
        return of(sizes);
    }

    public Integer tfValue() { // TODO docasne, po odladeni vyhodit
        if (sizes.length == 1) {
            return sizes[0];
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
