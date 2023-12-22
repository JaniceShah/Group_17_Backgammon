package service;

import dto.Player;

import java.util.Random;

public class PlayerActions {


    public static String roll = "roll";
    public static String quit = "quit";
    public static String hint = "hint";
    public static String pip = "pip";
    public static String double_cube = "double_cube";
    public static String accept = "accept";
    public static String refuse = "refuse";
    public static String new_game = "new_game";

    public static String test = "test";
    private static int customDice1;
    private static int customDice2;

    /**
     * Simulates rolling a six-sided die.
     *
     * @return The result of the die roll.
     */
    public static int rollDie() {
        Random random = new Random();
        if (customDice1 != 0 && customDice2 != 0) {
            int temp = customDice1;
            customDice1 = 0;
            return temp;
        }
        return random.nextInt(6) + 1;
    }

    /**
     * Sets custom values for the dice. Used for testing purposes.
     *
     * @param value1 The value for the first die.
     * @param value2 The value for the second die.
     */
     public static void setCustomDiceValues(int value1, int value2) {
        customDice1 = value1;
        customDice2 = value2;
    }

    /**
     * Calculates and updates the pip count for a player based on the total dice result.
     *
     * @param totalDice The total value obtained from rolling the dice.
     * @param player    The player whose pip count needs to be updated.
     */
    public static void calculatePipCount(int totalDice, Player player){
        player.setScore(player.getScore() - totalDice);
    }

    /**
     * Displays the pip count for a player.
     *
     * @param player The player whose pip count needs to be displayed.
     */
    public static void getPipCount(Player player){
        System.out.println("Pip number for " + player.getName() + ":" + player.getScore());
    }

    /**
     * Determines the first player based on the result of rolling dice for each player.
     *
     * @return The index of the first player.
     */
    public static int determineFirstPlayer() {
        System.out.println("Rolling one die for each player to determine which player goes first:\n");

        int numPlayers = 2;
        int[] diceResults = new int[numPlayers];

        for (int player = 0; player < numPlayers; player++) {
            diceResults[player] = PlayerActions.rollDie();
            System.out.println("Player " + (player + 1) + " rolled a " + diceResults[player]);

            // If both players roll the same number, shuffle the dice again
            if (player == 1 && diceResults[0] == diceResults[1]) {
                player = -1;
                System.out.println("Since both players have got the same dice number, shuffling the dice again");
            }
        }

        int maxRoll = 0;
        int firstPlayer = 0;

        // Determine the player with the highest roll
        for (int player = 0; player < diceResults.length; player++) {
            if (diceResults[player] > maxRoll) {
                maxRoll = diceResults[player];
                firstPlayer = player;
            }
        }

        return firstPlayer;
    }

}

