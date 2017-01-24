package playground;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

/**
 * The Class Day8.
 */
public class Day08 {

    /**
     * My main.
     *
     * @param argh the argh
     */
    public static void main(String[] argh) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        Map<String, Integer> phoneBook = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String name = in.next();
            int phone = in.nextInt();
            phoneBook.put(name, phone);
        }
        while (in.hasNext()) {
            String s = in.next();
            if (phoneBook.containsKey(s)) {
                System.out.println(s + "=" + phoneBook.get(s));
            }
            else {
                System.out.println("Not found");
            }
        }
        in.close();
    }

    /**
     * Test main.
     *
     * @throws Exception the exception
     */
    @Test
    public void testMain() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("3\n");
        input.append("sam 99912222\n");
        input.append("tom 11122222\n");
        input.append("harry 12299933\n");
        input.append("sam\n");
        input.append("edward\n");
        input.append("harry\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        Day08.main(null);
    }
}
