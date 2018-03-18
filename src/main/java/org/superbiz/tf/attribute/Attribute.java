package org.superbiz.tf.attribute;

public interface Attribute {
    static Attribute named(String name) {
        return new AttributeName(name);
    }

    static Attribute generic(String key, String value) {
        return new AttributeGeneric(key, value);
    }
}
