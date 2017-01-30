package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class SDay001 {

    public static String result;

    public static void main(String... args) {

        Scanner scanner = new Scanner(System.in, "UTF-8");

        int N = scanner.nextInt();

        List<Integer> X = new ArrayList<>();
        double MSUM = 0.0;
        double WSUM = 0.0;

        for (int i = 0; i < N; i++) {
            X.add(scanner.nextInt());
        }

        for (int i = 0; i < N; i++) {
            int w = scanner.nextInt();
            MSUM += X.get(i) * w;
            WSUM += w;
        }

        scanner.close();

        double WMEAN = MSUM / WSUM;
        WMEAN = (double) Math.round(WMEAN * 10) / 10;
        result = Double.toString(WMEAN);

        System.out.println(result);
    }

    @Test
    public void testInput0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("5\n");
        input.append("10 40 30 50 20\n");
        input.append("1 2 3 4 5\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        SDay001.main();

        assertEquals("32.0", SDay001.result);
    }

    @Test
    public void testInput1() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("5\n");
        input.append("10 40 30 50 20\n");
        input.append("1 2 3 4 4\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        SDay001.main();

        assertEquals("32.9", SDay001.result);
    }

}
