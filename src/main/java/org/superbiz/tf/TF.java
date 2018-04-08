package org.superbiz.tf;

import org.superbiz.tf.type.DType;
import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.TFType;

public class TF <T extends TFType, NTType> {
    private final T node;
    private final QMLContext qmlContext;
    //private boolean built = false;

    private TF(T node, QMLContext qmlContext) {
        this.node = node;
        this.qmlContext = qmlContext;
    }

    public static TF of(TFType node, QMLContext qmlContext) {
        return new TF(node, qmlContext);
    }

    public <R extends TFType> TF<Operation.Add, NTType> add(TF<R, NTType> operand) {
        TF of = of(new Operation.Add(this, operand), qmlContext);
        return qmlContext.makeFromTemplate(of, qmlContext);
    }

    public <R extends TFType> TF<Operation.Multiply, NTType> multiply(TF<R, NTType> operand) {
        TF of = of(new Operation.Multiply(this, operand), qmlContext);
        return qmlContext.makeFromTemplate(of, qmlContext);
    }

    public void build(QMLContext qmlContext) {
        node.build(qmlContext);
        //built = true;
    }

//    public Output<?> getOutput(QMLContext qmlContext) {
////        if (!built) {
////            this.build(qmlContext);
////        }
//        return node.getOutput();
//    }

    public String getName() {
//        if (!built) {
//            this.build(qmlContext);
//        }
        return node.getName();
    }

//    public String getTemplateName() {
//        return node.getTemplateName();
//    }

    public Object getNodeVariable(String variableName) {
//        if (!built) {
//            this.build(qmlContext);
//        }
        return node.getVariable(variableName);
    }

    public String getTemplateText() {
        return node.getTemplateText();
    }

    public String getOutputNodeName() {
        return node.getOutputNodeName();
    }

    public String getFMTemplateName() {
        return node.getClass().getName();
    }

    public DType getDType() {
        return node.getDType();
    }
}
