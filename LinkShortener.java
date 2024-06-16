import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LinkShortener {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private static final String STORAGE_FILE = "shortened_urls.txt";

    private Map<String, String> urlToShortMap;
    private ShortenedUrlStorage storage;

    public LinkShortener() {
        this.urlToShortMap = new HashMap<>();
        this.storage = new ShortenedUrlStorage();
        loadMappings(); // Load mappings from file on initialization
    }

    public String shortenUrl(String longUrl) {
        // Check if the URL already exists in the map
        if (urlToShortMap.containsKey(longUrl)) {
            return urlToShortMap.get(longUrl); // Return existing short URL
        }

        // Generate short URL
        String shortUrl = generateRandomString(SHORT_URL_LENGTH);

        // Handle collisions (unlikely with this simple approach)
        while (urlToShortMap.containsValue(shortUrl)) {
            shortUrl = generateRandomString(SHORT_URL_LENGTH);
        }

        // Store mapping
        urlToShortMap.put(longUrl, shortUrl);

        // Save mapping to file
        storage.saveMapping(longUrl, shortUrl);

        return shortUrl;
    }

    public String expandUrl(String shortUrl) {
        // Retrieve long URL from map
        return urlToShortMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(shortUrl))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null); // Return null if short URL not found
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private void loadMappings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STORAGE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    urlToShortMap.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading mappings from file: " + e.getMessage());
        }
    }
}
