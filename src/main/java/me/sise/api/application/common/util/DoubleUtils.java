package me.sise.api.application.common.util;

public class DoubleUtils {
    private DoubleUtils() {
        throw new UnsupportedOperationException();
    }

    public static double round(double x, int decimalPoint) {
        double y = Math.pow(10, decimalPoint);
        return Math.round(x * y) / y;
    }
}
