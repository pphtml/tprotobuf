package org.superbiz.run;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.superbiz.util.ClasspathResource;
import org.tensorflow.framework.GraphDef;

import java.io.File;
import java.io.IOException;

public class ProtoParser {
    public static void main(String[] args) throws IOException {
        ClasspathResource resource = ClasspathResource.of("com/qml/usecases/ex01.pb");

        //File file = new File("/tmp/graphdef.pb");
//        File file = new File("/tmp/ex01f.pb");
//        ByteSource source = Files.asByteSource(file);
//        byte[] bytes = source.read();

        GraphDef graph = GraphDef.parseFrom(resource.getInputStream());
        String string = graph.toString();
        //GraphDef graph = GraphDef.parseFrom(bytes);
        System.out.println(graph);
    }
}
