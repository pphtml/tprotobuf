package org.superbiz.tf.type;

import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.TemplateInline;
import org.superbiz.tf.attribute.Attribute;

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
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("add")
    public static class Add extends AbstractNode implements TFType, NamingSequence {
        @Mapping("operand1")
        public final TF<?, ?> operand1;
        @Mapping("operand2")
        public final TF<?, ?> operand2;

        public <T extends TFType, R extends TFType, NTType> Add(TF<T, NTType> operand1, TF<R, NTType> operand2) {
            super(new Attribute[]{}); // TODO predavat
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.setDType(this.operand1.getDType());
        }
    }

    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Mul\"\n" +
            "  input: \"${operand1}\"\n" +
            "  input: \"${operand2}\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("multiply")
    public static class Multiply extends AbstractNode implements TFType, NamingSequence {
        @Mapping("operand1")
        public final TF<?, ?> operand1;
        @Mapping("operand2")
        public final TF<?, ?> operand2;

        public <T extends TFType, R extends TFType, NTType> Multiply(TF<T, NTType> operand1, TF<R, NTType> operand2) {
            super(new Attribute[]{}); // TODO predavat
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.setDType(this.operand1.getDType());
        }
    }
}
