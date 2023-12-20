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
    @DisplayName("Test Apply Move")
    void testApplyMove() {
        Move move = new Move(8, 13); // Move a checker from point 8 to point 13
        BackgammonBoard.applyMove(move);
        List<Checkers> destinationList = BackgammonBoard.checkersPosition.get(13);
        assertFalse(destinationList.isEmpty());
        assertEquals(1, destinationList.size());
        assertEquals(move.source, destinationList.get(0).getNumberOfChecker() - 1);
    }

    @Test
    @DisplayName("Test Apply End Moves")
    void testApplyEndMoves() {
        Move move = new Move(23, 24); // Move a checker from point 23 to point 24
        BackgammonBoard.applyEndMoves(move);
        List<Checkers> destinationList = BackgammonBoard.checkersPosition.get(24);
        assertFalse(destinationList.isEmpty());
        assertEquals(1, destinationList.size());
    }

    @Test
    @DisplayName("Test Is Game Over")
    void testIsGameOver() {
        BackgammonBoard.blackOutCheckers.clear();
        BackgammonBoard.whiteOutCheckers.clear();
        boolean isGameOver = BackgammonBoard.isGameOver();
        assertFalse(isGameOver);
    }

    @Test
    @DisplayName("Test Determine Winner")
    void testDetermineWinner() {
        BackgammonBoard.blackOutCheckers.clear();
        BackgammonBoard.whiteOutCheckers.clear();
        int winner = BackgammonBoard.determineWinner();
        assertEquals(0, winner);
    }
}

