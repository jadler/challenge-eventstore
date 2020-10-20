package net.intelie.challenges.event.iterators;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import net.intelie.challenges.Event;
import net.intelie.challenges.EventIteratorTest;

/**
 *
 * @author Jaguaraquem A. Reinaldo
 */
public class SimpleEventIteratorTest extends EventIteratorTest<SimpleEventIterator> {

    Collection<Event> collection = new HashSet<>();

    public SimpleEventIteratorTest() {
        collection.add(new Event("LoginEvent", 1000));
        collection.add(new Event("LogoutEvent", 2000));
    }

    @Override
    protected SimpleEventIterator create() {
        return new SimpleEventIterator(collection.iterator());
    }

    @Override
    protected SimpleEventIterator createEmpty() {
        return new SimpleEventIterator(Collections.emptyIterator());
    }
}
