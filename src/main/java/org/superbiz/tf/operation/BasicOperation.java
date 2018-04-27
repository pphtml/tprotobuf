package org.superbiz.tf.operation;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.*;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.shape.ShapeOperation;
import org.superbiz.tf.type.*;

import static org.superbiz.tf.QMLContext.value;
import static org.superbiz.tf.QMLContext.values;
import static org.superbiz.tf.attribute.Attribute.named;

public class BasicOperation {

    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Add\"\n" +
            "  input: \"${operand}\"\n" +
            "  input: \"${operand2}\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("add")
    @ShapeTransformations({@ShapeTransformation("1,1->1"), @ShapeTransformation("1,N->N"), @ShapeTransformation("N,1->N"), @ShapeTransformation("N,N->N")})
    public static class Add extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("operand")
        public final TF<?, ?> operand1;
        @TFInput
        @Mapping("operand2")
        public final TF<?, ?> operand2;

        public <T extends TFType, R extends TFType, NTType> Add(TF<T, NTType> operand1, TF<R, NTType> operand2, Attribute[] attributes) {
            super(attributes);
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.setDType(this.operand1.getDType());
            super.postInit();
        }
    }

    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Sub\"\n" +
            "  input: \"${operand}\"\n" +
            "  input: \"${operand2}\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("subtract")
    @ShapeTransformations({@ShapeTransformation("1,1->1"), @ShapeTransformation("1,N->N"), @ShapeTransformation("N,1->N"), @ShapeTransformation("N,N->N")})
    public static class Subtract extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("operand")
        public final TF<?, ?> operand1;
        @TFInput
        @Mapping("operand2")
        public final TF<?, ?> operand2;

        public <T extends TFType, R extends TFType, NTType> Subtract(TF<T, NTType> operand1, TF<R, NTType> operand2, Attribute[] attributes) {
            super(attributes);
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.setDType(this.operand1.getDType());
            super.postInit();
        }

        @Override
        public TF<? extends TFType, ?> createGradientOp(QMLContext qmlContext, TF<? extends TFType, ?> output) {
//            ShapeOperation shapeOperation = this.getShapeOperation();
//            Shape shape = shapeOperation.getTheOnlyFromShape();

            return output.negative();
        }
    }

    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Mul\"\n" +
            "  input: \"${operand}\"\n" +
            "  input: \"${operand2}\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("multiply")
    @ShapeTransformations({@ShapeTransformation("1,1->1"), @ShapeTransformation("1,N->N"), @ShapeTransformation("N,1->N"), @ShapeTransformation("N,N->N")})
    public static class Multiply extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("operand")
        public final TF<?, ?> operand1;
        @TFInput
        @Mapping("operand2")
        public final TF<?, ?> operand2;

        public <T extends TFType, R extends TFType, NTType> Multiply(TF<T, NTType> operand1, TF<R, NTType> operand2, Attribute[] attributes) {
            super(attributes);
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.setDType(this.operand1.getDType());
            super.postInit();
        }

        @Override
        public TF<? extends TFType, ?> createGradientOp(QMLContext qmlContext, TF<? extends TFType, ?> output) {
            return super.createGradientOp(qmlContext, output);
        }
    }

