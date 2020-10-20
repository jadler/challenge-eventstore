package net.intelie.challenges;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This abstract class is meant to be used by developers that want to test the
 * implementation of the {@link EventIterator} interface.
 *
 * To ensure the contract's restrictions, classes that implement the
 * {@link EventIterator} interface must have unit test classes that extend this
 * test class and implement the abstract {@link #create()} and
 * {@link #createEmpty()} methods.
 *
 * @author Jaguaraquem A. Reinaldo
 * @param <T>
 */
public abstract class EventIteratorTest<T extends EventIterator> {

    /**
     * Creates an instance of the {@link EventIterator} implementation.
     *
     * This method expect that the iterator has at least one element.
     *
     * @return An instance of the {@link EventIterator} implementation.
     */
    protected abstract T create();

    /**
     * This method is used to test the behaviour of an empty iterator
     *
     * @return An empty iterator.
     */
    protected abstract T createEmpty();

    @Test
    @DisplayName("Call moveNext() on an empty iterator")
    public final void testMoveNextEmptyIterator() {
        assertFalse(createEmpty()::moveNext);
    }

    @Test
    @DisplayName("Assert current() and remove() not throw IllegalStateException while moveNext() equals true")
    public final void testIterate() {
        EventIterator iterator = create();
        while (iterator.moveNext()) {
            assertNotNull(iterator.current());
            iterator.remove();
        }
    }

    @Test
    @DisplayName("Test IllegalStateException when calling current() without calling moveNext().")
    @SuppressWarnings("ThrowableResultIgnored")
    public final void testCurrentWithoutMoveNext() {
        EventIterator iterator = create();
        assertThrows(IllegalStateException.class, iterator::current);
    }

    @Test
    @DisplayName("Test IllegalStateException when calling current() after reached the end.")
    @SuppressWarnings("ThrowableResultIgnored")
    public final void testCurrentAfterReachEnd() {
        EventIterator iterator = create();
        while (iterator.moveNext()) {
            // Just to reach the end
        }

        assertFalse(iterator.moveNext());
        assertThrows(IllegalStateException.class, iterator::current);
    }

    @Test
    @DisplayName("Test IllegalStateException when calling remove() without calling moveNext().")
    @SuppressWarnings("ThrowableResultIgnored")
    public final void testRemoveWithoutMoveNext() {
        EventIterator iterator = create();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    @DisplayName("Test IllegalStateException when calling remove() after reached the end.")
    @SuppressWarnings("ThrowableResultIgnored")
    public final void testRemoveAfterReachEnd() {
        EventIterator iterator = create();
        while (iterator.moveNext()) {
            // Just to reach the end
        }

        assertFalse(iterator.moveNext());
        assertThrows(IllegalStateException.class, iterator::remove);
    }

}
