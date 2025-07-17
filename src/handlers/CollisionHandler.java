package handlers;

import collisions.CollisionEvent;
import collisions.CollisionType;
import collisions.CollisionUtil;
import game.AlienBlock;
import game.Block;
import game.BulletBlock;
import game.ShieldBlock;
import game.ShipBlock;
import java.util.List;

public abstract class CollisionHandler {
    protected CollisionHandler nextHandler;

    // Sets the next handler in the chain
    public void setNextHandler(CollisionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Handles a collision event (to be implemented by subclasses)
    public abstract void handle(CollisionEvent event);

    // Checks for collisions between blocks and creates events for them
    public void checkCollisions(List<Block> blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = i + 1; j < blocks.size(); j++) {
                Block blockA = blocks.get(i);
                Block blockB = blocks.get(j);

                if (blockA.isAlive() && blockB.isAlive() && CollisionUtil.isColliding(blockA, blockB)) {
                    CollisionEvent event = new CollisionEvent(blockA, blockB, determineCollisionType(blockA, blockB));
                    handle(event); // Pass the event to the chain
                }
            }
        }
    }

    // Determines the type of collision based on the block types
    private CollisionType determineCollisionType(Block a, Block b) {
        if (isPair(a, b, BulletBlock.class, AlienBlock.class)) {
            return CollisionType.BULLET_ALIEN;
        }
        if (isPair(a, b, BulletBlock.class, ShieldBlock.class)) {
            return CollisionType.BULLET_SHIELD;
        }
        if (isPair(a, b, AlienBlock.class, ShipBlock.class)) {
            return CollisionType.ALIEN_SHIP;
        }
        return CollisionType.DEFAULT;
    }

    // Helper method to check if two blocks match specific types
    private boolean isPair(Block a, Block b, Class<?> classA, Class<?> classB) {
        return (classA.isInstance(a) && classB.isInstance(b)) || 
               (classA.isInstance(b) && classB.isInstance(a));
    }
}