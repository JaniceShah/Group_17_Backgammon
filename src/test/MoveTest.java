package test;

import dto.Move;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveTest {

    @Test
    @DisplayName("Test Move Constructor and toString")
    void testMoveConstructorAndToString() {
        int source = 2;
        int destination = 5;
        Move move = new Move(source, destination);
        assertEquals(source, move.source);
        assertEquals(destination, move.destination);
        assertEquals("Move from " + (source + 1) + " to " + (destination + 1), move.toString());
    }
}
