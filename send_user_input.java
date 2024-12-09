import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// This program collects basic strings from user and sends those to server. 

public class SendUserInput {
    public static void main(String[] args) {
        // URL
        String serverURL = "http://???????/api/data";

        // Read input from user
        Scanner scanner = new Scanner(System.in);
        String userInput;

        System.out.println("Give data to server (write 'stop' to end execution):");

        while (true) {
            System.out.print("Give data: ");
            userInput = scanner.nextLine();

            // Check if stop is requested
            if (userInput.equalsIgnoreCase("stop")) {
                break;
            }

            // Send data
            try {
                URL url = new URL(serverURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                String jsonData = "{\"data\": \"" + userInput + "\"}";
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(jsonData.getBytes());
                    os.flush();
                }

                // Response from server
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Data is sent.");
                } else {
                    System.out.println("Error in server: " + responseCode);
                }

                connection.disconnect();
            } catch (Exception e) {
                System.out.println("Error sending data: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
