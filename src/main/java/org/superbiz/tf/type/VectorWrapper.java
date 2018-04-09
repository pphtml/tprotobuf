package org.superbiz.tf.type;

import org.tensorflow.Tensor;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class VectorWrapper<T> {

    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;

    public VectorWrapper(FloatBuffer floatBuffer) {
        this.floatBuffer = floatBuffer;
    }

    public VectorWrapper(IntBuffer intBuffer) {
        this.intBuffer = intBuffer;
    }

    public List<T> getList1D() {
        final int length = this.intBuffer.capacity();
        List<T> result = new ArrayList<T>(length);
        for (int index = 0; index < length; index++) {
            Integer integer = this.intBuffer.get(index);
            T value = (T) integer;
            result.add(value);
        }
        return result;
    }
}
