package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

public class CoinChange {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int coins[] = new int[m];

        for (int coins_i = 0; coins_i < m; coins_i++) {
            coins[coins_i] = in.nextInt();
        }

        in.close();

        long[][] cache = new long[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                cache[i][j] = 0;
            }
        }

        System.out.println(change(coins, m, n, cache));
    }

    public static long change(int[] coins, int m, int n, long[][] cache) {

        // there is only 1 way to make change for $0, no coins
        if (n == 0) {
            return 1;
        }

        // if there is no money to make change for, there are 0 ways to make change
        if (n < 0) {
            return 0;
        }

        // if there are no coins, but change needs to be made, there are 0 ways to make change
        if (m <= 0) {
            return 0;
        }

        // return the cache if it was greater than 0
        if (cache[m][n] > 0) {
            return cache[m][n];
        }

        // change is sum of:
        // (i) how many ways to make change with one less coin denomination (m-1)
        // (j) how many ways to make change with money minus the last coin denomination (n - coins[m-1])
        long i = change(coins, m - 1, n, cache);
        long j = change(coins, m, n - coins[m - 1], cache);

        // cache i, if necessary
        if (m - 1 >= 0) {
            cache[m - 1][n] = i;
        }

        // cache j, if necessary
        if (n - coins[m - 1] >= 0) {
            cache[m][n - coins[m - 1]] = j;
        }

        return i + j;
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

        assertEquals("4", baos.toString().trim());
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

        assertEquals("5", baos.toString().trim());
    }

    @Test
    public void test2() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("7 3\n");
        input.append("1 2 3\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("8", baos.toString().trim());
    }

}
