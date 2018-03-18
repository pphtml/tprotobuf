package org.superbiz.run;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.superbiz.util.ClasspathResource;
import org.tensorflow.framework.GraphDef;

import java.io.File;
import java.io.IOException;

public class ProtoParser {
    public static void main(String[] args) throws IOException {
        //ClasspathResource resource = ClasspathResource.of("model00/helloworld.pb");
        //ClasspathResource resource = ClasspathResource.priority("model02/graphdef.pb");

        File file = new File("/tmp/graphdef.pb");
        ByteSource source = Files.asByteSource(file);
        byte[] bytes = source.read();

        //GraphDef graph = GraphDef.parseFrom(resource.getInputStream());
        GraphDef graph = GraphDef.parseFrom(bytes);
        System.out.println(graph);
    }
}
