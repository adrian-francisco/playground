package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class SockMerchant {

	public static void main(String... args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int c[] = new int[n];
		for (int c_i = 0; c_i < n; c_i++) {
			c[c_i] = in.nextInt();
		}
		in.close();

		int match = 0;

		for (int i = 0; i < n; i++) {
			if (c[i] == 0) {
				continue;
			}

			for (int j = i + 1; j < n; j++) {
				if (c[j] == 0) {
					continue;
				}

				if (c[i] == c[j]) {
					match++;
					c[j] = 0;
					break;
				}
			}
		}

		System.out.println(match);
	}

	@Test
	public void testInput0() throws Exception {
		StringBuilder input = new StringBuilder();
		input.append("9\n");
		input.append("10 20 20 10 10 30 50 10 20\n");

		System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));

		SockMerchant.main();

		Assert.assertEquals("3\n", baos.toString());
	}

}
