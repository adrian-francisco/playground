package merger;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/**
 * The RecordMerger main class.
 *
 * @author Adrian Francisco
 */
public class RecordMerger {

    /**
     * The output file.
     */
    public static final String FILENAME_COMBINED = "target/merger/combined.csv";

    /**
     * Entry point of this test.
     *
     * @param args command line arguments: first.html and second.csv.
     * @throws Exception bad things had happened.
     */
    public static void main(final String[] args) throws Exception {

        if (args.length == 0) {
            System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
            System.exit(1);
        }

        Table<String, String, String> combined = TreeBasedTable.create();

        for (String arg : args) {
            System.out.println("handling argument: " + arg);

            File file = new File(arg);
            Handler handler = null;

            if (file.getName().endsWith(".html")) {
                handler = new HTMLHandler(file);
            }
            else if (file.getName().endsWith(".csv")) {
                handler = new CSVHandler(file);
            }
            else if (file.getName().endsWith(".xml")) {
                handler = new XMLHandler(file);
            }
            else {
                System.out.println("skipping unsupported type: " + file);
                continue;
            }

            Table<String, String, String> single = handler.handle();

            if (single != null) {
                combined.putAll(single);
            }
            else {
                System.out.println("failed to generate table");
            }
        }

        String csv = toCSV(combined);
        File out = new File(FILENAME_COMBINED);
        FileUtils.writeStringToFile(out, csv, StandardCharsets.UTF_8);
        System.out.println("wrote to output file: " + out);
    }

    /**
     * Convert a table to csv.
     *
     * @param table the table
     * @return the string
     */
    private static String toCSV(Table<String, String, String> table) {
        if (table == null) {
            return "";
        }

        StringBuilder csv = new StringBuilder();

        // add the column headers
        csv.append("ID,");
        for (String header : table.columnKeySet()) {
            csv.append(header).append(",");
        }
        csv.deleteCharAt(csv.length() - 1);
        csv.append("\n");

        for (String row : table.rowKeySet()) {
            csv.append(row + ",");
            for (String column : table.columnKeySet()) {
                String cell = table.get(row, column);
                if (cell == null) {
                    csv.append(",");
                }
                else {
                    csv.append(cell).append(",");
                }
            }
            csv.deleteCharAt(csv.length() - 1);
            csv.append("\n");
        }
        return csv.toString();
    }
}
