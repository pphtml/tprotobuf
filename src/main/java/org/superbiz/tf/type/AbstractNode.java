package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.QMLNodeProcessingException;
import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.AllowedShapeTransformation;
import org.superbiz.tf.annotation.ClassMetadata;
import org.superbiz.tf.annotation.FieldOrMethod;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.shape.ShapeOperation;
import org.superbiz.tf.shape.TransformationFinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractNode implements TFType, NamingSequence {
    String name;
    final Attribute[] attributes;
    private DType dType;
    private Shape shape;
    private static final Map<Class, ClassMetadata> ANNOTATIONS_CACHE = new HashMap<>();
    private ShapeOperation shapeOperation;

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
        return shape != null ? shape : Shape.SCALAR;
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
        // scanClassAnnotations();
        this.name = qmlContext.getNamingService().name(attributes, this);
    }

    private ClassMetadata scanClassAnnotations() {
        ClassMetadata classMetadata = ANNOTATIONS_CACHE.get(this.getClass());
        if (classMetadata == null) {
            synchronized (AbstractNode.class) {
                classMetadata = ANNOTATIONS_CACHE.get(this.getClass());
                if (classMetadata == null) {
                    classMetadata = ClassMetadata.scan(this.getClass());
                    ANNOTATIONS_CACHE.put(this.getClass(), classMetadata);
                }
            }
        }
        return classMetadata;
    }

    @Override
    public String getPrefix() {
        return getClassMetadata().getNamePrefix();
    }

    @Override
    public String getTemplateText() {
        return getClassMetadata().getTemplateText();
    }

    @Override
    public String getOutputNodeName() {
        String outputNodePostfix = getClassMetadata().getOutputNodePostfix();
        if (outputNodePostfix != null) {
            return String.format("%s%s", getName(), outputNodePostfix);
        } else {
            return getName();
        }
    }

    @Override
    public List<TFType> getInputs() {
        Map<String, Field> tfInputs = getClassMetadata().getTfInputs();
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

    protected void postInit() {
        List<TFType> inputs = getInputs();
        checkAndSetShape(inputs);
    }

    private void checkAndSetShape(List<TFType> inputs) {
        if (shape != null && inputs.size() > 0) {
            throw new IllegalStateException("Inputs > 0 & shape already set.");
        }

        final List<AllowedShapeTransformation> allowedShapeTransformations = getClassMetadata().getAllowedShapeTransformations();

        if (inputs.size() > 0) {
            List<Shape> shapes = inputs.stream()
                    .map(op -> op.getShape())
                    .collect(Collectors.toList());
            Optional<ShapeOperation> shapeOperation = TransformationFinder.findMatching(allowedShapeTransformations, shapes);
            if (!shapeOperation.isPresent()) {
                throw new IllegalStateException("Missing shape");
            } else {
                this.shape = shapeOperation.get().getToShape();
                this.shapeOperation = shapeOperation.get();
            }
        }
    }

    protected ClassMetadata getClassMetadata() {
        ClassMetadata result = ANNOTATIONS_CACHE.get(this.getClass());
        if (result == null) {
            result = scanClassAnnotations();
        }
        return result;
    }

    @Override
    public ShapeOperation getShapeOperation() {
        return shapeOperation;
    }

    @Override
    public TF<? extends TFType, ?> createGradientOp(QMLContext qmlContext, TF<? extends TFType, ?> output) {
        return throwUnsupportedGradientException();
    }

    protected TF<? extends TFType, ?> throwUnsupportedGradientException() {
        throw new UnsupportedOperationException(String.format(
                "Computation of gradients for %s operation is not supported.", this.getClass().getName()));
    }
}
