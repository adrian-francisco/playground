/*
 * Name: TrainTest.java
 * Author: Adrian Francisco
 * Created: Oct 11, 2017
 */
package playground.schedule.transit;

import org.junit.Test;

/**
 * Tests the Trains.
 *
 * @author Adrian Francisco
 */
public class TrainTest {

    @Test
    public final void testTrain() {
        System.out.println(new Train(0, "adrian").toJson());
        System.out.println(new Train(1, "nico").toJson());
    }

}
