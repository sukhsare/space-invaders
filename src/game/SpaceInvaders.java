package game;
import collisions.CollisionUtil;
import events.EventHandler;
import events.EventType;
import events.GameEvent;
import events.GameOverHandler;
import events.ScoreUpdateHandler;
import factory.BlockFactory;
import factory.BlockType;
import handlers.AlienMovementHandler;
import handlers.BulletAlienHandler;
import handlers.BulletShieldHandler;
import handlers.CollisionHandler;
import handlers.InputHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import rendering.Renderable;


public final class SpaceInvaders extends JPanel implements ActionListener {
    
    // Singleton instance
    private static SpaceInvaders instance;

    // Board dimensions
    private final int tileSize = 32;
    private final int rows = 28;
    private final int columns = 28;
    private final int boardWidth = tileSize * columns;
    private final int boardHeight = tileSize * rows;

    // Images
    private final Image alienImg, bollardImg;
    
    // Game objects
    private ShipBlock ship;
    private final ArrayList<AlienBlock> alienArray;
    private final ArrayList<BulletBlock> bulletArray;
    private final ArrayList<ShieldBlock> shieldArray;
    
    // Collision handler
    private final CollisionHandler collisionHandler;

    // Player properties
    private final int shipWidth = tileSize * 2;
    private final int shipHeight = tileSize;
    private final int shipVelocityX = tileSize / 10;
    private final int playerLives = 3;
    private final int shipX = tileSize * columns / 2 - tileSize;
    private final int shipY = boardHeight - tileSize * 2;

    // Alien properties
    private final int alienWidth = tileSize * 2;
    private final int alienHeight = tileSize;
    private int alienColumns = 8;
    private int alienRows = 3;
    private int alienCount = 0;
    private int alienVelocityX = 1;
                    
    // Bullet properties
    private final int bulletWidth = tileSize / 8;
    private final int bulletHeight = tileSize / 2;
    private final int bulletVelocityY = -10;

    // Shield properties
    private final int shieldHeight = tileSize * 2;

    // Declare the InputHandler
    private final InputHandler inputHandler;  

    // Input flags
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private final boolean spacePressed = false;
    
    // Game state
    private final Timer gameLoop;
    private boolean gameOver = false;

    private final List<Block> blocks = new ArrayList<>(); // Holds all game objects
    
    // Returns the singleton instance of SpaceInvaders
    public static SpaceInvaders getInstance() { return instance; }      
    
    public SpaceInvaders() {
        // Set the instance to the current object (for singleton access)
        instance = this;
    
        // Set up the game window
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.black);
        setFocusable(true);
    
        // Initialise InputHandler and add it as a key listener
        inputHandler = new InputHandler(this);
        addKeyListener(inputHandler);
    
        // Load resources
        alienImg = ResourceManager.getImage("alien");
        bollardImg = ResourceManager.getImage("shield");
    
        // Initialise game object lists
        alienArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        shieldArray = new ArrayList<>();
    
        // Set up the game loop
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    
        // Initialise collision handlers and build the chain of responsibility
        BulletAlienHandler bulletAlienHandler = new BulletAlienHandler();
        BulletShieldHandler bulletShieldHandler = new BulletShieldHandler();
        AlienMovementHandler alienMovementHandler = new AlienMovementHandler();
        bulletAlienHandler.setNextHandler(bulletShieldHandler);
        bulletShieldHandler.setNextHandler(alienMovementHandler);
        collisionHandler = bulletAlienHandler;  // Set the starting point of the chain
    
        // Initialise event handlers for game over and score update
        EventHandler scoreUpdateHandler = new ScoreUpdateHandler();
        scoreUpdateHandler.setNextHandler(new GameOverHandler());
        GameState.getInstance().setEventChain(scoreUpdateHandler);
    
