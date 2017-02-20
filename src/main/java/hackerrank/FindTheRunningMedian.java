package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import org.junit.Test;

public class FindTheRunningMedian {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        // both queues must be balanced at all times

        // max heap containing the lower half of the list, including the median if size is odd
        PriorityQueue<Double> lower = new PriorityQueue<>(new Comparator<Double>() {

            @Override
            public int compare(Double o1, Double o2) {
                return o2.compareTo(o1);
            }
        });

        // min heap containing the upper half of the list
        PriorityQueue<Double> upper = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            double a = in.nextDouble();

            if (lower.isEmpty()) {
                lower.offer(a);
            }
            else if (lower.size() <= upper.size()) {
                if (!upper.isEmpty() && upper.peek() < a) {
                    lower.offer(upper.poll());
                    upper.offer(a);
                }
                else {
                    lower.offer(a);
                }
            }
            else { // lower.size() > upper.size()
                if (lower.peek() >= a) {
                    upper.offer(lower.poll());
                    lower.offer(a);
                }
                else {
                    upper.offer(a);
                }
            }

            int size = lower.size() + upper.size();
            double median = Double.NaN;

            if (size % 2 == 0) {
                median = (lower.peek() + upper.peek()) / 2;
            }
            else {
                median = lower.peek();
            }

            System.out.println(round(median, 1));
        }

        in.close();
    }

    public static double round(double d, int precision) {
        double tens = Math.pow(10.0, precision);
        return Math.round(d * tens) / tens;
    }

    @Test
    public void test0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("6\n");
        input.append("12\n");
        input.append("4\n");
        input.append("5\n");
        input.append("3\n");
        input.append("8\n");
        input.append("7\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("12.0\n" + "8.0\n" + "5.0\n" + "4.5\n" + "5.0\n" + "6.0", baos.toString().trim());
    }

    @Test
    public void test3() throws Exception {
        FileInputStream fis = new FileInputStream("src/main/resources/hackerrank/FTRMinput03.txt");
        System.setIn(fis);

        main();
    }
}
