package handlers;

import collisions.CollisionEvent;
import game.AlienBlock;
import game.SpaceInvaders;

// Handles movement of alien blocks
public class AlienMovementHandler extends CollisionHandler {

    @Override
    public void handle(CollisionEvent event) {
        if (event.getObject1() instanceof AlienBlock alien) {
            SpaceInvaders game = SpaceInvaders.getInstance();
            // End the game if the alien reaches the players ship
            if (alien.getY() >= game.getShip().getY()) {
                game.setGameOver(true);
            }
        }

        // Pass the event to the next handler (if it exists)
        if (nextHandler != null) {
            nextHandler.handle(event);
        }
    }
}
