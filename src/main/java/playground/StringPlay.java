/*
 * Name: StringPlay.java
 * Author: Adrian Francisco
 * Created: Oct 10, 2017
 */
package playground;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Playing with Strings.
 *
 * @author Adrian Francisco
 */
public class StringPlay {

    /**
     * Creates a new instance of StringPlay.
     */
    public StringPlay() {
    }

    /**
     * My own parse int. Base 10, positives only, always valid integers.
     *
     * @param s the string to parse
     * @return the int
     */
    public static int parseInt(String s) {
        int result = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int x = c - '0';
            result += Math.pow(10, s.length() - 1 - i) * x;
        }

        return result;
    }

    /**
     * Test parse int.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseInt() throws Exception {
        assertEquals(0, parseInt("0"));
        assertEquals(123, parseInt("123"));
    }
}
