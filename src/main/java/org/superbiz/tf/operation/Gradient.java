package org.superbiz.tf.operation;

import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;

import java.util.List;

public class Gradient {
    @Template("gradients.pb.ftl")
    @NamePrefix("gradients")
    public static class Gradients extends AbstractNode implements TFType, NamingSequence {
        @Mapping("sourceOperation")
        public final TF<?, ?> sourceOperation;

        public <T extends TFType, R extends TFType, NTType> Gradients(TF<T, NTType> sourceOperation, List<TF<Variable, ?>> variables, Attribute[] attributes) {
            super(attributes);
            this.sourceOperation = sourceOperation;
            //this.setDType(this.operand.getDType());
        }
    }
}
