package hackerrank;

import java.util.Scanner;

public class CamelCase {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String s = in.next();
		in.close();

		int result = 1;

		for (char c : s.toCharArray()) {
			if (Character.isUpperCase(c)) {
				result++;
			}
		}

		System.out.println(result);
	}

}
