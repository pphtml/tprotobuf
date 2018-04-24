package org.superbiz.tf.operation;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.shape.ShapeOperation;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.attribute.Attribute.named;

public class Gradient {
    @Template("gradients.pb.ftl")
    @NamePrefix("gradients")
    public static class Gradients extends AbstractNode implements TFType, NamingSequence {
        @Mapping("sourceOperation")
        public final TF<? extends TFType, ?> sourceOperation;

        public <T extends TFType, R extends TFType, NTType> Gradients(TF<T, NTType> sourceOperation, List<TF<Variable, ?>> variables, Attribute[] attributes) {
            super(attributes);
            this.sourceOperation = sourceOperation;
            //this.setDType(this.operand.getDType());
        }

        public void computeGradients(QMLContext qmlContext) {
            Map<String, WrappedNode> mapOfNodes = analyzeOperations(qmlContext);

            List<WrappedNode> toOperations = Collections.singletonList(mapOfNodes.get(sourceOperation.getName()));
            List<WrappedNode> fromOperations = qmlContext.getVariables().stream().map(v -> mapOfNodes.get(v.getName())).collect(Collectors.toList());

            // TODO nejspis delit 1.0f podle poctu output nodes
            TF<Constant, Float> gradientStart = qmlContext.constant(value(1.0f), named("gradientStart"));
            for (WrappedNode fromOperation : fromOperations) {
                fromOperation.getOutputs().add(gradientStart.getNode());
            }

            Deque<WrappedNode> queue = new ArrayDeque<>(toOperations);
            if (!queue.isEmpty()) { // TODO spocitat vsechny gradienty
                WrappedNode wrappedNode = queue.pop();
                computeNodeGradients(wrappedNode, qmlContext);
            }
        }

        private void computeNodeGradients(WrappedNode wrappedNode, QMLContext qmlContext) {
            ShapeOperation shapeOperation = wrappedNode.node.getShapeOperation();
        }

        public Map<String, WrappedNode> analyzeOperations(QMLContext qmlContext) {
            List<TF<? extends TFType, ?>> nodes = qmlContext.getNodes();
            Map<String, WrappedNode> mapWrappedNodes = IntStream.range(0, nodes.size())
                    .mapToObj(index -> WrappedNode.of(nodes.get(index), index))
                    .collect(Collectors.toMap(wrapper -> wrapper.node.getName(), identity()));

            for (WrappedNode wrappedNode : mapWrappedNodes.values()) {
                List<TFType> inputs = wrappedNode.getInputs();
                for (TFType input : inputs) {
                    String name = input.getName();
                    WrappedNode inputWrappedNode = mapWrappedNodes.get(name);
                    inputWrappedNode.getOutputs().add(wrappedNode.node.getNode());
                }
            }

            return mapWrappedNodes;
//            List<OutputMapping> mappings = wrappedNodes
//                    .stream()
//                    .map(node -> node.operation.getInputList().stream().map(input -> OutputMapping.of(node.operation.getName(), input)))
//                    .flatMap(stream -> stream)
//                    .collect(Collectors.toList());
//            Map<String, WrappedNode> nodeMap = wrappedNodes.stream()
//                    .collect(Collectors.toMap(node -> node.operation.getName(), Function.identity()));
//            for (OutputMapping mapping : mappings) {
//                WrappedNode inputNode = nodeMap.get(mapping.inputName);
//                WrappedNode outputNode = nodeMap.get(mapping.nodeName);
//                if (outputNode == null) {
//                    throw new IllegalStateException();
//                }
//                inputNode.outputs.add(outputNode);
//            }
//
//            List<WrappedNode> toOperations = Collections.singletonList(nodeMap.get(sourceOperation.getName()));
//            List<WrappedNode> fromOperations = qmlContext.getVariables().stream().map(v -> nodeMap.get(v.getName())).collect(Collectors.toList());
//
//            // collect nodes from inputs to outputs
//            final List<Boolean> dosazenyOperace = IntStream.range(0, nodes.size()).mapToObj(index -> false).collect(Collectors.toList());

        }
    }

    private static class WrappedNode {
        private final int index;
        private final TF<?, ?> node;
        private final List<TFType> outputs = new ArrayList<>();
        //private final List<WrappedNode> outputs = new ArrayList<>();

        WrappedNode(TF<?, ?> node, int index) {
            this.node = node;
            this.index = index;
        }

        static WrappedNode of(TF<?, ?> node, int index) {
            return new WrappedNode(node, index);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("WrappedNode{");
            sb.append("index=").append(index);
            sb.append(", node=").append(node);
            sb.append('}');
            return sb.toString();
        }

//        public List<WrappedNode> inputs(Gradients.OpFinder opFinder) {
//            return this.operation.getInputList().stream().map(name -> opFinder.find(name)).collect(Collectors.toList());
//        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WrappedNode that = (WrappedNode) o;
            return index == that.index &&
                    Objects.equals(node, that.node);
        }

        @Override
        public int hashCode() {

            return Objects.hash(index, node);
        }

        public List<TFType> getInputs() {
            return node.getInputs();
        }

        public List<TFType> getOutputs() {
            return this.outputs;
        }
    }
}
