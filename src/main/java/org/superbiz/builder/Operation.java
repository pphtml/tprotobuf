package org.superbiz.builder;

public enum Operation {
    CONSTANT("Const"),
    ADD("Add");

    private final String tensorflowOperation;

    Operation(String tensorflowOperation) {
        this.tensorflowOperation = tensorflowOperation;
    }

    public String getTensorflowOperation() {
        return tensorflowOperation;
    }
}
