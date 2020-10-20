package net.intelie.challenges.event.iterators;

import java.util.Iterator;
import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;

/**
 *
 * @author Jaguaraquem A. Reinaldo
 */
public class SimpleEventIterator implements EventIterator {

    private final Iterator<Event> iterator;
    private Event current = null;

    public SimpleEventIterator(Iterator<Event> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean moveNext() {
        current = iterator.hasNext() ? iterator.next() : null;
        return current != null;
    }

    @Override
    public Event current() {
        if (current == null) {
            throw new IllegalStateException("Event not available, iteration not initialized or reached the end.");
        }

        return this.current;
    }

    @Override
    public void remove() {
        if (current == null) {
            throw new IllegalStateException("Could not remove event, iteration not initialized or reached the end.");
        }

        this.iterator.remove();
    }

    @Override
    public void close() throws Exception {
        // Does nothing
    }
}
