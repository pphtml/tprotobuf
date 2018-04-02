package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.attribute.Attribute;
import org.tensorflow.Output;

import static org.superbiz.tf.util.TensorflowConstants.OPERATION_ADD;

public class Operation {

    public static class Add extends AbstractNode implements TFType, NamingSequence {
        private final TF<?> operand1;
        private final TF<?> operand2;
        private org.tensorflow.Operation operation;

        public <T extends TFType, R extends TFType> Add(TF<T> operand1, TF<R> operand2) {
            super(new Attribute[]{}); // TODO predavat
            this.operand1 = operand1;
            this.operand2 = operand2;
        }

        @Override
        public void build(QMLContext qmlContext) {
//            Tensor tensor = Tensors.create((Double)value);
//            qmlContext.registerAutoCloseable(tensor);
//
//            this.output = qmlContext.getGraph().opBuilder(NODE_CONSTANT, qmlContext.getNamingService().name(attributes, this))
//                    .setAttr(DTYPE, tensor.dataType())
//                    .setAttr(VALUE, tensor)
//                    .build().output(0);

            this.name = qmlContext.getNamingService().name(attributes, this);
            org.tensorflow.Operation add = qmlContext.getGraph().opBuilder(OPERATION_ADD, this.name)
                    .addInput(operand1.getOutput(qmlContext))
                    .addInput(operand2.getOutput(qmlContext))
                    .build();
            this.operation = add;
        }

        @Override
        public Output<?> getOutput() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getTemplateName() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getPrefix() {
            return "ADD";
        }
    }
}
