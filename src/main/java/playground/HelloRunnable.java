/*
 * Name   : HelloRunnable.java
 * Author : AdrianF
 * Created: 2013-11-04
 */
package playground;

import java.util.Random;


/**
 * DOCUMENT ME!
 *
 * @author  AdrianF
 */
public class HelloRunnable implements Runnable {

    /** DOCUMENT ME! */
    private int id = 0;

    /** DOCUMENT ME! */
    private int counter = 0;

    /** DOCUMENT ME! */
    private Random random = new Random();

    /**
     * @param  id  DOCUMENT ME!
     */
    public HelloRunnable(int id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {

        while (true) {
            int sleep = random.nextInt(5000);

            try {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("id=" + id + ": count=" + counter++ + ": sleep=" + sleep);
        }
    }

    /**
     * @param  args
     */
    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new HelloRunnable(i));
            thread.start();
        }
    }

}
