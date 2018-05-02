package org.superbiz.tf.annotation;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllowedShapeTransformation {
    private static final Pattern ALLOWED_FORMAT = Pattern.compile("^(?<left>[1N](,[1N])*)->(?<right>[1NUC])$");
    private final String[] lefts;
    private final String right;

    public AllowedShapeTransformation(String[] lefts, String right) {
        this.lefts = lefts;
        this.right = right;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AllowedShapeTransformation{");
        sb.append("lefts=").append(Arrays.toString(lefts));
        sb.append(", right='").append(right).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String[] getLefts() {
        return lefts;
    }

    public String getRight() {
        return right;
    }

    public static AllowedShapeTransformation parse(String shapeTransformation) {
        Matcher matcher = ALLOWED_FORMAT.matcher(shapeTransformation);
        if (matcher.find()) {
            final String[] lefts = matcher.group("left").split(",");
            final String right = matcher.group("right");
            return new AllowedShapeTransformation(lefts, right);
        } else {
            throw new IllegalStateException(String.format("%s is not allowed shape transformation", shapeTransformation));
        }
    }
}
