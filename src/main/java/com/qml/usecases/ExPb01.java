package com.qml.usecases;

import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import com.google.protobuf.ExtensionRegistry;
import org.superbiz.util.ClasspathResource;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.framework.GraphDef;

import java.io.IOException;
import java.nio.FloatBuffer;

public class ExPb01 {
    public static void main(String[] args) throws IOException {
        ClasspathResource resource = ClasspathResource.of("com/qml/usecases/ex01.pb");
        byte[] bytes = ByteStreams.toByteArray(resource.getInputStream());
        Graph graph = new Graph();
        graph.importGraphDef(bytes);


        //BasicOperation variable = graph.operation("add_1");

        try (Session s = new Session(graph)) {
//            try(Tensor<Float> result = s.runner().fetch(out.name(), 0).run().get(0).expect(Float.class)){
//                System.out.println(result.floatValue());
//            }
            //Session.Run out = s.runner().addTarget(variable).runAndFetchMetadata();
            //Tensor<?> res = s.runner().addTarget(variable).fetch("add", 0).run().get(0);
            s.runner().addTarget("init").run();
            Tensor<?> res = s.runner().fetch("add_1", 0).run().get(0);
            System.out.println(res);
            System.out.println(res.numElements());
            System.out.println(res.dataType());
            System.out.println(res.intValue());
//            FloatBuffer fb = FloatBuffer.allocate(8);
//            res.writeTo(fb);
//            System.out.println(fb.get(0));
//            System.out.println(fb.get(1));
//            System.out.println(fb.get(2));
//            System.out.println(fb.get(3));
        }
    }
}
