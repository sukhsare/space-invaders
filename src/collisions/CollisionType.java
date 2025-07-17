package collisions;

// Enum representing different types of collisions in the game
public enum CollisionType {
    BULLET_ALIEN,   // Bullet hits an alien
    BULLET_SHIELD,  // Bullet hits a shield
    ALIEN_SHIP,     // Alien collides with the ship
    DEFAULT         // Default collision type for others
}