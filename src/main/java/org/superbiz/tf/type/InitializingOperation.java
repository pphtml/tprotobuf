package org.superbiz.tf.type;

public abstract class InitializingOperation <NTType> {
    public Shape getShape() {
        return null;
    }

    public abstract Object getInitialValue();

    public abstract DType getDType();

    public abstract boolean isVector();
}
