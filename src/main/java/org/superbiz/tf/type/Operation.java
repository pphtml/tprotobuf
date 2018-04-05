package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.TemplateInline;
import org.superbiz.tf.attribute.Attribute;
import org.tensorflow.Output;

import static org.superbiz.tf.util.TensorflowConstants.OPERATION_ADD;

public class Operation {

    // node {
    //  name: "add"
    //  op: "Add"
    //  input: "mul_1"
    //  input: "y/read"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_INT32
    //    }
    //  }
    //}
    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Add\"\n" +
            "  input: \"${operand1}\"\n" +
            "  input: \"${operand2}\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: DT_INT32\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("add")
    public static class Add extends AbstractNode implements TFType, NamingSequence {
        @Mapping("operand1")
        public final TF<?, ?> operand1;
        @Mapping("operand2")
        public final TF<?, ?> operand2;
        //private org.tensorflow.Operation operation;

        public <T extends TFType, R extends TFType, NTType> Add(TF<T, NTType> operand1, TF<R, NTType> operand2) {
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

            super.commonBuild(qmlContext);

//            this.name = qmlContext.getNamingService().name(attributes, this);
//            org.tensorflow.Operation add = qmlContext.getGraph().opBuilder(OPERATION_ADD, this.name)
//                    .addInput(operand1.getOutput(qmlContext))
//                    .addInput(operand2.getOutput(qmlContext))
//                    .build();
//            this.operation = add;
        }

        @Override
        public Output<?> getOutput() {
            throw new UnsupportedOperationException();
        }
    }
}
