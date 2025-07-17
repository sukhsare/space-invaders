package handlers;

import game.SpaceInvaders;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private final SpaceInvaders game;  // Reference to main game class

    // Constructor to initialise the InputHandler with the game instance
    public InputHandler(SpaceInvaders game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle left/right movement and firing bullets
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> game.moveShipLeft(true);  // Move ship left
            case KeyEvent.VK_RIGHT -> game.moveShipRight(true);  // Move ship right
            case KeyEvent.VK_SPACE -> game.fireBullet(true);  // Fire a bullet
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Stop moving the ship or stop firing bullets
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> game.moveShipLeft(false);  // Stop moving ship left
            case KeyEvent.VK_RIGHT -> game.moveShipRight(false);  // Stop moving ship right
            case KeyEvent.VK_SPACE -> game.fireBullet(false);  // Stop firing bullets
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
