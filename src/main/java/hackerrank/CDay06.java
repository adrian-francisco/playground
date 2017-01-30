package hackerrank;

import java.util.ArrayList;
import java.util.Scanner;

public class CDay06 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt();
        ArrayList<String> Ss = new ArrayList<>();

        for (int i = 0; i < T; i++) {
            Ss.add(scanner.next());
        }

        scanner.close();

        for (String S : Ss) {
            for (int i = 0; i < S.length(); i = i + 2) {
                if (i > S.length() - 1) {
                    break;
                }
                System.out.print(S.charAt(i));
            }
            System.out.print(" ");
            for (int i = 1; i < S.length(); i = i + 2) {
                if (i > S.length() - 1) {
                    break;
                }
                System.out.print(S.charAt(i));
            }
            System.out.println();
        }
    }
}
