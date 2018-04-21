package org.superbiz.tf.operation;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;
import org.tensorflow.framework.GraphDef;
import org.tensorflow.framework.NodeDef;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GradientOld {
    @Template("gradients.pb.ftl")
    @NamePrefix("gradients")
    public static class Gradients extends AbstractNode implements TFType, NamingSequence {
        @Mapping("sourceOperation")
        public final TF<?, ?> sourceOperation;

        public <T extends TFType, R extends TFType, NTType> Gradients(TF<T, NTType> sourceOperation, List<TF<Variable, ?>> variables, Attribute[] attributes) {
            super(attributes);
            this.sourceOperation = sourceOperation;
            //this.setDType(this.operand.getDType());
        }

        @Mapping("opNodeName")
        public String opNodeName() {
            return "difference";
        }

//        @FunctionalInterface
//        public interface OpFinder {
//            WrappedNode find(String nodeName);
//        }

//        public void collectAllOperations(QMLContext qmlContext) {
//            GraphDef.Builder graphBuilder = qmlContext.getGraphBuilder();
//            List<NodeDef> nodes = graphBuilder.getNodeList();
//            final List<WrappedNode> indexedOperations = IntStream.range(0, nodes.size())
//                    .mapToObj(index -> WrappedNode.of(nodes.get(index), index)).collect(Collectors.toList());
//            List<OutputMapping> mappings = indexedOperations
//                    .stream()
//                    .map(node -> node.operation.getInputList().stream().map(input -> OutputMapping.of(node.operation.getName(), input)))
//                    .flatMap(stream -> stream)
//                    .collect(Collectors.toList());
//            Map<String, WrappedNode> nodeMap = indexedOperations.stream()
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
//
////            List<WrappedNode> indexedToOperations = toOperations.stream().map(operation -> opFinder.find(operation)).collect(Collectors.toList());
////            List<WrappedNode> indexedFromOperations = fromOperations.stream().map(operation -> opFinder.find(operation)).collect(Collectors.toList());
//            toOperations.stream().forEach(operation -> dosazenyOperace.set(operation.index, true));
//            Deque<WrappedNode> queue = new ArrayDeque<>(fromOperations);
//            while (!queue.isEmpty()) {
//                WrappedNode indexedNode = queue.pop();
//                if (!dosazenyOperace.get(indexedNode.index)) {
//                    dosazenyOperace.set(indexedNode.index, true);
//                    queue.addAll(indexedNode.outputs);
//                }
//            }
//
//            OpFinder opFinder = nodeName -> nodeMap.get(nodeName);
//
//            // collect nodes from outputs to inputs
//            final List<Boolean> pruchoziOperace = IntStream.range(0, nodes.size()).mapToObj(index -> false).collect(Collectors.toList());
//            List<WrappedNode> pruchoziOperaceList = new ArrayList<>();
//            queue = new ArrayDeque<>(toOperations);
//            while (!queue.isEmpty()) {
//                WrappedNode indexedNode = queue.pop();
//                if (dosazenyOperace.get(indexedNode.index)) {
//                    pruchoziOperace.set(indexedNode.index, true);
//                    pruchoziOperaceList.add(indexedNode);
//                    dosazenyOperace.set(indexedNode.index, false); // aby se nepocitalo znovu
//                    List<WrappedNode> inputs = indexedNode.operation.getInputList().stream().map(name -> opFinder.find(name)).collect(Collectors.toList());
//                    queue.addAll(inputs);
//                }
//            }
//
//            //IntStream.range(0, nodes.size()).mapToObj(index -> false).collect(Collectors.toList());
//            List<Integer> visiciPocty = indexedOperations.stream().map(node -> 0).collect(Collectors.toList());
//
//            pruchoziOperaceList.forEach(node -> node.inputs(opFinder).forEach(inputNode -> {
//                if (pruchoziOperace.get(inputNode.index)) {
//                    visiciPocty.set(inputNode.index, visiciPocty.get(inputNode.index) + 1);
//                }
//            }));
//
//            Set<WrappedNode> doOpsSet = new HashSet<>();
//            doOpsSet.addAll(toOperations);
//            queue.clear(); // zbytecny
//            queue.addAll(toOperations);
//
//            Set<WrappedNode> stopOpsSet = new HashSet<>();
//            //doOpsSet.addAll(toOperations);
//            fromOperations.forEach(operation -> {
//                boolean isStopOperation = true;
//                for (WrappedNode inputOperation : operation.inputs(opFinder)) {
//                    if (visiciPocty.get(inputOperation.index) > 0) {
//                        isStopOperation = true;
//                        break;
//                    }
//                }
//                if (isStopOperation) {
//                    stopOpsSet.add(operation);
//                }
//            });
//
//            while (!queue.isEmpty()) {
//                WrappedNode indexedNode = queue.pop();
//
//            }
//
//            System.out.println(pruchoziOperaceList);
//
//
//
//        }
    }

    static class OutputMapping {
        private final String nodeName;
        private final String inputName;

        public OutputMapping(String nodeName, String inputName) {
            this.nodeName = nodeName;
            this.inputName = inputName;
        }

        static OutputMapping of(String nodeName, String inputName) {
            return new OutputMapping(nodeName, inputName);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Mapping{");
            sb.append("nodeName='").append(nodeName).append('\'');
            sb.append(", inputName='").append(inputName).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

//    static class WrappedNode {
//        private final int index;
//        private final NodeDef operation;
//        private final List<WrappedNode> outputs = new ArrayList<>();
//
//        WrappedNode(NodeDef operation, int index) {
//            this.operation = operation;
//            this.index = index;
//        }
//
//        static WrappedNode of(NodeDef operation, int index) {
//            return new WrappedNode(operation, index);
//        }
//
//        @Override
//        public String toString() {
//            final StringBuilder sb = new StringBuilder("WrappedNode{");
//            sb.append("index=").append(index);
//            sb.append(", operation=").append(operation);
//            sb.append(", outputs=").append(outputs);
//            sb.append('}');
//            return sb.toString();
//        }
//
//        public List<WrappedNode> inputs(Gradients.OpFinder opFinder) {
//            return this.operation.getInputList().stream().map(name -> opFinder.find(name)).collect(Collectors.toList());
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            WrappedNode that = (WrappedNode) o;
//            return index == that.index &&
//                    Objects.equals(operation, that.operation);
//        }
//
//        @Override
//        public int hashCode() {
//
//            return Objects.hash(index, operation);
//        }
//    }
}