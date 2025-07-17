package events;

import game.GameState;

public class ScoreUpdateHandler extends EventHandler {
    @Override
    public void handleEvent(GameEvent event) {
        if (event.getType() == EventType.SCORE_UPDATE) {
            // Add points to the game score
            GameState.getInstance().addScore(event.getPoints());
        } else if (nextHandler != null) {
            // Pass the event to the next handler in the chain
            nextHandler.handleEvent(event);
        }
    }
}