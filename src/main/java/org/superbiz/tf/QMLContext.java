package org.superbiz.tf;

import org.superbiz.engine.Engine;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.operation.Constant;
import org.superbiz.tf.operation.Gradient;
import org.superbiz.tf.operation.Operation;
import org.superbiz.tf.operation.TileAndShapeOperation;
import org.superbiz.tf.operation.Variable;
import org.superbiz.tf.type.*;
import org.superbiz.tf.util.NamingService;
//import org.tensorflow.DataType;
//import org.tensorflow.Graph;
//import org.tensorflow.Session;
//import org.tensorflow.Tensor;
//import org.tensorflow.framework.GraphDef;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//import static com.google.protobuf.TensorContentEncoder.toStringTensorContent; // TODO prehodit
import static org.superbiz.engine.Engine.DEFAULT_ML_FRAMEWORK;
import static org.superbiz.engine.Engine.findEngine;
import static org.superbiz.tf.util.AutoCloseablePriority.priority;

public class QMLContext implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(QMLContext.class.getName());
    //private final List<AutoCloseable> autoCloseables = new ArrayList<>();
    private final List<TF<Variable, ?>> variables = new ArrayList<>();
    //private final List<TF<? extends TFType>> nodes = new ArrayList<>();
    private NamingService namingService = new NamingService();
    private boolean graphBuilderOpen = true;
    private final Engine engine;

    public QMLContext(Engine engine) {
        this.engine = engine;
    }


    public static QMLContext createContext(Engine engine) {
        QMLContext result = new QMLContext(engine);
        return result;
    }

    public static QMLContext createContext(String engineName) {
        return createContext(findEngine(engineName));
    }

    public static QMLContext createContext() {
        return createContext(DEFAULT_ML_FRAMEWORK);
    }

//    public <T> TF<Constant> constant(T value, Attribute... attributes) {
//        return register(TF.of(Constant.of(value, attributes), this));
//    }
    public <T extends TFType, NTType> TF<T, NTType> addToGraph(TF<T, NTType> node, QMLContext qmlContext) {
        if (!graphBuilderOpen) {
            throw new IllegalStateException("Graph has already been built. It is not allowed to add new nodes to graph at this point. Execution of run() or fetch() builds graph automatically.");
        }
        engine.addToGraph(node, qmlContext);
        return node;
    }

    public <NTType> TF<Variable, NTType> variable(/*Class<NTType> clazz, */InitializingOperation<NTType> initializingOperation, Attribute... attributes) {
        TF<Variable, NTType> result = addToGraph(TF.of(Variable.of(initializingOperation, attributes), this), this);
        variables.add(result);
        return result;
    }

    public <NTType> TF<Constant, NTType> constant(InitializingOperation<NTType> initializingOperation, Attribute... attributes) {
        TF<Constant, NTType> result = addToGraph(TF.of(Constant.of(initializingOperation, attributes), this), this);
        return result;
    }

    @Override
    public void close() {
//        autoCloseables.stream()
//                .sorted((left, right) -> priority(right) - priority(left))
//                .forEach(autoCloseable -> {
//                    LOGGER.fine(String.format("Closing %s", autoCloseable));
//                    try {
//                        autoCloseable.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
        try {
            engine.close();
        } catch (Exception e) {
            final String message = String.format("Cannot close engine %s because of: %s", engine.getClass().getName(), e.getMessage());
            LOGGER.log(Level.SEVERE, message, e);
            throw new RuntimeException(e);
        }
    }

    public List<TF<? extends TFType, ?>> getNodes() {
        return this.engine.getNodes();
    }




//    public <T extends TFType, NTType> TF<T, NTType> makeFromTemplate(TF<T, NTType> node, QMLContext qmlContext) {
//        node.build(qmlContext);
//        String templateName = node.getFMTemplateName();
//        String template = node.getTemplateText();
//        try {
//            String templateWithValues = fillInVariables(templateName, template, node);
//            parseFromString(templateWithValues, registry, graphBuilder);
//            return node;
//        } catch (TextFormat.ParseException e) {
//            throw new IllegalStateException(e);
//        }
//    }



