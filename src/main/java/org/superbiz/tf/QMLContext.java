package org.superbiz.tf;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.*;
import org.superbiz.tf.util.NamingService;
import org.superbiz.tf.util.NodeFreemarkerVariableReader;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.framework.GraphDef;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.protobuf.TensorContentEncoder.toStringTensorContent;
import static org.superbiz.tf.util.AutoCloseablePriority.priority;

public class QMLContext implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(QMLContext.class.getName());
    private static final String DEFAULT_ML_FRAMEWORK = "TensorFlow";
    private final List<AutoCloseable> autoCloseables = new ArrayList<>();
    private final List<TF<Variable, ?>> variables = new ArrayList<>();
    //private final List<TF<? extends TFType>> nodes = new ArrayList<>();
    private Graph graph;
    private NamingService namingService = new NamingService();
    private Session session;
    private GraphDef.Builder graphBuilder = GraphDef.newBuilder();
    private boolean graphBuilderOpen = true;
    private ExtensionRegistry registry = ExtensionRegistry.newInstance();

    private Configuration freemarkerConfig;
    private StringTemplateLoader stringTemplateLoader;

    public static QMLContext createSession(String mlFramework) {
        if (!DEFAULT_ML_FRAMEWORK.equals(mlFramework)) {
            throw new QMLContextException(String.format("ML Framework %s is not registered/supported. " +
                    "Check outcome priority listSupportedFrameworks().", mlFramework));
        }
        QMLContext result = new QMLContext();
        result.initialize();
        return result;
    }

    private void initialize() {
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
        //freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        stringTemplateLoader = new StringTemplateLoader();
//        stringLoader.putTemplate("greetTemplate", "<#macro greet>Hello</#macro>");
//        stringLoader.putTemplate("myTemplate", "<#include \"greetTemplate\"><@greet/> World!");
        freemarkerConfig.setTemplateLoader(stringTemplateLoader);

        freemarkerConfig.setDefaultEncoding("UTF-8");
    }

    public static QMLContext createSession() {
        return createSession(DEFAULT_ML_FRAMEWORK);
    }

    public static List<String> listSupportedFrameworks() {
        return Collections.singletonList(DEFAULT_ML_FRAMEWORK);
    }

//    public <T> TF<Constant> constant(T value, Attribute... attributes) {
//        return register(TF.of(Constant.of(value, attributes), this));
//    }

    public <NTType> TF<Variable, NTType> variable(/*Class<NTType> clazz, */InitializingOperation<NTType> initializingOperation, Attribute... attributes) {
        TF<Variable, NTType> result = makeFromTemplate(TF.of(Variable.of(initializingOperation, attributes), this), this);
        variables.add(result);
        return result;
    }

    public <NTType> TF<Constant, NTType> constant(InitializingOperation<NTType> initializingOperation, Attribute... attributes) {
        TF<Constant, NTType> result = makeFromTemplate(TF.of(Constant.of(initializingOperation, attributes), this), this);
        return result;
    }

