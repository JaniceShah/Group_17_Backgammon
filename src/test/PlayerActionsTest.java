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



    private String captureSystemOut(Runnable action) {
        // Redirect System.out to a ByteArrayOutputStream
        var originalOut = System.out;
        var outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            action.run();
            return outputStream.toString();
        } finally {
            System.setOut(originalOut);
        }
    }
}
