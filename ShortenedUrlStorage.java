import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ShortenedUrlStorage {

    private static final String STORAGE_FILE = "shortened_urls.txt";

    public void saveMapping(String longUrl, String shortUrl) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STORAGE_FILE, true))) {
            writer.write(longUrl + "," + shortUrl);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving mapping to file: " + e.getMessage());
        }
    }
}
