package handlers;

import collisions.CollisionEvent;
import events.EventType;
import events.GameEvent;
import game.AlienBlock;
import game.BulletBlock;
import game.GameState;

// Handles collisions between bullets and aliens
public class BulletAlienHandler extends CollisionHandler {

    @Override
    public void handle(CollisionEvent event) {
        // Check if the event involves a bullet and an alien
        if (event.getObject1() instanceof BulletBlock bullet && event.getObject2() instanceof AlienBlock alien) {
            if (bullet.used || !alien.isAlive()) {
                return; // Skip if bullet is used or alien is destroyed
            }

            bullet.used = true;
            alien.setAlive(false);

            // Update score and trigger event
            int points = 60;
            GameState.getInstance().addScore(points);
            GameState.getInstance().triggerEvent(new GameEvent(EventType.SCORE_UPDATE, points));
        }

        // Pass the event to the next handler (if it exists)
        if (nextHandler != null) {
            nextHandler.handle(event);
        }
    }
}