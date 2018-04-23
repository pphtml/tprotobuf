package org.superbiz.engine;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.TFType;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEngine implements Engine {
    private List<TF<? extends TFType, ?>> nodes = new ArrayList<>();

    @Override
    public <T extends TFType, NTType> void addToGraph(TF<T, NTType> node, QMLContext qmlContext) {
        node.build(qmlContext);
        nodes.add(node);
    }

    @Override
    public List<TF<? extends TFType, ?>> getNodes() {
        return nodes;
    }
}
