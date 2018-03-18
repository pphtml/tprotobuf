package org.superbiz.tf.util;

import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.attribute.AttributeName;
import org.superbiz.tf.type.NamingSequence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class NamingService {
    private final Map<String, AtomicInteger> sequences = new HashMap<>();

    public String name(Attribute[] attributes, NamingSequence namingSequence) {
        for (Attribute attribute : attributes) {
            if (attribute instanceof AttributeName) {
                return ((AttributeName)attribute).getName();
            }
        }

        String prefix = namingSequence.getPrefix();
        Integer id = this.getUniqueId(prefix);
        return String.format("%s_%d", prefix, id);
    }

    private Integer getUniqueId(String prefix) {
        AtomicInteger sequence = this.sequences.get(prefix);
        if (sequence == null) {
            sequence = new AtomicInteger();
            this.sequences.put(prefix, sequence);
        }

        return sequence.getAndIncrement();
    }
}
