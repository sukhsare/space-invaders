package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class BulletBlock extends Block {
    public boolean used = false;

    // Constructor for setting position, size, and image of the bullet
    public BulletBlock(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);  // Pass values to the parent class constructor
    }

    @Override
    public void render(Graphics g) {
        if (!used) {
            g.setColor(Color.white);
            g.fillRect(getX(), getY(), getWidth(), getHeight());  // Draw the bullet as a white rectangle
        }
    }

    @Override
    public void update() { 
        // No update for now, can be added if needed later
    }
}