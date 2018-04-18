package org.superbiz.tf.operation;

import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.TemplateInline;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;

public class Operation {

    public static class Add extends BasicOperation.Add {
        public <T extends TFType, R extends TFType, NTType> Add(TF<T, NTType> operand1, TF<R, NTType> operand2, Attribute[] attributes) {
            super(operand1, operand2, attributes);
        }
    }

    public static class Subtract extends BasicOperation.Subtract {
        public <T extends TFType, R extends TFType, NTType> Subtract(TF<T, NTType> operand1, TF<R, NTType> operand2, Attribute[] attributes) {
            super(operand1, operand2, attributes);
        }
    }

    public static class Multiply extends BasicOperation.Multiply {
        public <T extends TFType, R extends TFType, NTType> Multiply(TF<T, NTType> operand1, TF<R, NTType> operand2, Attribute[] attributes) {
            super(operand1, operand2, attributes);
        }
    }

    public static class Divide extends BasicOperation.Divide {
        public <T extends TFType, R extends TFType, NTType> Divide(TF<T, NTType> operand1, TF<R, NTType> operand2, Attribute[] attributes) {
            super(operand1, operand2, attributes);
        }
    }

    public static class Square extends BasicOperation.Square {
        public <T extends TFType, NTType> Square(TF<T, NTType> operand, Attribute[] attributes) {
            super(operand, attributes);
        }
    }

    public static class ReduceMean extends BasicOperation.ReduceMean {
        public <T extends TFType, NTType> ReduceMean(TF<T, NTType> operand, Attribute[] attributes) {
            super(operand, attributes);
        }
    }

    public static class Cast extends BasicOperation.Cast {
        public <T extends TFType, R, NTType> Cast(TF<T, NTType> operand, Class<R> type, Attribute[] attributes) {
            super(operand, type, attributes);
        }
    }
}
