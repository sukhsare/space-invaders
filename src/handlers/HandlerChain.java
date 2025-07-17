package handlers;

import collisions.CollisionEvent;
import collisions.CollisionType;
import collisions.CollisionUtil;
import game.AlienBlock;
import game.Block;
import game.BulletBlock;
import game.ShieldBlock;
import game.ShipBlock;
import java.util.ArrayList;
import java.util.List;

public class HandlerChain {
    private final List<CollisionHandler> handlers = new ArrayList<>();

    // Add a handler to the chain
    public void addHandler(CollisionHandler handler) {
        if (!handlers.isEmpty()) {
            handlers.get(handlers.size() - 1).setNextHandler(handler);
        }
        handlers.add(handler);
    }

    // Process a collision event through the chain
    public void processEvent(CollisionEvent event) {
        if (!handlers.isEmpty()) {
            handlers.get(0).handle(event); // Start processing from the first handler
        }
    }

    // Check collisions between all blocks and create collision events
    public void checkCollisions(List<Block> blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            for (int j = i + 1; j < blocks.size(); j++) {
                Block blockA = blocks.get(i);
                Block blockB = blocks.get(j);

                // Check if both blocks are alive and colliding
                if (blockA.isAlive() && blockB.isAlive() && CollisionUtil.isColliding(blockA, blockB)) {
                    // Create a collision event and set the correct CollisionType
                    CollisionEvent event = new CollisionEvent(blockA, blockB, determineCollisionType(blockA, blockB));
                    processEvent(event); // Pass the event to the handler chain
                }
            }
        }
    }

    // Determines the type of collision
    private CollisionType determineCollisionType(Block a, Block b) {
        // Determine collision type here (can be expanded based on your game logic)
        if (a instanceof BulletBlock && b instanceof AlienBlock) {
            return CollisionType.BULLET_ALIEN;
        }
        if (a instanceof BulletBlock && b instanceof ShieldBlock) {
            return CollisionType.BULLET_SHIELD;
        }
        if (a instanceof AlienBlock && b instanceof ShipBlock) {
            return CollisionType.ALIEN_SHIP;
        }
        return CollisionType.DEFAULT;
    }
}
