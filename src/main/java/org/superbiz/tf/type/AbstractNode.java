package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.QMLNodeProcessingException;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.ClassMetadata;
import org.superbiz.tf.annotation.FieldOrMethod;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.attribute.Attribute;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractNode implements TFType, NamingSequence {
    String name;
    final Attribute[] attributes;
    private DType dType;
    private Shape shape;
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

    @Mapping("dType")
    public String getDTypeString() {
        if (this.dType == null) {
            throw new IllegalStateException(String.format("%s named %s doesn't have dType.", this.getClass().getName(), this.getName()));
        }
        return this.dType.name();
    }

    @Mapping("dTypeArgumentName") // TODO spatny nazev
    public String getDTypeArgumentNameString() {
        if (shape != null) {
            return "tensor_content";
        } else {
            return this.dType.getArgumentName();
        }
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Mapping("tensorShape")
    public String getTensorShapeString() {
        return Shape.toProtobufString(this.shape);
    }

    public void setDType(DType dType) {
        this.dType = dType;
    }

    @Override
    public DType getDType() {
        return dType;
    }

    @Override
    public Object getVariable(String variableName) {
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
            } else {
                return variable;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new QMLNodeProcessingException(e.getMessage(), e);
        }
    }

    public void build(QMLContext qmlContext) {
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

    @Override
    public List<TFType> getInputs() {
        Map<String, Field> tfInputs = ANNOTATIONS_CACHE.get(this.getClass()).getTfInputs();
        List<TFType> result = tfInputs.values().stream()
                .map(field -> {
                    try {
                        TF<? extends TFType, ?> tf = (TF<? extends TFType, ?>)field.get(this);
                        return tf.getNode();
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("{");
        sb.append("name='").append(name).append('\'');
        sb.append(", attributes=").append(Arrays.toString(attributes));
        sb.append(", dType=").append(dType);
        sb.append(", shape=").append(shape);
        sb.append('}');
        return sb.toString();
    }
}
