package org.superbiz.builder;

import org.junit.Test;
import org.tensorflow.framework.GraphDef;

import static org.superbiz.builder.Attribute.named;
import static org.superbiz.builder.GraphBuilder.createGraph;

public class GraphBuilderTest {
    @Test
    public void basicGraphCreation() {
        GraphDef graph = createGraph()
                .constant("Hello", named("c1"))
                .constant(" World!", named("c2"))
                .add("c1", "c2", named("result"))
                .multiple("x1", "x2", named("ble"))
                .build();
        System.out.println(graph);
    }

//    node {
//        name: "Const"
//        op: "Const"
//        attr {
//            key: "dtype"
//            value {
//                type: DT_STRING
//            }
//        }
//        attr {
//            key: "value"
//            value {
//                tensor {
//                    dtype: DT_STRING
//                    tensor_shape {
//                    }
//                    string_val: "Hello"
//                }
//            }
//        }
//    }
//    node {
//        name: "Const_1"
//        op: "Const"
//        attr {
//            key: "value"
//            value {
//                tensor {
//                    dtype: DT_STRING
//                    tensor_shape {
//                    }
//                    string_val: " World!"
//                }
//            }
//        }
//        attr {
//            key: "dtype"
//            value {
//                type: DT_STRING
//            }
//        }
//    }
//    node {
//        name: "add"
//        op: "Add"
//        input: "Const"
//        input: "Const_1"
//        attr {
//            key: "T"
//            value {
//                type: DT_STRING
//            }
//        }
//    }
//    versions {
//        producer: 26
//    }


    // h = tf.constant("Hello")
    //w = tf.constant(" World!")
    //hw = h + w
    //
    //with tf.Session() as sess:
    //    ans = sess.run(hw)
    //    graph_def = tf.get_default_graph().as_graph_def()
    //    tf.train.write_graph(graph_def, '/tmp', 'helloworld.pb', False)
    //
    //print(ans)
}