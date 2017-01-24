package playground;

import java.io.File;

import ca.gc.ec.dms.commons.metadata.MDInstance;
import ca.gc.ec.dms.commons.util.XMLPrint;

/**
 * My Play Ground.
 */
public class PlayGround {

    /**
     * The main playground.
     *
     * @param args the playground arguments
     * @throws Exception on any exception
     */
    public static void main(String[] args) throws Exception {
        MDInstance instance = new MDInstance();
        instance.parse(new File("src/test/resources/element_11_1001.xml"));

        System.out.println(XMLPrint.prettyPrint(new String(instance.generateXMLAsByte())));

        System.out.println(instance.getCreatedBy());
    }
}
