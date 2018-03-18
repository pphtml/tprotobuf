package org.superbiz.tf.attribute;

public class AttributeName implements Attribute {
    private final String name;

    public AttributeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
