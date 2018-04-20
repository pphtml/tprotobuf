package org.superbiz.tf.util;

import org.superbiz.tf.type.DType;
import org.superbiz.tf.type.InitializingOperation;

import static com.google.protobuf.TensorContentEncoder.toStringTensorContent;

public class TFInitialValueConverter {
    public static String getValue(InitializingOperation initializingOperation) {
        if (initializingOperation.isVector()) {
            if (initializingOperation.getDType() == DType.DT_INT32) {
                return toStringTensorContent((int[]) initializingOperation.getInitialValue());
            } else if (initializingOperation.getDType() == DType.DT_INT64) {
                return toStringTensorContent((long[]) initializingOperation.getInitialValue());
            } else if (initializingOperation.getDType() == DType.DT_FLOAT) {
                return toStringTensorContent((float[]) initializingOperation.getInitialValue());
            } else if (initializingOperation.getDType() == DType.DT_DOUBLE) {
                return toStringTensorContent((double[]) initializingOperation.getInitialValue());
            } else {
                throw new IllegalArgumentException(String.format("Unsupported type: \"%s\"", initializingOperation.getDType()));
            }
        } else {
            Object value = initializingOperation.getInitialValue();
            return value.toString();
        }
    }
}
