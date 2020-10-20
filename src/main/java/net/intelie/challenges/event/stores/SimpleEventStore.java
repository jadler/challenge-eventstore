package net.intelie.challenges.event.stores;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import net.intelie.challenges.Event;
import net.intelie.challenges.EventIterator;
import net.intelie.challenges.EventStore;
import net.intelie.challenges.event.iterators.SimpleEventIterator;

/**
 * The in-memory event store implementation.
 *
 * @author Jaguaraquem A. Reinaldo
 */
public class SimpleEventStore implements EventStore {

    private final Map<String, Collection<Event>> map;

    public SimpleEventStore() {
        /*
         * From ConcurrentHashMap javadoc.
         *
         * ConcurrentHashMap do not throw ConcurrentModificationException.
         * However, iterators are designed to be used by only one thread at a
         * time.
         *
         * And from here <a href="https://en.wikipedia.org/wiki/Skip_list"/>
         * skip list based implementations
         *
         * "... can get the best features of a sorted array while maintaining
         * a linked list-like structure ..."
         */
        this.map = new ConcurrentSkipListMap<>();
    }

    @Override
    public void insert(Event event) {
        Collection<Event> collection = map.get(event.type());

        if (collection == null) {
            /*
             * A scalable concurrent NavigableSet implementation based on a
             * ConcurrentSkipListMap.
             */
            collection = new ConcurrentSkipListSet<>();
        }

        collection.add(event);
        map.put(event.type(), collection);
    }

    @Override
    public void removeAll(String type) {
        map.remove(type);
    }

    @Override
    public EventIterator query(String type, long start, long end) {
        Collection<Event> collection = map.get(type);
        if (collection == null) {
            return new SimpleEventIterator(Collections.emptyIterator());
        }

        Iterator<Event> iterator = collection.stream()
                .filter((event) -> event.timestamp() >= start && event.timestamp() < end)
                .iterator();

        return new SimpleEventIterator(iterator);
    }

}
