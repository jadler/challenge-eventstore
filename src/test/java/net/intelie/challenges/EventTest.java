package net.intelie.challenges;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EventTest {

    private static Stream<Arguments> testEqualsProvider() {

        Event event = new Event("LoginEvent", 1000);

        return Stream.of(
                Arguments.of(event, event, true),
                Arguments.of(event, null, false),
                Arguments.of(new Event("LoginEvent", 1000), new Event("LoginEvent", 1000), true),
                Arguments.of(new Event("LoginEvent", 1000), new Event("LoginEvent", 2000), false),
                Arguments.of(new Event("LogoutEvent", 2000), new Event("LoginEvent", 2000), false)
        );
    }

    @ParameterizedTest(name = "'{0}' equals '{1}' is expected to be '{2}'.")
    @MethodSource("testEqualsProvider")
    public void thisEquals(Event source, Event target, boolean expected) throws Exception {
        assertEquals(source.equals(target), expected);
    }

    private static Stream<Arguments> testHashCodeProvider() {
        return Stream.of(
                Arguments.of(new Event("LoginEvent", 2000), new Event("LoginEvent", 4000), true),
                Arguments.of(new Event("LoginEvent", 2000), new Event("LogoutEvent", 2000), false)
        );
    }

    @ParameterizedTest
    @MethodSource("testHashCodeProvider")
    public void thisHashCode(Event source, Event target, boolean expected) throws Exception {
        boolean result = source.hashCode() == target.hashCode();
        assertEquals(result, expected);
    }

}
