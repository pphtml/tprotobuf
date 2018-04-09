package com.google.protobuf;

import com.google.common.primitives.Ints;
import org.tensorflow.Tensor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TensorContentEncoder {
//    public static void main(String[] args) {
////        long[] src = {1, 2, 3, 4, 5, 6};
////        LongBuffer lb = LongBuffer.wrap(src, 0, src.length);
////        //lb.put(src);
////        Tensor<?> tLong = Tensor.create(new long[]{6}, lb);
////        System.out.println(tLong.numDimensions());
//
//        int[] src = {1, 2, 3};
//        IntBuffer intBuffer = IntBuffer.wrap(src, 0, src.length);
//        Tensor<?> tLong = Tensor.create(new long[]{3}, intBuffer);
//
//        try {
//            Method method = tLong.getClass().getDeclaredMethod("buffer");
//            method.setAccessible(true);
//            ByteBuffer byteBuffer = (ByteBuffer) method.invoke(tLong, new Object[]{});
//
//            ByteString byteString = ByteString.copyFrom(byteBuffer);
//
//            String bs = TextFormatEscaper.escapeBytes(byteString);
//            System.out.println(bs);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static String toStringTensorContent(int ...values) {
        //Arrays.stre
//        int[] src = new int[values.length];
//        for (int index = 0; index < values.length; index++) {
//            src[index] = values[index];
//        }

        // IntStream.of(unboxed).boxed().toArray();

        IntBuffer intBuffer = IntBuffer.wrap(values);
        Tensor<?> tLong = Tensor.create(new long[]{values.length}, intBuffer);

        try {
            Method method = tLong.getClass().getDeclaredMethod("buffer");
            method.setAccessible(true);
            ByteBuffer byteBuffer = (ByteBuffer) method.invoke(tLong, new Object[]{});

            ByteString byteString = ByteString.copyFrom(byteBuffer);

            String result = TextFormatEscaper.escapeBytes(byteString);
            return String.format("\"%s\"", result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
