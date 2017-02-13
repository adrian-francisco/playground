package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class FibonacciModified {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        int t1 = in.nextInt();
        int t2 = in.nextInt();
        int n = in.nextInt();

        in.close();

        Map<Integer, BigInteger> t = new HashMap<>();
        t.put(1, BigInteger.valueOf(t1));
        t.put(2, BigInteger.valueOf(t2));

        BigInteger f = fibonacciModified(n, t);

        System.out.println(f);
    }

    private static BigInteger fibonacciModified(int n, Map<Integer, BigInteger> t) {
        if (!t.containsKey(n)) {
            BigInteger ti = fibonacciModified(n - 2, t);
            BigInteger tj = fibonacciModified(n - 1, t).pow(2);
            t.put(n, ti.add(tj));
        }
        return t.get(n);
    }

    @Test
    public void test0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("0 1 5\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("5", baos.toString().trim());
    }

    @Test
    public void test1() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("0 1 10\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("84266613096281243382112", baos.toString().trim());
    }
}
