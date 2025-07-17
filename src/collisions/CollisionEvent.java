// Class representing a collision event between two game objects

package collisions;

import game.Block;

public class CollisionEvent {
    private final Block object1; // First object in the collision
    private final Block object2; // Second object in the collision
    private final CollisionType type; // Type of collision


    // Constructor for a collision event
    public CollisionEvent(Block object1, Block object2, CollisionType type) {
        this.object1 = object1;
        this.object2 = object2;
        this.type = type;
    }

    // Gets the first object in the collision
    public Block getObject1() {
        return object1;
    }

    // Gets the second object in the collision
    public Block getObject2() {
        return object2;
    }

    // Gets the type of the collision
    public CollisionType getType() {
        return type;
    }
}