package org.superbiz.tf.type;

public interface InitializingOperation <NTType> {
    Shape getShape();

    String getInitialValue();

    DType getDType();
}
