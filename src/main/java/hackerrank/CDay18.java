package hackerrank;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class CDay18 {

}

class Solution {

    private Stack<Character> stack = new Stack<>();

    private Queue<Character> queue = new LinkedList<>();

    public void pushCharacter(char c) {
        stack.push(c);
    }

    public void enqueueCharacter(char c) {
        queue.offer(c);
    }

    public char popCharacter() {
        return stack.pop();
    }

    public char dequeueCharacter() {
        return queue.poll();
    }
}
