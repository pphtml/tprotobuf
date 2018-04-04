package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.OutputNodePostfix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.tensorflow.Output;
import org.tensorflow.Tensor;
import org.tensorflow.Tensors;

import static org.superbiz.tf.attribute.Attribute.named;
import static org.superbiz.tf.util.TensorflowConstants.*;

@NamePrefix("var")
@Template("variable-from-constant.pb.txt")
@OutputNodePostfix("/read")
public class Variable extends AbstractNode implements TFType, NamingSequence {

    private final InitializingOperation initializingOperation;

    private Variable(InitializingOperation initializingOperation, Attribute[] attributes) {
        super(attributes);
        this.initializingOperation = initializingOperation;
    }

    public static Variable of(InitializingOperation initializingOperation, Attribute[] attributes) {
        return new Variable(initializingOperation, attributes);
    }

    public static Variable of(Attribute[] attributes) {
        return new Variable(null, attributes);
    }

    @Override
    public void build(QMLContext qmlContext) {
        super.commonBuild(qmlContext);
    }

    @Override
    public Output<?> getOutput() {
        throw new UnsupportedOperationException();
    }

//    private Shape guessShape() {
//        if (this.initializingOperation != null) {
//            return this.initializingOperation.getShape();
//        } else {
//            throw new UnsupportedOperationException();
//        }
//    }

    @Mapping("initialValue")
    public String getInitialValue() {
        return this.initializingOperation.getInitialValue();
    }
}
