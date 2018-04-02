package org.superbiz.builder;

import org.junit.Test;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.type.Constant;
import org.superbiz.tf.type.Operation;
import org.superbiz.tf.type.Shape;
import org.superbiz.tf.type.Variable;
import org.tensorflow.Graph;

import static org.junit.Assert.assertEquals;
import static org.superbiz.tf.QMLContext.createSession;
import static org.superbiz.tf.attribute.Attribute.named;
import static org.superbiz.tf.type.Shape.shape;

public class UseCase02Test {
    // import tensorflow as tf
    //
    // #v1 = tf.get_variable("v1", shape=[3], initializer=tf.zeros_initializer)
    //v1 = tf.Variable(tf.zeros([3]), name="v1")
    //inc_v1 = v1.assign(v1+1)
    //init_op = tf.global_variables_initializer()
    //
    //with tf.Session() as sess:
    //    sess.run(init_op)
    //    res = sess.run(inc_v1)
    //    print(res)
    //
    //    graph_def = tf.get_default_graph().as_graph_def()
    //    tf.train.write_graph(graph_def, '/tmp', 'graphdef.pb', False)

    /**
     * One constant and one variable are added.
     */
    @Test
    public void buildGraph() {
        try (QMLContext tf = createSession("TensorFlow")) {
            //TF<Constant> c1 = tf.constant(1.0f, named("start"));
            TF<Variable> v1 = tf.variable(tf.zeros(shape(3)), named("v1"));
            //TF<Operation.Add> add = c1.add(v1);

            Graph graph = tf.buildGraph();
            Float result = tf.run(v1);
            //assertEquals(3.0f, result.floatValue(), 0.000001);
        }
    }



    // node {
    //  name: "zeros/shape_as_tensor"
    //  op: "Const"
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_INT32
    //    }
    //  }
    //  attr {
    //    key: "value"
    //    value {
    //      tensor {
    //        dtype: DT_INT32
    //        tensor_shape {
    //          dim {
    //            size: 1
    //          }
    //        }
    //        int_val: 3
    //      }
    //    }
    //  }
    //}
    //node {
    //  name: "zeros/Const"
    //  op: "Const"
    //  attr {
    //    key: "value"
    //    value {
    //      tensor {
    //        dtype: DT_FLOAT
    //        tensor_shape {
    //        }
    //        float_val: 0.0
    //      }
    //    }
    //  }
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //}
    //node {
    //  name: "zeros"
    //  op: "Fill"
    //  input: "zeros/shape_as_tensor"
    //  input: "zeros/Const"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "index_type"
    //    value {
    //      type: DT_INT32
    //    }
    //  }
    //}
    //node {
    //  name: "v1"
    //  op: "VariableV2"
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "container"
    //    value {
    //      s: ""
    //    }
    //  }
    //  attr {
    //    key: "shape"
    //    value {
    //      shape {
    //        dim {
    //          size: 3
    //        }
    //      }
    //    }
    //  }
    //  attr {
    //    key: "shared_name"
    //    value {
    //      s: ""
    //    }
    //  }
    //}
    //node {
    //  name: "v1/Assign"
    //  op: "Assign"
    //  input: "v1"
    //  input: "zeros"
    //  attr {
    //    key: "validate_shape"
    //    value {
    //      b: true
    //    }
    //  }
    //  attr {
    //    key: "use_locking"
    //    value {
    //      b: true
    //    }
    //  }
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //}
    //node {
    //  name: "v1/read"
    //  op: "Identity"
    //  input: "v1"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //}
    //node {
    //  name: "add/y"
    //  op: "Const"
    //  attr {
    //    key: "value"
    //    value {
    //      tensor {
    //        dtype: DT_FLOAT
    //        tensor_shape {
    //        }
    //        float_val: 1.0
    //      }
    //    }
    //  }
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //}
    //node {
    //  name: "add"
    //  op: "Add"
    //  input: "v1/read"
    //  input: "add/y"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //}
    //node {
    //  name: "Assign"
    //  op: "Assign"
    //  input: "v1"
    //  input: "add"
    //  attr {
    //    key: "use_locking"
    //    value {
    //      b: false
    //    }
    //  }
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //  attr {
    //    key: "validate_shape"
    //    value {
    //      b: true
    //    }
    //  }
    //}
    //node {
    //  name: "init"
    //  op: "NoOp"
    //  input: "^v1/Assign"
    //}
    //versions {
    //  producer: 26
    //}


    // node {
    //  name: "v1/Initializer/zeros/shape_as_tensor"
    //  op: "Const"
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //  attr {
    //    key: "value"
    //    value {
    //      tensor {
    //        dtype: DT_INT32
    //        tensor_shape {
    //          dim {
    //            size: 1
    //          }
    //        }
    //        int_val: 3
    //      }
    //    }
    //  }
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_INT32
    //    }
    //  }
    //}
    //node {
    //  name: "v1/Initializer/zeros/Const"
    //  op: "Const"
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //  attr {
    //    key: "value"
    //    value {
    //      tensor {
    //        dtype: DT_FLOAT
    //        tensor_shape {
    //        }
    //        float_val: 0.0
    //      }
    //    }
    //  }
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //}
    //node {
    //  name: "v1/Initializer/zeros"
    //  op: "Fill"
    //  input: "v1/Initializer/zeros/shape_as_tensor"
    //  input: "v1/Initializer/zeros/Const"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //  attr {
    //    key: "index_type"
    //    value {
    //      type: DT_INT32
    //    }
    //  }
    //}
    //node {
    //  name: "v1"
    //  op: "VariableV2"
    //  attr {
    //    key: "shared_name"
    //    value {
    //      s: ""
    //    }
    //  }
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "container"
    //    value {
    //      s: ""
    //    }
    //  }
    //  attr {
    //    key: "shape"
    //    value {
    //      shape {
    //        dim {
    //          size: 3
    //        }
    //      }
    //    }
    //  }
    //}
    //node {
    //  name: "v1/Assign"
    //  op: "Assign"
    //  input: "v1"
    //  input: "v1/Initializer/zeros"
    //  attr {
    //    key: "validate_shape"
    //    value {
    //      b: true
    //    }
    //  }
    //  attr {
    //    key: "use_locking"
    //    value {
    //      b: true
    //    }
    //  }
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //}
    //node {
    //  name: "v1/read"
    //  op: "Identity"
    //  input: "v1"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //}
    //node {
    //  name: "add/y"
    //  op: "Const"
    //  attr {
    //    key: "dtype"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "value"
    //    value {
    //      tensor {
    //        dtype: DT_FLOAT
    //        tensor_shape {
    //        }
    //        float_val: 1.0
    //      }
    //    }
    //  }
    //}
    //node {
    //  name: "add"
    //  op: "Add"
    //  input: "v1/read"
    //  input: "add/y"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //}
    //node {
    //  name: "Assign"
    //  op: "Assign"
    //  input: "v1"
    //  input: "add"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //  attr {
    //    key: "_class"
    //    value {
    //      list {
    //        s: "loc:@v1"
    //      }
    //    }
    //  }
    //  attr {
    //    key: "validate_shape"
    //    value {
    //      b: true
    //    }
    //  }
    //  attr {
    //    key: "use_locking"
    //    value {
    //      b: false
    //    }
    //  }
    //}
    //node {
    //  name: "init"
    //  op: "NoOp"
    //  input: "^v1/Assign"
    //}
    //versions {
    //  producer: 26
    //}
}
