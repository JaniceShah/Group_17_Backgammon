package view;

import service.BackgammonBoard;
import service.Actions;
import dto.Player;

import java.util.Scanner;

import static service.Actions.*;

public class BackgammonGame {

    static Player player1 = new Player();
    static Player player2 = new Player();

    public static void play() {
        Scanner scanner = new Scanner(System.in);
        BackgammonBoard.initialize();
        BackgammonBoard.display();

        int firstPlayer = BackgammonBoard.determineFirstPlayer();
        System.out.println("Player " + (firstPlayer + 1) + " goes first!");

        boolean p1Turn = (firstPlayer == 0);

        while (true) {
            System.out.println((p1Turn ? player1.getName() : player2.getName())
                    + ", enter '" + roll + "' to roll the dice, '" + quit + "' to exit, '" + pip
                    + "' to show pip count, '" + hint + "' to show all possible commands");
            String input = scanner.nextLine();

            if (input.equals(quit)) {
                System.out.println("Game over!");
                break;
            }

            switch (input) {
                case "roll": {
                    // Actions.rollChance(p1Turn ? player1 : player2);
                    int dice1 = Actions.rollDie();
                    int dice2 = Actions.rollDie();
                    BackgammonBoard.options(dice1, dice2, p1Turn);
                    Actions.rollChance(p1Turn ? player1 : player2);
                    p1Turn = !p1Turn;
                    break;
                }
                case "pip": {
                    System.out.println("Pip number for both players are "
                            + player1.getName() + ":" + player1.getScore() + " "
                            + player2.getName() + ":" + player2.getScore());
                    break;
                }
                case "hint": {
                    System.out.println("Following commands are allowed to be entered:\n1. pip \n" + //
                            "2. roll \n" + //
                                    "3. quit");
                    break;
                }
                default: {
                    System.out.println("Invalid command. Please enter a valid command.");
                    break;
                }
            }

        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of Player 1: ");
        String player1Name = scanner.nextLine();
        System.out.print("Enter the name of Player 2: ");
        String player2Name = scanner.nextLine();
        player1.setName(player1Name);
        player2.setName(player2Name);
        play();
        scanner.close();
    }
}
