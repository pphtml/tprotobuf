package org.superbiz.tf;

import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.TFType;
import org.tensorflow.Output;

public class TF <T extends TFType> {
    private final T node;
    private final QMLContext qmlContext;
    private boolean built = false;

    private TF(T node, QMLContext qmlContext) {
        this.node = node;
        this.qmlContext = qmlContext;
    }

    public static TF of(TFType node, QMLContext qmlContext) {
        return new TF(node, qmlContext);
    }

    public <R extends TFType> TF<Operation.Add> add(TF<R> c2) {
        return qmlContext.register(of(new Operation.Add(this, c2), qmlContext));
    }

    public void build(QMLContext qmlContext) {
        node.build(qmlContext);
        built = true;
    }

    public Output<?> getOutput(QMLContext qmlContext) {
        if (!built) {
            this.build(qmlContext);
        }
        return node.getOutput();
    }

    public String getName() {
        if (!built) {
            this.build(qmlContext);
        }
        return node.getName();
    }
}
