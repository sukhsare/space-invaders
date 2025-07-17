package game;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

// Handles loading and retrieving game resources like images
public class ResourceManager {
    private static final Map<String, Image> images = new HashMap<>();

    static {
        // Load images into the map for quick access
        images.put("ship", new ImageIcon(ResourceManager.class.getResource("/images/player.png")).getImage());
        images.put("alien", new ImageIcon(ResourceManager.class.getResource("/images/enemy.png")).getImage());
        images.put("shield", new ImageIcon(ResourceManager.class.getResource("/images/shield.png")).getImage());
    }

    // Retrieves an image based on the key
    public static Image getImage(String key) {
        return images.get(key);
    }
}