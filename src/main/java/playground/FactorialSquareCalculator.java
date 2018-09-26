package playground;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FactorialSquareCalculator extends RecursiveTask<Integer> {

    private static final long serialVersionUID = -1629665065156648726L;

    private Integer n;

    public FactorialSquareCalculator(Integer n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(n - 1);

        System.out.println("before fork: " + n);
        calculator.fork();
        System.err.println("after fork: " + n);

        System.out.println("before join: " + n);
        Integer result = n * n + calculator.join();
        System.err.println("after join: " + n);

        System.out.println(result);

        return result;
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(1000);

        forkJoinPool.execute(calculator);

        Thread.sleep(2000);
    }
}
