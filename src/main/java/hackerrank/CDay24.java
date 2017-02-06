package hackerrank;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

public class CDay24 {

    public static Node removeDuplicates(Node head) {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return head;
        }

        if (head.data == head.next.data) {
            head.next = head.next.next;
            removeDuplicates(head);
        }
        else {
            removeDuplicates(head.next);
        }

        return head;
    }

    public static Node insert(Node head, int data) {
        Node p = new Node(data);
        if (head == null) {
            head = p;
        }
        else if (head.next == null) {
            head.next = p;
        }
        else {
            Node start = head;
            while (start.next != null) {
                start = start.next;
            }
            start.next = p;

        }
        return head;
    }

    public static void display(Node head) {
        Node start = head;
        while (start != null) {
            System.out.print(start.data + " ");
            start = start.next;
        }
    }

    public static void main(String... args) {
        Scanner sc = new Scanner(System.in);
        Node head = null;
        int T = sc.nextInt();
        while (T-- > 0) {
            int ele = sc.nextInt();
            head = insert(head, ele);
        }
        head = removeDuplicates(head);
        display(head);
    }

    private static class Node {

        int data;

        Node next;

        Node(int d) {
            data = d;
            next = null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "data: " + data + ", next: " + next + "\n";
        }
    }

    @Test
    public void testInput0() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("6\n1\n2\n2\n3\n3\n4");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("1 2 3 4 ", baos.toString());
    }

    @Test
    public void testInput1() throws Exception {
        StringBuilder input = new StringBuilder();
        input.append("6\n1\n1\n1\n1\n1\n1");

        System.setIn(new ByteArrayInputStream(input.toString().getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        main();

        assertEquals("1 ", baos.toString());
    }
}
