package org.superbiz.run;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import org.superbiz.util.ClasspathResource;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.framework.GraphDef;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TemplateRun {
    public static void main(String[] args) throws IOException {
        ClasspathResource resource = ClasspathResource.of("qml/template/random-uniform.pb.txt");
        //System.out.println(resource);

//        URL url = Resources.getResource("foo.txt");
        String text = Resources.toString(resource.getUrl(), Charsets.UTF_8);
        //System.out.println(text);

        //GraphDef.parseFrom(text);
        GraphDef.Builder builder = GraphDef.newBuilder();
        ExtensionRegistry registry = ExtensionRegistry.newInstance();

        parseFromString(text, registry, builder);

        //System.out.println(builder);

        byte[] bytes = builder.build().toByteArray();

        Graph graph = new Graph();
        graph.importGraphDef(bytes);
        //final Session session = new Session(graph);

//        ArrayList<BasicOperation> operations = Lists.newArrayList(graph.operations());
//        for (BasicOperation operation : operations) {
//            System.out.println(operation);
//        }

        Operation variable = graph.operation("Variable");
//        List<BasicOperation> operations = StreamSupport.stream(Spliterators.spliteratorUnknownSize(graph.operations(), Spliterator.ORDERED), false)
//                //.filter(operation -> operation.name().equals("add"))
//                .collect(Collectors.toList());

        try(Session s = new Session(graph)) {
//            try(Tensor<Float> result = s.runner().fetch(out.name(), 0).run().get(0).expect(Float.class)){
//                System.out.println(result.floatValue());
//            }
            //Session.Run out = s.runner().addTarget(variable).runAndFetchMetadata();
            //Tensor<?> res = s.runner().addTarget(variable).fetch("add", 0).run().get(0);
            Tensor<?> res = s.runner().fetch("Variable/Assign", 0).run().get(0);
            System.out.println(res);
            System.out.println(res.numElements());
            System.out.println(res.dataType());
            FloatBuffer fb = FloatBuffer.allocate(8);
            res.writeTo(fb);
            System.out.println(fb.get(0));
            System.out.println(fb.get(1));
            System.out.println(fb.get(2));
            System.out.println(fb.get(3));
        }


    }

    public static void parseFromString(CharSequence input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws TextFormat.ParseException {
        builder.clear();
        TextFormat.merge(input, extensionRegistry, builder);
    }
}
