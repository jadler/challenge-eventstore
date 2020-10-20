package net.intelie.challenges;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * This abstract class is meant to be used by developers that want to test the
 * implementation of the {@link EventStore} interface.
 *
 * To ensure the contract's restrictions, classes that implement the
 * {@link EventStore} interface must have unit test classes that extend this
 * test class and implement the abstract {@link #create()} method.
 *
 * @author Jaguaraquem A. Reinaldo
 * @param <T>
 */
public abstract class EventStoreTest<T extends EventStore> {

    /**
     * Creates an instance of the {@link EventStore} implementation.
     *
     * @return An instance of the {@link EventStore} implementation.
     */
    protected abstract T create();

    @Test
    @DisplayName("Remove all events of a specific type from event store")
    public final void testRemoveAll() {
        EventStore store = create();

        store.insert(new Event("LoginEvent", 1000L));
        store.insert(new Event("RequestEvent", 2000L));
        store.insert(new Event("RequestEvent", 3000L));
        store.insert(new Event("RequestEvent", 4000L));
        store.insert(new Event("RequestEvent", 5000L));
        store.insert(new Event("RequestEvent", 6000L));
        store.insert(new Event("LogoutEvent", 7000L));

        store.removeAll("RequestEvent");

        assertEquals(1, count(store, "LoginEvent"));
        assertEquals(0, count(store, "RequestEvent"));
        assertEquals(1, count(store, "LogoutEvent"));
    }

    private int count(EventStore store, String type) {
        int counter = 0;
        EventIterator iterator = store.query(type, Long.MIN_VALUE, Long.MAX_VALUE);

        while (iterator.moveNext()) {
            counter++;
        }
        return counter;
    }

    private static Stream<Arguments> testQueryProvider() {
        return Stream.of(
                Arguments.of("RequestEvent", 2000, 4000, 1),
                Arguments.of("RequestEvent", Long.MIN_VALUE, Long.MAX_VALUE, 2),
                Arguments.of("RequestEvent", 2000, 2000, 0)
        );
    }

    @ParameterizedTest(name = "Requesting event '{0}' between '{1}' (inclusive) and '{2}' (exclusive) expect query '{3}' result(s).")
    @MethodSource("testQueryProvider")
    public final void testQuery(String event, long start, long end, int expected) {

        EventStore store = create();

        store.insert(new Event("LoginEvent", 2000));
        store.insert(new Event(event, 2000));
        store.insert(new Event("CriticalEvent", 3000));
        store.insert(new Event(event, 4000));
        store.insert(new Event("LogoutEvent", 6000));

        int counter = 0;
        EventIterator iterator = store.query(event, start, end);
        while (iterator.moveNext()) {
            assertEquals(event, iterator.current().type());
            counter++;
        }

        assertEquals(expected, counter);

    }
}
