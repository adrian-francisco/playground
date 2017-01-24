/*
 * Name   : DataSAXParser.java
 * Author : Adrian Francisco
 * Created: 2015-10-07
 */
package playground;

import org.apache.commons.io.IOUtils;


/**
 * DOCUMENT ME!
 *
 * @author  Adrian Francisco
 */
public class DataSAXParser {

    /**
     * Creates a new instance of DataSAXParser.
     */
    public DataSAXParser() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param   args  DOCUMENT ME!
     *
     * @throws  Exception  DOCUMENT ME!
     */
    public static void main(String[] args) throws Exception {
        String xml = IOUtils.toString(DataSAXParser.class.getResourceAsStream("testQcedXML.xml"), "UTF-8");

        System.out.println(xml);

    }
}
