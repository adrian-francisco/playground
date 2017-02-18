package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

public class DavisStaircase {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int s = in.nextInt();

        int[] steps = new int[] {1, 2, 3};
        int m = steps.length;

        for (int a0 = 0; a0 < s; a0++) {
            int n = in.nextInt();

            long[][] cache = new long[m + 1][n + 1];
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    cache[i][j] = 0;
                }
            }

            System.out.println(steps(steps, m, n, cache));

            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    System.out.print(cache[i][j] + " ");
                }
                System.out.println();
            }
        }

        in.close();
    }

    public static long steps(int[] steps, int m, int n, long[][] cache) {
        if (n == 0) {
            return 1;
        }

        if (n < 0) {
            return 0;
        }

        if (m <= 0) {
            return 0;
        }

        if (cache[m][n] > 0) {
            return cache[m][n];
        }

        long i = steps(steps, m - 1, n, cache);
        long j = steps(steps, m, n - steps[m - 1], cache);

        if (m - 1 >= 0) {
            cache[m - 1][n] = i;
        }

        if (n - steps[m - 1] >= 0) {
            cache[m][n - steps[m - 1]] = j;
        }

        cache[m][n] = i + j;
        return cache[m][n];
    }

    @Test
    public void test0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("3\n");
        input.append("1\n");
        input.append("3\n");
        input.append("7\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("1\n4\n44", baos.toString().trim());
    }
}
