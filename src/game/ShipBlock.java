package game;

import java.awt.Graphics;
import java.awt.Image;

public class ShipBlock extends Block {

    public ShipBlock(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    @Override
    public void render(Graphics g) {
        if (alive) {
            g.drawImage(getImg(), getX(), getY(), getWidth(), getHeight(), null);  // Draw the ship if alive
        }
    }

    @Override
    public void update() { }

    // Move the ship left (ensuring it stays on screen)
    public void moveLeft(int velocity, int boardWidth) {
        if (getX() - velocity >= 0) {  // Prevent going off the left edge
            setX(getX() - velocity);
        }
    }

    // Move the ship right (ensuring it stays on screen)
    public void moveRight(int velocity, int boardWidth) {
        if (getX() + getWidth() + velocity <= boardWidth) {  // Prevent going off the right edge
            setX(getX() + velocity);
        }
    }
}