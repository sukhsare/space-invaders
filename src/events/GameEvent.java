package events;

public class GameEvent {
    private final EventType type; // Type of event
    private final int points;     // Points related to event

    // Creates a new game event
    public GameEvent(EventType type, int points) {
        this.type = type;
        this.points = points;
    }

    // Gets the event type
    public EventType getType() {
        return type;
    }

    // Gets the points for the event
    public int getPoints() {
        return points;
    }
}