package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class ConnectedCellInAGrid {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();
        int[][] grid = new int[n][m];

        for (int grid_i = 0; grid_i < n; grid_i++) {
            for (int grid_j = 0; grid_j < m; grid_j++) {
                grid[grid_i][grid_j] = in.nextInt();
            }
        }

        in.close();

        int maxRegion = 0;
        boolean[][] visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (visited[i][j]) {
                    continue;
                }

                int region = countRegion(i, j, grid, visited);

                if (region > maxRegion) {
                    maxRegion = region;
                }
            }
        }

        System.out.println(maxRegion);
    }

    private static int countRegion(int i, int j, int[][] grid, boolean[][] visited) {
        int n = grid.length;
        int m = grid[0].length;

        if (i < 0 || n <= i || j < 0 || m <= j) {
            return 0;
        }

        if (visited[i][j]) {
            return 0;
        }

        visited[i][j] = true;

        if (grid[i][j] == 0) {
            return 0;
        }

        int connected = 1;
        connected += countRegion(i - 1, j - 1, grid, visited);
        connected += countRegion(i - 1, j, grid, visited);
        connected += countRegion(i - 1, j + 1, grid, visited);
        connected += countRegion(i, j - 1, grid, visited);
        connected += countRegion(i, j + 1, grid, visited);
        connected += countRegion(i + 1, j - 1, grid, visited);
        connected += countRegion(i + 1, j, grid, visited);
        connected += countRegion(i + 1, j + 1, grid, visited);

        return connected;
    }

    @Test
    public void test0() throws Exception {
        String input = "4\n" + "4\n" + "1 1 0 0\n" + "0 1 1 0\n" + "0 0 1 0\n" + "1 0 0 0\n";

        System.setIn(new ByteArrayInputStream(input.getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        Assert.assertEquals("5", baos.toString().trim());
    }

}
