package org.superbiz.tf.type;

public enum DType {
    DT_FLOAT("float_val"),
    DT_INT32("int_val"),
    DT_INT64("int64_val"),
    DT_DOUBLE("double_val");

    private final String argumentName;

    DType(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return this.argumentName;
    }
}
