package org.superbiz.engine;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.EngineImplementation;
import org.superbiz.tf.type.TFType;
import org.superbiz.tf.type.VectorWrapper;

@EngineImplementation(LocalJDKEngine.NAME)
public class LocalJDKEngine extends BaseEngine {
    public static final String NAME = "LocalJDKEngine";

    @Override
    public <T extends TFType, NTType> void addToGraph(TF<T, NTType> node, QMLContext qmlContext) {
        super.addToGraph(node, qmlContext);
        throw new UnsupportedOperationException();
    }

    @Override
    public <NTType> void run(TF<? extends TFType, NTType> node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <NTType> NTType fetch(TF<? extends TFType, NTType> node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <NTType> VectorWrapper<NTType> fetchVector(String nodeName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
    }
}
