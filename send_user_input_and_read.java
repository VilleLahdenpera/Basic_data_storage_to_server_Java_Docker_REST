import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class send_user_input_and_read {
    public static void main(String[] args) {
        // URLs for server GET and POST
        String serverUrlPost = "http://****/api/data";
        String serverUrlGet = "http://****/api/data";

        // Read user
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Choose action:");
        System.out.println("1: Send data");
        System.out.println("2: Read data");
        System.out.println("0: Stop");

        while (true) {
            System.out.print("\nChoose: ");
            syote = scanner.nextLine();

            if (syote.equals("0")) {
                System.out.println("Bye!.");
                break;
            }

            switch (input) {
                case "1":
                    // Send data
                    System.out.print("Give data to send: ");
                    String data = scanner.nextLine();

                    try {
                        URL url = new URL(serverUrlPost);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoOutput(true);

                        // Data in JSON-format
                        String jsonData = "{\"data\": \"" + data + "\"}";
                        try (OutputStream os = connection.getOutputStream()) {
                            os.write(jsonData.getBytes());
                            os.flush();
                        }

                        // Check response
                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            System.out.println("Data sent succesfully.");
                        } else {
                            System.out.println("Error in server: " + responseCode);
                        }

                        onnection.disconnect();
                    } catch (Exception e) {
                        System.out.println("Error in sending the data: " + e.getMessage());
                    }
                    break;

                case "2":
                    // Read data
                    System.out.println("Getting data from the server...");
                    try {
                        URL url = new URL(palvelimenUrlGet);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");

                        // Check response
                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            try (BufferedReader in = new BufferedReader(new InputStreamReader(connnection.getInputStream()))) {
                                StringBuilder response = new StringBuilder();
                                String line;
                                while ((line = in.readLine()) != null) {
                                    response.append(line);
                                }
                                System.out.println("Data from the server:");
                                System.out.println(response);
                            }
                        } else {
                            System.out.println("Error in server: " + responseCode);
                        }

                        connection.disconnect();
                    } catch (Exception e) {
                        System.out.println("Error reading the data from the server: " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("Give 0 ,1 or 2 as input!.");
                    break;
            }
        }

        scanner.close();
    }
}
