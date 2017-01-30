/*
 * Name : Day11.java
 * Author : Adrian Francisco
 * Created: Jan 24, 2017
 */
package hackerrank;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

/**
 * Day 11.
 *
 * @author Adrian Francisco
 */
public class CDay11 {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of Day11.
     */
    public CDay11() {
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        int arr[][] = new int[6][6];
        try (Scanner in = new Scanner(System.in)) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    arr[i][j] = in.nextInt();
                }
            }
        }

        int max = Integer.MIN_VALUE;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int hourGlass = hourGlass(arr, i, j);
                if (max < hourGlass) {
                    max = hourGlass;
                }
            }
        }

        System.out.println(max);
    }

    /**
     * Calculate the hour glass.
     *
     * @param arr the arr
     * @param row the row
     * @param col the col
     * @return the hour glass
     */
    private static int hourGlass(int arr[][], int row, int col) {
        int result = 0;

        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                if (i == row + 1 && j == col) {
                    continue;
                }
                if (i == row + 1 && j == col + 2) {
                    continue;
                }

                result += arr[i][j];
            }
        }

        return result;
    }

    /*
     **************************************** TEST METHODS **********************************************************
     */

    /**
     * Test main.
     *
     * @throws Exception the exception
     */
    @Test
    public void testMain() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("1 1 1 0 0 0\n");
        input.append("0 1 0 0 0 0\n");
        input.append("1 1 1 0 0 0\n");
        input.append("0 0 2 4 4 0\n");
        input.append("0 0 0 2 0 0\n");
        input.append("0 0 1 2 4 0\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        CDay11.main(null);
        System.out.println();
    }

    /**
     * Test main custom.
     *
     * @throws Exception the exception
     */
    @Test
    public void testMainCustom() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("-1 -1 0 -9 -2 -2\n");
        input.append("-2 -1 -6 -8 -2 -5\n");
        input.append("-1 -1 -1 -2 -3 -4\n");
        input.append("-1 -9 -2 -4 -4 -5\n");
        input.append("-7 -3 -3 -2 -9 -9\n");
        input.append("-1 -3 -1 -2 -4 -5\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        CDay11.main(null);
        System.out.println();
    }
}
