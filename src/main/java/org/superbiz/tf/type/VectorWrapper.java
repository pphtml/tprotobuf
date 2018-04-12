package org.superbiz.tf.type;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayList;
import java.util.List;

public class VectorWrapper<T> {

    private DoubleBuffer doubleBuffer;
    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;
    private LongBuffer longBuffer;

    public VectorWrapper(FloatBuffer floatBuffer) {
        this.floatBuffer = floatBuffer;
    }

    public VectorWrapper(IntBuffer intBuffer) {
        this.intBuffer = intBuffer;
    }

    public VectorWrapper(DoubleBuffer doubleBuffer) {
        this.doubleBuffer = doubleBuffer;
    }

    public VectorWrapper(LongBuffer longBuffer) {
        this.longBuffer = longBuffer;
    }

    public List<T> getList1D() {
        final int length = this.floatBuffer != null ? this.floatBuffer.capacity() :
                this.intBuffer != null ? this.intBuffer.capacity() :
                this.doubleBuffer != null ? this.doubleBuffer.capacity() :
                this.longBuffer != null ? this.longBuffer.capacity() :
                -1;
        List<T> result = new ArrayList<T>(length);
        for (int index = 0; index < length; index++) {
            if (this.floatBuffer != null) {
                Float floatValue = this.floatBuffer.get(index);
                T value = (T) floatValue;
                result.add(value);
            } else if (this.intBuffer != null) {
                Integer integer = this.intBuffer.get(index);
                T value = (T) integer;
                result.add(value);
            } else if (this.doubleBuffer != null) {
                Double doubleValue = this.doubleBuffer.get(index);
                T value = (T) doubleValue;
                result.add(value);
            } else if (this.longBuffer != null) {
                Long longValue = this.longBuffer.get(index);
                T value = (T) longValue;
                result.add(value);
            } else {
                throw new UnsupportedOperationException();
            }
        }
        return result;
    }
}
