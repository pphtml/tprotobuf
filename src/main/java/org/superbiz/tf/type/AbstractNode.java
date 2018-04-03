package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.attribute.Attribute;

import java.lang.reflect.Method;

public abstract class AbstractNode implements TFType {
    final Attribute[] attributes;
    String name;

    public AbstractNode(Attribute[] attributes) {
        this.attributes = attributes;
    }
    //private final QMLContext graph;

//    public AbstractNode(QMLContext graph) {
//        this.graph = graph;
//    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVariable(String variableName) {
        if (variableName.equals("nodeName")) {
            return getName();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    protected void commonBuild(QMLContext qmlContext) {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            Mapping mapping = method.getAnnotation(Mapping.class);
            if (mapping != null) {
                throw new UnsupportedOperationException();
            }
        }
    }
}
