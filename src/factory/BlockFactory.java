package factory;

import game.*;
import java.awt.Image;

public class BlockFactory {
    // Factory method for creating different types of blocks
    public static Block createBlock(BlockType type, int x, int y, int width, int height, Image img) {
        switch (type) {
            case SHIP -> {
                return new ShipBlock(x, y, width, height, img);
            }
            case ALIEN -> {
                return new AlienBlock(x, y, width, height, img);
            }
            case SHIELD -> {
                return new ShieldBlock(x, y, width, height, img);
            }
            case BULLET -> {
                return new BulletBlock(x, y, width, height, img);
            }
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}