        // Initialise game objects (ship, aliens, shields)
        initialiseGameObjects();
    }
    

    // Method to create shields
    private void createShields() {
        if (!shieldArray.isEmpty()) return;  // If shields exist, return

        int shieldStartX = boardWidth / 9;  // Starting X position for shields
        int shieldSpacing = tileSize * 10;

        for (int i = 0; i < 3; i++) {
            // Create shield block
            ShieldBlock shield = (ShieldBlock) BlockFactory.createBlock(
                BlockType.SHIELD,
                shieldStartX + i * shieldSpacing,
                shipY - 2 * shieldHeight,  // Position above the players ship
                tileSize * 3,              // Width of shield
                tileSize * 2,              // Height of shield
                bollardImg                 // Shield image
            );
            shieldArray.add(shield);  // Add shield to shieldArray
        }
    }
     
    
    public void fireBullet() {
        // Create a bullet at the center of the ship
        BulletBlock bullet = (BulletBlock) BlockFactory.createBlock(
            BlockType.BULLET,
            ship.getX() + (ship.getWidth() - bulletWidth) / 2,  // Center horizontally
            ship.getY() - bulletHeight,                         // Position above the ship
            bulletWidth,
            bulletHeight,
            null // No image for bullet
        );
        bulletArray.add(bullet);  // Add bullet to array
    }
    
        
    public void createAliens() {
        alienArray.clear();  // Remove any existing aliens
    
        // Create aliens for the remaining rows
        for (int r = 1; r < alienRows; r++) {  // Start from row 1 (skip row 0)
            for (int c = 0; c < alienColumns; c++) {
                AlienBlock alien = (AlienBlock) BlockFactory.createBlock(
                    BlockType.ALIEN,
                    c * alienWidth + tileSize,
                    r * alienHeight + tileSize,
                    alienWidth,
                    alienHeight,
                    alienImg
                );
                alienArray.add(alien);  // Add each alien to the array
            }
        }
    
        alienCount = alienArray.size();  // Set alien count
    }
    

    private void initialiseGameObjects() {
        // Create the player ship if it doesn't exist
        if (ship == null) {
            ship = new ShipBlock(shipX, shipY, shipWidth, shipHeight, ResourceManager.getImage("ship"));
            blocks.add(ship);  // Add player ship to the blocks list
        }

        // Create aliens if they haven't been created yet
        if (alienArray.isEmpty()) {
            createAliens();
        }

        // Create shields if they haven't been created yet
        if (shieldArray.isEmpty()) {
            createShields();
        }
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        // Create list of renderable objects
        List<Renderable> renderables = new ArrayList<>();
        renderables.add(ship);
        renderables.addAll(alienArray);
        renderables.addAll(bulletArray);
        renderables.addAll(shieldArray);
    
        // Render and update all objects
        for (Renderable renderable : renderables) {
            renderable.render(g);
            renderable.update();
        }
    
        // Display score and lives
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        g.drawString("Score: " + GameState.getInstance().getScore(), 10, 35);
        g.drawString("Lives: " + playerLives, boardWidth - 200, 35);
    
        // Display "Game Over" message if the game is over
        if (GameState.getInstance().isGameOver()) {
            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 64));
            g.drawString("GAME OVER", boardWidth / 2 - 200, boardHeight/4);
        }
    }
    

    public void move() {
        handleInput();  // Handle player input
    
        // Create list of all blocks for collision checks
        List<Block> blocks = new ArrayList<>(alienArray);
        blocks.addAll(bulletArray);
        blocks.addAll(shieldArray);
        blocks.add(ship);
    
        // Check for collisions
        collisionHandler.checkCollisions(blocks);
    
        // Update all block positions
        blocks.forEach(Block::update);
    
        // Move ship and fire bullets based on input
        if (leftPressed) ship.moveLeft(2, getWidth());
        if (rightPressed) ship.moveRight(2, getWidth());
        if (spacePressed) fireBullet();
    
        // Start new wave if all aliens are destroyed
        if (alienCount == 0) {
            GameState.getInstance().triggerEvent(new GameEvent(EventType.SCORE_UPDATE, alienColumns * alienRows * 60));
            startNewWave();
            return;
        }
    
        // Move aliens and check game over condition
        for (AlienBlock alien : alienArray) {
            if (!alien.alive) continue;
            
            // Move alien horizontally
            alien.setX(alien.getX() + alienVelocityX);
    
            // Reverse direction and move aliens down if necessary
            if (alien.getX() <= 0 || alien.getX() + alien.getWidth() >= boardWidth) {
                alienVelocityX *= -1;
                alienArray.forEach(a -> a.y += alienHeight);  // Move aliens down
                break;
            }
    
            // Game over if any alien reaches the ship
            if (alien.getY() >= ship.getY()) {
                GameState.getInstance().triggerEvent(new GameEvent(EventType.GAME_OVER, 0));
                break;
            }
        }
    
        // Move bullets and check for collisions
        for (int i = 0; i < bulletArray.size(); i++) {
            BulletBlock bullet = bulletArray.get(i);
            if (!bullet.used) {
                bullet.setY(bullet.getY() + bulletVelocityY);  // Move bullet upwards
    
                // Mark bullet as used if out of bounds
                if (bullet.getY() < 0) {
                    bullet.used = true;
                    continue;
                }
    
                // Check for bullet-alien collision
                for (int j = 0; j < alienArray.size(); j++) {
                    AlienBlock alien = alienArray.get(j);
                    if (alien.alive && CollisionUtil.isColliding(bullet, alien)) {
                        bullet.used = true;
                        alien.alive = false;
                        alienArray.remove(j);
                        alienCount--;
                        GameState.getInstance().triggerEvent(new GameEvent(EventType.SCORE_UPDATE, 60));
                        break;
                    }
                }
            }
        }
    
        // Remove used bullets
        bulletArray.removeIf(bullet -> bullet.used);
    }
    
    
    private void startNewWave() {
    // Increase alien rows and columns, with max limits
    alienColumns = Math.min(alienColumns + 1, columns / 2 - 2);
    alienRows = Math.min(alienRows + 1, rows - 6);
    
    // Reset alien velocity and increase difficulty
    alienVelocityX = Math.abs(alienVelocityX) + 1;
    
    // Clear old aliens and bullets, create new wave
    alienArray.clear();
    bulletArray.clear();
    createAliens();
    
    // Update alien count
    alienCount = alienArray.size();
}

    
public void handleInput() {
    // Move ship based on key presses
    if (leftPressed) {
        ship.moveLeft(shipVelocityX, boardWidth);
    } else if (rightPressed) {
        ship.moveRight(shipVelocityX, boardWidth);
    }

    // Fire bullet if space is pressed
    if (spacePressed) {
        fireBullet();
    }
}


   // Getter for the ship
public ShipBlock getShip() {
    return ship;
}

// Setter for gameOver state
public void setGameOver(boolean gameOver) {
    this.gameOver = gameOver;
}

// Getter for gameOver state
public boolean isGameOver() {
    return gameOver;
}

@Override
public void actionPerformed(ActionEvent e) {
    move();
    repaint();
}

// Move the ship left or right based on input
public void moveShipLeft(boolean move) {
    leftPressed = move;
}

public void moveShipRight(boolean move) {
    rightPressed = move;
}

// Fire a bullet if the flag is set
public void fireBullet(boolean fire) {
    if (fire) {
        BulletBlock bullet = new BulletBlock(
            ship.getX() + ship.getWidth() / 2, 
            ship.y, 
            5, 
            10, 
            null
        );
        bulletArray.add(bullet);
    }
}


}