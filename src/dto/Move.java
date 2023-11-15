package dto;

public class Move {
    public final int source;
    public final int destination;

    public Move(int source, int destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Move from " + (source + 1) + " to " + (destination + 1);
    }
}
