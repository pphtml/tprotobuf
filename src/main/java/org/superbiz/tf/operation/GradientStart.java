package org.superbiz.tf.operation;

import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.OutputNodePostfix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.DType;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;

@NamePrefix("gradient-start")
@Template("gradient-start.pb.ftl")
@OutputNodePostfix("/Fill")
public class GradientStart<R extends TFType> extends AbstractNode implements TFType, NamingSequence {

    private final R operation;

    private GradientStart(/*InitializingOperation initializingOperation,*/ R operation, Attribute[] attributes) {
        super(attributes);
        // this.setShape(initializingOperation.getShape());
        this.setDType(DType.DT_FLOAT);
        this.operation = operation;
    }

    public static <R extends TFType> GradientStart of(R operation, Attribute[] attributes) {
        GradientStart result = new GradientStart(operation, attributes);
        return result;
    }
}
