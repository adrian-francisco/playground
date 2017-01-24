package merger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/**
 * The HTML Handler.
 *
 * @author Adrian Francisco
 */
public class HTMLHandler implements Handler {

    /** The input file. */
    private File file;

    /**
     * Creates a new instance of HTMLHandler.
     *
     * @param file the file
     */
    public HTMLHandler(File file) {
        this.file = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Table<String, String, String> handle() {
        Table<String, String, String> table = TreeBasedTable.create();

        Document document = null;

        try {
            document = Jsoup.parse(file, "UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
            return table;
        }

        Element directory = document.getElementById("directory");
        Elements rows = directory.select("tr");
        List<String> headers = new ArrayList<>();
        int idPosition = 0;

        // extract the header names and identify the id position
        for (Element columns : rows.get(0).select("th")) {
            String header = columns.text();

            if ("ID".equalsIgnoreCase(header)) {
                idPosition = headers.size();
            }

            headers.add(header);
        }

        // extract the table body
        for (int i = 1; i < rows.size(); i++) {
            Elements columns = rows.get(i).select("td");
            for (int j = 0; j < columns.size(); j++) {
                if (j == idPosition) {
                    continue;
                }
                String text = columns.get(j).text();
                table.put(columns.get(idPosition).text(), headers.get(j), text);
            }
        }

        return table;
    }
}
