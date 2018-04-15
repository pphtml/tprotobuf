package org.superbiz.tf.operation;

import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.OutputNodePostfix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.InitializingOperation;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;

@NamePrefix("var")
@Template("variable-from-constant.pb.ftl")
@OutputNodePostfix("/read")
public class Variable extends AbstractNode implements TFType, NamingSequence {

    private final InitializingOperation initializingOperation;

    private Variable(InitializingOperation initializingOperation, Attribute[] attributes) {
        super(attributes);
        this.initializingOperation = initializingOperation;
    }

    public static Variable of(InitializingOperation initializingOperation, Attribute[] attributes) {
        Variable result = new Variable(initializingOperation, attributes);
        if (initializingOperation.getDType() != null) {
            result.setDType(initializingOperation.getDType());
        } else {
            throw new UnsupportedOperationException("unable to get DType");
        }
        return result;
    }

    @Mapping("initialValue")
    public String getInitialValue() {
        return this.initializingOperation.getInitialValue();
    }
}
