package cap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class DesignationsGenerator {

    /** The input file. */
    private static final String INPUT_FILE = "src/main/resources/designations.csv";

    /** The output file designations file. */
    private static final String DESIGNATION_FILE = "src/main/resources/designation.application.properties";

    /** The output file designations language file. */
    private static final String LANGUAGE_FILE = "src/main/resources/designation.language.application.properties";

    /**
     * @param  args
     */
    public static void main(String[] args) {
        File file = FileUtils.getFile(INPUT_FILE);

        if ((file == null) || !file.exists()) {
            throw new ExceptionInInitializerError("The file: " + INPUT_FILE +
                " could not be encapulated in a File object and cannot be used.");
        }

        if (!file.canRead()) {
            throw new ExceptionInInitializerError("The file: " + INPUT_FILE + " cannot be read and used.");
        }

        FileInputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        FileWriter fw2 = null;
        BufferedWriter bw2 = null;

        int i = 0;

        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);

            fw = new FileWriter(DESIGNATION_FILE);
            bw = new BufferedWriter(fw);

            fw2 = new FileWriter(LANGUAGE_FILE);
            bw2 = new BufferedWriter(fw2);

            String line = StringUtils.EMPTY;

            for (line = br.readLine(); line != null; line = br.readLine(), i++) {

                String[] entries = line.split("\\|");

                // let's see if we even have anything, otherwise, skip
                if (entries == null) {
                    continue;
                }

                System.out.println("Handling line: " + line + " with list of entries: " + entries +
                    " at line number: " + i);

                // format the bulletin names for entry 1 and 2 to have underscores
                String enBulletin = entries[1].substring(0, 2) + "_" + entries[1].substring(2, 4) + "_" +
                    entries[1].substring(4, 6) + "_" + entries[1].substring(7);
                String frBulletin = entries[2].substring(0, 2) + "_" + entries[2].substring(2, 4) + "_" +
                    entries[2].substring(4, 6) + "_" + entries[2].substring(7);

                // let's write both files now
                bw.write("designation." + enBulletin + "=" + entries[0] + "\n");
                bw.write("designation." + frBulletin + "=" + entries[0] + "\n");
                bw2.write("designation.language." + entries[0] + "=" + entries[13] + "\n");

                bw.flush();
                bw2.flush();
            }
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError("The file: " + INPUT_FILE + " could not be read correctly at line: " +
                i + ".  No designation information could be used: " + e.getMessage());
        }
        finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(fw);
            IOUtils.closeQuietly(bw);
            IOUtils.closeQuietly(fw2);
            IOUtils.closeQuietly(bw2);
        }
    }
}
