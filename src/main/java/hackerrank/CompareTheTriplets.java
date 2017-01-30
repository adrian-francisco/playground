package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class CompareTheTriplets {

    private static String result;

    public static void main(String... args) {
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        a.add(in.nextInt());
        a.add(in.nextInt());
        a.add(in.nextInt());
        b.add(in.nextInt());
        b.add(in.nextInt());
        b.add(in.nextInt());
        in.close();

        int aScore = 0;
        int bScore = 0;

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) > b.get(i)) {
                aScore++;
            }
            else if (a.get(i) < b.get(i)) {
                bScore++;
            }
        }

        result = aScore + " " + bScore;
        System.out.println(result);
    }

    @Test
    public void testInput0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("5 6 7\n");
        input.append("3 6 10\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        CompareTheTriplets.main();

        assertEquals("1 1", CompareTheTriplets.result);
    }

}
