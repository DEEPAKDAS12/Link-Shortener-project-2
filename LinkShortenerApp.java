import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LinkShortenerApp {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    private Map<String, String> urlToShortMap;

    public LinkShortenerApp() {
        this.urlToShortMap = new HashMap<>();
    }

    public static void main(String[] args) {
        LinkShortenerApp app = new LinkShortenerApp();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            // Prompt user for long URL input
            System.out.print("Enter the long URL (or 'exit' to quit): ");
            String longUrl = scanner.nextLine();
    
            if (longUrl.equalsIgnoreCase("exit")) {
                break;
            }
    
            // Normalize the URL to lowercase before processing
            longUrl = normalizeUrl(longUrl);
    
            // Validate URL format (optional)
            if (!isValidUrl(longUrl)) {
                System.err.println("Error: Invalid URL format.");
                continue;
            }
    
            // Shorten the URL
            try {
                String shortUrl = shortenUrl(longUrl);
                System.out.println("Shortened URL: " + shortUrl);
            } catch (IllegalArgumentException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    
        System.out.println("Exiting Link Shortener App.");
    }
    
    private boolean isValidUrl(String url) {
        // Basic URL format validation (can be extended as per requirements)
        // This is a simple check and may not cover all edge cases of URL validation
        return url.matches("^https?://.*$");
    }
    

    private String shortenUrl(String longUrl) {
        // Check if the URL already exists in the map (normalized to lowercase)
        if (urlToShortMap.containsKey(longUrl)) {
            return urlToShortMap.get(longUrl); // Return existing short URL
        }

        // Generate short URL
        String shortUrl = generateRandomString(SHORT_URL_LENGTH);

        // Handle collisions (unlikely with this simple approach)
        while (urlToShortMap.containsValue(shortUrl)) {
            shortUrl = generateRandomString(SHORT_URL_LENGTH);
        }

        // Store mapping (normalized long URL to lowercase)
        urlToShortMap.put(longUrl, shortUrl);

        return shortUrl;
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private String normalizeUrl(String url) {
        // Convert the URL to lowercase
        return url.toLowerCase();
    }
}
