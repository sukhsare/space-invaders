package rendering;

import java.awt.Graphics;

public interface Renderable {
    void render(Graphics g);  // Method to render the object
    void update();  // Optional method to update the object (if needed)
}
