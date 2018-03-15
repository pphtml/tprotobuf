package org.superbiz.run.model04;

import org.superbiz.util.ClasspathResource;
import org.tensorflow.framework.GraphDef;

import java.io.IOException;

public class SoftmaxTest {
    public static void main(String[] args) throws IOException {
        ClasspathResource resource = ClasspathResource.of("model04/softmax.simple.ch02.pb");
        GraphDef graph = GraphDef.parseFrom(resource.getInputStream());
        System.out.println(graph);
    }
}
