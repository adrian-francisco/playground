package playground;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class DeepSeekPlay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String model = "deepseek-r1:latest";
            String statement = "";
            String response = "";

            System.out.println("Enter your prompt (or type 'exit' to quit):");
            statement = scanner.nextLine();
            if (statement.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                // Set up an HTTP POST request
                URL url = new URL("http://localhost:11434/api/generate");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Create the prompt
                var prompt = "Returning only positive, negative, or neutral, what is the sentiment of the following statement.\n" + statement;

                // Create request JSON
                JSONObject requestJson = new JSONObject();
                requestJson.put("model", model);
                requestJson.put("prompt", prompt);
                requestJson.put("stream", false);

                // Send request
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(requestJson.toString().getBytes());
                }

                // Get response
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String responseLine = br.readLine();
                    JSONObject responseJson = new JSONObject(responseLine);
                    var fullResponse = responseJson.getString("response");
                    response = fullResponse.replaceAll("(?s)<think>.*?</think>", "").trim();
                }

                System.out.println(response);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
