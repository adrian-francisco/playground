/*
 * Name   : TaxonomyCopy.java
 * Author : Adrian Francisco
 * Created: 2013-03-26
 */
package playground;

import ca.gc.ec.dms.commons.controller.DataPayload;
import ca.gc.ec.dms.commons.controller.ReadWriteException;
import ca.gc.ec.dms.commons.parser.NotificationItem;
import ca.gc.ec.dms.commons.parser.NotificationXML;
import ca.gc.ec.dms.commons.util.HttpURLConnectionUtil;
import ca.gc.ec.dms.commons.util.Notification;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;


/**
 * Copies data given complete notification paths from one core to another.
 *
 * @author  Adrian Francisco
 */
public class DataCopy {

    /** logging */
    private static final Log LOG = LogFactory.getLog(DataCopy.class);

    /**
     * The main method.
     *
     * @param   args  the arguments
     *
     * @throws  Exception  on any error
     */
    public static void main(String[] args) throws Exception {
        String fromDomain = "dms-stability.to.on.ec.gc.ca";
        String fromPort = "8180";

        String toDomain = "dms-dev1.to.on.ec.gc.ca";
        String toPort = "8180";

        String[] notifications = {
                "http://dms-stability.to.on.ec.gc.ca:8180/notification?path=/msc/observation/atmospheric/satellite/sarwinds-1.0-binary/product-geotiff-1.0&time=2d&obstime=20130324022628&format=prettyPrint",
                "http://dms-stability.to.on.ec.gc.ca:8180/notification?path=/msc/observation/atmospheric/satellite/sarwinds-1.0-binary/product-ws_ascii-1.0&time=2d&obstime=20130324022628",
                "http://dms-stability.to.on.ec.gc.ca:8180/notification?path=/msc/observation/atmospheric/satellite/sarwinds-1.0-binary/decoded-header_ascii-1.0&time=2d&obstime=20130324022628",
                "http://dms-stability.to.on.ec.gc.ca:8180/notification?path=/msc/observation/atmospheric/satellite/sarwinds-1.0-binary/decoded-sum_ascii-1.0&time=2d&obstime=20130324022628",
                "http://dms-stability.to.on.ec.gc.ca:8180/notification?path=/msc/observation/atmospheric/satellite/sarwinds-1.0-binary/decoded-tiff-1.0&time=2d&obstime=20130324022628",
                "http://dms-stability.to.on.ec.gc.ca:8180/notification?path=/msc/observation/atmospheric/satellite/sarwinds-1.0-binary/consolidated_wind-csv-1.0&time=6d&station=west_coast&obstime=20130325140000-20130325160000"
            };

        Notification.setDomain(fromDomain);
        Notification.setPort(fromPort);

        for (String notification : notifications) {
            LOG.info("copying notificaton rss: " + notification);

            NotificationXML xml = new NotificationXML();
            InputStream in = null;

            try {
                in = HttpURLConnectionUtil.getInputStream(notification);
                xml.parse(in);
            }
            finally {

                if (in != null) {
                    in.close();
                }
            }

            for (NotificationItem item : xml.getItems()) {
                DataPayload payload = Notification.getDataPayload(item.getLink().toURI());
                String link = item.getLink().toString();
                link = link.replaceFirst(fromDomain + ":" + fromPort, toDomain + ":" + toPort);

                LOG.info("copying notificaton item: " + link);

                try {
                    HttpURLConnectionUtil.putBytes(link, payload.getPayload());
                }
                catch (ReadWriteException e) {
                    LOG.info("found duplicate, ignoring: " + link);
                }
            }
        }
    }
}
