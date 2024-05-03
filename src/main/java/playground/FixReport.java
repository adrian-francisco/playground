package playground;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class FixReport {
    public static void main(String... args) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader("/Users/afrancisco/Downloads/report.csv"));
             CSVWriter writer = new CSVWriter(new FileWriter("/Users/afrancisco/Downloads/report.fixed.csv"))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                if (row[0].startsWith("Description")) {
                    continue;
                }
                String[] split = row[3].split("/");
                String date = split[2] + "/" + split[0] + "/" + split[1];
                String[] fixed = new String[6];
                fixed[0] = row[0];
                fixed[1] = row[1];
                fixed[2] = row[2];
                fixed[3] = date;
                fixed[4] = row[4];
                fixed[5] = row[5];
                writer.writeNext(fixed);
            }
        }
    }
}
