package org.superbiz.run;

import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import org.superbiz.util.ClasspathResource;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProtoRun {
    public static void main(String[] args) throws IOException {
        ClasspathResource resource = ClasspathResource.of("model01/helloworld.pb");
        byte[] bytes = ByteStreams.toByteArray(resource.getInputStream());
        Graph graph = new Graph();
        graph.importGraphDef(bytes);
        //final Session session = new Session(graph);

//        ArrayList<BasicOperation> operations = Lists.newArrayList(graph.operations());
//        for (BasicOperation operation : operations) {
//            System.out.println(operation);
//        }
        List<Operation> operations = StreamSupport.stream(Spliterators.spliteratorUnknownSize(graph.operations(), Spliterator.ORDERED), false)
                //.filter(operation -> operation.name().equals("add"))
                .collect(Collectors.toList());

        Operation c1 = operations.get(0);
        Operation c2 = operations.get(1);
        Operation add = operations.get(2);

        try(Session s = new Session(graph)) {
            s.runner().addTarget(c1).run();
            s.runner().addTarget(c2).run();
            //List<Tensor<?>> result = s.runner().addTarget(add).run();
            Tensor<String> res = s.runner().fetch(add.name(), 0).run().get(0).expect(String.class);
            System.out.println(res);
//            s.runner().addTarget(xUpdateOp).run();
//            s.runner().addTarget(xUpdateOp).run();
//
//            try(Tensor<Float> result = s.runner().fetch(xUpdateOp.name(), 0).run().get(0).expect(Float.class)){
//                System.out.println(result.floatValue());
//            }
        }


//        List<Tensor<?>> result = session.runner().run();
//        System.out.println(result);


//        final Session session = new Session(new SessionOptions());
//        GraphDef def = new GraphDef();
//        tensorflow.ReadBinaryProto(Env.Default(),
//                "somedir/trained_model.proto", def);
//        Status s = session.Create(def);
//        if (!s.ok()) {
//            throw new RuntimeException(s.error_message().getString());
//        }
    }
}
