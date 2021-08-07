package ru.pflb.ipr.RandomNumber;

import org.springframework.util.Assert;

import java.util.Random;

public class RandomNumberUtils {

    private static final Random random = new Random();

    private RandomNumberUtils() {

    }

    public static double generateNextDouble(double min, double max, int decimalPlaces) {
        Assert.isTrue(decimalPlaces >= 0 && decimalPlaces <= 16, "[decimalPlaces] should be between 0 and 16 (current=" + decimalPlaces + ")");
        Assert.isTrue(min < max, "[leftBound] should be greater than [rightBound]");
        return Double.parseDouble(String.format("%." + decimalPlaces + "f", min + (max - min) * random.nextDouble()).replace(',', '.'));
    }

    public static int generateNextInt(int min, int max) {
        Assert.isTrue(min < max, "[leftBound] should be greater than [rightBound]");
        return min + random.nextInt(max - min);
    }

    public static long generateNextLong(long min, long max) {
        Assert.isTrue(min < max, "[leftBound] should be greater than [rightBound]");
        return min + random.nextLong() % (max - min);
    }
}
