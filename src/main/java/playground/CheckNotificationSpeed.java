/*
 * Name   : CheckNotificationSpeed.java
 * Author : Adrian Francisco
 * Created: 2013-10-04
 */
package playground;

import ca.gc.ec.dms.commons.controller.ConfigurationResult;
import ca.gc.ec.dms.commons.controller.DataPayload;
import ca.gc.ec.dms.commons.controller.WebWrite;
import ca.gc.ec.dms.commons.util.DateUtil;
import ca.gc.ec.dms.commons.util.Notification;
import ca.gc.ec.dms.commons.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;

import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * Tests Speed for various notification methods.
 *
 * @author  Adrian Francisco
 */
public class CheckNotificationSpeed {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** logging */
    private static final Log LOG = LogFactory.getLog(CheckNotificationSpeed.class);

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of CheckNotificationSpeed.
     */
    public CheckNotificationSpeed() {
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /**
     * @param   args  no arguments
     *
     * @throws  Exception  on any exception
     */
    public static void main(String[] args) throws Exception {
        Properties.getInstance();
        Notification.getInstance();
        testInstanceLookupSpeedBetweenTwoCores();
    }

    /**
     * Tests notification speed between latest lookups vs received datetime lookups.
     *
     * @throws  Exception  on any exception
     */
    public static void testInstanceLookupSpeedBetweenTwoCores() throws Exception {

        String host1 = "dms-v2-stage.cmc.ec.gc.ca";
        String port1 = "8180";
        String host2 = "dms-v2-stage2.cmc.ec.gc.ca";
        String port2 = "8180";

        URI path = URI.create(
                "/nav_canada/observation/atmospheric/surface_weather/hwos-1.0-binary/product_synop_tac-xml-2.0");
        String[] stations = {"2400633", "1108395"};

        System.out.println("\n" + host1 + "\n|\t" + host2 + "\n|\t|\tdifference");

        while (true) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.HOUR, -24);

            Date from = now.getTime();
            Date to = new Date();

            Notification.setDomain(host1);
            Notification.setPort(port1);

            long time1 = System.currentTimeMillis();

            for (String station : stations) {
                Notification.getURIsByPrimaryStationReceivedTime(path, station, from, to);
                Notification.getURIsByPrimaryStationObsTime(path, station, from, to);
                Notification.getLatestURI(path, station);
            }

            time1 = System.currentTimeMillis() - time1;

            Notification.setDomain(host2);
            Notification.setPort(port2);

            long time2 = System.currentTimeMillis();

            for (String station : stations) {
                Notification.getURIsByPrimaryStationReceivedTime(path, station, from, to);
                Notification.getURIsByPrimaryStationObsTime(path, station, from, to);
                Notification.getLatestURI(path, station);
            }

            time2 = System.currentTimeMillis() - time2;

            System.out.println(time1 + "\t" + time2 + "\t" + (time2 - time1));

            Thread.sleep(3000);
        }
    }

    /**
     * Tests notification speed between latest lookups vs received datetime lookups.
     *
     * @throws  Exception  on any exception
     */
    public static void testLatestSpeed() throws Exception {

        //Notification.setDomain("dms-dev15.to.on.ec.gc.ca");
        //Notification.setPort("8180");
        Notification.setDomain("dms-dev12.to.on.ec.gc.ca");
        Notification.setPort("9180");
        Notification.setStartTime(8765);

        URI path = URI.create("wmo/observation/atmospheric/aviation/amdar-1.0-ascii/decoded-xml-2.0");
        String station = "afza44";

        while (true) {
            URI latest = null;

            long time = System.currentTimeMillis();

            latest = Notification.getLatestURI(path, station);

            System.out.println(System.currentTimeMillis() - time + ": latest 1: " + latest);

            int count = 0;
            time = System.currentTimeMillis();

            for (URI uri :
                Notification.getURIsByPrimaryStationReceivedTime(URI.create(path.toString() + "&orderBy=desc&count=1"),
                    station)) {
                latest = uri;
                count++;
            }

            System.out.println(System.currentTimeMillis() - time + ": latest 2 (" + count + "): " + latest);

            System.out.println();

            Thread.sleep(3000);
        }
    }

    /**
     * Tests a write, then immediate notify. Seeing odd behaviour when doing this.
     *
     * @throws  Exception  on any exception
     */
    public static void testWriteThenNotify() throws Exception {
        String domain = "dms-dev12.to.on.ec.gc.ca";
        String port = "9180";

        Notification.setDomain(domain);
        Notification.setPort(port);
        Notification.setStartTime(1);

        Properties properties = Properties.getInstance();
        properties.setProperty("controller.writer.web.domain", domain);
        properties.setProperty("controller.writer.web.port", port);

        WebWrite writer = new WebWrite();
        writer.setName("web");

        ConfigurationResult cr = writer.checkConfig();

        if (!cr.isConfigurationPassed()) {
            cr.logErrors();
            throw new Exception("configuration failed!");
        }

        String taxonomy = "msc/observation/atmospheric/aviation/amdar-1.0-binary/product_profile-xml-2.1";
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.IDENTITY_DATE_FORMAT_PATTERN);
        String date = df.format(new Date());
        String station = "test";
        int count = 1;

        while (true) {
            long time = System.currentTimeMillis();

            String identity = taxonomy + "/" + date + "/" + station + "/" + count;

            DataPayload payload = new DataPayload();
            payload.setIdentity(Arrays.asList(identity.split("/")));
            payload.setPayload("test".getBytes("UTF-8"));

            writer.write(payload);

            URI latest = null;

            // using &time, must specify a future date
            Calendar past = Calendar.getInstance();
            past.add(Calendar.HOUR, -1);

            Calendar future = Calendar.getInstance();
            future.add(Calendar.HOUR, 1);

            for (URI uri :
                Notification.getURIsByPrimaryStationReceivedTime(URI.create(taxonomy), station, past.getTime(),
                    future.getTime())) {
                latest = uri;
            }

            // using &latest, always pass, but very slow
//            latest = Notification.getLatestURI(URI.create(taxonomy), station);

            if (!latest.toString().endsWith(identity)) {
                System.out.println("fail! (" + (System.currentTimeMillis() - time) + " ms)");
                System.out.println("\texpected: " + identity);
                System.out.println("\tactual: " + latest);
            }
            else {
                System.out.println("success! (" + (System.currentTimeMillis() - time) + " ms)");
            }

            count++;
            Thread.sleep(3000);
        }
    }

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
