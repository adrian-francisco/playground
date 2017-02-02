/*
 * Name : Notification.java
 * Author : Adrian Francisco
 * Created: Feb 2, 2017
 */
package commons;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * DOCUMENT ME!
 *
 * @author Adrian Francisco
 */
public class Notification {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** the logging */
    private static final Log LOG = LogFactory.getLog(Notification.class);

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of Notification.
     */
    public Notification() {
    }

    /**
     *
     */
    public static void getInstance() {
        // TODO Auto-generated method stub

    }

    /**
     * @param host1
     */
    public static void setDomain(String host1) {
        // TODO Auto-generated method stub

    }

    /**
     * @param port1
     */
    public static void setPort(String port1) {
        // TODO Auto-generated method stub

    }

    /**
     * @param path
     * @param station
     * @param from
     * @param to
     */
    public static Collection<URI> getURIsByPrimaryStationReceivedTime(URI path, String station, Date from, Date to) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param path
     * @param station
     * @return
     */
    public static URI getLatestURI(URI path, String station) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param i
     */
    public static void setStartTime(int i) {
        // TODO Auto-generated method stub

    }

    /**
     * @param uri
     * @return
     */
    public static DataPayload getDataPayload(URI uri) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param path
     * @param station
     * @param from
     * @param to
     */
    public static void getURIsByPrimaryStationObsTime(URI path, String station, Date from, Date to) {
        // TODO Auto-generated method stub

    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */}
