package collisions;

import game.Block;

public class CollisionUtil {

    // Checks if two blocks are colliding using AABB logic
    // Returns true if the blocks overlap, otherwise false
    public static boolean isColliding(Block a, Block b) {
        return a.getX() < b.getX() + b.getWidth() &&
               a.getX() + a.getWidth() > b.getX() &&
               a.getY() < b.getY() + b.getHeight() &&
               a.getY() + a.getHeight() > b.getY();
    }
}