package org.superbiz.tf.operation;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.OutputNodePostfix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.InitializingOperation;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;
import org.superbiz.tf.util.TFInitialValueConverter;

import java.util.Collections;
import java.util.List;

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
        return TFInitialValueConverter.getValue(this.initializingOperation);
    }

    @Override
    public List<TF<? extends TFType, ?>> createGradientOp(QMLContext qmlContext, TF<? extends TFType, ?> output) {
        return Collections.emptyList();
    }
}
