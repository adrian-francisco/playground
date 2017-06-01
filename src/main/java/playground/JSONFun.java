package playground;

import java.io.FileReader;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class JSONFun {

    public static void main(String[] args) throws IOException {
        create();
        read();
    }

    private static void create() {
        JsonObject person = Json.createObjectBuilder().add("name", "Adrian").add("age", 30).add("isMarried", true)
                .add("address",
                        Json.createObjectBuilder().add("street", "Main Street").add("city", "New York")
                                .add("zipCode", "11111").build())
                .add("phoneNumber",
                        Json.createArrayBuilder().add("00-000-0000").add("11-111-1111").add("11-111-1112").build())
                .build();

        System.out.println(person);
    }

    private static void read() throws IOException {
        try (JsonReader reader = Json.createReader(new FileReader("src/main/resources/person.json"))) {
            JsonObject person = reader.readObject();
            System.out.println(person);
        }
    }
}
