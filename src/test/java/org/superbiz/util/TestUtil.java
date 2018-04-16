package org.superbiz.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestUtil {
    public static List<Float> roundListOfFloats(List<Float> list, int precision) {
        return list.stream().map(number -> {
            BigDecimal bd = new BigDecimal(Double.toString(number));
            bd = bd.setScale(precision, RoundingMode.HALF_UP);
            return bd.floatValue();
        }).collect(Collectors.toList());
    }

    public static List<Double> roundListOfDoubles(List<Double> list, int precision) {
        return list.stream().map(number -> {
            BigDecimal bd = new BigDecimal(Double.toString(number));
            bd = bd.setScale(precision, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }).collect(Collectors.toList());
    }
}
