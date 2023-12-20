package view;

import service.BackgammonBoard;
import service.PlayerActions;
import dto.Player;
import service.GetMoveOptions;
import service.MatchActions;

import java.util.Scanner;

import static service.PlayerActions.*;

public class BackgammonGame {

    static Player player1 = new Player();
    static Player player2 = new Player();
    static int matchLength;

    static MatchActions matchActions;

    public static void play() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Match Length: " + matchLength + " games");

        for (int game = 1; game <= matchLength; game++) {
            System.out.println("---------------------");
            System.out.println("Game " + game);
            BackgammonBoard backgammonBoard = new BackgammonBoard();
            backgammonBoard.initialize();
            BackgammonBoard.display();
            matchActions = new MatchActions();
            int firstPlayer = PlayerActions.determineFirstPlayer();
            System.out.println("Player " + (firstPlayer + 1) + " goes first!");

            boolean p1Turn = (firstPlayer == 0);
            // In case user refuses to take double

            while (true) {
                if (backgammonBoard.isGameOver()) {
                    matchActions.updateMatchScore(backgammonBoard.determineWinner()==1? player1 : player2);
                    break;
                }

                System.out.println((p1Turn ? player1.getName() : player2.getName())
                        + ", enter \n'" + roll + "' to roll the dice, \n'" + quit + "' to exit, \n'"
                        + "dice <int> <int>' to set customized dice values \n'"+ pip
                        + "' to show pip count, \n'" + hint + "' to show all possible commands \n'"
                        + new_game + "' to start a new game \n'"
                        + double_cube + "' offers a double to the other player \n \t'"
                        + accept + "' to continue the game for twice the stake \n \t'"
                        + refuse + "' to reject the double offer" );
                String input = scanner.nextLine();

                if (input.equals(quit)) {
                    System.out.println("Game over!");
                    break;
                }
                if(input.equals(refuse)){
                    if (matchActions.isDoubleOffered()) {
                        matchActions.refuseDouble();
                        System.out.println("Game over!");
                        break;
                    } else {
                        System.out.println("No double is currently offered.");
                    }
                }

                if (input.equals(new_game)) {
                    System.out.println("Starting a new game");
                    break;
                }

                if (input.startsWith("dice")) {
                    String[] diceValues = input.split("\\s+");
                    if (diceValues.length == 3) {
                        try {
                        int customDice1 = Integer.parseInt(diceValues[1]);
                        int customDice2 = Integer.parseInt(diceValues[2]);

                        // Set the custom dice values
                        PlayerActions.setCustomDiceValues(customDice1, customDice2);

                        // Inform the user about the custom dice values
                        System.out.println("Custom dice values set: " + customDice1 + " " + customDice2);

                        // Continue with the turn
                        GetMoveOptions.options(customDice1, customDice2, p1Turn, player1);
                        PlayerActions.getPipCount(p1Turn ? player1 : player2);
                        p1Turn = !p1Turn;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid dice values. Please enter valid integers.");
                    }
                } else {
                    System.out.println("Invalid command. Please use the format 'dice <int> <int>'.");
                }
            } else {
                switch (input) {
                    case "roll": {
                        int dice1 = PlayerActions.rollDie();
                        int dice2 = PlayerActions.rollDie();
                        GetMoveOptions.options(dice1, dice2, p1Turn, p1Turn ? player1 : player2);
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
                                "\n 4." + new_game +
                                "\n 5." + double_cube);
                        break;
                    }
                    case "double_cube": {
                        if (!matchActions.isDoubleOffered()) {
                            matchActions.doubleOffer(p1Turn ? player1 : player2, p1Turn ? player2 : player1);
                        } else {
                            System.out.println("A double is already offered. You can accept or refuse.");
                        }
                        break;
                    }
                    case "accept": {
                        if (matchActions.isDoubleOffered()) {
                            matchActions.acceptDouble();
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
        }

            System.out.println("Match Score: " + player1.getName() + ": " + player1.getMatchScore() +
                    " - " + player2.getName() + ": " + player2.getMatchScore());
        }

        System.out.println("Match Over! " + 
        (player1.getMatchScore() > player2.getMatchScore() ? "Winner of the match is: Player1" :
         (player2.getMatchScore() > player1.getMatchScore() ? "Winner of the match is: Player2" : "No winner")));

         
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
        BackgammonBoard.setMatchLength(matchLength);
        play();
        scanner.close();
    }
}
