package org.superbiz.builder;

import org.tensorflow.framework.NodeDef;

public class NodeBuilder {
    private final Operation operation;
    private final Attribute[] attributes;
    private String name;
    private static int sequenceId = 0;

    public NodeBuilder(Operation operation, Attribute[] attributes) {
        this.operation = operation;
        this.attributes = attributes;
    }

    public String getName() {
        if (name == null) {
            this.name = String.format("node_%d", sequenceId++);
        }

        return name;
    }

    public NodeDef.Builder asProtobufBuilder() {
        NodeDef.Builder protoBuilder = NodeDef.newBuilder();
        protoBuilder.setOp(this.operation.getTensorflowOperation());
        protoBuilder.setName(this.getName());
        return protoBuilder;
    }
}
