package org.superbiz.tf.annotation;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.util.ClasspathResource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassMetadata {
    private static final String TEMPLATE_FOLDER = "qml/template/";
    private Map<String, FieldOrMethod> mappings = new HashMap<>();
    private Map<String, Field> tfInputs = new LinkedHashMap<>();
    private String namePrefix;
    private String templateText;
    private String outputNodePostfix;
    private List<AllowedShapeTransformation> allowedShapeTransformations = new ArrayList<>();
    private String templateGradient;

    public Map<String, FieldOrMethod> getMappings() {
        return mappings;
    }

    public Map<String, Field> getTfInputs() {
        return tfInputs;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public static ClassMetadata scan(Class<? extends AbstractNode> aClass) {
        ClassMetadata result = new ClassMetadata();

        NamePrefix namePrefixAnnotation = aClass.getAnnotation(NamePrefix.class);
        if (namePrefixAnnotation != null) {
            result.namePrefix = namePrefixAnnotation.value();
        } else if (aClass.getSuperclass().getAnnotation(NamePrefix.class) != null) {
            result.namePrefix = aClass.getSuperclass().getAnnotation(NamePrefix.class).value();
        } else {
            result.namePrefix = String.format("prefix_%s", aClass.getName());
        }

        OutputNodePostfix outputNodePostfixAnnotation = aClass.getAnnotation(OutputNodePostfix.class);
        if (outputNodePostfixAnnotation != null) {
            result.outputNodePostfix = outputNodePostfixAnnotation.value();
        }

        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            Mapping mapping = method.getAnnotation(Mapping.class);
            if (mapping != null) {
                result.mappings.put(mapping.value(), FieldOrMethod.method(method));
            }
        }

        Field[] fields = aClass.getFields();
        for (Field field : fields) {
            Mapping mapping = field.getAnnotation(Mapping.class);
            if (mapping != null) {
                result.mappings.put(mapping.value(), FieldOrMethod.field(field));
            }

            TFInput tfInput = field.getAnnotation(TFInput.class);
            if (tfInput != null) {
                result.tfInputs.put(field.getName(), field);
            }
        }

        GradientTemplate gradientTemplateLocation = aClass.getAnnotation(GradientTemplate.class);
        if (gradientTemplateLocation != null) {
            result.templateGradient = readTemplateFromClasspath(gradientTemplateLocation.value(), aClass);
        }

        Template templateLocation = aClass.getAnnotation(Template.class);
        if (templateLocation != null) {
            result.templateText = readTemplateFromClasspath(templateLocation.value(), aClass);
        } else if (aClass.getAnnotation(TemplateInline.class) != null) {
            result.templateText = aClass.getAnnotation(TemplateInline.class).value();
        } else if (aClass.getSuperclass().getAnnotation(TemplateInline.class) != null) {
            result.templateText = aClass.getSuperclass().getAnnotation(TemplateInline.class).value();
        } else {
            throw new IllegalArgumentException(String.format("Template or TemplateInline missing for class %s", aClass.getName()));
        }

//        ShapeTransformations transformationsSpecialized = aClass.getAnnotation(ShapeTransformations.class);
//        ShapeTransformations transformationsBase = aClass.getSuperclass().getAnnotation(ShapeTransformations.class);
//        ShapeTransformation transformationSpecialized = aClass.getAnnotation(ShapeTransformation.class);
//        ShapeTransformation transformationBase = aClass.getAnnotatedSuperclass().getAnnotation(ShapeTransformation.class);
//
        if (aClass.getAnnotation(ShapeTransformations.class) != null) {
            List<AllowedShapeTransformation> transformations = Stream.of(aClass.getAnnotation(ShapeTransformations.class).value())
                    .map(shapeTransformation -> AllowedShapeTransformation.parse(shapeTransformation.value()))
                    .collect(Collectors.toList());
            result.allowedShapeTransformations.addAll(transformations);
        }
        if (aClass.getAnnotation(ShapeTransformation.class) != null) {
            result.allowedShapeTransformations.add(AllowedShapeTransformation.parse(
                    aClass.getAnnotation(ShapeTransformation.class).value()));
        }

        return result;
    }

    private static String readTemplateFromClasspath(String location, Class<?> aClass) {
        String templateName = TEMPLATE_FOLDER + location;
        ClasspathResource resource = ClasspathResource.of(templateName);
        try {
            return Resources.toString(resource.getUrl(), Charsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("Cannot read template for class %s", aClass.getName()));
        }
    }

    public String getTemplateText() {
        return this.templateText;
    }

    public String getOutputNodePostfix() {
        return outputNodePostfix;
    }

    public List<AllowedShapeTransformation> getAllowedShapeTransformations() {
        return allowedShapeTransformations;
    }

}
