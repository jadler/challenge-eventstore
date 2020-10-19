package net.intelie.challenges;

import java.util.Objects;

/**
 * This is just an event stub, feel free to expand it if needed.
 */
public class Event {

    private final String type;
    private final long timestamp;

    public Event(String type, long timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }

    public String type() {
        return type;
    }

    public long timestamp() {
        return timestamp;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Event other = (Event) obj;
        return Objects.equals(this.type, other.type)
                && this.timestamp == other.timestamp;
    }
}
