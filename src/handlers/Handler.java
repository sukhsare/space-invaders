package handlers;

import collisions.CollisionEvent;

// Abstract class for handling events in a chain of responsibility
public abstract class Handler {
    protected final Handler nextHandler; // Next handler in the chain

    // Constructor to set the next handler in the chain
    public Handler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Subclasses must implement this to handle the event
    public abstract void handle(CollisionEvent event);
}
