package org.superbiz.builder;

import org.tensorflow.framework.GraphDef;
import org.tensorflow.framework.GraphDefOrBuilder;
import org.tensorflow.framework.NodeDef;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphBuilder {
    private Map<String, NodeBuilder> nodes = new LinkedHashMap<>();
    private NodeBuilder rootNode;

    public GraphBuilder() {

    }

    public NodeBuilder getRootNode() {
        if (rootNode == null & nodes.size() > 0) {
            rootNode = nodes.values().stream().reduce((first, second) -> second).orElse(null);
        }
        return rootNode;
    }

    public void setRootNode(NodeBuilder rootNode) {
        this.rootNode = rootNode;
    }

    public static GraphBuilder createGraph() {
        return new GraphBuilder();
    }

    public GraphBuilder constant(String value, Attribute... attributes) {
        Stream<Attribute> concatenated = Stream.concat(Stream.of(attributes), Stream.of(Attribute.generic("value", value)));
        return nodeForOperation(Operation.CONSTANT, concatenated);


////        final List<? extends Attribute> attributeList = Stream.priority(attributes)
////                .collect(Collectors.toList());
//        ConstantBuilder constant = new ConstantBuilder(value, attributes);
//        constants.put(constant.getName(), constant);
//        return this;
    }

    private GraphBuilder nodeForOperation(Operation operation, Stream<Attribute> attributesStream) {
        Attribute[] attributes = attributesStream.collect(Collectors.toList()).toArray(new Attribute[]{});
        NodeBuilder node = new NodeBuilder(operation, attributes);
        nodes.put(node.getName(), node);
        return this;
    }

    public GraphBuilder add(String nodeName1, String nodeName2, Attribute... attributes) {
        Stream<Attribute> a = Stream.of(attributes);
        Stream<Attribute> b = Stream.of(Attribute.generic("name1", nodeName1));
        Stream<Attribute> c = Stream.of(Attribute.generic("name2", nodeName2));
        Stream<Attribute> concatenated = Stream.concat(a, Stream.concat(b, c));
        Attribute[] array = concatenated.collect(Collectors.toList()).toArray(new Attribute[attributes.length + 2]);
        NodeBuilder node = new NodeBuilder(Operation.ADD, array);
        nodes.put(node.getName(), node);
        return this;
    }

    public GraphBuilder multiple(String nodeName1, String nodeName2, Attribute... attributes) {
        Method method = new Object() {}.getClass().getEnclosingMethod();
        System.out.println(method);
        return this;
    }

    public GraphDef build() {
        GraphDef.Builder graphDef = GraphDef.newBuilder();

        NodeDef.Builder node = getRootNode().asProtobufBuilder();
        //NodeDef.Builder node = NodeDef.newBuilder().setName("c1");
        graphDef.addNode(node);
        return graphDef.build();
    }
}
