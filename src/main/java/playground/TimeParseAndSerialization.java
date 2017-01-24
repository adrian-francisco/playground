/*
 * Name   : TimeParseAndSerialization.java
 * Author : AdrianF
 * Created: 2013-11-13
 */
package playground;

import ca.gc.ec.dms.commons.parser.MultiPointObservation;
import ca.gc.ec.dms.commons.util.XMLPrint;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SerializationUtils;

import java.io.File;


/**
 * Compares XML Parse/Generation vs. Object Serialization.
 *
 * @author  AdrianF
 */
public class TimeParseAndSerialization {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** filenames */
    private static String[] filenames = {"point-obs-small.xml", "point-obs-large.xml", "point-obs-multi.xml"};
    //private static String[] filenames = {"point-obs-small.xml"};

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of TimeParseAndSerialization.
     */
    public TimeParseAndSerialization() {
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /**
     * @param   args
     *
     * @throws  Exception  on any exception
     */
    public static void main(String[] args) throws Exception {

        for (String filename : filenames) {
            long time;
            long total;

            // XML Parse and Generate

            time = System.currentTimeMillis();

            MultiPointObservation obs = new MultiPointObservation();
            obs.parse(new File("src/main/resources/" + filename));
            time = System.currentTimeMillis() - time;
            System.out.println(filename + ": parse xml (ms): " + time);
            total = time;

            time = System.currentTimeMillis();

            String xml = new String(obs.generateXMLAsByte());
            time = System.currentTimeMillis() - time;
            System.out.println(filename + ": generate xml (ms): " + time);
            total += time;

            time = System.currentTimeMillis();

            String pretty = XMLPrint.prettyPrint(xml);
            time = System.currentTimeMillis() - time;
            System.out.println(filename + ": pretty print xml (ms): " + time);
            total += time;

            System.out.println(filename + ": size (bytes): " + xml.getBytes().length);
            System.out.println(filename + ": total time (ms): " + total);

            // Object Serialzation

            System.out.println();
            time = System.currentTimeMillis();

            byte[] serialized = SerializationUtils.serialize(obs);
            FileUtils.writeByteArrayToFile(new File("target/" + filename + ".ser"), serialized);
            time = System.currentTimeMillis() - time;
            System.out.println(filename + ": serialize object (ms): " + time);
            total = time;

            time = System.currentTimeMillis();

            obs = (MultiPointObservation)SerializationUtils.deserialize(serialized);
            time = System.currentTimeMillis() - time;
            System.out.println(filename + ": deserialize object (ms): " + time);
            total += time;

            System.out.println(filename + ": size (bytes): " + serialized.length);
            System.out.println(filename + ": total time (ms): " + total);

            String newXML = new String(obs.generateXMLAsByte());

//            if (xml.equals(newXML)) {
//                System.out.println("SAME!");
//            }
//            else {
//                System.err.println("expected:");
//                System.err.println(XMLPrint.prettyPrint(xml));
//                System.err.println("but was:");
//                System.err.println(XMLPrint.prettyPrint(newXML));
//            }

            System.out.println();
        }
    }

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
