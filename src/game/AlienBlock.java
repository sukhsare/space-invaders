package game;

import java.awt.Graphics;
import java.awt.Image;

public class AlienBlock extends Block {

    // Sets the position, size, and image for the alien
    public AlienBlock(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);  // Pass values to the parent class
    }

    @Override
    public void render(Graphics g) {
        // Draw the alien only if its alive
        if (isAlive()) {
            g.drawImage(getImg(), getX(), getY(), getWidth(), getHeight(), null);  // Draw the aliens image
        }
    }

    @Override
    public void update() {
    // Currently no update logic, can be added in the future
}
}