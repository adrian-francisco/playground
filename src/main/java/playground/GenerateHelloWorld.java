/*
 * Name : GenerateHelloWorld.java
 * Author : AdrianF
 * Created: 2013-09-12
 */
package playground;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Generates the playground/HelloWorld.java file in the target/generated-sources/dms
 *
 * @author AdrianF
 */
public class GenerateHelloWorld {

    /*
     **************************************** PUBLIC FIELDS ************************************************************
     */

    /*
     **************************************** PRIVATE FIELDS ***********************************************************
     */

    /** logging */
    private static final Log LOG = LogFactory.getLog(GenerateHelloWorld.class);

    /*
     **************************************** CONSTRUCTORS *************************************************************
     */

    /**
     * Creates a new instance of GenerateHelloWorld.
     */
    public GenerateHelloWorld() {
    }

    /**
     * @param args
     * @throws Exception on any exception
     */
    public static void main(String[] args) {
        StringBuffer java = new StringBuffer();

        java.append("package playground.generated;\n");
        java.append("public class HelloWorld {\n");
        java.append("    public static void main(String[] args) {\n");
        java.append("        System.out.println(\"Hello, World\");\n");
        java.append("    }\n");
        java.append("}\n");

        try {
            FileUtils.write(new File("target/generated-sources/playground/generated/HelloWorld.java"), java.toString(),
                    StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     **************************************** PUBLIC METHODS ***********************************************************
     */

    /*
     **************************************** PRIVATE METHODS **********************************************************
     */
}
