package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.QMLNodeProcessingException;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.ClassMetadata;
import org.superbiz.tf.annotation.FieldOrMethod;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.attribute.Attribute;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNode implements TFType, NamingSequence {
    final Attribute[] attributes;
    String name;
    private static final Map<Class, ClassMetadata> ANNOTATIONS_CACHE = new HashMap<>();

    public AbstractNode(Attribute[] attributes) {
        this.attributes = attributes;
    }
    //private final QMLContext graph;

//    public AbstractNode(QMLContext graph) {
//        this.graph = graph;
//    }

    @Override
    @Mapping("nodeName")
    public String getName() {
        return name;
    }

    @Override
    public String getVariable(String variableName) {
        Map<String, FieldOrMethod> mappings = ANNOTATIONS_CACHE.get(this.getClass()).getMappings();

        FieldOrMethod fieldOrMethod = mappings.get(variableName);
        if (fieldOrMethod == null) {
            throw new IllegalArgumentException(String.format("Variable %s doesn't have mapping on class %s",
                    variableName, this.getClass().getName()));
        }

        try {
            final Object variable;
            if (fieldOrMethod.isMethod()) {
                variable = fieldOrMethod.getMethod().invoke(this, new Object[]{});
            } else if (fieldOrMethod.isField()) {
                variable = fieldOrMethod.getField().get(this);
            } else {
                throw new IllegalStateException("Either Method or Field is supposed to be provided.");
            }

            if (variable instanceof TF) {
                TF tf = (TF) variable;
                return tf.getOutputNodeName();
            } else if (variable instanceof String) {
                String resultingString = (String) variable;
                return resultingString;
            } else {
                throw new QMLNodeProcessingException(String.format("Cannot get variable value from type %s", variable.getClass()));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new QMLNodeProcessingException(e);
        }
    }

    protected void commonBuild(QMLContext qmlContext) {
        scanClassAnnotations();
        this.name = qmlContext.getNamingService().name(attributes, this);
    }

    private void scanClassAnnotations() {
        if (!ANNOTATIONS_CACHE.containsKey(this.getClass())) {
            synchronized (AbstractNode.class) {
                if (!ANNOTATIONS_CACHE.containsKey(this.getClass())) {
                    ClassMetadata classMetadata = ClassMetadata.scan(this.getClass());
                    ANNOTATIONS_CACHE.put(this.getClass(), classMetadata);
                }
            }
        }
    }

    @Override
    public String getPrefix() {
        return ANNOTATIONS_CACHE.get(this.getClass()).getNamePrefix();
    }

    @Override
    public String getTemplateText() {
        return ANNOTATIONS_CACHE.get(this.getClass()).getTemplateText();
    }

    @Override
    public String getOutputNodeName() {
        String outputNodePostfix = ANNOTATIONS_CACHE.get(this.getClass()).getOutputNodePostfix();
        if (outputNodePostfix != null) {
            return String.format("%s%s", getName(), outputNodePostfix);
        } else {
            return getName();
        }
    }
}
