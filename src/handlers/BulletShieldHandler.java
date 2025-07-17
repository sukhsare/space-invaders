package handlers;

import collisions.CollisionEvent;
import game.BulletBlock;
import game.ShieldBlock;

// Handles collisions between bullets and shields
public class BulletShieldHandler extends CollisionHandler {

    @Override
    public void handle(CollisionEvent event) {
        // Check if the event involves a bullet and a shield
        if (event.getObject1() instanceof BulletBlock bullet && event.getObject2() instanceof ShieldBlock shield) {
            if (bullet.used || !shield.isAlive()) {
                return; // Skip if bullet is used or the shield is destroyed
            }
            bullet.used = true;
            shield.takeDamage(); // Reduce shield durability
        }

        // Pass the event to the next handler in the chain (if it exists)
        if (nextHandler != null) {
            nextHandler.handle(event);
        }
    }
}