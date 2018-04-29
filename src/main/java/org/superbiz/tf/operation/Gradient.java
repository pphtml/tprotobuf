package org.superbiz.tf.operation;

import org.superbiz.engine.TensorflowEngine;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

public class Gradient {
    private static final Logger LOGGER = Logger.getLogger(Gradient.class.getName());

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

        @FunctionalInterface
        public interface OpFinder {
            WrappedNode find(String nodeName);
        }

        public List<TF<? extends TFType, ?>> computeGradients(QMLContext qmlContext, List<TF<Variable, ?>> variables) {
            // TODO nejspis delit 1.0f podle poctu output nodes // TODO muze byt vic instanci - konflikt name
            TF<Constant, Float> gradientStart = qmlContext.constant(values(1.0f), named("gradientStart"));

            Map<String, WrappedNode> mapOfNodes = analyzeOperations(qmlContext);
            OpFinder opFinder = nodeName -> mapOfNodes.get(nodeName);
            WrappedNode wrappedGradientStart = opFinder.find(gradientStart.getName());
            wrappedGradientStart.setGradientOperations(Collections.singletonList(gradientStart));

            List<WrappedNode> toOperations = Collections.singletonList(mapOfNodes.get(sourceOperation.getName()));
            final Set<String> fromOperationsSet = variables.stream().map(variable -> variable.getName()).collect(Collectors.toSet());
            List<WrappedNode> fromOperations = variables.stream()
                    //.filter(wrappedNode -> fromOperationsSet.contains(wrappedNode.getName()))
                    .map(v -> mapOfNodes.get(v.getName()))
                    .collect(Collectors.toList());

            for (WrappedNode toOperation : toOperations) {
                toOperation.getOutputs().add(gradientStart);
            }

            Deque<WrappedNode> queue = new ArrayDeque<>(toOperations);
            while (!queue.isEmpty()) {
                WrappedNode wrappedNode = queue.pop();
                List<TF<? extends TFType, ?>> gradientOps = computeNodeGradients(wrappedNode, opFinder, qmlContext);
                wrappedNode.setGradientOperations(gradientOps);

                wrappedNode.getInputs().stream().map(input -> mapOfNodes.get(input.getName())).forEach(
                    wrappedInput -> {
                        if (fromOperationsSet.contains(wrappedInput.node.getName())) {
                            TF<? extends TFType, ?> result = wrappedNode.findGradientFor(wrappedInput);
                            //throw new RuntimeException("Uz sem tady");
                            wrappedInput.addResultingGradientOp(result);
                            // LOGGER.info(String.format("Variable %s reached", wrappedInput.node.getName()));
                        } else {
                            queue.add(wrappedInput);
                        }
                    }
                );

//                if ("difference".equals(wrappedNode.node.getName())) {
//                    return gradientOperations;
//                }
            }

            if (fromOperations.size() != 1) {
                throw new UnsupportedOperationException(); // musi se dodelat
            }

            List<TF<? extends TFType, ?>> result = fromOperations.stream()
                    .map(wrappedNode -> wrappedNode.resultingGradientOps)
                    .map(gradientOps -> {
                        if (gradientOps.size() != 1) {
                            throw new UnsupportedOperationException(); // musi se dodelat
                        }
                        return gradientOps.get(0);
                    })
                    .collect(Collectors.toList());

//            List<TF<? extends TFType, ?>> gradientOps = fromOperations.get(0).resultingGradientOps;

            return result;
        }

        private List<TF<? extends TFType, ?>> computeNodeGradients(WrappedNode wrappedNode, OpFinder opFinder, QMLContext qmlContext) {
            //ShapeOperation shapeOperation = wrappedNode.node.getShapeOperation();
            List<TF<? extends TFType, ?>> outputs = wrappedNode.getOutputs();
            if (outputs == null || outputs.size() == 0) {
                throw new IllegalStateException("There is no output to compute the gradients from.");
            } else if (outputs.size() > 1) {
                throw new UnsupportedOperationException("Computation of gradients for multiple outputs is not supported.");
            }
            //TF<? extends TFType, ?> output = outputs.get(0);
            WrappedNode outputGradientOperationWrapped = opFinder.find(outputs.get(0).getName());

            final TF<? extends TFType, ?> outputGradientOperation = outputGradientOperationWrapped.findGradientFor(wrappedNode);
            List<TF<? extends TFType, ?>> result = wrappedNode.node.getNode().createGradientOp(qmlContext, outputGradientOperation);
            //List<TF<? extends TFType, ?>> result = wrappedNode.node.getNode().createGradientOp(qmlContext, outputGradientOperations);
            return result;
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
                    inputWrappedNode.getOutputs().add(wrappedNode.node);
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

        @Override
        public List<TF<? extends TFType, ?>> createGradientOp(QMLContext qmlContext, TF<? extends TFType, ?> output) {
            throw new UnsupportedOperationException("Computation of gradients for gradient operation is not supported.");
        }
    }

    private static class WrappedNode {
        private final int index;
        private final TF<? extends TFType, ?> node;
        private final List<TF<? extends TFType, ?>> outputs = new ArrayList<>();
        private List<TF<? extends TFType, ?>> gradientOperations;
        private List<TF<? extends TFType, ?>> resultingGradientOps = new ArrayList<>();
        //private final List<WrappedNode> outputs = new ArrayList<>();

        WrappedNode(TF<? extends TFType, ?> node, int index) {
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

        public List<TF<? extends TFType, ?>> getOutputs() {
            return this.outputs;
        }

        public void setGradientOperations(List<TF<? extends TFType,?>> gradientOperations) {
            this.gradientOperations = gradientOperations;
        }

        public List<TF<? extends TFType,?>> getGradientOperations() {
            return gradientOperations;
        }

        public TF<? extends TFType, ?> findGradientFor(WrappedNode forWrappedNode) {
            if (this.gradientOperations.size() == 1) {
                return this.gradientOperations.get(0);
            }

            List<TFType> inputs = this.node.getInputs();
            for (int index = 0; index < inputs.size(); index++) {
                TFType input = inputs.get(index);
                if (input.getName().equals(forWrappedNode.node.getName())) {
                    TF<? extends TFType, ?> gradientOperation = this.gradientOperations.get(index);
                    return gradientOperation;
                }
            }

            throw new IllegalStateException();
        }

        public void addResultingGradientOp(TF<? extends TFType,?> gradientOp) {
            this.resultingGradientOps.add(gradientOp);
        }
    }
}