//    public <T extends AutoCloseable> T registerAutoCloseable(T autoCloseableSomething) {
//        this.autoCloseables.add(autoCloseableSomething);
//        return autoCloseableSomething;
//    }

    public NamingService getNamingService() {
        return namingService;
    }

    public <NTType> void run(TF<? extends TFType, NTType> node) {
        engine.run(node);
    }

    public <NTType> NTType fetch(TF<? extends TFType, NTType> node) {
        return engine.fetch(node);
    }

    public <NTType> VectorWrapper<NTType> fetchVector(String nodeName) {
        return engine.fetchVector(nodeName);
    }

    public <NTType> VectorWrapper<NTType> fetchVector(TF<? extends TFType, NTType> node) {
        return fetchVector(node.getName());
    }

    public static InitializingOperation zeros(Object shape) {
        return new InitializingOperation(){
            @Override
            public Shape getShape() {
                throw new UnsupportedOperationException();
            }

            @Override
            public Object getInitialValue() {
                throw new UnsupportedOperationException();
            }

            @Override
            public DType getDType() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isVector() {
                return true;
            }
        };
    }

    public static InitializingOperation<Integer> value(int value) {
        return new InitializingOperation<Integer>(){
            @Override
            public Object getInitialValue() {
                return Integer.valueOf(value).toString();
            }

            @Override
            public DType getDType() {
                return DType.DT_INT32;
            }


            @Override
            public boolean isVector() {
                return false;
            }
        };
    }

    public static InitializingOperation<Long> value(long value) {
        return new InitializingOperation<Long>(){
            @Override
            public Object getInitialValue() {
                return Long.valueOf(value).toString();
            }

            @Override
            public DType getDType() {
                return DType.DT_INT64;
            }


            @Override
            public boolean isVector() {
                return false;
            }
        };
    }

    public static InitializingOperation<Integer> values(int... values) {
        return new InitializingOperation<Integer>(){
            @Override
            public Shape getShape() {
                return Shape.shape(values.length);
            }

            @Override
            public Object getInitialValue() {
                return values;
            }

            @Override
            public DType getDType() {
                return DType.DT_INT32;
            }

            @Override
            public boolean isVector() {
                return true;
            }
        };
    }

    public static InitializingOperation<Long> values(long... values) {
        return new InitializingOperation<Long>(){
            @Override
            public Shape getShape() {
                return Shape.shape(values.length);
            }

            @Override
            public Object getInitialValue() {
                return values;
            }

            @Override
            public DType getDType() {
                return DType.DT_INT64;
            }

            @Override
            public boolean isVector() {
                return true;
            }
        };
    }

    public static InitializingOperation<Float> values(float... values) {
        return new InitializingOperation<Float>(){
            @Override
            public Shape getShape() {
                return Shape.shape(values.length);
            }

            @Override
            public Object getInitialValue() {
                return values;
            }

            @Override
            public DType getDType() {
                return DType.DT_FLOAT;
            }

            @Override
            public boolean isVector() {
                return true;
            }
        };
    }

    public static InitializingOperation<Double> values(double... values) {
        return new InitializingOperation<Double>(){
            @Override
            public Shape getShape() {
                return Shape.shape(values.length);
            }

            @Override
            public Object getInitialValue() {
                return values;
            }

            @Override
            public DType getDType() {
                return DType.DT_DOUBLE;
            }

            @Override
            public boolean isVector() {
                return true;
            }
        };
    }

    public static InitializingOperation<Float> value(float value) {
        return new InitializingOperation<Float>(){
            @Override
            public String getInitialValue() {
                return Float.valueOf(value).toString();
            }

            @Override
            public DType getDType() {
                return DType.DT_FLOAT;
            }

            @Override
            public boolean isVector() {
                return false;
            }
        };
    }

    public static InitializingOperation<Double> value(double value) {
        return new InitializingOperation<Double>(){
            @Override
            public String getInitialValue() {
                return Double.valueOf(value).toString();
            }

            @Override
            public DType getDType() {
                return DType.DT_DOUBLE;
            }

            @Override
            public boolean isVector() {
                return false;
            }
        };
    }

    public TF<Initializer, ?> globalVariablesInitializer(Attribute... attributes) {
        return addToGraph(TF.of(Initializer.of(attributes), this), this);
    }

    public List<TF<Variable, ?>> getVariables() {
        return variables;
    }

    // public <R extends TFType> TF<BasicOperation.Subtract, NTType> subtract(TF<R, NTType> operand, Attribute... attributes) {
    public <R extends TFType, NTType> TF<Operation.Square, NTType> square(TF<R, NTType> operation, Attribute... attributes) {
        TF of = TF.of(new Operation.Square(operation, attributes), this);
        return this.addToGraph(of, this);
    }

    public <R extends TFType, NTType> TF<Operation.ReduceMean, NTType> reduceMean(TF<R, NTType> operation, Attribute... attributes) {
        TF of = TF.of(new Operation.ReduceMean(operation, attributes), this);
        return this.addToGraph(of, this);
    }

    public <R extends TFType, S extends TFType, NTTypeSource, NTTypeMultiples> TF<Operation.Tile, NTTypeSource> tile(TF<R, NTTypeSource> source, TF<S, NTTypeMultiples> multiples, Attribute... attributes) {
        TF of = TF.of(new TileAndShapeOperation.Tile(source, multiples, attributes), this);
        return this.addToGraph(of, this);
    }

//     public <R extends TFType> TF<Operation.Add, NTType> add(TF<R, NTType> operand, Attribute... attributes) {
//        TF of = of(new Operation.Add(this, operand, attributes), qmlContext);
//        return qmlContext.addToGraph(of, qmlContext);
//    }


    public <R extends TFType, NTType, S> TF<Operation.ReduceMean, S> cast(TF<R, NTType> operation, Class<S> type, Attribute... attributes) {
        TF of = TF.of(new Operation.Cast(operation, type, attributes), this);
        return this.addToGraph(of, this);
    }

    public <R extends TFType, NTType> TF<Gradient.Gradients, NTType> gradients(TF<R, NTType> operation, List<TF<Variable, ?>> variables, Attribute... attributes) {
        Gradient.Gradients gradients = new Gradient.Gradients(operation, variables, attributes);
        TF<? extends TFType, ?> value = gradients.computeGradients(this);
        return (TF<Gradient.Gradients, NTType>) value;
//        TF of = TF.of(gradients, this);
//        return this.addToGraph(of, this);
    }

//    public GraphDef.Builder getGraphBuilder() {
//        return graphBuilder;
//    }
}
