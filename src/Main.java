import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println("Visit the Nginx-served page at http://localhost/");

        // Check if the Nginx-served page is accessible
        try {
            URL url = new URL("http://localhost/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            if (responseCode == 200) {
                System.out.println("Nginx-served page is accessible!");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println("Page Content: " + content);
            } else {
                System.out.println("Failed to access the Nginx-served page. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Error while checking Nginx-served page: " + e.getMessage());
        }
    }
}