// divide
//node {
//  name: "div"
//  op: "RealDiv"
//  input: "w"
//  input: "y"
//  attr {
//    key: "T"
//    value {
//      type: DT_FLOAT
//    }
//  }
//}
    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"RealDiv\"\n" +
            "  input: \"${operand}\"\n" +
            "  input: \"${operand2}\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("divide")
    @ShapeTransformations({@ShapeTransformation("1,1->1"), @ShapeTransformation("1,N->N"), @ShapeTransformation("N,1->N"), @ShapeTransformation("N,N->N")})
    public static class Divide extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("operand")
        public final TF<?, ?> operand1;
        @TFInput
        @Mapping("operand2")
        public final TF<?, ?> operand2;

        public <T extends TFType, R extends TFType, NTType> Divide(TF<T, NTType> operand1, TF<R, NTType> operand2, Attribute[] attributes) {
            super(attributes);
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.setDType(this.operand1.getDType());
            super.postInit();
        }
    }

    // node {
    //  name: "Square"
    //  op: "Square"
    //  input: "sub"
    //  attr {
    //    key: "T"
    //    value {
    //      type: DT_FLOAT
    //    }
    //  }
    //}
    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Square\"\n" +
            "  input: \"${operand}\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("square")
    @ShapeTransformations({@ShapeTransformation("N->N"), @ShapeTransformation("1->1")})
    public static class Square extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("operand")
        public final TF<?, ?> operand;

        public <T extends TFType, R extends TFType, NTType> Square(TF<T, NTType> operand, Attribute[] attributes) {
            super(attributes);
            this.operand = operand;
            this.setDType(this.operand.getDType());
            super.postInit();
        }

        @Override
        public TF<? extends TFType, ?> createGradientOp(QMLContext qmlContext, TF<? extends TFType, ?> output) {
            ShapeOperation shapeOperation = this.getShapeOperation();
            Shape shape = shapeOperation.getTheOnlyFromShape();
            TF<Constant, Float> squareDerivative = qmlContext.constant(value(2.0f));
            TF input = TF.of(this.getInputs().get(0), qmlContext);
            TF multiplied = input.multiply(squareDerivative, named("squareGrad/Mul"));
            TF result = multiplied.multiply(output, named("squareGrad/Mul2"));
            return result;


//            if (shape.dimensions() != 1) {
//                throw new UnsupportedOperationException("Gradient for ReduceMean is not supported for bigger dimensions than 1D.");
//            }
//            TF<? extends TFType, Float> floatOutput = (TF<? extends TFType, Float>) output;
//            TF<Constant, Integer> repeatsCount = qmlContext.constant(values(shape.asInts()));
//            TF<Operation.Tile, Float> tiles = qmlContext.tile(floatOutput, repeatsCount);
//
//            TF<Operation.Divide, Float> result = tiles.divide(qmlContext.constant(value(shape.getSize0().floatValue())));
//            return result;
        }
    }

    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Neg\"\n" +
            "  input: \"${operand}\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("neg")
    @ShapeTransformations({@ShapeTransformation("N->N"), @ShapeTransformation("1->1")})
    public static class Negative extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("operand")
        public final TF<?, ?> operand;

        public <T extends TFType, R extends TFType, NTType> Negative(TF<T, NTType> operand, Attribute[] attributes) {
            super(attributes);
            this.operand = operand;
            this.setDType(this.operand.getDType());
            super.postInit();
        }
    }

    @TemplateInline("node {\n" +
            "  name: \"${nodeName}/const\"\n" +
            "  op: \"Const\"\n" +
            "  attr {\n" +
            "    key: \"dtype\"\n" +
            "    value {\n" +
            "      type: DT_INT32\n" +
            "    }\n" +
            "  }\n" +
            "  attr {\n" +
            "    key: \"value\"\n" +
            "    value {\n" +
            "      tensor {\n" +
            "        dtype: DT_INT32\n" +
            "        tensor_shape {\n" +
            "          dim {\n" +
            "            size: 1\n" +
            "          }\n" +
            "        }\n" +
            "        int_val: 0\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n" +
            "node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Mean\"\n" +
            "  input: \"${operand}\"\n" +
            "  input: \"${nodeName}/const\"\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "  attr {\n" +
            "    key: \"keep_dims\"\n" + // TODO change to keepdims
            "    value {\n" +
            "      b: false\n" +
            "    }\n" +
            "  }\n" +
            "  attr {\n" +
            "    key: \"Tidx\"\n" +
            "    value {\n" +
            "      type: DT_INT32\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("square")
    @ShapeTransformation("N->1")
    public static class ReduceMean extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("operand")
        public final TF<?, ?> operand;

        public <T extends TFType, R extends TFType, NTType> ReduceMean(TF<T, NTType> operand, Attribute[] attributes) {
            super(attributes);
            this.operand = operand;
            this.setDType(this.operand.getDType());
            super.postInit();
        }

        @Override
        public TF<? extends TFType, ?> createGradientOp(QMLContext qmlContext, TF<? extends TFType, ?> output) {
            ShapeOperation shapeOperation = this.getShapeOperation();
            Shape shape = shapeOperation.getTheOnlyFromShape();
            if (shape.dimensions() != 1) {
                throw new UnsupportedOperationException("Gradient for ReduceMean is not supported for bigger dimensions than 1D.");
            }
            TF<? extends TFType, Float> floatOutput = (TF<? extends TFType, Float>) output;
            TF<Constant, Integer> repeatsCount = qmlContext.constant(values(shape.asInts()));
            TF<Operation.Tile, Float> tiles = qmlContext.tile(floatOutput, repeatsCount);

            TF<Operation.Divide, Float> result = tiles.divide(qmlContext.constant(value(shape.getSize0().floatValue())));
            return result;
        }
    }

    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Cast\"\n" +
            "  input: \"${operand}\"\n" +
            "  attr {\n" +
            "    key: \"SrcT\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "  attr {\n" +
            "    key: \"DstT\"\n" +
            "    value {\n" +
            "      type: ${targetDType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("cast")
    @ShapeTransformations({@ShapeTransformation("N->N"), @ShapeTransformation("1->1")})
    public static class Cast<R> extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("operand")
        public final TF<?, ?> operand;
        private final Class<R> targetType;

        public <T extends TFType, NTType> Cast(TF<T, NTType> operand, Class<R> type, Attribute[] attributes) {
            super(attributes);
            this.operand = operand;
            this.setDType(this.operand.getDType());
            this.targetType = type;
            super.postInit();
        }

        @Mapping("targetDType")
        public String targetDType() {
            DType dType = DType.dTypeForJavaType(targetType);
            return dType.name();
        }
    }
}
