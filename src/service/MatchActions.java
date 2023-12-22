package service;

import dto.Player;

public class MatchActions {

    public static int doublingCubePosition = 1;
    public static int currentStakes = 1;
    private static boolean doubleOffered = false;
    public static Player offeringPlayer = null;
    public static Player receivingPlayer = null;


    /**
     * Initializes the match actions with default values.
     */
    public MatchActions(){
        doublingCubePosition = 1;
        doubleOffered = false;
        offeringPlayer = null;
        receivingPlayer = null;
        currentStakes = 1;
    }

    /**
     * Updates the match score for the given player based on the current stakes.
     *
     * @param player The player whose match score needs to be updated.
     */
    public void updateMatchScore(Player player) {
        player.setMatchScore(player.getMatchScore()+currentStakes);
    }

    /**
     * Checks if a double has been offered.
     *
     * @return True if a double has been offered, false otherwise.
     */
    public boolean isDoubleOffered() {
        return doubleOffered;
    }

    /**
     * Displays information about the doubling cube, including its position and the player who offered the double.
     */
    public void displayDoublingCube() {
        System.out.println("Doubling Cube: Position " + doublingCubePosition +
                (doubleOffered ? (" Offered by: " + offeringPlayer.getName()) : ""));
    }

    /**
     * Offers a double to another player.
     *
     * @param offeringPlayer  The player offering the double.
     * @param receivingPlayer The player receiving the double offer.
     */
    public void doubleOffer(Player offeringPlayer, Player receivingPlayer) {
        doubleOffered = true;
        MatchActions.offeringPlayer = offeringPlayer;
        MatchActions.receivingPlayer = receivingPlayer;
        System.out.println(offeringPlayer.getName() + " offers a double to " + receivingPlayer.getName() + ".");
    }


    /**
     * Accepts a double offer, doubling the stakes.
     */
    public void acceptDouble() {
        doubleOffered = false;
        doublingCubePosition += 1; // Double the current position
        int currentStake = (int) Math.pow(2, doublingCubePosition - 1); // 2^(position-1) for the current stake
        currentStakes = currentStake;
        System.out.println(receivingPlayer.getName() + " accepts the double. The stake is now " + currentStake + ".");
    }

    /**
     * Refuses a double offer, awarding the current stake to the offering player.
     */
    public void refuseDouble() {
        doubleOffered = false;
        System.out.println(receivingPlayer.getName() + " refuses the double. " +
                offeringPlayer.getName() + " wins the current stake.");
        offeringPlayer.setMatchScore(offeringPlayer.getMatchScore()+currentStakes);
    }

}
