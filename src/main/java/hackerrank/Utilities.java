package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore // this is not a real test
@SuppressWarnings("javadoc") // add javadoc warnings suppressions
public class Utilities {

    // use ... args to be able to call main()
    public static void main(String... args) {

        // specify UTF-8 encoding for scanners
        Scanner in = new Scanner(System.in, "UTF-8");

        // close scanners
        in.close();
    }

    // string in/out unit test
    // can cut and paste the strings directly from hackerrank
    @Test
    public void testString() throws Exception {
        String input = "2\n" + "4 2\n" + "1 2\n" + "1 3\n" + "1\n" + "3 1\n" + "2 3\n" + "2";

        System.setIn(new ByteArrayInputStream(input.getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, false, "UTF-8"));

        main();

        String output = "6 6 -1\n" + "-1 6";

        Assert.assertEquals(output, baos.toString("UTF-8").trim());
    }

    // file in/out unit test
    // useful for purchased test cases
    @Test
    public void testFile() throws Exception {
        FileInputStream input = new FileInputStream("src/main/resources/hackerrank/input.txt");
        System.setIn(input);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, false, "UTF-8"));

        String output = FileUtils.readFileToString(new File("src/main/resources/hackerrank/output.txt"), "UTF-8");

        main();

        Assert.assertEquals(output, baos.toString("UTF-8").trim());
    }
}
