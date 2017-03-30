package hackerrank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class JavaVisitorPattern {

    enum Color {
        RED, GREEN
    }

    abstract class Tree {

        private int value;

        private Color color;

        private int depth;

        public Tree(int value, Color color, int depth) {
            this.value = value;
            this.color = color;
            this.depth = depth;
        }

        public int getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }

        public int getDepth() {
            return depth;
        }

        public abstract void accept(TreeVis visitor);
    }

    class TreeNode extends Tree {

        private ArrayList<Tree> children = new ArrayList<>();

        public TreeNode(int value, Color color, int depth) {
            super(value, color, depth);
        }

        @Override
        public void accept(TreeVis visitor) {
            visitor.visitNode(this);

            for (Tree child : children) {
                child.accept(visitor);
            }
        }

        public void addChild(Tree child) {
            children.add(child);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("node ");
            sb.append(getValue()).append(" ");
            sb.append(getColor()).append(" ");
            sb.append(getDepth()).append(" -> ");
            for (Tree child : children) {
                sb.append(child.getValue()).append(" ");
            }
            return sb.toString();
        }
    }

    class TreeLeaf extends Tree {

        public TreeLeaf(int value, Color color, int depth) {
            super(value, color, depth);
        }

        @Override
        public void accept(TreeVis visitor) {
            visitor.visitLeaf(this);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("leaf ");
            sb.append(getValue()).append(" ");
            sb.append(getColor()).append(" ");
            sb.append(getDepth());
            return sb.toString();
        }
    }

    abstract class TreeVis {

        public abstract int getResult();

        public abstract void visitNode(TreeNode node);

        public abstract void visitLeaf(TreeLeaf leaf);

    }

    class SumInLeavesVisitor extends TreeVis {

        private int result = 0;

        @Override
        public int getResult() {
            return result;
        }

        @Override
        public void visitNode(TreeNode node) {
        }

        @Override
        public void visitLeaf(TreeLeaf leaf) {
            result += leaf.getValue();
        }
    }

    class ProductOfRedNodesVisitor extends TreeVis {

        private long result = 1;

        private int modulo = (int) (Math.pow(10, 9) + 7);

        @Override
        public int getResult() {
            return (int) result;
        }

        @Override
        public void visitNode(TreeNode node) {
            if (node.getColor().equals(Color.RED)) {
                result = result * node.getValue() % modulo;
            }
        }

        @Override
        public void visitLeaf(TreeLeaf leaf) {
            if (leaf.getColor().equals(Color.RED)) {
                result = result * leaf.getValue() % modulo;
            }
        }
    }

    class FancyVisitor extends TreeVis {

        private int evenResult = 0;

        private int greenResult = 0;

        @Override
        public int getResult() {
            return Math.abs(evenResult - greenResult);
        }

        @Override
        public void visitNode(TreeNode node) {
            if (node.getDepth() % 2 == 0) {
                evenResult += node.getValue();
            }
        }

        @Override
        public void visitLeaf(TreeLeaf leaf) {
            if (leaf.getColor().equals(Color.GREEN)) {
                greenResult += leaf.getValue();
            }
        }
    }

    public Tree solve() {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();

        Tree[] trees = new Tree[n];
        int[] values = new int[n];
        Color[] colors = new Color[n];
        int[][] edges = new int[n - 1][2];
        int[] depths = new int[n];
        HashMap<Integer, ArrayList<Integer>> children = new HashMap<>();

        for (int i = 0; i < n; i++) {
            values[i] = in.nextInt();
            children.put(i, new ArrayList<Integer>());
        }

        for (int i = 0; i < n; i++) {
            if (in.nextInt() == 0) {
                colors[i] = Color.RED;
            }
            else {
                colors[i] = Color.GREEN;
            }
        }

        for (int i = 0; i < n - 1; i++) {
            int u = in.nextInt() - 1;
            int v = in.nextInt() - 1;
            edges[i] = new int[] {u, v};
        }

        in.close();

        LinkedList<Integer> nextNode = new LinkedList<>();
        nextNode.offer(0);
        depths[0] = 0;

        while (!nextNode.isEmpty()) {
            int curNode = nextNode.poll();

            for (int i = 0; i < n - 1; i++) {
                if (edges[i][0] == curNode) {
                    int child = edges[i][1];
                    nextNode.offer(child);
                    depths[child] = depths[curNode] + 1;
                    children.get(curNode).add(child);
                    edges[i] = new int[] {-1, -1};
                }
                else if (edges[i][1] == curNode) {
                    int child = edges[i][0];
                    nextNode.offer(child);
                    depths[child] = depths[curNode] + 1;
                    children.get(curNode).add(child);
                    edges[i] = new int[] {-1, -1};
                }
            }
        }

        // create the leaves and nodes
        for (int i = 0; i < n; i++) {
            if (children.get(i).isEmpty()) {
                trees[i] = new TreeLeaf(values[i], colors[i], depths[i]);
            }
            else {
                trees[i] = new TreeNode(values[i], colors[i], depths[i]);
            }
        }

        // add the children to nodes
        for (int i = 0; i < n; i++) {
            if (!children.get(i).isEmpty()) {
                TreeNode node = (TreeNode) trees[i];
                for (int j : children.get(i)) {
                    node.addChild(trees[j]);
                }
            }
        }

        return trees[0];
    }

    public void main() {
        Tree root = new JavaVisitorPattern().solve();
        SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
        ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
        FancyVisitor vis3 = new FancyVisitor();

        root.accept(vis1);
        root.accept(vis2);
        root.accept(vis3);

        int res1 = vis1.getResult();
        int res2 = vis2.getResult();
        int res3 = vis3.getResult();

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }

    @Test
    public void test1() throws Exception {
        String input = "5\n4 7 2 5 12\n0 1 0 0 1\n1 2\n1 3\n3 4\n3 5";

        System.setIn(new ByteArrayInputStream(input.getBytes("UTF-8")));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos, false, "UTF-8"));

        main();

        String output = "24\n40\n15";

        Assert.assertEquals(output.trim(), baos.toString("UTF-8").trim());
    }
}
