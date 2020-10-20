package net.intelie.challenges;

/**
 * An abstraction of an event store.
 * <p>
 * Events may be stored in memory, data files, a database, on a remote server,
 * etc. For this challenge, you should implement an in-memory event event store.
 */
public interface EventStore {

    /**
     * Stores an event
     *
     * @param event
     */
    void insert(Event event);

    /**
     * Removes all events of specific type.
     *
     * @param type
     */
    void removeAll(String type);

    /**
     * Retrieves an iterator for events based on their type and timestamp.
     *
     * @param type The type we are querying for.
     * @param startTime Start timestamp (inclusive).
     * @param endTime End timestamp (exclusive).
     * @return An iterator where all its events have same type as {@code type}
     * and timestamp between {@code startTime} (inclusive) and {@code endTime}
     * (exclusive).
     */
    EventIterator query(String type, long startTime, long endTime);
}
