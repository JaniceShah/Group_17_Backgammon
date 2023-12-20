package test;

import dto.Checkers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.BackgammonBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckersTest {

    private Checkers checkers;

    @BeforeEach
    void setUp() {
        // Initialize a Checkers object before each test
        checkers = new Checkers();
    }

    @Test
    void getColor() {
        // Test getColor method
        checkers.setColor(BackgammonBoard.colors.Black);
        assertEquals(BackgammonBoard.colors.Black, checkers.getColor());
    }

    @Test
    void getNumberOfChecker() {
        // Test getNumberOfChecker method
        checkers.setNumberOfChecker(3);
        assertEquals(3, checkers.getNumberOfChecker());
    }

    @Test
    void setColor() {
        // Test setColor method
        checkers.setColor(BackgammonBoard.colors.White);
        assertEquals(BackgammonBoard.colors.White, checkers.getColor());
    }

    @Test
    void setNumberOfChecker() {
        // Test setNumberOfChecker method
        checkers.setNumberOfChecker(5);
        assertEquals(5, checkers.getNumberOfChecker());
    }

    @Test
    void testToString() {
        // Test toString method
        checkers.setColor(BackgammonBoard.colors.Black);
        assertEquals(" O ", checkers.toString());

        checkers.setColor(BackgammonBoard.colors.White);
        assertEquals(" X ", checkers.toString());
    }
}

