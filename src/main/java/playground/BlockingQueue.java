/*
 * Name : BlockingQueue.java
 * Author : Adrian Francisco
 * Created: Feb 9, 2017
 */
package playground;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * A blocking queue implementation using wait() and notify().
 *
 * @author Adrian Francisco
 */
public class BlockingQueue {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** The queue. */
    private Queue<Object> queue = new LinkedList<>();

    /** The size. */
    private int size = 0;

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of BlockingQueue.
     *
     * @param size the size
     */
    public BlockingQueue(int size) {
        this.size = size;
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /**
     * Enqueue.
     *
     * @param obj the obj
     * @throws InterruptedException the interrupted exception
     */
    public void enqueue(Object obj) throws InterruptedException {
        synchronized (queue) {
            while (queue.size() + 1 > size) {
                System.out.println("enqueue: waiting...");
                queue.wait();
            }
            queue.offer(obj);
            queue.notify();
            System.out.println("enqueue: size: " + queue.size());
        }
    }

    /**
     * Dequeue.
     *
     * @return the object
     * @throws InterruptedException the interrupted exception
     */
    public Object dequeue() throws InterruptedException {
        synchronized (queue) {
            while (queue.size() == 0) {
                System.out.println("dequeue: waiting...");
                queue.wait();
            }
            Object obj = queue.poll();
            queue.notify();
            System.out.println("dequeue: size: " + queue.size());
            return obj;
        }
    }

    /**
     * The main method. Used for testing.
     *
     * @param args the arguments
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws InterruptedException {
        // the size of the queue
        int size = 5;

        // the number of dequeuer threads to spawn
        int threads = 3;

        // the number of milliseconds each dequeue thread waits before dequeuing again
        // (this allows testing enqueue overflow)
        int sleep = 5000;

        BlockingQueue queue = new BlockingQueue(size);

        ExecutorService dequeuers = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            dequeuers.execute(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            System.out.println(this.getClass().getName() + ": dequeued: " + queue.dequeue());
                            Thread.sleep(sleep);
                        }
                        catch (InterruptedException e) {
                            System.out.println(this.getClass().getName() + ": shutting down");
                            return;
                        }
                    }
                }
            });
        }

        Scanner in = new Scanner(System.in, "UTF-8");

        System.out.println();
        System.out.println("Enqueue stuff (Enter 'quit' to stop): ");

        while (true) {
            String obj = in.next();

            if ("quit".equals(obj)) {
                break;
            }

            queue.enqueue(obj);
        }

        in.close();
        dequeuers.shutdownNow();
        dequeuers.awaitTermination(60, TimeUnit.SECONDS);
    }

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
