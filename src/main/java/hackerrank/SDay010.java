/*
 * Name : SDay010.java
 * Author : Adrian Francisco
 * Created: Feb 1, 2017
 */
package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

/**
 * The Class SDay010.
 */
public class SDay010 {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String... args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        ArrayList<Integer> x = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            x.add(scanner.nextInt());
        }

        scanner.close();

        Collections.sort(x);
        boolean even = n % 2 == 0;

        int q1 = 0;
        int q2 = 0;
        int q3 = 0;

        q2 = median(x);

        if (even) {
            q1 = median(x.subList(0, n / 2));
            q3 = median(x.subList(n / 2, x.size()));
        }
        else {
            q1 = median(x.subList(0, n / 2));
            q3 = median(x.subList(n / 2 + 1, x.size()));
        }

        System.out.println(q1);
        System.out.println(q2);
        System.out.println(q3);
    }

    /**
     * Median.
     *
     * @return the int
     */
    public static int median(List<Integer> list) {
        int half = list.size() / 2;
        boolean even = list.size() % 2 == 0;

        if (even) {
            return (list.get(half - 1) + list.get(half)) / 2;
        }
        else {
            return list.get(half);
        }
    }

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */

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
        input.append("9\n");
        input.append("3 7 8 5 12 14 21 13 18\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("6\n12\n16\n", baos.toString());
    }

    /**
     * Test input 1.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInput1() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("11\n");
        input.append("6 7 15 36 39 40 41 42 43 47 49\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("15\n40\n43\n", baos.toString());

    }

    /**
     * Test input 2.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInput2() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("6\n");
        input.append("7 15 36 39 40 41\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("15\n37\n40\n", baos.toString());
    }

    /**
     * Test input 3.
     *
     * @throws Exception the exception
     */
    @Test
    public void testInput3() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("10\n");
        input.append("3 7 8 5 12 14 21 15 18 14\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("7\n13\n15\n", baos.toString());
    }
}
