package org.superbiz.tf.shape;

import org.superbiz.tf.annotation.AllowedShapeTransformation;
import org.superbiz.tf.type.Shape;

import java.util.List;
import java.util.Optional;

public class TransformationFinder {
    public static Optional<ShapeOperation> findMatching(List<AllowedShapeTransformation> transformations, List<Shape> shapes) {
        final Optional<ShapeOperation> allowedShapeTransformation = transformations.stream()
                .filter(transformation -> {
                    String[] lefts = transformation.getLefts();
                    for (int index = 0; index < lefts.length; index++) {
                        String left = lefts[index];
                        Shape shape = shapes.get(index);
                        if ("1".equals(left) && shape.isEmpty()) {
                        } else if ("N".equals(left) && shape.dimensions() == 1) {
                        } else {
                            return false;
                        }
                    }
                    return true;
                })
                .map(transformation -> {
                    String right = transformation.getRight();
                    if ("1".equals(right)) {
                        return ShapeOperation.of(shapes, Shape.SCALAR);
                    }

                    String[] lefts = transformation.getLefts();
                    for (int index = 0; index < lefts.length; index++) {
                        String left = lefts[index];
                        Shape shape = shapes.get(index);
                        if (right.equals(left)) {
                            return ShapeOperation.of(shapes, shape);
                        }
                    }

                    throw new IllegalStateException(String.format("ShapeTransformation %s is invalid", transformation));
                })
                .findFirst();
        return allowedShapeTransformation;
    }
}
