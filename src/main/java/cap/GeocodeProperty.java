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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Creates the geocode.properties file for the CAP PG component.
 *
 * @author  Jason Wong
 */
public class GeocodeProperty {

    /** The map of all the zones with the CLC key and their SGC codes. */
    private static final Map<String, List<String>> ZONES = new TreeMap<String, List<String>>();

    /** The input file. */
    private static final String INPUT_FILE = "src/main/resources/clc_to_sgc.txt";

    /** The output file. */
    private static final String OUTPUT_FILE = "src/main/resources/geocode.properties";

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

        int i = 0;

        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);

            String line = StringUtils.EMPTY;

            for (line = br.readLine(); line != null; line = br.readLine(), i++) {

                // skip if we have a problem, empty, or it's a comment in the list
                if ((line.trim().length() == 0) || line.contains("PROBLEM") || line.contains("EMPTY") ||
                        line.contains("#") || line.contains("INFO")) {
                    continue;
                }

                String[] entries = line.split(" ");

                // let's see if we even have anything, otherwise, skip
                if (entries == null) {
                    continue;
                }

                // if the first entry is empty, we know this is an SGC code without a CLC so we can ignore
                if ((entries[0] == null) || entries[0].trim().isEmpty()) {
                    continue;
                }

                // If it exists, we've already dealt with it and skip.  Otherwise, create a new array and process
                List<String> existingCLC = ZONES.get(entries[0]);

                if (existingCLC == null) {
                    existingCLC = new ArrayList<String>();
                }

                if (!existingCLC.isEmpty()) {
                    continue;
                }

                System.out.println("Handling line: " + line + " with list of entries: " + outputList(entries) +
                    " at line number: " + i);

                addSGCCodes(existingCLC, entries);
                ZONES.put(entries[0], existingCLC);
            }
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError("The file: " + INPUT_FILE + " could not be read correctly at line: " +
                i + ".  No zone geocode data could be used: " + e.getMessage());
        }
        finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(is);
        }

        // time to save to the output file with all the clc to sgc codes we've generated
        FileWriter fw = null;
        BufferedWriter bw = null;

        try {
            fw = new FileWriter(OUTPUT_FILE);
            bw = new BufferedWriter(fw);

            for (String clc : ZONES.keySet()) {

                bw.write("geocode." + clc + "=" + outputList(ZONES.get(clc)) + "\n");
                bw.flush();
            }
        }
        catch (IOException e) {
            throw new ExceptionInInitializerError("The file: " + INPUT_FILE + " could not be read correctly at line: " +
                i + ".  No zone geocode data could be used: " + e.getMessage());
        }
        finally {
            IOUtils.closeQuietly(fw);
            IOUtils.closeQuietly(bw);
        }
    }

    /**
     * Adds the SGC Codes from the entries to the list of SGC Codes container already defined.
     *
     * @param  sgcCodes  the list of SGC codes container.
     * @param  entries   the list of SGC codes to add to the container.
     */
    private static void addSGCCodes(List<String> sgcCodes, String[] entries) {

        int i = 0;

        // we need to ignore the first one as it's the CLC code and we only want the SGC codes
        for (String s : entries) {

            if (i == 0) {
                i++;

                continue;
            }

            if ((s != null) && !s.trim().isEmpty()) {
                sgcCodes.add(s);
            }
        }
    }

    /**
     * Creates a string that is properly formatted with a comma delimiter given an array of strings.
     *
     * @param   list  the array of input strings.
     *
     * @return  a string that is properly formatted with a comma delimiter.
     */
    private static String outputList(String[] list) {
        return outputList(Arrays.asList(list));
    }

    /**
     * Creates a string that is properly formatted with a comma delimiter given a list of strings.
     *
     * @param   input  the input list of strings.
     *
     * @return  a string that is properly formatted with a comma delimiter.
     */
    private static String outputList(List<String> input) {

        StringBuilder sb = new StringBuilder();

        for (String inputString : input) {
            sb.append(inputString);
            sb.append(",");
        }

        // delete the last comma if there's something generated
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
