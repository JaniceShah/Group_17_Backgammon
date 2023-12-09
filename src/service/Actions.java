package service;

import dto.Player;

import java.util.Random;
import java.util.Scanner;

public class Actions {


    public static String roll = "roll";
    public static String quit = "quit";
    public static String hint = "hint";
    public static String pip = "pip";
    public static String double_cube = "double_cube";
    public static String accept = "accept";
    public static String refuse = "refuse";

    public static int rollDie() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
    public static void rollChance(Player player){
        int dice1 = rollDie();
        int dice2 = rollDie();

        System.out.println(player.getName() + " rolled a " + dice1 + " and a " + dice2);

        int totalDice = dice1 + dice2;
        Scanner scanner = new Scanner(System.in);
        BackgammonBoard.display();
        player.setScore(player.getScore() - totalDice);

        System.out.println("Total of the dice for " + player.getName() + ": " + totalDice
                +"\npip count:"+ player.getScore());
    }
    public static int determineFirstPlayer() {
        System.out.println("Rolling one die for each player to determine which player \r\n" +
                "goes first:\n");

        int numPlayers = 2;
        int[] diceResults = new int[numPlayers];

        for (int player = 0; player < numPlayers; player++) {
            diceResults[player] = Actions.rollDie();
            System.out.println("Player " + (player + 1) + " rolled a " + diceResults[player]);
            if(player==1 && diceResults[0]==diceResults[1]){
                player=-1;
                System.out.println("Since both players have got same dice number shuffling the dice again");
            }
        }

        int maxRoll = 0;
        int firstPlayer = 0;

        for (int player = 0; player < diceResults.length; player++) {
            if (diceResults[player] > maxRoll) {
                maxRoll = diceResults[player];
                firstPlayer = player;
            }
        }

        return firstPlayer;
    }

}

