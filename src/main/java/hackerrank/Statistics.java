/*
 * Name : Statistics.java
 * Author : Adrian Francisco
 * Created: Feb 10, 2017
 */
package hackerrank;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Utility methods for various statistics calculations.
 *
 * @author Adrian Francisco
 */
public class Statistics {

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of Statistics.
     */
    private Statistics() {
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /**
     * Rounds a double based on the given precision.
     *
     * @param d the d
     * @param precision the precision
     * @return the rounded double
     */
    public static double round(double d, int precision) {
        double tens = Math.pow(10.0, precision);
        return Math.round(d * tens) / tens;
    }

    /**
     * Calculate the mean of the array.
     *
     * @param array the array
     * @return the mean
     */
    public static double mean(double[] array) {
        int n = array.length;

        double mean = 0.0;

        for (int i = 0; i < n; i++) {
            mean += array[i];
        }

        mean = mean / n;

        return mean;
    }

    /**
     * Calculate the median of the array.
     *
     * @param array the array
     * @return the median
     */
    public static double median(double[] array) {
        int n = array.length;
        int half = n / 2;
        boolean even = n % 2 == 0;

        Arrays.sort(array);

        double median;

        if (even) {
            median = (array[half - 1] + array[half]) / 2.0;
        }
        else {
            median = array[half];
        }

        return median;
    }

    /**
     * Calculate the mode of the array.
     *
     * @param array the array
     * @return the mode
     */
    public static double mode(double[] array) {
        int n = array.length;

        Map<Double, Integer> occurances = new TreeMap<>();

        int mode = 0;

        for (int i = 0; i < n; i++) {
            double k = array[i];

            if (!occurances.containsKey(k)) {
                occurances.put(k, 0);
            }

            int newMode = occurances.get(k) + 1;

            if (newMode > mode) {
                mode = newMode;
            }

            occurances.put(k, newMode);
        }

        for (Map.Entry<Double, Integer> entry : occurances.entrySet()) {
            if (entry.getValue() == mode) {
                return entry.getKey();
            }
        }

        return Double.NaN;
    }

    /**
     * Calculate the weighted mean of the given array and weight.
     *
     * @param array the array
     * @param weight the weight
     * @return the weighted mean
     */
    public static double weightedMean(double[] array, double[] weight) {
        if (array.length != weight.length) {
            return Double.NaN;
        }

        int n = array.length;

        double top = 0.0;
        double bottom = 0.0;

        for (int i = 0; i < n; i++) {
            top += array[i] * weight[i];
            bottom += weight[i];
        }

        return top / bottom;
    }

    /**
     * Calculate the quartiles of the given array. The returned array will contain 3 doubles: q1, q2, and q3,
     * respectively.
     *
     * @param array the array
     * @return the quartiles
     */
    public static double[] quartiles(double[] array) {
        int n = array.length;
        int half = n / 2;
        boolean even = n % 2 == 0;

        Arrays.sort(array);

        double[] quartiles = new double[] {Double.NaN, Double.NaN, Double.NaN};

        quartiles[1] = median(array);

        if (even) {
            quartiles[0] = median(Arrays.copyOfRange(array, 0, half));
            quartiles[2] = median(Arrays.copyOfRange(array, half, n));
        }
        else {
            quartiles[0] = median(Arrays.copyOfRange(array, 0, half));
            quartiles[2] = median(Arrays.copyOfRange(array, half + 1, n));
        }

        return quartiles;
    }

    /**
     * Calculate the interquartile range of the given array.
     *
     * @param array the array
     * @return the interquartile range
     */
    public static double interquartileRange(double[] array) {
        double[] quartiles = quartiles(array);
        return quartiles[2] - quartiles[0];
    }

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
