package org.superbiz.engine;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.TFType;
import org.superbiz.tf.type.VectorWrapper;

import java.util.Collections;
import java.util.List;

public interface Engine extends AutoCloseable {
    String DEFAULT_ML_FRAMEWORK = "TFEngine";

    static Engine findEngine(String engine) {
        return EngineFinder.load(engine);
    }

    static <NTType> List<String> listSupportedFrameworks() {
        return Collections.singletonList(DEFAULT_ML_FRAMEWORK);
    }

    // public <T extends TFType, NTType> TF<T, NTType> addToGraph(TF<T, NTType> node, QMLContext qmlContext) {
    <T extends TFType, NTType> void addToGraph(TF<T,NTType> node, QMLContext qmlContext);

    <NTType> void run(TF<? extends TFType, NTType> node);

    <NTType> NTType fetch(TF<? extends TFType, NTType> node);

    <NTType> VectorWrapper<NTType> fetchVector(String nodeName);

    List<TF<? extends TFType, ?>> getNodes();
}
