package org.superbiz.engine;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.TFType;

public abstract class BaseEngine implements Engine {
    @Override
    public <T extends TFType, NTType> void addToGraph(TF<T, NTType> node, QMLContext qmlContext) {
        node.build(qmlContext);
    }
}
