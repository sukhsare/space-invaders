package game;

import events.EventHandler;
import events.GameEvent;

public class GameState {
    private static GameState instance; // Singleton instance for global access
    private int score = 0;
    private boolean gameOver = false;
    private EventHandler eventChain; // Chain of event handlers for event processing

    private GameState() {}

    // Returns the singleton instance of GameState
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    // Gets the current score
    public int getScore() {
        return score;
    }

    // Adds points to the score
    public void addScore(int points) {
        score += points;
    }

    // Checks if the game is over
    public boolean isGameOver() {
        return gameOver;
    }

    // Sets the game over status
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // Sets the event chain
    public void setEventChain(EventHandler eventChain) {
        this.eventChain = eventChain;
    }

    // Passes the event through the chain of handlers
    public void triggerEvent(GameEvent event) {
        if (eventChain != null) {
            eventChain.handleEvent(event);
        }
    }
}