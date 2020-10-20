package net.intelie.challenges.event.stores;

import net.intelie.challenges.EventStoreTest;

/**
 *
 * @author Jaguaraquem A. Reinaldo
 */
public class SimpleEventStoreTest extends EventStoreTest<SimpleEventStore> {

    @Override
    public SimpleEventStore create() {
        return new SimpleEventStore();
    }
}
