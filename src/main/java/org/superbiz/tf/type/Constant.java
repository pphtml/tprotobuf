package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.OutputNodePostfix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.tensorflow.Output;

@NamePrefix("const")
@Template("constant-from-constant.pb.ftl")
//@OutputNodePostfix("/read")
public class Constant extends AbstractNode implements TFType, NamingSequence {

    private final InitializingOperation initializingOperation;

    private Constant(InitializingOperation initializingOperation, Attribute[] attributes) {
        super(attributes);
        this.initializingOperation = initializingOperation;
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

    @Override
    public void build(QMLContext qmlContext) {
        super.commonBuild(qmlContext);
    }

    @Override
    public Output<?> getOutput() {
        throw new UnsupportedOperationException();
    }

    @Mapping("initialValue")
    public String getInitialValue() {
        return this.initializingOperation.getInitialValue();
    }
}
