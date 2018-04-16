package org.superbiz.run;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import org.superbiz.util.ClasspathResource;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProtoRun2 {
    // based on https://stackoverflow.com/questions/48118747/why-does-a-java-tensorflow-session-seem-to-reset-state-when-a-python-tensorflow
    // import tensorflow as tf
    //
    //zero = tf.constant(0.0)
    //step = tf.constant(1.0)
    //xVar = tf.Variable(initial_value=zero, name="x")
    //x = tf.assign(xVar, zero)
    //xUpdateOp = tf.assign_add(x, step)

    public static void main(String[] args) throws IOException {
        //CSVClasspathResource resource = CSVClasspathResource.priority("model03/tensorun.pb");
//        CSVClasspathResource resource = CSVClasspathResource.priority("model00/helloworld.pb");
//        byte[] bytes = ByteStreams.toByteArray(resource.getInputStream());

        File file = new File("tensorun.pb");
        ByteSource source = Files.asByteSource(file);
        byte[] bytes = source.read();

        Graph graph = new Graph();
        graph.importGraphDef(bytes);

        Operation out = graph.operation("add");
        System.out.println(out);

        try(Session s = new Session(graph)) {
//            try(Tensor<Float> result = s.runner().fetch(out.name(), 0).run().get(0).expect(Float.class)){
//                System.out.println(result.floatValue());
//            }
            Tensor<?> res = s.runner().addTarget(out).fetch("add", 0).run().get(0);
            System.out.println(res);
        }

//        //Output<Object> x = graph.operation("init_x").output(0);
//        BasicOperation x = graph.operation("init_x");
//        BasicOperation xUpdateOp = graph.operation("x_get_x_plus_step");
//
//        try(Session s = new Session(graph)) {
//            s.runner().addTarget(x).run();
//            s.runner().addTarget(xUpdateOp).run();
//
//            try(Tensor<Float> result = s.runner().fetch(xUpdateOp.name(), 0).run().get(0).expect(Float.class)){
//                System.out.println(result.floatValue());
//            }
//        }
    }
}
