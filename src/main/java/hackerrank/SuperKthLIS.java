/*
 * Name : SuperKthLIS.java
 * Author : Adrian Francisco
 * Created: Jan 19, 2017
 */
package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.Test;

/**
 * Super Kth LIS: https://www.hackerrank.com/challenges/super-kth-lis
 *
 * @author Adrian Francisco
 */
public class SuperKthLIS {

    /** The logger. */
    private static final Logger LOG = Logger.getLogger(SuperKthLIS.class.getName());

    /** The result. */
    private static String result;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%6$s%n");
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String... args) {
        Level level = Level.OFF;

        if (args.length > 0) {
            level = Level.FINE;
        }

        ConsoleHandler handler = new ConsoleHandler();

        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        handler.setLevel(level);

        LOG.setLevel(level);
        if (LOG.getHandlers().length == 0) {
            LOG.addHandler(handler);
        }

        Scanner scanner = new Scanner(System.in, "UTF-8");

        int N = scanner.nextInt();
        int K = scanner.nextInt();

        List<Integer> NS = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            NS.add(scanner.nextInt());
        }

        scanner.close();

        LOG.fine("N: " + N);
        LOG.fine("K: " + K);
        LOG.fine("NS: " + NS);

        List<List<Integer>> sequences = new ArrayList<>();
        int maxSequence = 0;

        for (int i = 0; i < N; i++) {
            Integer currentN = NS.get(i);
            List<List<Integer>> modifiedSequences = new ArrayList<>();

            for (List<Integer> sequence : sequences) {
                // add to previous sequences if incrementing
                if (currentN > last(sequence)) {

                    // copy the current sequence first before adding to it
                    // this is to allow additional numbers in the sequence later
                    List<Integer> copy = new ArrayList<>(sequence);
                    modifiedSequences.add(copy);

                    // add to the current sequence
                    sequence.add(currentN);
                    if (sequence.size() > maxSequence) {
                        maxSequence = sequence.size();
                    }

                    // delete the previous sequence
                    // TODO
                }
            }

            if (modifiedSequences.size() > 0) {
                // add all modified sequences
                sequences.addAll(modifiedSequences);
            }
            else {
                // add current number as a new sequence
                List<Integer> sequence = new ArrayList<>();
                sequence.add(currentN);
                sequences.add(sequence);
            }
        }

        LOG.fine("generated sequences: " + sequences.size());
        List<String> sortedMaxSequences = new ArrayList<>();
        for (List<Integer> sequence : sequences) {
            LOG.finer(sequence.toString());
            if (sequence.size() == maxSequence) {
                sortedMaxSequences.add(print(sequence));
            }
        }
        Collections.sort(sortedMaxSequences);

        LOG.fine("sorted and max sequences:");
        for (String sortedMaxSequence : sortedMaxSequences) {
            LOG.fine(sortedMaxSequence);
        }

        if (0 < K && K < sortedMaxSequences.size() + 1) {
            result = sortedMaxSequences.get(K - 1);
        }
        else {
            result = "-1";
        }

        LOG.fine("result:");
        System.out.println(result);
    }

    /**
     * Generate.
     *
     * @param n the n
     * @return the list
     */
    private static List<Integer> generate(int n) {
        List<Integer> result = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            result.add(random.nextInt(n));
        }

        return result;
    }

    /**
     * Prints.
     *
     * @param list the list
     * @return the string
     */
    private static String print(List<Integer> list) {
        StringBuffer sb = new StringBuffer();
        for (Integer i : list) {
            sb.append(i).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Last.
     *
     * @param list the list
     * @return the int
     */
    private static int last(List<Integer> list) {
        return list.get(list.size() - 1);
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

        SuperKthLIS.main("log");

        assertEquals("1 3 5", SuperKthLIS.result);
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

        SuperKthLIS.main("log");

        assertEquals("1 3 4 5", SuperKthLIS.result);
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

        SuperKthLIS.main("log");

        assertEquals("0 2 6 9 11 15", SuperKthLIS.result);
    }
}
