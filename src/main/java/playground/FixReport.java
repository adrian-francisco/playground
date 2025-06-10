package playground;

import java.io.FileReader;

import com.opencsv.CSVReader;

public class FixReport {
    public static void main(String... args) throws Exception {
        try (var reader = new CSVReader(new FileReader("/Users/afrancisco/Downloads/report.csv"))) {
            var rows = reader.readAll();
            for (var row : rows) {
                if (row[0].startsWith("Description")) {
                    continue;
                }
                var description = row[0];
                var type = row[1];
                var name = row[2];
                var split = row[3].split("/");
                var date = split[2] + "/" + split[0] + "/" + split[1];
                var amount = Double.parseDouble(row[5]);

                type = switch (type) {
                    case "PURCHASE" -> "PUR";
                    case "REFUND" -> "RFD";
                    case "PAYMENT" -> "PAY";
                    default -> type;
                };

                name = switch (name) {
                    case "ADRIAN" -> "Adr";
                    case "CHARLENE" -> "Char";
                    default -> name;
                };

                var fixed = new String[11];
                fixed[0] = date;
                fixed[1] = "";
                fixed[2] = description;
                fixed[3] = type;
                fixed[4] = name;
                fixed[5] = "";
                fixed[6] = "";
                fixed[7] = "";
                fixed[8] = "";
                fixed[9] = amount <= 0 ? -amount + "" : "";
                fixed[10] = amount > 0 ? amount + "" : "";

                System.out.println(String.join(",", fixed));
            }
        }
    }
}
