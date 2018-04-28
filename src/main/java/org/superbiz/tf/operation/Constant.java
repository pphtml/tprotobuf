package org.superbiz.tf.operation;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.InitializingOperation;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;
import org.superbiz.tf.util.TFInitialValueConverter;

import java.util.Collections;
import java.util.List;

@NamePrefix("const")
@Template("constant-from-constant.pb.ftl")
//@OutputNodePostfix("/read")
public class Constant extends AbstractNode implements TFType, NamingSequence {

    private final InitializingOperation initializingOperation;

    private Constant(InitializingOperation initializingOperation, Attribute[] attributes) {
        super(attributes);
        this.initializingOperation = initializingOperation;
        this.setShape(initializingOperation.getShape());
    }

    public static Constant of(InitializingOperation initializingOperation, Attribute[] attributes) {
        Constant result = new Constant(initializingOperation, attributes);
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
