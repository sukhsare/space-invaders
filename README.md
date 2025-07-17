# Space Invaders

Classic Space Invaders game built in Java with clean code architecture, implementing multiple design patterns including Factory, Chain of Responsibility, and Singleton.

## Design Patterns

- **Factory Pattern** - Clean object creation through BlockFactory
- **Chain of Responsibility** - Two chains handling collisions and events separately
- **Singleton Pattern** - Manages game state and instance control
- **Open/Closed Principle** - Add new features without breaking existing code
- **Single Responsibility** - Each class has one clear job

The collision detection uses bounding box algorithms, and the whole thing follows proper OOP principles. Check `manifest.txt` for the full technical breakdown of each class and pattern implementation.

## Getting Started

```bash
javac game/*.java
java game.App
```

**Controls:** Arrow keys to move, spacebar to shoot  
**Requirements:** Java 8+

## Architecture

Organised into focused packages:

```
game/           # Core game logic and objects
handlers/       # Input and collision processing
factory/        # Object creation
collisions/     # Detection utilities  
events/         # Game event system
rendering/      # Display components
```
