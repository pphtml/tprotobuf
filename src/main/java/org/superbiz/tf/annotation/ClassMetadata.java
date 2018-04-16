package org.superbiz.tf.annotation;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.util.ClasspathResource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ClassMetadata {
    private Map<String, FieldOrMethod> mappings = new HashMap<>();
    private String namePrefix;
    private String templateText;
    private String outputNodePostfix;

    public Map<String, FieldOrMethod> getMappings() {
        return mappings;
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
        }

        Template templateLocation = aClass.getAnnotation(Template.class);
        if (templateLocation != null) {
            String templateName = "qml/template/" + templateLocation.value();
            ClasspathResource resource = ClasspathResource.of(templateName);
            try {
                result.templateText = Resources.toString(resource.getUrl(), Charsets.UTF_8);
            } catch (IOException e) {
                throw new IllegalArgumentException(String.format("Cannot read template for class %s", aClass.getName()));
            }

        } else if (aClass.getAnnotation(TemplateInline.class) != null) {
            result.templateText = aClass.getAnnotation(TemplateInline.class).value();
        } else if (aClass.getSuperclass().getAnnotation(TemplateInline.class) != null) {
            result.templateText = aClass.getSuperclass().getAnnotation(TemplateInline.class).value();
        } else {
            throw new IllegalArgumentException(String.format("Template or TemplateInline missing for class %s", aClass.getName()));
        }

        return result;
    }

    public String getTemplateText() {
        return this.templateText;
    }

    public String getOutputNodePostfix() {
        return outputNodePostfix;
    }
}
