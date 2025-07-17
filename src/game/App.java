package game;

import javax.swing.JFrame;

public class App {

    public static void main(String[] args) {
        // Create a window to display the game
        JFrame frame = new JFrame("Space Invaders");

        // Create the game panel
        SpaceInvaders game = new SpaceInvaders();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the game when the window is closed
        frame.setResizable(false); // Prevent resizing the window
        frame.add(game); // Add the game panel to the window
        frame.pack(); // Adjust window size to fit the game panel
        frame.setVisible(true); // Show the window
    }
}