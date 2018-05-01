package org.superbiz.tf;

import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.operation.BasicOperation;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.shape.ShapeOperation;
import org.superbiz.tf.type.DType;
import org.superbiz.tf.type.TFType;

import java.util.List;

public class TF <T extends TFType, NTType> {
    private final T node;
    private final QMLContext qmlContext;

    private TF(T node, QMLContext qmlContext) {
        this.node = node;
        this.qmlContext = qmlContext;
    }

    public static TF of(TFType node, QMLContext qmlContext) {
        return new TF(node, qmlContext);
    }

    public <R extends TFType> TF<Operation.Add, NTType> add(TF<R, NTType> operand, Attribute... attributes) {
        TF node = of(new Operation.Add(this, operand, attributes), qmlContext);
        return qmlContext.addToGraph(node, qmlContext);
    }

    public <R extends TFType> TF<Operation.Subtract, NTType> subtract(TF<R, NTType> operand, Attribute... attributes) {
        TF node = of(new Operation.Subtract(this, operand, attributes), qmlContext);
        return qmlContext.addToGraph(node, qmlContext);
    }

    public <R extends TFType> TF<Operation.Multiply, NTType> multiply(TF<R, NTType> operand, Attribute... attributes) {
        TF node = of(new Operation.Multiply(this, operand, attributes), qmlContext);
        return qmlContext.addToGraph(node, qmlContext);
    }

    public <R extends TFType> TF<Operation.Divide, NTType> divide(TF<R, NTType> operand, Attribute... attributes) {
        TF node = of(new Operation.Divide(this, operand, attributes), qmlContext);
        return qmlContext.addToGraph(node, qmlContext);
    }

    public <R extends TFType> TF<Operation.Negative, NTType> negative(Attribute... attributes) {
        TF node = of(new Operation.Negative(this, attributes), qmlContext);
        return qmlContext.addToGraph(node, qmlContext);
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TF{");
        sb.append(node);
        sb.append('}');
        return sb.toString();
    }

    public List<TFType> getInputs() {
        return node.getInputs();
    }

    public T getNode() {
        return node;
    }
}
