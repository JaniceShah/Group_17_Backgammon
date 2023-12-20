package test;

import dto.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    @DisplayName("Test Player Constructor and Getters")
    void testPlayerConstructorAndGetters() {
        String playerName = "Alice";
        Player player = new Player();
        player.setName(playerName);
        assertEquals(playerName, player.getName());
        assertEquals(167, player.getScore());
        assertEquals(0, player.getMatchScore());
    }

    @Test
    @DisplayName("Test Setters and Getters for MatchScore")
    void testSettersAndGettersForMatchScore() {
        Player player = new Player();
        player.setMatchScore(3);
        assertEquals(3, player.getMatchScore());
    }
}
