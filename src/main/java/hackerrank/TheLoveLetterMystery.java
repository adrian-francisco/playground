package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class TheLoveLetterMystery {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in, "UTF-8");

        int n = in.nextInt();

        for (int i = 0; i < n; i++) {
            String word = in.next();
            int length = word.length() - 1;
            int half = word.length() / 2;
            int ops = 0;

            for (int j = 0; j < half; j++) {
                char front = word.charAt(j);
                char back = word.charAt(length - j);

                if (front != back) {
                    ops += Math.abs(front - back);
                }
            }

            System.out.println(ops);
        }

        in.close();
    }

    @Test
    public void test0() throws Exception {
        String input = "4\n" + "abc\n" + "abcba\n" + "abcd\n" + "cba";

        System.setIn(new ByteArrayInputStream(input.getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, false, "UTF-8"));

        main();

        String output = "2\n" + "0\n" + "4\n" + "2";

        Assert.assertEquals(output, baos.toString("UTF-8").trim());
    }
}
