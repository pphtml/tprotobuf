package org.superbiz.tf.shape;

import org.superbiz.tf.type.Shape;

import java.util.List;

public class ShapeOperation {
    private final List<Shape> fromShapes;
    private final Shape toShape;

    public ShapeOperation(List<Shape> fromShapes, Shape toShape) {
        this.fromShapes = fromShapes;
        this.toShape = toShape;
    }

    public static ShapeOperation of(List<Shape> fromShapes, Shape toShape) {
        return new ShapeOperation(fromShapes, toShape);
    }

    public List<Shape> getFromShapes() {
        return fromShapes;
    }

    public Shape getToShape() {
        return toShape;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShapeOperation{");
        sb.append("fromShapes=").append(fromShapes);
        sb.append(", toShape=").append(toShape);
        sb.append('}');
        return sb.toString();
    }
}
