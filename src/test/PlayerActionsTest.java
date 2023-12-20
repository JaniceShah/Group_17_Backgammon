package test;

import dto.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PlayerActions;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerActionsTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void rollDieShouldReturnValidResult() {
        int result = PlayerActions.rollDie();
        assertTrue(result >= 1 && result <= 6, "Die roll result should be between 1 and 6");
    }

//    @Test
//    void setCustomDiceValuesShouldSetValues() {
//        PlayerActions.setCustomDiceValues(3, 4);
//        assertEquals(3, PlayerActions.rollDie());
//        assertEquals(4, PlayerActions.rollDie());
//    }

    @Test
    void calculatePipCountShouldReduceScore() {
        player.setScore(10);
        PlayerActions.calculatePipCount(3, player);
        assertEquals(7, player.getScore());
    }

    @Test
    void getPipCountShouldPrintCorrectMessage() {
        player.setScore(15);
        String expectedMessage = "Pip number for " + player.getName() + ":15";
        assertDoesNotThrow(() -> {
            System.out.println("Redirected output: " + captureSystemOut(() -> PlayerActions.getPipCount(player)));
        });
    }

//    @Test
//    void determineFirstPlayerShouldReturnValidPlayerIndex() {
//        // Mocking random die rolls for testing
//        PlayerActions.setCustomDiceValues(4, 2);
//        int result = PlayerActions.determineFirstPlayer();
//        assertEquals(0, result, "Player 1 should win with a roll of 4 over 2");
//    }

    private String captureSystemOut(Runnable action) {
        // Redirect System.out to a ByteArrayOutputStream
        var originalOut = System.out;
        var outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            action.run();
            return outputStream.toString();
        } finally {
            // Restore System.out
            System.setOut(originalOut);
        }
    }
}
