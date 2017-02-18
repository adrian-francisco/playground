package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class DavisStaircase {

    private static Map<Integer, Long> cache = new HashMap<>();

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int s = in.nextInt();

        cache.put(1, 1L);
        cache.put(2, 2L);
        cache.put(3, 4L);

        for (int i = 0; i < s; i++) {
            int n = in.nextInt();
            System.out.println(steps(n));
        }

        in.close();
    }

    public static long steps(int n) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }

        cache.put(n, steps(n - 1) + steps(n - 2) + steps(n - 3));
        return cache.get(n);
    }

    @Test
    public void test0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("4\n");
        input.append("1\n");
        input.append("3\n");
        input.append("7\n");
        input.append("36\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("1\n4\n44\n2082876103", baos.toString().trim());
    }
}
