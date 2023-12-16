package view;

import service.BackgammonBoard;
import service.Actions;
import dto.Player;

import java.util.Scanner;

import static service.Actions.*;

public class BackgammonGame {

    static Player player1 = new Player();
    static Player player2 = new Player();
    static int matchLength;

    public static void play() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Match Length: " + matchLength + " games");

        for (int game = 1; game <= matchLength; game++) {
            System.out.println("---------------------");
            System.out.println("Game " + game);
            BackgammonBoard.initialize();
            BackgammonBoard.display();
            int firstPlayer = Actions.determineFirstPlayer();
            System.out.println("Player " + (firstPlayer + 1) + " goes first!");

            boolean p1Turn = (firstPlayer == 0);
            // In case user refuses to take double

            while (true) {
                if (BackgammonBoard.isGameOver()) {
                    updateMatchScore(BackgammonBoard.isGameOver() ? 1 : 2);
                    break;
                }

                System.out.println((p1Turn ? player1.getName() : player2.getName())
                        + ", enter \n'" + roll + "' to roll the dice, \n'" + quit + "' to exit, \n'" + pip
                        + "' to show pip count, \n'" + hint + "' to show all possible commands \n'" + double_cube + "' offers a double to the other player \n \t'" + accept + "' to continue the game for twice the stake \n \t'"
                        + refuse + "' to reject the double offer"  );
                String input = scanner.nextLine();
                if (input.equals(refuse) || input.equals(quit)) {
                    System.out.println("Game over!");
                    break;
                }

                switch (input) {
                    case "roll": {
                        int dice1 = Actions.rollDie();
                        int dice2 = Actions.rollDie();
                        BackgammonBoard.options(dice1, dice2, p1Turn, p1Turn ? player1 : player2);
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
                        System.out.println("Following commands are allowed to be entered:\n1." + pip +
                                "\n 2." + roll +
                                "\n 3." + quit +
                                "\n 4." + double_cube);
                        break;
                    }
                    case "double_cube": {
                        if (!BackgammonBoard.isDoubleOffered()) {
                            BackgammonBoard.doubleOffer(p1Turn ? player1 : player2, p1Turn ? player2 : player1);
                        } else {
                            System.out.println("A double is already offered. You can accept or refuse.");
                        }
                        break;
                    }
                    case "accept": {
                        if (BackgammonBoard.isDoubleOffered()) {
                            BackgammonBoard.acceptDouble();
                        } else {
                            System.out.println("No double is currently offered.");
                        }
                        break;
                    }
                    case "refuse": {
                        if (BackgammonBoard.isDoubleOffered()) {
                            BackgammonBoard.refuseDouble();

                        } else {
                            System.out.println("No double is currently offered.");
                        }
                        break;
                    }
                    default: {
                        System.out.println("Invalid command. Please enter a valid command.");
                        break;
                    }
                }
            }

            System.out.println("Match Score: " + player1.getName() + " " + player1.getMatchScore() +
                    " - " + player2.getName() + " " + player2.getMatchScore());
        }

        System.out.println(" Match Over! Winner of the match is: "+ (player1.getMatchScore()>player2.getMatchScore()?"Player1":"Player2"));
    }

    private static void updateMatchScore(int winner) {
        if (winner == 1) {
            player1.incrementMatchScore();
        } else {
            player2.incrementMatchScore();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of Player 1: ");
        String player1Name = scanner.nextLine();
        System.out.print("Enter the name of Player 2: ");
        String player2Name = scanner.nextLine();

        System.out.print("Enter the length of the match (number of games): ");
        matchLength = scanner.nextInt();

        player1.setName(player1Name);
        player2.setName(player2Name);
        play();
        scanner.close();
    }
}
