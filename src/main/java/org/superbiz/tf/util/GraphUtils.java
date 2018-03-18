package org.superbiz.tf.util;

import com.google.protobuf.InvalidProtocolBufferException;
import org.superbiz.tf.QMLGraphException;
import org.tensorflow.Graph;
import org.tensorflow.framework.GraphDef;

public class GraphUtils {
    public static String graphToProtobufString(Graph graph) {
        try {
            final byte[] graphDefBytes = graph.toGraphDef();
            GraphDef graphDef = GraphDef.parseFrom(graphDefBytes);
            return graphDef.toString();
        } catch (InvalidProtocolBufferException e) {
            throw new QMLGraphException(e);
        }
    }
}