//    public TF<Variable> variable(Attribute... attributes) {
//        return register(TF.of(Variable.of(attributes), this));
//    }

    @Override
    public void close() {
        autoCloseables.stream()
                .sorted((left, right) -> priority(right) - priority(left))
                .forEach(autoCloseable -> {
                    LOGGER.fine(String.format("Closing %s", autoCloseable));
                    try {
                        autoCloseable.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public Graph buildGraph() {
        if (graphBuilderOpen) {
            byte[] bytes = graphBuilder.build().toByteArray();
            this.graph = new Graph();
            this.graph.importGraphDef(bytes);
            this.registerAutoCloseable(graph);
            this.graphBuilderOpen = false;
        } else {
            throw new IllegalStateException("Graph is already built.");
        }

        return graph;
    }

    public <T extends TFType, NTType> TF<T, NTType> makeFromTemplate(TF<T, NTType> node, QMLContext qmlContext) {
        node.build(qmlContext);
        String templateName = node.getFMTemplateName();
        String template = node.getTemplateText();
        try {
            String templateWithValues = fillInVariables(templateName, template, node);
            parseFromString(templateWithValues, registry, graphBuilder);
            return node;
        } catch (TextFormat.ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    private <T extends TFType, NTType> String fillInVariables(String templateName, String template, TF<T, NTType> node) {
        stringTemplateLoader.putTemplate(templateName, template);
        try {
            Template freemarkerTemplate = freemarkerConfig.getTemplate(templateName);
            NodeFreemarkerVariableReader nodeFreemarkerVariableReader = new NodeFreemarkerVariableReader(node);
            StringWriter writer = new StringWriter();
            freemarkerTemplate.process(nodeFreemarkerVariableReader, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

//    private static final Pattern REGEX_VARIABLE_REPLACEMENTS = Pattern.compile("\\$\\{(\\w.+?)\\}");
//    private <T extends TFType> String fillInVariables(String template, TF<T> node) {
//        StringBuffer result = new StringBuffer();
//        Matcher matcher = REGEX_VARIABLE_REPLACEMENTS.matcher(template);
//        while (matcher.find()) {
//            final String variableName = matcher.group(1);
//            String value = node.getNodeVariable(variableName);
//            if (value == null) {
//                throw new IllegalStateException(String.format("Variable %s not found in class %s", variableName, node.getName()));
//            } else {
//                try {
//                    matcher.appendReplacement(result, Matcher.quoteReplacement(value));
//                } catch (IllegalArgumentException e) {
//                    throw new IllegalStateException(e);
//                }
//            }
//        }
//        matcher.appendTail(result);
//        return result.toString();
//    }
//
    public <T extends AutoCloseable> T registerAutoCloseable(T autoCloseableSomething) {
        this.autoCloseables.add(autoCloseableSomething);
        return autoCloseableSomething;
    }

    public Graph getGraph() {
        if (this.graphBuilderOpen) {
            return this.buildGraph();
        } else {
            return this.graph;
        }
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public NamingService getNamingService() {
        return namingService;
    }

    public <NTType> void run(TF<? extends TFType, NTType> node) {
        Session session = this.getSession();
        session.runner().addTarget(node.getName()).run();
    }

    public <NTType> NTType fetch(TF<? extends TFType, NTType> node) {
        Session session = this.getSession();
        try (Tensor<?> result = session.runner().fetch(node.getName(), 0).run().get(0)) {
            if (result.dataType().equals(DataType.FLOAT)) {
                return (NTType) Float.valueOf(result.floatValue());
            } else if (result.dataType().equals(DataType.DOUBLE)) {
                return (NTType) Double.valueOf(result.doubleValue());
            } else if (result.dataType().equals(DataType.INT32)) {
                return (NTType) Integer.valueOf(result.intValue());
            } else {
                throw new UnsupportedOperationException(String.format("Result type %s is not supported.", result.dataType()));
            }
        }
    }

    public <NTType> VectorWrapper<NTType> fetchVector(TF<? extends TFType, NTType> node) {
        Session session = this.getSession();
        try (Tensor<?> result = session.runner().fetch(node.getName(), 0).run().get(0)) {
            if (result.dataType().equals(DataType.FLOAT)) {
                FloatBuffer floatBuffer = FloatBuffer.allocate(result.numElements());
                result.writeTo(floatBuffer);
                return new VectorWrapper<NTType>(floatBuffer);
            } else if (result.dataType().equals(DataType.DOUBLE)) {
                DoubleBuffer doubleBuffer = DoubleBuffer.allocate(result.numElements());
                result.writeTo(doubleBuffer);
                return new VectorWrapper<NTType>(doubleBuffer);
            } else if (result.dataType().equals(DataType.INT32)) {
                IntBuffer intBuffer = IntBuffer.allocate(result.numElements());
                result.writeTo(intBuffer);
                return new VectorWrapper<NTType>(intBuffer);
            } else {
                throw new UnsupportedOperationException(String.format("Result type %s is not supported.", result.dataType()));
            }
        }
    }

    public Session getSession() {
        if (this.session == null) {
            this.session = this.registerAutoCloseable(new Session(getGraph()));
        }
        return this.session;
    }

    public static InitializingOperation zeros(Object shape) {
        return new InitializingOperation(){
            @Override
            public Shape getShape() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getInitialValue() {
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
            public String getInitialValue() {
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

    public static InitializingOperation<Integer> values(int... values) {
        return new InitializingOperation<Integer>(){
            @Override
            public Shape getShape() {
                return Shape.shape(values.length);
            }

            @Override
            public String getInitialValue() {
                return toStringTensorContent(values);
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

    public static InitializingOperation<Float> values(float... values) {
        return new InitializingOperation<Float>(){
            @Override
            public Shape getShape() {
                return Shape.shape(values.length);
            }

            @Override
            public String getInitialValue() {
                return toStringTensorContent(values);
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
            public String getInitialValue() {
                return toStringTensorContent(values);
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

    private static void parseFromString(CharSequence input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws TextFormat.ParseException {
        //builder.clear();
        TextFormat.merge(input, extensionRegistry, builder);
    }

    public TF<Initializer, ?> globalVariablesInitializer(Attribute... attributes) {
        return makeFromTemplate(TF.of(Initializer.of(attributes), this), this);
    }

    public List<TF<Variable, ?>> getVariables() {
        return variables;
    }

    // public <R extends TFType> TF<Operation.Subtract, NTType> subtract(TF<R, NTType> operand, Attribute... attributes) {
    public <R extends TFType, NTType> TF<Operation.Square, NTType> square(TF<R, NTType> operation, Attribute... attributes) {
        Operation.Square ble = new Operation.Square(operation, attributes);
        TF of = TF.of(new Operation.Square(operation, attributes), this);
        return this.makeFromTemplate(of, this);
    }
}
