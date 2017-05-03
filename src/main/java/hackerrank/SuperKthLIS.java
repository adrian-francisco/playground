/*
 * Name : SuperKthLIS.java
 * Author : Adrian Francisco
 * Created: Jan 19, 2017
 */
package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Super Kth LIS: https://www.hackerrank.com/challenges/super-kth-lis
 *
 * @author Adrian Francisco
 */
public class SuperKthLIS {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");

        int n = scanner.nextInt();
        int k = scanner.nextInt();

        // the array to calculate lis
        int[] array = new int[n];

        // array of max lis at the given index
        int[] lis = new int[n];

        // array of max found sequences at the given index
        // format: 1 2 3 4, 5 6 7 8
        // TODO: change this to an array of array of ints, the string manipulation is expensive
        String[] seqs = new String[n];

        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
            lis[i] = 1;
            seqs[i] = Integer.toString(array[i]);
        }

        scanner.close();

        // the currently known max lis
        int max = Integer.MIN_VALUE;

        // do it
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (array[i] > array[j]) {

                    if (lis[i] < lis[j] + 1) {
                        lis[i] = lis[j] + 1;

                        StringBuilder sb = new StringBuilder();
                        for (String seq : seqs[j].split(",")) {
                            sb.append(seq).append(" ").append(array[i]).append(",");
                        }

                        sb.deleteCharAt(sb.length() - 1);
                        seqs[i] = sb.toString();

                        if (max < lis[i]) {
                            max = lis[i];
                        }
                    }
                    else if (lis[i] == lis[j] + 1) {
                        StringBuilder sb = new StringBuilder();
                        for (String seq : seqs[j].split(",")) {
                            sb.append(seq).append(" ").append(array[i]).append(",");
                        }
                        sb.append(seqs[i]);
                        seqs[i] = sb.toString();
                    }
                }
            }
        }

        // extract the max sequences and sort
        List<String> sortedMaxSeqs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (lis[i] == max) {
                sortedMaxSeqs.addAll(Arrays.asList(seqs[i].split(",")));
            }
        }

        Collections.sort(sortedMaxSeqs);

        // print the kth max lis
        if (sortedMaxSeqs.size() < k) {
            System.out.println("-1");
        }
        else {
            System.out.println(sortedMaxSeqs.get(k - 1));
        }
    }

    /*
     **************************************** TEST METHODS **********************************************************
     */

    /**
     * Test input 0.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInput0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("5 3\n");
        input.append("1 3 1 2 5\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("1 3 5", baos.toString().trim());
    }

    /**
     * Test input 1.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInput1() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("5 2\n");
        input.append("1 3 2 4 5\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("1 3 4 5", baos.toString().trim());
    }

    /**
     * Test input 2.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInput2() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("16 1\n");
        input.append("0 8 4 12 2 10 6 14 1 9 5 13 3 11 7 15\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("0 2 6 9 11 15", baos.toString().trim());
    }

    /**
     * Test 3.
     *
     * @throws Exception the exception
     */
    @Ignore
    @Test
    public void testInput3() throws Exception {
        int n = 1000;
        StringBuilder input = new StringBuilder();
        input.append(n + " 1\n");

        for (int i = 0; i < n; i++) {
            input.append(Math.round(Math.random() * n));
            input.append(" ");
        }

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        main();
    }
}
