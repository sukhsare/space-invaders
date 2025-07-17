package events;

import game.GameState;

public class GameOverHandler extends EventHandler {
    @Override
    public void handleEvent(GameEvent event) {
        if (event.getType() == EventType.GAME_OVER) {
            // Set the game as over
            GameState.getInstance().setGameOver(true);
        } else if (nextHandler != null) {
            // Pass the event to the next handler in the chain
            nextHandler.handleEvent(event);
        }
    }
}