package game;

import java.awt.Image;

// Represents a shield block in the game, with durability
public class ShieldBlock extends Block {
    private int durability = 3; // Number of hits the shield can take, before being destroyed

    public ShieldBlock(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    // Reduces durability when the shield takes damage
    public void takeDamage() {
        durability--;
        if (durability <= 0) {
            this.alive = false; // Destroy the shield when durability reaches 0
        }
    }
}