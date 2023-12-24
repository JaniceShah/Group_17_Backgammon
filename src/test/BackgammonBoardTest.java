package test;

import dto.Checkers;
import dto.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.BackgammonBoard;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackgammonBoardTest {

    @BeforeEach
    void setUp() {
        BackgammonBoard.initialize();
    }

    @Test
    @DisplayName("Test Is Game Over")
    void testIsGameOver() {
        BackgammonBoard.blackBearOffCheckers.clear();
        BackgammonBoard.whiteBearOffCheckers.clear();
        boolean isGameOver = BackgammonBoard.isGameOver();
        assertFalse(isGameOver);
    }

    @Test
    @DisplayName("Test Determine Winner")
    void testDetermineWinner() {
        BackgammonBoard.blackBearOffCheckers.clear();
        BackgammonBoard.whiteBearOffCheckers.clear();
        int winner = BackgammonBoard.determineWinner();
        assertEquals(0, winner);
    }
}

