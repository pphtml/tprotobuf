package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.attribute.Attribute;
import org.tensorflow.Output;
import org.tensorflow.Tensor;
import org.tensorflow.Tensors;

import static org.superbiz.tf.attribute.Attribute.named;
import static org.superbiz.tf.util.TensorflowConstants.*;

public class Variable extends AbstractNode implements TFType, NamingSequence {

    private final InitializingOperation initializingOperation;

    private Variable(InitializingOperation initializingOperation, Attribute[] attributes) {
        super(attributes);
        this.initializingOperation = initializingOperation;
    }

    public static Variable of(InitializingOperation initializingOperation, Attribute[] attributes) {
        return new Variable(initializingOperation, attributes);
    }

    public static Variable of(Attribute[] attributes) {
        return new Variable(null, attributes);
    }

    @Override
    public void build(QMLContext qmlContext) {
//        Tensor tensor = Tensors.create((Float)value);
//        qmlContext.registerAutoCloseable(tensor);
//                Output<Float> xVar = g.opBuilder("Variable", "x")
//                        .setAttr("dtype", zero.dataType())
//                        .setAttr("shape", zero.shape())
//                        .build().output(0);
        this.name = qmlContext.getNamingService().name(attributes, this);
        throw new UnsupportedOperationException();

//        if (this.initializingOperation != null) {
//            TF<Constant> zeroConstant = qmlContext.constant(0.0f, named(this.name + "/value"));
//            Shape shape = guessShape();
//            TF<Constant> shapeConstant = qmlContext.constant(shape.tfValue(), named(this.name + "/shape_const"));
//            //node {
//            //  name: "zeros"
//            //  op: "Fill"
//            //  input: "zeros/shape_as_tensor"
//            //  input: "zeros/Const"
//            //  attr {
//            //    key: "T"
//            //    value {
//            //      type: DT_FLOAT
//            //    }
//            //  }
//            //  attr {
//            //    key: "index_type"
//            //    value {
//            //      type: DT_INT32
//            //    }
//            //  }
//            //}
//            //node {
//            //  name: "v1"
//            //  op: "VariableV2"
//            //  attr {
//            //    key: "dtype"
//            //    value {
//            //      type: DT_FLOAT
//            //    }
//            //  }
//            //  attr {
//            //    key: "container"
//            //    value {
//            //      s: ""
//            //    }
//            //  }
//            //  attr {
//            //    key: "shape"
//            //    value {
//            //      shape {
//            //        dim {
//            //          size: 3
//            //        }
//            //      }
//            //    }
//            //  }
//
////            qmlContext.getGraph().opBuilder(OPERATION_FILL, this.name + "/fill")
////                    .setAttr(DTYPE, tensor.dataType())
////                    .setAttr(VALUE, tensor)
////                    .build().output(0);
//        }


//        this.output = qmlContext.getGraph().opBuilder(NODE_VARIABLE, this.name)
//                .setAttr(DTYPE, tensor.dataType())
//                .setAttr(VALUE, tensor)
//                .build().output(0);
    }

    @Override
    public Output<?> getOutput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPrefix() {
        return "VARIABLE";
    }

    private Shape guessShape() {
        if (this.initializingOperation != null) {
            return this.initializingOperation.getShape();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String getTemplateName() {
        return "qml/template/variable-from-constant.pb.txt";
    }

//    private final T value;
//
//    public Constant(T value) {
//        this.value = value;
//    }
//
//    public static <T> Constant priority(T value) {
//        return new Constant(value);
//    }

}
