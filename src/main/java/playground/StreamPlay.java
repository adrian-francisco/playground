package playground;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class StreamPlay {

    public static void main(String[] args) {
        Random random = new Random();
        Collection<Integer> c = new ArrayList<>();
        int size = 100;
        long timer = 0;
        long count = 0;

        for (int i = 0; i < size; i++) {
            c.add(random.nextInt(size));
        }

        c.stream().forEach(System.out::println);

        timer = System.currentTimeMillis();
        count = c.stream().filter(i -> i % 2 == 0).count();
        System.out.println("stream even count: " + count + " (" + (System.currentTimeMillis() - timer) + " ms)");

        timer = System.currentTimeMillis();
        count = c.parallelStream().filter(i -> i % 2 == 0).count();
        System.out
                .println("parallel stream even count: " + count + " (" + (System.currentTimeMillis() - timer) + " ms)");

        Integer max = c.stream().reduce(Integer.MIN_VALUE, Math::max);
        System.out.println("max: " + max);

        Integer min = c.stream().reduce(Integer.MAX_VALUE, Math::min);
        System.out.println("min: " + min);
    }
}
