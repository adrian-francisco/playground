package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class BFSShortestReach {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in, "UTF-8");

        int q = in.nextInt();

        for (int i = 0; i < q; i++) {

            int n = in.nextInt();
            Map<Integer, Node> nodes = new HashMap<>();

            for (int j = 1; j <= n; j++) {
                nodes.put(j, new Node(j));
            }

            int m = in.nextInt();

            for (int j = 0; j < m; j++) {
                Node u = nodes.get(in.nextInt());
                Node v = nodes.get(in.nextInt());

                u.adjacents.add(v);
                v.adjacents.add(u);
            }

            Node s = nodes.get(in.nextInt());

            Queue<Node> queue = new LinkedList<>();
            queue.offer(s);

            while (!queue.isEmpty()) {
                Node current = queue.poll();

                if (current.visited) {
                    continue;
                }

                current.visited = true;

                for (Node adjacent : current.adjacents) {
                    if (adjacent.distance == 0) {
                        adjacent.distance = current.distance + 6;
                    }
                    queue.add(adjacent);
                }
            }

            StringBuilder sb = new StringBuilder();

            for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
                if (entry.getKey() == s.id) {
                    continue;
                }

                if (entry.getValue().distance == 0) {
                    sb.append(-1);
                }
                else {
                    sb.append(entry.getValue().distance);
                }
                sb.append(" ");
            }

            System.out.println(sb.toString().trim());
        }

        in.close();
    }

    private static class Node {

        public int id = 0;

        public int distance = 0;

        public boolean visited = false;

        public List<Node> adjacents = new ArrayList<>();

        public Node(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id + ": " + distance + ": " + visited + ": " + adjacents.size();
        }
    }

    @Test
    public void test0() throws Exception {
        String input = "2\n" + "4 2\n" + "1 2\n" + "1 3\n" + "1\n" + "3 1\n" + "2 3\n" + "2";

        System.setIn(new ByteArrayInputStream(input.getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, false, "UTF-8"));

        main();

        Assert.assertEquals("6 6 -1\n" + "-1 6", baos.toString("UTF-8").trim());
    }

    @Test
    public void test1() throws Exception {
        FileInputStream input = new FileInputStream("src/main/resources/hackerrank/BFSinput01.txt");
        System.setIn(input);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, false, "UTF-8"));

        String output = FileUtils.readFileToString(new File("src/main/resources/hackerrank/BFSoutput01.txt"), "UTF-8");

        main();

        Assert.assertEquals(output, baos.toString("UTF-8").trim());
    }
}
