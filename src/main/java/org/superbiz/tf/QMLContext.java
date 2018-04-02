package org.superbiz.tf;

import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.*;
import org.superbiz.tf.util.NamingService;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.superbiz.tf.util.AutoCloseablePriority.priority;

public class QMLContext implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(QMLContext.class.getName());
    private static final String DEFAULT_ML_FRAMEWORK = "TensorFlow";
    private final List<AutoCloseable> autoCloseables = new ArrayList<>();
    private final List<TF<? extends TFType>> nodes = new ArrayList<>();
    private Graph graph;
    private NamingService namingService = new NamingService();
    private Session session;

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

    public <T> TF<Constant> constant(T value, Attribute... attributes) {
        return register(TF.of(Constant.of(value, attributes), this));
    }

    public TF<Variable> variable(InitializingOperation initializingOperation, Attribute... attributes) {
        return register(TF.of(Variable.of(initializingOperation, attributes), this));
    }

    public TF<Variable> variable(Attribute... attributes) {
        return register(TF.of(Variable.of(attributes), this));
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

    public Graph buildGraph() {
        Graph graph = new Graph();
        this.setGraph(graph);
        this.registerAutoCloseable(graph);

        for (TF<? extends TFType> node : this.nodes) {
            node.build(this);
        }

        return graph;
    }

    public <T extends TFType> TF<T> register(TF<T> node) {
        this.nodes.add(node);
        return node;
    }

    public <T extends AutoCloseable> T registerAutoCloseable(T autoCloseableSomething) {
        this.autoCloseables.add(autoCloseableSomething);
        return autoCloseableSomething;
    }

    public Graph getGraph() {
        return graph;
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
        };
    }

    public static InitializingOperation value(Integer value) {
        return new InitializingOperation(){
            @Override
            public Shape getShape() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
