package test;
import dto.Player;

import service.MatchActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

public class MatchTest {


    private MatchActions matchActions;
    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        matchActions = new MatchActions();
        player1 = new Player();
        player1.setName("Player1");
        player2 = new Player();
        player2.setName("Player2");
    }

    @Test
    public void testUpdateMatchScore() {
        matchActions.updateMatchScore(player1);
        assertEquals(1, player1.getMatchScore());
    }

    @Test
    public void testIsDoubleOffered() {
        assertFalse(matchActions.isDoubleOffered());

        matchActions.doubleOffer(player1, player2);

        assertTrue(matchActions.isDoubleOffered());
    }

    @Test
    public void testDisplayDoublingCube() {
        matchActions.doubleOffer(player1, player2);
        matchActions.displayDoublingCube();

    }

    @Test
    public void testDoubleOffer() {
        matchActions.doubleOffer(player1, player2);

        assertTrue(matchActions.isDoubleOffered());
        assertEquals(player1, MatchActions.offeringPlayer);
        assertEquals(player2, MatchActions.receivingPlayer);
    }

    @Test
    public void testAcceptDouble() {
        matchActions.doubleOffer(player1, player2);
        matchActions.acceptDouble();

        assertFalse(matchActions.isDoubleOffered());
        assertEquals(2, MatchActions.doublingCubePosition);
        assertEquals(2, MatchActions.currentStakes);
    }

    @Test
    public void testRefuseDouble() {
        matchActions.doubleOffer(player1, player2);
        matchActions.refuseDouble();

        assertFalse(matchActions.isDoubleOffered());
        assertEquals(1, player1.getMatchScore()); // Refusing player wins the current stake
    }
}
