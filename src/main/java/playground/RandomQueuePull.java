/*
 * Name   : RandomQueuePull.java
 * Author : AdrianF
 * Created: 2014-05-01
 */
package playground;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author  AdrianF
 */
public class RandomQueuePull {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** logging */
    private static final Log LOG = LogFactory.getLog(RandomQueuePull.class);

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of RandomQueuePull.
     */
    public RandomQueuePull() {
    }

    /**
     * @param   args
     *
     * @throws  Exception
     */
    public static void main(String[] args) throws Exception {


        List<String> queue = new ArrayList<String>();
        queue.add("a");
        queue.add("b");
        queue.add("c");

        while (true) {
            Collections.shuffle(queue);
            System.out.println(queue);
            Thread.sleep(3000);
        }
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
