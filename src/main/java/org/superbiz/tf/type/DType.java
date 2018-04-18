package org.superbiz.tf.type;


import java.util.stream.Stream;

public enum DType {
    DT_FLOAT("float_val", Float.class),
    DT_INT32("int_val", Integer.class),
    DT_INT64("int64_val", Long.class),
    DT_DOUBLE("double_val", Double.class);

    private final String argumentName;
    private final Class<?> type;

    DType(String argumentName, Class<?> type) {
        this.argumentName = argumentName;
        this.type = type;
    }

    public String getArgumentName() {
        return this.argumentName;
    }

    public static DType dTypeForJavaType(Class<?> type) {
        return Stream.of(DType.values())
                .filter(dType -> dType.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Class %s does not have mapping to DType", type)));
    }
}
