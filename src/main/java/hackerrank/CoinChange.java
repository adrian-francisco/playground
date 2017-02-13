package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class CoinChange {

    private static Map<Integer, Integer> cache = new HashMap<>();

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();

        int[] c = new int[m];

        for (int i = 0; i < m; i++) {
            c[i] = in.nextInt();
        }

        Arrays.sort(c);

        int result = change(n, c);

        System.out.print(result);
    }

    private static int change(int n, int[] c) {
        if (n == 0 || c.length == 0) {
            return 0;
        }

        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        return 0;
    }

    @Test
    public void test0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("4 3\n");
        input.append("1 2 3\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("4", baos.toString());
    }

    @Test
    public void test1() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("10 4\n");
        input.append("2 5 3 6\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("4", baos.toString());
    }

}
