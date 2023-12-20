package service;

import dto.Player;

public class MatchActions {

    public static int doublingCubePosition = 1;
    public static int currentStakes = 1;
    private static boolean doubleOffered = false;
    public static Player offeringPlayer = null;
    public static Player receivingPlayer = null;

    public MatchActions(){
        doublingCubePosition = 1;
        doubleOffered = false;
        offeringPlayer = null;
        receivingPlayer = null;
        currentStakes = 1;
    }

    public void updateMatchScore(Player player) {
        player.setMatchScore(player.getMatchScore()+currentStakes);
    }

    public boolean isDoubleOffered() {
        return doubleOffered;
    }

    public void displayDoublingCube() {
        System.out.println("Doubling Cube: Position " + doublingCubePosition +
                (doubleOffered ? (" Offered by: " + offeringPlayer.getName()) : ""));
    }

    public void doubleOffer(Player offeringPlayer, Player receivingPlayer) {
        doubleOffered = true;
        MatchActions.offeringPlayer = offeringPlayer;
        MatchActions.receivingPlayer = receivingPlayer;
        System.out.println(offeringPlayer.getName() + " offers a double to " + receivingPlayer.getName() + ".");
    }

    public void acceptDouble() {
        doubleOffered = false;
        doublingCubePosition += 1; // Double the current position
        int currentStake = (int) Math.pow(2, doublingCubePosition - 1); // 2^(position-1) for the current stake
        currentStakes = currentStake;
        System.out.println(receivingPlayer.getName() + " accepts the double. The stake is now " + currentStake + ".");
    }

    public void refuseDouble() {
        doubleOffered = false;
        System.out.println(receivingPlayer.getName() + " refuses the double. " +
                offeringPlayer.getName() + " wins the current stake.");
        offeringPlayer.setMatchScore(offeringPlayer.getMatchScore()+currentStakes);
    }

}
