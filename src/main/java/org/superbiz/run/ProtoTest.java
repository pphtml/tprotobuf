package org.superbiz.run;

import org.superbiz.util.ClasspathResource;
import org.tensorflow.framework.GraphDef;

import java.io.IOException;

public class ProtoTest {
    public static void main(String[] args) throws IOException {
        ClasspathResource resource = ClasspathResource.of("model01/helloworld.pb");
        //ClasspathResource resource = ClasspathResource.of("model02/graphdef.pb");
        GraphDef graph = GraphDef.parseFrom(resource.getInputStream());
        System.out.println(graph);
    }
}
