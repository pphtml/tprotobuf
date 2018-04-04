package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.tensorflow.Output;

public interface TFType {
    void build(QMLContext qmlContext);

    Output<?> getOutput();

    String getName();

    String getVariable(String variableName);

    String getTemplateText();

    String getOutputNodeName();
}
