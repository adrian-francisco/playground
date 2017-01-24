package merger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Handle CSV Files.
 *
 * @author Adrian Francisco
 */
public class CSVHandler implements Handler {

    /** The input file. */
    private File file;

    /**
     * Creates a new instance of CSVHandler.
     *
     * @param file the file
     */
    public CSVHandler(File file) {
        this.file = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Table<String, String, String> handle() {
        Table<String, String, String> table = TreeBasedTable.create();

        try (CSVReader reader =
                new CSVReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            List<String[]> entries = reader.readAll();

            if (entries == null || entries.isEmpty()) {
                return table;
            }

            // read the column headers
            String[] headers = entries.get(0);

            // find the id header location
            int id = 0;
            for (int j = 0; j < headers.length; j++) {
                if ("ID".equals(headers[j])) {
                    id = j;
                }
            }

            for (int i = 1; i < entries.size(); i++) {

                String[] row = entries.get(i);

                if (row.length == 0) {
                    continue;
                }

                for (int j = 0; j < row.length; j++) {
                    if (j == id) {
                        continue;
                    }

                    table.put(row[id], headers[j], row[j]);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return table;
    }

}
