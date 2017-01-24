/*
 * Name   : CheckTimeZones.java
 * Author : Adrian Francisco
 * Created: 2015-07-02
 */
package playground;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.TimeZone;
import java.util.TreeSet;


/**
 * DOCUMENT ME!
 *
 * @author  Adrian Francisco
 */
public class CheckTimeZones {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** the logging */
    private static final Log LOG = LogFactory.getLog(CheckTimeZones.class);

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of CheckTimeZones.
     */
    public CheckTimeZones() {
    }

    /**
     * @param  args
     */
    public static void main(String[] args) {
        int hourOffset = -4;
        Collection<String> ids = new TreeSet<String>();
        ids.addAll(Arrays.asList(TimeZone.getAvailableIDs(hourOffset * 60 * 60 * 1000)));

        System.out.println("id\t\tdaylight");
        System.out.println("--\t\t--------");

        for (String id : ids) {
            boolean daylight = TimeZone.getTimeZone(id).useDaylightTime();

            System.out.println(id + "\t\t" + daylight);
        }
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
