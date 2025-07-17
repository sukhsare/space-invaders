package game;

import java.awt.Graphics;
import java.awt.Image;
import rendering.Renderable;

public abstract class Block implements Renderable {
    // Abstract class representing a block in the game, used as a base for different block types
    private int x;
    int y;
    private int width;
    private int height;
    protected boolean alive = true;
    private Image img;

    // Constructor for setting position, size and image
    public Block(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    // Overloaded constructor for blocks without an image
    public Block(int x2, int y2, int width2, int height2) {
    }

    @Override
    public void render(Graphics g) {
        if (alive && img != null) {
            g.drawImage(img, x, y, width, height, null);
        }
    }

    @Override
    public void update() {
        // Default update logic
    }

    // Getters and setters for encapsulated fields
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}