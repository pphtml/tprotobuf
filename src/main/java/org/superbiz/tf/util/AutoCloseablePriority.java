package org.superbiz.tf.util;

import org.tensorflow.Graph;
import org.tensorflow.Session;

public class AutoCloseablePriority {
    public static int priority(AutoCloseable autoCloseable) {
        if (autoCloseable instanceof Graph) {
            return 10;
        } else if (autoCloseable instanceof Session) {
            return 20;
        } else {
            return 100;
        }
    }
}
