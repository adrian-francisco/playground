package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

public class Contacts {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        Node root = new Node('*');

        for (int i = 0; i < n; i++) {
            String op = in.next();
            String contact = in.next();

            if ("add".equals(op)) {
                Node current = root;
                for (char c : contact.toCharArray()) {
                    current.words++;
                    if (current.children.containsKey(c)) {
                        current = current.children.get(c);
                    }
                    else {
                        Node node = new Node(c);
                        current.children.put(c, node);
                        current = node;
                    }
                }
                current.words++;
                current.children.put('*', new Node('*'));
            }
            else { // "find".equals(op)
                Node current = root;
                boolean found = true;
                for (char c : contact.toCharArray()) {
                    if (current.children.containsKey(c)) {
                        current = current.children.get(c);
                    }
                    else {
                        found = false;
                    }
                }
                if (found) {
                    System.out.println(current.words);
                }
                else {
                    System.out.println('0');
                }
            }
        }

        in.close();
    }

    public static class Node {

        public char data;

        public int words;

        public Map<Character, Node> children = new HashMap<>();

        public Node(char data) {
            this.data = data;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Character, Node> entry : children.entrySet()) {
                sb.append(data + " (" + words + "): " + entry.getKey() + "=" + entry.getValue());
                sb.append('\n');
            }

            return sb.toString();
        }
    }

    @Test
    public void test0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("4\n");
        input.append("add hack\n");
        input.append("add hackerrank\n");
        input.append("find hac\n");
        input.append("find hak\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("2 0", baos.toString().trim().replaceAll("\n", " "));
    }

    @Test
    public void test1() throws Exception {
        System.setIn(new FileInputStream("src/main/resources/hackerrank/Contactsinput01.txt"));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("5 4 3 2 1 0", baos.toString().trim().replaceAll("\n", " "));
    }

    @Test
    public void test3() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("2\n");
        input.append("add hack\n");
        input.append("find hack\n");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("1", baos.toString().trim().replaceAll("\n", " "));
    }
}
