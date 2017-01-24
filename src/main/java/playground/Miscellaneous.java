/*
 * Name : Miscellaneous.java
 * Author : Adrian Francisco
 * Created: 2014-04-15
 */
package playground;

import java.util.ArrayList;

/**
 * Random miscellaneous stuff.
 *
 * @author Adrian Francisco
 */
public class Miscellaneous {

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception on any exception
     */
    public static void main(String[] args) throws Exception {

        ArrayList<String> taxonomiesNotPurged = new ArrayList<>();

        taxonomiesNotPurged.add("/msc/observation/atmospheric/satellite/sarwinds-1.0-binary/product-geotiff-1.0");
        taxonomiesNotPurged.add("/msc/model/atmospheric/nwp/model-1.0-binary/decoded-grib_csv-2.0");
        taxonomiesNotPurged.add("/msc/observation/atmospheric/satellite/sarwinds-1.0-binary/product-jpeg-1.0");

        // log the warning and build the response
        StringBuffer toReturnBufr = new StringBuffer();

        if (!taxonomiesNotPurged.isEmpty()) {
            for (String taxonomy : taxonomiesNotPurged) {
                toReturnBufr.append(taxonomy);
                toReturnBufr.append(" ");
            }

            toReturnBufr.deleteCharAt(toReturnBufr.length() - 1);

            System.out.println("The following taxonomies have not been purged in the DB, " +
                    "reference purge for them will not occur: " + toReturnBufr.toString().replaceAll(" ", ", "));
        }

        System.out.println("--" + toReturnBufr.toString() + "--");
    }
}
