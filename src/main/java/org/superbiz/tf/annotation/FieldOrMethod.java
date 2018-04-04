package org.superbiz.tf.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldOrMethod {
    private final Method method;
    private final Field field;

    private FieldOrMethod(Method method, Field field) {
        this.method = method;
        this.field = field;
    }

    public static FieldOrMethod field(Field field) {
        return new FieldOrMethod(null, field);
    }
    public static FieldOrMethod method(Method method) {
        return new FieldOrMethod(method, null);
    }

    public Method getMethod() {
        return method;
    }

    public Field getField() {
        return field;
    }

    public boolean isMethod() {
        return method != null;
    }

    public boolean isField() {
        return field != null;
    }
}
