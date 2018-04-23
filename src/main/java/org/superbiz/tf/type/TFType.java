package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.tensorflow.Output;

import java.util.List;

public interface TFType {
    void build(QMLContext qmlContext);

    String getName();

    Object getVariable(String variableName);

    String getTemplateText();

    String getOutputNodeName();

    DType getDType();

    List<TFType> getInputs();

    Shape getShape();
}
