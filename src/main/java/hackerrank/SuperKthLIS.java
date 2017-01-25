/*
 * Name : SuperKthLIS.java
 * Author : Adrian Francisco
 * Created: Jan 19, 2017
 */
package hackerrank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Super Kth LIS: https://www.hackerrank.com/challenges/super-kth-lis
 *
 * @author Adrian Francisco
 */
public class SuperKthLIS {

    /**
     * Level.ALL during debugging, Level.OFF during submission.
     */
    private static final Level LOG_LEVEL = Level.OFF;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s%6$s%n");
        Logger log = Logger.getLogger(SuperKthLIS.class.getName());
        log.setLevel(LOG_LEVEL);
        ConsoleHandler handler = new ConsoleHandler();
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        handler.setLevel(LOG_LEVEL);
        log.addHandler(handler);

        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int K = scanner.nextInt();

        List<Integer> NS = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            NS.add(scanner.nextInt());
        }

        scanner.close();

        // int N = 10;
        // int K = 3;
        // // List<Integer> NS = Arrays.asList(0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15);
        // List<Integer> NS = generate(100);
        // N = NS.size();

        log.fine("N: " + N);
        log.fine("K: " + K);
        log.fine("NS: " + NS);

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

        log.fine("generated sequences: " + sequences.size());
        List<String> sortedMaxSequences = new ArrayList<>();
        for (List<Integer> sequence : sequences) {
            log.finer(sequence.toString());
            if (sequence.size() == maxSequence) {
                sortedMaxSequences.add(print(sequence));
            }
        }
        Collections.sort(sortedMaxSequences);

        log.fine("sorted and max sequences");
        for (String sortedMaxSequence : sortedMaxSequences) {
            log.fine(sortedMaxSequence);
        }

        log.fine("result");
        if (0 < K && K < sortedMaxSequences.size() + 1) {
            System.out.println(sortedMaxSequences.get(K - 1));
        }
        else {
            System.out.println("-1");
        }
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

}
