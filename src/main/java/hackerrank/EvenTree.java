package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class EvenTree {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Map<Integer, Node> nodes = new HashMap<>();

        for (int i = 1; i <= n; i++) {
            nodes.put(i, new Node(i));
        }

        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            nodes.get(u).parent = nodes.get(v);
            nodes.get(v).children.add(nodes.get(u));
        }

        in.close();

        decendants(nodes.get(1));

        int result = 0;

        for (Map.Entry<Integer, Node> entry : nodes.entrySet()) {
            Node value = entry.getValue();
            if (value.parent == null) {
                continue;
            }
            if (value.decendants % 2 == 0) {
                result++;
            }
        }

        System.out.println(result);
    }

    private static void decendants(Node node) {
        for (Node child : node.children) {
            decendants(child);
            node.decendants += child.decendants;
        }
    }

    public static class Node {

        public int data;

        public int decendants;

        public Node parent;

        public Collection<Node> children;

        public Node(int data) {
            this.data = data;
            this.decendants = 1;
            this.parent = null;
            this.children = new ArrayList<>();
        }
    }

    @Test
    public void testInput0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("10 9\n");
        input.append("2 1\n");
        input.append("3 1\n");
        input.append("4 3\n");
        input.append("5 2\n");
        input.append("6 1\n");
        input.append("7 2\n");
        input.append("8 6\n");
        input.append("9 8\n");
        input.append("10 8\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("2", baos.toString().trim());
    }
}
