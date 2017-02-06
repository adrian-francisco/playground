package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class MissingNumbers {

	public static void main(String... args) {
		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		List<Integer> a = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			a.add(in.nextInt());
		}

		int m = in.nextInt();
		List<Integer> b = new ArrayList<>();

		for (int i = 0; i < m; i++) {
			b.add(in.nextInt());
		}

		in.close();

		for (Integer i : a) {
			b.remove(i);
		}

		Set<Integer> s = new TreeSet<>(b);

		for (Integer i : s) {
			System.out.print(i + " ");
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
