package org.superbiz.builder;

public class AttributeGeneric implements Attribute {
    private final String key;
    private final Object value;

    public AttributeGeneric(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
