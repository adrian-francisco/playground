package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class MissingNumbers {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        Map<Integer, Integer> map = new TreeMap<>();

        int n = in.nextInt();

        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            Integer v = map.get(x);

            if (v == null) {
                map.put(x, -1);
            }
            else {
                map.put(x, v - 1);
            }
        }

        int m = in.nextInt();

        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            Integer v = map.get(x);

            if (v == null) {
                map.put(x, 1);
            }
            else {
                map.put(x, v + 1);
            }
        }

        in.close();

        for (Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            if (value > 0) {
                System.out.print(key + " ");
            }
        }

    }

    @Test
    public void testInput0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("10\n");
        input.append("203 204 205 206 207 208 203 204 205 206\n");
        input.append("13\n");
        input.append("203 204 204 205 206 207 205 208 203 206 205 206 204\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        MissingNumbers.main();

        Assert.assertEquals("204 205 206 ", baos.toString());
    }

    @Test
    public void testInput1() throws Exception {
        FileInputStream fis = new FileInputStream("src/main/resources/hackerrank/MissingNumbersInput04.txt");
        System.setIn(fis);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        MissingNumbers.main();

        Assert.assertEquals("7251 7259 7276 7279 7292 7293 ", baos.toString());
    }
}
