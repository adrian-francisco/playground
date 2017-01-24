/*
 * Name : GreedyFlorist.java
 * Author : Adrian Francisco
 * Created: Jan 13, 2017
 */
package playground;

import java.util.Arrays;
import java.util.Scanner;

class GreedyFlorist {

    public static void main(String args[]) {

        // helpers for input/output

        Scanner in = new Scanner(System.in);

        int flowers, people;

        flowers = in.nextInt();
        people = in.nextInt();

        int cost[] = new int[flowers];
        for (int i = 0; i < flowers; i++) {
            cost[i] = in.nextInt();
        }
        Arrays.sort(cost);

        in.close();

        int result = 0;

        int purchased[] = new int[people];

        int person = 0;

        for (int i = flowers - 1; i >= 0; i--) {
            result += (purchased[person] + 1) * cost[i];
            purchased[person]++;
            person++;

            if (purchased.length <= person) {
                person = 0;
            }
        }

        System.out.println(result);

    }
}
