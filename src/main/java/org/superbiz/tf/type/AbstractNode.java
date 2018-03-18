package org.superbiz.tf.type;

import org.superbiz.tf.attribute.Attribute;

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
}
