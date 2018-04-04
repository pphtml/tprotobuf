package org.superbiz.tf;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.*;
import org.superbiz.tf.util.NamingService;
import org.superbiz.util.ClasspathResource;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.framework.GraphDef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.superbiz.tf.util.AutoCloseablePriority.priority;

public class QMLContext implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(QMLContext.class.getName());
    private static final String DEFAULT_ML_FRAMEWORK = "TensorFlow";
    private final List<AutoCloseable> autoCloseables = new ArrayList<>();
    //private final List<TF<? extends TFType>> nodes = new ArrayList<>();
    private Graph graph;
    private NamingService namingService = new NamingService();
    private Session session;
    private GraphDef.Builder graphBuilder = GraphDef.newBuilder();
    private boolean graphBuilderOpen = true;
    private ExtensionRegistry registry = ExtensionRegistry.newInstance();

    public static QMLContext createSession(String mlFramework) {
        if (!DEFAULT_ML_FRAMEWORK.equals(mlFramework)) {
            throw new QMLContextException(String.format("ML Framework %s is not registered/supported. " +
                    "Check outcome priority listSupportedFrameworks().", mlFramework));
        }
        return new QMLContext();
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

    public TF<Variable> variable(InitializingOperation initializingOperation, Attribute... attributes) {
        return makeFromTemplate(TF.of(Variable.of(initializingOperation, attributes), this), this);
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

    public <T extends TFType> TF<T> makeFromTemplate(TF<T> node, QMLContext qmlContext) {
        node.build(qmlContext);
        String template = node.getTemplateText();
        try {
            String templateWithValues = fillInVariables(template, node);
            parseFromString(templateWithValues, registry, graphBuilder);
            return node;
        } catch (TextFormat.ParseException e) {
            throw new IllegalStateException(e);
        }
    }

    private static final Pattern REGEX_VARIABLE_REPLACEMENTS = Pattern.compile("\\$\\{(\\w.+?)\\}");
    private <T extends TFType> String fillInVariables(String template, TF<T> node) {
        StringBuffer result = new StringBuffer();
        Matcher matcher = REGEX_VARIABLE_REPLACEMENTS.matcher(template);
        while (matcher.find()) {
            final String variableName = matcher.group(1);
            String value = node.getNodeVariable(variableName);
            if (value == null) {
                throw new IllegalStateException(String.format("Variable %s not found in class %s", variableName, node.getName()));
            } else {
                try {
                    matcher.appendReplacement(result, Matcher.quoteReplacement(value));
                } catch (IllegalArgumentException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        matcher.appendTail(result);
        return result.toString();
    }


//    public <T extends TFType> TF<T> register(TF<T> node) {
//        //this.nodes.add(node);
//        LOGGER.warning("Missing registration " + node);
//        return node;
//    }

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

    public Float run(TF<? extends TFType> node) {
        Session session = this.getSession();
        try (Tensor<?> result = session.runner().fetch(node.getName(), 0).run().get(0)) {
            return result.floatValue();
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
        };
    }

    public static InitializingOperation value(Integer value) {
        return new InitializingOperation(){
            @Override
            public Shape getShape() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getInitialValue() {
                return value.toString();
            }
        };
    }

    private static void parseFromString(CharSequence input, ExtensionRegistry extensionRegistry, Message.Builder builder) throws TextFormat.ParseException {
        //builder.clear();
        TextFormat.merge(input, extensionRegistry, builder);
    }
}
