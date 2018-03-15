package org.superbiz.run;

import com.google.common.io.ByteStreams;
import org.superbiz.util.ClasspathResource;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.IOException;

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
        ClasspathResource resource = ClasspathResource.of("model03/tensorun.pb");
        byte[] bytes = ByteStreams.toByteArray(resource.getInputStream());
//        File file = new File("tensorun.pb");
//        ByteSource source = Files.asByteSource(file);
//        byte[] bytes = source.read();

        Graph graph = new Graph();
        graph.importGraphDef(bytes);

        //Output<Object> x = graph.operation("init_x").output(0);
        Operation x = graph.operation("init_x");
        Operation xUpdateOp = graph.operation("x_get_x_plus_step");

        try(Session s = new Session(graph)) {
            s.runner().addTarget(x).run();
            s.runner().addTarget(xUpdateOp).run();

            try(Tensor<Float> result = s.runner().fetch(xUpdateOp.name(), 0).run().get(0).expect(Float.class)){
                System.out.println(result.floatValue());
            }
        }
    }
}