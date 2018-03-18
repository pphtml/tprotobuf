package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.attribute.Attribute;
import org.tensorflow.Output;

public class Variable extends AbstractNode implements TFType {

    private Variable(Attribute[] attributes) {
        super(attributes);
    }

    public static Variable of(Attribute[] attributes) {
        return new Variable(attributes);
    }

    @Override
    public void build(QMLContext qmlContext) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Output<?> getOutput() {
        throw new UnsupportedOperationException();
    }

//    private final T value;
//
//    public Constant(T value) {
//        this.value = value;
//    }
//
//    public static <T> Constant priority(T value) {
//        return new Constant(value);
//    }

}
