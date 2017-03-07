package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class Java1DArray {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in, "UTF-8");

        int t = in.nextInt();

        while (t-- > 0) {
            int n = in.nextInt();
            int m = in.nextInt();
            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = in.nextInt();
            }

            if (solvable(array, m, 0)) {
                System.out.println("YES");
            }
            else {
                System.out.println("NO");
            }
        }

        in.close();
    }

    private static boolean solvable(int[] array, int m, int i) {
        if (i < 0 || array[i] == 1) {
            return false;
        }
        else if (i + 1 >= array.length || i + m >= array.length) {
            return true;
        }

        array[i] = 1;

        return solvable(array, m, i - 1) || solvable(array, m, i + 1) || solvable(array, m, i + m);
    }

    @Test
    public void test09() throws Exception {
        FileInputStream input = new FileInputStream("src/main/resources/hackerrank/Java1DArrayInput09.txt");
        System.setIn(input);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, false, "UTF-8"));

        String output =
                FileUtils.readFileToString(new File("src/main/resources/hackerrank/Java1DArrayOutput09.txt"), "UTF-8");

        main();

        Assert.assertEquals(output.trim(), baos.toString("UTF-8").trim());
    }

    @Test
    public void test09a() throws Exception {
        String input = "1\n" + "58 27\n" +
                "0 0 0 1 1 0 1 0 1 0 0 0 1 1 1 0 0 0 1 1 1 0 1 1 0 1 1 0 1 0 0 0 0 1 0 0 1 1 1 1 0 1 0 0 1 1 0 1 1 1 1 1 1 1 1 0 1 0";

        System.setIn(new ByteArrayInputStream(input.getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, false, "UTF-8"));

        main();

        String output = "YES";

        Assert.assertEquals(output, baos.toString("UTF-8").trim());
    }
}
