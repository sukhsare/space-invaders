package events;

public abstract class EventHandler {
    protected EventHandler nextHandler; // Next handler in the chain

    // Sets the next handler in the chain
    public void setNextHandler(EventHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Abstract method to handle an event, which is to be implemented by subclasses
    public abstract void handleEvent(GameEvent event);
}