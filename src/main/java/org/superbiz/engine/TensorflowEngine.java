package org.superbiz.engine;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.superbiz.tf.QMLContext;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.EngineImplementation;
import org.superbiz.tf.type.TFType;
import org.superbiz.tf.type.VectorWrapper;
import org.superbiz.tf.util.NodeFreemarkerVariableReader;
import org.tensorflow.*;
import org.tensorflow.framework.GraphDef;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.superbiz.tf.util.AutoCloseablePriority.priority;

@EngineImplementation(TensorflowEngine.NAME)
public class TensorflowEngine extends BaseEngine{
    public static final String NAME = "TFEngine";

    private Graph graph;
    private Session session;
    private GraphDef.Builder graphBuilder = GraphDef.newBuilder();
    private ExtensionRegistry registry = ExtensionRegistry.newInstance();
    private Configuration freemarkerConfig;
    private StringTemplateLoader stringTemplateLoader;
    private final List<AutoCloseable> autoCloseables = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(TensorflowEngine.class.getName());
    private boolean tfGraphOpen = true;

    public TensorflowEngine() {
        this.initialize(); // mozna dat nekam jinam
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

    // TODO vyhodit qmlContext
    public <T extends TFType, NTType> TF<T, NTType> makeFromTemplate(TF<T, NTType> node /*, QMLContext qmlContext*/) {
        //node.build(qmlContext);
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

    private static void parseFromString(CharSequence input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws TextFormat.ParseException {
        //builder.clear();
        TextFormat.merge(input, extensionRegistry, builder);
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

    public Session getSession() {
        if (this.session == null) {
            this.session = this.registerAutoCloseable(new Session(getGraph()));
        }
        return this.session;
    }

    public Graph buildGraph() {
        if (tfGraphOpen) {
            for (TF<?, ?> node : this.getNodes()) {
                makeFromTemplate(node);
            }
            System.out.println(graphBuilder); // graph out

            byte[] bytes = graphBuilder.build().toByteArray();
            this.graph = new Graph();
            this.graph.importGraphDef(bytes);
            this.registerAutoCloseable(graph);
            this.tfGraphOpen = false;
        } else {
            throw new IllegalStateException("Graph is already built.");
        }

        return graph;
    }

    public Graph getGraph() {
        if (this.tfGraphOpen) {
            return this.buildGraph();
        } else {
            return this.graph;
        }
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public <T extends TFType, NTType> void addToGraph(TF<T, NTType> node, QMLContext qmlContext) {
        super.addToGraph(node, qmlContext);
        //makeFromTemplate(node);
    }

    @Override
    public <NTType> void run(TF<? extends TFType, NTType> node) {
        Session session = this.getSession();
        session.runner().addTarget(node.getName()).run();
    }

    @Override
    public <NTType> NTType fetch(String nodeName, Map<String, Object> feedDict) { // TODO doimlementovat
        Session session = this.getSession();
        try (Tensor<?> result = session.runner().fetch(nodeName, 0).run().get(0)) {
            if (result.dataType().equals(DataType.FLOAT)) {
                return (NTType) Float.valueOf(result.floatValue());
            } else if (result.dataType().equals(DataType.DOUBLE)) {
                return (NTType) Double.valueOf(result.doubleValue());
            } else if (result.dataType().equals(DataType.INT32)) {
                return (NTType) Integer.valueOf(result.intValue());
            } else if (result.dataType().equals(DataType.INT64)) {
                return (NTType) Long.valueOf(result.longValue());
            } else {
                throw new UnsupportedOperationException(String.format("Result type %s is not supported.", result.dataType()));
            }
        }
    }

    @Override
    public <NTType> VectorWrapper<NTType> fetchVector(String nodeName, Map<String, Object> feedDict) {
        Session session = this.getSession();
        Map<String, Tensor> feedData = Collections.emptyMap();

        if (feedDict != null) {
            feedData = feedDict.entrySet().stream()
                    .map(entry -> TensorPair.of(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toMap(TensorPair::getKey, TensorPair::getTensor));
        }
        final Session.Runner runner = session.runner();
        feedData.entrySet().forEach(tensorEntry -> {
            runner.feed(tensorEntry.getKey(), tensorEntry.getValue());
        });
        try (Tensor<?> result = runner.fetch(nodeName, 0).run().get(0)) {
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
            } else if (result.dataType().equals(DataType.INT64)) {
                LongBuffer longBuffer = LongBuffer.allocate(result.numElements());
                result.writeTo(longBuffer);
                return new VectorWrapper<NTType>(longBuffer);
            } else {
                throw new UnsupportedOperationException(String.format("Result type %s is not supported.", result.dataType()));
            }
        } finally {
            feedData.values().forEach(tensor -> {
                try {
                    tensor.close();
                } catch (Exception throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }

    @Override
    public void inspectAllNodes() {
        Iterator<Operation> operations = this.graph.operations();
        StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(operations, Spliterator.ORDERED), false)
                .forEach(operation -> {
                    if (!operation.name().equals("init_0")) {
                        try (Tensor<?> tensor = session.runner().fetch(operation.name(), 0).run().get(0)) {
                            System.out.println(String.format("%s - %s", operation, tensor));
                        }
                    }
                });
    }

    public <T extends AutoCloseable> T registerAutoCloseable(T autoCloseableSomething) {
        this.autoCloseables.add(autoCloseableSomething);
        return autoCloseableSomething;
    }

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

    private static class TensorPair implements AutoCloseable {
        private final String key;
        private final Tensor tensor;

        public TensorPair(String key, Tensor tensor) {
            this.key = key;
            this.tensor = tensor;
        }

        public static TensorPair of(String key, Object value) {
            Tensor tensor = Tensor.create(value);
            return new TensorPair(key, tensor);
        }

        @Override
        public void close() throws Exception {
            this.tensor.close();
        }

        public String getKey() {
            return key;
        }

        public Tensor getTensor() {
            return tensor;
        }
    }
}
