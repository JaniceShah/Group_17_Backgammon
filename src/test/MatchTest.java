package test;
import dto.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.MatchActions;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {


    private MatchActions matchActions;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        matchActions = new MatchActions();
        player1 = new Player();
        player1.setName("Player1");
        player2 = new Player();
        player2.setName("Player2");
    }

    @Test
    void testUpdateMatchScore() {
        matchActions.updateMatchScore(player1);
        assertEquals(1, player1.getMatchScore());
    }

    @Test
    void testIsDoubleOffered() {
        assertFalse(matchActions.isDoubleOffered());

        matchActions.doubleOffer(player1, player2);

        assertTrue(matchActions.isDoubleOffered());
    }

    @Test
    void testDisplayDoublingCube() {
        matchActions.doubleOffer(player1, player2);
        matchActions.displayDoublingCube();

        // You may want to check the console output manually since System.out.println cannot be easily tested.
    }

    @Test
    void testDoubleOffer() {
        matchActions.doubleOffer(player1, player2);

        assertTrue(matchActions.isDoubleOffered());
        assertEquals(player1, MatchActions.offeringPlayer);
        assertEquals(player2, MatchActions.receivingPlayer);
    }

    @Test
    void testAcceptDouble() {
        matchActions.doubleOffer(player1, player2);
        matchActions.acceptDouble();

        assertFalse(matchActions.isDoubleOffered());
        assertEquals(2, MatchActions.doublingCubePosition);
        assertEquals(2, MatchActions.currentStakes);
    }

    @Test
    void testRefuseDouble() {
        matchActions.doubleOffer(player1, player2);
        matchActions.refuseDouble();

        assertFalse(matchActions.isDoubleOffered());
        assertEquals(1, player1.getMatchScore()); // Refusing player wins the current stake
    }
}
