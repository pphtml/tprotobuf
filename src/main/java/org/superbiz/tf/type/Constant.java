package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.attribute.Attribute;
import org.tensorflow.Output;
import org.tensorflow.Tensor;
import org.tensorflow.Tensors;

import static org.superbiz.tf.util.TensorflowConstants.DTYPE;
import static org.superbiz.tf.util.TensorflowConstants.NODE_CONSTANT;
import static org.superbiz.tf.util.TensorflowConstants.VALUE;

public class Constant <T> extends AbstractNode implements TFType, NamingSequence {
    private final T value;
    private Output<Object> output;

    public Constant(T value, Attribute[] attributes) {
        super(attributes);
        this.value = value;
    }

    public static <T> Constant of(T value, Attribute[] attributes) {
        return new Constant(value, attributes);
    }

    @Override
    public void build(QMLContext qmlContext) {
        Tensor tensor = Tensors.create((Float)value);
        qmlContext.registerAutoCloseable(tensor);

        this.name = qmlContext.getNamingService().name(attributes, this);
        this.output = qmlContext.getGraph().opBuilder(NODE_CONSTANT, this.name)
                .setAttr(DTYPE, tensor.dataType())
                .setAttr(VALUE, tensor)
                .build().output(0);
    }

    @Override
    public Output<?> getOutput() {
        return this.output;
    }

    @Override
    public String getPrefix() {
        return "CONST";
    }

//    public Constant(QMLContext graph, double value) {
//        super(graph);
//        this.value = value;
//    }
}
