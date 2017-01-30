package hackerrank;

import java.util.Scanner;

public class Day03 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.close();
        String ans = "";

        // if 'n' is NOT evenly divisible by 2 (i.e.: n is odd)
        if (n % 2 == 1) {
            ans = "Weird";
        }
        else {
            // Complete the code
            if (2 <= n && n <= 5) {
                ans = "Not Weird";
            }
            else if (6 <= n && n <= 20) {
                ans = "Weird";
            }
            else {
                ans = "Not Weird";
            }
        }
        System.out.println(ans);
    }
}
