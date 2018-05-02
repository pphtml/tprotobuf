package org.superbiz.tf.operation;

import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;

import static org.superbiz.tf.type.DType.dTypeForJavaType;

@NamePrefix("placeholder")
@Template("placeholder.pb.ftl")
public class Placeholder<NTType> extends AbstractNode implements TFType, NamingSequence {

    private final Class<NTType> type;

    private Placeholder(Class<NTType> type, Attribute[] attributes) {
        super(attributes);
        this.type = type;
    }

    public static <NTType> Placeholder of(Class<NTType> type, Attribute[] attributes) {
        Placeholder result = new Placeholder(type, attributes);
        result.setDType(dTypeForJavaType(type));
        return result;
    }
}
